package de.timherbst.wau.domain.wettkampf;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import de.timherbst.wau.domain.wertungen.Wertung;

@XStreamAlias("Wettkampf")
public abstract class Wettkampf implements Serializable {

	private static final long serialVersionUID = -2570588112678147223L;

	String name;
	String jahrgaenge;
	String typ;
	Integer geraete = 6;
	Integer gewerteteGeraete = 6;
	Wertung.Wertungsmodus wertungsmodus = Wertung.WERTUNGSMODUS_PStufen;

	public abstract boolean isWKModusChangeable();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJahrgaenge() {
		return jahrgaenge;
	}

	public void setJahrgaenge(String jahrgaenge) {
		this.jahrgaenge = jahrgaenge;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public Integer getGeraete() {
		return geraete;
	}

	public void setGeraete(Integer geraete) {
		this.geraete = geraete;
	}

	public Integer getGewerteteGeraete() {
		return gewerteteGeraete;
	}

	public void setGewerteteGeraete(Integer gewerteteGeraete) {
		this.gewerteteGeraete = gewerteteGeraete;
	}

	public Wertung.Wertungsmodus getWertungsmodus() {
		return wertungsmodus;
	}

	public void setWertungsmodus(Wertung.Wertungsmodus wertungsmodus) {
		this.wertungsmodus = wertungsmodus;
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getGeraeteText() {
		String text = "Turn-";
		if (gewerteteGeraete < geraete)
			text += gewerteteGeraete + "-aus-" + geraete;
		else
			text += geraete + "";
		text += "-Kampf";
		return text;
	}

}