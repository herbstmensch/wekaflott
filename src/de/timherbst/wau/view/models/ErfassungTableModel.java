package de.timherbst.wau.view.models;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.wertungen.PStufenWertung;
import de.timherbst.wau.domain.wertungen.Wertung;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;

public class ErfassungTableModel extends DefaultTableModel {

	public static final String CONTEXT_EINZEL_WETTKAMPF = "CONTEXT_EINZEL_WETTKAMPF";
	public static final String CONTEXT_MANNSCHAFTS_WETTKAMPF = "CONTEXT_MANNSCHAFTS_WETTKAMPF";
	public static final String CONTEXT_EINZEL_RIEGE = "CONTEXT_EINZEL_RIEGE";
	public static final String CONTEXT_MANNSCHAFTS_RIEGE = "CONTEXT_MANNSCHAFTS_RIEGE";
	public static final String CONTEXT_MANNSCHAFT = "CONTEXT_MANNSCHAFT";

	private static final long serialVersionUID = 1L;

	private List<Turner> turner;
	private String context;

	public ErfassungTableModel(List<Turner> turner, String context) {
		this.context = context;
		this.turner = turner;
	}

	@Override
	public int getRowCount() {
		return turner == null ? 0 : turner.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Turner t = turner.get(row);
		if (CONTEXT_EINZEL_RIEGE.equals(context) || CONTEXT_EINZEL_WETTKAMPF.equals(context)) {
			switch (column) {
			case -1:
				return t;
			case 0:
				return t.getName();
			case 1:
				return t.getVorname();
			case 2:
				return CONTEXT_EINZEL_RIEGE.equals(context) ? (t.getWettkampf() != null ? t.getWettkampf().getName() : "") : (t.getRiege() != null ? t.getRiege().getName() : "");
			case 3:
				return t.getWertungen().getBoden();
			case 4:
				return t.getWertungen().getSeitpferd();
			case 5:
				return t.getWertungen().getRinge();
			case 6:
				return t.getWertungen().getSprung();
			case 7:
				return t.getWertungen().getBarren();
			case 8:
				return t.getWertungen().getReck();
			}
		} else {
			switch (column) {
			case -1:
				return t;
			case 0:
				return t.getName();
			case 1:
				return t.getVorname();
			case 2:
				return CONTEXT_MANNSCHAFT.equals(context) ? (t.getRiege() != null ? t.getRiege().getName() : "") : (t.getMannschaft() != null ? t.getMannschaft().getName() : "");
			case 3:
				return CONTEXT_MANNSCHAFTS_RIEGE.equals(context) || CONTEXT_MANNSCHAFT.equals(context) ? (t.getWettkampf() != null ? t.getWettkampf().getName() : "") : (t.getRiege() != null ? t.getRiege().getName() : "");
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
			}
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		if (CONTEXT_EINZEL_RIEGE.equals(context) || CONTEXT_EINZEL_WETTKAMPF.equals(context))
			return 9;
		return 10;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (CONTEXT_EINZEL_RIEGE.equals(context) || CONTEXT_EINZEL_WETTKAMPF.equals(context))
			return columnIndex < 3 ? String.class : PStufenWertung.class;
		return columnIndex < 4 ? String.class : PStufenWertung.class;
	}

	@Override
	public String getColumnName(int column) {
		if (CONTEXT_EINZEL_RIEGE.equals(context) || CONTEXT_EINZEL_WETTKAMPF.equals(context))
			return Arrays.asList("Name", "Vorname", CONTEXT_EINZEL_RIEGE.equals(context) ? "Wettkampf" : "Riege", "Boden", "Seitpferd", "Ringe", "Sprung", "Barren", "Reck").get(column);
		return Arrays.asList("Name", "Vorname", CONTEXT_MANNSCHAFT.equals(context) ? "Riege" : "Mannschaft", CONTEXT_MANNSCHAFTS_RIEGE.equals(context) || CONTEXT_MANNSCHAFT.equals(context) ? "Wettkampf" : "Riege", "Boden", "Seitpferd", "Ringe", "Sprung", "Barren", "Reck").get(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (CONTEXT_EINZEL_RIEGE.equals(context) || CONTEXT_EINZEL_WETTKAMPF.equals(context))
			return columnIndex < 3 ? false : true;
		return columnIndex < 4 ? false : true;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Turner t = turner.get(row);
		if (CONTEXT_EINZEL_RIEGE.equals(context) || CONTEXT_EINZEL_WETTKAMPF.equals(context)) {
			switch (column) {
			case 3:
				t.getWertungen().setBoden((Wertung) aValue);
				break;
			case 4:
				t.getWertungen().setSeitpferd((Wertung) aValue);
				break;
			case 5:
				t.getWertungen().setRinge((Wertung) aValue);
				break;
			case 6:
				t.getWertungen().setSprung((Wertung) aValue);
				break;
			case 7:
				t.getWertungen().setBarren((Wertung) aValue);
				break;
			case 8:
				t.getWertungen().setReck((Wertung) aValue);
				break;
			}
		} else {
			switch (column) {
			case 4:
				t.getWertungen().setBoden((Wertung) aValue);
				break;
			case 5:
				t.getWertungen().setSeitpferd((Wertung) aValue);
				break;
			case 6:
				t.getWertungen().setRinge((Wertung) aValue);
				break;
			case 7:
				t.getWertungen().setSprung((Wertung) aValue);
				break;
			case 8:
				t.getWertungen().setBarren((Wertung) aValue);
				break;
			case 9:
				t.getWertungen().setReck((Wertung) aValue);
				break;
			}
		}
		EventDispatcher.dispatchEvent(Event.WERTUNG_CHANGED);
		fireTableCellUpdated(row, column);
	}

}
