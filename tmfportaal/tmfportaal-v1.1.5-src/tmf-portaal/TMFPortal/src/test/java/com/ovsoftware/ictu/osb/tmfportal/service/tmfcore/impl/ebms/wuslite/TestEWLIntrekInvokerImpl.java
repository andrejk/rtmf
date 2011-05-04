package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.ebms.wuslite;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.Slapen;
import com.ovsoftware.ictu.osb.tmfportal.service.common.TerugmeldingFactory;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.IntrekInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.OpvraagInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TerugmeldInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;

public class TestEWLIntrekInvokerImpl extends TestCase {
	
	private ApplicationContext ac;

	/**
	 * Laad de beans uit src/test/resources/springapp-mock-servlet.xml.
	 */
	public void setUp() throws IOException {
		ac = new FileSystemXmlApplicationContext("src/test/resources/springapp-mock-servlet.xml");
	}
	
	/**
	 * Test de getter en setter voor endpoint.
	 */
	public void testSetterAndGetterEndpoint() {
		EWLIntrekInvokerImpl eiii = (EWLIntrekInvokerImpl) ac.getBean("intrekInvoker");
		String oudeEndpoint = eiii.getEndpointAddress();
		eiii.setEndpointAddress(oudeEndpoint + "!!!");
		assertEquals("Niet correct geset", oudeEndpoint + "!!!", eiii.getEndpointAddress());
		eiii.setEndpointAddress(oudeEndpoint);
		assertEquals("Niet correct geset", oudeEndpoint, eiii.getEndpointAddress());
	}

	/**
	 * Test het doen van een Intrekking bij de webservice. Het volgende
	 * stappenplan wordt gevolgd:
	 * - maak terugmelding aan
	 * - voer nieuwe terugmelding in
	 * - wacht een halve minuut
	 * - haal de nieuwe terugmelding op
	 * - controleer de terugmelding (niet null, en slechts 1 terugmelding retour)
	 * - controleer de terugmelding inhoudelijk (naam, idObject, lengte attributenLijst)
	 * - haal de details op van de terugmelding
	 * - check of het resultaat niet null is
	 * - check of de status gelijk is aan 'gemeld'
	 * - doe een intrekking
	 * - wacht een halve minuut
	 * - haal de details op van de terugmelding nogmaals
	 * - check of het resultaat niet null is
	 * - check of de status gelijk is aan 'ingetrokken'
	 * 
	 * @throws InterruptedException
	 */
	public void testIntrekking() throws InterruptedException {
		OpvraagInvoker oi = (OpvraagInvoker) ac.getBean("opvraagInvoker");
		
		Terugmelding t = TerugmeldingFactory.getTerugmelding();
		Terugmelding t2 = prepare(t);
		
		//doe een intrekking
		IntrekInvoker ii = (IntrekInvoker) ac.getBean("intrekInvoker");
		try {
			ii.intrekking(t2.getTerugmeldingKern().getTmfKenmerk(), "Ingetrokken als test");
		} catch (InvokerException ie) {
			assertNull("InvokerException in testIntrekking", ie);
		}
			
		//wacht een aantal seconden
		Slapen.kleinePowernap();
		
		//haal de details van deze terugmelding op
		TerugmeldingDetails td2 = null;
		try {
			td2 = oi.ophalenMeldingDetails(t.getMeldingKenmerk());
		} catch (InvokerException ie) {
			assertNull("InvokerException in testIntrekking", ie);
		}
		
		//check of het resultaat niet leeg is
		assertNotNull("TerugmeldingDetails is null!", td2);
		
		//check de status van het resultaat (zou 'ingetrokken' moeten zijn)
		assertEquals("Status is niet zoals verwacht 'gemeld', maar '" + td2.getStatusMelding() + "'", "ingetrokken", td2.getStatusMelding());
		
	}

	private Terugmelding prepare(Terugmelding t) {
		//maak een terugmelding kenmerk op basis van de huidige tijd
		
		//voer nieuwe terugmelding in
		TerugmeldInvoker ti = (TerugmeldInvoker) ac.getBean("terugmeldInvoker");
		try {
			ti.melding(t);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testIntrekking", ie);
		}
		
		//wacht een aantal seconden
		Slapen.kleinePowernap();
		
		//haal de nieuwe terugmelding op
		OpvraagInvoker oi = (OpvraagInvoker) ac.getBean("opvraagInvoker");
		ArrayList<Terugmelding> terugmeldingen = null;
		try {
			terugmeldingen = oi.ophalenMeldingen(null, null, null, t.getMeldingKenmerk(), null);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testIntrekking", ie);
		}
		
		//check of het resultaat niet leeg is en maar 1 Terugmelding bevat
		assertNotNull("Lijst met terugmeldingen is null", terugmeldingen);
		assertTrue("Lijst bevat niet precies 1 Terugmelding, namelijk " + terugmeldingen.size(), terugmeldingen.size() == 1);
		
		//controleer of het resultaat hetzelfde is op enkele belangrijke punten
		Terugmelding t2 = terugmeldingen.get(0);
		assertEquals("naam is niet gelijk", t.getContactGegevens().getNaam(), t2.getContactGegevens().getNaam());
		assertEquals("idObject is niet gelijk", t.getIdObject(), t2.getIdObject());
		assertEquals("attributen lijst is niet gelijk qua lengte", t.getObjectAttribuutLijst().size(), t2.getObjectAttribuutLijst().size());

		//haal de details van deze terugmelding op
		TerugmeldingDetails td = null;
		try {
			td = oi.ophalenMeldingDetails(t.getMeldingKenmerk());
		} catch (InvokerException ie) {
			assertNull("InvokerException in testIntrekking", ie);
		}
		
		//check of het resultaat niet leeg is
		assertNotNull("TerugmeldingDetails is null!", td);
		
		//check de status van het resultaat (zou 'gemeld' moeten zijn)
		assertEquals("Status is niet zoals verwacht 'gemeld', maar '" + td.getStatusMelding() + "'", td.getStatusMelding(), "gemeld");
		return t2;
	}

	public void testMalformedURL() {
		Terugmelding t = TerugmeldingFactory.getTerugmelding();
		Terugmelding t2 = prepare(t);
		
		//doe een intrekking
		IntrekInvoker ii = (IntrekInvoker) ac.getBean("intrekInvokerMallFormedURL");
		try {
			ii.intrekking(t2.getTerugmeldingKern().getTmfKenmerk(), "Ingetrokken als test");
		} catch (InvokerException ie) {
			return;
		}
		fail("Hier had al een exceptie moeten optreden!");
	}
}
