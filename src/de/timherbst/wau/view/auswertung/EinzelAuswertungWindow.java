package de.timherbst.wau.view.auswertung;

import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.auswertung.EinzelAuswertung;
import de.timherbst.wau.domain.auswertung.EinzelAuswertungEntry;
import de.timherbst.wau.domain.wertungen.Wertung;
import de.timherbst.wau.service.AuswertungService;
import de.timherbst.wau.view.wertungen.WertungCellRenderer;

public class EinzelAuswertungWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel wkName;
	private JLabel wkGeraete;
	private JLabel wkKlasse;
	private JLabel wkJahrgaenge;
	private EinzelAuswertung auswertung;

	private static final NumberFormat nf = NumberFormat.getNumberInstance();

	static {
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		nf.setMaximumIntegerDigits(2);
	}

	public EinzelAuswertungWindow(EinzelAuswertung auswertung) {
		SwingJavaBuilder.build(this);
		setTitle("Auswertung - " + auswertung.getWettkampf().getName());
		this.auswertung = auswertung;
		table.setDefaultRenderer(Wertung.class, new WertungCellRenderer());
		table.setModel(getModel(auswertung));
		wkName.setText(auswertung.getWettkampf().getName());
		wkGeraete.setText(auswertung.getWettkampf().getGeraeteText());
		wkKlasse.setText(auswertung.getWettkampf().getTyp());
		wkJahrgaenge.setText(auswertung.getWettkampf().getJahrgaenge());
	}

	public void printAuswertungPDF() {
		try {
			AuswertungService.printEinzelAuswertung(Arrays.asList(auswertung), false);
		} catch (Exception e) {
			AxtresLogger.error("printAuswertungPDF", e);
			JOptionPane.showMessageDialog(this, "Beim erstellen der Auswertung ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void printAuswertungXLS() {
		try {
			AuswertungService.printEinzelAuswertung(Arrays.asList(auswertung), true);
		} catch (Exception e) {
			AxtresLogger.error("printAuswertungXLS", e);
			JOptionPane.showMessageDialog(this, "Beim erstellen der Auswertung ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void printUrkunden() {
		try {
			AuswertungService.printUrkunden(auswertung);
		} catch (Exception e) {
			AxtresLogger.error("printUrkunden", e);
			JOptionPane.showMessageDialog(this, "Beim erstellen der Urkunden ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static TableModel getModel(final EinzelAuswertung auswertung) {
		TableModel t = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public int getRowCount() {
				return auswertung.getWettkampf().getTurner().size();
			}

			@Override
			public int getColumnCount() {
				return 12;
			}

			@Override
			public Object getValueAt(int row, int column) {
				Turner t = auswertung.getWettkampf().getTurner().get(row);
				EinzelAuswertungEntry ae = auswertung.getEntrys().get(t);

				switch (column) {
				case 0:
					return t.getName();
				case 1:
					return t.getVorname();
				case 2:
					return t.getVerein();
				case 3:
					return t.getJahrgang();
				case 4:
					return t.getWertungen().getBoden();
				case 5:
					return t.getWertungen().getSeitpferd();
				case 6:
					return t.getWertungen().getRinge();
				case 7:
					return t.getWertungen().getSprung();
				case 8:
					return t.getWertungen().getBarren();
				case 9:
					return t.getWertungen().getReck();
				case 10:
					return ae.getGesamt();
				case 11:
					return ae.getPlatzierung();
				}
				return super.getValueAt(row, column);
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex < 4)
					return String.class;
				if (columnIndex < 10)
					return Wertung.class;
				if (columnIndex < 11)
					return Double.class;
				return Integer.class;
			}

			@Override
			public String getColumnName(int column) {
				return Arrays.asList("Name", "Vorname", "Verein", "Jahrgang", "Boden", "Seitpferd", "Ringe", "Sprung", "Barren", "Reck", "Gesamt", "Platz").get(column);
			}
		};
		return t;
	}

}
