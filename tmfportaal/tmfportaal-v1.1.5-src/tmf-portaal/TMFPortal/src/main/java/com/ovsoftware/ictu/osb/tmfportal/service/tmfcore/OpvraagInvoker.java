package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;

/**
 * Dit is de interface van de OpvraagInvoker. Concrete klasses kunnen deze implementeren.
 * 
 * De volgende functies zijn beschikbaar vanuit de view:
 * - ophalenMeldingen, dit is de use case UC046S
 * - ophalenMeldingDetails, dit is de use case UC045S
 * Beide functies roepen de respectievelijke webservice op de OSB Gateway aan.
 * De OSB Gateway roept op diens beurt de TMF Core aan.
 * 
 * @author OVSoftware
 * @version 0.1 (NOV/DEC 2008)
 */
public interface OpvraagInvoker {

	/**
	 * Deze functie wordt gebruikt in de view om een lijst met eerder gedane terugmeldingen
	 * op te vragen. De functie roept een webservice op de OSB Gateway aan, waarna
	 * de OSB Gateway een webservice aanroept op TMF Core. Zie ook use case UC046S.
	 * 
	 * @param status			De status waarop gefilterd moet worden, mag null zijn.
	 * @param vanDatum			Het begin van het tijdbereik waarop gefilterd moet worden, mag null zijn.
	 * @param totDatum			Het eind van het tijdbereik waarop gefilterd moet worden, mag null zijn.
	 * @param meldingKenmerk	Het eigen kenmerk van de organisatie die de melding plaatste waarop gefilterd moet worden, mag null zijn.
	 * @param tmfKenmerk		Het door TMF Core uitgegeven kenmerk waarop gefilterd moet worden, mag null zijn.
	 * @return 					De lijst met terugmeldingen die voldoet aan de opgegeven parameters.
	 * @throws InvokerException Indien er een fout is opgetreden.
	 */
	ArrayList<Terugmelding> ophalenMeldingen(String status,
													GregorianCalendar vanDatum,
													GregorianCalendar totDatum,
													String meldingKenmerk,
													String tmfKenmerk) throws InvokerException;
	
	/**
	 * Deze functie wordt gebruikt in de view om de details van een enkele melding
	 * op te vragen. De functie roept een webservice op de OSB Gateway aan, waarna
	 * de OSB Gateway een webservice aanroept op TMF Core. Zie ook use case UC045S.
	 * 
	 * @param meldingKenmerk	Het eigen kenmerk van de organisatie die de melding plaatste waarop gefilterd moet worden.
	 * @return 					De details van de gevonden melding of null indien geen melding gevonden kon worden.
	 * @throws InvokerException Indien er een fout is opgetreden.
	 */
	TerugmeldingDetails ophalenMeldingDetails(String meldingKenmerk) throws InvokerException;
}
