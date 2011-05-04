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
 * Deze testcase werkt samen de mock services.
 * 
 * @author Peter Paul Bakker
 *
 */
public class RTMFInzienUseCaseTest extends RtmfSeleniumUseCaseBase {
	
	public void testRTMFInzienBestaandKenmerkUseCase() throws Exception {
		selenium.open("/main.htm");
		selenium.click("link=Start");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Inzien / Intrekken");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("meldingkenmerk", "123");
		selenium.click("//input[@value='Ophalen gegevens']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=123");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		assertTrue("Verwachte text met kenmerk '123' en status 'ontvangen' niet op het scherm.", 
				selenium.isTextPresent("123") & selenium.isTextPresent("ontvangen"));

	}
	public void testRTMFInzienNietBestaandKenmerkUseCase() throws Exception {
		selenium.open("/main.htm");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Start");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Inzien / Intrekken");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("meldingkenmerk", "1234");
		selenium.click("//input[@value='Ophalen gegevens']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		String pattern = "Er zijn geen terugmeldingen die voldoen aan de selectiecriteria.";
		assertTrue("De verwachte melding ontbreekt: '" + pattern + "'", 
				selenium.isTextPresent(pattern));

	}
}
