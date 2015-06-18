package de.timherbst.wau.domain.riege;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import de.timherbst.wau.domain.WettkampfTag;

@XStreamAlias("Riege")
public abstract class Riege implements Serializable {

	private static final long serialVersionUID = -868908033808328851L;
	private String name;
	private WettkampfTag wkt;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WettkampfTag getWkt() {
		return wkt;
	}

	public void setWkt(WettkampfTag wkt) {
		this.wkt = wkt;
	}

	@Override
	public String toString() {
		return name;
	}

}