package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import java.util.Arrays;

import junit.framework.TestCase;

public class TestBijlage extends TestCase {
	
	/**
	 * Test de constructor en getters van Bijlage.
	 */
	public void testConstructorAndGetters() {
		
		byte[] CbArr = { 0x01, 0x02, 0x03 };
		String Cbestandsnaam = "bestandsnaam";
		
		Bijlage b = new Bijlage(CbArr, Cbestandsnaam);
		
		assertTrue("byte array niet gelijk", Arrays.equals(CbArr, b.getInhoud()));
		assertEquals("bestandsnaam niet gelijk", Cbestandsnaam, b.getBestandsnaam());
	}

}
