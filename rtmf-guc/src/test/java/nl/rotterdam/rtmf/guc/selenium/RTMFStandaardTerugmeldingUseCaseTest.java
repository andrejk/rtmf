package nl.rotterdam.rtmf.guc.selenium;

import nl.rotterdam.rtmf.guc.test.RtmfSeleniumUseCaseBase;

/**
 * Deze testcase werkt samen met de service mocks in mule.
 * 
 * Dit is een TerugMelding met een bevraag en verbeter actie.
 * 
 * @author Peter Paul Bakker
 *
 */
public class RTMFStandaardTerugmeldingUseCaseTest extends RtmfSeleniumUseCaseBase {
	
	public void testTmfSmoke() throws Exception {
		selenium.open("/");
		selenium.click("link=Start");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("link=Terugmelden");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("BRObjectID", "2063193504");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("oldTMF-PERSOON-VOORNAAM", "Petet");
		selenium.type("newTMF-PERSOON-VOORNAAM", "Peter");
		selenium.type("oldTMF-PERSOON-TUSSENVOEGSEL", "vab");
		selenium.type("newTMF-PERSOON-TUSSENVOEGSEL", "van");
		selenium.type("oldTMF-PERSOON-ACHTERNAAM", "Oort");
		selenium.type("newTMF-PERSOON-ACHTERNAAM", "Oord");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.type("reason", "Naam klopt niet");
		// kenmerk 123 is voor de intrekken use case test
		selenium.type("meldingkenmerk", "P123");
		selenium.type("name", "PP Bakker");
		selenium.type("phonenumber", "020-123");
		selenium.type("email", "pp@abc");
		selenium.click("//input[@value='Volgende >>']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		selenium.click("//input[@value='Versturen']");
		selenium.waitForPageToLoad(PAGE_WAIT_TIMEOUT);
		assertTrue("Terugmelding niet succesvol verstuurd, volgens text op het scherm. Zie TMFPortal log file.", 
				selenium.isTextPresent("Terugmelding is succesvol verstuurd"));
	}
}
