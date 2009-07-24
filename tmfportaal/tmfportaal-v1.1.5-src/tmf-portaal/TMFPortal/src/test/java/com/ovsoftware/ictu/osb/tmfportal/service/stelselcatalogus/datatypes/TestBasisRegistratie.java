package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes;

import junit.framework.TestCase;

public class TestBasisRegistratie extends TestCase {

	/**
	 * Test de constructor en getters van BasisRegistratie.
	 */
	public void testConstructorAndGetters() {
		String Ctag = "tag";
		String Cnaam = "naam";
		
		BasisRegistratie br = new BasisRegistratie(Ctag, Cnaam);
		
		assertEquals("Tag niet gelijk", "tag", br.getTag());
		assertEquals("Naam niet gelijk", "naam", br.getNaam());
	}

}
