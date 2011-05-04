package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes;

/**
 * Deze klasse stelt een BasisRegistratieObjectAttribuut voor in het interne datamodel
 * van de StelselCatalogus-kant van deze applicatie.
 * 
 * @author ktinselboer
 *
 */
public class BRObjectAttribuut {

	private String tag;
	private String naam;
	private String waarde;
	
	/**
	 * Lege constructor ivm Spring beans.
	 */
	public BRObjectAttribuut(){}
	
	/**
	 * Constructor voor een BRObjectAttribuut.
	 * 
	 * @param tag De waarde van tag
	 * @param naam De waarde van naam
	 * @param waarde De waarde van waarde
	 */
	public BRObjectAttribuut(String tag, String naam, String waarde) {
		this.tag = tag;
		this.naam = naam;
		this.waarde = waarde;
	}

	/**
	 * Getter voor tag.
	 * 
	 * @return tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Getter voor naam.
	 * 
	 * @return naam
	 */
	public String getNaam() {
		return naam;
	}

	/**
	 * Getter voor waarde.
	 * 
	 * @return waarde
	 */
	public String getWaarde() {
		return waarde;
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

	/**
	 * Setter voor waarde.
	 * 
	 * @param waarde De nieuwe waarde voor waarde
	 */
	public void setWaarde(String waarde) {
		this.waarde = waarde;
	}
	
}
