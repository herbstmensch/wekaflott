package de.timherbst.wau.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.domain.riege.Riege;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.exceptions.HasWettkampfException;

@XStreamAlias("Mannschaft")
public class Mannschaft implements Serializable {

	private static final long serialVersionUID = -8418807999669812167L;

	private String verein = "Verein";
	private String name;

	private MannschaftsWettkampf wettkampf;
	private Riege riege;

	public Mannschaft(String name) {
		setName(name);
	}

	public String getVerein() {
		return verein;
	}

	public void setVerein(String verein) {
		this.verein = verein;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Turner> getTurner() {
		List<Turner> l = new Vector<Turner>();
		for (Turner t : WettkampfTag.get().getTurner())
			if (this.equals(t.getMannschaft()))
				l.add(t);
		return l;
	}

	public void addTurner(Turner t) throws HasWettkampfException {
		AxtresLogger.info("Adding Turner " + t + " to Mannschaft " + this);
		if (t.getMannschaft() != null) {
			AxtresLogger.info("Turner " + t + " already had Mannschaft: " + t.getMannschaft());
			t.getMannschaft().removeTurner(t);
		}
		if (t.getWettkampf() != null)
			throw new HasWettkampfException();
		t.setMannschaft(this);
		if (this.getWettkampf() != null)
			t.initWertungen(this.getWettkampf().getWertungsmodus());

		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
		EventDispatcher.dispatchEvent(Event.MANNSCHAFT_CHANGED);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	public void removeTurner(Turner t) {
		AxtresLogger.info("Removing Turner " + t + " from Mannschaft " + this);
		t.setWettkampf(null);
		t.setRiege(null);
		try {
			t.setMannschaft(null);
		} catch (HasWettkampfException hme) {
			// Kann nicht passieren
		}
		EventDispatcher.dispatchEvent(Event.TURNER_CHANGED);
		EventDispatcher.dispatchEvent(Event.MANNSCHAFT_CHANGED);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	@Override
	public String toString() {
		return getName() + (wettkampf != null ? " (" + wettkampf.getName() + ")" : "");
	}

	public MannschaftsWettkampf getWettkampf() {
		return wettkampf;
	}

	public void setWettkampf(MannschaftsWettkampf wettkampf) {
		this.wettkampf = wettkampf;
		for (Turner t : getTurner())
			t.setWettkampf(wettkampf);
	}

	public Riege getRiege() {
		return riege;
	}

	public void setRiege(Riege riege) {
		this.riege = riege;
		for (Turner t : getTurner())
			t.setRiege(riege);
	}

}
