package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock;

import java.util.ArrayList;

import com.ovsoftware.ictu.osb.tmfportal.service.common.TerugmeldingFactory;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;

import junit.framework.TestCase;

public class TestMockTerugmeldInvokerImpl extends TestCase {

	/**
	 * Voegt een nieuwe melding toe, haalt deze weer op en vergelijkt ze.
	 */
	public void testNieuweTerugmelding() {
		
		//wis alle oude meldingen
		MockOpvraagInvokerImpl.clearAll();
		
		//plaats een nieuwe terugmelding
		MockTerugmeldInvokerImpl ti = new MockTerugmeldInvokerImpl();
		Terugmelding t = TerugmeldingFactory.getTerugmelding();
		ti.melding(t);
		
		//vraag de ene melding op
		MockOpvraagInvokerImpl oi = new MockOpvraagInvokerImpl();
		ArrayList<Terugmelding> terugmeldingen = oi.ophalenMeldingen(null, null, null, null, null);
		assertTrue("Lijst van terugmeldingen ongelijk 1, nl: " + terugmeldingen.size(), terugmeldingen.size() == 1);
		
		//vergelijk de melding kort met de gedane melding
		assertEquals("Terugmeldingen niet gelijk", t, terugmeldingen.get(0));
				
		//wis alle oude meldingen
		MockOpvraagInvokerImpl.clearAll();
	}	

}
