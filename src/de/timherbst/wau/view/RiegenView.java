package de.timherbst.wau.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javabuilders.swing.SwingJavaBuilder;

import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.riege.EinzelRiege;
import de.timherbst.wau.domain.riege.MannschaftsRiege;
import de.timherbst.wau.domain.riege.Riege;
import de.timherbst.wau.view.components.ExcelAdapter;
import de.timherbst.wau.view.models.RiegenTableModel;

public class RiegenView extends JPanel {

	private static final long serialVersionUID = -6003576993912964040L;

	JTable table;
	JPopupMenu tablePopUp;
	Action deleteRiege;
	Action openErfassung;

	public RiegenView() {
		SwingJavaBuilder.build(this);
		table.setModel(new RiegenTableModel());
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
					deleteRiege.setEnabled(true);
					openErfassung.setEnabled(true);
				} else {
					deleteRiege.setEnabled(false);
					openErfassung.setEnabled(false);
				}
			}
		});
		table.setAutoCreateRowSorter(true);
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
					deleteRiege();
				super.keyReleased(e);
			}
		});

	}

	public void newEinzelriege() {
		WettkampfTag.get().addRiege(new EinzelRiege("Riege " + (WettkampfTag.get().getRiegen().size() + 1)));
	}

	public void newMannschaftsriege() {
		WettkampfTag.get().addRiege(new MannschaftsRiege("Riege " + (WettkampfTag.get().getRiegen().size() + 1)));
	}

	public void deleteRiege() {
		if (table.getSelectedRowCount() == 0)
			return;
		if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie die ausgewählten Riegen löschen wollen?", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;
		List<Riege> rg = new Vector<Riege>();
		for (int i : table.getSelectedRows()) {
			Riege r = (Riege) table.getValueAt(i, -1);
			rg.add(r);
		}
		for (Riege r : rg)
			WettkampfTag.get().removeRiege(r);
	}

	private void openErfassung() {
		for (int i : table.getSelectedRows())
			Application.openErfassung(table.getValueAt(i, -1));
	}

}
