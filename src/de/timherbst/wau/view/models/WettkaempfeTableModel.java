package de.timherbst.wau.view.models;

import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.wertungen.Wertung.Wertungsmodus;
import de.timherbst.wau.domain.wettkampf.EinzelWettkampf;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.domain.wettkampf.Wettkampf;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.events.EventListener;

public class WettkaempfeTableModel extends DefaultTableModel implements EventListener {

	private static final long serialVersionUID = 1L;
	private ImageIcon icon = new ImageIcon(getClass().getResource("/icons/r16/wettkampf.png"));

	public WettkaempfeTableModel() {
		EventDispatcher.addListener(Event.WETTKAMPFTAG_CHANGED, this);
		EventDispatcher.addListener(Event.WETTKAMPF_CHANGED, this);
	}

	@Override
	public int getRowCount() {
		return WettkampfTag.get().getWettkaempfe().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Wettkampf w = WettkampfTag.get().getWettkaempfe().get(row);
		switch (column) {
		case -1:
			return w;
		case 0:
			return icon;
		case 1:
			return (w instanceof EinzelWettkampf) ? "Einzel" : "Mannschaft";
		case 2:
			return w.getName();
		case 3:
			return w.getJahrgaenge();
		case 4:
			return w.getTyp();
		case 5:
			return w.getWertungsmodus();
		case 6:
			return w.getGeraete();
		case 7:
			return w.getGewerteteGeraete();
		case 8:
			return (w instanceof MannschaftsWettkampf) ? ((MannschaftsWettkampf) w).getTurnerProGeraet() : null;
		case 9:
			return (w instanceof MannschaftsWettkampf) ? ((MannschaftsWettkampf) w).getGewerteteTurnerProGeraet() : null;
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		// boolean mannschaft = false;
		// for (Wettkampf w : WettkampfTag.get().getWettkaempfe())
		// if (w instanceof MannschaftsWettkampf) {
		// mannschaft = true;
		// break;
		// }
		// return mannschaft ? 10 : 8;
		return 10;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return ImageIcon.class;
		return columnIndex < 5 ? String.class : (columnIndex == 5 ? Wertungsmodus.class : Integer.class);
	}

	@Override
	public String getColumnName(int column) {
		return Arrays.asList("", "WK Typ", "Name", "Jahrgänge", "Typ", "Wertungsmodus", "Geräte", "Gew. Geräte", "Turner p. Gerät", "Gew. Turner p. Gerät").get(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		Wettkampf w = WettkampfTag.get().getWettkaempfe().get(rowIndex);
		if (columnIndex == 5)
			return w.isWKModusChangeable();
		boolean mannschaft = (w instanceof MannschaftsWettkampf);
		return columnIndex > 1 && ((mannschaft) || (!mannschaft && columnIndex < 8));
	}

	@Override
	protected void finalize() throws Throwable {
		EventDispatcher.removeListener(Event.WETTKAMPFTAG_CHANGED, this);
		EventDispatcher.removeListener(Event.WETTKAMPF_CHANGED, this);
		super.finalize();
	}

	@Override
	public void inform(Event e) {
		this.fireTableStructureChanged();
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Wettkampf w = WettkampfTag.get().getWettkaempfe().get(row);
		switch (column) {
		case 2:
			w.setName((String) aValue);
			break;
		case 3:
			w.setJahrgaenge((String) aValue);
			break;
		case 4:
			w.setTyp((String) aValue);
			break;
		case 5:
			w.setWertungsmodus((Wertungsmodus) aValue);
			break;
		case 6:
			w.setGeraete((Integer) aValue);
			if (w.getGeraete() < w.getGewerteteGeraete())
				setValueAt(w.getGeraete(), row, column + 1);
			break;
		case 7:
			w.setGewerteteGeraete((Integer) aValue);
			break;
		case 8:
			((MannschaftsWettkampf) w).setTurnerProGeraet((Integer) aValue);
			if (((MannschaftsWettkampf) w).getTurnerProGeraet() < ((MannschaftsWettkampf) w).getGewerteteTurnerProGeraet())
				setValueAt(((MannschaftsWettkampf) w).getTurnerProGeraet(), row, column + 1);
			break;
		case 9:
			((MannschaftsWettkampf) w).setGewerteteTurnerProGeraet((Integer) aValue);
			break;
		}
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
		fireTableCellUpdated(row, column);
	}
}
