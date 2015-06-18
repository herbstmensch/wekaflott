package de.timherbst.wau.domain.riege;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.exceptions.HasMannschaftException;

@XStreamAlias("EinzelRiege")
public class EinzelRiege extends Riege implements Serializable {

	private static final long serialVersionUID = 3774432493979662197L;

	public EinzelRiege(String name, WettkampfTag wkt) {
		setName(name);
		setWkt(wkt);
	}

	public List<Turner> getTurner() {
		List<Turner> l = new Vector<Turner>();
		for (Turner t : getWkt().getTurner())
			if (this.equals(t.getRiege()))
				l.add(t);
		return l;
	}

	public void addTurner(Turner t) throws HasMannschaftException {
		if (t.getMannschaft() != null) {
			throw new HasMannschaftException();
		}

		t.setRiege(this);
		EventDispatcher.dispatchEvent(Event.RIEGE_CHANGED);
		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
	}

	public void removeTurner(Turner t) throws HasMannschaftException {
		if (t.getMannschaft() != null) {
			throw new HasMannschaftException();
		}
		if (t.getRiege() != null) {

			t.setRiege(null);
			EventDispatcher.dispatchEvent(Event.RIEGE_CHANGED);
			EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
		}
	}

}
