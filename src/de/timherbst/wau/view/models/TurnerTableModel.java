package de.timherbst.wau.view.models;

import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.events.EventListener;

public class TurnerTableModel extends DefaultTableModel implements EventListener {

	private static final long serialVersionUID = 1L;
	private ImageIcon icon = new ImageIcon(getClass().getResource("/icons/r16/turner.png"));

	public TurnerTableModel() {
		EventDispatcher.addListener(Event.WETTKAMPFTAG_CHANGED, this);
		EventDispatcher.addListener(Event.TURNER_CHANGED, this);
	}

	@Override
	public int getRowCount() {
		return WettkampfTag.get().getTurner().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Turner t = WettkampfTag.get().getTurner().get(row);
		switch (column) {
		case -1:
			return t;
		case 0:
			return icon;
		case 1:
			return t.getName();
		case 2:
			return t.getVorname();
		case 3:
			return t.getVerein();
		case 4:
			return t.getJahrgang();
		case 5:
			return t.getAk();
		case 6:
			return t.getWettkampf() != null ? t.getWettkampf().getName() : "";
		case 7:
			return t.getRiege() != null ? t.getRiege().getName() : "";
		case 8:
			return t.getMannschaft() != null ? t.getMannschaft().getName() : "";
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return ImageIcon.class;
		if (columnIndex == 5)
			return Boolean.class;
		return String.class;
	}

	@Override
	public String getColumnName(int column) {
		return Arrays.asList("", "Name", "Vorname", "Verein", "Jahrgang", "AK?", "Wettkampf", "Riege", "Mannschaft").get(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex > 0 && columnIndex < 6;
	}

	@Override
	protected void finalize() throws Throwable {
		EventDispatcher.removeListener(Event.TURNER_CHANGED, this);
		super.finalize();
	}

	@Override
	public void inform(Event e) {
		this.fireTableStructureChanged();
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Turner t = WettkampfTag.get().getTurner().get(row);
		switch (column) {
		case 1:
			t.setName((String) aValue);
			break;
		case 2:
			t.setVorname((String) aValue);
			break;
		case 3:
			t.setVerein((String) aValue);
			break;
		case 4:
			t.setJahrgang((String) aValue);
			break;
		case 5:
			t.setAk((Boolean) aValue);
			break;
		}
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
		fireTableCellUpdated(row, column);
	}
}
