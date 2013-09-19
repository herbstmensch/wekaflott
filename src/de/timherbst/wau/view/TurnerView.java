package de.timherbst.wau.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.riege.EinzelRiege;
import de.timherbst.wau.domain.riege.MannschaftsRiege;
import de.timherbst.wau.domain.riege.Riege;
import de.timherbst.wau.domain.wettkampf.EinzelWettkampf;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.domain.wettkampf.Wettkampf;
import de.timherbst.wau.exceptions.HasMannschaftException;
import de.timherbst.wau.exceptions.HasWettkampfException;
import de.timherbst.wau.view.components.ExcelAdapter;
import de.timherbst.wau.view.lookup.SelectObjectDialog;
import de.timherbst.wau.view.models.TurnerTableModel;

public class TurnerView extends JPanel {

	private static final long serialVersionUID = 8748441873019286762L;
	JTable table;
	JScrollPane scrollPane1;
	JPopupMenu tablePopUp;
	JButton btnZuWettkampf;
	JButton btnZuRiege;
	JButton btnZuMannschaft;
	Action deleteTurner;
	Action zuMannschaft;
	Action zuRiege;
	Action zuWettkampf;

	public TurnerView() {
		SwingJavaBuilder.build(this);
		try {
			((JComponent) table.getDefaultRenderer(Boolean.class)).setOpaque(true);
		} catch (Throwable t) {
			// Funktioniert nicht mit jedem LaF
		}
		table.setModel(new TurnerTableModel());
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
					zuWettkampf.setEnabled(true);
					zuRiege.setEnabled(true);
					zuMannschaft.setEnabled(true);
					deleteTurner.setEnabled(true);
				} else {
					zuWettkampf.setEnabled(false);
					zuRiege.setEnabled(false);
					zuMannschaft.setEnabled(false);
					deleteTurner.setEnabled(false);
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
		});
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
					deleteTurner();
				super.keyReleased(e);
			}
		});
	}

	public void newTurner() {
		WettkampfTag.get().addTurner(new Turner());
	}

	public void deleteTurner() {
		if (table.getSelectedRowCount() == 0)
			return;
		if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie die ausgewählten Turner löschen wollen?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;
		List<Turner> tn = new Vector<Turner>();
		for (int i : table.getSelectedRows()) {
			Turner t = (Turner) table.getValueAt(i, -1);
			tn.add(t);
		}
		for (Turner t : tn)
			WettkampfTag.get().removeTurner(t);
	}

	@SuppressWarnings({ "unused", "serial" })
	private void zuMannschaft() {

		new SelectObjectDialog<Mannschaft>(WettkampfTag.get().getMannschaften(), true, "Mannschaft wählen...") {

			@Override
			public void onSelect(Mannschaft m) {

				if (m == null)
					if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie die Zuordnung entfernen wollen?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
						return;

				boolean ok = true;
				for (int i : table.getSelectedRows()) {
					try {
						if (m == null) {
							Turner t = ((Turner) table.getValueAt(i, -1));

							if (t.getMannschaft() != null)
								t.getMannschaft().removeTurner(t);
						} else {
							m.addTurner(((Turner) table.getValueAt(i, -1)));
						}
					} catch (HasWettkampfException e) {

						AxtresLogger.error("Turner konnte der Mannschaft nicht zugeordnet werden", e);
						ok = false;
					}
				}
				if (!ok)
					JOptionPane.showMessageDialog(this, "Mind. 1 Turner konnte der gewählten Mannschaft nicht hinzugefügt werden.\n\nWahrscheinlich ist er schon einem Wettkampf zugeordnet.", "Warnung", JOptionPane.WARNING_MESSAGE);
			}

		}.show(btnZuMannschaft);

	}

	@SuppressWarnings({ "unused", "serial" })
	private void zuWettkampf() {
		List<EinzelWettkampf> l = new Vector<EinzelWettkampf>();
		for (Wettkampf w : WettkampfTag.get().getWettkaempfe())
			if (w instanceof EinzelWettkampf) {
				l.add((EinzelWettkampf) w);
			}

		new SelectObjectDialog<EinzelWettkampf>(l, true, "Wettkampf wählen...") {
			@Override
			public void onSelect(EinzelWettkampf ew) {

				if (ew == null)
					if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie die Zuordnung entfernen wollen?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
						return;

				boolean ok = true;
				for (int i : table.getSelectedRows())
					try {
						if (ew == null) {
							Turner t = ((Turner) table.getValueAt(i, -1));

							if (t.getWettkampf() instanceof EinzelWettkampf)
								((EinzelWettkampf) t.getWettkampf()).removeTurner(t);
							else if (t.getWettkampf() instanceof MannschaftsWettkampf)
								throw new HasMannschaftException();
						} else {
							ew.addTurner(((Turner) table.getValueAt(i, -1)));
						}
					} catch (HasMannschaftException e) {

						AxtresLogger.error("Turner konnte dem Wettkampf nicht zugeordnet werden.", e);
						ok = false;
					}
				if (!ok)
					JOptionPane.showMessageDialog(this, "Mind. 1 Turner konnte dem gewählten Wettkampf nicht hinzugefügt werden.\n\nWahrscheinlich ist er schon einer Mannschaft zugeordnet.", "Warnung", JOptionPane.WARNING_MESSAGE);
			}
		}.show(btnZuWettkampf);

	}

	@SuppressWarnings({ "unused", "serial" })
	private void zuRiege() {
		List<EinzelRiege> l = new Vector<EinzelRiege>();
		for (Riege r : WettkampfTag.get().getRiegen())
			if (r instanceof EinzelRiege) {
				l.add((EinzelRiege) r);
			}

		new SelectObjectDialog<EinzelRiege>(l, true, "Riege wählen...") {
			@Override
			public void onSelect(EinzelRiege er) {

				if (er == null)
					if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie die Zuordnung entfernen wollen?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
						return;

				boolean ok = true;
				for (int i : table.getSelectedRows())
					try {
						if (er == null) {
							Turner t = ((Turner) table.getValueAt(i, -1));

							if (t.getRiege() instanceof EinzelRiege)
								((EinzelRiege) t.getRiege()).removeTurner(t);
							else if (t.getRiege() instanceof MannschaftsRiege)
								throw new HasMannschaftException();
						} else {
							er.addTurner(((Turner) table.getValueAt(i, -1)));
						}
					} catch (HasMannschaftException e) {

						AxtresLogger.error("Turner konnte der Riege nicht zugeordnet werden.", e);
						ok = false;
					}
				if (!ok)
					JOptionPane.showMessageDialog(this, "Mind. 1 Turner konnte der gewählten Riege nicht hinzugefügt werden.\n\nWahrscheinlich ist er schon einer Mannschaft zugeordnet.", "Warnung", JOptionPane.WARNING_MESSAGE);
			}
		}.show(btnZuRiege);

	}

}
