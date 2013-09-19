package de.timherbst.wau.domain.wettkampf;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;

public class MannschaftsWettkampf extends Wettkampf implements Serializable {
	private static final long serialVersionUID = 4554973205726013325L;

	Integer turnerProGeraet = 5;
	Integer gewerteteTurnerProGeraet = 3;

	List<Mannschaft> mannschaften = new Vector<Mannschaft>();

	public MannschaftsWettkampf(String name) {
		setName(name);
	}

	public Integer getTurnerProGeraet() {
		return turnerProGeraet;
	}

	public void setTurnerProGeraet(Integer turnerProGeraet) {
		this.turnerProGeraet = turnerProGeraet;
	}

	public Integer getGewerteteTurnerProGeraet() {
		return gewerteteTurnerProGeraet;
	}

	public void setGewerteteTurnerProGeraet(Integer gewerteteTurnerProGeraet) {
		this.gewerteteTurnerProGeraet = gewerteteTurnerProGeraet;
	}

	public List<Mannschaft> getMannschaften() {
		return mannschaften;
	}

	public List<Turner> getTurner() {
		List<Turner> turner = new Vector<Turner>();
		for (Mannschaft m : mannschaften)
			turner.addAll(m.getTurner());
		return turner;
	}

	public void addMannschaft(Mannschaft m) {
		if (m.getWettkampf() != null)
			m.getWettkampf().getMannschaften().remove(m);
		m.setWettkampf(this);
		mannschaften.add(m);
		for (Turner t : m.getTurner()) {
			t.setWettkampf(this);
			t.initWertungen(this.getWertungsmodus());
		}
		EventDispatcher.dispatchEvent(Event.WETTKAMPF_CHANGED);
		EventDispatcher.dispatchEvent(Event.MANNSCHAFT_CHANGED);
	}

	@Override
	public boolean isWKModusChangeable() {
		return !(getMannschaften().size() > 0);
	}

}
