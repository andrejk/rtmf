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
package nl.rotterdam.rtmf.guc.transformer;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.tck.FunctionalTestCase;

/**
 * @author tarem
 *
 */
public class ValidateSoapMessageTest extends FunctionalTestCase {
	private static String validSOAPMessage = "<?xml version='1.0' encoding='UTF-8'?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header /><soapenv:Body /></soapenv:Envelope>";
	private static String faultSOAPMessage = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header/><env:Body><env:Fault xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><faultcode xmlns=\"\">soap:Client</faultcode><faultstring xmlns=\"\">Exception [TOPLINK-4002] (Oracle TopLink Essentials - 2.1 (Build b52-fcs (09/24/2008))): oracle.toplink.essentials.exceptions.DatabaseException&#xD;&#xA;Internal Exception: java.sql.SQLException: Listener refused the connection with the following error:&#xA;ORA-12505, TNS:listener does not currently know of SID given in connect descriptor&#xA;The Connection descriptor used by the client was:&#xA;localhost:1521:xe&#xA;&#xD;&#xA;Error Code: 0; nested exception is: &#xA;&#x9;Exception [TOPLINK-4002] (Oracle TopLink Essentials - 2.1 (Build b52-fcs (09/2 4/2008))): oracle.toplink.essentials.exceptions.DatabaseException&#xD;&#xA;Internal Exception: java.sql.SQLException: Listener refused the connection with the following error:&#xA;ORA-12505, TNS:listener does not currently know of SID given in connect descriptor&#xA;The Connection descriptor used by the client was:&#xA;localhost:1521:xe&#xA;&#xD;&#xA;Error Code: 0</faultstring><detail xmlns=\"\"><ns3:zakenmagazijnFault xmlns:ns3=\"http://www.interaccess.nl/webplus/statuswfm_v2\"><ns3:zakenmagazijnMessage>Exception [TOPLINK-4002] (Oracle TopLink Essentials - 2.1 (Build b52-fcs (09/24/2008))): oracle.toplink.essentials.exceptions.DatabaseException&#xD;&#xA;Internal Exception: java.sql.SQLException: Listener refused the connection with the following error:&#xA;ORA-12505, TNS:listener does not currently know of SID given in connect descriptor&#xA;The Connection descriptor used by the client was:&#xA;localhost:1521:xe&#xA;&#xD;&#xA;Error Code: 0; nested exception is: &#xA;&#x9;Exception [TOPLIN K-4002] (Oracle TopLink Essentials - 2.1 (Build b52-fcs (09/24/2008))): oracle.toplink.essentials.exceptions.DatabaseException&#xD;&#xA;Internal Exception: java.sql.SQLException: Listener refused the connection with the following error:&#xA;ORA-12505, TNS:listener does not currently know of SID given in connect descriptor&#xA;The Connection descriptor used by the client was:&#xA;localhost:1521:xe&#xA;&#xD;&#xA;Error Code: 0</ns3:zakenmagazijnMessage></ns3:zakenmagazijnFault></detail></env:Fault></env:Body></env:Envelope>";
	
	@Test 
	public void testTransformMissingSchema(){
		ValidateSoapMessage validateSoapMessage = new ValidateSoapMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(validSOAPMessage.replace("http://schemas.xmlsoap.org/soap/envelope/", ""));
		
		try {
			validateSoapMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			e.printStackTrace();
			assertTrue(e.getMessage().contains("Geen valide soap envelope schema aanwezig"));
			assertTrue(e.getMessage().contains("<code>921</code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}

	@Test 
	public void testTransformMissingBodyElement(){
		ValidateSoapMessage validateSoapMessage = new ValidateSoapMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(validSOAPMessage.replace("Body", ""));
		
		try {
			validateSoapMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			e.printStackTrace();
			assertTrue(e.getMessage().contains("Element body ontbreekt"));
			assertTrue(e.getMessage().contains("<code>924</code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}

	@Test 
	public void testTransformMissingEnvelopElement(){
		ValidateSoapMessage validateSoapMessage = new ValidateSoapMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(validSOAPMessage.replace("Envelope", ""));
		
		try {
			validateSoapMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			e.printStackTrace();
			assertTrue(e.getMessage().contains("Element envelop ontbreekt"));
			assertTrue(e.getMessage().contains("<code>922</code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}
	@Test 
	public void testTransformMissingHeaderElement(){
		ValidateSoapMessage validateSoapMessage = new ValidateSoapMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(validSOAPMessage.replace("Header", ""));
		
		try {
			validateSoapMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			e.printStackTrace();
			assertTrue(e.getMessage().contains("Element header ontbreekt"));
			assertTrue(e.getMessage().contains("<code>923</code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}
	
	@Test
	public void testTransformValidSoapMessage() {
		ValidateSoapMessage validateSoapMessage = new ValidateSoapMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(validSOAPMessage);

		try {
			validateSoapMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			fail("RtmfGucException not expected");
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}

		try {
			assertTrue(defaultMuleMessage.getPayloadAsString().contains(
					validSOAPMessage));
		} catch (Exception e) {
			fail("Exception not expected");
		}
	}

	@Test
	public void testListenerFaultMessage() throws TransformerException {
		ValidateSoapMessage validateSoapMessage = new ValidateSoapMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(
				faultSOAPMessage);

		try {
			validateSoapMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			// We testen een fout bericht, dus hij moet ook fout gaan.
			return;
		}

		fail("exception was expected");
	}
/* (non-Javadoc)
	 * @see org.mule.tck.FunctionalTestCase#getConfigResources()
	 */
	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml";
	}
	
	@Override
	public void doSetUp() throws Exception {
		super.doSetUp();

		// zorg ervoor dat mule blijft draaien en niet bij elke methode opnieuw
		// opstart
		setDisposeManagerPerSuite(true);
	}
public static void main(String[] args) {
	String faultSOAPMessage = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header/><env:Body><env:Fault xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><faultcode xmlns=\"\">soap:Client</faultcode><faultstring xmlns=\"\">Exception [TOPLINK-4002] (Oracle TopLink Essentials - 2.1 (Build b52-fcs (09/24/2008))): oracle.toplink.essentials.exceptions.DatabaseException&#xD;&#xA;Internal Exception: java.sql.SQLException: Listener refused the connection with the following error:&#xA;ORA-12505, TNS:listener does not currently know of SID given in connect descriptor&#xA;The Connection descriptor used by the client was:&#xA;localhost:1521:xe&#xA;&#xD;&#xA;Error Code: 0; nested exception is: &#xA;&#x9;Exception [TOPLINK-4002] (Oracle TopLink Essentials - 2.1 (Build b52-fcs (09/2 4/2008))): oracle.toplink.essentials.exceptions.DatabaseException&#xD;&#xA;Internal Exception: java.sql.SQLException: Listener refused the connection with the following error:&#xA;ORA-12505, TNS:listener does not currently know of SID given in connect descriptor&#xA;The Connection descriptor used by the client was:&#xA;localhost:1521:xe&#xA;&#xD;&#xA;Error Code: 0</faultstring><detail xmlns=\"\"><ns3:zakenmagazijnFault xmlns:ns3=\"http://www.interaccess.nl/webplus/statuswfm_v2\"><ns3:zakenmagazijnMessage>Exception [TOPLINK-4002] (Oracle TopLink Essentials - 2.1 (Build b52-fcs (09/24/2008))): oracle.toplink.essentials.exceptions.DatabaseException&#xD;&#xA;Internal Exception: java.sql.SQLException: Listener refused the connection with the following error:&#xA;ORA-12505, TNS:listener does not currently know of SID given in connect descriptor&#xA;The Connection descriptor used by the client was:&#xA;localhost:1521:xe&#xA;&#xD;&#xA;Error Code: 0; nested exception is: &#xA;&#x9;Exception [TOPLIN K-4002] (Oracle TopLink Essentials - 2.1 (Build b52-fcs (09/24/2008))): oracle.toplink.essentials.exceptions.DatabaseException&#xD;&#xA;Internal Exception: java.sql.SQLException: Listener refused the connection with the following error:&#xA;ORA-12505, TNS:listener does not currently know of SID given in connect descriptor&#xA;The Connection descriptor used by the client was:&#xA;localhost:1521:xe&#xA;&#xD;&#xA;Error Code: 0</ns3:zakenmagazijnMessage></ns3:zakenmagazijnFault></detail></env:Fault></env:Body></env:Envelope>";
	System.out.println(faultSOAPMessage.matches(".*Body>.*Fault.*faultcode.*Fault>.*Body>.*"));
}
}
