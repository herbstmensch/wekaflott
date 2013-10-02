package de.timherbst.wau.service;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import de.axtres.logging.main.AxtresLogger;
import de.axtres.util.AppProperties;
import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.auswertung.Auswertung;
import de.timherbst.wau.domain.auswertung.EinzelAuswertung;
import de.timherbst.wau.domain.auswertung.EinzelAuswertungBean;
import de.timherbst.wau.domain.auswertung.EinzelAuswertungEntry;
import de.timherbst.wau.domain.auswertung.EinzelAuswertungSubBean;
import de.timherbst.wau.domain.auswertung.EinzelUrkundeBean;
import de.timherbst.wau.domain.auswertung.MannschaftsAuswertung;
import de.timherbst.wau.domain.auswertung.MannschaftsAuswertungBean;
import de.timherbst.wau.domain.auswertung.MannschaftsAuswertungEinzelBean;
import de.timherbst.wau.domain.auswertung.MannschaftsAuswertungEntry;
import de.timherbst.wau.domain.auswertung.MannschaftsAuswertungMannschaftBean;
import de.timherbst.wau.domain.auswertung.MannschaftsErgebnis;
import de.timherbst.wau.domain.auswertung.MannschaftsRundenAuswertungBean;
import de.timherbst.wau.domain.auswertung.MannschaftsUrkundeBean;
import de.timherbst.wau.domain.auswertung.TabellenEintrag;
import de.timherbst.wau.domain.auswertung.TabellenEintragTurner;
import de.timherbst.wau.domain.wettkampf.EinzelWettkampf;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.util.Formatter;
import de.timherbst.wau.util.WeKaUtil;

public class AuswertungService {

	public static EinzelAuswertung getAuswertung(final EinzelWettkampf w) {
		EinzelAuswertung aw = new EinzelAuswertung(w);
		aw.getEntrys().putAll(getEinzelAuswertungEntrys(w.getTurner(), w.getGewerteteGeraete()));
		return aw;
	}

	public static Map<Turner, EinzelAuswertungEntry> getEinzelAuswertungEntrys(final List<Turner> lt, final int gewerteteGeraete) {

		Collections.sort(lt, new Comparator<Turner>() {

			@Override
			public int compare(Turner o1, Turner o2) {
				return o2.getWertungen().getGesamtWertung(gewerteteGeraete, o2.getAk()).compareTo(o1.getWertungen().getGesamtWertung(gewerteteGeraete, o1.getAk()));
			}
		});

		Map<Turner, EinzelAuswertungEntry> entrys = new HashMap<Turner, EinzelAuswertungEntry>();
		double lastWertung = -1d;
		int lastPlatz = 1;
		for (Turner t : lt) {
			double gw = t.getWertungen().getGesamtWertung(gewerteteGeraete, false);
			int size = entrys.size();
			int platz = gw == lastWertung ? lastPlatz : size + 1;
			entrys.put(t, new EinzelAuswertungEntry(t, gw, t.getAk() ? 0 : platz));
			lastWertung = gw;
			lastPlatz = platz;
		}
		return entrys;
	}

	public static MannschaftsAuswertung getAuswertung(final MannschaftsWettkampf w) {

		List<Mannschaft> lm = w.getMannschaften();
		Collections.sort(lm, new Comparator<Mannschaft>() {

			@Override
			public int compare(Mannschaft o1, Mannschaft o2) {
				return getMannschaftsErgebnis(o2, w.getGewerteteTurnerProGeraet(), w.getGewerteteGeraete()).compareTo(getMannschaftsErgebnis(o1, w.getGewerteteTurnerProGeraet(), w.getGewerteteGeraete()));
			}

		});

		MannschaftsAuswertung aw = new MannschaftsAuswertung(w);
		double lastWertung = -1d;
		int lastPlatz = 1;
		for (Mannschaft m : lm) {
			MannschaftsErgebnis gw = getMannschaftsErgebnis(m, w.getGewerteteTurnerProGeraet(), w.getGewerteteGeraete());
			int size = aw.getEntrysMannschaft().size();
			int platz = gw.getGesamt() == lastWertung ? lastPlatz : size + 1;
			aw.getEntrysMannschaft().put(m, new MannschaftsAuswertungEntry(m, gw, platz));
			lastWertung = gw.getGesamt();
			lastPlatz = platz;
		}
		return aw;
	}

	private static MannschaftsErgebnis getMannschaftsErgebnis(Mannschaft o2, int turnerProGeraet, int gewerteteGeraete) {
		MannschaftsErgebnis erg = new MannschaftsErgebnis();
		List<Double> l;
		// Boden
		l = new Vector<Double>();
		for (Turner t : o2.getTurner())
			l.add(t.getAk() ? 0 : t.getWertungen().getBoden().getEndwert());
		for (; l.size() < turnerProGeraet;)
			l.add(0d);
		erg.setBoden(getBestN(l, turnerProGeraet));
		// Seitpferd
		l = new Vector<Double>();
		for (Turner t : o2.getTurner())
			l.add(t.getAk() ? 0 : t.getWertungen().getSeitpferd().getEndwert());
		for (; l.size() < turnerProGeraet;)
			l.add(0d);
		erg.setSeitpferd(getBestN(l, turnerProGeraet));
		// Ringe
		l = new Vector<Double>();
		for (Turner t : o2.getTurner())
			l.add(t.getAk() ? 0 : t.getWertungen().getRinge().getEndwert());
		for (; l.size() < turnerProGeraet;)
			l.add(0d);
		erg.setRinge(getBestN(l, turnerProGeraet));
		// Sprung
		l = new Vector<Double>();
		for (Turner t : o2.getTurner())
			l.add(t.getAk() ? 0 : t.getWertungen().getSprung().getEndwert());
		for (; l.size() < turnerProGeraet;)
			l.add(0d);
		erg.setSprung(getBestN(l, turnerProGeraet));
		// Barren
		l = new Vector<Double>();
		for (Turner t : o2.getTurner())
			l.add(t.getAk() ? 0 : t.getWertungen().getBarren().getEndwert());
		for (; l.size() < turnerProGeraet;)
			l.add(0d);
		erg.setBarren(getBestN(l, turnerProGeraet));
		// Reck
		l = new Vector<Double>();
		for (Turner t : o2.getTurner())
			l.add(t.getAk() ? 0 : t.getWertungen().getReck().getEndwert());
		for (; l.size() < turnerProGeraet;)
			l.add(0d);
		erg.setReck(getBestN(l, turnerProGeraet));
		// Gesamt
		erg.setGesamt(getBestN(Arrays.asList(erg.getBoden(), erg.getSeitpferd(), erg.getRinge(), erg.getSprung(), erg.getBarren(), erg.getReck()), gewerteteGeraete));
		return erg;
	}

	private static Double getBestN(List<Double> l, int n) {
		Collections.sort(l);
		Collections.reverse(l);
		Double ges = 0d;
		for (int i = 0; i < n; i++)
			ges += l.get(i);
		return ges;
	}

	public static void printEinzelAuswertung(List<EinzelAuswertung> lea, boolean xls) throws JRException, IOException {

		if (lea == null || lea.size() == 0)
			return;

		for (EinzelAuswertung ea : lea) {

			List<EinzelAuswertungBean> list = new Vector<EinzelAuswertungBean>();

			final EinzelWettkampf w = ea.getWettkampf();
			List<Turner> lt = w.getTurner();
			Collections.sort(lt, new Comparator<Turner>() {

				@Override
				public int compare(Turner o1, Turner o2) {
					return o2.getWertungen().getGesamtWertung(w.getGewerteteGeraete(), o2.getAk()).compareTo(o1.getWertungen().getGesamtWertung(w.getGewerteteGeraete(), o2.getAk()));
				}
			});

			List<EinzelAuswertungSubBean> list2 = new Vector<EinzelAuswertungSubBean>();

			for (Turner t : lt) {
				EinzelAuswertungEntry e = ea.getEntrys().get(t);
				list2.add(new EinzelAuswertungSubBean(t.getName(), t.getVorname(), t.getVerein(), t.getJahrgang(), t.getWertungen().getBoden(), t.getWertungen().getSeitpferd(), t.getWertungen().getRinge(), t.getWertungen().getSprung(), t.getWertungen().getBarren(), t.getWertungen().getReck(), e.getGesamt(), e.getPlatzierung()));
			}

			list.add(new EinzelAuswertungBean(w.getName(), w.getGeraeteText(), w.getTyp(), w.getJahrgaenge(), new JRBeanCollectionDataSource(list2)));

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("veranstaltung", WettkampfTag.get().getName());
			parameters.put("datum", Formatter.format(WettkampfTag.get().getDatum()));
			parameters.put("ort", WettkampfTag.get().getOrt());
			JasperReport sjr = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "siegerliste_sub.jrxml");
			parameters.put("SUBREPORT", sjr);

			JasperReport jr = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "siegerliste.jrxml");

			JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(list));
			File f = new File(getOutputFolder() + "siegerlisten");
			if (!f.exists())
				f.mkdirs();

			if (xls)
				printAuswertungXLS(jp, getNameSuffix(ea));
			else
				printAuswertungPDF(jp, getNameSuffix(ea));

		}
	}

	// public static void printRundenAuswertung(String veranstaltung, Date date,
	// String ort, List<TabellenEintrag> tabelle) throws JRException,
	// IOException {
	//
	//
	// Map<String, Object> parameters = new HashMap<String, Object>();
	// parameters.put("veranstaltung", veranstaltung);
	// parameters
	// .put("datum", Formatter.format(date));
	// parameters.put("ort", ort);
	//
	// JasperReport jr = JasperCompileManager
	// .compileReport("templates/tabelle.jrxml");
	//
	// JasperPrint jp = JasperFillManager.fillReport(jr, parameters,
	// new JRBeanCollectionDataSource(tabelle));
	// File f = new File(getOutputFolder()+"tabellen");
	// if (!f.exists())
	// f.mkdirs();
	//
	// printAuswertungPDF(jp, getNameSuffix(lea));
	// }

	private static String getOutputFolder() {
		return WeKaUtil.enshureEnding(AppProperties.getProperty("OUTPUT_PATH", "out"), "/") + WeKaUtil.enshureEnding(WeKaUtil.getOutputFoldername(), "/");
	}

	public static void printMannschaftsAuswertung(List<MannschaftsAuswertung> lma, boolean xls) throws JRException, IOException {

		if (lma == null || lma.size() == 0)
			return;

		for (MannschaftsAuswertung ma : lma) {

			List<MannschaftsAuswertungBean> list = new Vector<MannschaftsAuswertungBean>();

			final MannschaftsWettkampf w = ma.getWettkampf();
			List<MannschaftsAuswertungEntry> entrys = new Vector<MannschaftsAuswertungEntry>(ma.getEntrysMannschaft().values());
			Collections.sort(entrys, new Comparator<MannschaftsAuswertungEntry>() {

				@Override
				public int compare(MannschaftsAuswertungEntry o1, MannschaftsAuswertungEntry o2) {
					return o1.getPlatzierung().compareTo(o2.getPlatzierung());
				}
			});

			List<MannschaftsAuswertungMannschaftBean> list_mannschaft = new Vector<MannschaftsAuswertungMannschaftBean>();
			for (MannschaftsAuswertungEntry mae : entrys) {

				list_mannschaft.add(new MannschaftsAuswertungMannschaftBean(mae.getMannschaft().getVerein(), mae.getErgebnis().getBoden(), mae.getErgebnis().getSeitpferd(), mae.getErgebnis().getRinge(), mae.getErgebnis().getSprung(), mae.getErgebnis().getBarren(), mae.getErgebnis().getReck(), mae.getErgebnis().getGesamt(), mae.getPlatzierung()));
			}

			List<EinzelAuswertungEntry> entrys_einzel = new Vector<EinzelAuswertungEntry>(getEinzelAuswertungEntrys(w.getTurner(), w.getGewerteteGeraete()).values());
			Collections.sort(entrys_einzel, new Comparator<EinzelAuswertungEntry>() {

				@Override
				public int compare(EinzelAuswertungEntry o1, EinzelAuswertungEntry o2) {
					return new Integer(o1.getPlatzierung() == 0 ? Integer.MAX_VALUE : o1.getPlatzierung()).compareTo(new Integer(o2.getPlatzierung() == 0 ? Integer.MAX_VALUE : o2.getPlatzierung()));
				}
			});

			List<MannschaftsAuswertungEinzelBean> list_einzel = new Vector<MannschaftsAuswertungEinzelBean>();
			for (EinzelAuswertungEntry eae : entrys_einzel) {

				list_einzel.add(new MannschaftsAuswertungEinzelBean(eae.getTurner().getName(), eae.getTurner().getVorname(), eae.getTurner().getMannschaft().getVerein(), eae.getTurner().getJahrgang(), eae.getTurner().getWertungen().getBoden(), eae.getTurner().getWertungen().getSeitpferd(), eae.getTurner().getWertungen().getRinge(), eae.getTurner().getWertungen().getSprung(), eae.getTurner().getWertungen().getBarren(), eae.getTurner().getWertungen().getReck(), eae.getGesamt(), eae.getPlatzierung()));
			}

			MannschaftsAuswertungBean bean = new MannschaftsAuswertungBean(w.getName(), w.getGeraeteText(), w.getTyp(), w.getJahrgaenge(), new JRBeanCollectionDataSource(list_einzel), new JRBeanCollectionDataSource(list_mannschaft));
			list.add(bean);

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("veranstaltung", WettkampfTag.get().getName());
			parameters.put("datum", Formatter.format(WettkampfTag.get().getDatum()));
			parameters.put("ort", WettkampfTag.get().getOrt());
			JasperReport sjr_einzel = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "MannschaftsSiegerliste_Einzelsub.jrxml");
			parameters.put("SUBREPORT_EINZEL", sjr_einzel);
			JasperReport sjr_mannschaft = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "MannschaftsSiegerliste_Mannschaftsub.jrxml");
			parameters.put("SUBREPORT_MANNSCHAFT", sjr_mannschaft);

			JasperReport jr = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "MannschaftsSiegerliste.jrxml");

			JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(list));
			File f = new File(getOutputFolder() + "siegerlisten");
			if (!f.exists())
				f.mkdirs();

			if (xls)
				printAuswertungXLS(jp, getNameSuffix(ma));
			else
				printAuswertungPDF(jp, getNameSuffix(ma));

		}

	}

	public static void printMannschaftsRundenAuswertung(MannschaftsAuswertung ma, List<TabellenEintrag> tabelle, String veranstaltung, boolean xls) throws JRException, IOException {

		if (ma == null)
			return;

		List<MannschaftsRundenAuswertungBean> list = new Vector<MannschaftsRundenAuswertungBean>();

		final MannschaftsWettkampf w = ma.getWettkampf();
		List<MannschaftsAuswertungEntry> entrys = new Vector<MannschaftsAuswertungEntry>(ma.getEntrysMannschaft().values());
		Collections.sort(entrys, new Comparator<MannschaftsAuswertungEntry>() {

			@Override
			public int compare(MannschaftsAuswertungEntry o1, MannschaftsAuswertungEntry o2) {
				return o1.getPlatzierung().compareTo(o2.getPlatzierung());
			}
		});

		List<MannschaftsAuswertungMannschaftBean> list_mannschaft = new Vector<MannschaftsAuswertungMannschaftBean>();
		for (MannschaftsAuswertungEntry mae : entrys) {

			list_mannschaft.add(new MannschaftsAuswertungMannschaftBean(mae.getMannschaft().getVerein(), mae.getErgebnis().getBoden(), mae.getErgebnis().getSeitpferd(), mae.getErgebnis().getRinge(), mae.getErgebnis().getSprung(), mae.getErgebnis().getBarren(), mae.getErgebnis().getReck(), mae.getErgebnis().getGesamt(), mae.getPlatzierung()));
		}

		List<EinzelAuswertungEntry> entrys_einzel = new Vector<EinzelAuswertungEntry>(getEinzelAuswertungEntrys(w.getTurner(), w.getGewerteteGeraete()).values());
		Collections.sort(entrys_einzel, new Comparator<EinzelAuswertungEntry>() {

			@Override
			public int compare(EinzelAuswertungEntry o1, EinzelAuswertungEntry o2) {
				return new Integer(o1.getPlatzierung() == 0 ? Integer.MAX_VALUE : o1.getPlatzierung()).compareTo(new Integer(o2.getPlatzierung() == 0 ? Integer.MAX_VALUE : o2.getPlatzierung()));
			}
		});

		List<MannschaftsAuswertungEinzelBean> list_einzel = new Vector<MannschaftsAuswertungEinzelBean>();
		for (EinzelAuswertungEntry eae : entrys_einzel) {

			list_einzel.add(new MannschaftsAuswertungEinzelBean(eae.getTurner().getName(), eae.getTurner().getVorname(), eae.getTurner().getMannschaft().getVerein(), eae.getTurner().getJahrgang(), eae.getTurner().getWertungen().getBoden(), eae.getTurner().getWertungen().getSeitpferd(), eae.getTurner().getWertungen().getRinge(), eae.getTurner().getWertungen().getSprung(), eae.getTurner().getWertungen().getBarren(), eae.getTurner().getWertungen().getReck(), eae.getGesamt(), eae.getPlatzierung()));
		}

		MannschaftsRundenAuswertungBean bean = new MannschaftsRundenAuswertungBean(w.getName(), w.getGeraeteText(), w.getTyp(), w.getJahrgaenge(), new JRBeanCollectionDataSource(list_einzel), new JRBeanCollectionDataSource(list_mannschaft), new JRBeanCollectionDataSource(tabelle));
		list.add(bean);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("veranstaltung", veranstaltung);
		JasperReport sjr_einzel = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "MannschaftsSiegerliste_Einzelsub.jrxml");
		parameters.put("SUBREPORT_EINZEL", sjr_einzel);
		JasperReport sjr_mannschaft = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "MannschaftsSiegerliste_Mannschaftsub.jrxml");
		parameters.put("SUBREPORT_MANNSCHAFT", sjr_mannschaft);
		JasperReport sjr_tabelle = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "MannschaftsSiegerliste_Tabellesub.jrxml");
		parameters.put("SUBREPORT_TABELLE", sjr_tabelle);

		JasperReport jr = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "MannschaftsRundenSiegerliste.jrxml");

		JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(list));
		File f = new File(getOutputFolder() + "siegerlisten");
		if (!f.exists())
			f.mkdirs();

		if (xls)
			printAuswertungXLS(jp, getNameSuffix(ma));
		else
			printAuswertungPDF(jp, getNameSuffix(ma));

	}

	private static void printAuswertungXLS(JasperPrint jp, String nameSuffix) throws JRException, IOException {
		JRXlsExporter exporter = new JRXlsExporter();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, getOutputFolder() + "siegerlisten/" + WettkampfTag.get().getName() + " - " + nameSuffix + ".xls");
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

		exporter.exportReport();

		if (Desktop.isDesktopSupported()) {
			AxtresLogger.debug("Opening Auswertun: " + getOutputFolder() + "siegerlisten/" + WettkampfTag.get().getName() + " - " + nameSuffix + ".xls");

			Desktop.getDesktop().open(new File(getOutputFolder() + "siegerlisten/" + WettkampfTag.get().getName() + " - " + nameSuffix + ".xls"));
		} else {
			AxtresLogger.info("Desktop API is not supported.");
		}
	}

	private static void printAuswertungPDF(JasperPrint jp, String nameSuffix) throws JRException, IOException {
		JasperExportManager.exportReportToPdfFile(jp, getOutputFolder() + "siegerlisten/" + WettkampfTag.get().getName() + " - " + nameSuffix + ".pdf");

		if (Desktop.isDesktopSupported()) {
			AxtresLogger.info("Opening Auswertun: " + getOutputFolder() + "siegerlisten/" + WettkampfTag.get().getName() + " - " + nameSuffix + ".pdf");
			Desktop.getDesktop().open(new File(getOutputFolder() + "siegerlisten/" + WettkampfTag.get().getName() + " - " + nameSuffix + ".pdf"));
		} else {
			AxtresLogger.info("Desktop API is not supported.");
		}
	}

	private static String getNameSuffix(List<? extends Auswertung> lea) {
		String s = "";
		for (Auswertung ea : lea)
			s += "".equals(s) ? ea.getWettkampf().getName() : "_" + ea.getWettkampf().getName();
		return s;
	}

	private static String getNameSuffix(Auswertung a) {
		String s = "";
		s += "".equals(s) ? a.getWettkampf().getName() : "_" + a.getWettkampf().getName();
		return s;
	}

	public static void printUrkunden(EinzelAuswertung auswertung) throws JRException, IOException {
		List<EinzelUrkundeBean> list = new Vector<EinzelUrkundeBean>();
		final EinzelWettkampf w = auswertung.getWettkampf();
		List<Turner> lt = w.getTurner();
		Collections.sort(lt, new Comparator<Turner>() {

			@Override
			public int compare(Turner o1, Turner o2) {
				return o2.getWertungen().getGesamtWertung(w.getGewerteteGeraete(), o2.getAk()).compareTo(o1.getWertungen().getGesamtWertung(w.getGewerteteGeraete(), o2.getAk()));
			}
		});

		for (Turner t : lt) {
			EinzelAuswertungEntry e = auswertung.getEntrys().get(t);
			list.add(new EinzelUrkundeBean(t.getVorname() + " " + t.getName(), t.getVerein(), WettkampfTag.get().getName(), w.getTyp(), Formatter.format(e.getGesamt()), Formatter.format(e.getPlatzierung())));
		}

		Map<String, Object> parameters = new HashMap<String, Object>();

		JasperReport jr = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "standardUrkunde.jrxml");
		JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(list));
		File f = new File(getOutputFolder() + "urkunden");
		if (!f.exists())
			f.mkdirs();
		JasperExportManager.exportReportToPdfFile(jp, getOutputFolder() + "urkunden/" + WettkampfTag.get().getName() + " - " + w.getName() + ".pdf");

		if (Desktop.isDesktopSupported()) {
			AxtresLogger.debug("Opening Auswertun: " + getOutputFolder() + "urkunden/" + WettkampfTag.get().getName() + " - " + w.getName() + ".pdf");

			Desktop.getDesktop().open(new File(getOutputFolder() + "urkunden/" + WettkampfTag.get().getName() + " - " + w.getName() + ".pdf"));
		} else {
			AxtresLogger.info("Desktop API is not supported.");
		}

	}

	public static void printUrkunden(MannschaftsAuswertung auswertung) throws JRException, IOException {
		List<MannschaftsUrkundeBean> list = new Vector<MannschaftsUrkundeBean>();
		final MannschaftsWettkampf w = auswertung.getWettkampf();
		List<MannschaftsAuswertungEntry> entrys = new Vector<MannschaftsAuswertungEntry>(auswertung.getEntrysMannschaft().values());
		Collections.sort(entrys, new Comparator<MannschaftsAuswertungEntry>() {

			@Override
			public int compare(MannschaftsAuswertungEntry o1, MannschaftsAuswertungEntry o2) {
				return o1.getPlatzierung().compareTo(o2.getPlatzierung());
			}
		});

		for (MannschaftsAuswertungEntry mae : entrys) {
			for (Turner tu : mae.getMannschaft().getTurner())
				list.add(new MannschaftsUrkundeBean(tu.toString(), mae.getMannschaft().getVerein(), WettkampfTag.get().getName(), w.getTyp(), Formatter.format(mae.getErgebnis().getGesamt()), Formatter.format(mae.getPlatzierung())));
		}

		Map<String, Object> parameters = new HashMap<String, Object>();

		JasperReport jr = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "standardMannschaftsUrkunde.jrxml");
		JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(list));
		File f = new File(getOutputFolder() + "urkunden");
		if (!f.exists())
			f.mkdirs();
		JasperExportManager.exportReportToPdfFile(jp, getOutputFolder() + "urkunden/" + WettkampfTag.get().getName() + " - " + w.getName() + ".pdf");

		AxtresLogger.debug("Opening Auswertun: " + getOutputFolder() + "urkunden/" + WettkampfTag.get().getName() + " - " + w.getName() + ".pdf");

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(new File(getOutputFolder() + "urkunden/" + WettkampfTag.get().getName() + " - " + w.getName() + ".pdf"));
		} else {
			AxtresLogger.info("Desktop API is not supported.");
		}
	}

	public static List<TabellenEintrag> getRundenWKAuswertung(String veranstaltung, List<MannschaftsWettkampf> wettkaempfe) {
		List<MannschaftsAuswertung> lma = new Vector<MannschaftsAuswertung>();
		HashMap<String, TabellenEintrag> tabelle = new HashMap<String, TabellenEintrag>();
		for (MannschaftsWettkampf w : wettkaempfe)
			lma.add(AuswertungService.getAuswertung(w));

		for (MannschaftsAuswertung a : lma) {
			for (Mannschaft m : a.getWettkampf().getMannschaften()) {
				TabellenEintrag t;
				if (tabelle.containsKey(m.getName())) {
					t = tabelle.get(m.getName());
				} else {
					t = new TabellenEintrag(m.getName());
					tabelle.put(m.getName(), t);
				}

				MannschaftsAuswertungEntry e = a.getEntrysMannschaft().get(m);
				t.setVeranstaltung(veranstaltung);
				t.setWettkampf(a.getWettkampf().getTyp() + " " + a.getWettkampf().getJahrgaenge());
				t.setPunkte(t.getPunkte() + e.getErgebnis().getGesamt());
				t.setTabellenPunkte(t.getTabellenPunkte() + getTabellenPunkte(a.getWettkampf().getMannschaften().size(), e.getPlatzierung(), a.getEntrysMannschaft().values()));
				t.setGegenPunkte(t.getGegenPunkte() + getGegenPunkte(a.getWettkampf().getMannschaften().size(), e.getPlatzierung(), a.getEntrysMannschaft().values()));
				t.setTurner(m.getTurner());
			}
		}

		List<TabellenEintrag> tab = new Vector<TabellenEintrag>(tabelle.values());

		Collections.sort(tab);
		addPlatzierung(tab);
		return tab;
	}

	private static void addPlatzierung(List<TabellenEintrag> tab) {
		int p = 0;
		TabellenEintrag t = null;
		for (int i = 0; i < tab.size(); i++) {
			t = tab.get(i);
			if (i > 0 && t.getTabellenPunkte().intValue() == tab.get(i - 1).getTabellenPunkte().intValue() && t.getGegenPunkte().intValue() == tab.get(i - 1).getGegenPunkte().intValue() && t.getPunkte().intValue() == tab.get(i - 1).getPunkte().intValue()) {
				t.setPlatzierung(p++);
			} else {
				t.setPlatzierung(++p);
			}
		}
	}

	private static Integer getTabellenPunkte(int teilnehmer, int platzierung, Collection<MannschaftsAuswertungEntry> lma) {
		// Wenn nur eine Mannschaft Teilnimmt, dann gibts immer 2 Siegpunkte
		if (lma.size() == 1)
			return 2;
		int tabPunkte = getTieferePlatzierungen(platzierung, lma) * 2;
		tabPunkte += getGleichePlatzierungen(platzierung, lma);
		return tabPunkte;
	}

	private static int getHoeherePlatzierungen(int platzierung, Collection<MannschaftsAuswertungEntry> lma) {
		int count = 0;
		for (MannschaftsAuswertungEntry a : lma) {
			if (a.getPlatzierung() < platzierung)
				count++;
		}
		return count;
	}

	private static int getTieferePlatzierungen(int platzierung, Collection<MannschaftsAuswertungEntry> lma) {
		int count = 0;
		for (MannschaftsAuswertungEntry a : lma) {
			if (a.getPlatzierung() > platzierung)
				count++;
		}
		return count;
	}

	private static int getGleichePlatzierungen(int platzierung, Collection<MannschaftsAuswertungEntry> lma) {
		int count = 0;
		for (MannschaftsAuswertungEntry a : lma) {
			if (a.getPlatzierung() == platzierung)
				count++;
		}
		// MAn selbst hat immer die gleiche Platzierung, daher muss einer
		// abgezogen werden.
		return count - 1;
	}

	private static Integer getGegenPunkte(int teilnehmer, int platzierung, Collection<MannschaftsAuswertungEntry> lma) {
		// Wenn nur eine Mannschaft Teilnimmt, dann gibts immer 0 Gegenpunkte
		if (lma.size() == 1)
			return 0;
		int tabPunkte = getHoeherePlatzierungen(platzierung, lma) * 2;
		tabPunkte += getGleichePlatzierungen(platzierung, lma);
		return tabPunkte;
	}

	public static void printUrkunden(List<TabellenEintrag> tabelle) throws JRException, IOException {

		JasperReport jr = JasperCompileManager.compileReport(WeKaUtil.enshureEnding(AppProperties.getProperty("TEMPLATE_PATH", "templates"), "/") + "standardRundenUrkunde.jrxml");

		List<TabellenEintragTurner> tabellePrint = new ArrayList<TabellenEintragTurner>();
		for (TabellenEintrag te : tabelle) {
			for (int i = 0; i < te.getTurner().size(); i++) {
				tabellePrint.add(te.getTurnerEintrag(i));
			}
		}

		JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap<String, Object>(), new JRBeanCollectionDataSource(tabellePrint));
		File f = new File(getOutputFolder() + "urkunden");
		if (!f.exists())
			f.mkdirs();
		JasperExportManager.exportReportToPdfFile(jp, getOutputFolder() + "urkunden/" + WettkampfTag.get().getName() + " - " + tabelle.iterator().next().getWettkampf() + ".pdf");

		AxtresLogger.debug("Opening Auswertun: " + getOutputFolder() + "urkunden/" + WettkampfTag.get().getName() + " - " + tabelle.iterator().next().getWettkampf() + ".pdf");

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(new File(getOutputFolder() + "urkunden/" + WettkampfTag.get().getName() + " - " + tabelle.iterator().next().getWettkampf() + ".pdf"));
		} else {
			AxtresLogger.info("Desktop API is not supported.");
		}
	}

	// private Auswertung getAuswertung(MannschaftsWettkampf w) {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
