package de.timherbst.wau.domain.auswertung;

import de.timherbst.wau.domain.Turner;

public class EinzelAuswertungEntry {

	private Turner turner;
	private Double gesamt;
	private Integer platzierung;

	public EinzelAuswertungEntry(Turner turner, Double gesamt, Integer platzierung) {
		super();
		this.turner = turner;
		this.gesamt = gesamt;
		this.platzierung = platzierung;
	}

	public Turner getTurner() {
		return turner;
	}

	public void setTurner(Turner turner) {
		this.turner = turner;
	}

	public Double getGesamt() {
		return gesamt;
	}

	public void setGesamt(Double gesamt) {
		this.gesamt = gesamt;
	}

	public Integer getPlatzierung() {
		return platzierung;
	}

	public void setPlatzierung(Integer platzierung) {
		this.platzierung = platzierung;
	}

}
