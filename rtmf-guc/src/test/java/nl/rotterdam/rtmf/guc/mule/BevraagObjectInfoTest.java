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

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

public class BevraagObjectInfoTest extends FunctionalTestCase {
	public final static String requestBasisregistratie = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
			+ "   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:f4df1804-7656-45e3-a406-8dbdc99b5190</wsa:MessageID><wsa:To>http://localhost:63081/stelselBevragenService</wsa:To></soapenv:Header>\r\n"
			+ "   <soapenv:Body>\r\n"
			+ "      <stel:getBasisregistratieList xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\"/>\r\n"
			+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";

	public final static String requestObjectTypeList = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeListRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:d1c2fdbf-c1c0-4eed-8a3a-d16cddfd1416</wsa:MessageID><wsa:To>http://localhost:63081/stelselBevragenService</wsa:To></soapenv:Header>\r\n"
			+ "   <soapenv:Body>\r\n"
			+ "      <stel:getObjectTypeList xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\">\r\n"
			+ "         <stel:BRTag>%s</stel:BRTag>\r\n"
			+ "      </stel:getObjectTypeList>\r\n"
			+ "   </soapenv:Body>\r\n"
			+ "</soapenv:Envelope>";

	public final static String requestObjectInfo = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
			+ "   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:e3dd7c6a-21e2-4cbd-8ba6-136892062b0f</wsa:MessageID><wsa:To>http://localhost:63081/stelselBevragenService</wsa:To></soapenv:Header>\r\n"
			+ "   <soapenv:Body>\r\n"
			+ "      <stel:getObjectInfo xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\">\r\n"
			+ "         <stel:BRTag>%s</stel:BRTag>\r\n"
			+ "         <stel:ObjectTag>%s</stel:ObjectTag>\r\n"
			+ "      </stel:getObjectInfo>\r\n"
			+ "   </soapenv:Body>\r\n"
			+ "</soapenv:Envelope>";

	@Override
	public void doSetUp() throws Exception {
		super.doSetUp();
		// zorg ervoor dat mule blijft draaien en niet bij elke methode opnieuw
		// opstart
		setDisposeManagerPerSuite(true);
		MuleClient client;

		// zorg ervoor dat de cache gevuld is.
		client = new MuleClient();
		client.send("vm://guc/rtmfguc/stelselBevragenService", requestBasisregistratie,
				null);
		client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(
				requestObjectTypeList, "TMF-REG1"), null);
		client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(
				requestObjectTypeList, "TMF-REG2"), null);
		client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(
				requestObjectTypeList, "GM-REG2"), null);
	}

	/**
	 * ConcurrencyTest welke 40 requests afvuurt op mule.
	 * @throws Exception
	 */
	public void testGetObjectInfoConcurrencyTest() throws Exception {
		int aantalFout = 0;
		for (int i = 0; i < 40; i++) {
			try {
				MuleClient client = new MuleClient();
				client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(
						requestObjectTypeList, "TMF-REG1"), null);
			} catch (AssertionError e) {
				aantalFout++;
			}
		}
		assertEquals("Er zijn fouten opgetreden", 0, aantalFout);
	}

	public void testGetObjectInfoBeide() throws Exception {
		MuleClient client = new MuleClient();

		MuleMessage response = client.send("vm://guc/rtmfguc/stelselBevragenService",
				String.format(requestObjectInfo, "TMF-REG1", "TMF-PERSOON"),
				null);

		String responsePayload = response.getPayloadAsString();
		// test de gegevens uit TMF mock
		assertTrue(responsePayload.contains("TMF-PERSOON-VOORNAAM"));
		assertTrue(responsePayload.contains("TMF-PERSOON-TUSSENVOEGSEL"));
		assertTrue(responsePayload.contains("TMF-PERSOON-ACHTERNAAM"));
		assertTrue(responsePayload.contains("GM-PERSOON-BIJNAAM"));
	}

	public void testGetObjectInfoTMF() throws Exception {
		MuleClient client = new MuleClient();

		MuleMessage response = client.send("vm://guc/rtmfguc/stelselBevragenService",
				String.format(requestObjectInfo, "TMF-REG2", "TMF-AUTO"), null);

		String responsePayload = response.getPayloadAsString();
		// test de gegevens uit TMF mock
		assertTrue(responsePayload.contains("TMF-PERSOON-VOORNAAM"));
		assertTrue(responsePayload.contains("TMF-PERSOON-TUSSENVOEGSEL"));
		assertTrue(responsePayload.contains("TMF-PERSOON-ACHTERNAAM"));
		assertFalse(responsePayload.contains("GM-PERSOON-BIJNAAM"));
	}

	public void testGetObjectInfoGM() throws Exception {
		MuleClient client = new MuleClient();

		MuleMessage response = client.send("vm://guc/rtmfguc/stelselBevragenService",
				String.format(requestObjectInfo, "GM-REG2", "GM-BOOT"), null);

		String responsePayload = response.getPayloadAsString();
		// test de gegevens uit TMF mock
		assertTrue(responsePayload.contains("GM-PERSOON-VOORNAAM"));
		assertTrue(responsePayload.contains("GM-PERSOON-TUSSENVOEGSEL"));
		assertTrue(responsePayload.contains("GM-PERSOON-ACHTERNAAM"));
		assertTrue(responsePayload.contains("GM-PERSOON-BIJNAAM"));
		assertFalse(responsePayload.contains("TMF-PERSOON-VOORNAAM"));
		assertFalse(responsePayload.contains("TMF-PERSOON-TUSSENVOEGSEL"));
		assertFalse(responsePayload.contains("TMF-PERSOON-ACHTERNAAM"));
	}

	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml,rtmfguc-mocks-config.xml";
	}

}
