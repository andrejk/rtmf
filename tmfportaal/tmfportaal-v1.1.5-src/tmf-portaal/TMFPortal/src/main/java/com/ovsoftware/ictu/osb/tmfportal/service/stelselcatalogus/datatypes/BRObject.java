package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes;

/**
 * Deze klasse stelt een BasisRegistratieObject voor in het interne datamodel
 * van de StelselCatalogus-kant van deze applicatie.
 * 
 * @author ktinselboer
 *
 */
public class BRObject {
	private String tag;
	private String naam;
	
	/**
	 * Lege constructor ivm Spring beans.
	 */
	public BRObject() {}
	
	/**
	 * Constructor voor het aanmaken van een BRObject.
	 * 
	 * @param tag De waarde van de tag
	 * @param naam De waarde van naam
	 */
	public BRObject(String tag, String naam) {
		this.tag = tag;
		this.naam = naam;
	}
	/**
	 * Getter voor het opvragen van tag.
	 * 
	 * @return tag
	 */	
	public String getTag() {
		return tag;
	}

	/**
	 * Getter voor het opvragen van naam.
	 * 
	 * @return naam
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
