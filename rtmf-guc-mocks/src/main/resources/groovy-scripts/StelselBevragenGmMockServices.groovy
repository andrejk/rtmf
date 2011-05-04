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
                                                                       
def final NOTHING = "No result"

def logger = Logger.getLogger("rtmfguc.mock.gm.stelsel")

String result = NOTHING
String payloadAsString = payload

def finder = (payloadAsString =~ /<.*MessageID.*>(.*)<\/.*MessageID>/)
// "expect one result: one messageID"
assert finder.count == 1, "StelselBevragenGmMockServices: de finder heeft geen match gevonden"
// finder[0][0] is the whole message
String wsaMessageId = finder[0][1] 

if (logger.isDebugEnabled()) {
   def text = new StringBuilder("*** properties incoming message ***\n")
   message.getPropertyNames().each { propertyName ->
       text.append "${propertyName}:${message.getProperty(propertyName)}\n"
   }
   text.append "***  incoming message: ***\n"
   text.append payload
   text.append "\n"
   logger.debug text
}
if (payload =~ /<.*getBasisregistratieList.*>/) {
	logger.info "match getBasisregistratieList"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>getBasisregistratieList</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
           <soapenv:Body>
               <stelgm:getBasisregistratieListResponse xmlns:stelgm="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
                  <!--Zero or more repetitions:-->
                  <stelgm:basisregistratieList>
                     <!-- assupmtie: zelfde tag voor zelfde registratie in TMF en GM -->
                     <stelgm:tag>TMF-REG1</stelgm:tag>
                     <stelgm:naam>Personen (GM)</stelgm:naam>
                  </stelgm:basisregistratieList>
                  <stelgm:basisregistratieList>
                     <stelgm:tag>GM-REG2</stelgm:tag>
                     <stelgm:naam>Havens (GM)</stelgm:naam>
                  </stelgm:basisregistratieList>
                  <stelgm:basisregistratieList>
                     <stelgm:tag>GBA</stelgm:tag>
                     <stelgm:naam>Gemeentelijke Basisregistratie Persoonsgegevens</stelgm:naam>
                  </stelgm:basisregistratieList>
               </stelgm:getBasisregistratieListResponse>
            </soapenv:Body>
         </soapenv:Envelope>    
   """
}
else if (payload =~ /<.*getObjectTypeList.*>/ && payload.contains("TMF-REG1")) {
	logger.info "match getObjectTypeList & TMF-REG1"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stelgm="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeList</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
            <soapenv:Body>
            <stelgm:getObjectTypeListResponse>
               <!--Zero or more repetitions:-->
               <stelgm:objectTypeList>
                  <stelgm:tag>TMF-PERSOON</stelgm:tag>
                  <stelgm:naam>Persoon</stelgm:naam>
                  <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
                  <stelgm:instructie>TEST-INSTRUCTIE 1</stelgm:instructie>
               </stelgm:objectTypeList>
               <stelgm:objectTypeList>
                  <stelgm:tag>TMF-GEBOUW</stelgm:tag>
                  <stelgm:naam>Gebouw</stelgm:naam>
                  <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
                  <stelgm:instructie>TEST-INSTRUCTIE 2</stelgm:instructie>
               </stelgm:objectTypeList>
               <stelgm:objectTypeList>
                  <stelgm:tag>TMF-VERGUNNING</stelgm:tag>
                  <stelgm:naam>Vergunning</stelgm:naam>
                  <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
                  <stelgm:instructie>TEST-INSTRUCTIE 3</stelgm:instructie>
               </stelgm:objectTypeList>
               <stelgm:objectTypeList>
                  <stelgm:tag>GM-VERGUNNING</stelgm:tag>
                  <stelgm:naam>Gemeentelijke Vergunning</stelgm:naam>
                  <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
               <stelgm:instructie>TEST-INSTRUCTIE 4</stelgm:instructie>
            </stelgm:objectTypeList>
            </stelgm:getObjectTypeListResponse>
         </soapenv:Body>
      </soapenv:Envelope>
   """
}
else if (payload =~ /<.*getObjectTypeList.*>/ && payload.contains("GM-REG2")) {
	logger.info "match getObjectTypeList & GM-REG2"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stelgm="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeList</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
           <soapenv:Body>
            <stelgm:getObjectTypeListResponse>
               <!--Zero or more repetitions:-->
               <stelgm:objectTypeList>
                  <stelgm:tag>TMF-VRACHTWAGEN</stelgm:tag>
                  <stelgm:naam>Vrachtwagen</stelgm:naam>
                  <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
                  <stelgm:instructie>TEST-INSTRUCTIE 2</stelgm:instructie>
               </stelgm:objectTypeList>
               <stelgm:objectTypeList>
                  <stelgm:tag>TMF-BUS</stelgm:tag>
                  <stelgm:naam>Bus</stelgm:naam>
                  <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
                  <stelgm:instructie>TEST-INSTRUCTIE 3</stelgm:instructie>
               </stelgm:objectTypeList>
               <stelgm:objectTypeList>
	               <stelgm:tag>GM-BOOT</stelgm:tag>
	               <stelgm:naam>Rotterdamse-BOOT</stelgm:naam>
	               <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
	               <stelgm:instructie>TEST-INSTRUCTIE 4</stelgm:instructie>
            </stelgm:objectTypeList>
            </stelgm:getObjectTypeListResponse>
         </soapenv:Body>
      </soapenv:Envelope>
    """
}
else if (payload =~ /<.*getObjectTypeList.*>/ && payload.contains("GBA")) {
	logger.info "match getObjectTypeList & GBA"
    result = """
<soapenv:Envelope xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stelgm="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
  <soapenv:Header>
    <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
    <wsa:MessageID>uuid:f9e3044f-37d0-4473-8d95-8c40f213bb9a</wsa:MessageID>
    <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeListResponse</wsa:Action>
    <wsa:RelatesTo>uuid:3b1e4e3a-df90-456a-abfe-7c27dd0ab73a</wsa:RelatesTo>
  </soapenv:Header>
  <soapenv:Body>
    <stelgm:getObjectTypeListResponse>
      <stelgm:objectTypeList>
        <stelgm:tag>01</stelgm:tag>
        <stelgm:naam>Natuurlijk Persoon</stelgm:naam>
        <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
        <stelgm:instructie></stelgm:instructie>
      </stelgm:objectTypeList>
      <stelgm:objectTypeList>
        <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
        <stelgm:instructie/>
        <stelgm:naam>Verblijfplaats</stelgm:naam>
        <stelgm:tag>08</stelgm:tag>
      </stelgm:objectTypeList>
   </stelgm:getObjectTypeListResponse>
  </soapenv:Body>
</soapenv:Envelope>
"""
}
else if (payload =~ /<.*getObjectTypeList.*>/) {
	logger.info "match getObjectTypeList"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stelgm="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeList</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
           <soapenv:Body>
            <stelgm:getObjectTypeListResponse>
               <!--Zero or more repetitions:-->
               <stelgm:objectTypeList>
                  <stelgm:tag>GM-AUTO</stelgm:tag>
                  <stelgm:naam>Auto</stelgm:naam>
                  <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
                  <stelgm:instructie>TEST-INSTRUCTIE 1</stelgm:instructie>
               </stelgm:objectTypeList>
               <stelgm:objectTypeList>
                  <stelgm:tag>GM-VRACHTWAGEN</stelgm:tag>
                  <stelgm:naam>Vrachtwagen</stelgm:naam>
                  <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
                  <stelgm:instructie>TEST-INSTRUCTIE 2</stelgm:instructie>
               </stelgm:objectTypeList>
            </stelgm:getObjectTypeListResponse>
         </soapenv:Body>
      </soapenv:Envelope>
    """
}
else if (payload =~ /<.*getObjectInfo.*>/ && payload.contains("GM-REG2")) {
	// TODO: maak hier een goede test case van: GM-REG2 is een registratie
	// die alleen in Rotterdam voorkomt en niet in landelijk(?)
	logger.info "match getObjectInfo & GM-REG2"
    result = """
     <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stelgm="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
        <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfo</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
        </soapenv:Header>
        <soapenv:Body>
            <stelgm:getObjectInfoResponse>
               <stelgm:objectInfo>
                  <stelgm:ObjectHeaderInfo>
                     <stelgm:tag>GM-PERSOON</stelgm:tag>
                     <stelgm:naam>Persoon</stelgm:naam>
                     <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
                     <stelgm:instructie>TEST-INSTRUCTIE</stelgm:instructie>
                  </stelgm:ObjectHeaderInfo>
                  <!--Zero or more repetitions:-->
                  <stelgm:attributen>
                     <stelgm:code>GM-PERSOON-VOORNAAM</stelgm:code>
                     <stelgm:naam>Voornaam</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>?</stelgm:stufpath>
                  </stelgm:attributen>
                  <stelgm:attributen>
                     <stelgm:code>GM-PERSOON-TUSSENVOEGSEL</stelgm:code>
                     <stelgm:naam>Tussenvoegsel</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>?</stelgm:stufpath>
                  </stelgm:attributen>
                  <stelgm:attributen>
                     <stelgm:code>GM-PERSOON-ACHTERNAAM</stelgm:code>
                     <stelgm:naam>Achternaam</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>?</stelgm:stufpath>
                  </stelgm:attributen>
                  <stelgm:attributen>
                     <stelgm:code>GM-PERSOON-BIJNAAM</stelgm:code>
                     <stelgm:naam>Rotterdamse Bijnaam</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>?</stelgm:stufpath>
                  </stelgm:attributen>
               </stelgm:objectInfo>
            </stelgm:getObjectInfoResponse>
         </soapenv:Body>
      </soapenv:Envelope>"""}
else if ( payload.contains("getObjectInfo") && payload.contains("GBA") && payload.contains("ObjectTag>08"))
{	logger.info "match match getObjectInfo GBA ObjectTag>08"
	result = """
		<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
	   <soapenv:Header xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
	      <wsa:MessageID xmlns:wsa="http://www.w3.org/2005/08/addressing">${wsaMessageId}</wsa:MessageID>
	   </soapenv:Header>
	   <soap:Body>
	      <getObjectInfoResponse>
	         <objectInfo>
	            <ObjectHeaderInfo>
	               <bevraagbaar>true</bevraagbaar>
	               <instructie/>
	               <naam>Verblijfplaats</naam>
	               <tag>08</tag>
	            </ObjectHeaderInfo>
	            <attributen>
	               <code>08.85.10</code>
	               <gegevenstype/>
	               <naam>Ingangsdatum geldigheid</naam>
	               <stufpath>/prs/body/object/tijdvakGeldigheid/beginGeldigheid;/prs/body/object/heeftAlsCorrespondentieAdres/tijdvakRelatie/beginRelatie;/prs/body/object/heeftAlsInschrijvingsAdres/tijdvakRelatie/beginRelatie;/prs/body/object/heeftAlsVerblijfsAdres/tijdvakRelatie/beginRelatie;/prs/body/object/heeftAlsCorrespondentieAdres/tijdvakRelatie/beginRelatie</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.11.40</code>
	               <gegevenstype/>
	               <naam>Huisnummertoevoeging</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/gerelateerde/straatHuisnummerGrp/huisnummertoevoeging;/prs/body/object/heeftAlsInschrijvingsAdres/gerelateerde/straatHuisnummerGrp/huisnummertoevoeging;/prs/body/object/heeftAlsVerblijfsAdres/gerelateerde/straatHuisnummerGrp/huisnummertoevoeging</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.12.10</code>
	               <gegevenstype/>
	               <naam>Locatiebeschrijving</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/gerelateerde/straatHuisnummerGrp/locatieOmschrijving;/prs/body/object/heeftAlsInschrijvingsAdres/gerelateerde/straatHuisnummerGrp/locatieOmschrijving;/prs/body/object/heeftAlsVerblijfsAdres/gerelateerde/straatHuisnummerGrp/locatieOmschrijving</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.72.10</code>
	               <gegevenstype/>
	               <naam>Omschrijving van de aangifte adreshouding</naam>
	            </attributen>
	            <attributen>
	               <code>08.10.30</code>
	               <gegevenstype/>
	               <naam>Datum aanvang adreshouding</naam>
	            </attributen>
	            <attributen>
	               <code>08.83.30</code>
	               <gegevenstype/>
	               <naam>Datum einde onderzoek</naam>
	               <stufpath>/prs/body/object/extraElementen/extraElement[@naam="Datumeindeonderzoekadres"];/prs/body/object/heeftAlsCorrespondentieAdres/extraElementen/extraElement[@naam="Datumeindeonderzoekadres"];/prs/body/object/heeftAlsInschrijvingsAdres/extraElementen/extraElement[@naam="Datumeindeonderzoekadres"];/prs/body/object/heeftAlsVerblijfsAdres/extraElementen/extraElement[@naam="Datumeindeonderzoekadres"]</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.09.20</code>
	               <gegevenstype/>
	               <naam>Datum inschrijving</naam>
	               <stufpath>/prs/body/object/datumInschrijvingGemeente</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.83.10</code>
	               <gegevenstype/>
	               <naam>Aanduiding gegevens in onderzoek</naam>
	               <stufpath>/prs/body/object/extraElementen/extraElement[@naam="Aanduidingonderzoekadres"];/prs/body/object/heeftAlsCorrespondentieAdres/extraElementen/extraElement[@naam="Aanduidingonderzoekadres"];/prs/body/object/heeftAlsInschrijvingsAdres/extraElementen/extraElement[@naam="Aanduidingonderzoekadres"];/prs/body/object/heeftAlsVerblijfsAdres/extraElementen/extraElement[@naam="Aanduidingonderzoekadres"]</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.86.10</code>
	               <gegevenstype/>
	               <naam>Datum van opneming</naam>
	               <stufpath>/prs/parametersKennisgeving/tijdstipMutatie</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.11.30</code>
	               <gegevenstype/>
	               <naam>Huisletter</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/gerelateerde/straatHuisnummerGrp/huisletter;/prs/body/object/heeftAlsInschrijvingsAdres/gerelateerde/straatHuisnummerGrp/huisletter;/prs/body/object/heeftAlsVerblijfsAdres/gerelateerde/straatHuisnummerGrp/huisletter</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.14.10</code>
	               <gegevenstype/>
	               <naam>Land vanwaar ingeschreven</naam>
	               <stufpath>/prs/body/object/codeLandImmigratie</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.09.10</code>
	               <gegevenstype/>
	               <naam>Gemeente van inschrijving</naam>
	               <stufpath>/prs/body/object/codeGemeenteVanInschrijving</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.11.10</code>
	               <gegevenstype/>
	               <naam>Straatnaam</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/gerelateerde/straatHuisnummerGrp/straatnaam;/prs/body/object/heeftAlsInschrijvingsAdres/gerelateerde/straatHuisnummerGrp/straatnaam;/prs/body/object/heeftAlsVerblijfsAdres/gerelateerde/straatHuisnummerGrp/straatnaam</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.13.10</code>
	               <gegevenstype/>
	               <naam>Land waarnaar vertrokken</naam>
	               <stufpath>/prs/body/object/codeLandEmigratie</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.11.20</code>
	               <gegevenstype/>
	               <naam>Huisnummer</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/gerelateerde/straatHuisnummerGrp/huisnummer;/prs/body/object/heeftAlsInschrijvingsAdres/gerelateerde/straatHuisnummerGrp/huisnummer;/prs/body/object/heeftAlsVerblijfsAdres/gerelateerde/straatHuisnummerGrp/huisnummer</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.11.50</code>
	               <gegevenstype/>
	               <naam>Aanduiding bij huisnummer</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/gerelateerde/straatHuisnummerGrp/aanduidingBijHuisnummer;/prs/body/object/heeftAlsInschrijvingsAdres/gerelateerde/straatHuisnummerGrp/aanduidingBijHuisnummer;/prs/body/object/heeftAlsVerblijfsAdres/gerelateerde/straatHuisnummerGrp/aanduidingBijHuisnummer</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.13.50</code>
	               <gegevenstype/>
	               <naam>Adres buitenland waarnaar vertrokken 3</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/adresBuitenlandGrp/adresBuitenland3</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.13.20</code>
	               <gegevenstype/>
	               <naam>Datum vertrek uit Nederland</naam>
	               <stufpath>/prs/body/object/datumVertrekUitNederland</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.83.20</code>
	               <gegevenstype/>
	               <naam>Datum ingang onderzoek</naam>
	               <stufpath>/prs/body/object/extraElementen/extraElement[@naam="Datumingangonderzoekadres"];/prs/body/object/heeftAlsCorrespondentieAdres/extraElementen/extraElement[@naam="Datumingangonderzoekadres"];/prs/body/object/heeftAlsInschrijvingsAdres/extraElementen/extraElement[@naam="Datumingangonderzoekadres"];/prs/body/object/heeftAlsVerblijfsAdres/extraElementen/extraElement[@naam="Datumingangonderzoekadres"]</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.14.20</code>
	               <gegevenstype/>
	               <naam>Datum vestiging in Nederland</naam>
	               <stufpath>/prs/body/object/datumVestigingInNederland</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.84.10</code>
	               <gegevenstype/>
	               <naam>Aanduiding strijdigheid / nietigheid</naam>
	            </attributen>
	            <attributen>
	               <code>08.13.30</code>
	               <gegevenstype/>
	               <naam>Adres buitenland waarnaar vertrokken 1</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/adresBuitenlandGrp/adresBuitenland1</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.13.40</code>
	               <gegevenstype/>
	               <naam>Adres buitenland waarnaar vertrokken 2</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/adresBuitenlandGrp/adresBuitenland2</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.11.60</code>
	               <gegevenstype/>
	               <naam>Postcode</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/gerelateerde/postcode;/prs/body/object/heeftAlsInschrijvingsAdres/gerelateerde/postcode;/prs/body/object/heeftAlsVerblijfsAdres/gerelateerde/postcode</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.10.20</code>
	               <gegevenstype/>
	               <naam>Gemeentedeel</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres/gerelateerde/woonplaatsnaam;/prs/body/object/heeftAlsInschrijvingsAdres/gerelateerde/woonplaatsnaam;/prs/body/object/heeftAlsVerblijfsAdres/gerelateerde/woonplaatsnaam</stufpath>
	            </attributen>
	            <attributen>
	               <code>08.10.10</code>
	               <gegevenstype/>
	               <naam>Functieadres</naam>
	               <stufpath>/prs/body/object/heeftAlsCorrespondentieAdres@entiteittype;/prs/body/object/heeftAlsInschrijvingsAdres@entiteittype;/prs/body/object/heeftAlsVerblijfsAdres@entiteittype</stufpath>
	            </attributen>
	         </objectInfo>
	      </getObjectInfoResponse>
	   </soap:Body>
	</soap:Envelope>
	"""
}
else if (payload =~ /<.*getObjectInfo.*>/ && payload.contains("GBA")) {
	logger.info "match getObjectInfo & GBA"
    result = """
    	<soapenv:Envelope xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
	<soapenv:Header>
		<wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoResponse</wsa:Action>
		<wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
		<wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
	</soapenv:Header>
	<soapenv:Body>
		<getObjectInfoResponse xmlns="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
			<objectInfo>
				<ObjectHeaderInfo>
					<tag>01</tag>
					<naam>Natuurlijk Persoon</naam>
					<bevraagbaar>true</bevraagbaar>
					<instructie/>
				</ObjectHeaderInfo>
				<attributen>
					<code>01.01.10</code>
					<naam>A-nummer persoon</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/a-nummer</stufpath>
                </attributen>
				<attributen>
					<code>01.01.20</code>
					<naam>Burgerservicenummer persoon</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/bsn</stufpath>
				</attributen>
				<attributen>
					<code>01.02.10</code>
					<naam>Voornamen persoon</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/naamPrsGrp/voornamen</stufpath>
				</attributen>
				<attributen>
					<code>01.02.20</code>
					<naam>Adellijke titel/predikaat pers</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.02.30</code>
					<naam>Voorvoegsel geslachtsnaam pers</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/naamPrsGrp/voorvoegselGeslachtsnaam</stufpath>
				</attributen>
				<attributen>
					<code>01.02.40</code>
					<naam>Geslachtsnaam persoon</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/naamPrsGrp/geslachtsnaam</stufpath>
				</attributen>
				<attributen>
					<code>01.03.10</code>
					<naam>Geboortedatum persoon</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/geboortedatum</stufpath>
				</attributen>
				<attributen>
					<code>01.03.20</code>
					<naam>Geboorteplaats persoon</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/geboorteplaatsGrp/geboorteplaats</stufpath>
				</attributen>
				<attributen>
					<code>01.03.30</code>
					<naam>Geboorteland persoon</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/codeGeboorteland</stufpath>
				</attributen>
				<attributen>
					<code>01.04.10</code>
					<naam>Geslachtsaanduiding</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/geslachtsaanduiding</stufpath>
				</attributen>
				<attributen>
					<code>01.20.10</code>
					<naam>Vorig A-nummer</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.20.20</code>
					<naam>Volgend A-nummer</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.61.10</code>
					<naam>Aanduiding naamgebruik</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/aanduidingNaamgebruik</stufpath>
				</attributen>
				<attributen>
					<code>01.81.10</code>
					<naam>Registergemeente akte waaraan </naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.81.20</code>
					<naam>Aktenummer van de akte waaraan</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.82.10</code>
					<naam>Gemeente waar de gegevens over</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/codeGemeenteVanInschrijving</stufpath>
				</attributen>
				<attributen>
					<code>01.82.20</code>
					<naam>Datum van de ontlening van de </naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>/prs/body/object/datumInschrijvingGemeente</stufpath>
				</attributen>
				<attributen>
					<code>01.82.30</code>
					<naam>Beschrijving van het document </naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.83.10</code>
					<naam>Aanduiding gegevens in onderzo</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.83.20</code>
					<naam>Datum ingang onderzoek</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.83.30</code>
					<naam>Datum einde onderzoek</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.84.10</code>
					<naam>Indicatie onjuist dan wel stri</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.85.10</code>
					<naam>Ingangsdatum geldigheid met be</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.86.10</code>
					<naam>Datum van opneming met betrekk</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
				<attributen>
					<code>01.86.11</code>
					<naam>Rotterdamse Bijnaam</naam>
                    <gegevenstype>?</gegevenstype>
                    <stufpath>?</stufpath>
				</attributen>
			</objectInfo>
		</getObjectInfoResponse>
	</soapenv:Body>
</soapenv:Envelope>"""
}
else if (payload =~ /<.*getObjectInfo.*>/) {
	logger.info "match getObjectInfo"
    result = """
     <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stelgm="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
    	<soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
    		<wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfo</wsa:Action>
        	<wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
        </soapenv:Header>
     	<soapenv:Body>
            <stelgm:getObjectInfoResponse>
               <stelgm:objectInfo>
                  <stelgm:ObjectHeaderInfo>
                     <stelgm:tag>TMF-PERSOON</stelgm:tag>
                     <stelgm:naam>Persoon</stelgm:naam>
                     <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
                     <stelgm:instructie>TEST-INSTRUCTIE</stelgm:instructie>
                  </stelgm:ObjectHeaderInfo>
                  <!--Zero or more repetitions:-->
                  <stelgm:attributen>
                     <stelgm:code>TMF-PERSOON-VOORNAAM</stelgm:code>
                     <stelgm:naam>Voornaam</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>/prs/body/object/naamPrsGrp/voornamen</stelgm:stufpath>
                  </stelgm:attributen>
                  <stelgm:attributen>
                     <stelgm:code>TMF-PERSOON-TUSSENVOEGSEL</stelgm:code>
                     <stelgm:naam>Tussenvoegsel</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>/prs/body/object/naamPrsGrp/voorvoegselGeslachtsnaam</stelgm:stufpath>
                  </stelgm:attributen>
                  <stelgm:attributen>
                     <stelgm:code>TMF-PERSOON-ACHTERNAAM</stelgm:code>
                     <stelgm:naam>Achternaam</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>/prs/body/object/naamPrsGrp/geslachtsnaam</stelgm:stufpath>
                  </stelgm:attributen>
                  <stelgm:attributen>
                  	 <stelgm:code>GM-PERSOON-BIJNAAM</stelgm:code>
                  	 <stelgm:naam>Rotterdamse Bijnaam</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>/prs/body/object/heeftAlsInschrijvingsAdres/gerelateerde/straatHuisnummerGrp/straatnaam</stelgm:stufpath>
                  </stelgm:attributen>
               </stelgm:objectInfo>
            </stelgm:getObjectInfoResponse>
         </soapenv:Body>
      </soapenv:Envelope>
    """
}
else if (payload =~ /<.*bevragen.*>/) {
	logger.info "match bevragen (deze komt te vervallen?!)"
    result = """
     <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stelgm="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd">
  	    <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
		   <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/bevragen</wsa:Action>
    	   <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
           <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
       </soapenv:Header>
	   <soapenv:Body>
	      <stelgm:bevragenResponse>
		 <stelgm:objectinstantie>
	            <stelgm:objectInfo>
	               <stelgm:tag>TMF-PERSOON</stelgm:tag>
	               <stelgm:naam>Persoon</stelgm:naam>
	               <stelgm:bevraagbaar>true</stelgm:bevraagbaar>
	               <stelgm:instructie>TEST-INSTRUCTIE</stelgm:instructie>
	            </stelgm:objectInfo>
	            <!--Zero or more repetitions:-->
	            <stelgm:attributeValues>
	               <stelgm:attribuutInfo>
	                 <stelgm:code>TMF-PERSOON-VOORNAAM</stelgm:code>
	                 <stelgm:naam>Voornaam</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>?</stelgm:stufpath>
	               </stelgm:attribuutInfo>
	               <stelgm:value>Petet</stelgm:value>
	            </stelgm:attributeValues>
	            <stelgm:attributeValues>
	               <stelgm:attribuutInfo>
	                 <stelgm:code>TMF-PERSOON-TUSSENVOEGSEL</stelgm:code>
	                 <stelgm:naam>Tussenvoegsel</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>?</stelgm:stufpath>
	               </stelgm:attribuutInfo>
	               <stelgm:value>van der</stelgm:value>
	            </stelgm:attributeValues>
	            <stelgm:attributeValues>
	               <stelgm:attribuutInfo>
	                 <stelgm:code>TMF-PERSOON-ACHTERNAAM</stelgm:code>
	                 <stelgm:naam>Achternaam</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>?</stelgm:stufpath>
	               </stelgm:attribuutInfo>
	               <stelgm:value>Baker</stelgm:value>
	            </stelgm:attributeValues>
	            <stelgm:attributeValues>
	               <stelgm:attribuutInfo>
	                 <stelgm:code>GM-PERSOON-BIJNAAM</stelgm:code>
	                 <stelgm:naam>Rotterdamse Bijnaam</stelgm:naam>
                     <stelgm:gegevenstype>?</stelgm:gegevenstype>
                     <stelgm:stufpath>?</stelgm:stufpath>
	               </stelgm:attribuutInfo>
	               <stelgm:value>Bak</stelgm:value>
	            </stelgm:attributeValues>
	         </stelgm:objectinstantie>
	      </stelgm:bevragenResponse>
	   </soapenv:Body>
	 </soapenv:Envelope>
	"""
}

assert result != NOTHING

logger.debug "reply: ${result}"

return result