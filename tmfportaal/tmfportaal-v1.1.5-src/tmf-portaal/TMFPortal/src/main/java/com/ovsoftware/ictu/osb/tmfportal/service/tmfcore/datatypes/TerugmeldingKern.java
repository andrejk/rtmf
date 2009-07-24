package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import java.io.Serializable;

/**
 * TerugmeldingKern wordt gebruikt om een Terugmelding of TerugmeldingDetails
 * uniek te identificeren. De TerugmeldingKern bevat naast het tmfKenmerk nog
 * gegevens over de organisatie en het berichtsoort.
 * 
 * @author ktinselboer
 *
 */

//UC046S - 2.1 - blz 7
//UC046S - 2.2 - blz 8
public class TerugmeldingKern implements Serializable{
	private static final long serialVersionUID = 4558350402715999119L;
	private String tmfKenmerk;
	private String berichtSoort;
	private String idOrganisatie;
	private String naamOrganisatie;
	
	/**
	 * Lege constructor ivm Spring beans.
	 */
	public TerugmeldingKern(){}

	/**
	 * Constructor voor TerugmeldingKern.
	 * 
	 * @param tmfKenmerk Kenmerk toegekend door TMF Core
	 * @param berichtSoort Het soort bericht (terugmelding/intrekking)
	 * @param idOrganisatie Het id van de organisatie
	 * @param naamOrganisatie Het naam van de organisatie
	 */
	public TerugmeldingKern(String tmfKenmerk, String berichtSoort,
			String idOrganisatie, String naamOrganisatie) {
		super();
		this.tmfKenmerk = tmfKenmerk;
		this.berichtSoort = berichtSoort;
		this.idOrganisatie = idOrganisatie;
		this.naamOrganisatie = naamOrganisatie;
	}

	/**
	 * Getter voor tmfKenmerk.
	 * 
	 * @return tmfKenmerk
	 */
	public String getTmfKenmerk() {
		return tmfKenmerk;
	}

	/**
	 * Getter voor berichtSoort.
	 * 
	 * @return berichtSoort
	 */
	public String getBerichtSoort() {
		return berichtSoort;
	}

	/**
	 * Getter voor idOrganisatie.
	 * 
	 * @return idOrganisatie
	 */
	public String getIdOrganisatie() {
		return idOrganisatie;
	}

	/**
	 * Getter voor naamOrganisatie.
	 * 
	 * @return naamOrganisatie
	 */
	public String getNaamOrganisatie() {
		return naamOrganisatie;
	}

	/**
	 * Setter voor tmfKenmerk.
	 * 
	 * @param tmfKenmerk De nieuwe waarde van tmfKenmerk
	 */
	public void setTmfKenmerk(String tmfKenmerk) {
		this.tmfKenmerk = tmfKenmerk;
	}

	/**
	 * Setter voor berichtSoort.
	 * 
	 * @param berichtSoort De nieuwe waarde van berichtSoort
	 */
	public void setBerichtSoort(String berichtSoort) {
		this.berichtSoort = berichtSoort;
	}

	/**
	 * Setter voor idOrganisatie.
	 * 
	 * @param idOrganisatie De nieuwe waarde van idOrganisatie
	 */
	public void setIdOrganisatie(String idOrganisatie) {
		this.idOrganisatie = idOrganisatie;
	}

	/**
	 * Setter voor naamOrganisatie.
	 * 
	 * @param naamOrganisatie De nieuwe waarde van naamOrganisatie
	 */
	public void setNaamOrganisatie(String naamOrganisatie) {
		this.naamOrganisatie = naamOrganisatie;
	}
	
}
