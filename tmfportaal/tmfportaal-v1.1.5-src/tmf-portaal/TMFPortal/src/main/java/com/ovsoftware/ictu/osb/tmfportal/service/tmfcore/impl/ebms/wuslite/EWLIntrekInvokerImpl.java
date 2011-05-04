package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.ebms.wuslite;

import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite.Fault;
import com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite.TmfAanmelden;
import com.ovsoftware.ictu.osb.tmfportal.service.common.Converter;
import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.IntrekInvoker;

/**
 * Dit is de EBMS-WUSLite implementatie van de OpvraagInvoker.
 * 
 * De volgende functies zijn beschikbaar vanuit de view:
 * - intrekking, dit is use case UC044S
 * De functie roept de respectievelijke webservice op de OSB Gateway aan.
 * De OSB Gateway roept op diens beurt de TMF Core aan.
 * 
 * @author OVSoftware
 * @version 0.1 (NOV/DEC 2008)
 */
public class EWLIntrekInvokerImpl implements IntrekInvoker {

	private Logger logger = Logger.getLogger(EWLIntrekInvokerImpl.class);
	private String endpointAddress = "http://tmfportal.ovsoftware.com/services";  
	
	/**
	 * Deze functie wordt gebruikt in de view om een melding in te trekken. De functie roept
	 * een WUSLite webservice op de OSB Gateway aan, waarna de OSB Gateway een webservice aanroept op
	 * TMF Core via EBMS. Zie ook use case UC044S.
	 * 
	 * @param betreftTmfKenmerk			Het kenmerk als uitgegeven door TMF Core.
	 * @param toelichting				De toelichting waarom de intrekking dient plaats te vinden.
	 * 
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	public void intrekking(String betreftTmfKenmerk,
						   String toelichting) throws InvokerException {
		
		//creeer de service
		TmfAanmelden ta = EWLCommon.creeerService(endpointAddress);
		
		//voer de aanroep uit
		String result = "";
		try {
			result = ta.intrekking("Intrekking-" + betreftTmfKenmerk + "-" + (new GregorianCalendar()).getTimeInMillis(),
					               Converter.converteerGCnaarXGC(new GregorianCalendar()),
					               betreftTmfKenmerk,
					               toelichting);
		} catch (Fault e) {
			logger.trace("Exception tijdens uitvoeren intrekking.", e);
			throw new InvokerException();
		} catch (Exception e) {
			logger.error("Exception tijdens uitvoeren intrekking.", e);
			throw new InvokerException(e);
		}
		
		//log result
		logger.trace("Resultaat van intrekking = \n");
		logger.trace("'" + result + "'\n");		
	}

	/**
	 * @param endpointAddress setter
	 */
	public void setEndpointAddress(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * @return the endPointAddress
	 */
	public String getEndpointAddress() {
		return endpointAddress;
	}

}
