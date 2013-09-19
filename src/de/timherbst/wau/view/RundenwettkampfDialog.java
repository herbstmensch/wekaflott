package de.timherbst.wau.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.auswertung.TabellenEintrag;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.domain.wettkampf.Wettkampf;
import de.timherbst.wau.service.AuswertungService;
import de.timherbst.wau.service.StorageService;

@SuppressWarnings("serial")
public class RundenwettkampfDialog extends JDialog {

	JTable tableWettkampftage;
	JTable tableWettkaempfe;
	JTable tableTabelle;
	JTextField tfVeranstaltung;
	List<WettkampfTag> tage = new Vector<WettkampfTag>();
	HashMap<String, List<MannschaftsWettkampf>> wettkaempfe = new HashMap<String, List<MannschaftsWettkampf>>();
	List<TabellenEintrag> tabelle;
	WettkampfTageModel wettkampftagemodel = new WettkampfTageModel();
	WettkaempfeModel wettkaempfemodel = new WettkaempfeModel();
	JSplitPane split1;
	JSplitPane split2;

	public RundenwettkampfDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(false);
		SwingJavaBuilder.build(this);
		split1.setDividerLocation(170);
		split2.setDividerLocation(400);
		tfVeranstaltung.setText(WettkampfTag.get().getName());
		tableWettkampftage.setModel(wettkampftagemodel);
		tableWettkaempfe.setModel(wettkaempfemodel);
		tableWettkaempfe.setRowSelectionAllowed(true);
		tableWettkaempfe.setCellSelectionEnabled(false);
		tableWettkaempfe.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (tableWettkaempfe.getSelectedColumn() != -1) {
					updateTabelle((List<MannschaftsWettkampf>) tableWettkaempfe.getValueAt(tableWettkaempfe.getSelectedRow(), -1));
				}
			}
		});
		tableWettkaempfe.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addEscapeListener(this);
	}
	
	public static void addEscapeListener(final JDialog dialog) {
	    ActionListener escListener = new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            dialog.setVisible(false);
	        }
	    };

	    dialog.getRootPane().registerKeyboardAction(escListener,
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
	            JComponent.WHEN_IN_FOCUSED_WINDOW);

	}

	private void updateTabelle(List<MannschaftsWettkampf> wettkaempfe) {
		tabelle = AuswertungService.getRundenWKAuswertung(tfVeranstaltung.getText(), wettkaempfe);
		tableTabelle.setModel(new TabellenModel(tabelle));
	}

	// @SuppressWarnings("unused")
	// private void printTabellePDF() {
	// AuswertungService.printRundenAuswertung(tabelle);
	// }

	@SuppressWarnings("unused")
	private void printUrkunden() {
		try {
			AuswertungService.printUrkunden(tabelle);
		} catch (Exception e) {
			AxtresLogger.error(e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Beim erstellen der Urkunden ist ein Fehler aufgetreten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("unused")
	private void addWKTag() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Wettkampftag Dateien", "wkt"));
		fc.setAcceptAllFileFilterUsed(false);

		int state = fc.showOpenDialog(this);

		if (state == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				WettkampfTag tag = StorageService.getWettkampftagFromFile(file.getAbsolutePath());
				addWettkampftag(tag);
			} catch (Throwable t) {
				JOptionPane.showMessageDialog(this, "Beim öffnen der Datei " + file.getAbsolutePath() + " ist ein Fehler aufgetreten:\n\n" + t.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
				AxtresLogger.error(t.getMessage(), t);
			}
		}
	}

	public void addWettkampftag(WettkampfTag tag) {
		tage.add(tag);
		for (Wettkampf w : tag.getWettkaempfe()) {
			if (w instanceof MannschaftsWettkampf)
				addWettkampf((MannschaftsWettkampf) w);
		}
		wettkampftagemodel.fireTableDataChanged();
	}

	private void addWettkampf(MannschaftsWettkampf w) {
		if (wettkaempfe.containsKey(w.getName())) {
			wettkaempfe.get(w.getName()).add(w);
		} else {
			List<MannschaftsWettkampf> l = new Vector<MannschaftsWettkampf>();
			l.add(w);
			wettkaempfe.put(w.getName(), l);
		}
		wettkaempfemodel.fireTableDataChanged();
	}

	@SuppressWarnings("unused")
	private void removeWKTag() {

	}

	private class WettkampfTageModel extends DefaultTableModel {

		@Override
		public int getRowCount() {
			return tage != null ? tage.size() : 0;
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return Arrays.asList("Wettkampftag", "Anz. Wettkämpfe").get(columnIndex);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case -1:
				return tage.get(rowIndex);
			case 0:
				return tage.get(rowIndex).getName();
			case 1:
				return tage.get(rowIndex).getWettkaempfe().size() + "";
			}
			return null;
		}

	}

	private class WettkaempfeModel extends DefaultTableModel {

		@Override
		public int getRowCount() {
			return wettkaempfe != null ? wettkaempfe.size() : 0;
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return Arrays.asList("Wettkampf", "Begegnungen").get(columnIndex);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex < 0)
				return null;
			String wettkampf = wettkaempfe.keySet().toArray(new String[0])[rowIndex];
			switch (columnIndex) {
			case -1:
				return wettkaempfe.get(wettkampf);
			case 0:
				return wettkampf;
			case 1:
				return wettkaempfe.get(wettkampf).size() + "";
			}
			return null;
		}
	}

	public class TabellenModel extends AbstractTableModel {

		List<TabellenEintrag> tabelle;

		public TabellenModel(List<TabellenEintrag> tabelle) {
			this.tabelle = tabelle;
		}

		@Override
		public int getRowCount() {
			return tabelle.size();
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return Arrays.asList("Mannschaft", "Tabellenpunkte", "Gesamtpunkte", "Platzierung").get(columnIndex);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex < 0)
				return null;
			switch (columnIndex) {
			case -1:
				return tabelle.get(rowIndex);
			case 0:
				return tabelle.get(rowIndex).getMannschaft();
			case 1:
				return tabelle.get(rowIndex).getTabellenPunkte() + ":" + tabelle.get(rowIndex).getGegenPunkte();
			case 2:
				return tabelle.get(rowIndex).getPunkte();
			case 3:
				return tabelle.get(rowIndex).getPlatzierung();
			}
			return null;
		}

	}

}
