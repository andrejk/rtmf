package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.mock;

import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectData;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

import junit.framework.TestCase;

public class TestMockAttribuutInvokerImpl extends TestCase {

	/**
	 * Test de getBasisAttributen-functie van MockAttribuutInvokerImpl
	 */
	public void testGetBasisAttributen() {
		
		BasisRegistratie br = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		BRObject bro = new BRObject("PER", "Persoon");
		
		MockAttribuutInvokerImpl ai = new MockAttribuutInvokerImpl();
		
		BRObjectData brod = ai.getBasisAttributen(br, bro);
		
		assertTrue("Bevraagbaar niet true", brod.isBevraagbaar());
		assertEquals("Instructie niet gelijk", brod.getInstructie(), "Instructie");
		assertEquals("Naam niet gelijk", brod.getNaam(), bro.getNaam());
		assertEquals("Tag niet gelijk", brod.getTag(), bro.getTag());
		assertTrue("Aantal attributen niet gelijk aan twee", brod.getBroaLijst().size() == 2);
		assertNull("Attribuutwaarde niet gelijk aan null", brod.getBroaLijst().get(0).getWaarde());
	}
	
	/**
	 * Test de getBasisAttributenValues-functie van MockAttribuutInvokerImpl
	 */
	public void testGetBasisAttributenValues() {
		
		BasisRegistratie br = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		BRObject bro = new BRObject("PER", "Persoon");
		
		MockAttribuutInvokerImpl ai = new MockAttribuutInvokerImpl();
		
		BRObjectData brod = ai.getBasisAttributenValues(br, bro, "123");
		
		assertTrue("Bevraagbaar niet true", brod.isBevraagbaar());
		assertEquals("Instructie niet gelijk", brod.getInstructie(), "Instructie voor deze Persoon");
		assertEquals("Naam niet gelijk", brod.getNaam(), bro.getNaam());
		assertEquals("Tag niet gelijk", brod.getTag(), bro.getTag());
		assertTrue("Aantal attributen niet gelijk aan twee", brod.getBroaLijst().size() == 2);
		assertTrue("Attribuutwaarde niet langer dan 1 teken", brod.getBroaLijst().get(0).getWaarde().length() > 1);
			
	}
	
}
