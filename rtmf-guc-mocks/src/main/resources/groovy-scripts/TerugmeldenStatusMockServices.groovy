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
import org.apache.log4j.Logger

final String NOTHING = "No result"

def logger = Logger.getLogger("rtmfguc.mock.tmf.TerugmeldenStatusMockServices")

String result = NOTHING

logger.debug "De binnenkomende payload is: ${payload}"
    	
if (payload =~ /<.*ZaakQuery.*>/) {
	logger.info "match ZaakQuery"
	//
	// Let op: Eventueel kunnen we hier nog een ondersched maken tussen de verschillende
	//         soorten ZaakQueries, door de payload nogmaals te checken en te kijken wat
	//		   soort query er gedaan is, en op basis daarvan een andere response terug te
	//		   geven.
	//
	// b.v.
	//	if (payload =~ /<.*Zaakidentificatie.*>/) {	- Zoeken op zaaknummer
	//  if (payload =~ /<.*Kenmerk.*>/) {			- Zoeken op kenmerk (van de terugmelding)
	//  if (payload =~ /<.*Resultaatcode.*>/) {		- Zoeken op de resultaatcode. Resultaatcode
	//												  staat gelijke aan de status van de terugmelding
	//  if (payload =~ /<.*StartdatumVanaf.*>/) {	- Zoeken op datum
	//
    result = """
    <env:Envelope xmlns:env="http://schemas.xmlsoap.org/soap/envelope/">
	   <env:Header/>
	   <env:Body>
	      <ns2:ZaakQueryResponse xmlns:ns2="http://www.interaccess.nl/webplus/statuswfm_v2">
	         <ns2:Zaak>
	            <ns2:Subject/>
	            <ns2:Geen_zaak_verantwoordelijke>true</ns2:Geen_zaak_verantwoordelijke>
	            <ns2:Geen_zaak_initiator>true</ns2:Geen_zaak_initiator>
	            <ns2:Zaakidentificatie>TMD.09.10.00001</ns2:Zaakidentificatie>
	            <ns2:Startdatum>2009-10-01T09:00:00.000+02:00</ns2:Startdatum>
	            <ns2:Zaaktypecode>TMDG</ns2:Zaaktypecode>
	            <ns2:Zaaktypeomschrijving>Terugmelding Rotterdamse kerngegevens</ns2:Zaaktypeomschrijving>
	            <ns2:Einddatum xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
	            <ns2:Einddatumgepland xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
	            <ns2:Zaakomschrijving xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
	            <ns2:Kenmerk>
	               <ns2:kenmerk>ABC123</ns2:kenmerk>
	               <ns2:kenmerkBron>TMF Portal</ns2:kenmerkBron>
	            </ns2:Kenmerk>
	            <ns2:Resultaatcode>gemeld</ns2:Resultaatcode>
	            <ns2:Resultaatomschrijving>De terugmelding is gemeld aan de bronhouder</ns2:Resultaatomschrijving>
	            <ns2:Resultaattoelichting xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
	            <ns2:Zaaktoelichting xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
	            <ns2:Uiterlijkeeinddatumafdoening xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
	            <ns2:Oge_id>0</ns2:Oge_id>
	            <ns2:Formulier>
	        		<!-- Hier moet het originele Terugmelding bericht staan -->
	            </ns2:Formulier>
	         </ns2:Zaak>
	      </ns2:ZaakQueryResponse>
	   </env:Body>
	</env:Envelope>
    """
}

assert result != NOTHING : "ZaakQuery repsonse Mock geeft geen resultaat!"

logger.debug "reply: ${result}"
	
return result