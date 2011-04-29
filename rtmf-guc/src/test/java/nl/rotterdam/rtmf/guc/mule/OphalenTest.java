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

/**
 * Deze test test het ophalen van de status van een terugmelding.
 * @author rweverwijk
 *
 */
public class OphalenTest  extends FunctionalTestCase {
	//public static final int TIMEOUT = 1200000;
	public static final int DEFAULT_MULE_TEST_TIMEOUT_SECS = 600;
	
	private static final String requestOphalenMeldingStatus= 
		"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
		"   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/ophalenService/ophalenMeldingStatusRequest/ophaalService</wsa:Action><wsa:MessageID>uuid:24fbc2fe-4053-4299-b73f-b61bfb9aa78f</wsa:MessageID><wsa:To>http://twd720.resource.ta-twd.rotterdam.nl:10080/services</wsa:To></soapenv:Header>" + 
		"   <soapenv:Body>" + 
		"      <oph:ophalenMeldingStatus xmlns:oph=\"http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd\">" + 
		"         <oph:meldingKenmerk>123</oph:meldingKenmerk>" + 
		"      </oph:ophalenMeldingStatus>" + 
		"   </soapenv:Body>" + 
		"</soapenv:Envelope>";
	
	private static final String requestOphalenMeldingKenmerk= 
		"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
		"   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/ophalenService/ophalenMeldingKenmerkRequest/ophaalService</wsa:Action><wsa:MessageID>uuid:86005f5f-bda2-455e-965c-abf79adc998e</wsa:MessageID><wsa:To>http://twd720.resource.ta-twd.rotterdam.nl:10080/services</wsa:To></soapenv:Header>" + 
		"   <soapenv:Body>" + 
		"      <oph:ophalenMeldingKenmerk xmlns:oph=\"http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd\">" + 
		"         <oph:meldingKenmerk>123(m) - (%s:TMD.09.10.00001)</oph:meldingKenmerk>" + 
		"      </oph:ophalenMeldingKenmerk>" + 
		"   </soapenv:Body>" + 
		"</soapenv:Envelope>";
				
	
	public void testGetOphalenMeldingStatus() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage send = client.send("vm://guc/rtmfguc/ophaalServiceIn", requestOphalenMeldingStatus, null);
		String payloadAsString = send.getPayloadAsString();
		assertNotNull(payloadAsString);
		logger.debug(payloadAsString);
		assertTrue(payloadAsString.contains("<oph:idAttribuut>TMF-PERSOON-VOORNAAM</oph:idAttribuut>"));
		assertTrue(payloadAsString.contains("<oph:idAttribuut>01.86.11</oph:idAttribuut>"));
	}
	
	public void testRequestOphalenMeldingKenmerkBron() throws Exception {
		System.out.println("start requestOphalenMeldingKenmerk");
		MuleClient client = new MuleClient();
		logger.debug("na client");
		MuleMessage send = client.send("vm://guc/rtmfguc/ophaalServiceIn", String.format(requestOphalenMeldingKenmerk, "B"), null);
		String payloadAsString = send.getPayloadAsString();
		assertNotNull(payloadAsString);
		assertTrue(payloadAsString.contains("<oph:statusMelding>ontvangen</oph:statusMelding>"));
		assertTrue(payloadAsString.contains("<oph:tijdstempelGemeld>2009-08-14T11:29:09.743+02:00</oph:tijdstempelGemeld>"));
	}
	
	public void testRequestOphalenMeldingKenmerkKern() throws Exception {
		System.out.println("start requestOphalenMeldingKenmerk");
		MuleClient client = new MuleClient();
		System.out.println("na client");
		MuleMessage send = client.send("vm://guc/rtmfguc/ophaalServiceIn", String.format(requestOphalenMeldingKenmerk, "K"), null);
		String payloadAsString = send.getPayloadAsString();
		assertNotNull(payloadAsString);
		logger.debug(payloadAsString);
		assertTrue("payload doesn't contain <oph:statusMelding>gemeld</oph:statusMelding>, message was: " + payloadAsString , payloadAsString.contains("<oph:statusMelding>gemeld</oph:statusMelding>"));
		assertTrue("payload doesn't contain <oph:statusMelding>gemeld</oph:statusMelding>, message was: " + payloadAsString , payloadAsString.contains("<oph:tijdstempelGemeld>2009-10-27T16:00:11.000+01:00</oph:tijdstempelGemeld>"));
	}
	
	@Override
	public void doSetUp() throws Exception {
		logger.debug("no fail on timeout");
		this.setFailOnTimeout(false);
		logger.debug("start doSetup");
		super.doSetUp();
		this.setFailOnTimeout(false);
		logger.debug("Na setup");
		logger.debug("nog een keer, no fail on timeout");
		
		// zorg ervoor dat mule blijft draaien en niet bij elke methode opnieuw
		// opstart
		setDisposeManagerPerSuite(true);
	}				

	
	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml,rtmfguc-mocks-config.xml,rtmfguc-zm-config.xml,rtmfguc-mocks-zm-config.xml";
	}
}
