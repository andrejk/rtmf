package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import junit.framework.TestCase;

public class TestContactGegevens extends TestCase {
	
	/**
	 * Test de constructor en getters van ContactGegevens.
	 */
	public void testConstructorAndGetters() {
		
		String Cnaam = "naam";
		String Ctelefoon = "telefoon";
		String Cemail = "email";
		
		ContactGegevens cg = new ContactGegevens(Cnaam, Ctelefoon, Cemail);
	
		assertEquals("naam niet gelijk", Cnaam, cg.getNaam());
		assertEquals("telefoon niet gelijk", Ctelefoon, cg.getTelefoon());
		assertEquals("email niet gelijk", Cemail, cg.getEmail());
	}
}
