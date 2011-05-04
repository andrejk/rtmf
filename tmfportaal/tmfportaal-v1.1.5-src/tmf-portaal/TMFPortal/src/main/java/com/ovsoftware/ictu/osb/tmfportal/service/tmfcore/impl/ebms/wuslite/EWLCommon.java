package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.ebms.wuslite;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite.TmfAanmelden;
import com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite.TmfAanmelden_Service;
import com.ovsoftware.ictu.osb.tmfportal.service.common.CustomHandlerResolver;
import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.MessageLoggingHandler;
import com.ovsoftware.ictu.osb.tmfportal.service.common.NameSpaceFixerHandler;
import com.ovsoftware.ictu.osb.tmfportal.service.common.SOAPActionFixerHandler;
import com.ovsoftware.ictu.osb.tmfportal.service.common.WebserviceSettings;

/**
 * Gedeelde klasse welke gebruikt wordt voor het opbouwen van een EBMS-WUSlite
 * verbinding met de TMFCore. Wordt gebruikt door de EWLIntrekInvokerImpl en de
 * EWLTerugmeldInvokerImpl.
 * 
 * @author ktinselboer
 *
 */
public class EWLCommon {
	
	private static Logger logger = Logger.getLogger(EWLIntrekInvokerImpl.class);
	
	/**
	 * Nep constructor die voorkomt dat je een instantie kunt aanmaken
	 * van deze utility klasse.
	 * 
	 * @throws UnsupportedOperationException
	 */
	protected EWLCommon() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Creert een TmfAanmelden service.
	 * 
	 * @param endpointAddress de url van de wus-lite service op de OSBGateway
	 * @return Een TmfAanmelden-service voor terugmeldingen en intrekkingen
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	public static TmfAanmelden creeerService(String endpointAddress) throws InvokerException {		
		//roep de service aan
		URL wsdlURL;
		wsdlURL = EWLCommon.class.getResource(WebserviceSettings.TMFCORE_EBMS_WSDL); 
		if (wsdlURL == null) {
			logger.error("Corrupt WSDL location: " + WebserviceSettings.TMFCORE_EBMS_WSDL);
			throw new InvokerException();
		}
			
		QName serviceQName = new QName(WebserviceSettings.AANMELDEN_NAMESPACE,
									   WebserviceSettings.TMFCORE_EBMS_SERVICE);
		TmfAanmelden_Service ass = new TmfAanmelden_Service(wsdlURL, serviceQName);
		
		//voeg een logging handler toe
		CustomHandlerResolver chr = new CustomHandlerResolver();
		chr.addHandler(new SOAPActionFixerHandler());
		chr.addHandler(new MessageLoggingHandler());
		chr.addHandler(new NameSpaceFixerHandler());
		ass.setHandlerResolver(chr);
				
		TmfAanmelden ta = ass.getTmfAanmelden();
		BindingProvider bp = (BindingProvider) ta;
		Map<String, Object> rc = bp.getRequestContext();
		rc.put (BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

		return ta;
	}
	
}
