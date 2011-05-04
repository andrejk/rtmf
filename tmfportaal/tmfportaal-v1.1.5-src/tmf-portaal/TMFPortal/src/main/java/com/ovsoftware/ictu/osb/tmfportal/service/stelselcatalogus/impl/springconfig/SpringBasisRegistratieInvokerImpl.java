package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.springconfig;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.BasisRegistratieInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

/**
 * Spring config implementatie van een BasisRegistratieInvoker welke de
 * basisregistraties laad uit een resource-bestand (xml).
 * 
 * @author ktinselboer
 *
 */
public class SpringBasisRegistratieInvokerImpl implements BasisRegistratieInvoker {

	private ArrayList<BasisRegistratie> springBeanBasisRegistraties;
	private ApplicationContext ac;
	
	/**
	 * Constructor waarin een lijst met BasisRegistratie-objecten uit een XML-bestand
	 * wordt geladen.
	 * 
	 * @param applicationContextLocation De naam van het XML-bestand
	 */
	@SuppressWarnings("unchecked")
	public SpringBasisRegistratieInvokerImpl(String applicationContextLocation) {
		ac = new ClassPathXmlApplicationContext(applicationContextLocation);
		Map<String, BasisRegistratie> br = ac.getBeansOfType(BasisRegistratie.class);
		this.springBeanBasisRegistraties = new ArrayList<BasisRegistratie>(br.values());
	}
	
	/**
	 * Een lege constructor ivm spring beans.
	 */
	public SpringBasisRegistratieInvokerImpl() {}

	/**
	 * Retourneert de lijst met BasisRegistratie-objecten.
	 * 
	 * @return De lijst met BasisRegistratie-objecten
	 */
	@Override
	public ArrayList<BasisRegistratie> getBasisRegistraties() {
		return springBeanBasisRegistraties;
	}

}
