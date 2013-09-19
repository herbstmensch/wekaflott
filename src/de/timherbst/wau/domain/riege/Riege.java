package de.timherbst.wau.domain.riege;

import java.io.Serializable;

public abstract class Riege implements Serializable {

	private static final long serialVersionUID = -868908033808328851L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}