package de.timherbst.wau.domain;

import java.io.Serializable;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.domain.riege.Riege;
import de.timherbst.wau.domain.wertungen.CdPWertung;
import de.timherbst.wau.domain.wertungen.PStufenWertung;
import de.timherbst.wau.domain.wertungen.Wertung;
import de.timherbst.wau.domain.wertungen.Wertungen;
import de.timherbst.wau.domain.wettkampf.Wettkampf;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.exceptions.HasWettkampfException;

public class Turner implements Serializable {

	private static final long serialVersionUID = 8412472847826759190L;
	private String verein = "Verein";
	private String name = "Name";
	private String vorname = "Vorname";
	private String jahrgang;
	private Boolean ak = false;
	private Wertungen wertungen = new Wertungen();

	// Added To
	private Wettkampf wettkampf;
	private Riege riege;
	private Mannschaft mannschaft;

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

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getJahrgang() {
		return jahrgang;
	}

	public void setJahrgang(String jahrgang) {
		this.jahrgang = jahrgang;
	}

	public Boolean getAk() {
		return Boolean.TRUE.equals(ak);
	}

	public void setAk(Boolean ak) {
		this.ak = ak;
	}

	public Wertungen getWertungen() {
		return wertungen;
	}

	public void setWertungen(Wertungen wertungen) {
		this.wertungen = wertungen;
	}

	public Wettkampf getWettkampf() {
		return wettkampf;
	}

	public void setWettkampf(Wettkampf wettkampf) {
		this.wettkampf = wettkampf;
	}

	public Riege getRiege() {
		return riege;
	}

	public void setRiege(Riege riege) {

		this.riege = riege;
	}

	public Mannschaft getMannschaft() {
		return mannschaft;
	}

	public void setMannschaft(Mannschaft mannschaft) throws HasWettkampfException {

		if (this.getWettkampf() != null) {
			AxtresLogger.info("Ein Turner mit Wettkampf kann keiner Mannschaft hinzugefügt werden.");
			throw new HasWettkampfException();
		}
		this.mannschaft = mannschaft;
		this.riege = mannschaft.getRiege();
		this.wettkampf = mannschaft.getWettkampf();
	}

	@Override
	public String toString() {
		return getName() + ", " + getVorname();
	}

	public void initWertungen(Wertung.Wertungsmodus wertungsmodus) {
		if (Wertung.WERTUNGSMODUS_CdP.equals(wertungsmodus)) {
			getWertungen().setBoden(new CdPWertung());
			getWertungen().setSeitpferd(new CdPWertung());
			getWertungen().setRinge(new CdPWertung());
			getWertungen().setSprung(new CdPWertung());
			getWertungen().setBarren(new CdPWertung());
			getWertungen().setReck(new CdPWertung());
			EventDispatcher.dispatchEvent(Event.WERTUNG_CHANGED);
		} else if (Wertung.WERTUNGSMODUS_PStufen.equals(wertungsmodus)) {
			getWertungen().setBoden(new PStufenWertung());
			getWertungen().setSeitpferd(new PStufenWertung());
			getWertungen().setRinge(new PStufenWertung());
			getWertungen().setSprung(new PStufenWertung());
			getWertungen().setBarren(new PStufenWertung());
			getWertungen().setReck(new PStufenWertung());
			EventDispatcher.dispatchEvent(Event.WERTUNG_CHANGED);
		}
	}

}
