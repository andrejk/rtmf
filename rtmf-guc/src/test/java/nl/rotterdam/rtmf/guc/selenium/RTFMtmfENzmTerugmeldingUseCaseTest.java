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
 * Deze testcase werkt samen de mocks.
 * 
 * Dit is een TerugMelding met een bevraag en verbeter actie welke zowel
 * betrekking heeft op data uit de landelijke catalogus als de rotterdamse
 * catalogus.
 * 
 * @author Enno Buis
 * 
 */
public class RTFMtmfENzmTerugmeldingUseCaseTest extends RtmfSeleniumUseCaseBase {

	public void testRTFMtmfENzmTerugmeldingUseCaseTest() throws Exception {
		selenium.open("/main.htm;jsessionid=1uxoir4xukrxq");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Start");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Terugmelden");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
        selenium.select("BasisRegistratieTag", "label=Gemeentelijke Basisregistratie Persoonsgegevens");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("BRObjectID", "3095146104");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
        String htmlSource = selenium.getHtmlSource();
        System.err.print(htmlSource);
        /* newTMF-PERSOON-VOORNAAM -> new01.02.10 */
		selenium.type("new01.02.10", "Piet");
        /* newTMF-PERSOON-TUSSENVOEGSE -> new01.02.30 */
		selenium.type("new01.02.30", "van de");
        /* newTMF-PERSOON-ACHTERNAAM -> new01.02.40 */
		selenium.type("new01.02.40", "boomen");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("reason", "Test TMF en ZM");
		selenium.type("meldingkenmerk", "TestTmfEnZm");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("//input[@value='Versturen']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		assertTrue(
				"Terugmelding niet succesvol verstuurd, volgens text op het scherm. Zie TMFPortal log file.",
				selenium.isTextPresent("Terugmelding is succesvol verstuurd"));
	}

}
