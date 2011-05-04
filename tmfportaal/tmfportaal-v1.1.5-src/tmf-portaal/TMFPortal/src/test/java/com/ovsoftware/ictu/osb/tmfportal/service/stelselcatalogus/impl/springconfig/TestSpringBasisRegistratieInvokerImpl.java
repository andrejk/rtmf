package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.springconfig;

import java.util.ArrayList;

import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

import junit.framework.TestCase;

public class TestSpringBasisRegistratieInvokerImpl extends TestCase {

	/**
	 * Test de constructor, getters en setters van de SpringBasisRegistratieInvokerImpl
	 */
	public void testConstructorGettersAndSetters() {
		SpringBasisRegistratieInvokerImpl bri = new SpringBasisRegistratieInvokerImpl(
				"basisregistraties.xml");
		
		ArrayList<BasisRegistratie> brLijst = bri.getBasisRegistraties();
		assertTrue("Aantal basisregistraties niet 4", brLijst.size() == 4);
		assertEquals("Element 1 heeft niet tag BRA", brLijst.get(0).getTag(), "BRA");
		assertEquals("Element 2 heeft niet tag BGR", brLijst.get(1).getTag(), "BGR");
		assertEquals("Element 3 heeft niet tag WOZ", brLijst.get(2).getTag(), "WOZ");
		assertEquals("Element 4 heeft niet tag BRK", brLijst.get(3).getTag(), "BRK");
	}
	
}
