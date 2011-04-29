/*
 * Copyright (c) 2009-2011 Gemeente Rotterdam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the European Union Public Licence (EUPL),
 * version 1.1 (or any later version).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * European Union Public Licence for more details.
 *
 * You should have received a copy of the European Union Public Licence
 * along with this program. If not, see
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
*/
package nl.rotterdam.rtmf.guc;

import junit.framework.TestCase;
import nl.rotterdam.rtmf.guc.cache.StelselCatalogusCacheHSQL;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.junit.Test;

/**
 * Test voor de HSQL implementatie voor de cache
 * 
 * @author rweverwijk
 * 
 */
public class StelselCatalogusCacheHSQLTest extends TestCase {
	StelselCatalogusCache cache;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cache = new StelselCatalogusCacheHSQL();
	}

	/**
	 * Test wat er gebeurt als een key niet aanwezig is.
	 */
	@Test
	public void testOnbekendeKey() {
		try {
			cache.determineServiceCallsForKey("TESTCase1");
		} catch (RtmfGucException e) {
			assertEquals(
					"De cache heeft de service niet kunnen achterhalen voor de key: TESTCase1",
					e.getMessage());
			return;
		}
		fail("Als de key niet gevonden is moet er een exceptie optreden");
	}

	/**
	 * Test om de cache te vullen en te testen of elke overgang goed loopt.
	 */
	@Test
	public void testViaGmNaarBoth() {
			
		cache.addKey("TESTCase2", "GM", false, "");
		String testCase2 = cache.determineServiceCallsForKey("TESTCase2");
		assertEquals("GM", testCase2);

		cache.addKey("TESTCase2", "TMF", false, "");
		testCase2 = cache.determineServiceCallsForKey("TESTCase2");
		assertEquals("Both", testCase2);
	}

	/**
	 * Test om de cache te vullen en te testen of elke overgang goed loopt.
	 */
	@Test
	public void testViaTmfNaarBoth() {
		cache.addKey("TestCase3", "TMF", false, "ObjectnaamCase3TMF");
		String testCase3 = cache.determineServiceCallsForKey("TestCase3");
		assertEquals("TMF", testCase3);
		assertEquals("ObjectnaamCase3TMF", cache.getObjectNaam("TestCase3"));

		cache.addKey("TestCase3", "GM", false, "ObjectnaamCase3GM");
		testCase3 = cache.determineServiceCallsForKey("TestCase3");
		assertEquals("Both", testCase3);
		assertEquals("ObjectnaamCase3GM", cache.getObjectNaam("TestCase3"));
	}
	
	/**
	 * Test stuffpath
	 */
	@Test
	public void testStufPath() {
		boolean isBevraagbaar = false;
		String expectedStufpath = "Xpath Expression";
		String key = "TestCaseStufpath";
		cache.addKey(key, "GM", isBevraagbaar, expectedStufpath,null);
		String stufpath = cache.getStufpathForKey(key);
		assertEquals(expectedStufpath, stufpath);
	}

	/**
	 * Test stuffpath twee keer, eentje van GM en eentje van TMF.
	 * Het is nu zo dat alleen GM een stufpath kent, TMF mag dat
	 * niet overschrijven met een leeg stufpath.
	 */
	@Test
	public void testStufPathGmEnTmf() {
		boolean isBevraagbaar = false;
		String expectedStufpath = "Xpath Expression";
		String key = "TestCaseStufpath";
		cache.addKey(key, "GM", isBevraagbaar, expectedStufpath, null);
		cache.addKey(key, "TMF", isBevraagbaar, null);
		String stufpath = cache.getStufpathForKey(key);
		assertEquals(expectedStufpath, stufpath);
	}

}
