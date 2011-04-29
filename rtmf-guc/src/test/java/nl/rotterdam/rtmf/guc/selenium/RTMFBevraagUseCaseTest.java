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



/**
 * Deze testcase werkt samen met de groovy mocks.
 * 
 * Dit is een TerugMelding met een bevraag en verbeter actie.
 * 
 * @author Peter Paul Bakker
 *
 */
public class RTMFBevraagUseCaseTest extends RtmfSeleniumUseCaseBase {
	
	public void testRTMFBevraagUseCase() throws Exception {
		selenium.open("/main.htm");
		selenium.click("link=Terugmelden");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.select("BasisRegistratieTag", "label=Gemeentelijke Basisregistratie Persoonsgegevens");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		// let op, dit is een bsn nummer!
		selenium.type("BRObjectID", "126946036");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Bevraag");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		verifyEquals("Everard", selenium.getValue("new01.02.10"));
		verifyEquals("Daniëls", selenium.getValue("new01.02.40"));
		verifyEquals("Man", selenium.getValue("new01.04.10"));
		selenium.type("new01.02.10", "Evelyn");
		selenium.type("new01.04.10", "Vrouw");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("reason", "Naam klopte niet");
		selenium.type("meldingkenmerk", "P123");
		selenium.type("name", "PP Bakker");
		selenium.type("phonenumber", "020-123");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("//input[@value='Versturen']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		assertTrue("Terugmelding is niet succesvol verstuurd.",
				selenium.isTextPresent("Terugmelding is succesvol verstuurd"));
	}
}
