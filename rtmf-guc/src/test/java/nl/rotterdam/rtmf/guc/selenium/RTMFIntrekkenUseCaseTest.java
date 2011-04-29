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

public class RTMFIntrekkenUseCaseTest extends RtmfSeleniumUseCaseBase {
	
	public void testRTMFIntrekkenUseCase() throws Exception {
		selenium.open("/main.htm");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Inzien / Intrekken");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("meldingkenmerk", "123");
		selenium.click("//input[@value='Ophalen gegevens']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=123");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("//input[@value='Intrekken']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("Toelichting", "foutje");
		selenium.click("//input[@value='Versturen']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		String pattern = "Intrekking is succesvol verstuurd";
		assertTrue("De verwachte melding ontbreekt: '" + pattern + "'", 
				selenium.isTextPresent(pattern));
	}
}
