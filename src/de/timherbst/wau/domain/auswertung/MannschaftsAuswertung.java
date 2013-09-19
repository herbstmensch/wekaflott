package de.timherbst.wau.domain.auswertung;

import java.util.HashMap;

import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;

public class MannschaftsAuswertung implements Auswertung {

	private MannschaftsWettkampf wettkampf;
	private HashMap<Turner, EinzelAuswertungEntry> entrysEinzel = new HashMap<Turner, EinzelAuswertungEntry>();
	private HashMap<Mannschaft, MannschaftsAuswertungEntry> entrysMannschaft = new HashMap<Mannschaft, MannschaftsAuswertungEntry>();

	public MannschaftsAuswertung(MannschaftsWettkampf wettkampf) {
		super();
		this.wettkampf = wettkampf;
	}

	public MannschaftsWettkampf getWettkampf() {
		return wettkampf;
	}

	public void setWettkampf(MannschaftsWettkampf wettkampf) {
		this.wettkampf = wettkampf;
	}

	public HashMap<Turner, EinzelAuswertungEntry> getEntrysEinzel() {
		return entrysEinzel;
	}

	public void setEntrysEinzel(HashMap<Turner, EinzelAuswertungEntry> entrysEinzel) {
		this.entrysEinzel = entrysEinzel;
	}

	public HashMap<Mannschaft, MannschaftsAuswertungEntry> getEntrysMannschaft() {
		return entrysMannschaft;
	}

	public void setEntrysMannschaft(HashMap<Mannschaft, MannschaftsAuswertungEntry> entrysMannschaft) {
		this.entrysMannschaft = entrysMannschaft;
	}

}
