package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import junit.framework.TestCase;

public class TestObjectAttribuut extends TestCase{

	/**
	 * Test de constructor en getters van ObjectAttribuut.
	 */
	public void testConstructorAndGetters() {
		
		String CidAttribuut = "idAttribuut";
		String CbetwijfeldeWaarde = "betwijfeldeWaarde";
		String Cvoorstel = "voorstel";
		String CattribuutNaam = "attribuutNaam";
		
		ObjectAttribuut oa = new ObjectAttribuut(CidAttribuut, CbetwijfeldeWaarde, Cvoorstel, CattribuutNaam);
		
		assertEquals("idAttribuut niet gelijk", CidAttribuut, oa.getIdAttribuut());
		assertEquals("betwijfeldeWaarde niet gelijk", CbetwijfeldeWaarde, oa.getBetwijfeldeWaarde());
		assertEquals("voorstel niet gelijk", Cvoorstel, oa.getVoorstel());
		assertEquals("attribuutNaam niet gelijk", CattribuutNaam, oa.getAttribuutNaam());
	}
}
