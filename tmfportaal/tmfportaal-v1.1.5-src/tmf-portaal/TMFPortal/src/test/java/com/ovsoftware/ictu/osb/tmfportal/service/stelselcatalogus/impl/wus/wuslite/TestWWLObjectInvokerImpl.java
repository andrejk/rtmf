package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.wus.wuslite;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.WebserviceSettings;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

public class TestWWLObjectInvokerImpl extends TestCase {

	private Logger logger = Logger.getLogger(TestWWLObjectInvokerImpl.class);
	private ApplicationContext ac;

	public void setUp() throws IOException {
		ac = new FileSystemXmlApplicationContext(
				"src/test/resources/springapp-mock-servlet.xml");
	}

	/**
	 * Test de getObjectTypen-functie van WWLObjectInvokerImpl.
	 */
	public void testGetObjectTypen() {
		WebserviceSettings.setSettingsFilename("settings.xml");
		WWLObjectInvokerImpl wwl = (WWLObjectInvokerImpl) ac.getBean("objInv");
		BasisRegistratie basisRegistratie = new BasisRegistratie("BRK",
				"Basisregistratie Kadaster");

		ArrayList<BRObject> objectTypen = null;
		try {
			objectTypen = wwl.getObjectTypen(basisRegistratie);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testGetObjectTypen", ie);
		}

		if (objectTypen != null) {
			logger.trace("Size = " + objectTypen.size());
		} else {
			logger.trace("objectTypen is null!");
		}
		assertNotNull("objectTypen is null!", objectTypen);

	}

	/**
	 * Test wat er gebeurt als er een malformed url wordt gebruikt in
	 * WWLObjectInvokerImpl in combinatie met de functie getObjectTypen.
	 */
	public void testMalformedURLWithoutValues() {
		WWLObjectInvokerImpl wwl = (WWLObjectInvokerImpl) ac
				.getBean("objInvMallFormedURL");
		BasisRegistratie basisRegistratie = new BasisRegistratie("BRK",
				"Basisregistratie Kadaster");

		try {
			wwl.getObjectTypen(basisRegistratie);
		} catch (InvokerException ie) {
			return;
		}

		fail("Hier had al een exceptie moeten optreden!");
	}
}
