package de.timherbst.wau.domain.auswertung;

import java.util.HashMap;

import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.wettkampf.EinzelWettkampf;

public class EinzelAuswertung implements Auswertung {

	private EinzelWettkampf wettkampf;
	private HashMap<Turner, EinzelAuswertungEntry> entrys = new HashMap<Turner, EinzelAuswertungEntry>();

	public EinzelAuswertung(EinzelWettkampf wettkampf) {
		super();
		this.wettkampf = wettkampf;
	}

	public EinzelWettkampf getWettkampf() {
		return wettkampf;
	}

	public void setWettkampf(EinzelWettkampf wettkampf) {
		this.wettkampf = wettkampf;
	}

	public HashMap<Turner, EinzelAuswertungEntry> getEntrys() {
		return entrys;
	}

	public void setEntrys(HashMap<Turner, EinzelAuswertungEntry> entrys) {
		this.entrys = entrys;
	}

}
