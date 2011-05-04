package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.wus.wuslite;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.Converter;
import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.Slapen;
import com.ovsoftware.ictu.osb.tmfportal.service.common.TerugmeldingFactory;
import com.ovsoftware.ictu.osb.tmfportal.service.common.WebserviceSettings;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.OpvraagInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TerugmeldInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;

public class TestWWLOpvraagInvokerImpl extends TestCase {

	private Logger logger = Logger.getLogger(TestWWLOpvraagInvokerImpl.class);
	private ApplicationContext ac;
	
	public void setUp() throws IOException {
		ac = new FileSystemXmlApplicationContext("src/test/resources/springapp-mock-servlet.xml");
	}	
	
	/**
	 * Test de getter en setter voor endpoint.
	 */
	public void testSetterAndGetterEndpoint() {
		WWLOpvraagInvokerImpl woii = (WWLOpvraagInvokerImpl) ac.getBean("opvraagInvoker");
		String oudeEndpoint = woii.getEndpointAddress();
		woii.setEndpointAddress(oudeEndpoint + "!!!");
		assertEquals("Niet correct geset", oudeEndpoint + "!!!", woii.getEndpointAddress());
		woii.setEndpointAddress(oudeEndpoint);
		assertEquals("Niet correct geset", oudeEndpoint, woii.getEndpointAddress());
	}
	
	/**
	 * Test het ophalen van een lijst met meldingen bij de webservice. Het
	 * volgende stappenplan wordt gevolgd:
	 * - maak een terugmelding aan
	 * - voer nieuwe terugmelding in
	 * - wacht een halve minuut
	 * - haal de nieuwe terugmelding op
	 * - check of de lijst niet null is en maar 1 terugmelding bevat
	 * - check naam, idAttribuut en lengte attribuutLijst
	 * - haal de details van de melding op
	 * - controleer dat deze niet null is
	 * - check dat de status 'gemeld' is
	 * 
	 * @throws InterruptedException 
	 */
	public void testOphalenMeldingenSuccesvol() throws InterruptedException {
		//maak een terugmelding kenmerk op basis van de huidige tijd
		Terugmelding t = TerugmeldingFactory.getTerugmelding();
		
		//voer nieuwe terugmelding in
		TerugmeldInvoker ti = (TerugmeldInvoker)ac.getBean("terugmeldInvoker");
		try {
			ti.melding(t);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testOphalenMeldingenSuccesvol", ie);
		}
		
		//wacht een aantal seconden
		Slapen.kleinePowernap();
		
		//haal de nieuwe terugmelding op
		OpvraagInvoker oi = (OpvraagInvoker)ac.getBean("opvraagInvoker");
		ArrayList<Terugmelding> terugmeldingen = null;
		try { 
			terugmeldingen = oi.ophalenMeldingen(null, null, null, t.getMeldingKenmerk(), null);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testOphalenMeldingenSuccesvol", ie);
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
			assertNull("InvokerException in testOphalenMeldingenSuccesvol", ie);
		}
		
		//check of het resultaat niet leeg is
		assertNotNull("TerugmeldingDetails is null!", td);
		
		//check de status van het resultaat (zou 'gemeld' moeten zijn)
		assertEquals("Status is niet zoals verwacht 'gemeld', maar '" + td.getStatusMelding() + "'", "gemeld", td.getStatusMelding());
		
	}	
	
	/**
	 * Probeert een lijst met meldingen op te halen met een foutieve URL
	 */
	public void testOphalenMeldingenFoutieveURL() {
		WebserviceSettings.setSettingsFilename("bad-settings.xml");
		OpvraagInvoker wwl = (OpvraagInvoker)ac.getBean("opvraagInvoker");
//		WWLOpvraagInvokerImpl wwl = new WWLOpvraagInvokerImpl();
		ArrayList<Terugmelding> terugmeldingen = null;
		try {
			terugmeldingen = wwl.ophalenMeldingen(null, null, null, null, null);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testOphalenMeldingenFoutieveURL", terugmeldingen);
			WebserviceSettings.setSettingsFilename("settings.xml");
			return;
		}
		
		assertNotNull("Er zou al een exceptie opgetreden moeten hebben", terugmeldingen);
		WebserviceSettings.setSettingsFilename("settings.xml");
	}
	
	/**
	 * Haalt succesvol een lijst met meldingdetails op.
	 * @throws InterruptedException 
	 */
	public void testOphalenMeldingDetailsSuccesvol() throws InterruptedException {
		//maak een terugmelding kenmerk op basis van de huidige tijd
		Terugmelding t = TerugmeldingFactory.getTerugmelding();
		
		//voer nieuwe terugmelding in
		TerugmeldInvoker ti = (TerugmeldInvoker)ac.getBean("terugmeldInvoker");
//		TerugmeldInvoker ti = new EWLTerugmeldInvokerImpl();
		try {
			ti.melding(t);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testOphalenMeldingDetailsSuccesvol", ie);
		}
		
		//wacht een aantal seconden
		Slapen.kleinePowernap();
		
		//haal TerugmeldingDetails op
		OpvraagInvoker wwl = (OpvraagInvoker)ac.getBean("opvraagInvoker");
//		WWLOpvraagInvokerImpl wwl = new WWLOpvraagInvokerImpl();
		TerugmeldingDetails td = null;
		try { 
			td = wwl.ophalenMeldingDetails(t.getMeldingKenmerk());
		} catch (InvokerException ie) {
			assertNull("InvokerException in testOphalenMeldingDetailsSuccesvol", ie);
		}
		
		//Gegevens vergelijken nieuwe melding met verwachte resultaat
		assertNotNull("TerugmeldingDetails is null!", td);
		assertEquals("Status is niet 'gemeld', maar '" + td.getStatusMelding() + "'", td.getStatusMelding(), "gemeld");		
	}
	
	/**
	 * Probeert de details van een terugmelding op te halen voor een niet bestaande terugmelding.
	 */
	public void testOphalenMeldingDetailsFoutief() {
		
		//poging tot ophalen details van niet bestaande terugmelding
		OpvraagInvoker wwl = (OpvraagInvoker)ac.getBean("opvraagInvoker");
//		WWLOpvraagInvokerImpl wwl = new WWLOpvraagInvokerImpl();
		//TerugmeldingDetails td = wwl.ophalenMeldingDetails("6E14DADD-636C-2275-CED8-30963DC59A25");
		TerugmeldingDetails td = null;
		try {
			td = wwl.ophalenMeldingDetails("blablabla");
		} catch (InvokerException ie) {
			assertNull("InvokerException in testOphalenMeldingDetailsFoutief", td);
			return;
		}
		
		if (td!= null) {
			logger.trace("TMFKenmerk details = " + td.getTerugmeldingKern().getTmfKenmerk());
		}
		
		assertNull("Er zou al een exceptie opgetreden moeten hebben hier!", td);
	}
	
	/**
	 * Haalt alle meldingen op en print ze. Dit is eigenlijk niet echt een test maar het
	 * is handig voor debuggen. Er bevinden zich dan ook geen assert-statements in deze
	 * functie.
	 */
	public void testHaalAlleTerugmeldingenOp() {
		//haal de nieuwe terugmelding op
		OpvraagInvoker oi = (OpvraagInvoker)ac.getBean("opvraagInvoker");
		ArrayList<Terugmelding> terugmeldingen = null;
		try {
			terugmeldingen = oi.ophalenMeldingen(null, null, null, null, null);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testHaalAlleTerugmeldingenOp", ie);
			return;
		}
		
		for (Terugmelding t : terugmeldingen) {
			logger.trace("==========================================================");
			logger.trace("Meldingkenmerk: " + t.getMeldingKenmerk());
			logger.trace("TMFkenmerk:     " + t.getTerugmeldingKern().getTmfKenmerk());
			logger.trace("Aanlevering:    " + Converter.converteerGCnaarStringNL(t.getTijdstempelAanlevering()));
			logger.trace("BR + Object:    " + t.getTagBR() + "-" + t.getTagObject());
			logger.trace("==========================================================");
		}
	}
	
	public void testMalformedURL() {
		OpvraagInvoker oi = (OpvraagInvoker) ac.getBean("opvraagInvokerMallFormedURL");
		try {
			oi.ophalenMeldingen(null, null, null, null, null);
		} catch (InvokerException ie) {
			return;
		}
		fail("Hier had al een exceptie moeten optreden!");
	}
}

