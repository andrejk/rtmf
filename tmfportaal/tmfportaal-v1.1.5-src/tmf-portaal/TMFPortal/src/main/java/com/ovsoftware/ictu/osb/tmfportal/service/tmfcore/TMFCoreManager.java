package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore;

/**
 * Dit is de interface van de TMFCoreManager. Concrete klasses kunnen deze implementeren.
 * 
 *  De volgende invokers kunnen benaderd worden dmv getters en setters:
 *  - intrekInvoker, voor het intrekken van een melding.
 *  - opvraagInvoker, voor het opvragen van een lijst van meldingen of details van een enkele melding.
 *  - terugmeldInvoker, voor het invoeren van een nieuwe terugmelding.
 * 
 * @author OVSoftware
 * @version 0.1 (NOV/DEC 2008)
 */
public interface TMFCoreManager {
	/**
	 * Getter voor intrekInvoker.
	 * 
	 * @return intrekInvoker
	 */
	IntrekInvoker getIntrekInvoker();
	
	/**
	 * Setter voor intrekInvoker.
	 * 
	 * @param intrekInvoker De nieuwe waarde voor intrekInvoker
	 */
	void setIntrekInvoker(IntrekInvoker intrekInvoker);
	
	/**
	 * Getter voor opvraagInvoker.
	 * 
	 * @return opvraagInvoker
	 */
	OpvraagInvoker getOpvraagInvoker();
	
	/**
	 * Setter voor opvraagInvoker.
	 * 
	 * @param opvraagInvoker De nieuwe waarde voor opvraagInvoker
	 */
	void setOpvraagInvoker(OpvraagInvoker opvraagInvoker);
	
	/**
	 * Getter voor terugmeldInvoker.
	 * 
	 * @return terugmeldInvoker
	 */
	TerugmeldInvoker getTerugmeldInvoker();
	
	/**
	 * Setter voor terugmeldInvoker.
	 * 
	 * @param terugmeldInvoker De nieuwe waarde voor terugmeldInvoker
	 */
	void setTerugmeldInvoker(TerugmeldInvoker terugmeldInvoker);		
}
