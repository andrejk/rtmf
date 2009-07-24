package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.springconfig;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.ObjectInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

/**
 * Spring config implementatie van een ObjectInvoker welke de objecten voor
 * een bepaalde basisregistratie laad uit een resource-bestand (xml).
 * 
 * @author ktinselboer
 *
 */
public class SpringObjectInvokerImpl implements ObjectInvoker {
	
	private Logger logger = Logger.getLogger(SpringObjectInvokerImpl.class);

	/**
	 * Retourneert een lijst met BRObject-objecten voor een bepaalde BasisRegistratie.
	 * 
	 * @param basisRegistratie De basisregistratie waarvoor de lijst moet worden opgehaald
	 * 
	 * @return Een lijst met BRObject-objecten voor de gegeven BasisRegistratie
	 * @throws InvokerException als het spring configuratie bestand niet gevonden kon worden
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<BRObject> getObjectTypen(BasisRegistratie basisRegistratie) throws InvokerException {
		Map<String, BRObject> objecten = null;
		String bestandsnaam = basisRegistratie.getTag()+"-objecten.xml";
		try{
			ApplicationContext ac = new ClassPathXmlApplicationContext(bestandsnaam);
			objecten = ac.getBeansOfType(BRObject.class);
		} catch (Exception e) {
			logger.error("Kon '" + bestandsnaam + "' niet openen!");
			throw new InvokerException(e);
		}
	
		return new ArrayList(objecten.values());
	}

}
