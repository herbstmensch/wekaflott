package de.timherbst.wau.domain.auswertung;

public class MannschaftsUrkundeBean {

	String turner;

	String verein;
	String veranstaltung;
	String wettkampf;
	String punkte;
	String platz;

	public MannschaftsUrkundeBean(String turner, String verein, String veranstaltung, String wettkampf, String punkte, String platz) {
		super();
		this.turner = turner;
		this.verein = verein;
		this.veranstaltung = veranstaltung;
		this.wettkampf = wettkampf;
		this.punkte = punkte;
		this.platz = platz;
	}

	public String getTurner() {
		return turner;
	}

	public void setTurner(String turner) {
		this.turner = turner;
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
