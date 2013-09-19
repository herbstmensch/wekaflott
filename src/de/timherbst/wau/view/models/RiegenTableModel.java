package de.timherbst.wau.view.models;

import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.riege.EinzelRiege;
import de.timherbst.wau.domain.riege.Riege;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.events.EventListener;

public class RiegenTableModel extends DefaultTableModel implements EventListener {

	private static final long serialVersionUID = 1L;
	private ImageIcon icon = new ImageIcon(getClass().getResource("/icons/r16/riege.png"));

	public RiegenTableModel() {
		EventDispatcher.addListener(Event.WETTKAMPFTAG_CHANGED, this);
		EventDispatcher.addListener(Event.RIEGE_CHANGED, this);
	}

	@Override
	public int getRowCount() {
		return WettkampfTag.get().getRiegen().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Riege r = WettkampfTag.get().getRiegen().get(row);
		switch (column) {
		case -1:
			return r;
		case 0:
			return icon;
		case 1:
			return (r instanceof EinzelRiege) ? "Einzel" : "Mannschaft";
		case 2:
			return r.getName();
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return ImageIcon.class;
		return String.class;
	}

	@Override
	public String getColumnName(int column) {
		return Arrays.asList("", "Riegen Typ", "Name").get(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex > 1;
	}

	@Override
	protected void finalize() throws Throwable {
		EventDispatcher.removeListener(Event.WETTKAMPFTAG_CHANGED, this);
		EventDispatcher.removeListener(Event.RIEGE_CHANGED, this);
		super.finalize();
	}

	@Override
	public void inform(Event e) {
		this.fireTableStructureChanged();
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {

		Riege r = WettkampfTag.get().getRiegen().get(row);
		switch (column) {
		case 2:
			r.setName((String) aValue);
			break;
		}
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
		fireTableCellUpdated(row, column);
	}
}
