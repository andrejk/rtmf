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
package nl.rotterdam.rtmf.guc;

import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;
import nl.rotterdam.rtmf.guc.bronhouder.bean.BronhouderInfo;
import nl.rotterdam.rtmf.guc.bronhouder.catalogus.BronhouderCatalogus;

public class BronhouderCatalogusTest extends TestCase {

	Properties propGoed = null;
	Properties propFout = null;

	String zendTerugmeldingGMemail = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "	<S:Header>"
			+ "		<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/tmfTerugmeldService</To>"
			+ "		<Action xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action>"
			+ "		<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ "			<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>"
			+ "		</ReplyTo>"
			+ "		<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">20090909</MessageID>"
			+ "	</S:Header>"
			+ "	<S:Body>"
			+ "		<ns2:terugmelding"
			+ "		xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\""
			+ "		xmlns:ns3=\"http://tmfportal.ovsoftware.com/services\" xmlns:ns4=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\">"
			+ "			<ns2:meldingKenmerk>UnitTester-001</ns2:meldingKenmerk>"
			+ "			<ns2:tijdstempelAanlevering>2009-08-27T15:51:58.635+02:00</ns2:tijdstempelAanlevering>"
			+ "			<ns2:basisRegistratie>RDAM-01</ns2:basisRegistratie>"
			+ "			<ns2:objectTag>GM-PERSOON</ns2:objectTag>"
			+ "			<ns2:objectIdentificatie>adres</ns2:objectIdentificatie>"
			+ "			<ns2:toelichting>Unit tester</ns2:toelichting>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>GM-PERSOON-VOORNAAM</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>Petet</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>Peter</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:contactInfo>"
			+ "				<ns2:naam></ns2:naam>"
			+ "				<ns2:telefoon></ns2:telefoon>"
			+ "				<ns2:email></ns2:email>"
			+ "			</ns2:contactInfo>"
			+ "		</ns2:terugmelding>" + "	</S:Body>" + "</S:Envelope>";

	String zendTerugmeldingGMfile = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "	<S:Header>"
			+ "		<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/tmfTerugmeldService</To>"
			+ "		<Action xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action>"
			+ "		<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ "			<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>"
			+ "		</ReplyTo>"
			+ "		<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">20090909</MessageID>"
			+ "	</S:Header>"
			+ "	<S:Body>"
			+ "		<ns2:terugmelding"
			+ "		xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\""
			+ "		xmlns:ns3=\"http://tmfportal.ovsoftware.com/services\" xmlns:ns4=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\">"
			+ "			<ns2:meldingKenmerk>UnitTester-001</ns2:meldingKenmerk>"
			+ "			<ns2:tijdstempelAanlevering>2009-08-27T15:51:58.635+02:00</ns2:tijdstempelAanlevering>"
			+ "			<ns2:basisRegistratie>RDAM-02</ns2:basisRegistratie>"
			+ "			<ns2:objectTag>TMF-PERSOON</ns2:objectTag>"
			+ "			<ns2:objectIdentificatie>adres</ns2:objectIdentificatie>"
			+ "			<ns2:toelichting>Unit tester</ns2:toelichting>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>GM-PERSOON-VOORNAAM</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>Petet</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>Peter</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:contactInfo>"
			+ "				<ns2:naam></ns2:naam>"
			+ "				<ns2:telefoon></ns2:telefoon>"
			+ "				<ns2:email></ns2:email>"
			+ "			</ns2:contactInfo>"
			+ "		</ns2:terugmelding>" + "	</S:Body>" + "</S:Envelope>";

	public void setUp() throws Exception {
		propGoed = new Properties();

		propGoed.put("bronhouder1.naam", "OnbekendeBronhouder");
		propGoed.put("bronhouder1.code", "Code van de onbekende bronhouder");
		propGoed.put("bronhouder1.bereikenVia", "Email");
		propGoed.put("bronhouder1.bereikenAdres",
				"applicatiebeheer@rtmf-guc.localhost");

		propGoed.put("bronhouder2.naam", "afdeling 1");
		propGoed.put("bronhouder2.code", "Code van RDAM-01");
		propGoed.put("bronhouder2.bereikenVia", "Email");
		propGoed.put("bronhouder2.bereikenAdres", "muletest@localhost");
		propGoed.put("bronhouder2.basisregistratie1", "RDAM-01");

		propGoed.put("bronhouder3.naam", "afdeling2");
		propGoed.put("bronhouder3.code", "Code van RDAM-02 bla bla");
		propGoed.put("bronhouder3.bereikenVia", "File");
		propGoed.put("bronhouder3.bereikenAdres", "file://tmp/GM2/");
		propGoed.put("bronhouder3.basisregistratie1", "RDAM-02");

	}

	public void testGetBronhouderInfoByObject() {

		BronhouderCatalogus bc = new BronhouderCatalogus();
		bc.setProperties(propGoed);

		List<BronhouderInfo> bi = bc.getBronhouderInfoByBasisregistratie("RDAM-01");
		assertNotNull("BronhouderInfo is null", bi);
		assertEquals("Aantal BronhouderInfo objecten komt niet overeen.", 1, bi
				.get(0).getBasisregistraties().size());
		assertEquals("BronhouderInfo object is niet correct.", "RDAM-01", bi
				.get(0).getBasisregistraties().get(0));

		bi = bc.getBronhouderInfoByBasisregistratie("TMF-PERSOON");
		assertNotNull("BronhouderInfo is null", bi);
		assertEquals("Aantal BronhouderInfo objecten komt niet overeen.", 0, bi
				.size());
	}

	public void testGetBronhoudersInfoByObject() {

		/*
		 * Test ophalen bronhouderinfo per object. Een object is NIET unique
		 * voor 1 bronhouder, dus het kan voorkomen dat wanneer je op een object
		 * zoekt, je meerdere bronhouderinfo objecten terug krijgt.
		 */

		BronhouderCatalogus bc = new BronhouderCatalogus();

		// Voeg een nieuwe bronhouder toe met een object wat al bestaat in de
		// propGoed properties: TMF-PERSOON.
		propGoed.put("bronhouder4.naam", "RDAM-XX");
		propGoed.put("bronhouder4.code", "RDAM-XX code");
		propGoed.put("bronhouder4.bereikenVia", "File");
		propGoed.put("bronhouder4.bereikenAdres", "file://tmp/GM2/");
		propGoed.put("bronhouder4.basisregistratie1", "TMF-PERSOON");

		bc.setProperties(propGoed);

		List<BronhouderInfo> bi = bc.getBronhouderInfoByBasisregistratie("TMF-PERSOON");
		assertNotNull("BronhouderInfo is null", bi);
		assertEquals("Aantal BronhouderInfo objecten komt niet overeen.", 1, bi
				.size());

	}

	public void testGetBronhouderInfoFoutBereikenVia() {
		propFout = new Properties();
		// Dit zou een exceptie moeten gooien (alleen File of Email toegestaan).
		propFout.put("bronhouder1.naam", "OnbekendeBronhouder");
		propFout.put("bronhouder1.code", "OnbekendeBronhouder code");
		propFout.put("bronhouder1.bereikenVia", "Email");
		propFout.put("bronhouder1.bereikenAdres",
				"applicatiebeheer@rtmf-guc.localhost");

		propFout.put("bronhouder2.naam", "RDAM-02");
		propFout.put("bronhouder2.code", "RDAM-02 code: xyz");
		propFout.put("bronhouder2.bereikenVia", "BrievenPost");
		propFout.put("bronhouder2.bereikenAdres", "/tmp/GM3");
		propFout.put("bronhouder2.object1", "KAAS-KOP");

		String error = "Er staat een foute property waarde in de rtmf-bronhouder.property file bij key: "
				+ "bronhouder2.bereikenVia"
				+ ". De waarde mag alleen Email of File zijn !";

		BronhouderCatalogus bc = new BronhouderCatalogus();
		try {
			bc.setProperties(propFout);
			fail("BronhouderCatalogus gooit geen exception bij foute porperties.");
		} catch (Exception e) {
			assertEquals("De verwachte exception message komt niet overeen.",
					error, e.getMessage());
		}
	}

	public void testGetBronhouderInfoFouteProperty() {
		propFout = new Properties();
		// Dit zou een exceptie moeten gooien (bereikenMiddels moet bereikenVia
		// zijn)).
		propFout.put("bronhouder1.naam", "OnbekendeBronhouder");
		propFout.put("bronhouder1.code", "Onbekende code");
		propFout.put("bronhouder1.bereikenVia", "Email");
		propFout.put("bronhouder1.bereikenAdres",
				"applicatiebeheer@rtmf-guc.localhost");

		propFout.put("bronhouder2.naam", "RDAM-02");
		propFout.put("bronhouder2.code", "Geen code aanwezig voor RDAM-02");
		propFout.put("bronhouder2.bereikenMiddels", "Email");
		propFout.put("bronhouder2.bereikenAdres", "/tmp/GM3");
		propFout.put("bronhouder2.object1", "KAAS-KOP");

		String error = "Er staat een foute property gedefinieerd in de rtmf-bronhouder.property file: "
				+ "bronhouder2.bereikenMiddels";

		BronhouderCatalogus bc = new BronhouderCatalogus();
		try {
			bc.setProperties(propFout);
			fail("BronhouderCatalogus gooit geen exception bij foute porperties.");
		} catch (Exception e) {
			assertEquals("De verwachte exception message komt niet overeen.",
					error, e.getMessage());
		}
	}

	public void testDetermineBereikenViaCallEmail() {
		BronhouderCatalogus bc = new BronhouderCatalogus();
		bc.setProperties(propGoed);
		String via = bc
				.determineBereikenViaForTerugmelding(zendTerugmeldingGMemail);
		assertNotNull("bereikenVia is null.", via);
		assertEquals(
				"determineBereikenViaForTerugmelding geeft de verkeerde waarde.",
				"Email", via);
	}

	public void testDetermineBereikenAdresCallEmail() {
		BronhouderCatalogus bc = new BronhouderCatalogus();
		bc.setProperties(propGoed);
		String adres = bc
				.determineBereikenAdresForTerugmelding(zendTerugmeldingGMemail);
		assertNotNull("bereikenAdres is null.", adres);
		assertEquals(
				"determineBereikenAdresForTerugmelding geeft de verkeerde waarde.",
				"muletest@localhost", adres);
	}

	public void testDetermineBereikenViaCallFile() {
		BronhouderCatalogus bc = new BronhouderCatalogus();
		bc.setProperties(propGoed);
		String via = bc
				.determineBereikenViaForTerugmelding(zendTerugmeldingGMfile);
		assertNotNull("bereikenVia is null.", via);
		assertEquals(
				"determineBereikenViaForTerugmelding geeft de verkeerde waarde.",
				"File", via);
	}

	public void testDetermineBereikenAdresCallFile() {
		BronhouderCatalogus bc = new BronhouderCatalogus();
		bc.setProperties(propGoed);
		String adres = bc
				.determineBereikenAdresForTerugmelding(zendTerugmeldingGMfile);
		assertNotNull("bereikenAdres is null.", adres);
		assertEquals(
				"determineBereikenAdresForTerugmelding geeft de verkeerde waarde.",
				"file://tmp/GM2/", adres);
	}
}
