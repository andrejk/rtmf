package com.ovsoftware.ictu.osb.tmfportal.service.common;

import com.ovsoftware.ictu.osb.tmfportal.service.common.WebserviceSettings;

import junit.framework.TestCase;

public class TestWebserviceSettings extends TestCase {

	/**
	 * Test of getProperty de waarde null retourneerd voor een niet bestaande property.
	 */
	public void testGetNullSetting() {
		WebserviceSettings.setSettingsFilename("settings.xml");
		String result = WebserviceSettings.getValueOfSetting("blabliadg");
		assertNull("Result is niet null voor niet bestaande property", result);
	}
	
	/**
	 * Test of een setting uit correct opgegeven bestand kan worden opgehaald.
	 */
	public void testGetCorrectSetting() {
		WebserviceSettings.setSettingsFilename("settings.xml");
		String result = WebserviceSettings.TMFCORE_WUS_SERVICE;
		assertEquals("Result is niet gelijk aan setting", result, "ophaalService");
	}
	
	/**
	 * Controleert of na het ingeven van een incorrect settingsbestand de waardes
	 * van het vorige settingsbestand nog worden geretourneerd. De oude waardes zouden
	 * namelijk niet meer beschikbaar moeten zijn.
	 */
	public void testGetSettingNoSuchFile() {
		WebserviceSettings.setSettingsFilename("settings.xml");
		//volgende bestand setten gaat fout...oude waardes zijn ook niet meer beschikbaar
		WebserviceSettings.setSettingsFilename("no-such-agency.xml");
		String result = WebserviceSettings.getValueOfSetting("nieuwe_melding.action_new");
		assertNull("Result is niet null terwijl er een incorrect settingsbestand is opgegeven", result);
		WebserviceSettings.setSettingsFilename("settings.xml");
	}
	
}
