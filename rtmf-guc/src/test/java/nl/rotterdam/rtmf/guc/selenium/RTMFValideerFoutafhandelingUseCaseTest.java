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
