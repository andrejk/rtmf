package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.TerugmeldingFactory;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.OpvraagInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TerugmeldInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;

import junit.framework.TestCase;

public class TestMockOpvraagInvokerImpl extends TestCase {
	
	private ApplicationContext ac;
	public void setUp() throws IOException {
		ac = new FileSystemXmlApplicationContext("src/test/resources/springapp-mock-servlet.xml");
	}

	/**
	 * Test het ophalen van meldingen alsmede de filtering die daarbij toegepast wordt.
	 * Het volgende stappenplan wordt gevolgd:
	 * - wis alle oude meldingen (ivm static klasse)
	 * - plaats een nieuwe terugmelding
	 * - vraag een lijst op van ingetrokken meldingen (zou 0 moeten zijn)
	 * - vraag een lijst van meldingen van 2007 op (zou 0 moeten zijn)
	 * - vraag een lijst op met een niet-bestaande status (zou 0 moeten zijn)
	 * - vraag een lijst met status 'gemeld' vanaf het jaar 1900 (zou 1 moeten zijn)
	 * - vergelijk de geplaatste en de opgevraagde melding met elkaar
	 * - wis alle meldingen
	 * 
	 * @throws InterruptedException 
	 */
	public void testMelding() throws InterruptedException {		
		//wis alle oude meldingen
		MockOpvraagInvokerImpl.clearAll();
		try {
		
			//plaats een nieuwe terugmelding
			MockTerugmeldInvokerImpl ti = new MockTerugmeldInvokerImpl();
			Terugmelding t = TerugmeldingFactory.getTerugmelding();
			ti.melding(t);
			
			//vraag een lijst van _ingetrokken_ meldingen op (resultaat zou 0 moeten zijn)
			OpvraagInvoker oi = (OpvraagInvoker) ac.getBean("mockOpvraagInvoker");
			ArrayList<Terugmelding> terugmeldingen = oi.ophalenMeldingen("ingetrokken", null, null, null, null);
			assertTrue("Lijst van terugmeldingen ongelijk 0, nl: " + terugmeldingen.size(), terugmeldingen.size() == 0);
			
			//vraag een lijst van meldingen van 2007 op (resultaat zou 0 moeten zijn)
			terugmeldingen = oi.ophalenMeldingen(null,
												 new GregorianCalendar(2007,1,1,0,0,0),
												 new GregorianCalendar(2007,12,31,23,59,59),
												 null,
												 null);
			assertTrue("Lijst van terugmeldingen ongelijk 0, nl: " + terugmeldingen.size(), terugmeldingen.size() == 0);
			
			//vraag een lijst van meldingen op met niet bestaande status (resultaat zou 0 moeten zijn)
			terugmeldingen = oi.ophalenMeldingen("blablabla", null, null, null, null);
			assertTrue("Lijst van terugmeldingen ongelijk 0, nl: " + terugmeldingen.size(), terugmeldingen.size() == 0);
	
			//vraag een lijst van meldingen op met status "gemeld" tot nu toe en vanaf 1900 (resultaat zou 1 moeten zijn)
			terugmeldingen = oi.ophalenMeldingen("gemeld", new GregorianCalendar(1900,1,1,0,0,0), null, null, null);
			assertTrue("Lijst van terugmeldingen vanaf 1900 ongelijk 1, nl: " + terugmeldingen.size(), terugmeldingen.size() == 1);
			
			//vergelijk de melding kort met de gedane melding
			assertEquals("Terugmeldingen niet gelijk", t, terugmeldingen.get(0));
					
			//wis alle oude meldingen
			MockOpvraagInvokerImpl.clearAll();
		} catch (InvokerException e) {
			fail(e.getStackTrace().toString());
		}
	}
	
	/**
	 * Leegt de lijst met terugmeldingen, plaatst een nieuwe melding, vraagt daarvan
	 * de details op en vergelijkt deze met wat het zou moeten zijn.
	 */
	public void testMeldingDetailsBestaand() {
		//wis alle oude meldingen
		MockOpvraagInvokerImpl.clearAll();
				
		//plaats een nieuwe terugmelding
		TerugmeldInvoker ti = (TerugmeldInvoker) ac.getBean("mockTerugmeldInvoker");
		Terugmelding t = TerugmeldingFactory.getTerugmelding();
		try {
			ti.melding(t);
		} catch (InvokerException e1) {
			fail(e1.getStackTrace().toString());
		}
		
		//vraag de details van die melding op
		OpvraagInvoker oi = (OpvraagInvoker) ac.getBean("mockOpvraagInvoker");
		TerugmeldingDetails td = null;
		try {
			td = oi.ophalenMeldingDetails(t.getMeldingKenmerk());
		} catch (InvokerException e) {
			fail(e.getStackTrace().toString());
		}
		assertNotNull("TerugmeldingDetails is null", td);		
		assertEquals("TerugmeldingKern niet gelijk", t.getTerugmeldingKern(), td.getTerugmeldingKern());
			
		//wis alle oude meldingen
		MockOpvraagInvokerImpl.clearAll();
	}
	
	/**
	 * Test waarin geprobeert wordt van een niet bestaande terugmelding de details op te vragen.
	 * Het resultaat hiervan zou null moeten zijn.
	 */
	public void testMeldingDetailsNietBestaand() {
		//wis alle oude meldingen
		MockOpvraagInvokerImpl.clearAll();
		
		//vraag de details van die melding op
		OpvraagInvoker oi = (OpvraagInvoker) ac.getBean("mockOpvraagInvoker");
		TerugmeldingDetails td = null;
		try {
			td = oi.ophalenMeldingDetails("blablabla");
		} catch (InvokerException e) {
			fail(e.getStackTrace().toString());
		}
		assertNull("TerugmeldingDetails is niet null", td);		
				
		//wis alle oude meldingen
		MockOpvraagInvokerImpl.clearAll();
	}	

}
