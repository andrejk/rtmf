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

import nl.rotterdam.rtmf.guc.StelselCatalogusCache;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

public class PrettyTerugmeldenEmailTest extends FunctionalTestCase {

	String terugmelding = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
		+ "	<S:Header>"
		+ "		<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/tmfTerugmeldService</To>"
		+ "		<Action xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action>"
		+ "		<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">"
		+ "			<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>"
		+ "		</ReplyTo>"
		+ "		<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">Component-Test-UUID</MessageID>"
		+ "	</S:Header>"
		+ "	<S:Body>"
		+ "		<ns2:terugmelding"
		+ "		xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\""
		+ "		xmlns:ns3=\"http://tmfportal.ovsoftware.com/services\" xmlns:ns4=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\">"
		+ "			<ns2:meldingKenmerk>UnitTester-001</ns2:meldingKenmerk>"
		+ "			<ns2:tijdstempelAanlevering>2009-08-27T15:51:58.635+02:00</ns2:tijdstempelAanlevering>"
		+ "			<ns2:basisRegistratie>RDAM-02</ns2:basisRegistratie>"
		+ "			<ns2:objectTag>GM-PERSOON</ns2:objectTag>"
		+ "			<ns2:objectIdentificatie>adres</ns2:objectIdentificatie>"
		+ "			<ns2:toelichting>Unit tester</ns2:toelichting>"
		+ "			<ns2:attributen>"
		+ "				<ns2:attribuutIdentificatie>GM-PERSOON-VOORNAAM</ns2:attribuutIdentificatie>"
		+ "				<ns2:betwijfeldeWaarde>Petet</ns2:betwijfeldeWaarde>"
		+ "				<ns2:voorstel>Peter</ns2:voorstel>"
		+ "			</ns2:attributen>"
		+ "			<ns2:attributen>"
		+ "				<ns2:attribuutIdentificatie>GM-PERSOON-BIJNAAM</ns2:attribuutIdentificatie>"
		+ "				<ns2:betwijfeldeWaarde>Klaas</ns2:betwijfeldeWaarde>"
		+ "				<ns2:voorstel>Klaaz</ns2:voorstel>"
		+ "			</ns2:attributen>"
		+ "			<ns2:contactInfo>"
		+ "				<ns2:naam>De Tester</ns2:naam>"
		+ "				<ns2:telefoon>555-1982</ns2:telefoon>"
		+ "				<ns2:email>de.tester@de.test.fabriek.nl</ns2:email>"
		+ "			</ns2:contactInfo>"
		+ "		</ns2:terugmelding>" + "	</S:Body>" + "</S:Envelope>";
	
	String nieuwZaaknummerResponse = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">"
		+ "   <env:Header/>"
		+ "   <env:Body>"
		+ "      <ns2:zaaknummerResponse xmlns:ns2=\"http://www.rotterdam.nl/zkn/zaaknummer\">TMD.09.10.10101</ns2:zaaknummerResponse>"
		+ "   </env:Body>"
		+ "</env:Envelope>";


	@Override
	protected String getConfigResources() {
		return "groovy-template-mule-config.xml";
	}

	public void testPrettyTerugmelding() throws Exception {
		MuleClient client = new MuleClient();
		
		StelselCatalogusCache cache = (StelselCatalogusCache) client
		.getProperty("stelselCatalogusCacheBean");
		
		if (cache != null) {
			cache.addKey("GM-PERSOON", "GM", false, "Persoon");
		}
		
		MuleMessage response = client.send("vm://testPrettyEmail",
				new String[] {terugmelding, nieuwZaaknummerResponse}, null);
		assertNotNull("De response is null.", response);
		assertNotNull("De payload is null.", response.getPayload());
		String responsePayload = response.getPayloadAsString();

		assertTrue("Contact email adres niet gevonden", responsePayload.contains("E-mail                          : de.tester@de.test.fabriek.nl"));
	}

}
