package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.mock;

import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;

/**
 * TerugmeldingCompleet wordt alleen gebruikt in MockOpvraagInvokerImpl om
 * de details van een Terugmelding en de Terugmelding zelf bij te houden.
 * 
 * @author ktinselboer
 *
 */
public class TerugmeldingCompleet {
	private Terugmelding terugmelding;
	private TerugmeldingDetails terugmeldingDetails;
	
	/**
	 * Lege constructor ivm Spring beans.
	 */
	public TerugmeldingCompleet(){}
	
	/**
	 * Constructor voor TerugmeldingCompleet.
	 * 
	 * @param terugmelding De terugmelding
	 * @param terugmeldingDetails De details behorende bij de terugmelding
	 */
	public TerugmeldingCompleet(Terugmelding terugmelding,
								TerugmeldingDetails terugmeldingDetails) {
		this.terugmelding = terugmelding;
		this.terugmeldingDetails = terugmeldingDetails;
	}

	/**
	 * Getter voor terugmelding.
	 * 
	 * @return terugmelding
	 */
	public Terugmelding getTerugmelding() {
		return terugmelding;
	}

	/**
	 * Setter voor terugmelding.
	 * 
	 * @param terugmelding De nieuwe waarde voor terugmelding
	 */
	public void setTerugmelding(Terugmelding terugmelding) {
		this.terugmelding = terugmelding;
	}

	/**
	 * Getter voor terugmeldingDetails.
	 * 
	 * @return terugmeldingDetails
	 */
	public TerugmeldingDetails getTerugmeldingDetails() {
		return terugmeldingDetails;
	}

	/**
	 * Setter voor terugmeldingDetails.
	 * 
	 * @param terugmeldingDetails De nieuwe waarde voor terugmeldingDetails
	 */
	public void setTerugmeldingDetails(TerugmeldingDetails terugmeldingDetails) {
		this.terugmeldingDetails = terugmeldingDetails;
	}

}
