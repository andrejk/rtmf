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
package nl.rotterdam.rtmf.guc.bronhouder.catalogus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import nl.rotterdam.rtmf.guc.bronhouder.bean.BronhouderInfo;
import nl.rotterdam.rtmf.guc.common.DocumentParser;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.apache.log4j.Logger;

/**
 * Deze class zorgt ervoor dat de lijst met properties wordt omgezet naar een
 * aantal doorzoekbare lijsten. Met kan zoeken op Bronhouder naam of op basisregistratiect.
 * De zoek functies retourneren basisregistraties van het type BronhouderInfo.
 * 
 * @author Enno Buis
 * 
 */
public class BronhouderCatalogus {

	static final int LENGTE_BRONHOUDER = "bronhouder".length();

	private static Logger logger = Logger.getLogger("rtmfguc.guc.bronhouder");

	private Properties properties = null;
	private List<BronhouderInfo> bhil = new ArrayList<BronhouderInfo>();
	/**
	 * Lijst met basisregistraties met de basisregistratie als key en indexen uit de 
	 * bhiRef table als value
	 */
	private Hashtable<String, Set<Integer>> basisRef = new Hashtable<String, Set<Integer>>();
	private Hashtable<String, Integer> bhiRef = new Hashtable<String, Integer>();

	public BronhouderCatalogus() {
		// nop
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
		logger.debug("Aanroepen: processProperties");
		processProperties();
	}

	/**
	 * Haal de manier op waarmee een terugmelding aan een Bronhouder bekend
	 * gemaakt wordt. Dit kan zijn via Email of via File.
	 * 
	 * @param payloadAsString
	 *            Het (terugmelden) bericht dat naar de Bronhouder gestuurd moet
	 *            worden.
	 * @return De manier van terugmelden aan de bronhouder (File of Email).
	 * @throws RtmfGucException
	 */
	public String determineBereikenViaForTerugmelding(String payloadAsString)
			throws RtmfGucException {
		String result = null;
		String tagValue = null;

		tagValue = getBasisregistratieTag(payloadAsString);
		if (tagValue != null && !tagValue.equals("")) {
			List<BronhouderInfo> bronhouderInfoByBasisregistratie = getBronhouderInfoByBasisregistratie(tagValue);
			BronhouderInfo bhi = bronhouderInfoByBasisregistratie.isEmpty() ? null : bronhouderInfoByBasisregistratie.get(0);
			if (bhi != null) {
				result = bhi.getBereikenVia();
			}
		}

		return result;
	}

	/**
	 * Haal het adres op waarmee een terugmelding aan een Bronhouder bekend
	 * gemaakt wordt. Dit kan zijn een email adres of een file path.
	 * 
	 * @param payloadAsString
	 *            Het (terugmelden) bericht dat naar de Bronhouder gestuurd moet
	 *            worden.
	 * @return Het adres van terugmelden aan de bronhouder (email adres of file
	 *         path).
	 * @throws RtmfGucException
	 */
	public String determineBereikenAdresForTerugmelding(String payloadAsString)
			throws RtmfGucException {
		String result = null;
		String tagValue = null;

		tagValue = getBasisregistratieTag(payloadAsString);
		if (tagValue != null && !tagValue.equals("")) {
			List<BronhouderInfo> bronhouderInfoByBasisregistratie = getBronhouderInfoByBasisregistratie(tagValue);
			BronhouderInfo bhi = bronhouderInfoByBasisregistratie.isEmpty() ? null : bronhouderInfoByBasisregistratie.get(0);
			if (bhi != null) {
				result = bhi.getBereikenAdres();
			}
		} else {
			throw new RtmfGucException(String.format(
					"No match found in bronhouder catalog for bronhouder: %s",
					tagValue));
		}

		return result;
	}

	/**
	 * Geef de BronhouderInfo terug voor de gegeven bronhouder naam.
	 * 
	 * @param naam
	 *            Naam van de bronhouder
	 * @return BronhouderInfo Informatie over de bronhouder
	 */
//	public BronhouderInfo getBronhouderInfoByNaam(String naam) {
//		BronhouderInfo bhi = null;
//
//		Integer bhIdx = bhiRef.get(naam.toLowerCase());
//		if (bhIdx != null) {
//			bhi = bhil.get(bhIdx);
//		} else {
//			bhi = bhil.get(0);
//		}
//
//		return bhi;
//	}

	/**
	 * Geef een lijst van BronhouderInfo basisregistraties terug met de bronhouder(s)
	 * welke een basisregistratie beheren waarvan de basisregistratie naam overeenkomt met het
	 * gevraagde basisregistratie.
	 * 
	 * Indien geen bronhouder(s) voor het gegeven basisregistratie gevonden kunnen worden,
	 * dan zal er een 'lege' lijst (size==0) worden geretourneerd.
	 * @param basisregistratie 
	 * 
	 * @param naam
	 *            Naam van het basisregistratie waarnaar gezocht wordt.
	 * @return Lijst van BronhouderInfo basisregistraties.
	 */
	public List<BronhouderInfo> getBronhouderInfoByBasisregistratie(String basisregistratie) {
		List<BronhouderInfo> bhl = new ArrayList<BronhouderInfo>();

		logger.debug("");
		logger.debug("Zoeken op: " + basisregistratie);
		Set<Integer> bhList = basisRef.get(basisregistratie.toLowerCase());
		if (bhList != null && bhList.size() > 0) {
			logger.debug("bhList.size(): " + bhList.size());
			for (Integer bhIdx : bhList) {
				logger.debug("Index: " + bhIdx.intValue());
				bhl.add(bhil.get(bhIdx.intValue()));
			}
		}
		logger.debug("");
		return bhl;
	}

	/**
	 * Haal de tag van een basisregistratie uit de payload.
	 * 
	 * @param payloadAsString
	 *            Het (terugmelden) bericht dat naar de Bronhouder gestuurd moet
	 *            worden.
	 * @return De basisregistratie tag
	 * @throws RtmfGucException
	 */
	private String getBasisregistratieTag(String payloadAsString)
			throws RtmfGucException {
		String tagValue = null;
		if (payloadAsString.contains(":basisRegistratie")) {
			tagValue = DocumentParser.parseTmfDocument(payloadAsString,
					"/Envelope/Body/terugmelding/basisRegistratie/text()");
		} else {
			throw new RtmfGucException(String.format(
					"No match found in cache for message: %s", payloadAsString));
		}
		return tagValue;
	}

	/**
	 * Verwerk alle properties, en sla ze op in verschillende lijsten.
	 */
	private void processProperties() {
		// Haal alle property keys op.
		Set<Object> keys = properties.keySet();
		TreeSet<Object> sortedKeys = new TreeSet<Object>(keys);

		for (Object oKey : sortedKeys) {
			String key = ((String) oKey).trim();
			logger.debug("Key: " + key);
			// Skip comment (#) lines
			if (!"#".equals(key)) {
				parseAndStoreProperty(key);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Hier volg de lijst van Bronhouders");
			for (BronhouderInfo bhi : bhil) {
				logger.debug("Bronhouder.naam         : " + bhi.getNaam());
				logger.debug("Bronhouder.code         : " + bhi.getCode());
				logger.debug("Bronhouder.bereikenVia  : "
						+ bhi.getBereikenVia());
				logger.debug("Bronhouder.bereikenAdres: "
						+ bhi.getBereikenAdres());
				for (String basisregistratie : bhi.getBasisregistraties()) {
					logger.debug("Bronhouder.basisregistratie       : " + basisregistratie);
				}
				logger.debug("");
			}
		}

		// Maak referentie tabellen aan waarin op basisregistratie, en op bronhouder naam
		// gezocht kan woren naar de bronhouder info.
		createRefTables();

	}

	/**
	 * Pluis de property key uit elkaar om zodoende de waarde van de property in
	 * het juiste BronhouderInfo basisregistratie te plaatsen.
	 * 
	 * @param key
	 *            De key van de property om uit te pluizen
	 */
	private void parseAndStoreProperty(String key) {
		int bronNum = getBronhouderNummer(key);
		logger.debug("Volgnummer van de bronhouder: " + bronNum);
		if (bronNum > 0) {
			BronhouderInfo bhi = null;
			if (bronNum > bhil.size()) {
				// Nieuwe bronhouder
				logger.debug("Aanmaken nieuw BronhouderInfo basisregistratie");
				bhi = new BronhouderInfo();
				bhil.add(bronNum - 1, bhi);
			} else {
				// Reeds greaakte bronhouder ?
				bhi = bhil.get(bronNum - 1);
			}

			// Haal de veld waarde uit de key
			String veld = getVeldNaam(key).trim();
			logger.debug("Gevonden veld: " + veld);

			if (veld != null && !veld.equals("")) {
				// Haal de property waarde op
				String value = properties.getProperty(key);
				if (value != null && !value.equals("")) {

					logger.debug("Gevonden waarde: " + value);

					// Sla de property waarde op de het juiste BronhouderInfo
					// property
					if ("naam".equalsIgnoreCase(veld)) {
						bhi.setNaam(value);
					} else if ("code".equalsIgnoreCase(veld)) {
						bhi.setCode(value);
					} else if ("bereikenVia".equalsIgnoreCase(veld)) {
						if (value.equalsIgnoreCase("email")
								|| value.equalsIgnoreCase("file")) {
							bhi.setBereikenVia(value);
						} else {
							// Verkeerde waarde voor bereikenVia
							throw new RtmfGucException(
									"Er staat een foute property waarde in de rtmf-bronhouder.property file bij key: "
											+ key
											+ ". De waarde mag alleen Email of File zijn !");
						}
					} else if ("bereikenAdres".equalsIgnoreCase(veld)) {
						bhi.setBereikenAdres(value);
					} else if (veld.toLowerCase().startsWith("basisregistratie")) {
						bhi.addBasisregistratie(value);
					} else {
						// Verkeerde property in de property file
						throw new RtmfGucException(
								"Er staat een foute property gedefinieerd in de rtmf-bronhouder.property file: "
										+ key);
					}
					logger.debug(bhi);
				} else {
					logger.debug("Gevonden waarde: <leeg> Skipping ...");
				}
			}
		}
	}

	/**
	 * Haal het nummer van de bronhouder zoals dat in de property file staat uit
	 * de key:<br>
	 * bronhouder2.naam=abc - geeft 2<br>
	 * bronhouder23.naam=def - geeft 23
	 * 
	 * @param key
	 *            De property key waarin wordt gekeken naar het volgnummer.
	 * @return Hel volgnummer van de bronhouder in de property file.
	 */
	private int getBronhouderNummer(String key) {
		int nummer = 0;
		int dotPos = key.indexOf(".");
		nummer = Integer.parseInt(key.substring(LENGTE_BRONHOUDER, dotPos));
		return nummer;
	}

	/**
	 * Haal de veldnaam uit de property key:<br>
	 * bronhouder1.naam - geeft naam - <br>
	 * bronhouder23.basisregistratie2 - geeft basisregistratie2
	 * 
	 * @param key
	 *            De property key waarin wordt gekeken naar de veldnaam.
	 * @return De veldnaam uit de property key.
	 */
	private String getVeldNaam(String key) {
		String veld = null;
		int dotPos = key.indexOf(".");
		veld = (key.substring(dotPos + 1, key.length())).trim();
		return veld;
	}

	/**
	 * Deze functie maakt twee referentie tabellen aan waar mee gezocht kan
	 * worden naar bronhouder naam of basisregistratie. De beide tabellen maken een
	 * referentie naar de eerder gevulde ArrayList met BronhouderInfo basisregistratie.
	 */
	private void createRefTables() {

		logger.debug("Aanmaken referentie tabellen");
		for (int idx = 0; idx < bhil.size(); idx++) {
			BronhouderInfo bhi = bhil.get(idx);
			logger.debug("Bronhouder: " + bhi.getNaam());
			bhiRef.put(bhi.getNaam().toLowerCase(), idx);
			for (String basisregistratie : bhi.getBasisregistraties()) {
				String obj = basisregistratie.toLowerCase();
				Set<Integer> objToBiList = basisRef.get(obj);
				if (objToBiList == null) {
					objToBiList = new HashSet<Integer>();
					objToBiList.add(Integer.valueOf(idx));
					basisRef.put(basisregistratie.toLowerCase(), objToBiList);
				} else {
					objToBiList.add(Integer.valueOf(idx));
				}
			}
		}
	}

}
