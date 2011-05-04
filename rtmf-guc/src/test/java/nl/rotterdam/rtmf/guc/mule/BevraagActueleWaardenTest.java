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

import java.util.Properties;

import nl.rotterdam.rtmf.guc.test.RtmfTestUtils;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

public class BevraagActueleWaardenTest extends FunctionalTestCase {

	private static final String requestActueleWaarden = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "<S:Header>"
			+ "<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/tmfStelselBevragenService</To>"
			+ "<Action xmlns=\"http://www.w3.org/2005/08/addressing\">http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/bevragenRequest/stelselBevragenService</Action>"
			+ "<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo>"
			+ "<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:475fd6ad-b53b-430d-95b7-e12d862bffbc</MessageID>"
			+ "</S:Header><S:Body>"
			+ "<bevragen xmlns=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\"><brTag>%s</brTag><objectTag>%s</objectTag><objectId>%s</objectId></bevragen>"
			+ "</S:Body></S:Envelope>";

	private static final String BR_TAG_GBA = "GBA";
	private static final String GBA_PERSOON_OBJECT_ID = "01";
	private static final String	GBA_VERBLIJFPLAATS_ID = "08";

	@Override
	public void doSetUp() throws Exception {
		super.doSetUp();

		// zorg ervoor dat mule blijft draaien en niet bij elke methode opnieuw
		// opstart
		setDisposeManagerPerSuite(true);

		// zorg ervoor dat de cache gevuld is.
		MuleClient client = new MuleClient();
		// eerst een getObjectInfo call om de cache te vullen met stufpath
		// waarden!
		// (die zijn nodig om de actuele waarden op te halen), ignore response
		client.send("vm://guc/rtmfguc/stelselBevragenService",
				BevraagObjectInfoTest.requestBasisregistratie, null);
		client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(
				BevraagObjectInfoTest.requestObjectTypeList, "GBA"), null);
		client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(
				BevraagObjectInfoTest.requestObjectInfo, "GBA", "01"), null);
		client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(
				BevraagObjectInfoTest.requestObjectInfo, "GBA", "08"), null);
	}

	public void testGetActueleWaardenSoZaWeBsnNummer126946036()
			throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://guc/rtmfguc/stelselBevragenService", String.format(
						requestActueleWaarden,BR_TAG_GBA, GBA_PERSOON_OBJECT_ID, "126946036"), null);
		String responsePayload = response.getPayloadAsString();
		assertNotNull("De payload van de response is null: " + response,
				responsePayload);
		RtmfTestUtils.assertContainsText(responsePayload, "Everard");
		RtmfTestUtils.assertContainsText(responsePayload, "Daniëls");
	}

	public void testGetActueleWaardenSoZaWeBsnNummer199040382()
			throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://guc/rtmfguc/stelselBevragenService", String.format(
						requestActueleWaarden, BR_TAG_GBA, GBA_PERSOON_OBJECT_ID, "199040382"), null);
		String responsePayload = response.getPayloadAsString();
		assertNotNull("De payload van de response is null: " + response,
				responsePayload);
		RtmfTestUtils.assertContainsText(responsePayload, "Mohamed");
		RtmfTestUtils.assertContainsText(responsePayload, "Boufarra");
	}

	public void testGetActueleWaardenSoZaWeBsnNummer78548718() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://guc/rtmfguc/stelselBevragenService", String.format(
						requestActueleWaarden,BR_TAG_GBA, GBA_PERSOON_OBJECT_ID, "78548718"), null);
		String responsePayload = response.getPayloadAsString();
		assertNotNull("De payload van de response is null: " + response,
				responsePayload);
		RtmfTestUtils.assertContainsText(responsePayload, "Antonia Wilhelmina");
		RtmfTestUtils.assertContainsText(responsePayload, "van den");
		RtmfTestUtils.assertContainsText(responsePayload, "Heuvel");
	}

	public void testGetActuleWaardeSoZaWeBsnNummer666() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://guc/rtmfguc/stelselBevragenService", String.format(
						requestActueleWaarden,BR_TAG_GBA, GBA_PERSOON_OBJECT_ID, "666"), null);
		String responsePayload = response.getPayloadAsString();
		assertNotNull("De payload van de response is null: " + response,
				responsePayload);
		RtmfTestUtils.assertContainsText(responsePayload, "<error>");
		RtmfTestUtils.assertContainsText(responsePayload, "<code>911</code>");
		RtmfTestUtils.assertContainsText(responsePayload, "<message>StUF fout versie 300</message>");
		RtmfTestUtils.assertContainsText(responsePayload,
				"<exceptionPayload>null;StUF058;server;Failed to search PRS records from statement: null</exceptionPayload>");
	}

	public void testGetActuleWaardeSoZaWeBsnNummer600() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://guc/rtmfguc/stelselBevragenService", String.format(
						requestActueleWaarden,BR_TAG_GBA, GBA_PERSOON_OBJECT_ID, "600"), null);
		String responsePayload = response.getPayloadAsString();
		assertNotNull("De payload van de response is null: " + response,
				responsePayload);
		RtmfTestUtils.assertContainsText(responsePayload, "<error>");
		RtmfTestUtils.assertContainsText(responsePayload, "<code>901</code>");
		RtmfTestUtils.assertContainsText(responsePayload, "<message>Binding fout op server</message>");
		RtmfTestUtils.assertContainsText(responsePayload,
				"<exceptionPayload>Cannot bind to address \"http://localhost:9090/gm/sozawa\" No component registered on that endpoint</exceptionPayload>");
	}
	
	public void testGetActueleWaardenSoZaWeBsnNummer78548718Verblijfplaats() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://guc/rtmfguc/stelselBevragenService", String.format(
						requestActueleWaarden,BR_TAG_GBA, GBA_VERBLIJFPLAATS_ID, "78548718"), null);
		String responsePayload = response.getPayloadAsString();
		assertNotNull("De payload van de response is null: " + response,
				responsePayload);
		System.out.println("TEST PAYLOAD : " + responsePayload);
		RtmfTestUtils.assertContainsText(responsePayload, "value>61");
		RtmfTestUtils.assertContainsText(responsePayload, "value>Rotterdam");
	}

	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml,rtmfguc-mocks-config.xml";
	}
}
