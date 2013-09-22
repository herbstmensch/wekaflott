package de.timherbst.wau.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javabuilders.swing.SwingJavaBuilder;

import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.riege.MannschaftsRiege;
import de.timherbst.wau.domain.riege.Riege;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.domain.wettkampf.Wettkampf;
import de.timherbst.wau.view.components.ExcelAdapter;
import de.timherbst.wau.view.lookup.SelectObjectDialog;
import de.timherbst.wau.view.models.MannschaftenTableModel;

public class MannschaftenView extends JPanel {

	private static final long serialVersionUID = 6687932600402445622L;
	JTable table;
	JPopupMenu tablePopUp;
	JButton btnZuWettkampf;
	JButton btnZuRiege;
	Action zuWettkampf;
	Action zuRiege;
	Action deleteMannschaft;
	Action openErfassung;

	public MannschaftenView() {
		SwingJavaBuilder.build(this);
		table.setModel(new MannschaftenTableModel());
		new ExcelAdapter(table);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setAutoCreateColumnsFromModel(false);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedColumns().length > 0) {
					zuWettkampf.setEnabled(true);
					zuRiege.setEnabled(true);
					deleteMannschaft.setEnabled(true);
					openErfassung.setEnabled(true);
				} else {
					zuWettkampf.setEnabled(false);
					zuRiege.setEnabled(false);
					deleteMannschaft.setEnabled(false);
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
					deleteMannschaft();
				super.keyReleased(e);
			}
		});
	}

	public void newMannschaft() {
		WettkampfTag.get().addMannschaft(new Mannschaft("Mannschaft " + (WettkampfTag.get().getMannschaften().size() + 1)));
	}

	public void deleteMannschaft() {
		if (table.getSelectedRowCount() == 0)
			return;
		if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie die ausgewählten Mannschaften löschen wollen?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;
		List<Mannschaft> ms = new Vector<Mannschaft>();
		for (int i : table.getSelectedRows()) {
			Mannschaft m = (Mannschaft) table.getValueAt(i, -1);
			ms.add(m);
		}
		for (Mannschaft m : ms)
			WettkampfTag.get().removeMannschaft(m);
	}

	private void openErfassung() {
		for (int i : table.getSelectedRows())
			Application.openErfassung(table.getValueAt(i, -1));
	}

	@SuppressWarnings({ "unused", "serial" })
	private void zuWettkampf() {
		List<MannschaftsWettkampf> l = new Vector<MannschaftsWettkampf>();
		for (Wettkampf w : WettkampfTag.get().getWettkaempfe())
			if (w instanceof MannschaftsWettkampf) {
				l.add((MannschaftsWettkampf) w);
			}

		new SelectObjectDialog<MannschaftsWettkampf>(l, true, "Wettkampf wählen...") {
			@Override
			public void onSelect(MannschaftsWettkampf mw) {

				if (mw == null)
					if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie die Zuordnung entfernen wollen?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
						return;

				for (int i : table.getSelectedRows())
					if (mw == null) {
						if (((Mannschaft) table.getValueAt(i, -1)).getWettkampf() != null)
							((Mannschaft) table.getValueAt(i, -1)).getWettkampf().getMannschaften().remove((Mannschaft) table.getValueAt(i, -1));
						((Mannschaft) table.getValueAt(i, -1)).setWettkampf(null);
						((Mannschaft) table.getValueAt(i, -1)).setRiege(null);
					} else
						mw.addMannschaft(((Mannschaft) table.getValueAt(i, -1)));
			}
		}.show(btnZuWettkampf);

	}

	@SuppressWarnings({ "unused", "serial" })
	private void zuRiege() {
		List<MannschaftsRiege> l = new Vector<MannschaftsRiege>();
		for (Riege r : WettkampfTag.get().getRiegen())
			if (r instanceof MannschaftsRiege) {
				l.add((MannschaftsRiege) r);
			}

		new SelectObjectDialog<MannschaftsRiege>(l, true, "Riege wählen...") {
			@Override
			public void onSelect(MannschaftsRiege mr) {

				if (mr == null)
					if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie die Zuordnung entfernen wollen?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
						return;

				for (int i : table.getSelectedRows())
					if (mr == null) {
						if (((Mannschaft) table.getValueAt(i, -1)).getRiege() != null)
							((MannschaftsRiege) ((Mannschaft) table.getValueAt(i, -1)).getRiege()).getMannschaften().remove((Mannschaft) table.getValueAt(i, -1));
						((Mannschaft) table.getValueAt(i, -1)).setRiege(null);
					} else
						mr.addMannschaft(((Mannschaft) table.getValueAt(i, -1)));
			}
		}.show(btnZuRiege);

	}

}
