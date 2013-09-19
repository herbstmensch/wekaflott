package de.timherbst.wau.view.models;

import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.events.EventListener;

public class MannschaftenTableModel extends DefaultTableModel implements EventListener {

	private static final long serialVersionUID = 1L;
	private ImageIcon icon = new ImageIcon(getClass().getResource("/icons/r16/mannschaft.png"));

	public MannschaftenTableModel() {
		EventDispatcher.addListener(Event.WETTKAMPFTAG_CHANGED, this);
		EventDispatcher.addListener(Event.MANNSCHAFT_CHANGED, this);
	}

	@Override
	public int getRowCount() {
		return WettkampfTag.get().getMannschaften().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Mannschaft m = WettkampfTag.get().getMannschaften().get(row);
		switch (column) {
		case -1:
			return m;
		case 0:
			return icon;
		case 1:
			return m.getName();
		case 2:
			return m.getVerein();
		case 3:
			return m.getRiege() != null ? m.getRiege().getName() : "";
		case 4:
			return m.getWettkampf() != null ? m.getWettkampf().getName() : "";
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return ImageIcon.class;
		return String.class;
	}

	@Override
	public String getColumnName(int column) {
		return Arrays.asList("", "Name", "Verein", "Riege", "Wettkampf").get(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex > 0 && columnIndex < 3;
	}

	@Override
	protected void finalize() throws Throwable {
		EventDispatcher.removeListener(Event.WETTKAMPFTAG_CHANGED, this);
		EventDispatcher.removeListener(Event.MANNSCHAFT_CHANGED, this);
		super.finalize();
	}

	@Override
	public void inform(Event e) {
		this.fireTableStructureChanged();
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Mannschaft m = WettkampfTag.get().getMannschaften().get(row);
		switch (column) {
		case 1:
			m.setName((String) aValue);
			break;
		case 2:
			m.setVerein((String) aValue);
			break;
		}
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
		fireTableCellUpdated(row, column);
	}
}
