package de.timherbst.wau.domain.riege;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.exceptions.HasMannschaftException;

public class EinzelRiege extends Riege implements Serializable {

	private static final long serialVersionUID = 3774432493979662197L;

	private List<Turner> turner = new Vector<Turner>();

	public EinzelRiege(String name) {
		setName(name);
	}

	public List<Turner> getTurner() {
		return turner;
	}

	public void addTurner(Turner t) throws HasMannschaftException {
		if (t.getMannschaft() != null) {
			throw new HasMannschaftException();
		}
		if (t.getRiege() != null)
			((EinzelRiege) t.getRiege()).getTurner().remove(t);
		t.setRiege(this);
		this.turner.add(t);
		EventDispatcher.dispatchEvent(Event.RIEGE_CHANGED);
		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
	}

	public void removeTurner(Turner t) throws HasMannschaftException {
		if (t.getMannschaft() != null) {
			throw new HasMannschaftException();
		}
		if (t.getRiege() != null)
			((EinzelRiege) t.getRiege()).getTurner().remove(t);
		t.setRiege(null);
		EventDispatcher.dispatchEvent(Event.RIEGE_CHANGED);
		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
	}

}
