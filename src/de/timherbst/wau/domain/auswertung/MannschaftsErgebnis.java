package de.timherbst.wau.domain.auswertung;

public class MannschaftsErgebnis implements Comparable<MannschaftsErgebnis> {

	private Double boden = 0d;
	private Double seitpferd = 0d;
	private Double ringe = 0d;
	private Double sprung = 0d;
	private Double barren = 0d;
	private Double reck = 0d;
	private Double gesamt = 0d;

	public Double getBoden() {
		return boden;
	}

	public void setBoden(Double boden) {
		this.boden = boden;
	}

	public Double getSeitpferd() {
		return seitpferd;
	}

	public void setSeitpferd(Double seitpferd) {
		this.seitpferd = seitpferd;
	}

	public Double getRinge() {
		return ringe;
	}

	public void setRinge(Double ringe) {
		this.ringe = ringe;
	}

	public Double getSprung() {
		return sprung;
	}

	public void setSprung(Double sprung) {
		this.sprung = sprung;
	}

	public Double getBarren() {
		return barren;
	}

	public void setBarren(Double barren) {
		this.barren = barren;
	}

	public Double getReck() {
		return reck;
	}

	public void setReck(Double reck) {
		this.reck = reck;
	}

	public Double getGesamt() {
		return gesamt;
	}

	public void setGesamt(Double gesamt) {
		this.gesamt = gesamt;
	}

	@Override
	public int compareTo(MannschaftsErgebnis o) {
		return getGesamt().compareTo(o.getGesamt());
	}

}
