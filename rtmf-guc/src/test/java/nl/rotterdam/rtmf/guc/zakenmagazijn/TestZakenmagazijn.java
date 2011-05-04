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
package nl.rotterdam.rtmf.guc.zakenmagazijn;

import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.NullPayload;

public class TestZakenmagazijn extends FunctionalTestCase {

	String nieuwZaaknummerRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:zaak=\"http://www.rotterdam.nl/zkn/zaaknummer\">"
			+ "   <soapenv:Header/>"
			+ "   <soapenv:Body>"
			+ "      <zaak:nieuwZaaknummerRequest>"
			+ "         <zaak:zaaktype>TMDG</zaak:zaaktype>"
			+ "         <zaak:jaartal>2009</zaak:jaartal>"
			+ "         <zaak:maand>10</zaak:maand>"
			+ "      </zaak:nieuwZaaknummerRequest>"
			+ "   </soapenv:Body>"
			+ "</soapenv:Envelope>";

	String nieuwZaaknummerResponseOK = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "   <env:Header/>"
			+ "   <env:Body>"
			+ "      <ns2:zaaknummerResponse xmlns:ns2=\"http://www.rotterdam.nl/zkn/zaaknummer\">TMD.09.10.10101</ns2:zaaknummerResponse>"
			+ "   </env:Body>" + "</env:Envelope>";

	String nieuwZaaknummerResponseNotOK = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "	<env:Header />"
			+ "	<env:Body>"
			+ "		<env:Fault xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "			<faultcode>soap:Client</faultcode>"
			+ "			<faultstring>java.lang.StringIndexOutOfBoundsException: String index"
			+ "				out of range: 3; nested exception is:"
			+ "				java.lang.StringIndexOutOfBoundsException: String index out of"
			+ "				range: 3</faultstring>"
			+ "		</env:Fault>"
			+ "	</env:Body>"
			+ "</env:Envelope>";

	String zaakCreatieRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:stat=\"http://www.interaccess.nl/webplus/statuswfm_v2\">"
			+ "	<soapenv:Header />"
			+ "	<soapenv:Body>"
			+ "		<stat:Zaak>"
			+ "			<!--Verantwoordelijke: ff tijdelijk geen, openstaande vraag aan IOO-->"
			+ "			<stat:Geen_zaak_verantwoordelijke>true"
			+ "			</stat:Geen_zaak_verantwoordelijke>"
			+ "			<!--Initiator: ff tijdelijk geen, openstaande vraag aan IOO-->"
			+ "			<stat:Geen_zaak_initiator>true</stat:Geen_zaak_initiator>"
			+ "			<stat:Zaakidentificatie>TMD.09.10.01010</stat:Zaakidentificatie>"
			+ "			<stat:Startdatum>2009-08-27T15:51:58.635+02:00</stat:Startdatum>"
			+ "			<stat:Zaaktypecode>TMDG</stat:Zaaktypecode>"
			+ "			<stat:Zaaktypeomschrijving>Terugmelding Rotterdamse kerngegevens</stat:Zaaktypeomschrijving>"
			+ "			<stat:Kenmerk>"
			+ "				<stat:kenmerk>RDAM-02</stat:kenmerk>"
			+ "				<stat:kenmerkBron>basisRegistratie</stat:kenmerkBron>"
			+ "			</stat:Kenmerk>"
			+ "			<stat:Kenmerk>"
			+ "				<stat:kenmerk>GM-PERSOON</stat:kenmerk>"
			+ "				<stat:kenmerkBron>objectTag</stat:kenmerkBron>"
			+ "			</stat:Kenmerk>"
			+ "			<stat:Kenmerk>"
			+ "				<stat:kenmerk>OBJECTNAAM</stat:kenmerk>"
			+ "				<stat:kenmerkBron>objectNaam</stat:kenmerkBron>"
			+ "			</stat:Kenmerk>"
			+ "			<stat:Kenmerk>"
			+ "				<stat:kenmerk>adres</stat:kenmerk>"
			+ "				<stat:kenmerkBron>objectIdentificatie</stat:kenmerkBron>"
			+ "			</stat:Kenmerk>"
			+ "         <stat:Trefwoord>UnitTester-ZakenMagazijn</stat:Trefwoord>"
			+ "			<stat:Resultaatcode>ontvangen</stat:Resultaatcode>"
			+ "			<stat:Resultaatomschrijving>Ontvangen door het Rotterdamse verdeelpunt</stat:Resultaatomschrijving>"
			+ "			<stat:Oge_id>0</stat:Oge_id>"
			+ "			<!--Optional:-->"
			+ "			<stat:Formulier>"
			+ "				<!--You may enter ANY elements at this point-->"
			+ "				<!--Hier staat het terugmelding bericht zoals verstuurd door de TmfPortal. -->"
			+ "				<!--Echter, de attachments uit het terugmeldingbericht zijn verwijderd! -->"
			+ "			</stat:Formulier>"
			+ "		</stat:Zaak>"
			+ "	</soapenv:Body>"
			+ "</soapenv:Envelope>";

	String stapCreatieRequest = "soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:stat=\"http://www.interaccess.nl/webplus/statuswfm_v2\"> "
			+ "   <soapenv:Header/>"
			+ "   <soapenv:Body>"
			+ "      <stat:Stap>"
			+ "         <stat:Zaakidentificatie>TMD.09.10.98765</stat:Zaakidentificatie>"
			+ "         <!--Uitvoerder: nog ff geen, openstaande vraag bij IOO-->"
			+ "         <stat:Geen_stap_uitvoerder>true</stat:Geen_stap_uitvoerder>"
			+ "         <!--Uitvoerder: nog ff geen, openstaande vraag bij IOO-->"
			+ "         <stat:Geen_stap_verantwoordelijke>true</stat:Geen_stap_verantwoordelijke>"
			+ "         <stat:Begindatum>2009-10-06T00:00:00.000</stat:Begindatum>"
			+ "         <stat:Stapomschrijving>%s</stat:Stapomschrijving>"
			+ "        <stat:Staptypecode>%s</stat:Staptypecode>"
			+ "        <stat:Oge_id>0</stat:Oge_id>"
			+ "      </stat:Stap>"
			+ "   </soapenv:Body>" + "</soapenv:Envelope>";

	String statusCreatieOntvangenRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:stat=\"http://www.interaccess.nl/webplus/statuswfm_v2\">"
			+ "   <soapenv:Header/>"
			+ "   <soapenv:Body>"
			+ "      <stat:Status>"
			+ "         <stat:Zaakidentificatie>TMD.09.10.00001</stat:Zaakidentificatie>"
			+ "         <!--Zetter: tijdelijk nog niet, openstaande vraag bij IOO-->"
			+ "         <stat:Geen_status_zetter>true</stat:Geen_status_zetter>"
			+ "         <stat:Datumstatusgezet>2009-10-06T00:00:00.000</stat:Datumstatusgezet>"
			+ "         <stat:Statuscode>ON</stat:Statuscode>"
			+ "         <stat:Statusomschrijving>Ontvangen</stat:Statusomschrijving>"
			+ "         <stat:Oge_id>0</stat:Oge_id>"
			+ "      </stat:Status>"
			+ "   </soapenv:Body>" + "</soapenv:Envelope>";

	String zmResponseOK = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "   <env:Header/>"
			+ "   <env:Body>"
			+ "      <ns2:Retour xmlns:ns2=\"http://www.interaccess.nl/webplus/statuswfm_v2\">"
			+ "         <ns2:Operatiestatus>operatie succesvol</ns2:Operatiestatus>"
			+ "         <ns2:Operatiefout/>"
			+ "      </ns2:Retour>"
			+ "   </env:Body>" + "</env:Envelope>";

	String zmResponseNotOK = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "   <env:Header/>"
			+ "   <env:Body>"
			+ "      <ns2:Retour xmlns:ns2=\"http://www.interaccess.nl/webplus/statuswfm_v2\">"
			+ "         <ns2:Operatiestatus>operatie is niet succesvol</ns2:Operatiestatus>"
			+ "         <ns2:Operatiefout>Err: Not responding</ns2:Operatiefout>"
			+ "      </ns2:Retour>" + "   </env:Body>" + "</env:Envelope>";

	String zaakUpdateRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:stat=\"http://www.interaccess.nl/webplus/statuswfm_v2\">"
			+ "   <soapenv:Header/>"
			+ "   <soapenv:Body>"
			+ "      <stat:ZaakUpdate>"
			+ "         <stat:Zaakidentificatie>TMD.09.10.00001</stat:Zaakidentificatie>"
			+ "         <stat:Resultaatcode>gemeld</stat:Resultaatcode>"
			+ "         <!--Optional:-->"
			+ "         <stat:Resultaatomschrijving>De terugmelding is gemeld aan de bronhouder</stat:Resultaatomschrijving>"
			+ "         <stat:Oge_id>0</stat:Oge_id>"
			+ "      </stat:ZaakUpdate>"
			+ "   </soapenv:Body>"
			+ "</soapenv:Envelope>";

	String stapUpdateGemeldRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:stat=\"http://www.interaccess.nl/webplus/statuswfm_v2\">"
			+ "   <soapenv:Header/>"
			+ "   <soapenv:Body>"
			+ "      <stat:StapUpdate>"
			+ "         <stat:Zaakidentificatie>TMD.09.10.00001</stat:Zaakidentificatie>"
			+ "         <stat:Begindatum>2009-10-06T00:00:00.000</stat:Begindatum>"
			+ "         <stat:Staptypecode>ONTVANGEN</stat:Staptypecode>"
			+ "         <stat:Resultaatcode>gemeld</stat:Resultaatcode>"
			+ "        <stat:Stapeinddatum>2009-10-11T00:00:00.000</stat:Stapeinddatum>"
			+ "         <stat:Oge_id>0</stat:Oge_id>"
			+ "      </stat:StapUpdate>"
			+ "   </soapenv:Body>"
			+ "</soapenv:Envelope>";

	@Override
	protected String getConfigResources() {
		return "rtmfguc-config.xml,rtmfguc-mocks-config.xml,rtmfguc-zm-config.xml,rtmfguc-mocks-zm-config.xml";
	}

	public void testNieuwZaaknummer() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://rtmfguc/zakenMagazijnNieuwZaaknummerService",
				nieuwZaaknummerRequest, null);
		assertNotNull(
				"Geen response teruggekregen na aanroep NieuwZaaknummer.",
				response);
		assertNotNull(
				"Geen payload gevonden in de response van NieuwZaaknummer",
				response.getPayload());

		String payload = response.getPayloadAsString();
		assertNotNull("Geen payload teruggekregen van NieuwZaaknummer", payload);
		assertTrue("Nieuwe zaaknummer komt niet overeen.", payload
				.contains("TMD.09.10.10101"));

		// System.out.println("Payload:" + payload);
	}

	public void testZaakCreatie() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://rtmfguc/zakenMagazijnZaakCreatieService",
				zaakCreatieRequest, null);
		assertNotNull("Geen response teruggekregen na aanroep ZaakCreatie.",
				response);
		assertNotNull("Geen payload gevonden in de response van ZaakCreatie",
				response.getPayload());

		String payload = response.getPayloadAsString();
		assertNotNull("Geen payload teruggekregen van ZaakCreatie", payload);
		assertTrue("De zaak is niet gecreeerd.", payload
				.contains("operatie succesvol"));

		// System.out.println("Payload:" + payload);
	}

	public void testStapCreatieOntvangen() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://rtmfguc/zakenMagazijnStapCreatieService",
				String.format(stapCreatieRequest,
						"Het ontvangen van de terugmelding door de bronhouder",
						"ONTVANGEN"), null);
		assertNotNull(
				"Geen response teruggekregen na aanroep StapCreatieOntvangen.",
				response);
		assertNotNull(
				"Geen payload gevonden in de response van StapCreatieOntvangen",
				response.getPayload());

		String payload = response.getPayloadAsString();
		assertNotNull("Geen payload teruggekregen van StapCreatieOntvangen",
				payload);
		assertTrue("De Stap 'Ontvangen' is niet aangemaakt.", payload
				.contains("operatie succesvol"));

		// System.out.println("Payload:" + payload);
	}

	public void testStatusCreatieOntvangen() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://rtmfguc/zakenMagazijnStatusCreatieService",
				statusCreatieOntvangenRequest, null);
		assertNotNull("Geen response teruggekregen na aanroep StatusCreatie.",
				response);
		assertNotNull("Geen payload gevonden in de response van StatusCreatie",
				response.getPayload());

		String payload = response.getPayloadAsString();
		assertNotNull("Geen payload teruggekregen van StatusCreatie", payload);
		assertTrue("De Status is niet aangemaakt.", payload
				.contains("operatie succesvol"));

		// System.out.println("Payload:" + payload);
	}

	public void testZakenmagazijnResponseTransformerOK() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send("vm://ZakenmagazijnResponseMockIn",
				zmResponseOK, null);
		assertNotNull(
				"Geen response teruggekregen na aanroep ZakenmagazijnResponseMockIn.",
				response);
		assertNotNull(
				"Geen payload gevonden in de response van ZakenmagazijnResponseMockIn",
				response.getPayload());

		String payload = response.getPayloadAsString();
		assertNotNull("Geen payload teruggekregen", payload);
		assertTrue("De zaak is niet aangemaakt.", payload
				.contains("operatie succesvol"));

		// System.out.println("Payload:" + payload);
	}

	public void testZakenmagazijnResponseTransformerNotOK() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send("vm://ZakenmagazijnResponseMockIn",
				zmResponseNotOK, null);
		assertNotNull(
				"Geen response teruggekregen na aanroep ZakenmagazijnResponseMockIn.",
				response);
		assertNotNull(
				"Geen payload gevonden in de response van ZakenmagazijnResponseMockIn",
				response.getPayload());
		assertTrue("NullPayload verwacht door fout.",
				response.getPayload() == NullPayload.getInstance());

		ExceptionPayload exPayload = response.getExceptionPayload();
		assertNotNull(
				"Geen exception payload gevonden in de response van ZakenmagazijnResponseMockIn",
				exPayload);
		// System.out.println("ExceptionPayload:" + exPayload.getMessage());
	}

	public void testZakenmagazijnZaaknummerResponseTransformerOK()
			throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send("vm://ZakenmagazijnResponseMockIn",
				nieuwZaaknummerResponseOK, null);
		assertNotNull(
				"Geen response teruggekregen na aanroep ZakenmagazijnResponseMockIn.",
				response);
		assertNotNull(
				"Geen payload gevonden in de response van ZakenmagazijnResponseMockIn",
				response.getPayload());

		String payload = response.getPayloadAsString();
		assertNotNull("Geen payload teruggekregen van NieuwZaaknummer", payload);
		assertTrue("Nieuwe zaaknummer komt niet overeen.", payload
				.contains("TMD.09.10.10101"));

		// System.out.println("Payload:" + payload);
	}

	public void testZakenmagazijnZaaknummerResponseTransformerNotOK()
			throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send("vm://ZakenmagazijnResponseMockIn",
				nieuwZaaknummerResponseNotOK, null);
		assertNotNull(
				"Geen response teruggekregen na aanroep ZakenmagazijnResponseMockIn.",
				response);
		assertNotNull(
				"Geen payload gevonden in de response van ZakenmagazijnResponseMockIn",
				response.getPayload());
		assertTrue("NullPayload verwacht door fout.",
				response.getPayload() == NullPayload.getInstance());

		ExceptionPayload exPayload = response.getExceptionPayload();
		assertNotNull(
				"Geen exception payload gevonden in de response van ZakenmagazijnResponseMockIn",
				exPayload);
		// System.out.println("ExceptionPayload:" + exPayload.getMessage());
	}

	public void testZaakUpdate() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://rtmfguc/zakenMagazijnZaakUpdateService",
				zaakUpdateRequest, null);
		assertNotNull("Geen response teruggekregen na aanroep ZaakUpdate.",
				response);
		assertNotNull("Geen payload gevonden in de response van ZaakUpdate",
				response.getPayload());

		String payload = response.getPayloadAsString();
		assertNotNull("Geen payload teruggekregen van ZaakUpdate", payload);
		assertTrue("De zaak is niet ge-update/aangepast.", payload
				.contains("operatie succesvol"));

		// System.out.println("Payload:" + payload);
	}

	public void testStapUpdateGemeld() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://rtmfguc/zakenMagazijnStapUpdateService",
				stapUpdateGemeldRequest, null);
		assertNotNull("Geen response teruggekregen na aanroep StapUpdate.",
				response);
		assertNotNull("Geen payload gevonden in de response van StapUpdate",
				response.getPayload());

		String payload = response.getPayloadAsString();
		assertNotNull("Geen payload teruggekregen van StapUpdate", payload);
		assertTrue("De Stap is niet aangemaakt.", payload
				.contains("operatie succesvol"));

		// System.out.println("Payload:" + payload);
	}

	public void testStapCreatieBeoordelen() throws Exception {
		MuleClient client = new MuleClient();
		MuleMessage response = client.send(
				"vm://rtmfguc/zakenMagazijnStapCreatieService",
				String.format(stapCreatieRequest,
						"Het beoordelen van de terugmelding door de bronhouder",
						"BEOORDELEN"), null);
		assertNotNull(
				"Geen response teruggekregen na aanroep StapCreatieBeoordelen.",
				response);
		assertNotNull(
				"Geen payload gevonden in de response van StapCreatieBeoordelen",
				response.getPayload());

		String payload = response.getPayloadAsString();
		assertNotNull("Geen payload teruggekregen van StapCreatieBeoordelen",
				payload);
		assertTrue("De Stap 'Beoordelen' is niet aangemaakt.", payload
				.contains("operatie succesvol"));

		// System.out.println("Payload:" + payload);
	}
	
	@Override
	public void doSetUp() throws Exception {
		super.doSetUp();		
		// zorg ervoor dat mule blijft draaien en niet bij elke methode opnieuw
		// opstart
		setDisposeManagerPerSuite(true);
	}		

}
