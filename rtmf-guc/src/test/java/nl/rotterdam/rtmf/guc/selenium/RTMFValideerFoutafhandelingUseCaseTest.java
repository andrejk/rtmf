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

import org.junit.Test;

import nl.rotterdam.rtmf.guc.test.RtmfSeleniumUseCaseBase;

/**
 * Deze testcase werkt samen met de mock services.
 * 
 * Dit zijn twee bevragings acties voor twee subjecten waarbij er vanuit de mock
 * service een fout bericht wordt geretouneerd
 * 
 * @author Theo van Arem
 * 
 */
public class RTMFValideerFoutafhandelingUseCaseTest extends
		RtmfSeleniumUseCaseBase {
	/**
	 * Test subject 666
	 */
	@Test
	public void testRTMFValideerFoutafhandelingBSN666() {
		selenium.open("/main.htm");
		selenium.click("link=Terugmelden");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.select("BasisRegistratieTag",
				"label=Gemeentelijke Basisregistratie Persoonsgegevens");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		// let op, dit is een bsn nummer!
		selenium.type("BRObjectID", "666");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Bevraag");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.isTextPresent("Er is een fout opgetreden");
	}

	/**
	 * Test subject 600
	 */
	@Test
	public void testRTMFValideerFoutafhandelingBSN600() {
		selenium.open("/main.htm");
		selenium.click("link=Terugmelden");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.select("BasisRegistratieTag",
				"label=Gemeentelijke Basisregistratie Persoonsgegevens");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		// let op, dit is een bsn nummer!
		selenium.type("BRObjectID", "600");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Bevraag");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.isTextPresent("Er is een fout opgetreden");
	}

}
