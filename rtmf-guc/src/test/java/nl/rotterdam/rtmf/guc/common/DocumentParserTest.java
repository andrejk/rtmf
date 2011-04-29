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
package nl.rotterdam.rtmf.guc.common;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * @author rweverwijk
 *
 */
public class DocumentParserTest {
	String payloadAsString = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
	"   <env:Header/>\n" + 
	"   <env:Body>\n" + 
	"      <ns2:ZaakQueryResponse xmlns:ns2=\"http://www.interaccess.nl/webplus/statuswfm_v2\">\n" + 
	"         <ns2:Zaak>\n" + 
	"            <ns2:Subject/>\n" + 
	"            <ns2:Zaak_verantwoordelijke_oeh>\n" + 
	"               <ns2:oeh_id>Rdam-07-07</ns2:oeh_id>\n" + 
	"               <ns2:oeh_naam>GBA</ns2:oeh_naam>\n" + 
	"            </ns2:Zaak_verantwoordelijke_oeh>\n" + 
	"            <ns2:Geen_zaak_initiator>true</ns2:Geen_zaak_initiator>\n" + 
	"            <ns2:Zaakidentificatie>TMD.09.10.00010</ns2:Zaakidentificatie>\n" + 
	"            <ns2:Startdatum>2009-10-27T16:00:11.000+01:00</ns2:Startdatum>\n" + 
	"            <ns2:Zaaktypecode>TMDG</ns2:Zaaktypecode>\n" + 
	"            <ns2:Zaaktypeomschrijving>Terugmelding Rotterdamse kerngegevens</ns2:Zaaktypeomschrijving>\n" + 
	"            <ns2:Einddatum xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Einddatumgepland xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Zaakomschrijving xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Kenmerk>\n" + 
	"               <ns2:kenmerk>GBA</ns2:kenmerk>\n" + 
	"               <ns2:kenmerkBron>basisRegistratie</ns2:kenmerkBron>\n" + 
	"            </ns2:Kenmerk>\n" + 
	"            <ns2:Kenmerk>\n" + 
	"               <ns2:kenmerk>126946036</ns2:kenmerk>\n" + 
	"               <ns2:kenmerkBron>objectIdentificatie</ns2:kenmerkBron>\n" + 
	"            </ns2:Kenmerk>\n" + 
	"            <ns2:Kenmerk>\n" + 
	"               <ns2:kenmerk>01</ns2:kenmerk>\n" + 
	"               <ns2:kenmerkBron>objectTag</ns2:kenmerkBron>\n" + 
	"            </ns2:Kenmerk>\n" +
	"			 <ns2:Trefwoord>Enno-2009-10-27-006</ns2:Trefwoord>\n" +
	"            <ns2:Resultaatcode>gemeld</ns2:Resultaatcode>\n" + 
	"            <ns2:Resultaatomschrijving>De terugmelding is gemeld aan de bronhouder</ns2:Resultaatomschrijving>\n" + 
	"            <ns2:Resultaattoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Zaaktoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Uiterlijkeeinddatumafdoening xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Oge_id>0</ns2:Oge_id>\n" + 
	"         </ns2:Zaak>\n" + 
	"         <ns2:Zaak>\n" + 
	"            <ns2:Subject/>\n" + 
	"            <ns2:Zaak_verantwoordelijke_oeh>\n" + 
	"               <ns2:oeh_id>Rdam-07-07</ns2:oeh_id>\n" + 
	"               <ns2:oeh_naam>GBA</ns2:oeh_naam>\n" + 
	"            </ns2:Zaak_verantwoordelijke_oeh>\n" + 
	"            <ns2:Geen_zaak_initiator>true</ns2:Geen_zaak_initiator>\n" + 
	"            <ns2:Zaakidentificatie>TMD.09.10.00011</ns2:Zaakidentificatie>\n" + 
	"            <ns2:Startdatum>2009-10-27T16:00:11.000+01:00</ns2:Startdatum>\n" + 
	"            <ns2:Zaaktypecode>TMDG</ns2:Zaaktypecode>\n" + 
	"            <ns2:Zaaktypeomschrijving>Terugmelding Rotterdamse kerngegevens</ns2:Zaaktypeomschrijving>\n" + 
	"            <ns2:Einddatum xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Einddatumgepland xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Zaakomschrijving xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Kenmerk>\n" + 
	"               <ns2:kenmerk>GBA</ns2:kenmerk>\n" + 
	"               <ns2:kenmerkBron>basisRegistratie</ns2:kenmerkBron>\n" + 
	"            </ns2:Kenmerk>\n" + 
	"            <ns2:Kenmerk>\n" + 
	"               <ns2:kenmerk>126946036</ns2:kenmerk>\n" + 
	"               <ns2:kenmerkBron>objectIdentificatie</ns2:kenmerkBron>\n" + 
	"            </ns2:Kenmerk>\n" + 
	"            <ns2:Kenmerk>\n" + 
	"               <ns2:kenmerk>01</ns2:kenmerk>\n" + 
	"               <ns2:kenmerkBron>objectTag</ns2:kenmerkBron>\n" + 
	"            </ns2:Kenmerk>\n" + 
	"			 <ns2:Trefwoord>Enno-2009-10-27-007</ns2:Trefwoord>\n" +
	"            <ns2:Resultaatcode>gemeld</ns2:Resultaatcode>\n" + 
	"            <ns2:Resultaatomschrijving>De terugmelding is gemeld aan de bronhouder</ns2:Resultaatomschrijving>\n" + 
	"            <ns2:Resultaattoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Zaaktoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Uiterlijkeeinddatumafdoening xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" + 
	"            <ns2:Oge_id>0</ns2:Oge_id>\n" + 
	"         </ns2:Zaak>\n" + 
	"      </ns2:ZaakQueryResponse>\n" + 
	"   </env:Body>\n" + 
	"</env:Envelope>";
	
	String tagPath = "/Envelope/Body/ZaakQueryResponse/Zaak/Zaakidentificatie";
	/**
	 * Test method for {@link nl.rotterdam.rtmf.guc.common.DocumentParser#getAllValues(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetAllValues() {
		List<String> result = DocumentParser.getAllValues(payloadAsString, tagPath);
		assertEquals(2, result.size());
		assertEquals("TMD.09.10.00010", result.get(0));
		assertEquals("TMD.09.10.00011", result.get(1));
	}
}
