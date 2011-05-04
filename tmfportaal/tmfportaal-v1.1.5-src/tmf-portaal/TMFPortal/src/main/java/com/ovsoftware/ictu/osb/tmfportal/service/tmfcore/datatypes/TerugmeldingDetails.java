package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import java.util.GregorianCalendar;

/**
 * TerugmeldingDetails wordt gebruikt om de details van een eerder
 * gedane terugmelding in te representeren. 
 * 
 * @author ktinselboer
 *
 */

//UC046S - 2.2 - blz 8
public class TerugmeldingDetails {
	private TerugmeldingKern terugmeldingKern;
	private String afdeling;
	private GregorianCalendar tijdstempelOntvangst;
	private GregorianCalendar tijdstempelGemeld;
	private String statusMelding;
	private GregorianCalendar tijdstempelStatus;
	private String toelichtingStatus;
	
	/**
	 * Lege constructor ivm Spring beans.
	 */
	public TerugmeldingDetails(){}
	
	/**
	 * Constructor voor TerugmeldingDetails.
	 * 
	 * @param terugmeldingKern De kern van de terugmelding, zie TerugmeldingKern
	 * @param afdeling Uit certificaatgegevens oorspronkelijke terugmelding
	 * @param tijdstempelOntvangst Moment van ontvangt door TMF Core
	 * @param tijdstempelGemeld Moment van doorsturen door TMF Core
	 * @param statusMelding De huidige status behorend bij de melding
	 * @param tijdstempelStatus Moment waarop de huidige status is gezet
	 */
	public TerugmeldingDetails(TerugmeldingKern terugmeldingKern,
			String afdeling, GregorianCalendar tijdstempelOntvangst,
			GregorianCalendar tijdstempelGemeld, String statusMelding,
			GregorianCalendar tijdstempelStatus, String toelichtingStatus) {
		super();
		this.terugmeldingKern = terugmeldingKern;
		this.afdeling = afdeling;
		this.tijdstempelOntvangst = tijdstempelOntvangst;
		this.tijdstempelGemeld = tijdstempelGemeld;
		this.statusMelding = statusMelding;
		this.tijdstempelStatus = tijdstempelStatus;
		this.toelichtingStatus = toelichtingStatus;
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
	 * Getter voor afdeling.
	 * 
	 * @return afdeling
	 */
	public String getAfdeling() {
		return afdeling;
	}

	/**
	 * Getter voor tijdstempelOntvangst.
	 * 
	 * @return tijdstempelOntvangst
	 */
	public GregorianCalendar getTijdstempelOntvangst() {
		return tijdstempelOntvangst;
	}

	/**
	 * Getter voor tijdstempelGemeld.
	 * 
	 * @return tijdstempelGemeld
	 */
	public GregorianCalendar getTijdstempelGemeld() {
		return tijdstempelGemeld;
	}

	/**
	 * Getter voor statusMelding.
	 * 
	 * @return statusMelding
	 */
	public String getStatusMelding() {
		return statusMelding;
	}
	
	/**
	 * Setter voor statusMelding.
	 * 
	 * @param statusMelding De nieuwe waarde voor statusMelding
	 */
	public void setStatusMelding(String statusMelding) {
		this.statusMelding = statusMelding;
	}

	/**
	 * Getter voor tijdstempelStatus.
	 * 
	 * @return tijdstempelStatus
	 */
	public GregorianCalendar getTijdstempelStatus() {
		return tijdstempelStatus;
	}

	/**
	 * Setter voor terugmeldingKern.
	 * 
	 * @param terugmeldingKern De nieuwe waarde voor terugmeldingKern
	 */
	public void setTerugmeldingKern(TerugmeldingKern terugmeldingKern) {
		this.terugmeldingKern = terugmeldingKern;
	}

	/**
	 * Setter voor afdeling.
	 * 
	 * @param afdeling De nieuwe waarde voor afdeling
	 */
	public void setAfdeling(String afdeling) {
		this.afdeling = afdeling;
	}

	/**
	 * Setter voor tijdstempelOntvangst.
	 * 
	 * @param tijdstempelOntvangst De nieuwe waarde voor tijdstempelOntvangst
	 */
	public void setTijdstempelOntvangst(GregorianCalendar tijdstempelOntvangst) {
		this.tijdstempelOntvangst = tijdstempelOntvangst;
	}

	/**
	 * Setter voor tijdstempelGemeld.
	 * 
	 * @param tijdstempelGemeld De nieuwe waarde voor tijdstempelGemeld
	 */
	public void setTijdstempelGemeld(GregorianCalendar tijdstempelGemeld) {
		this.tijdstempelGemeld = tijdstempelGemeld;
	}

	/**
	 * Setter voor tijdstempelStatus.
	 * 
	 * @param tijdstempelStatus De nieuwe waarde voor tijdstempelStatus
	 */
	public void setTijdstempelStatus(GregorianCalendar tijdstempelStatus) {
		this.tijdstempelStatus = tijdstempelStatus;
	}
	
	/**
	 * Getter voor toelichting.
	 * 
	 * @return toelichtingStatus
	 */
	public String getToelichtingStatus() {
		return toelichtingStatus;
	}
	
	/**
	 * Setter voor toelichtingStatus.
	 * 
	 * @param toelichtingStatus De nieuwe waarde voor toelichtingStatus
	 */
	public void setToelichtingStatus(String toelichtingStatus) {
		this.toelichtingStatus = toelichtingStatus;
	}
	
	
}
