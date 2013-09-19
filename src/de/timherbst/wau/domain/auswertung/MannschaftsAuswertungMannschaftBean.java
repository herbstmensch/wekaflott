package de.timherbst.wau.domain.auswertung;

public class MannschaftsAuswertungMannschaftBean {

	private String mannschaft;
	private Double boden;
	private Double seitpferd;
	private Double ringe;
	private Double sprung;
	private Double barren;
	private Double reck;
	private Double gesamt;
	private Integer platz;

	public MannschaftsAuswertungMannschaftBean(String mannschaft, Double boden, Double seitpferd, Double ringe, Double sprung, Double barren, Double reck, Double gesamt, Integer platz) {
		super();
		this.mannschaft = mannschaft;
		this.boden = boden;
		this.seitpferd = seitpferd;
		this.ringe = ringe;
		this.sprung = sprung;
		this.barren = barren;
		this.reck = reck;
		this.gesamt = gesamt;
		this.platz = platz;
	}

	public String getMannschaft() {
		return mannschaft;
	}

	public void setMannschaft(String mannschaft) {
		this.mannschaft = mannschaft;
	}

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

	public Integer getPlatz() {
		return platz;
	}

	public void setPlatz(Integer platz) {
		this.platz = platz;
	}

}
