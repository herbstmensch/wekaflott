package de.timherbst.wau.domain.riege;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;

public class MannschaftsRiege extends Riege implements Serializable {

	public MannschaftsRiege(String name) {
		setName(name);
	}

	private static final long serialVersionUID = 3774432493979662197L;

	private List<Mannschaft> mannschaften = new Vector<Mannschaft>();

	public List<Mannschaft> getMannschaften() {
		return mannschaften;
	}

	public void addMannschaft(Mannschaft m) {
		if (m.getRiege() != null)
			((MannschaftsRiege) m.getRiege()).getMannschaften().remove(m);
		m.setRiege(this);
		this.mannschaften.add(m);
		for (Turner t : m.getTurner()) {
			t.setRiege(this);
		}
		EventDispatcher.dispatchEvent(Event.RIEGE_CHANGED);
		EventDispatcher.dispatchEvent(Event.MANNSCHAFT_CHANGED);
		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
	}

	public List<Turner> getTurner() {
		List<Turner> turner = new Vector<Turner>();
		for (Mannschaft m : getMannschaften())
			turner.addAll(m.getTurner());
		return turner;
	}

}
