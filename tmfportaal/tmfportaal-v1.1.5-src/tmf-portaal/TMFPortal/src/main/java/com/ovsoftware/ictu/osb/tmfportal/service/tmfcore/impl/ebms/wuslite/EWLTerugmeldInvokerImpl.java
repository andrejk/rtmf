package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.ebms.wuslite;

import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite.Fault;
import com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite.TmfAanmelden;
import com.ovsoftware.ictu.osb.tmfportal.service.common.Converter;
import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TerugmeldInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;

/**
 * Dit is de EBMS-WUSLite implementatie van de TerugmeldInvoker.
 * 
 * De volgende functies zijn beschikbaar vanuit de view:
 * - intrekking, dit is use case UC044S
 * De functie roept de respectievelijke webservice op de OSB Gateway aan mbv WUSlite.
 * De OSB Gateway roept op diens beurt de TMF Core aan mbv EBMS.
 * 
 * @author OVSoftware
 * @version 0.1 (NOV/DEC 2008)
 */
public class EWLTerugmeldInvokerImpl implements TerugmeldInvoker {
	
	private Logger logger = Logger.getLogger(EWLTerugmeldInvokerImpl.class);
	private String endpointAddress = "http://tmfportal.ovsoftware.com/services";  
	
	/**
	 * Deze functie wordt gebruikt in de view om een melding te plaatsen. De functie roept
	 * een WUSLite webservice op de OSB Gateway aan, waarna de OSB Gateway een webservice aanroept op
	 * TMF Core via EBMS. Zie ook use case UC041S.
	 * 
	 * @param terugmelding Het Terugmelding-object
	 * 
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	public void melding(Terugmelding terugmelding) throws InvokerException {
		
		//creeer de service
		TmfAanmelden ta = EWLCommon.creeerService(endpointAddress);
		
		//voer de aanroep uit
		String result = "";
		try {
			result = ta.terugmelding(terugmelding.getMeldingKenmerk(),
									 Converter.converteerGCnaarXGC(new GregorianCalendar()),
									 terugmelding.getTagBR(),
									 terugmelding.getTagObject(),
									 terugmelding.getIdObject(),
									 terugmelding.getToelichting(),
									 Converter.converteerOALijstnaarObjectAttribuutTypeLijst(terugmelding.getObjectAttribuutLijst()), //wordt list ObjectAttribuutType
									 Converter.converteerCGnaarCT(terugmelding.getContactGegevens()), //wordt ContactType
									 Converter.converteerALijstNaarATLijst(terugmelding.getBijlageLijst())); //wordt list AttachmentType
		} catch (Fault e) {
			logger.error("Exception tijdens uitvoeren intrekking.", e);
			throw new InvokerException(e);
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
	 * @return getter
	 */
	public String getEndpointAddress() {
		return endpointAddress;
	}
}

