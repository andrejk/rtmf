package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import junit.framework.TestCase;

public class TestTerugmeldingKern extends TestCase {

	/**
	 * Test de constructor en getters van TerugmeldingKern.
	 */
	public void testConstructorAndGetters() {
		
		String CtmfKenmerk = "tmfKenmerk";
		String CberichtSoort = "berichtSoort";
		String CidOrganisatie = "idOrganisatie";
		String CnaamOrganisatie = "naamOrganisatie";
		TerugmeldingKern tk = new TerugmeldingKern(CtmfKenmerk,
												   CberichtSoort,
												   CidOrganisatie,
												   CnaamOrganisatie);
		
		assertEquals("tmfKenmerk niet gelijk", CtmfKenmerk, tk.getTmfKenmerk());
		assertEquals("berichtSoort niet gelijk", CberichtSoort, tk.getBerichtSoort());
		assertEquals("idOrganisatie niet gelijk", CidOrganisatie, tk.getIdOrganisatie());
		assertEquals("naamOrganisatie niet gelijk", CnaamOrganisatie, tk.getNaamOrganisatie());
	}
}
