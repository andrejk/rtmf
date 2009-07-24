package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes;

import junit.framework.TestCase;

public class TestBRObjectAttribuut extends TestCase {

	/**
	 * Test de constructor en getters van BRObjectAttribuut.
	 */
	public void testConstructorAndGetters() {
		
		String Ctag = "tag";
		String Cnaam = "naam";
		String Cwaarde = "waarde";
		
		BRObjectAttribuut broa = new BRObjectAttribuut(Ctag, Cnaam, Cwaarde);
		
		assertEquals("Tag niet gelijk", Ctag, broa.getTag());
		assertEquals("Naam niet gelijk", Cnaam, broa.getNaam());
		assertEquals("Waarde niet gelijk", Cwaarde, broa.getWaarde());
		
	}
}
