package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectData;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

/**
 * Interface welke geimplementeerd dient te worden door een concrete
 * implementatie bijvoorbeeld MockAttribuutInvokerImpl of 
 * WWLAttribuutInvokerImpl.
 * 
 * @author OVSoftware
 *
 */
public interface AttribuutInvoker {
	
	/**
	 * Haalt een lijst met attributen op voor een bepaald object in een bepaalde basisregistratie.
	 * 
	 * @param basisRegistratie De basisregistratie waaruit het object komt
	 * @param basisRegistratieObject Het object waarvan de attributen opgevraagd worden
	 * @return Informatie over het object, alsmede de lijst met attributen
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	BRObjectData getBasisAttributen(BasisRegistratie basisRegistratie, BRObject basisRegistratieObject) throws InvokerException;
	
	/**
	 * Haalt een lijst met attributen en waardes op voor een bepaald object in een bepaalde basisregistratie.
	 * 
	 * @param basisRegistratie De basisregistratie waaruit het object komt
	 * @param basisRegistratieObject Het object waarvan de attributen en waardes opgevraagd worden
	 * @param objectKey Het identificatie-nummer van een specifiek object (bijv BSN-nummer van een persoon oid)
	 * @return Informatie over het object, alsmede de lijst met attributen en waardes
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	BRObjectData getBasisAttributenValues(BasisRegistratie basisRegistratie, BRObject basisRegistratieObject, String objectKey) throws InvokerException;
}
