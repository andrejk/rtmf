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
package nl.rotterdam.rtmf.guc.selenium;

import nl.rotterdam.rtmf.guc.test.RtmfSeleniumUseCaseBase;

public class RTMFSplitsUseCase extends RtmfSeleniumUseCaseBase {
	
	public void testRTMFSplitsUseCase() throws Exception {
		String[] selectOptions = null;
		selenium.open("main.htm");
		selenium.click("link=Start");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Terugmelden");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selectOptions = selenium.getSelectOptions("BasisRegistratieTag");
		assertEquals(10, selectOptions.length);
		assertTrue(contains(selectOptions, "Personen (TMF)"));
		assertTrue(contains(selectOptions, "Vervoer (TMF)"));
		assertTrue(contains(selectOptions, "Havens (GM)"));
		assertTrue(contains(selectOptions, "Gemeentelijke Basisregistratie Persoonsgegevens"));
		selenium.select("BasisRegistratieTag", "label=Personen (TMF)");
		
		// test naar beiden services.
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selectOptions = selenium.getSelectOptions("BRObjectTag");		
		assertEquals(4, selectOptions.length);
		assertTrue(contains(selectOptions, "Persoon"));
		assertTrue(contains(selectOptions, "Gebouw"));
		assertTrue(contains(selectOptions, "Vergunning"));
		assertTrue(contains(selectOptions, "Gemeentelijke Vergunning"));
		// test Vervoer (TMF) Alleen tmf
		selenium.click("//input[@value='<< Vorige']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);		
		selenium.select("BasisRegistratieTag", "label=Vervoer (TMF)");		
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selectOptions = selenium.getSelectOptions("BRObjectTag");
		assertEquals(3, selectOptions.length);
		assertTrue(contains(selectOptions, "Auto"));
		assertTrue(contains(selectOptions, "Vrachtwagen"));
		assertTrue(contains(selectOptions, "Bus"));
		// test alleen gm
		selenium.click("//input[@value='<< Vorige']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);		
		selenium.select("BasisRegistratieTag", "label=Havens (GM)");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selectOptions = selenium.getSelectOptions("BRObjectTag");
		assertEquals(3, selectOptions.length);
		assertTrue(contains(selectOptions, "Vrachtwagen"));
		assertTrue(contains(selectOptions, "Bus"));
		assertTrue(contains(selectOptions, "Rotterdamse-BOOT"));
		
		// test persoonsgegevens
		selenium.click("//input[@value='<< Vorige']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);		
		selenium.select("BasisRegistratieTag", "label=Gemeentelijke Basisregistratie Persoonsgegevens");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selectOptions = selenium.getSelectOptions("BRObjectTag");
		assertEquals(13, selectOptions.length);
		assertTrue(contains(selectOptions, "Natuurlijk Persoon"));
	}
	
	private boolean contains(String[] source, String key) {
		if (source != null && key != null) {
			for (String element : source) {
				if (element.equals(key)) {
					return true;
				}
			}
		}
		return false;
	}
}
