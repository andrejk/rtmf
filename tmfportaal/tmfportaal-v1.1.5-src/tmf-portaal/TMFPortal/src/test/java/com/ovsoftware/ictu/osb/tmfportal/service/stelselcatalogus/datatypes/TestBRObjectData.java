package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes;

import java.util.ArrayList;

import junit.framework.TestCase;

public class TestBRObjectData extends TestCase {

	/**
	 * Test de constructor en getters van BRObjectData.
	 */
	public void testConstructorAndGetters() {
		
		ArrayList<BRObjectAttribuut> CbroaLijst = new ArrayList<BRObjectAttribuut>();
		boolean Cbevraagbaar = false;
		String Cinstructie = "instructie";
		String Cnaam = "naam";
		String Ctag = "tag";
		
		BRObjectData brod = new BRObjectData(CbroaLijst, Cbevraagbaar, Cinstructie, Cnaam, Ctag);
		
		assertEquals("broaLijst niet gelijk", CbroaLijst, brod.getBroaLijst());
		assertEquals("Bevraagbaar niet gelijk", Cbevraagbaar, brod.isBevraagbaar());
		assertEquals("Instructie niet gelijk", Cinstructie, brod.getInstructie());
		assertEquals("Naam niet gelijk", Cnaam, brod.getNaam());
		assertEquals("Tag niet gelijk", Ctag, brod.getTag());
	}	
}
