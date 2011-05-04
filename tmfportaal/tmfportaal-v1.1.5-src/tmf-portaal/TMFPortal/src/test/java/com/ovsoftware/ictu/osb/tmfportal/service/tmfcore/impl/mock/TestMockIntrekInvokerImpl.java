package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.TerugmeldingFactory;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.OpvraagInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TerugmeldInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;

import junit.framework.TestCase;

public class TestMockIntrekInvokerImpl extends TestCase {
	
	private Logger logger = Logger.getLogger(TestMockIntrekInvokerImpl.class);
	
	private ApplicationContext ac;
	public void setUp() throws IOException {
		ac = new FileSystemXmlApplicationContext("src/test/resources/springapp-mock-servlet.xml");
	}

	/**
	 * Test het doen van een intrekking voor de Mock-services. Het
	 * volgende stappenplan wordt gevolgd:
	 * - wis alle oude meldingen (ivm static klasse)
	 * - plaats een nieuwe terugmelding
	 * - vraag de terugmelding op
	 * - check of de lijst niet null is en maar 1 terugmelding bevat
	 * - trek de melding in
	 * - vraag de details van de melding op
	 * - check dat de details niet null zijn
	 * - check dat de status van de melding 'ingetrokken' is
	 */
	public void testIntrekken() {
				
		//wis alle oude meldingen
		MockOpvraagInvokerImpl.clearAll();
		
		//plaats een nieuwe terugmelding
		TerugmeldInvoker ti = (TerugmeldInvoker)ac.getBean("mockTerugmeldInvoker");
		Terugmelding t = TerugmeldingFactory.getTerugmelding();
		try {
			ti.melding(t);
		} catch (InvokerException e1) {
			fail(e1.getStackTrace().toString());
		}
		
		//vraag de ene melding op (ivm tmfkenmerk)
		OpvraagInvoker oi = (OpvraagInvoker)ac.getBean("mockOpvraagInvoker");
		ArrayList<Terugmelding> terugmeldingen = null;
		try {
			terugmeldingen = oi.ophalenMeldingen(null, null, null, null, null);
		} catch (InvokerException e1) {
			fail(e1.getStackTrace().toString());
		}
		assertNotNull("terugmeldingen is null!", terugmeldingen);
		assertTrue("Lijst van terugmeldingen ongelijk 1, nl: " + terugmeldingen.size(), terugmeldingen.size() == 1);
				
		//trek die melding in
		MockIntrekInvokerImpl ii = new MockIntrekInvokerImpl();
		try {
			ii.intrekking(terugmeldingen.get(0).getTerugmeldingKern().getTmfKenmerk(), "test");
		} catch (InvokerException e) {
			logger.error("InvokerException tijdens intrekken melding.", e);
			assertNull("InvokerException tijdens intrekken melding!", e);
		}
		
		//vraag de details van die ene melding op
		MockOpvraagInvokerImpl oi2 = new MockOpvraagInvokerImpl();
		TerugmeldingDetails terugmeldingDetails = oi2.ophalenMeldingDetails(t.getMeldingKenmerk());
		assertNotNull("TerugmeldingDetails is null!", terugmeldingDetails);
		
		//check of de melding ingetrokken is
		assertEquals("Terugmelding is niet ingetrokken!",
					 "ingetrokken",
					 terugmeldingDetails.getStatusMelding());
				
		//wis alle oude meldingen
		MockOpvraagInvokerImpl.clearAll();
	}
}
