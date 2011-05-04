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

import java.util.Properties;

import nl.rotterdam.rtmf.guc.bronhouder.catalogus.BronhouderCatalogus;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.tck.FunctionalTestCase;

/**
 * 
 * @author tarem + rweverwijk
 *
 */
public class IntrekkingAddBereikenAdresTest extends FunctionalTestCase{
	private static final String bronhouderEmail = "muletest@localhost";
	BronhouderCatalogus bronhouderCatalogus = new BronhouderCatalogus();
	private String[] payload = {"","", "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"	<env:Header />" + 
			"	<env:Body>" + 
			"		<ns2:ZaakDetailResponse" + 
			"			xmlns:ns2=\"http://www.interaccess.nl/webplus/statuswfm_v2\">" + 
			"			<ns2:Subject />" + 
			"			<ns2:Zaak_verantwoordelijke_oeh>" + 
			"				<ns2:oeh_id>Rdam-07-07</ns2:oeh_id>" + 
			"				<ns2:oeh_naam>GBA</ns2:oeh_naam>" + 
			"			</ns2:Zaak_verantwoordelijke_oeh>" + 
			"			<ns2:Geen_zaak_initiator>true</ns2:Geen_zaak_initiator>" + 
			"			<ns2:Zaakidentificatie>TMD.09.10.00010</ns2:Zaakidentificatie>" + 
			"			<ns2:Startdatum>2009-10-27T16:00:11.000+01:00</ns2:Startdatum>" + 
			"			<ns2:Zaaktypecode>TMDG</ns2:Zaaktypecode>" + 
			"			<ns2:Zaaktypeomschrijving>Terugmelding Rotterdamse kerngegevens</ns2:Zaaktypeomschrijving>" + 
			"			<ns2:Einddatum xsi:nil=\"true\"" + 
			"				xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"			<ns2:Einddatumgepland xsi:nil=\"true\"" + 
			"				xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"			<ns2:Zaakomschrijving xsi:nil=\"true\"" + 
			"				xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"			<ns2:Kenmerk>" + 
			"				<ns2:kenmerk>GBA</ns2:kenmerk>" + 
			"				<ns2:kenmerkBron>basisRegistratie</ns2:kenmerkBron>" + 
			"			</ns2:Kenmerk>" + 
			"			<ns2:Kenmerk>" + 
			"				<ns2:kenmerk>126946036</ns2:kenmerk>" + 
			"				<ns2:kenmerkBron>objectIdentificatie</ns2:kenmerkBron>" + 
			"			</ns2:Kenmerk>" +
			"			<ns2:Kenmerk>" + 
			"				<ns2:kenmerk>01</ns2:kenmerk>" + 
			"				<ns2:kenmerkBron>objectTag</ns2:kenmerkBron>" + 
			"			</ns2:Kenmerk>" + 
            "           <ns2:Trefwoord>Enno-2009-10-27-006</ns2:Trefwoord>" +
			"			<ns2:Resultaatcode>gemeld</ns2:Resultaatcode>" + 
			"			<ns2:Resultaatomschrijving>De terugmelding is gemeld aan de" + 
			"				bronhouder</ns2:Resultaatomschrijving>" + 
			"			<ns2:Resultaattoelichting xsi:nil=\"true\"" + 
			"				xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"			<ns2:Zaaktoelichting xsi:nil=\"true\"" + 
			"				xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"			<ns2:Uiterlijkeeinddatumafdoening" + 
			"				xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"			<ns2:Oge_id>0</ns2:Oge_id>" + 
			"			<ns2:Formulier>" + 
			"				<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"					<S:Header>" + 
			"						<To:To xmlns:To=\"http://www.w3.org/2005/08/addressing\"" + 
			"							xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/rtmfguc/terugmeldService</To:To>" + 
			"						<Action:Action xmlns:Action=\"http://www.w3.org/2005/08/addressing\"" + 
			"							xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action:Action>" + 
			"						<ReplyTo:ReplyTo xmlns:ReplyTo=\"http://www.w3.org/2005/08/addressing\"" + 
			"							xmlns=\"http://www.w3.org/2005/08/addressing\">" + 
			"							<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>" + 
			"						</ReplyTo:ReplyTo>" + 
			"						<MessageID:MessageID xmlns:MessageID=\"http://www.w3.org/2005/08/addressing\"" + 
			"							xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:2047eee1-c303-47c6-8e41-ca76fe8d4afb</MessageID:MessageID>" + 
			"					</S:Header>" + 
			"					<S:Body>" + 
			"						<ns2:terugmelding" + 
			"							xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\">" + 
			"							<ns2:meldingKenmerk>Enno-2009-10-27-006</ns2:meldingKenmerk>" + 
			"							<ns2:tijdstempelAanlevering>2009-10-27T16:00:11.598+01:00</ns2:tijdstempelAanlevering>" + 
			"							<ns2:basisRegistratie>GBA</ns2:basisRegistratie>" + 
			"							<ns2:objectTag>01</ns2:objectTag>" + 
			"							<ns2:objectIdentificatie>126946036</ns2:objectIdentificatie>" + 
			"							<ns2:toelichting>ZM test</ns2:toelichting>" + 
			"							<ns2:attributen>" + 
			"								<ns2:attribuutIdentificatie>01.86.11</ns2:attribuutIdentificatie>" + 
			"								<ns2:betwijfeldeWaarde>Beppie</ns2:betwijfeldeWaarde>" + 
			"								<ns2:voorstel>Bepster</ns2:voorstel>" + 
			"							</ns2:attributen>" + 
			"							<ns2:contactInfo>" + 
			"								<ns2:naam>Enno Buis</ns2:naam>" + 
			"								<ns2:telefoon />" + 
			"								<ns2:email>Enno.Buis@rotterdam.org</ns2:email>" + 
			"							</ns2:contactInfo>" + 
			"						</ns2:terugmelding>" + 
			"					</S:Body>" + 
			"				</S:Envelope>" + 
			"			</ns2:Formulier>" + 
			"			<ns2:Status>" + 
			"				<ns2:Zaakidentificatie>TMD.09.10.00010</ns2:Zaakidentificatie>" + 
			"				<ns2:Geen_status_zetter>true</ns2:Geen_status_zetter>" + 
			"				<ns2:Datumstatusgezet>2009-10-27T16:00:11.000+01:00</ns2:Datumstatusgezet>" + 
			"				<ns2:Statuscode>ON</ns2:Statuscode>" + 
			"				<ns2:Statusomschrijving>Ontvangen</ns2:Statusomschrijving>" + 
			"				<ns2:Statustoelichting xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Statusvolgnummer>1</ns2:Statusvolgnummer>" + 
			"				<ns2:Oge_id>0</ns2:Oge_id>" + 
			"			</ns2:Status>" + 
			"			<ns2:Stap>" + 
			"				<ns2:Zaakidentificatie>TMD.09.10.00010</ns2:Zaakidentificatie>" + 
			"				<ns2:Geen_stap_uitvoerder>true</ns2:Geen_stap_uitvoerder>" + 
			"				<ns2:Geen_stap_verantwoordelijke>true</ns2:Geen_stap_verantwoordelijke>" + 
			"				<ns2:Begindatum>2009-10-27T16:00:11.000+01:00</ns2:Begindatum>" + 
			"				<ns2:Stapeinddatum xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Einddatumgepland xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Normdoorlooptijd xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Procedurecode xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Procedureomschrijving xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Rappeldatum xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Resultaatcode xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Resultaatomschrijving xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Resultaattoelichting xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Stapomschrijving>Het beoordelen van de terugmelding door de" + 
			"					bronhouder</ns2:Stapomschrijving>" + 
			"				<ns2:Staptypecode>BEOORDELEN</ns2:Staptypecode>" + 
			"				<ns2:Stapvolgnummer>2</ns2:Stapvolgnummer>" + 
			"				<ns2:Staptoelichting xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Oge_id>0</ns2:Oge_id>" + 
			"			</ns2:Stap>" + 
			"			<ns2:Stap>" + 
			"				<ns2:Zaakidentificatie>TMD.09.10.00010</ns2:Zaakidentificatie>" + 
			"				<ns2:Geen_stap_uitvoerder>true</ns2:Geen_stap_uitvoerder>" + 
			"				<ns2:Geen_stap_verantwoordelijke>true</ns2:Geen_stap_verantwoordelijke>" + 
			"				<ns2:Begindatum>2009-10-27T16:00:11.000+01:00</ns2:Begindatum>" + 
			"				<ns2:Stapeinddatum>2009-10-27T16:00:15.000+01:00</ns2:Stapeinddatum>" + 
			"				<ns2:Einddatumgepland xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Normdoorlooptijd xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Procedurecode xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Procedureomschrijving xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Rappeldatum xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Resultaatcode>gemeld</ns2:Resultaatcode>" + 
			"				<ns2:Resultaatomschrijving xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Resultaattoelichting xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Stapomschrijving>Het ontvangen van de terugmelding door de" + 
			"					bronhouder</ns2:Stapomschrijving>" + 
			"				<ns2:Staptypecode>ONTVANGEN</ns2:Staptypecode>" + 
			"				<ns2:Stapvolgnummer>1</ns2:Stapvolgnummer>" + 
			"				<ns2:Staptoelichting xsi:nil=\"true\"" + 
			"					xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" />" + 
			"				<ns2:Oge_id>0</ns2:Oge_id>" + 
			"			</ns2:Stap>" + 
			"		</ns2:ZaakDetailResponse>" + 
			"	</env:Body>" + 
			"</env:Envelope>"};
	
	@Test
	public void testTransformEmptyMessage() throws TransformerException {
		
		IntrekkingAddBereikenAdresTransformer transformer = new IntrekkingAddBereikenAdresTransformer();
		transformer.setBronhouderCatalogus(bronhouderCatalogus);
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(payload);

			transformer.transform(defaultMuleMessage, null);
			assertEquals(bronhouderEmail, defaultMuleMessage.getProperty("toAddresses"));
	}
	
	@Test
	public void testTransformEmptyMessage2() throws TransformerException {
		
		IntrekkingAddBereikenAdresTransformer transformer = new IntrekkingAddBereikenAdresTransformer();
		transformer.setBronhouderCatalogus(bronhouderCatalogus);
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(payload);

			transformer.transform(defaultMuleMessage, null);
			assertEquals(bronhouderEmail, defaultMuleMessage.getProperty("toAddresses"));
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
		
		
		// properties zetten
		Properties properties = new Properties();
		properties.put("bronhouder1.naam", "RDAM-01");
		properties.put("bronhouder1.code", "Code van RDAM-01");
		properties.put("bronhouder1.bereikenVia", "Email");
		properties.put("bronhouder1.bereikenAdres", "muletest@localhost");
		properties.put("bronhouder1.basisregistratie1", "GBA");
		bronhouderCatalogus.setProperties(properties);

	}

}
