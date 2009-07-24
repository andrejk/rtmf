package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import java.io.Serializable;

/**
 * ContactGegevens komen voor in een Terugmelding. De velden zijn
 * in de usecase weliswaar niet verplicht gesteld, maar ze zijn dit
 * wel volgens de email van dhr Schipperheijn (woensdag 26 November).
 * 
 * @author ktinselboer
 *
 */

//UC041S - 5.1 - blz 10
public class ContactGegevens implements Serializable{
	private static final long serialVersionUID = 2269002041059416022L;
	private String naam;
	private String telefoon;
	private String email;
	
	/**
	 * Lege constructor ivm Spring beans.
	 */
	public ContactGegevens(){}

	/**
	 * Constructor voor ContactGegevens.
	 * 
	 * @param naam De naam van de contactpersoon
	 * @param telefoon Het telefoonnummer van de contactpersoon
	 * @param email Het emailadres van de contactpersoon
	 */
	public ContactGegevens(String naam, String telefoon, String email) {
		super();
		this.naam = naam;
		this.telefoon = telefoon;
		this.email = email;
	}

	/**
	 * Getter voor naam.
	 * 
	 * @return naam.
	 */
	public String getNaam() {
		return naam;
	}

	/**
	 * Getter voor telefoon.
	 * 
	 * @return telefoon
	 */
	public String getTelefoon() {
		return telefoon;
	}

	/**
	 * Getter voor email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	
	/**
	 * Setter voor naam.
	 * 
	 * @param naam De nieuwe waarde voor naam
	 */
	public void setNaam(String naam) {
		this.naam = naam;
	}

	/**
	 * Setter voor telefoon.
	 * 
	 * @param telefoon De nieuwe waarde voor telefoon
	 */
	public void setTelefoon(String telefoon) {
		this.telefoon = telefoon;
	}

	/**
	 * Setter voor email.
	 * 
	 * @param email De nieuwe waarde voor email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
