package de.timherbst.wau.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import de.timherbst.wau.domain.riege.EinzelRiege;
import de.timherbst.wau.domain.riege.MannschaftsRiege;
import de.timherbst.wau.domain.riege.Riege;
import de.timherbst.wau.domain.wettkampf.EinzelWettkampf;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.domain.wettkampf.Wettkampf;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.exceptions.HasWettkampfException;

public class WettkampfTag implements Serializable {

	private static final long serialVersionUID = 2524662626105936234L;
	private static WettkampfTag instance;

	private String name;
	private String ort;
	private Date datum;

	private Vector<Wettkampf> wettkaempfe;
	private Vector<Turner> turner;
	private Vector<Mannschaft> mannschaften;
	private Vector<Riege> riegen;

	private WettkampfTag() {
		name = "Wettkampf Tag";
		ort = "Ort";
		datum = new Date();
		wettkaempfe = new Vector<Wettkampf>();
		turner = new Vector<Turner>();
		mannschaften = new Vector<Mannschaft>();
		riegen = new Vector<Riege>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Vector<Wettkampf> getWettkaempfe() {
		return wettkaempfe;
	}

	public void addWettkampf(Wettkampf wettkampf) {
		this.wettkaempfe.add(wettkampf);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	public Vector<Turner> getTurner() {
		return turner;
	}

	public void addTurner(Turner turner) {
		this.turner.add(turner);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	public Vector<Mannschaft> getMannschaften() {
		return mannschaften;
	}

	public void addMannschaft(Mannschaft mannschaft) {
		this.mannschaften.add(mannschaft);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	public Vector<Riege> getRiegen() {
		return riegen;
	}

	public void addRiege(Riege riege) {
		this.riegen.add(riege);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	public static WettkampfTag get() {
		if (instance == null)
			instance = new WettkampfTag();
		return instance;
	}

	public static void set(WettkampfTag wt) {
		instance = wt;
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	@Override
	public String toString() {
		return getName();
	}

	public static WettkampfTag newInstance() {
		return new WettkampfTag();
	}

	public void removeWettkampf(Wettkampf w) {
		if (w instanceof EinzelWettkampf) {
			EinzelWettkampf ew = (EinzelWettkampf) w;
			for (Turner t : ew.getTurner()) {
				t.setWettkampf(null);
			}
		}
		if (w instanceof MannschaftsWettkampf) {
			MannschaftsWettkampf mw = (MannschaftsWettkampf) w;
			for (Mannschaft m : mw.getMannschaften()) {
				m.setWettkampf(null);
			}
		}
		getWettkaempfe().remove(w);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	public void removeRiege(Riege r) {
		if (r instanceof EinzelRiege) {
			EinzelRiege er = (EinzelRiege) r;
			for (Turner t : er.getTurner()) {
				t.setRiege(null);
			}
		}
		if (r instanceof MannschaftsRiege) {
			MannschaftsRiege mr = (MannschaftsRiege) r;
			for (Mannschaft m : mr.getMannschaften()) {
				m.setRiege(null);
			}

		}
		getRiegen().remove(r);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	public void removeTurner(Turner t) {
		if (t.getMannschaft() != null)
			t.getMannschaft().getTurner().remove(t);

		if (t.getRiege() != null && t.getRiege() instanceof EinzelRiege)
			((EinzelRiege) t.getRiege()).getTurner().remove(t);

		if (t.getWettkampf() != null && t.getWettkampf() instanceof EinzelWettkampf)
			((EinzelWettkampf) t.getWettkampf()).getTurner().remove(t);
		getTurner().remove(t);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

	public void removeMannschaft(Mannschaft m) {
		for (Turner t : m.getTurner()) {
			t.setRiege(null);
			t.setWettkampf(null);
			try {
				t.setMannschaft(null);
			} catch (HasWettkampfException e) {
				// kann nicht passieren
			}
		}
		if (m.getRiege() != null && m.getRiege() instanceof MannschaftsRiege)
			((MannschaftsRiege) m.getRiege()).getMannschaften().remove(m);

		if (m.getWettkampf() != null && m.getWettkampf() instanceof MannschaftsWettkampf)
			((MannschaftsWettkampf) m.getWettkampf()).getMannschaften().remove(m);

		getMannschaften().remove(m);
		EventDispatcher.dispatchEvent(Event.WETTKAMPFTAG_CHANGED);
	}

}
