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
 * Deze testcase werkt samen met de mock services.
 * 
 * Dit is een TerugMelding met een bevraag en verbeter actie welke alleen
 * betrekking heeft op data uit de Rotterdamse catalogus
 * 
 * @author Enno Buis
 * 
 */
public class RTFMzmTerugmeldingUseCaseTest extends RtmfSeleniumUseCaseBase {

	public void testRTFMzmTerugmeldingUseCaseTest() throws Throwable {
		try {
			selenium.open("/terugmelding_Send.htm");
			selenium.click("link=Start");
			selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
			selenium.click("link=Terugmelden");
			selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
			selenium.select("BasisRegistratieTag", "label=Havens (GM)");
			selenium.click("//input[@value='Volgende >>']");
			selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
			// comment volgende regel om een fout te forceren voor de screencapture
			selenium.type("BRObjectID", "XB-01-YZ");
			selenium.click("//input[@value='Volgende >>']");
			selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
			selenium.type("oldGM-PERSOON-VOORNAAM", "LKW");
			selenium.type("newGM-PERSOON-VOORNAAM", "Camion");
			selenium.click("//input[@value='Volgende >>']");
			selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
			selenium.type("reason", "Test GM");
			selenium.type("meldingkenmerk", "TestGM");
			selenium.click("//input[@value='Volgende >>']");
			selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
			selenium.click("//input[@value='Versturen']");
			selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
			assertTrue(
					"Terugmelding niet succesvol verstuurd, volgens text op het scherm. Zie TMFPortal log file.",
					selenium.isTextPresent("Terugmelding is succesvol verstuurd"));
		} catch (Throwable e) {
			 selenium.captureEntirePageScreenshot("/tmp/" + this.getClass().getName() + "."
                     + "testRTFMzmTerugmeldingUseCaseTest" + ".png", "");
			 throw e;
		}
	}

}
