/**
 * 
 */
package nl.rotterdam.rtmf.form.pojo;

import java.io.Serializable;

import nl.rotterdam.rtmf.form.helper.SelectOption;

/**
 * @author rweverwijk
 * Dit is een Pojo om te gebruiken in het zoekscherm
 */
public class ZoekForm implements Serializable{
	private static final long serialVersionUID = 6483297672719977878L;
	
	private String meldingskenmerk;
	private String zaaknummer;
	private String objectIdentificatie;
	private String vanafDatumDag;
	private String vanafDatumMaand;
	private String vanafDatumJaar;
	private String totDatumDag;
	private String totDatumMaand;
	private String totDatumJaar;
	private SelectOption statusTerugmelding;
	
	/**
	 * @return the meldingskenmerk
	 */
	public String getMeldingskenmerk() {
		return meldingskenmerk;
	}
	/**
	 * @param meldingskenmerk the meldingskenmerk to set
	 */
	public void setMeldingskenmerk(String meldingskenmerk) {
		this.meldingskenmerk = meldingskenmerk;
	}
	/**
	 * @return the zaakNummer
	 */
	public String getZaaknummer() {
		return zaaknummer;
	}
	/**
	 * @param zaakNummer the zaakNummer to set
	 */
	public void setZaaknummer(String zaakNummer) {
		this.zaaknummer = zaakNummer;
	}
	/**
	 * @return the objectIdentificatie
	 */
	public String getObjectIdentificatie() {
		return objectIdentificatie;
	}
	/**
	 * @param objectIdentificatie the objectIdentificatie to set
	 */
	public void setObjectIdentificatie(String objectIdentificatie) {
		this.objectIdentificatie = objectIdentificatie;
	}
	/**
	 * @return the vanafDatumDag
	 */
	public String getVanafDatumDag() {
		return vanafDatumDag;
	}
	/**
	 * @param vanafDatumDag the vanafDatumDag to set
	 */
	public void setVanafDatumDag(String vanafDatumDag) {
		this.vanafDatumDag = vanafDatumDag;
	}
	/**
	 * @return the vanafDatumMaand
	 */
	public String getVanafDatumMaand() {
		return vanafDatumMaand;
	}
	/**
	 * @param vanafDatumMaand the vanafDatumMaand to set
	 */
	public void setVanafDatumMaand(String vanafDatumMaand) {
		this.vanafDatumMaand = vanafDatumMaand;
	}
	/**
	 * @return the vanafDatumJaar
	 */
	public String getVanafDatumJaar() {
		return vanafDatumJaar;
	}
	/**
	 * @param vanafDatumJaar the vanafDatumJaar to set
	 */
	public void setVanafDatumJaar(String vanafDatumJaar) {
		this.vanafDatumJaar = vanafDatumJaar;
	}
	/**
	 * @return the totDatumDag
	 */
	public String getTotDatumDag() {
		return totDatumDag;
	}
	/**
	 * @param totDatumDag the totDatumDag to set
	 */
	public void setTotDatumDag(String totDatumDag) {
		this.totDatumDag = totDatumDag;
	}
	/**
	 * @return the totDatumMaand
	 */
	public String getTotDatumMaand() {
		return totDatumMaand;
	}
	/**
	 * @param totDatumMaand the totDatumMaand to set
	 */
	public void setTotDatumMaand(String totDatumMaand) {
		this.totDatumMaand = totDatumMaand;
	}
	/**
	 * @return the totDatumJaar
	 */
	public String getTotDatumJaar() {
		return totDatumJaar;
	}
	/**
	 * @param totDatumJaar the totDatumJaar to set
	 */
	public void setTotDatumJaar(String totDatumJaar) {
		this.totDatumJaar = totDatumJaar;
	}
	/**
	 * @return the statusTerugmelding
	 */
	public SelectOption getStatusTerugmelding() {
		return statusTerugmelding;
	}
	/**
	 * @param statusTerugmelding the statusTerugmelding to set
	 */
	public void setStatusTerugmelding(SelectOption statusTerugmelding) {
		this.statusTerugmelding = statusTerugmelding;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ZoekForm [meldingskenmerk=" + meldingskenmerk
				+ ", objectIdentificatie=" + objectIdentificatie
				+ ", statusTerugmelding=" + statusTerugmelding
				+ ", totDatumDag=" + totDatumDag + ", totDatumJaar="
				+ totDatumJaar + ", totDatumMaand=" + totDatumMaand
				+ ", vanafDatumDag=" + vanafDatumDag + ", vanafDatumJaar="
				+ vanafDatumJaar + ", vanafDatumMaand=" + vanafDatumMaand
				+ ", zaaknummer=" + zaaknummer + "]";
	}
}
