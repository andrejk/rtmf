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
public class ValidateStelselCatalogusMessageTest extends FunctionalTestCase{
	
	private static final String VALIDSTELSELCATALOGUSOBJECTLISTMESSAGE_STRING = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" "
			+ "xmlns=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd\">"
			+ "  <soapenv:Header xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "    <wsa:MessageID xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">uuid:f9972ae5-5659-4112-bcb6-3fbb0a7aabbc</wsa:MessageID>"
			+ "  </soapenv:Header>"
			+ "  <soap:Body>"
			+ "    <getObjectTypeListResponse>"
			+ "      <objectTypeList>"
			+ "        <bevraagbaar>true</bevraagbaar>"
			+ "        <instructie/>"
			+ "        <naam>Ouder1</naam>"
			+ "        <tag>02</tag>"
			+ "      </objectTypeList>"
			+ "    </getObjectTypeListResponse>"
			+ "  </soap:Body>"
			+ "</soap:Envelope>";
	
	private static final String VALIDSTELSELCATALOGUSOBJECTINFO_STRING = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"" + 
			" xmlns=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd\">" + 
			"  <soapenv:Header xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"    <wsa:MessageID xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">uuid:1a716ed1-f4f1-4257-9700-61dab283637c</wsa:MessageID>" + 
			"  </soapenv:Header>" + 
			"  <soap:Body>" + 
			"    <getObjectInfoResponse>" + 
			"      <objectInfo>" + 
			"        <ObjectHeaderInfo>" + 
			"          <bevraagbaar>true</bevraagbaar>" + 
			"          <instructie/>" + 
			"          <naam>Nationaliteitentabel</naam>" + 
			"          <tag>32</tag>" + 
			"        </ObjectHeaderInfo>" + 
			"        <attributen>" + 
			"          <code>32.05.11</code>" + 
			"          <gegevenstype/>" + 
			"          <naam>Nationaliteitscode</naam>" + 
			"          <stufpath>/nat/body/object/code</stufpath>" + 
			"        </attributen>" + 
			"        <attributen>" + 
			"          <code>32.99.98</code>" + 
			"          <gegevenstype/>" + 
			"          <naam>Datum ingang tabelregel</naam>" + 
			"          <stufpath>/nat/body/object/tijdvakObject/beginObject</stufpath>" + 
			"        </attributen>" + 
			"      </objectInfo>" + 
			"    </getObjectInfoResponse>" + 
			"  </soap:Body>" + 
			"</soap:Envelope>";

	private static final String VALIDSTELSELCATALOGUSBASISREGISTRATIE_STRING = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"            <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">" + 
			"            <wsa:Action>getBasisregistratieList</wsa:Action>" + 
			"            <wsa:RelatesTo RelationshipType=\"http://www.w3.org/2005/08/addressing/reply\">${wsaMessageId}</wsa:RelatesTo>" + 
			"            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>" + 
			"           </soapenv:Header>" + 
			"           <soapenv:Body>" + 
			"               <stelgm:getBasisregistratieListResponse xmlns:stelgm=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd\">" + 
			"                  <!--Zero or more repetitions:-->" + 
			"                  <stelgm:basisregistratieList>" + 
			"                     <!-- assupmtie: zelfde tag voor zelfde registratie in TMF en GM -->" + 
			"                     <stelgm:tag>TMF-REG1</stelgm:tag>" + 
			"                     <stelgm:naam>Personen (GM)</stelgm:naam>" + 
			"                  </stelgm:basisregistratieList>" + 
			"                  <stelgm:basisregistratieList>" + 
			"                     <stelgm:tag>GM-REG2</stelgm:tag>" + 
			"                     <stelgm:naam>Havens (GM)</stelgm:naam>" + 
			"                  </stelgm:basisregistratieList>" + 
			"                  <stelgm:basisregistratieList>" + 
			"                     <stelgm:tag>GBA</stelgm:tag>" + 
			"                     <stelgm:naam>Gemeentelijke Basisregistratie Persoonsgegevens</stelgm:naam>" + 
			"                  </stelgm:basisregistratieList>" + 
			"               </stelgm:getBasisregistratieListResponse>" + 
			"            </soapenv:Body>" + 
			"         </soapenv:Envelope>";
	/**
	 * Test method for
	 * {@link nl.rotterdam.rtmf.guc.transformer.ValidateStelselCatalogusMessage#doValidate(org.mule.api.MuleMessage, java.lang.String)}
	 * .
	 */
	@Test
	public void testTransformMissingSchema() {
		ValidateStelselCatalogusMessage validateStelselCatalogusMessage = new ValidateStelselCatalogusMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(VALIDSTELSELCATALOGUSOBJECTLISTMESSAGE_STRING.replace("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd", ""));
		 
			try {
				validateStelselCatalogusMessage.transform(defaultMuleMessage, null);
			} catch (RtmfGucException e) {
				//e.printStackTrace();
				assertTrue(e.getMessage().contains("Geen namespace voor stelselbevragen in bericht"));
				assertTrue(e.getMessage().contains("<code>931</code>"));
				return;
			} catch (TransformerException e) {
				fail("TransformerException not expected");
			}
			fail("RtmfGucException was expected");
	}

	@Test
	public void testValidMessageObjectList() {
		ValidateStelselCatalogusMessage validateStelselCatalogusMessage = new ValidateStelselCatalogusMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(VALIDSTELSELCATALOGUSOBJECTLISTMESSAGE_STRING);
		 
			try {
				validateStelselCatalogusMessage.transform(defaultMuleMessage, null);
			} catch (RtmfGucException e) {
				fail("RtmfGucException not expected");
				return;
			} catch (TransformerException e) {
				fail("TransformerException not expected");
			}
			try {
				assertTrue(defaultMuleMessage.getPayloadAsString().contains(VALIDSTELSELCATALOGUSOBJECTLISTMESSAGE_STRING));
			} catch (Exception e) {
				fail("Exception not expected");
			}
	}

	@Test
	public void testValidMessageObjectInfo() {
		ValidateStelselCatalogusMessage validateStelselCatalogusMessage = new ValidateStelselCatalogusMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(VALIDSTELSELCATALOGUSOBJECTINFO_STRING);
		 
			try {
				validateStelselCatalogusMessage.transform(defaultMuleMessage, null);
			} catch (RtmfGucException e) {
				fail("RtmfGucException not expected");
				return;
			} catch (TransformerException e) {
				fail("TransformerException not expected");
			}
			try {
				assertTrue(defaultMuleMessage.getPayloadAsString().contains(VALIDSTELSELCATALOGUSOBJECTINFO_STRING));
			} catch (Exception e) {
				fail("Exception not expected");
			}
	}
	@Test
	public void testValidMessageBasisregistratieList() {
		ValidateStelselCatalogusMessage validateStelselCatalogusMessage = new ValidateStelselCatalogusMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(VALIDSTELSELCATALOGUSBASISREGISTRATIE_STRING);
		 
			try {
				validateStelselCatalogusMessage.transform(defaultMuleMessage, null);
			} catch (RtmfGucException e) {
				fail("RtmfGucException not expected");
				return;
			} catch (TransformerException e) {
				fail("TransformerException not expected");
			}
			try {
				assertTrue(defaultMuleMessage.getPayloadAsString().contains(VALIDSTELSELCATALOGUSBASISREGISTRATIE_STRING));
			} catch (Exception e) {
				fail("Exception not expected");
			}
	}
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
}
