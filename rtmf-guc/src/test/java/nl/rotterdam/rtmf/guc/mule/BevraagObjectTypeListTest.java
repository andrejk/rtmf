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

import nl.rotterdam.rtmf.guc.test.RtmfTestUtils;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

public class BevraagObjectTypeListTest extends FunctionalTestCase{
	String requestBasisregistratieList = 
		"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
		"   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:f4df1804-7656-45e3-a406-8dbdc99b5190</wsa:MessageID><wsa:To>http://localhost:63081/stelselBevragenService</wsa:To></soapenv:Header>\r\n" + 
		"   <soapenv:Body>\r\n" + 
		"      <stel:getBasisregistratieList xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\"/>\r\n" + 
		"   </soapenv:Body>\r\n" + 
		"</soapenv:Envelope>";

	String requestObjectTypeList = 
		"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeListRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:d1c2fdbf-c1c0-4eed-8a3a-d16cddfd1416</wsa:MessageID><wsa:To>http://localhost:63081/stelselBevragenService</wsa:To></soapenv:Header>\r\n" + 
		"   <soapenv:Body>\r\n" + 
		"      <stel:getObjectTypeList xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\">\r\n" + 
		"         <stel:BRTag>%s</stel:BRTag>\r\n" + 
		"      </stel:getObjectTypeList>\r\n" + 
		"   </soapenv:Body>\r\n" + 
		"</soapenv:Envelope>";

	@Override
	public void doSetUp() throws Exception {
		super.doSetUp();
		// zorg ervoor dat mule blijft draaien en niet bij elke methode opnieuw opstart
		setDisposeManagerPerSuite(true);
		MuleClient client = new MuleClient();
		client.send("vm://guc/rtmfguc/stelselBevragenService", requestBasisregistratieList, null);
	}
	
	public void testGetObjectTypeListBeideResources() throws Exception {
		MuleClient client;
			client = new MuleClient();
			MuleMessage response = client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(requestObjectTypeList, "TMF-REG1"), null);
			String responsePayload = response.getPayloadAsString();
			//test de gegevens uit TMF mock
			RtmfTestUtils.assertContainsText(responsePayload, "TMF-PERSOON");
			RtmfTestUtils.assertContainsText(responsePayload, "TMF-GEBOUW");
			RtmfTestUtils.assertContainsText(responsePayload, "TMF-VERGUNNING");
			RtmfTestUtils.assertContainsText(responsePayload, "GM-VERGUNNING");
	}
	
	public void testGetObjectTypeListAlleenTMF() throws Exception{
		MuleClient client;
			client = new MuleClient();
			MuleMessage response = client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(requestObjectTypeList, "TMF-REG2"), null);
			String responsePayload = response.getPayloadAsString();
			//test de gegevens uit TMF mock
			RtmfTestUtils.assertContainsText(responsePayload, "TMF-AUTO");
			RtmfTestUtils.assertContainsText(responsePayload, "TMF-VRACHTWAGEN");
			RtmfTestUtils.assertContainsText(responsePayload, "TMF-BUS");
			RtmfTestUtils.assertDoesNotContainText(responsePayload,"GM-AUTO");
			RtmfTestUtils.assertDoesNotContainText(responsePayload, "GM-VRACHTWAGEN");
	}
	
	public void testGetObjectTypeListAlleenGM() throws Exception {
		MuleClient client;
		
			client = new MuleClient();
			MuleMessage response = client.send("vm://guc/rtmfguc/stelselBevragenService", String.format(requestObjectTypeList, "GM-REG2"), null);
			String responsePayload = response.getPayloadAsString();
			//test de gegevens uit GM mock
			RtmfTestUtils.assertContainsText(responsePayload, "GM-BOOT");
			RtmfTestUtils.assertContainsText(responsePayload, "TMF-VRACHTWAGEN");
			RtmfTestUtils.assertContainsText(responsePayload, "TMF-BUS");
			RtmfTestUtils.assertDoesNotContainText(responsePayload,"TMF-AUTO");
			RtmfTestUtils.assertDoesNotContainText(responsePayload, "TMF-PERSOON");
			RtmfTestUtils.assertDoesNotContainText(responsePayload, "TMF-GEBOUW");
		
	}
	
	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml,rtmfguc-mocks-config.xml";
	}

}
