package de.timherbst.wau.domain.wettkampf;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.exceptions.HasMannschaftException;

@XStreamAlias("EinzelWettkampf")
public class EinzelWettkampf extends Wettkampf implements Serializable {
	private static final long serialVersionUID = 4554973205726013325L;

	public EinzelWettkampf(String name) {
		this.setName(name);
	}

	public List<Turner> getTurner() {
		List<Turner> l = new Vector<Turner>();
		for (Turner t : WettkampfTag.get().getTurner())
			if (this.equals(t.getWettkampf()))
				l.add(t);
		return l;
	}

	public void addTurner(Turner t) throws HasMannschaftException {
		if (t.getMannschaft() != null) {
			throw new HasMannschaftException();
		}
		t.setWettkampf(this);
		t.initWertungen(this.getWertungsmodus());
		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
		EventDispatcher.dispatchEvent(Event.WETTKAMPF_CHANGED);
	}

	public void removeTurner(Turner t) throws HasMannschaftException {
		if (t.getMannschaft() != null) {
			throw new HasMannschaftException();
		}
		t.setWettkampf(null);
		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
		EventDispatcher.dispatchEvent(Event.WETTKAMPF_CHANGED);
	}

	@Override
	public boolean isWKModusChangeable() {
		return !(getTurner().size() > 0);
	}

}
