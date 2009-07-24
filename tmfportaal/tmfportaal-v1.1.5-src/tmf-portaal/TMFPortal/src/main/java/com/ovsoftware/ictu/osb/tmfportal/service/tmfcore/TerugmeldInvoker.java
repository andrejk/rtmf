package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;

/**
 * Dit is de interface van de IntrekInvoker. Concrete klasses kunnen deze implementeren.
 * 
 * De volgende functies zijn beschikbaar vanuit de view:
 * - intrekking, dit is use case UC044S
 * De functie roept de respectievelijke webservice op de OSB Gateway aan.
 * De OSB Gateway roept op diens beurt de TMF Core aan.
 * 
 * @author OVSoftware
 * @version 0.1 (NOV/DEC 2008)
 */
public interface TerugmeldInvoker {
	
	/**
	 * Deze functie wordt gebruikt in de view om een melding te plaatsen. De functie roept
	 * een webservice op de OSB Gateway aan, waarna de OSB Gateway een webservice aanroept op
	 * TMF Core. Zie ook use case UC041S.
	 * 
	 * @param terugmelding	Nieuwe terugmelding waarvan de TerugmeldingKern zal worden overschreven
	 * @throws InvokerException Indien er een fout is opgetreden
	 */
	void melding(Terugmelding terugmelding) throws InvokerException;
}
