package com.ovsoftware.ictu.osb.tmfportal.service.common;

import java.util.ArrayList;

import junit.framework.TestCase;

public class TestStatussen extends TestCase {

	/**
	 * Test of de lijst met statussen correct wordt ingeladen.
	 */
	public void testStatussen() {
	
		//haal de lijst op
		ArrayList<String> statussen = Statussen.getValues();
		
		//test de lijst
		assertNotNull("Statussen is null!", statussen);
		assertTrue("Grootte lijst niet 7 maar " + statussen.size(), statussen.size() == 7);
	
		//test geldige statussen
		assertTrue("De status 'ontvangen' is niet een geldige status!", Statussen.geldigeStatus("ontvangen"));
		assertTrue("De status 'onderzocht' is niet een geldige status!", Statussen.geldigeStatus("onderzocht"));
	
		//test niet geldige status
		assertTrue("De status 'ontvangen123' is een geldige status!", !Statussen.geldigeStatus("ontvangen123"));
		
		//test status null
		assertTrue("De status null is een geldige status!", !Statussen.geldigeStatus(null));
	
		//test status intrekbaar
		assertTrue("De status 'ontvangen' is niet intrekbaar!", Statussen.isIntrekbaar("ontvangen"));
		assertTrue("De status 'gemeld' is niet intrekbaar!", Statussen.isIntrekbaar("gemeld"));
	}
}
