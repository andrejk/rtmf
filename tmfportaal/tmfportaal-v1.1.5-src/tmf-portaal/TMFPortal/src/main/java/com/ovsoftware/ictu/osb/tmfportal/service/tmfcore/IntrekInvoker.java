package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;

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
public interface IntrekInvoker {
	/**
	 * Deze functie wordt gebruikt in de view om een melding in te trekken. De functie roept
	 * een webservice op de OSB Gateway aan, waarna de OSB Gateway een webservice aanroept op
	 * TMF Core. Zie ook use case UC044S.
	 * 
	 * @param betreftTmfKenmerk			Het kenmerk als uitgegeven door TMF Core.
	 * @param toelichting				De toelichting waarom de intrekking dient plaats te vinden.
	 * 
	 * @throws InvokerException Indien er een fout is opgetreden.
	 */
	void intrekking(String betreftTmfKenmerk, String toelichting) throws InvokerException;
}
