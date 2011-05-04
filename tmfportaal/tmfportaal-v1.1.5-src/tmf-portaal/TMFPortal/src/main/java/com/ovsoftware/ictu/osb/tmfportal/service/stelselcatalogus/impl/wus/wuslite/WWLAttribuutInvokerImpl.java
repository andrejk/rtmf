package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.wus.wuslite;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.FaultMsg;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.ObjectType;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.ObjectValueType;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.StelselBevragen;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.StelselBevragenService;
import com.ovsoftware.ictu.osb.tmfportal.service.common.Converter;
import com.ovsoftware.ictu.osb.tmfportal.service.common.CustomHandlerResolver;
import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.MessageLoggingHandler;
import com.ovsoftware.ictu.osb.tmfportal.service.common.SOAPActionFixerHandler;
import com.ovsoftware.ictu.osb.tmfportal.service.common.WebserviceSettings;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.AttribuutInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectData;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

/**
 * Concrete WUS-WUSlite implementatie van een AttribuutInvoker. Deze
 * klasse roept mbv WUSlite een webservice-functie aan op de OSB
 * Gateway waarna deze een WUS webservice-functie oproept van de
 * StelselCatalogus.
 * 
 * @author ktinselboer
 *
 */
public class WWLAttribuutInvokerImpl implements AttribuutInvoker {

	private Logger logger = Logger.getLogger(WWLAttribuutInvokerImpl.class);
	private String endpointAddress = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1";  
	
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
	 * Dit is de WUS-WUSlite implementatie van de AttribuutInvoker.
	 * 
	 * Haalt een lijst met attributen op voor een bepaalde basisregistratie
	 * en object. Deze lijst en wat metadata mbt het object worden geretourneerd
	 * in de vorm van een BRObjectData-object. LET OP: Zonder huidige waardes.
	 * 
	 * @param basisRegistratie De basisregistratie waarvoor de attributen opgehaald moeten worden
	 * @param bRObject Het object waarvoor de attributen opgehaald moeten worden
	 * 
	 * @return Een BRObjectData-object met informatie over het object en een lijst met BRObjectAttribuut-objecten
	 *
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	@Override
	public BRObjectData getBasisAttributen(
			BasisRegistratie basisRegistratie,
			BRObject bRObject) throws InvokerException {
		
		//creeer een service
		StelselBevragen sbs = creeerService();
		
		//roep de service aan
		ObjectType ot = null;
		try {
			ot = sbs.getObjectInfo(basisRegistratie.getTag(), bRObject.getTag());
		} catch (FaultMsg e) {
			logger.error("Exception tijdens uitvoeren getBasisAttributen.", e);
			throw new InvokerException(e);
		} catch (Exception e) {
			logger.error("Exception tijdens uitvoeren getBasisAttributen.", e);
			throw new InvokerException(e);
		}
		
		//pak ot uit
		BRObjectData brod = Converter.converteerOTnaarBROD(ot);
		
		return brod;
	}

	/**
	 * Dit is de WUS-WUSlite implementatie van de AttribuutInvoker.
	 * 
	 * Haalt een lijst met attributen op voor een bepaalde basisregistratie
	 * en object. Deze lijst en wat metadata mbt het object worden geretourneerd
	 * in de vorm van een BRObjectData-object. LET OP: Met huidige waardes.
	 * 
	 * @param basisRegistratie De basisregistratie waarvoor de attributen opgehaald moeten worden
	 * @param bRObject Het object waarvoor de attributen opgehaald moeten worden
	 * @param objectID Het id van het object (bijv BSN-nummer)
	 * 
	 * @return Een BRObjectData-object met informatie over het object en een lijst met BRObjectAttribuut-objecten
	 *
	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	@Override
	public BRObjectData getBasisAttributenValues(
			BasisRegistratie basisRegistratie,
			BRObject bRObject,
			String objectID) throws InvokerException {

		//creeer een service
		StelselBevragen sbs = creeerService();
		
		//roep de service aan
		ObjectValueType ovt = null;
		try {
			ovt = sbs.bevragen(basisRegistratie.getTag(), bRObject.getTag(), objectID);
		} catch (FaultMsg e) {
			logger.error("Exception tijdens uitvoeren getBasisAttributenValues.", e);
			throw new InvokerException(e);
		} catch (Exception e) {
			logger.error("Exception tijdens uitvoeren getBasisAttributenValues.", e);
			throw new InvokerException(e);
		}
		
		//pak ovt uit
		BRObjectData brod = Converter.converteerOVTnaarBROD(ovt);
		
		return brod;
		
	}
	
	/**
	 * Maakt een service aan mbv het settingsbestand en retourneert deze.
	 * 
	 * @return Een nieuwe instantie van de StelselBevragenService
	 * 
 	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	private StelselBevragen creeerService() throws InvokerException {
		
		//roep de service aan
		URL wsdlURL;
		wsdlURL = WWLAttribuutInvokerImpl.class.getResource(WebserviceSettings.STELSELCATALOGUS_WSDL);
		if (wsdlURL == null) {
			throw new InvokerException();
		}
			
		QName serviceQName = new QName(WebserviceSettings.STELSELCATALOGUS_NAMESPACE,
									   WebserviceSettings.STELSELCATALOGUS_SERVICE);
		StelselBevragenService sbss = new StelselBevragenService(wsdlURL, serviceQName);
		
		//voeg een logging handler toe
		CustomHandlerResolver chr = new CustomHandlerResolver();
		chr.addHandler(new SOAPActionFixerHandler());
		chr.addHandler(new MessageLoggingHandler());
		sbss.setHandlerResolver(chr);
		
		StelselBevragen sbs = sbss.getStelselBevragenSOAP();
		BindingProvider bp = (BindingProvider) sbs;
		Map<String, Object> rc = bp.getRequestContext();
		rc.put (BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		
		return sbs;
	}

}
