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

public class BevraagBasisregistratieTest extends FunctionalTestCase{

	String requestBasisregistratieList = 
		"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
		"   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:f4df1804-7656-45e3-a406-8dbdc99b5190</wsa:MessageID><wsa:To>http://localhost:63081/stelselBevragenService</wsa:To></soapenv:Header>\r\n" + 
		"   <soapenv:Body>\r\n" + 
		"      <stel:getBasisregistratieList xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\"/>\r\n" + 
		"   </soapenv:Body>\r\n" + 
		"</soapenv:Envelope>";
	
	public void testGetBasisregistraties() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send("vm://guc/rtmfguc/stelselBevragenService", requestBasisregistratieList, null);
		String responsePayload = response.getPayloadAsString();
		//test de gegevens uit TMF mock
		RtmfTestUtils.assertContainsText(responsePayload, "TMF-REG1");
		RtmfTestUtils.assertContainsText(responsePayload, "TMF-REG2");
		//test de gegevens uit de GM mock
		RtmfTestUtils.assertContainsText(responsePayload, "GM-REG2");
		// controleer of de TMF-REG1 maar 1 keer voorkomt
		assertTrue("TMF-REG1 mag maar 1 keer voorkomen: " + responsePayload, responsePayload.indexOf("TMF-REG1") == responsePayload.lastIndexOf("TMF-REG1"));
	}

	@Override
	public void doSetUp() throws Exception {
		this.setFailOnTimeout(false);
		super.doSetUp();
		setDisposeManagerPerSuite(true);
	}		
	
	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml,rtmfguc-mocks-config.xml";
	}

}
