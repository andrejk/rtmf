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
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("BRObjectID", "3095146104");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("newTMF-PERSOON-VOORNAAM", "Piet");
		selenium.type("newTMF-PERSOON-TUSSENVOEGSEL", "van de");
		selenium.type("newTMF-PERSOON-ACHTERNAAM", "boomen");
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
