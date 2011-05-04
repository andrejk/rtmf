package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.wus.wuslite;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.BasisregistratieType;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.FaultMsg;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.StelselBevragen;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.StelselBevragenService;
import com.ovsoftware.ictu.osb.tmfportal.service.common.Converter;
import com.ovsoftware.ictu.osb.tmfportal.service.common.CustomHandlerResolver;
import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.MessageLoggingHandler;
import com.ovsoftware.ictu.osb.tmfportal.service.common.SOAPActionFixerHandler;
import com.ovsoftware.ictu.osb.tmfportal.service.common.WebserviceSettings;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.BasisRegistratieInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

/**
 * Concrete WUS-WUSlite implementatie van een AttribuutInvoker. Deze
 * klasse roept mbv WUSlite een webservice-functie aan op de OSB
 * Gateway waarna deze een WUS webservice-functie oproept van de
 * StelselCatalogus.
 * 
 * @author michael.borg@ordina.nl
 *
 */
public class WWLBasisregistratieInvokerImpl implements BasisRegistratieInvoker {

	private Logger logger = Logger.getLogger(WWLBasisregistratieInvokerImpl.class);
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
	 * Maakt een service aan mbv het settingsbestand en retourneert deze.
	 * 
	 * @return Een nieuwe instantie van de StelselBevragenService
	 * 
 	 * @throws InvokerException Indien er een fout is opgetreden tijdens het aanmaken of gebruiken van de webservice
	 */
	private StelselBevragen creeerService() throws InvokerException {
		
		//roep de service aan
		URL wsdlURL;
		wsdlURL = WWLBasisregistratieInvokerImpl.class.getResource(WebserviceSettings.STELSELCATALOGUS_WSDL);
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

	@Override
	public ArrayList<BasisRegistratie> getBasisRegistraties()
			throws InvokerException {
		//creeer een service
		StelselBevragen sbs = creeerService();
		
		//roep de service aan
		List<BasisregistratieType> basisregistratieList = null;
		try {
			basisregistratieList = sbs.getBasisregistratieList();
		} catch (FaultMsg e) {
			logger.error("Exception tijdens uitvoeren getBasisregistratieList.", e);
			throw new InvokerException(e);
		} catch (Exception e) {
			logger.error("Exception tijdens uitvoeren getBasisregistratieList.", e);
			throw new InvokerException(e);
		}
		
		//pak ot uit
		ArrayList<BasisRegistratie> brList = Converter.converteerBasisregistratieTypeLijstnaarBRLijst(basisregistratieList);
		
		return brList;
	}

}
