package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.mock;

import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;

import junit.framework.TestCase;

public class TestTerugmeldingCompleet extends TestCase {

	/**
	 * Test de constructor en getters van TerugmeldingCompleet.
	 */
	public void testConstructorAndGetters() {
		
		Terugmelding Cterugmelding = new Terugmelding();
		TerugmeldingDetails CterugmeldingDetails = new TerugmeldingDetails();
		
		TerugmeldingCompleet tc = new TerugmeldingCompleet(Cterugmelding, CterugmeldingDetails);
		
		assertEquals("Terugmelding niet gelijk", Cterugmelding, tc.getTerugmelding());
		assertEquals("TerugmeldingDetails niet gelijk", CterugmeldingDetails, tc.getTerugmeldingDetails());
	}
	
	/**
	 * Test de setters en getters van TerugmeldingCompleet.
	 */
	public void testSettersAndGetters() {
		
		Terugmelding Cterugmelding = new Terugmelding();
		TerugmeldingDetails CterugmeldingDetails = new TerugmeldingDetails();
		
		TerugmeldingCompleet tc = new TerugmeldingCompleet();
		
		tc.setTerugmelding(Cterugmelding);
		tc.setTerugmeldingDetails(CterugmeldingDetails);
		
		assertEquals("Terugmelding niet gelijk", Cterugmelding, tc.getTerugmelding());
		assertEquals("TerugmeldingDetails niet gelijk", CterugmeldingDetails, tc.getTerugmeldingDetails());
	}
	
}
