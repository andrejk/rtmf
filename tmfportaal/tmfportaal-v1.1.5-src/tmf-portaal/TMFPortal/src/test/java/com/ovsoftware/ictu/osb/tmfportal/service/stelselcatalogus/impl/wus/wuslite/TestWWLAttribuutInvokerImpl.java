package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.wus.wuslite;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.WebserviceSettings;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.AttribuutInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectAttribuut;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectData;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

public class TestWWLAttribuutInvokerImpl extends TestCase {
	
	private Logger logger = Logger.getLogger(TestWWLAttribuutInvokerImpl.class);
	private ApplicationContext ac;

	public void setUp() throws IOException {
		ac = new FileSystemXmlApplicationContext("src/test/resources/springapp-mock-servlet.xml");
	}

	/**
	 * Test de getBasisAttributen-functie van WWLAttribuutInvokerImpl.
	 */
	public void testGetBasisAttributen() {
		WebserviceSettings.setSettingsFilename("settings.xml");
		WWLAttribuutInvokerImpl wwl = (WWLAttribuutInvokerImpl) ac.getBean("attrInv");
		BasisRegistratie basisRegistratie = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		BRObject bRObject = new BRObject("PER", "Persoon");
		
		BRObjectData brod = null;
		try {
			brod = wwl.getBasisAttributen(basisRegistratie, bRObject);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testGetBasisAttributen", ie);
		}
		
		ArrayList<BRObjectAttribuut> broaLijst = brod.getBroaLijst();
		if (broaLijst!=null) {
			logger.trace("Size = " + broaLijst.size());
			for (BRObjectAttribuut broa : broaLijst) {
				logger.trace(broa.getTag() + " -> " + broa.getNaam());
			}
		} else {
			logger.trace("broaLijst is null!");
		}
		assertNotNull("broaLijst is null!", broaLijst);
	}
	
	/**
	 * Test de getBasisAttributenValues-functie van WWLAttribuutInvokerImpl.
	 */
	public void testGetBasisAttributenValues() {
		WebserviceSettings.setSettingsFilename("settings.xml");
		WWLAttribuutInvokerImpl wwl = (WWLAttribuutInvokerImpl) ac.getBean("attrInv");
		BasisRegistratie basisRegistratie = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		BRObject bRObject = new BRObject("PER", "Persoon");
		
		BRObjectData brod = null;
		try {
			brod = wwl.getBasisAttributenValues(basisRegistratie, bRObject, "123");
		} catch (InvokerException ie) {
			assertNull("InvokerException in testGetBasisAttributenValues", ie);
		}
		
		ArrayList<BRObjectAttribuut> broaLijst = brod.getBroaLijst();
		if (broaLijst!=null) {
			logger.trace("Size = " + broaLijst.size());
			for (BRObjectAttribuut broa : broaLijst) {
				logger.trace(broa.getTag() + " -> " + broa.getNaam());
			}
		} else {
			logger.trace("broaLijst is null!");
		}
		assertNotNull("broaLijst is null!", broaLijst);
	}
	
	/**
	 * Test wat er gebeurt als er een malformed url wordt gebruikt in WWLAttribuutInvokerImpl
	 * in combinatie met de functie getAttributeValues.
	 */
	public void testMalformedURLWithoutValues() {
		AttribuutInvoker wwl = (WWLAttribuutInvokerImpl) ac.getBean("attrInvMallFormedURL");
		BasisRegistratie basisRegistratie = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		BRObject bRObject = new BRObject("PER", "Persoon");
		
		try {
			wwl.getBasisAttributen(basisRegistratie, bRObject);
		} catch (InvokerException ie) {
			WebserviceSettings.setSettingsFilename("settings.xml");
			return;
		}
		
		fail("Hier had al een exceptie moeten optreden!");
		WebserviceSettings.setSettingsFilename("settings.xml");
	}
	
	
	/**
	 * Test wat er gebeurt als er een malformed url wordt gebruikt in WWLAttribuutInvokerImpl
	 * in combinatie met de functie getAttributeValues.
	 */
	public void testMalformedURLWithValues() {
		AttribuutInvoker wwl = (WWLAttribuutInvokerImpl) ac.getBean("attrInvMallFormedURL");
		BasisRegistratie basisRegistratie = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		BRObject bRObject = new BRObject("PER", "Persoon");
		
		try {
			wwl.getBasisAttributenValues(basisRegistratie, bRObject, "123");
		} catch (InvokerException ie) {
			WebserviceSettings.setSettingsFilename("settings.xml");
			return;
		}
		
		fail("Hier had al een exceptie moeten optreden!");
		WebserviceSettings.setSettingsFilename("settings.xml");
	}
}
