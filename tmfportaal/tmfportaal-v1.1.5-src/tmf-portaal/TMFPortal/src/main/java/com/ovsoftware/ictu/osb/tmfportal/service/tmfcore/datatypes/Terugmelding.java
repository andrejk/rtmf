package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Terugmelding bevat alle gegevens die benodigd zijn bij het aanmaken
 * van een nieuwe Terugmelding. Ook wordt dit type gebruikt bij het
 * retourneren van een lijst van eerder gedane terugmeldingen.
 * 
 * @author ktinselboer
 *
 */

//UC046S - 2.1 - blz 7
public class Terugmelding implements Serializable{
	private static final long serialVersionUID = -2532981241840325998L;
	private String meldingKenmerk;
	private ContactGegevens contactGegevens;
	private GregorianCalendar tijdstempelAanlevering;
	private String tagBR;
	private String tagObject;
	private String idObject;
	private String toelichting;
	private ArrayList<ObjectAttribuut> objectAttribuutLijst;
	private TerugmeldingKern terugmeldingKern;
	private ArrayList<Bijlage> bijlageLijst;
	private String status;

	/**
	 * Lege constructor ivm Spring beans.
	 */
	public Terugmelding(){}
	
	/**
	 * Constructor voor Terugmelding.
	 * 
	 * @param meldingKenmerk Het kenmerk dat de afnemer aan de Terugmelding geeft
	 * @param contactGegevens De gegevens van de contactpersoon bij de afnemer
	 * @param tijdstempelAanlevering De datumtijd waarop de melding werd aangeleverd
	 * @param tagBR De tag van de BasisRegistratie
	 * @param tagObject De tag van het BasisRegistratie Object
	 * @param idObject Het ID (bijv BSN-nummer) van het Object
	 * @param toelichting Reden voor de terugmelding
	 * @param objectAttribuutLijst Lijst met te wijzigen object-attributen
	 * @param terugmeldingKern De kern van de Terugmelding, zie TerugmeldingKern
	 * @param bijlageLijst Een lijst met bijlages, zie Bijlage
	 */
	public Terugmelding(String meldingKenmerk,
						ContactGegevens contactGegevens,
						GregorianCalendar tijdstempelAanlevering,
						String tagBR,
						String tagObject,
						String idObject, 
						String toelichting,
						ArrayList<ObjectAttribuut> objectAttribuutLijst,
						TerugmeldingKern terugmeldingKern,
						ArrayList<Bijlage> bijlageLijst,
						String status) {
		this.meldingKenmerk = meldingKenmerk;
		this.contactGegevens = contactGegevens;
		this.tijdstempelAanlevering = tijdstempelAanlevering;
		this.tagBR = tagBR;
		this.tagObject = tagObject;
		this.idObject = idObject;
		this.toelichting = toelichting;
		this.objectAttribuutLijst = objectAttribuutLijst;
		this.terugmeldingKern = terugmeldingKern;
		this.bijlageLijst = bijlageLijst;
		this.status = status;
	}

	/**
	 * Getter voor bijlageLijst.
	 * 
	 * @return bijlageLijst
	 */
	public ArrayList<Bijlage> getBijlageLijst() {
		return bijlageLijst;
	}

	/**
	 * Setter voor bijlageLijst.
	 * 
	 * @param bijlageLijst De nieuwe waarde voor de bijlageLijst
	 */
	public void setBijlageLijst(ArrayList<Bijlage> bijlageLijst) {
		this.bijlageLijst = bijlageLijst;
	}
	
	/**
	 * Getter voor meldingKenmerk.
	 * 
	 * @return meldingKenmerk
	 */
	public String getMeldingKenmerk() {
		return meldingKenmerk;
	}

	/** 
	 * Setter voor meldingKenmerk.
	 * 
	 * @param meldingKenmerk De nieuwe waarde voor meldingKenmerk
	 */
	public void setMeldingKenmerk(String meldingKenmerk) {
		this.meldingKenmerk = meldingKenmerk;
	}

	/**
	 * Getter voor contactGegevens.
	 * 
	 * @return contactGegevens
	 */
	public ContactGegevens getContactGegevens() {
		return contactGegevens;
	}

	/**
	 * Setter voor contactGegevens.
	 * 
	 * @param contactGegevens De nieuwe waarde voor contactGegevens
	 */
	public void setContactGegevens(ContactGegevens contactGegevens) {
		this.contactGegevens = contactGegevens;
	}

	/**
	 * Getter voor tijdstempelAanlevering.
	 * 
	 * @return tijdstempelAanlevering
	 */
	public GregorianCalendar getTijdstempelAanlevering() {
		return tijdstempelAanlevering;
	}

	/**
	 * Setter voor tijdstempelAanlevering.
	 * 
	 * @param tijdstempelAanlevering De nieuwe waarde voor tijdstempelAanlevering
	 */
	public void setTijdstempelAanlevering(GregorianCalendar tijdstempelAanlevering) {
		this.tijdstempelAanlevering = tijdstempelAanlevering;
	}

	/**
	 * Getter voor tagBR.
	 * 
	 * @return tagBR
	 */
	public String getTagBR() {
		return tagBR;
	}

	/**
	 * Setter voor tagBR.
	 * 
	 * @param tagBR De nieuwe waarde voor tagBR
	 */
	public void setTagBR(String tagBR) {
		this.tagBR = tagBR;
	}

	/**
	 * Getter voor tagObject.
	 * 
	 * @return tagObject
	 */
	public String getTagObject() {
		return tagObject;
	}

	/**
	 * Setter voor tagObject.
	 * 
	 * @param tagObject De nieuwe waarde voor tagObject
	 */
	public void setTagObject(String tagObject) {
		this.tagObject = tagObject;
	}

	/**
	 * Getter voor idObject.
	 * 
	 * @return idObject
	 */
	public String getIdObject() {
		return idObject;
	}

	/**
	 * Setter voor idObject.
	 * 
	 * @param idObject De nieuwe waarde voor idObject
	 */
	public void setIdObject(String idObject) {
		this.idObject = idObject;
	}

	/**
	 * Getter voor toelichting.
	 * 
	 * @return toelichting
	 */
	public String getToelichting() {
		return toelichting;
	}

	/**
	 * Setter voor toelichting.
	 * 
	 * @param toelichting De nieuwe waarde voor toelichting
	 */
	public void setToelichting(String toelichting) {
		this.toelichting = toelichting;
	}

	/**
	 * Getter voor objectAttribuutLijst.
	 * 
	 * @return objectAttribuutLijst
	 */
	public ArrayList<ObjectAttribuut> getObjectAttribuutLijst() {
		return objectAttribuutLijst;
	}

	/**
	 * Setter voor objectAttribuutLijst.
	 * 
	 * @param objectAttribuutLijst De nieuwe waarde voor objectAttribuutLijst
	 */
	public void setObjectAttribuutLijst(
			ArrayList<ObjectAttribuut> objectAttribuutLijst) {
		this.objectAttribuutLijst = objectAttribuutLijst;
	}

	/**
	 * Getter voor terugmeldingKern.
	 * 
	 * @return terugmeldingKern
	 */
	public TerugmeldingKern getTerugmeldingKern() {
		return terugmeldingKern;
	}

	/**
	 * Setter voor terugmeldingKern.
	 * 
	 * @param terugmeldingKern De nieuwe waarde voor terugmeldingKern
	 */
	public void setTerugmeldingKern(TerugmeldingKern terugmeldingKern) {
		this.terugmeldingKern = terugmeldingKern;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
