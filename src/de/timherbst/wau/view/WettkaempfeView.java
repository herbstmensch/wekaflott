package de.timherbst.wau.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.gpl.JSplitButton.JSplitButton;
import org.gpl.JSplitButton.action.SplitButtonActionListener;
import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.auswertung.Auswertung;
import de.timherbst.wau.domain.auswertung.EinzelAuswertung;
import de.timherbst.wau.domain.auswertung.MannschaftsAuswertung;
import de.timherbst.wau.domain.wertungen.Wertung;
import de.timherbst.wau.domain.wertungen.Wertung.Wertungsmodus;
import de.timherbst.wau.domain.wettkampf.EinzelWettkampf;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.domain.wettkampf.Wettkampf;
import de.timherbst.wau.service.AuswertungService;
import de.timherbst.wau.view.auswertung.EinzelAuswertungWindow;
import de.timherbst.wau.view.auswertung.MannschaftsAuswertungWindow;
import de.timherbst.wau.view.components.ExcelAdapter;
import de.timherbst.wau.view.models.WettkaempfeTableModel;

public class WettkaempfeView extends JPanel {

	private static final long serialVersionUID = -1191559939329049767L;
	JTable table;
	JPopupMenu tablePopUp;
	Action deleteWettkampf;
	Action showAuswertung;
	Action printAuswertungPDF;
	Action printAuswertungXLS;
	Action printUrkunden;
	Action openErfassung;

	public WettkaempfeView() {
		SwingJavaBuilder.build(this);
		table.setModel(new WettkaempfeTableModel());
		table.setDefaultEditor(Wertungsmodus.class, new DefaultCellEditor(new JComboBox<Wertungsmodus>(Wertung.getWertungsmodi())));
		new ExcelAdapter(table);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setAutoCreateColumnsFromModel(false);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedColumns().length > 0) {
					deleteWettkampf.setEnabled(true);
					showAuswertung.setEnabled(true);
					printAuswertungPDF.setEnabled(true);
					printAuswertungXLS.setEnabled(true);
					printUrkunden.setEnabled(true);
					openErfassung.setEnabled(true);
				} else {
					deleteWettkampf.setEnabled(false);
					showAuswertung.setEnabled(false);
					printAuswertungPDF.setEnabled(false);
					printAuswertungXLS.setEnabled(false);
					printUrkunden.setEnabled(false);
					openErfassung.setEnabled(false);
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					tablePopUp.show(e.getComponent(), e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					tablePopUp.show(e.getComponent(), e.getX(), e.getY());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					openErfassung();
				}
			}
		});

		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
					deleteWettkampf();
				super.keyReleased(e);
			}
		});
		// AutofitTableColumns.autoResizeTable(table, true);
	}

	public void newEinzelwettkampf() {
		WettkampfTag.get().addWettkampf(new EinzelWettkampf("Wettkampf " + (WettkampfTag.get().getWettkaempfe().size() + 1)));
	}

	public void newMannschaftswettkampf() {
		WettkampfTag.get().addWettkampf(new MannschaftsWettkampf("Wettkampf " + (WettkampfTag.get().getWettkaempfe().size() + 1)));
	}

	public void deleteWettkampf() {
		if (table.getSelectedRowCount() == 0)
			return;
		if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie die ausgewählten Wettkämpfe löschen wollen?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;
		List<Wettkampf> wk = new Vector<Wettkampf>();
		for (int i : table.getSelectedRows()) {
			Wettkampf w = (Wettkampf) table.getValueAt(i, -1);
			wk.add(w);
		}
		for (Wettkampf w : wk)
			WettkampfTag.get().removeWettkampf(w);

	}

	private void openErfassung() {
		for (int i : table.getSelectedRows())
			Application.openErfassung(table.getValueAt(i, -1));
	}

	public void showAuswertung() {
		if (table.getSelectedRowCount() == 0)
			return;
		List<EinzelWettkampf> lewk = new Vector<EinzelWettkampf>();
		List<MannschaftsWettkampf> lmwk = new Vector<MannschaftsWettkampf>();
		for (int i : table.getSelectedRows()) {
			Wettkampf w = (Wettkampf) table.getValueAt(i, -1);
			if (w instanceof EinzelWettkampf)
				lewk.add((EinzelWettkampf) w);
			if (w instanceof MannschaftsWettkampf)
				lmwk.add((MannschaftsWettkampf) w);
		}
		for (EinzelWettkampf w : lewk) {
			openAuswertungWindow(AuswertungService.getAuswertung(w));
		}
		for (MannschaftsWettkampf w : lmwk) {
			openAuswertungWindow(AuswertungService.getAuswertung(w));
		}
	}

	public void printAuswertungPDF() {
		if (table.getSelectedRowCount() == 0)
			return;
		List<EinzelWettkampf> lewk = new Vector<EinzelWettkampf>();
		List<MannschaftsWettkampf> lmwk = new Vector<MannschaftsWettkampf>();
		for (int i : table.getSelectedRows()) {
			Wettkampf w = (Wettkampf) table.getValueAt(i, -1);
			if (w instanceof EinzelWettkampf)
				lewk.add((EinzelWettkampf) w);
			if (w instanceof MannschaftsWettkampf)
				lmwk.add((MannschaftsWettkampf) w);
		}
		List<EinzelAuswertung> lea = new Vector<EinzelAuswertung>();
		List<MannschaftsAuswertung> lma = new Vector<MannschaftsAuswertung>();
		for (EinzelWettkampf w : lewk) {
			lea.add(AuswertungService.getAuswertung(w));
		}
		for (MannschaftsWettkampf w : lmwk) {
			lma.add(AuswertungService.getAuswertung(w));
		}
		try {
			AuswertungService.printEinzelAuswertung(lea, false);
		} catch (Exception e) {

			AxtresLogger.error("Beim erstellen der Auswertung ist ein Fehler aufgetreten.", e);
			JOptionPane.showMessageDialog(this, "Beim erstellen der Auswertung ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		try {
			AuswertungService.printMannschaftsAuswertung(lma, false);
		} catch (Exception e) {
			AxtresLogger.error("Beim erstellen der Auswertung ist ein Fehler aufgetreten.", e);
			JOptionPane.showMessageDialog(this, "Beim erstellen der Auswertung ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void printAuswertungXLS() {
		if (table.getSelectedRowCount() == 0)
			return;
		List<EinzelWettkampf> lewk = new Vector<EinzelWettkampf>();
		List<MannschaftsWettkampf> lmwk = new Vector<MannschaftsWettkampf>();
		for (int i : table.getSelectedRows()) {
			Wettkampf w = (Wettkampf) table.getValueAt(i, -1);
			if (w instanceof EinzelWettkampf)
				lewk.add((EinzelWettkampf) w);
			if (w instanceof MannschaftsWettkampf)
				lmwk.add((MannschaftsWettkampf) w);
		}
		List<EinzelAuswertung> lea = new Vector<EinzelAuswertung>();
		List<MannschaftsAuswertung> lma = new Vector<MannschaftsAuswertung>();
		for (EinzelWettkampf w : lewk) {
			lea.add(AuswertungService.getAuswertung(w));
		}
		for (MannschaftsWettkampf w : lmwk) {
			lma.add(AuswertungService.getAuswertung(w));
		}
		try {
			AuswertungService.printEinzelAuswertung(lea, true);
		} catch (Exception e) {
			AxtresLogger.error("Beim erstellen der Auswertung ist ein Fehler aufgetreten.", e);
			JOptionPane.showMessageDialog(this, "Beim erstellen der Auswertung ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		try {
			AuswertungService.printMannschaftsAuswertung(lma, true);
		} catch (Exception e) {
			AxtresLogger.error("Beim erstellen der Auswertung ist ein Fehler aufgetreten.", e);
			JOptionPane.showMessageDialog(this, "Beim erstellen der Auswertung ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void printUrkunden() {
		if (table.getSelectedRowCount() == 0)
			return;
		List<EinzelWettkampf> lewk = new Vector<EinzelWettkampf>();
		List<MannschaftsWettkampf> lmwk = new Vector<MannschaftsWettkampf>();
		for (int i : table.getSelectedRows()) {
			Wettkampf w = (Wettkampf) table.getValueAt(i, -1);
			if (w instanceof EinzelWettkampf)
				lewk.add((EinzelWettkampf) w);
			if (w instanceof MannschaftsWettkampf)
				lmwk.add((MannschaftsWettkampf) w);
		}
		boolean ok = true;
		for (EinzelWettkampf w : lewk) {
			try {
				AuswertungService.printUrkunden(AuswertungService.getAuswertung(w));
			} catch (Exception e) {
				AxtresLogger.error("Beim erstellen der Urkunden ist ein Fehler aufgetreten.", e);
				ok = false;
			}
		}
		for (MannschaftsWettkampf w : lmwk) {
			try {
				AuswertungService.printUrkunden(AuswertungService.getAuswertung(w));
			} catch (Exception e) {
				AxtresLogger.error("Beim erstellen der Urkunden ist ein Fehler aufgetreten.", e);
				ok = false;
			}
		}
		if (!ok)
			JOptionPane.showMessageDialog(this, "Beim erstellen der Urkunden ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
	}

	private void openAuswertungWindow(Auswertung auswertung) {
		if (auswertung instanceof EinzelAuswertung)
			new EinzelAuswertungWindow((EinzelAuswertung) auswertung).setVisible(true);
		if (auswertung instanceof MannschaftsAuswertung)
			new MannschaftsAuswertungWindow((MannschaftsAuswertung) auswertung, AuswertungService.getEinzelAuswertungEntrys(((MannschaftsAuswertung) auswertung).getWettkampf().getTurner(), ((MannschaftsAuswertung) auswertung).getWettkampf().getGewerteteGeraete())).setVisible(true);
	}

}
