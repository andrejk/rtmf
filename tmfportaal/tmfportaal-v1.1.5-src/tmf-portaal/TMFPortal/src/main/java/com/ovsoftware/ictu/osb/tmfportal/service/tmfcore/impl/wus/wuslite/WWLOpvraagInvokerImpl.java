package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.wus.wuslite;

import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.FaultMsg;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.OphaalService;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.OphaalService_Service;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.OphalenMeldingKenmerkRequestType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.OphalenMeldingStatusRequestType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.OphalenStatusRequestType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.TerugmeldMetaType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.TerugmeldResponseTypeList;
import com.ovsoftware.ictu.osb.tmfportal.service.common.Converter;
import com.ovsoftware.ictu.osb.tmfportal.service.common.CustomHandlerResolver;
import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.MessageLoggingHandler;
import com.ovsoftware.ictu.osb.tmfportal.service.common.SOAPActionFixerHandler;
import com.ovsoftware.ictu.osb.tmfportal.service.common.WebserviceSettings;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.OpvraagInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;

/**
 * Dit is de WUS-WUSLite implementatie van de OpvraagInvoker. De volgende functies zijn
 * beschikbaar vanuit de view:
 * - ophalenMeldingen, dit is de use case UC046S
 * - ophalenMeldingDetails, dit is de use case UC045S
 * Beide functies roepen dmv WUSLite de respectievelijke webservice op de OSB Gateway aan.
 * De OSB Gateway roept op diens beurt dmv WUS de TMF Core aan.
 * 
 * @author OVSoftware
 * @version 0.1 (NOV/DEC 2008)
 */
public class WWLOpvraagInvokerImpl implements OpvraagInvoker {

	private Logger logger = Logger.getLogger(WWLOpvraagInvokerImpl.class);
	private String endpointAddress = "http://tmfportal.ovsoftware.com/services";  
	
	/**
	 * @return getter
	 */
	public String getEndpointAddress() {
		return endpointAddress;
	}

	/**
	 * @param endpointAddress setter
	 */
	public void setEndpointAddress(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Deze functie wordt gebruikt in de view om een lijst met eerder gedane terugmeldingen
	 * op te vragen. De functie roept een webservice op de OSB Gateway aan dmv WUSLite, waarna
	 * de OSB Gateway een webservice aanroept op TMF Core dmv WUS. Zie ook use case UC046S.
	 * 
	 * @param status			De status waarop gefilterd moet worden, mag null zijn.
	 * @param vanDatum			Het begin van het tijdbereik waarop gefilterd moet worden, mag null zijn.
	 * @param totDatum			Het eind van het tijdbereik waarop gefilterd moet worden, mag null zijn.
	 * @param meldingKenmerk	Het eigen kenmerk van de organisatie die de melding plaatste waarop gefilterd moet worden, mag null zijn.
	 * @param tmfKenmerk		Het door TMF Core uitgegeven kenmerk waarop gefilterd moet worden, mag null zijn.
	 * @return 					De lijst met terugmeldingen die voldoet aan de opgegeven parameters.
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	public ArrayList<Terugmelding> ophalenMeldingen(String status,
													GregorianCalendar vanDatum,
													GregorianCalendar totDatum,
													String meldingKenmerk,
													String tmfKenmerk) throws InvokerException {
	
		//creeer een OphalenMeldingStatusRequestType
		OphalenMeldingStatusRequestType omsrt = new OphalenMeldingStatusRequestType();
		omsrt.setMeldingKenmerk(meldingKenmerk);
		omsrt.setTmfKenmerk(tmfKenmerk);
			OphalenStatusRequestType osrtTmp = new OphalenStatusRequestType();
			osrtTmp.setStatus(status);
			osrtTmp.setVanDatum(Converter.converteerGCnaarXGC(vanDatum));
			osrtTmp.setTotDatum(Converter.converteerGCnaarXGC(totDatum));
		omsrt.setMeldingStatusRequest(osrtTmp);
	
		//haal een service instantie op
		OphaalService os = creeerService();
		
		//voer een functie uit
		TerugmeldResponseTypeList trtl = null;
		try {
			trtl = os.ophalenMeldingStatus(osrtTmp, meldingKenmerk, tmfKenmerk);
		} catch (FaultMsg e) {
			logger.error("Exception tijdens uitvoeren ophalen lijst met meldingen.", e);
			throw new InvokerException();
		} catch (Exception e) {
			logger.error("Exception tijdens uitvoeren ophalen lijst met meldingen.", e);
			throw new InvokerException(e);
		}
		
		//pak de reactie uit
		ArrayList<Terugmelding> terugmeldingLijst = Converter.converteerTRTLnaarTerugmeldingLijst(trtl);
		
		//retourneer het resultaat
		return terugmeldingLijst;
	}
	
	/**
	 * Deze functie wordt gebruikt in de view om de details van een enkele melding
	 * op te vragen. De functie roept een webservice op de OSB Gateway aan dmv WUSLite, waarna
	 * de OSB Gateway een webservice aanroept op TMF Core dmv WUS. Zie ook use case UC045S.
	 * 
	 * @param meldingKenmerk	Het eigen kenmerk van de organisatie die de melding plaatste waarop gefilterd moet worden.
	 * @return 					De details van de gevonden melding of null indien geen melding gevonden kon worden.
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	public TerugmeldingDetails ophalenMeldingDetails(String meldingKenmerk) throws InvokerException {
		//creeer een OMKRT
		OphalenMeldingKenmerkRequestType omkrtVraag = new OphalenMeldingKenmerkRequestType();
		omkrtVraag.setMeldingKenmerk(meldingKenmerk);
		
		//haal een service instantie op
		OphaalService os = creeerService();
		
		//voer een functie uit
		TerugmeldMetaType tmt = null;
		try {
			tmt = os.ophalenMeldingKenmerk(meldingKenmerk);
		} catch (FaultMsg e) {
			logger.error("Exception tijdens uitvoeren ophalen meldingdetails.", e);
			throw new InvokerException();
		} catch (Exception e) {
			logger.error("Exception tijdens uitvoeren ophalen meldingdetails.", e);
			throw new InvokerException(e);
		}
		
		//pak de reactie uit
		TerugmeldingDetails terugmeldingDetails = Converter.converteerTMTnaarTerugmeldingDetails(tmt);
		
		//retourneer het resultaat
		return terugmeldingDetails;
	}

	/**
	 * Creert een service en retourneert deze.
	 * 
	 * @return Een nieuwe instantie van OphaalService
	 * 
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	private OphaalService creeerService() throws InvokerException{		
		//roep de service aan
		URL wsdlURL;
		wsdlURL = WWLOpvraagInvokerImpl.class.getResource(WebserviceSettings.TMFCORE_WUS_WSDL); 
		if (wsdlURL == null) {
			throw new InvokerException();
		}
			
		QName serviceQName = new QName(WebserviceSettings.OPHAALSERVICE_NAMESPACE,
									   WebserviceSettings.TMFCORE_WUS_SERVICE);
		OphaalService_Service oss = new OphaalService_Service(wsdlURL, serviceQName);
		
		//voeg een logging handler toe
		CustomHandlerResolver chr = new CustomHandlerResolver();
		chr.addHandler(new SOAPActionFixerHandler());
		chr.addHandler(new MessageLoggingHandler());
		oss.setHandlerResolver(chr);
		
		OphaalService os = oss.getOphaalServiceSOAP();
		BindingProvider bp = (BindingProvider) os;
		Map<String, Object> rc = bp.getRequestContext();
		rc.put (BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		
		return os;
	}

}
