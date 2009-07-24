package nl.rotterdam.rtmf.guc.common;

import junit.framework.TestCase;

public class NamespaceFixerTest extends TestCase {
	
	final static String NS_ROTTERDAM = "twd720.resource.ta-twd.rotterdam";
	final static String NS_TMFPORTAL = "tmfportal.ovsoftware.com";
	final static String NS_HI_HA_HO  = "hi-ha-ho.de.grappen.fabriek:12345";

	// Standaard response zoals teruggekregen van de Rotterdamse OSB.
	final static String payloadAsString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
			+ "xmlns:defrep=\"http://twd720.resource.ta-twd.rotterdam.nl:10080/services/defaultreply.xsd\"> "
			+ "   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"> "
			+ "      <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding</wsa:Action> "
			+ "      <wsa:RelatesTo RelationshipType=\"http://www.w3.org/2005/08/addressing/reply\">${wsaMessageId}</wsa:RelatesTo> "
			+ "      <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To> "
			+ "   </soapenv:Header>"
			+ "   <soapenv:Body>"
			+ "      <defrep:response>"
			+ "         <text>OK</text>"
			+ "      </defrep:response>"
			+ "   </soapenv:Body>"
			+ "</soapenv:Envelope>";

	// Aangepaste response zoals teruggekregen van de 'hi-ha-ho.de.grappen.fabriek' OSB, en met twee response nodes.
	final static String payloadAsString2 = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
		+ "xmlns:defrep=\"http://hi-ha-ho.de.grappen.fabriek:12345/defaultreply.xsd\"> "
		+ "   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"> "
		+ "      <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding</wsa:Action> "
		+ "      <wsa:RelatesTo RelationshipType=\"http://www.w3.org/2005/08/addressing/reply\">${wsaMessageId}</wsa:RelatesTo> "
		+ "      <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To> "
		+ "   </soapenv:Header>"
		+ "   <soapenv:Body>"
		+ "      <defrep:response>"
		+ "         <text>OK</text>"
		+ "      </defrep:response>"
		+ "      <defrep:response>"
		+ "         <text>OK</text>"
		+ "      </defrep:response>"
		+ "   </soapenv:Body>"
		+ "</soapenv:Envelope>";

	// Ge-fixed-te response van de Rotterdamse OSB.
	final static String fixResult = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
			+ "xmlns:defrep=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\"> "
			+ "   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"> "
			+ "      <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding</wsa:Action> "
			+ "      <wsa:RelatesTo RelationshipType=\"http://www.w3.org/2005/08/addressing/reply\">${wsaMessageId}</wsa:RelatesTo> "
			+ "      <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To> "
			+ "   </soapenv:Header>"
			+ "   <soapenv:Body>"
			+ "      <defrep:response>"
			+ "         <text>OK</text>"
			+ "      </defrep:response>"
			+ "   </soapenv:Body>"
			+ "</soapenv:Envelope>";

	// Ge-fixed-te response van de 'hi-ha-ho.de.grappen.fabriek' OSB, en met twee response nodes.
	final static String fixResult2 = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
		+ "xmlns:defrep=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\"> "
		+ "   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"> "
		+ "      <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding</wsa:Action> "
		+ "      <wsa:RelatesTo RelationshipType=\"http://www.w3.org/2005/08/addressing/reply\">${wsaMessageId}</wsa:RelatesTo> "
		+ "      <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To> "
		+ "   </soapenv:Header>"
		+ "   <soapenv:Body>"
		+ "      <defrep:response>"
		+ "         <text>OK</text>"
		+ "      </defrep:response>"
		+ "      <defrep:response>"
		+ "         <text>OK</text>"
		+ "      </defrep:response>"
		+ "   </soapenv:Body>"
		+ "</soapenv:Envelope>";

	public void testDoFix() {
		String testResult = null;
		try {
			
			testResult = NamespaceFixer.fixResponseNamespace(payloadAsString);
			
			assertNotNull("Resutaat van de NamespaceFixer is null", testResult);
			assertEquals("NamespaceFixer heeft gefaald. Het resultaat bevat nog steeds: " + NS_ROTTERDAM, false, testResult.contains(NS_ROTTERDAM));
			assertEquals("NamespaceFixer heeft gefaald. Het resultaat bevat geen: " + NS_TMFPORTAL, true, testResult.contains(NS_TMFPORTAL));
			assertEquals("Het resultaat komt niet overeen metde verwachte uitkomst. Check de logging.", fixResult, testResult);
			
		} catch (Exception e) {
			fail("Exception opgetreden tijdens fixen: " + e.getCause());			
		}
	}

	public void testDoFix2ResponseNodes() {
		String testResult = null;
		try {
			
			testResult = NamespaceFixer.fixResponseNamespace(payloadAsString2);
			
			assertNotNull("Resutaat van de NamespaceFixer is null", testResult);
			assertEquals("NamespaceFixer heeft gefaald. Het resultaat bevat nog steeds: " + NS_HI_HA_HO, false, testResult.contains(NS_HI_HA_HO));
			assertEquals("NamespaceFixer heeft gefaald. Het resultaat bevat geen: " + NS_TMFPORTAL, true, testResult.contains(NS_TMFPORTAL));
			assertEquals("Het resultaat komt niet overeen metde verwachte uitkomst. Check de logging.", fixResult2, testResult);
			
		} catch (Exception e) {
			fail("Exception opgetreden tijdens fixen: " + e.getCause());			
		}
	}

}
