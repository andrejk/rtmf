package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import java.util.GregorianCalendar;

import junit.framework.TestCase;

public class TestTerugmeldingDetails extends TestCase {
	
	/**
	 * Test de constructor en getters van TerugmeldingDetails.
	 */
	public void testConstructorAndGetters() {
		
		TerugmeldingKern Ctk = new TerugmeldingKern();
		String Cafdeling = "afdeling";
		GregorianCalendar CtijdstempelOntvangst = new GregorianCalendar();
		GregorianCalendar CtijdstempelGemeld = new GregorianCalendar();
		String CstatusMelding = "statusMelding";
		GregorianCalendar CtijdstempelStatus = new GregorianCalendar();
		String CtoelichtingStatus = "toelichtingSatus";
				
		TerugmeldingDetails td = new TerugmeldingDetails(Ctk,
														 Cafdeling,
														 CtijdstempelOntvangst,
														 CtijdstempelGemeld,
														 CstatusMelding,
														 CtijdstempelStatus,
														 CtoelichtingStatus);
		
		assertEquals("TerugmeldingKern niet gelijk", Ctk, td.getTerugmeldingKern());
		assertEquals("Afdeling niet gelijk", Cafdeling, td.getAfdeling());
		assertEquals("TijdstempelOntvangst niet gelijk", CtijdstempelOntvangst, td.getTijdstempelOntvangst());
		assertEquals("TijdstempelGemeld niet gelijk", CtijdstempelGemeld, td.getTijdstempelGemeld());
		assertEquals("StatusMelding niet gelijk", CstatusMelding, td.getStatusMelding());
		assertEquals("TijdstempelStatus niet gelijk", CtijdstempelStatus, td.getTijdstempelStatus());
		assertEquals("TijdstempelStatus niet gelijk", CtoelichtingStatus, td.getToelichtingStatus());
		
	}
}
