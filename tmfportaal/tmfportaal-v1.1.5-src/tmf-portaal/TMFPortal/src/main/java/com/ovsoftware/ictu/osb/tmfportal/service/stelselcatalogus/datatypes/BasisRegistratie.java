package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes;

/**
 * Deze klasse stelt een BasisRegistratie voor in het interne datamodel
 * van de StelselCatalogus-kant van deze applicatie.
 * 
 * @author ktinselboer
 *
 */
public class BasisRegistratie {
	private String tag;
	private String naam;

	/**
	 * Lege constructor ivm Spring beans.
	 */
	public BasisRegistratie() {}
	
	/**
	 * Constructor voor het aanmaken van een BasisRegistratie.
	 * 
	 * @param tag De waarde van tag
	 * @param naam De waarde van naam
	 */
	public BasisRegistratie(String tag, String naam) {
		this.tag = tag;
		this.naam = naam;
	}
	
	/**
	 * Getter voor het opvragen van tag.
	 * 
	 * @return tag De waarde van tag
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * Getter voor het opvragen van naam.
	 * 
	 * @return naam De waarde van naam
	 */
	public String getNaam() {
		return naam;
	}

	/**
	 * Setter voor tag.
	 * 
	 * @param tag De nieuwe waarde voor tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * Setter voor naam.
	 * 
	 * @param naam De nieuwe waarde voor naam
	 */
	public void setNaam(String naam) {
		this.naam = naam;
	}
}
