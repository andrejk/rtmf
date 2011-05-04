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
package nl.rotterdam.rtmf.guc.mule;

import java.io.ByteArrayInputStream;

import nl.rotterdam.rtmf.guc.StelselCatalogusCache;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.subethamail.wiser.Wiser;

public class StuurTerugmeldingTest extends FunctionalTestCase {
	private Wiser smtpServer;

	String UUID = "uuid:2ca2737d-78ea-4289-8d2c-3f7d3464b913";

	String RESPONSE_OK = "<text>OK</text>";
	String RESPONSE_2XOK = "<text>OK</text></defrep:response><defrep:response><text>OK</text>";

	String ERROR_MSG_UUID = "De response bevat niet de juiste UUID. De response is: ";
	String ERROR_MSG_OK = "Verwacht '" + RESPONSE_OK
			+ "' in de response. De response is: ";
	String ERROR_MSG_2XOK = "Verwacht '" + RESPONSE_2XOK
			+ "'. De response is: ";

	String zendTerugmeldingTMF = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "	<S:Header>"
			+ "		<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/tmfTerugmeldService</To>"
			+ "		<Action xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action>"
			+ "		<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ "			<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>"
			+ "		</ReplyTo>"
			+ "		<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ UUID
			+ "</MessageID>"
			+ "	</S:Header>"
			+ "	<S:Body>"
			+ "		<ns2:terugmelding"
			+ "		xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\""
			+ "		xmlns:ns3=\"http://tmfportal.ovsoftware.com/services\" "
			+ "       xmlns:ns4=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\" >"
			+ "			<ns2:meldingKenmerk>UnitTester-001</ns2:meldingKenmerk>"
			+ "			<ns2:tijdstempelAanlevering>2009-08-27T15:51:58.635+02:00</ns2:tijdstempelAanlevering>"
			+ "			<ns2:basisRegistratie>TMF-REG2</ns2:basisRegistratie>"
			+ "			<ns2:objectTag>TMF-PERSOON</ns2:objectTag>"
			+ "			<ns2:objectNaam>Persoon</ns2:objectNaam>"
			+ "			<ns2:objectIdentificatie>adres</ns2:objectIdentificatie>"
			+ "			<ns2:toelichting>Unit tester</ns2:toelichting>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>TMF-PERSOON-VOORNAAM</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>Petet</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>Peter</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>TMF-PERSOON-TUSSENVOEGSEL"
			+ "				</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>van der</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>van de</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>TMF-PERSOON-ACHTERNAAM</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>Baker</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>Bakker</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:contactInfo>"
			+ "				<ns2:naam></ns2:naam>"
			+ "				<ns2:telefoon></ns2:telefoon>"
			+ "				<ns2:email></ns2:email>"
			+ "			</ns2:contactInfo>"
			+ "		</ns2:terugmelding>" + "	</S:Body>" + "</S:Envelope>";

	String zendTerugmeldingGM = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "	<S:Header>"
			+ "		<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/tmfTerugmeldService</To>"
			+ "		<Action xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action>"
			+ "		<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ "			<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>"
			+ "		</ReplyTo>"
			+ "		<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ UUID
			+ "</MessageID>"
			+ "	</S:Header>"
			+ "	<S:Body>"
			+ "		<ns2:terugmelding"
			+ "		xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\""
			+ "		xmlns:ns3=\"http://tmfportal.ovsoftware.com/services\" xmlns:ns4=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\">"
			+ "			<ns2:meldingKenmerk>UnitTester-001</ns2:meldingKenmerk>"
			+ "			<ns2:tijdstempelAanlevering>2009-08-27T15:51:58.635+02:00</ns2:tijdstempelAanlevering>"
			+ "			<ns2:basisRegistratie>GM-REG2</ns2:basisRegistratie>"
			+ "			<ns2:objectTag>GM-PERSOON</ns2:objectTag>"
			+ "			<ns2:objectNaam>Persoon</ns2:objectNaam>"
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

	String zendTerugmeldingBoth = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "	<S:Header>"
			+ "		<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/tmfTerugmeldService</To>"
			+ "		<Action xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action>"
			+ "		<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ "			<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>"
			+ "		</ReplyTo>"
			+ "		<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ UUID
			+ "</MessageID>"
			+ "	</S:Header>"
			+ "	<S:Body>"
			+ "		<ns2:terugmelding"
			+ "		xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\""
			+ "		xmlns:ns3=\"http://tmfportal.ovsoftware.com/services\" "
			+ "       xmlns:ns4=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\" >"
			+ "			<ns2:meldingKenmerk>UnitTester-001</ns2:meldingKenmerk>"
			+ "			<ns2:tijdstempelAanlevering>2009-08-27T15:51:58.635+02:00</ns2:tijdstempelAanlevering>"
			+ "			<ns2:basisRegistratie>TMF-REG1</ns2:basisRegistratie>"
			+ "			<ns2:objectTag>TMF-PERSOON</ns2:objectTag>"
			+ "			<ns2:objectNaam>Persoon</ns2:objectNaam>"
			+ "			<ns2:objectIdentificatie>adres</ns2:objectIdentificatie>"
			+ "			<ns2:toelichting>Unit tester</ns2:toelichting>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>TMF-PERSOON-VOORNAAM</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>Petet</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>Peter</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>TMF-PERSOON-TUSSENVOEGSEL"
			+ "				</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>van der</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>van de</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>TMF-PERSOON-ACHTERNAAM</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>Baker</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>Bakker</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>GM-PERSOON-BIJNAAM</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>Rotterdamse Bijnaam</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>?</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:contactInfo>"
			+ "				<ns2:naam></ns2:naam>"
			+ "				<ns2:telefoon></ns2:telefoon>"
			+ "				<ns2:email></ns2:email>"
			+ "			</ns2:contactInfo>"
			+ "		</ns2:terugmelding>" + "	</S:Body>" + "</S:Envelope>";

	String requestBasisregistratie = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
			+ "   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:f4df1804-7656-45e3-a406-8dbdc99b5190</wsa:MessageID><wsa:To>http://localhost:63081/stelselBevragenService</wsa:To></soapenv:Header>\r\n"
			+ "   <soapenv:Body>\r\n"
			+ "      <stel:getBasisregistratieList xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\"/>\r\n"
			+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";

	String requestObjectTypeList = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeListRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:d1c2fdbf-c1c0-4eed-8a3a-d16cddfd1416</wsa:MessageID><wsa:To>http://localhost:63081/stelselBevragenService</wsa:To></soapenv:Header>\r\n"
			+ "   <soapenv:Body>\r\n"
			+ "      <stel:getObjectTypeList xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\">\r\n"
			+ "         <stel:BRTag>%s</stel:BRTag>\r\n"
			+ "      </stel:getObjectTypeList>\r\n"
			+ "   </soapenv:Body>\r\n"
			+ "</soapenv:Envelope>";

	String requestObjectInfo = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
			+ "   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:e3dd7c6a-21e2-4cbd-8ba6-136892062b0f</wsa:MessageID><wsa:To>http://localhost:63081/stelselBevragenService</wsa:To></soapenv:Header>\r\n"
			+ "   <soapenv:Body>\r\n"
			+ "      <stel:getObjectInfo xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\">\r\n"
			+ "         <stel:BRTag>%s</stel:BRTag>\r\n"
			+ "         <stel:ObjectTag>%s</stel:ObjectTag>\r\n"
			+ "      </stel:getObjectInfo>\r\n"
			+ "   </soapenv:Body>\r\n"
			+ "</soapenv:Envelope>";

	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml,rtmfguc-mocks-config.xml,rtmfguc-zm-config.xml,rtmfguc-mocks-zm-config.xml";
	}

	@Override
	public void doSetUp() throws Exception {
		// start smtp server mock
		super.doSetUp();
		
		if (smtpServer == null){
			smtpServer = new Wiser(); 
			smtpServer.setPort(18089);
			smtpServer.start();
			
		}
		// zorg ervoor dat mule blijft draaien en niet bij elke methode opnieuw
		// opstart
		setDisposeManagerPerSuite(true);
		setFailOnTimeout(false);
		
		MuleClient client;
		try {
			// Zorg dat alle TAGs voor de terugmelding test beschikbaar zijn
			client = new MuleClient();
			client.send("vm://guc/rtmfguc/stelselBevragenService",
					requestBasisregistratie, null);
			client.send("vm://guc/rtmfguc/stelselBevragenService", String
					.format(requestObjectTypeList, "TMF-REG1"), null);
			client.send("vm://guc/rtmfguc/stelselBevragenService", String
					.format(requestObjectTypeList, "TMF-REG2"), null);
			client
					.send("vm://guc/rtmfguc/stelselBevragenService", String
							.format(requestObjectInfo, "TMF-REG1",
									"TMF-PERSOON"), null);
			client.send("vm://guc/rtmfguc/stelselBevragenService", String
					.format(requestObjectTypeList, "GM-REG2"), null);
		} catch (MuleException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Override
	public void doTearDown() throws Exception {
		System.out.println("Stop email server");

		if (null != smtpServer) {
			smtpServer.stop();
		}
		super.doTearDown();
	}

	public void testZendTerugmeldingNaarTMF() throws Exception {
		MuleClient client = new MuleClient();
		@SuppressWarnings("unused")
		StelselCatalogusCache cache = (StelselCatalogusCache) client
				.getProperty("stelselCatalogusCacheBean");

		MuleMessage response = client.send(
				"vm://guc/rtmfguc/tmfTerugmeldServiceIn", zendTerugmeldingTMF,
				null);
		String responsePayload = response.getPayloadAsString();

		// Match the response to the send message
		assertTrue(ERROR_MSG_UUID + responsePayload, responsePayload
				.contains(UUID));
		// Check something went well
		assertTrue(ERROR_MSG_OK + responsePayload, responsePayload
				.contains(RESPONSE_OK));
	}


	// Response example
	//
	// <soapenv:Envelope
	// xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
	// xmlns:def="http://tmfportal.ovsoftware.com/services/defaultreply.xsd">
	// <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
	// <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding</wsa:Action>
	// <wsa:RelatesTo
	// RelationshipType="http://www.w3.org/2005/08/addressing/reply">uuid:2ca2737d-78ea-4289-8d2c-3f7d3464b913</wsa:RelatesTo>
	// <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
	// </soapenv:Header>
	// <soapenv:Body>
	// <def:response>
	// <text>OK</text>
	// </def:response>
	// </soapenv:Body>
	// </soapenv:Envelope>

	public void testZendTerugmeldingNaarGM() throws Exception {
		MuleClient client = new MuleClient();
		StelselCatalogusCache cache = (StelselCatalogusCache) client
		.getProperty("stelselCatalogusCacheBean");

		/*
		 * Voeg de keys toe waarnaar we gaan kijken in het groovy script
		 */
		if (cache != null) {
			cache.addKey("GM-PERSOON", "GM", false, "Persoon");
		}
		
		MuleMessage response = client.send(
				"vm://guc/rtmfguc/zmTerugmeldServiceIn", zendTerugmeldingGM,
				null);
		String responsePayload = response.getPayloadAsString();

		// Match the response to the send message
		assertTrue(ERROR_MSG_UUID + responsePayload, responsePayload
				.contains(UUID));
		// Check something went well
		assertTrue(ERROR_MSG_OK + responsePayload, responsePayload
				.contains(RESPONSE_OK));
	}

	public void testZendTerugmeldingNaarBoth() throws Exception {

		MuleClient client = new MuleClient();
		StelselCatalogusCache cache = (StelselCatalogusCache) client
				.getProperty("stelselCatalogusCacheBean");

		/*
		 * Voeg de keys toe waarnaar we gaan kijken in het groovy script
		 */
	
		if (cache != null) {
			cache.addKey("GM-PERSOON-BIJNAAM", "GM", false, null);
		}

		MuleMessage response = client.send(
				"vm://guc/rtmfguc/terugmeldServiceIn", new ByteArrayInputStream(zendTerugmeldingBoth.getBytes()),
				null);
		String responsePayload = response.getPayloadAsString();
		assertNotNull(responsePayload);

		// Match the response to the send message
		assertTrue(ERROR_MSG_UUID + responsePayload, responsePayload
				.contains(UUID));
		// Check something went well
		assertTrue(ERROR_MSG_OK + responsePayload, responsePayload
				.contains(RESPONSE_OK));
		// Check two things went well
		assertTrue(ERROR_MSG_2XOK + responsePayload, responsePayload
				.contains(RESPONSE_2XOK));

	}

}
