package de.timherbst.wau.domain.wertungen;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("PStufenWertung")
public class PStufenWertung extends Wertung implements Serializable {

	private static final long serialVersionUID = 3742504743775430850L;
	private Double ausgang = 0d;
	private Double abzug = 0d;

	public Double getAusgang() {
		return ausgang;
	}

	public void setAusgang(Double ausgang) {
		this.ausgang = ausgang;
	}

	public Double getAbzug() {
		return abzug;
	}

	public void setAbzug(Double abzug) {
		this.abzug = abzug;
	}

	@Override
	public Double getEndwert() {
		if (ausgang == null || abzug == null)
			return 0d;
		double r = ausgang - abzug;
		return r < 0 ? 0 : r;
	}

}
