package de.timherbst.wau.domain.auswertung;

import java.util.ArrayList;
import java.util.List;

import de.timherbst.wau.domain.Turner;

public class TabellenEintrag implements Comparable<TabellenEintrag> {

	String veranstaltung = "";
	String mannschaft = "";
	List<Turner> turner = new ArrayList<Turner>();
	String wettkampf = "";
	Integer tabellenPunkte = 0;
	Integer gegenPunkte = 0;
	Double punkte = 0d;
	Integer platzierung;

	public TabellenEintrag(String mannschaft) {
		this.mannschaft = mannschaft;
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

	public String getMannschaft() {
		return mannschaft;
	}

	public void setMannschaft(String mannschaft) {
		this.mannschaft = mannschaft;
	}

	public List<Turner> getTurner() {
		return turner;
	}

	public void setTurner(List<Turner> turner) {
		this.turner = turner;
	}

	public Integer getTabellenPunkte() {
		return tabellenPunkte;
	}

	public void setTabellenPunkte(Integer tabellenPunkte) {
		this.tabellenPunkte = tabellenPunkte;
	}

	public Integer getGegenPunkte() {
		return gegenPunkte;
	}

	public void setGegenPunkte(Integer gegenPunkte) {
		this.gegenPunkte = gegenPunkte;
	}

	public Double getPunkte() {
		return punkte;
	}

	public void setPunkte(Double punkte) {
		this.punkte = punkte;
	}

	public Integer getPlatzierung() {
		return platzierung;
	}

	public void setPlatzierung(Integer platzierung) {
		this.platzierung = platzierung;
	}

	@Override
	public int compareTo(TabellenEintrag o) {
		int c = o.getTabellenPunkte().compareTo(this.tabellenPunkte);
		if (c != 0)
			return c;
		c = this.gegenPunkte.compareTo(o.getGegenPunkte());
		if (c != 0)
			return c;
		return o.getPunkte().compareTo(this.getPunkte());
	}

	public TabellenEintragTurner getTurnerEintrag(int i) {
		// TODO Auto-generated method stub
		return new TabellenEintragTurner(veranstaltung, mannschaft, turner.get(i).getVorname() + " " + turner.get(i).getName(), wettkampf, tabellenPunkte, gegenPunkte, punkte, platzierung);
	}

}
