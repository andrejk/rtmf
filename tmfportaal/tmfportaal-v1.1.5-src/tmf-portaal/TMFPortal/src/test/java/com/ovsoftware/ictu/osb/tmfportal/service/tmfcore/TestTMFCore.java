package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock.MockOpvraagInvokerImpl;

import junit.framework.TestCase;

public class TestTMFCore extends TestCase {
	private ApplicationContext ac;
	private Logger logger = Logger.getLogger(TestTMFCore.class);
	
	/**
	 * Leest de applicationcontext uit het XML-bestand
	 */
	public void setUp() throws IOException {
		MockOpvraagInvokerImpl.clearAll(); //nodig ivm static waardes
		ac = new FileSystemXmlApplicationContext("src/test/resources/springapp-mock-servlet.xml");
	}

	/**
	 * Laadt de MockOpvraagInvoker uit het XML-bestand en test of terugmelding-compleet.xml correct
	 * is ingeladen.
	 */
	public void testMockOpvraagManagerImpl() {
		OpvraagInvoker momi = (OpvraagInvoker) ac.getBean("mockOpvraagInvoker");
		logger.trace("Loaded + " + momi.toString());
		
		ArrayList<Terugmelding> terugmeldingLijst = null;
		try {
			terugmeldingLijst = momi.ophalenMeldingen(null, null, null, null, null);
		} catch (InvokerException e) {
			fail(e.getStackTrace().toString());
		}
		assertNotNull("terugmeldingLijst is null!", terugmeldingLijst);
		assertTrue("Aantal terugmeldingen niet 3, maar " + terugmeldingLijst.size(), terugmeldingLijst.size() == 3);
		assertEquals("Terugmelding 1 heeft als voorstel niet Koen", "Koen", terugmeldingLijst.get(0).getObjectAttribuutLijst().get(0).getVoorstel());
		assertEquals("Terugmelding 2 heeft als voorstel niet Rick", "Rick", terugmeldingLijst.get(1).getObjectAttribuutLijst().get(0).getVoorstel());
		assertEquals("Terugmelding 3 heeft als voorstel niet Peter", "Peter", terugmeldingLijst.get(2).getObjectAttribuutLijst().get(0).getVoorstel());
	}
}
	