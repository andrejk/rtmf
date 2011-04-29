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

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

public class IntrekkenTest extends FunctionalTestCase {
	private Wiser smtpServer;

	String zendIntrekking = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"   <S:Header>" + 
			"      <To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/abkr/rtmfguc/terugmeldService</To>" + 
			"      <Action xmlns=\"http://www.w3.org/2005/08/addressing\">intrekkingtmf-aanmelden-00000003271987420000</Action>" + 
			"      <ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">" + 
			"         <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>" + 
			"      </ReplyTo>" + 
			"      <MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:24cf9fd5-7b2a-4437-8f85-d7b02ef52776</MessageID>" + 
			"   </S:Header>" + 
			"   <S:Body>" + 
			"      <ns2:intrekking xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\">" + 
			"         <ns2:meldingKenmerk>Intrekking-%s.09.10.10101-1256225482778</ns2:meldingKenmerk>" + 
			"         <ns2:tijdstempelAanlevering>2009-10-22T17:31:22.778+02:00</ns2:tijdstempelAanlevering>" + 
			"         <ns2:betreftTmfKenmerk>%s.09.10.10101</ns2:betreftTmfKenmerk>" + 
			"         <ns2:toelichting>Intrekken tessie</ns2:toelichting>" + 
			"      </ns2:intrekking>" + 
			"   </S:Body>" + 
			"</S:Envelope>";

	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml,rtmfguc-zm-config.xml,rtmfguc-mocks-config.xml,rtmfguc-mocks-zm-config.xml";
	}

	@Override
	public void doSetUp() throws Exception {
		super.doSetUp();
		// start smtp server mock
		smtpServer = new Wiser();
		smtpServer.setPort(18089);
		smtpServer.start();
		setDisposeManagerPerSuite(true);
		setFailOnTimeout(false);
	}

	@Override
	public void doTearDown() throws Exception {
		if (null != smtpServer) {
			smtpServer.stop();
		}
		super.doTearDown();
	}

	public void testKern() throws Exception {

		MuleClient client = new MuleClient();
		MuleMessage response = client.send("vm://guc/rtmfguc/intrekService",
				new DefaultMuleMessage(String.format(zendIntrekking, "TMD", "TMD")), null);
		assertNotNull("De response is null.", response);
		assertNotNull("De payload is null.", response.getPayload());
		String responsePayload = response.getPayloadAsString();
		assertTrue("De service heeft geen ok terug gegeven", responsePayload.contains("text>OK<"));
		assertTrue("Er zijn geen emails gevonden op de SMTP server.",
				smtpServer.getMessages().size() > 0);

		List<WiserMessage> emails = smtpServer.getMessages();
		for (WiserMessage message : emails) {
			message.dumpMessage(System.out);
			MimeMessage mm = message.getMimeMessage();
			String content = (String) mm.getContent();

			assertTrue("De content komt niet overeen met: " + content, content.contains("Onderstaand een bericht van een intrekking van een Terugmelding vanuit"));
		}
	}
	
	public void testBron() throws Exception {
		assertEquals("Er zijn bij het starten emails gevonden op de SMTP server.",0,
				smtpServer.getMessages().size());
		MuleClient client = new MuleClient();
		MuleMessage response = client.send("vm://guc/rtmfguc/intrekService",
				new DefaultMuleMessage(String.format(zendIntrekking, "PREFIX", "PREFIX")), null);
		assertNotNull("De response is null.", response);
		assertNotNull("De payload is null.", response.getPayload());
		String responsePayload = response.getPayloadAsString();
		assertTrue("De service heeft geen ok terug gegeven", responsePayload.contains("text>OK<"));
		assertEquals("Er zijn emails gevonden op de SMTP server.",0,
				smtpServer.getMessages().size());
	}
}
