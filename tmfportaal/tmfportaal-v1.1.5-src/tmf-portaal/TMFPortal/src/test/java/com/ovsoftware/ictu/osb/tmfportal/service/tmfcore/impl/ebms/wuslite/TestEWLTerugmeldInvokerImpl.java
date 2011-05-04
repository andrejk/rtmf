package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.ebms.wuslite;

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

public class TestEWLTerugmeldInvokerImpl extends TestCase {

	private Logger logger = Logger.getLogger(TestEWLTerugmeldInvokerImpl.class);
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
		EWLTerugmeldInvokerImpl eiii = (EWLTerugmeldInvokerImpl) ac.getBean("terugmeldInvoker");
		String oudeEndpoint = eiii.getEndpointAddress();
		eiii.setEndpointAddress(oudeEndpoint + "!!!");
		assertEquals("Niet correct geset", oudeEndpoint + "!!!", eiii.getEndpointAddress());
		eiii.setEndpointAddress(oudeEndpoint);
		assertEquals("Niet correct geset", oudeEndpoint, eiii.getEndpointAddress());
	}
	
	/**
	 * Test het doen van een Terugmelding bij de webservice. Het volgende
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
	 * - print nog enkele details mbt de terugmelding (alleen op logger.trace niveau)
	 * 
	 * @throws InterruptedException
	 */
	public void testTerugmelding() throws InterruptedException {
		WebserviceSettings.setSettingsFilename("settings.xml");
		
		//maak een terugmelding kenmerk op basis van de huidige tijd
		Terugmelding t = TerugmeldingFactory.getTerugmelding();
		
		//voer nieuwe terugmelding in
		TerugmeldInvoker ti = (TerugmeldInvoker) ac.getBean("terugmeldInvoker");
//		TerugmeldInvoker ti = new EWLTerugmeldInv okerImpl();
		try {
			ti.melding(t);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testTerugmelding", ie);
		}
		
		//wacht een aantal seconden
		Slapen.kleinePowernap();
		
		//haal de nieuwe terugmelding op
		OpvraagInvoker oi = (OpvraagInvoker) ac.getBean("opvraagInvoker");
//		OpvraagInvoker oi = new WWLOpvraagInvoke rImpl();
		ArrayList<Terugmelding> terugmeldingen = null;
		try {
			terugmeldingen = oi.ophalenMeldingen(null, null, null, t.getMeldingKenmerk(), null);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testTerugmelding", ie);
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
			assertNull("InvokerException in testTerugmelding", ie);
		}
		
		//check of het resultaat niet leeg is
		assertNotNull("TerugmeldingDetails is null!", td);
		
		//check de status van het resultaat (zou 'gemeld' moeten zijn)
		assertEquals("Status is niet zoals verwacht 'gemeld', maar '" + td.getStatusMelding() + "'", "gemeld", td.getStatusMelding());
	
		//print de details op trace-niveau
		logger.trace("----------------------------------------------------");
		logger.trace("TMFkenmerk    : " + td.getTerugmeldingKern().getTmfKenmerk());
		logger.trace("Tijd gemeld   : " + Converter.converteerGCnaarStringNL(td.getTijdstempelGemeld()));
		logger.trace("Tijd ontvangst: " + Converter.converteerGCnaarStringNL(td.getTijdstempelOntvangst()));
		logger.trace("Tijd status   : " + Converter.converteerGCnaarStringNL(td.getTijdstempelStatus()));
		logger.trace("Status        : " + td.getStatusMelding());
		logger.trace("----------------------------------------------------");
	}	

	public void testMalformedURL() {
		TerugmeldInvoker ti = (TerugmeldInvoker) ac.getBean("terugmeldInvokerMallFormedURL");
		Terugmelding t = TerugmeldingFactory.getTerugmelding();
		
		try {
			ti.melding(t);
		} catch (InvokerException ie) {
			return;
		}
		fail("Hier had al een exceptie moeten optreden!");
	}
}
