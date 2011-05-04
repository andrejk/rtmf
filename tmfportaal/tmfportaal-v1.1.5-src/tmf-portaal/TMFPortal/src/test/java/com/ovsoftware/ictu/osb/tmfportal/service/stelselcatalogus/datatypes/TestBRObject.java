package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes;

import junit.framework.TestCase;

public class TestBRObject extends TestCase{
	
	/**
	 * Test de constructor en getters van BRObject.
	 */
	public void testConstructorAndGetters() {
		
		String Ctag = "tag";
		String Cnaam = "naam";
		
		BRObject bro = new BRObject(Ctag, Cnaam);
	
		assertEquals("Tag niet gelijk", Ctag, bro.getTag());
		assertEquals("Naam niet gleijk", Cnaam, bro.getNaam());		
	}

}
