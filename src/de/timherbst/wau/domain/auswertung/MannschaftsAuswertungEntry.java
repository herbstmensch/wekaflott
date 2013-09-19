package de.timherbst.wau.domain.auswertung;

import de.timherbst.wau.domain.Mannschaft;

public class MannschaftsAuswertungEntry {

	private Mannschaft mannschaft;
	private MannschaftsErgebnis ergebnis;
	private Integer platzierung;

	public MannschaftsAuswertungEntry(Mannschaft mannschaft, MannschaftsErgebnis ergebnis, Integer platzierung) {
		super();
		this.mannschaft = mannschaft;
		this.ergebnis = ergebnis;
		this.platzierung = platzierung;
	}

	public Mannschaft getMannschaft() {
		return mannschaft;
	}

	public void setMannschaft(Mannschaft mannschaft) {
		this.mannschaft = mannschaft;
	}

	public MannschaftsErgebnis getErgebnis() {
		return ergebnis;
	}

	public void setErgebnis(MannschaftsErgebnis ergebnis) {
		this.ergebnis = ergebnis;
	}

	public Integer getPlatzierung() {
		return platzierung;
	}

	public void setPlatzierung(Integer platzierung) {
		this.platzierung = platzierung;
	}

}
