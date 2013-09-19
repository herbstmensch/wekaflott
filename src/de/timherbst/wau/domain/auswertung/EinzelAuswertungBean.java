package de.timherbst.wau.domain.auswertung;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EinzelAuswertungBean {

	private String wettkampf;
	private String art;
	private String modus;
	private String jahrgaenge;
	private JRBeanCollectionDataSource list;

	public EinzelAuswertungBean(String wettkampf, String art, String modus, String jahrgaenge, JRBeanCollectionDataSource list) {
		super();
		this.wettkampf = wettkampf;
		this.art = art;
		this.modus = modus;
		this.jahrgaenge = jahrgaenge;
		this.list = list;
	}

	public String getWettkampf() {
		return wettkampf;
	}

	public void setWettkampf(String wettkampf) {
		this.wettkampf = wettkampf;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public String getModus() {
		return modus;
	}

	public void setModus(String modus) {
		this.modus = modus;
	}

	public String getJahrgaenge() {
		return jahrgaenge;
	}

	public void setJahrgaenge(String jahrgaenge) {
		this.jahrgaenge = jahrgaenge;
	}

	public JRBeanCollectionDataSource getList() {
		return list;
	}

	public void setList(JRBeanCollectionDataSource list) {
		this.list = list;
	}

}
