package com.ovsoftware.ictu.osb.tmfportal.service.common;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebserviceSettings {

	private static Logger logger = Logger.getLogger(WebserviceSettings.class);
	private static HashMap<String, String> settings = new HashMap<String, String>();
	private static String xmlFilename = "settings.xml";	
	/** Default settings. */
	private static KeyValuePair[] kvpArray = {
		new KeyValuePair("nieuwe_melding.action_old", 		"A04000000000046-tmf-aanmelden-terugmelding-https---s794.nxs.nl-corvus-httpd-ebms-inbound"),
		new KeyValuePair("intrekking.action_old", 			"A04000000000046-tmf-aanmelden-intrekking-https---s794.nxs.nl-corvus-httpd-ebms-inbound"),
		new KeyValuePair("ophalen_melding.action_old", 		"http://wus.tmf.gbo.overheid.nl/wsdl/ophalenService/ophalenMeldingStatusRequest"),
		new KeyValuePair("ophalen_details.action_old", 		"http://wus.tmf.gbo.overheid.nl/wsdl/ophalenService/ophalenMeldingKenmerkRequest"),
		new KeyValuePair("attributen.action_old", 			"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoRequest"),
		new KeyValuePair("attributen_waardes.action_old", 	"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/bevragenRequest"),
		new KeyValuePair("objecttypelist.action_old", 		"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeListRequest"),
		new KeyValuePair("basisregistraties.action_old", 	"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListRequest"),
	};
	public static final String STELSELCATALOGUS_WSDL = "/WSDL/STELSELCATALOGUS/WUS/WUSLITE/stelselBevragen-servicebinding.wsdl";
	public static final String TMFCORE_EBMS_WSDL = "/WSDL/TMFCORE/EBMS/WUSLITE/Service.wsdl";
	public static final String TMFCORE_WUS_WSDL = "/WSDL/TMFCORE/WUS/WUSLITE/ophaalService-servicebinding.wsdl";
	
	public static final String STELSELCATALOGUS_NAMESPACE = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1";
	public static final String OPHAALSERVICE_NAMESPACE = "http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1";
	public static final String AANMELDEN_NAMESPACE = "http://tmfportal.ovsoftware.com/services";
	
	public static final String STELSELCATALOGUS_SERVICE = "stelselBevragenService";
	public static final String TMFCORE_EBMS_SERVICE = "tmf-aanmelden";
	public static final String TMFCORE_WUS_SERVICE = "ophaalService";
	/**
	 * Nep constructor die voorkomt dat je een instantie kunt aanmaken
	 * van deze utility klasse.
	 * 
	 * @throws UnsupportedOperationException
	 */
	protected WebserviceSettings() {
		throw new UnsupportedOperationException();
	}
	

	/**
	 * Haalt de waarde van de meegegeven setting.
	 * 
	 * @param settingName De naam (key) van de op te halen setting
	 * @return De waarde van de gevraagde setting
	 */
	public static String getValueOfSetting(String settingName) {
		// Laad het settings-bestand indien nodig
		if (settings.size() == 0) { reloadSettings(); }

		// probeer de waarde op te halen
		String result = settings.get(settingName);

		// log wat we retourneren
		if (result != null) {
			logger.trace("Returning >>>" + result + "<<<");
		} else {
			logger.error("Returning null (nr settings is " + settings.size() + ", settingName = '" + settingName + "')");
		}

		// return de gewenste waarde
		return result;
	}

	/**
	 * Geeft een lijst van KeyValuePair (sleutel, waarde) objecten uit het 
	 * Settings-bestand waarvan de sleutel eindigt op 'end_with'.
	 * 
	 * @param endWith De string waarop elke geretourneerde key moet eindigen
	 * @return Een lijst met KeyValuePair (sleutel, waarde) objecten waarvan de
	 * sleutels eindigen op 'end_with'
	 */
	public static ArrayList<KeyValuePair> getSettingsEndingWith(String endWith) {
		// Laad het settings-bestand indien nodig
		if (settings.isEmpty()) { reloadSettings(); }

		// prep een arraylist voor het resultaat (key, value)
		ArrayList<KeyValuePair> result = new ArrayList<KeyValuePair>();

		// sla de waarde van alle settings die eindigen op 'end_with' op in de
		// arraylist
		for (Object obj : settings.keySet()) {
			String key = (String) obj;
			if (key.endsWith(endWith)) {
				String value = settings.get(key);
				KeyValuePair kvp = new KeyValuePair(key, value);
				result.add(kvp);
			}
		}

		// retourneer de arraylist
		return result;
	}

	/**
	 * Zet de bestandsnaam waaruit de settings geladen worden en probeert de
	 * settings te herladen. Indien het bestand niet gevonden kan worden dan
	 * wordt het oude bestand aangehouden.
	 * 
	 * @param xmlFilename De bestandsnaam van het Settings-bestand
	 */
	public static void setSettingsFilename(String xmlFilename) {
		WebserviceSettings.xmlFilename = xmlFilename;
		reloadSettings();
	}

	/**
	 * (Her)laad de settings.
	 */
	@SuppressWarnings("unchecked")
	private static void reloadSettings() {
		//gooi de oude settings alvast weg
		settings.clear();
		
		//probeer het nieuwe bestand in te lezen
		HashMap<String, String> settingsUitXml = null;
		try {
			ApplicationContext ac = new ClassPathXmlApplicationContext(xmlFilename);
			settingsUitXml =  (HashMap<String, String>) ac.getBean("settings");
		} catch (NoSuchBeanDefinitionException nsbde) {
			logger.error("NoSuchBeanDefinitionException in reloadSettings tijdens laden '" + xmlFilename + "'!", nsbde);
			return;
		} catch (BeansException be) {
			logger.error("BeansException in reloadSettings tijdens laden '" + xmlFilename + "'!", be);
			return;
		}
			
		//check of er iets is gelezen, zo niet log de fout en return
		if ((settingsUitXml == null) || settingsUitXml.isEmpty()) {
			logger.error("Settingsbestand '" + xmlFilename + "' kon niet succesvol worden ingeladen!");
			return;
		}
		
		//voeg deze toe aan settings
		settings.putAll(settingsUitXml);
		
		//en voeg tenslotte nog de default settings erbij
		settings.putAll(getDefaultSettings());
	}

	/**
	 * Retourneer de default settings (uit kvpArray) in de vorm van een HashMap(String, String).
	 * 
	 * @return Een HashMap met String,String entries met daarin de default settings
	 */
	private static HashMap<String, String> getDefaultSettings() {
		HashMap<String, String> defaultSettings = new HashMap<String, String>();
		
		for (KeyValuePair kvp : kvpArray) {
			defaultSettings.put(kvp.getKey(), kvp.getValue());
		}
		
		return defaultSettings;
	}
	
}
