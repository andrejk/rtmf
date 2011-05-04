package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes;

import java.util.ArrayList;

/**
 * Deze klasse stelt een BasisRegistratieObjectData-object voor in
 * het interne datamodel van de StelselCatalogus-kant van deze 
 * applicatie. 
 * 
 * Dit object type wordt alleen teruggeven aan de view
 * als antwoord op een aanroep waarbij huidige waardes voor een
 * bepaald object worden opgevraagd bij de StelselCatalogus.
 * 
 * @author ktinselboer
 *
 */
public class BRObjectData {
	private ArrayList<BRObjectAttribuut> broaLijst;
	private boolean bevraagbaar;
	private String instructie;
	private String naam;
	private String tag;
	
	/**
	 * Lege constructor ivm Spring beans.
	 */
	public BRObjectData() {}
	
	/**
	 * Constructor voor BRObjectData.
	 * 
	 * @param broaLijst De lijst met BRObjectAttribuut'en
	 * @param bevraagbaar Boolean of dit object bevraagbaar is
	 * @param instructie Instructie mbt dit object
	 * @param naam De naam van het object
	 * @param tag De tag van het object
	 */
	public BRObjectData(ArrayList<BRObjectAttribuut> broaLijst, 
						boolean bevraagbaar,
						String instructie,
						String naam,
						String tag) {
		this.broaLijst = broaLijst;
		this.bevraagbaar = bevraagbaar;
		this.instructie = instructie;
		this.naam = naam;
		this.tag = tag;
	}
	
	/**
	 * Setter voor broaLijst.
	 * 
	 * @param broaLijst De nieuwe waarde voor broaLijst
	 */
	public void setBroaLijst(ArrayList<BRObjectAttribuut> broaLijst) {
		this.broaLijst = broaLijst;
	}

	/**
	 * Setter voor bevraagbaar.
	 * 
	 * @param bevraagbaar De nieuwe waarde voor bevraagbaar
	 */
	public void setBevraagbaar(boolean bevraagbaar) {
		this.bevraagbaar = bevraagbaar;
	}

	/**
	 * Setter voor instructie.
	 * 
	 * @param instructie De nieuwe waarde voor instructie
	 */
	public void setInstructie(String instructie) {
		this.instructie = instructie;
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
	 * Setter voor tag.
	 * 
	 * @param tag De nieuwe waarde voor tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * Getter voor de lijst van BRObjectAttribuut'en.
	 * 
	 * @return Een lijst met BRObjectAttribuut'en
	 */
	public ArrayList<BRObjectAttribuut> getBroaLijst() {
		return this.broaLijst;
	}
	
	/**
	 * Getter die aangeeft of dit object bevraagbaar is. Dat wil
	 * zeggen of de actuele waardes opgevraagd kunnen worden bij
	 * de desbetreffende basisregistratie.
	 * 
	 * @return True indien bevraagbaar, anders False
	 */
	public boolean isBevraagbaar() {
		return bevraagbaar;
	}
	
	/**
	 * Getter voor instructie.
	 * 
	 * @return instructie
	 */
	public String getInstructie() {
		return instructie;
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
	 * Getter voor tag.
	 * 
	 * @return tag
	 */
	public String getTag() {
		return tag;
	}
	
}
