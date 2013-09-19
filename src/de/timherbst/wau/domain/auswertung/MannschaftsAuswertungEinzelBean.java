package de.timherbst.wau.domain.auswertung;

import de.timherbst.wau.domain.wertungen.Wertung;

public class MannschaftsAuswertungEinzelBean {

	private String name;
	private String vorname;
	private String mannschaft;
	private String jahrgang;
	private Wertung boden;
	private Wertung seitpferd;
	private Wertung ringe;
	private Wertung sprung;
	private Wertung barren;
	private Wertung reck;
	private Double gesamt;
	private Integer platz;

	public MannschaftsAuswertungEinzelBean(String name, String vorname, String mannschaft, String jahrgang, Wertung boden, Wertung seitpferd, Wertung ringe, Wertung sprung, Wertung barren, Wertung reck, Double gesamt, Integer platz) {
		super();
		this.name = name;
		this.vorname = vorname;
		this.mannschaft = mannschaft;
		this.jahrgang = jahrgang;
		this.boden = boden;
		this.seitpferd = seitpferd;
		this.ringe = ringe;
		this.sprung = sprung;
		this.barren = barren;
		this.reck = reck;
		this.gesamt = gesamt;
		this.platz = platz;
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

	public String getMannschaft() {
		return mannschaft;
	}

	public void setMannschaft(String mannschaft) {
		this.mannschaft = mannschaft;
	}

	public String getJahrgang() {
		return jahrgang;
	}

	public void setJahrgang(String jahrgang) {
		this.jahrgang = jahrgang;
	}

	public Wertung getBoden() {
		return boden;
	}

	public void setBoden(Wertung boden) {
		this.boden = boden;
	}

	public Wertung getSeitpferd() {
		return seitpferd;
	}

	public void setSeitpferd(Wertung seitpferd) {
		this.seitpferd = seitpferd;
	}

	public Wertung getRinge() {
		return ringe;
	}

	public void setRinge(Wertung ringe) {
		this.ringe = ringe;
	}

	public Wertung getSprung() {
		return sprung;
	}

	public void setSprung(Wertung sprung) {
		this.sprung = sprung;
	}

	public Wertung getBarren() {
		return barren;
	}

	public void setBarren(Wertung barren) {
		this.barren = barren;
	}

	public Wertung getReck() {
		return reck;
	}

	public void setReck(Wertung reck) {
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
