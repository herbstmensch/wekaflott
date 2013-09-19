package de.timherbst.wau.domain.wettkampf;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.exceptions.HasMannschaftException;

public class EinzelWettkampf extends Wettkampf implements Serializable {
	private static final long serialVersionUID = 4554973205726013325L;

	List<Turner> turner = new Vector<Turner>();

	public EinzelWettkampf(String name) {
		this.setName(name);
	}

	public List<Turner> getTurner() {
		return turner;
	}

	public void addTurner(Turner t) throws HasMannschaftException {
		if (t.getMannschaft() != null) {
			throw new HasMannschaftException();
		}
		if (t.getWettkampf() != null) {
			if (t.getWettkampf() instanceof EinzelWettkampf)
				((EinzelWettkampf) t.getWettkampf()).getTurner().remove(t);
		}
		t.setWettkampf(this);
		t.initWertungen(this.getWertungsmodus());
		turner.add(t);
		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
		EventDispatcher.dispatchEvent(Event.WETTKAMPF_CHANGED);
	}

	public void removeTurner(Turner t) throws HasMannschaftException {
		if (t.getMannschaft() != null) {
			throw new HasMannschaftException();
		}
		if (t.getWettkampf() != null) {
			if (t.getWettkampf() instanceof EinzelWettkampf)
				((EinzelWettkampf) t.getWettkampf()).getTurner().remove(t);
		}
		t.setWettkampf(null);
		turner.remove(t);
		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
		EventDispatcher.dispatchEvent(Event.WETTKAMPF_CHANGED);
	}

	@Override
	public boolean isWKModusChangeable() {
		return !(getTurner().size() > 0);
	}

}
