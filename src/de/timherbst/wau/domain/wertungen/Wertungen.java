package de.timherbst.wau.domain.wertungen;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Wertungen implements Serializable {

	private static final long serialVersionUID = 1802579932147239022L;
	private Wertung boden;
	private Wertung seitpferd;
	private Wertung ringe;
	private Wertung sprung;
	private Wertung barren;
	private Wertung reck;

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

	public Double getGesamtWertung(Integer gewerteteGeraete, Boolean ak) {
		if (Boolean.TRUE.equals(ak))
			return -42d;
		List<Wertung> lw = Arrays.asList(getBoden(), getSeitpferd(), getRinge(), getSprung(), getBarren(), getReck());
		Collections.sort(lw, new Comparator<Wertung>() {
			@Override
			public int compare(Wertung o1, Wertung o2) {
				return o2.getEndwert().compareTo(o1.getEndwert());
			}
		});

		double ew = 0d;
		for (int i = 0; i < gewerteteGeraete && i < lw.size(); i++) {
			ew += lw.get(i).getEndwert();
		}

		return ew;
	}

}
