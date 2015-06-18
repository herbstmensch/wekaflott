package de.timherbst.wau.domain.riege;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;

@XStreamAlias("MannschaftsRiege")
public class MannschaftsRiege extends Riege implements Serializable {

	public MannschaftsRiege(String name, WettkampfTag wkt) {
		setName(name);
		setWkt(wkt);
	}

	private static final long serialVersionUID = 3774432493979662197L;

	public List<Mannschaft> getMannschaften() {
		List<Mannschaft> l = new Vector<Mannschaft>();
		for (Mannschaft m : getWkt().getMannschaften())
			if (this.equals(m.getRiege()))
				l.add(m);
		return l;
	}

	public void addMannschaft(Mannschaft m) {
		for (Turner t : m.getTurner()) {
			t.setRiege(this);
		}
		m.setRiege(this);

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
