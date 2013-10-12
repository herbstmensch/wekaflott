package de.timherbst.wau.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import de.axtres.logging.main.AxtresLogger;
import de.axtres.util.AppProperties;
import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.riege.EinzelRiege;
import de.timherbst.wau.domain.riege.MannschaftsRiege;
import de.timherbst.wau.domain.riege.Riege;
import de.timherbst.wau.domain.wertungen.CdPWertung;
import de.timherbst.wau.domain.wertungen.PStufenWertung;
import de.timherbst.wau.domain.wertungen.Wertung;
import de.timherbst.wau.domain.wertungen.Wertungen;
import de.timherbst.wau.domain.wettkampf.EinzelWettkampf;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.domain.wettkampf.Wettkampf;
import de.timherbst.wau.util.WeKaUtil;

public class StorageService {

	public static String filename = null;

	public static void saveWettkampftag(String filename, boolean isAutosave) throws IOException {
		FileWriter fw = new FileWriter(filename);

		getXStream().marshal(WettkampfTag.get(), new PrettyPrintWriter(fw));

		if (!isAutosave)
			StorageService.filename = filename;
		Application.getMainFrame().setDirty(false);
		if (!isAutosave)
			Application.getMainFrame().setStatus(filename + " gespeichert (" + new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())) + ")");
		else
			Application.getMainFrame().setStatus("Autosave '" + filename + "' um " + new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
	}

	private static XStream getXStream() {
		XStream xstream = new XStream(new StaxDriver());
		xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
		xstream.processAnnotations(new Class[] { WettkampfTag.class, Turner.class, Mannschaft.class, Riege.class, EinzelRiege.class, MannschaftsRiege.class, Wettkampf.class, EinzelWettkampf.class, MannschaftsWettkampf.class, Wertung.class, PStufenWertung.class, CdPWertung.class, Wertungen.class });
		return xstream;
	}

	public static void loadWettkampftag(String filename) throws IOException, ClassNotFoundException {

		WettkampfTag.set(getWettkampftagFromFile(filename));
		StorageService.filename = filename;
		if (Application.getMainFrame() != null)
			Application.getMainFrame().setDirty(false);
		Application.getMainFrame().setStatus(filename + " geladen");
	}

	public static WettkampfTag getWettkampftagFromFile(String filename) throws IOException, ClassNotFoundException {
		FileReader fr = null;

		BufferedReader br = null;

		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);

			WettkampfTag wt = (WettkampfTag) getXStream().fromXML(br);

			return wt;
		} finally {
			br.close();
		}
	}

	public static void newWettkampftag() {
		WettkampfTag.set(WettkampfTag.newInstance());
		filename = null;
		if (Application.getMainFrame() != null)
			Application.getMainFrame().setDirty(false);
		Application.getMainFrame().setStatus("Neuer Wettkampftag");
	}

	public static void autoSave() {
		String autoSaveFilename;
		if (filename == null)
			autoSaveFilename = WeKaUtil.enshureEnding(AppProperties.getProperty("STD_AUTOSAVE_PATH", ""), "/") + "autosave.aswkt";
		else
			autoSaveFilename = filename.replaceAll(".wkt", "") + ".aswkt";
		try {
			saveWettkampftag(autoSaveFilename, true);
		} catch (IOException e) {
			AxtresLogger.error(e.getMessage(), e);
			Application.getMainFrame().setStatus("Autosave fehlgeschlagen: " + e.getMessage());
		}
	}

	public class AutoSaveThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(new Integer(AppProperties.getProperty("AUTOSAVE_INTERVAL_SEC")).intValue() * 1000);
				} catch (InterruptedException e) {
					AxtresLogger.error("AutoSaveThread", e);
				}
				StorageService.autoSave();
			}
		}
	}

}
