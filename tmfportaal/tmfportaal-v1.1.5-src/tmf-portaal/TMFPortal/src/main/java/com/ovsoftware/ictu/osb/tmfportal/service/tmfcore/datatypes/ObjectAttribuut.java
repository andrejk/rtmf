package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import java.io.Serializable;

/**
 * ObjectAttribuut is de interne representatie aan de TMFCore kant van
 * een attribuut van een object uit een basisregistratie. Zie evt ook:
 * - AttribuutType, uit het externe StelselCatalogus model
 * - BRObjectAttribuut, uit het interne StelselCatalogus model
 * - ObjectAttribuutType, uit het externe TMFCore WUS model
 * - ObjectAttribuutType, uit het externe TMFCore EBMS model
 * 
 * @author ktinselboer
 *
 */

//UC046S - 2.1 - blz 7
public class ObjectAttribuut implements Serializable{
	private static final long serialVersionUID = 3486974391840803956L;
	private String idAttribuut;
	private String betwijfeldeWaarde;
	private String voorstel;
	private String attribuutNaam;

	/**
	 * Lege constructor ivm Spring beans.
	 */
	public ObjectAttribuut(){}
	
	/**
	 * Constructor voor een ObjectAttribuut.
	 * 
	 * @param idAttribuut Het id, ook wel tag, voor dit object
	 * @param betwijfeldeWaarde De waarde die betwijfeld wordt
	 * @param voorstel De waarde die voorgesteld wordt
	 * @param attribuutNaam De naam van dit attribuut
	 */
	public ObjectAttribuut(String idAttribuut, String betwijfeldeWaarde,
			String voorstel, String attribuutNaam) {
		this.idAttribuut = idAttribuut;
		this.betwijfeldeWaarde = betwijfeldeWaarde;
		this.voorstel = voorstel;
		this.attribuutNaam = attribuutNaam;
	}

	/**
	 * Getter voor IdAttribuut, ook wel bekend
	 * als "tag".
	 * 
	 * @return idAttribuut (tag)
	 */
	public String getIdAttribuut() {
		return idAttribuut;
	}
	
	/**
	 * Getter voor betwijfeldeWaarde.
	 * 
	 * @return betwijfeldeWaarde
	 */
	public String getBetwijfeldeWaarde() {
		return betwijfeldeWaarde;
	}

	/**
	 * Getter voor voorstel.
	 * 
	 * @return voorstel
	 */
	public String getVoorstel() {
		return voorstel;
	}

	/**
	 * Getter voor attribuutNaam.
	 * 
	 * @return attribuutNaam
	 */
	public String getAttribuutNaam() {
		return attribuutNaam;
	}

	/**
	 * Setter voor idAttribuut.
	 * 
	 * @param idAttribuut De nieuwe waarde voor idAttribuut
	 */
	public void setIdAttribuut(String idAttribuut) {
		this.idAttribuut = idAttribuut;
	}

	/**
	 * Setter voor betwijfeldeWaarde.
	 * 
	 * @param betwijfeldeWaarde De nieuwe waarde voor betwijfeldeWaarde
	 */
	public void setBetwijfeldeWaarde(String betwijfeldeWaarde) {
		this.betwijfeldeWaarde = betwijfeldeWaarde;
	}

	/**
	 * Setter voor voorstel.
	 * 
	 * @param voorstel De nieuwe waarde voor voorstel
	 */
	public void setVoorstel(String voorstel) {
		this.voorstel = voorstel;
	}

	/**
	 * Setter voor attribuutNaam.
	 * 
	 * @param attribuutNaam De nieuwe waarde voor attribuutNaam
	 */
	public void setAttribuutNaam(String attribuutNaam) {
		this.attribuutNaam = attribuutNaam;
	}
	
	
}
