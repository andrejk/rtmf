package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.wus.wuslite;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.WebserviceSettings;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

public class TestWWLBasisregistratieInvokerImpl extends TestCase {

	private Logger logger = Logger
			.getLogger(TestWWLBasisregistratieInvokerImpl.class);
	private ApplicationContext ac;

	public void setUp() throws IOException {
		ac = new FileSystemXmlApplicationContext(
				"src/test/resources/springapp-mock-servlet.xml");
	}

	/**
	 * Test de getBasisRegistraties-functie van WWLBasisregistratieInvokerImpl.
	 */
	public void testGetBasisRegistraties() {
		WebserviceSettings.setSettingsFilename("settings.xml");
		WWLBasisregistratieInvokerImpl wwl = (WWLBasisregistratieInvokerImpl) ac
				.getBean("brInv");

		ArrayList<BasisRegistratie> basisRegistraties = null;
		try {
			basisRegistraties = wwl.getBasisRegistraties();
		} catch (InvokerException ie) {
			assertNull("InvokerException in testGetBasisRegistraties", ie);
		}

		if (basisRegistraties != null) {
			logger.trace("Size = " + basisRegistraties.size());
		} else {
			logger.trace("basisRegistraties is null!");
		}
		assertNotNull("basisRegistraties is null!", basisRegistraties);
	}

	/**
	 * Test wat er gebeurt als er een malformed url wordt gebruikt in
	 * WWLBasisregistratieInvokerImpl in combinatie met de functie
	 * getBasisRegistraties.
	 */
	public void testMalformedURLWithoutValues() {
		WWLBasisregistratieInvokerImpl wwl = (WWLBasisregistratieInvokerImpl) ac
				.getBean("brInvMallFormedURL");

		try {
			wwl.getBasisRegistraties();
		} catch (InvokerException ie) {
			return;
		}

		fail("Hier had al een exceptie moeten optreden!");
	}
}
