package de.timherbst.wau.domain.auswertung;

public class EinzelUrkundeBean {

	String name;
	String verein;
	String veranstaltung;
	String wettkampf;
	String punkte;
	String platz;

	public EinzelUrkundeBean(String name, String verein, String veranstaltung, String wettkampf, String punkte, String platz) {
		super();
		this.name = name;
		this.verein = verein;
		this.veranstaltung = veranstaltung;
		this.wettkampf = wettkampf;
		this.punkte = punkte;
		this.platz = platz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVerein() {
		return verein;
	}

	public void setVerein(String verein) {
		this.verein = verein;
	}

	public String getVeranstaltung() {
		return veranstaltung;
	}

	public void setVeranstaltung(String veranstaltung) {
		this.veranstaltung = veranstaltung;
	}

	public String getWettkampf() {
		return wettkampf;
	}

	public void setWettkampf(String wettkampf) {
		this.wettkampf = wettkampf;
	}

	public String getPunkte() {
		return punkte;
	}

	public void setPunkte(String punkte) {
		this.punkte = punkte;
	}

	public String getPlatz() {
		return platz;
	}

	public void setPlatz(String platz) {
		this.platz = platz;
	}

}
