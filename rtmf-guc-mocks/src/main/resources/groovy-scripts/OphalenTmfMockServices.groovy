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

def logger = Logger.getLogger("rtmfguc.mock.tmf.ophalen")

String result = NOTHING
String payloadAsString = payload

def finder = (payloadAsString =~ /<.*MessageID.*>(.*)<\/.*MessageID>/)
// "expect one result: one messageID"
assert  finder.count == 1, "OphalenTmfMockServices: de finder heeft geen match gevonden"
// finder[0][0] is the whole message
String wsaMessageId = finder[0][1] 

if (logger.isDebugEnabled()) {
	def text = new StringBuilder("*** properties incoming message ***\n")

	message.getPropertyNames().each { propertyName ->
    	text.append "${propertyName}:${message.getProperty(propertyName)}\n"
	}
	
	text.append "*** incoming message: ***\n"
	text.append payload
	text.append "\n"
	logger.debug text
}
    	
if (payload =~ /<.*ophalenMeldingStatus.*>/ && !payload.contains("meldingKenmerk>123<")) {
	logger.info "match ophalenMeldingStatus, any kenmerk but 123"
		// lege lijst is nodig voor terugmelding (anders: "bestaat al" bij opsturen)
	   result = """
	         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
	            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
	            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService/ophalenMeldingStatusRequest</wsa:Action>
	            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
	            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
	           </soapenv:Header>
		     	 <soapenv:Body>
		  	      <oph:ophalenMeldingStatusResponse xmlns:oph="http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd">
		  	         <oph:statusResponseList>
		  	         </oph:statusResponseList>
		  	      </oph:ophalenMeldingStatusResponse>
		  	     </soapenv:Body>
		  	   </soapenv:Envelope>    
	    """
}	    
else if (payload =~ /<.*ophalenMeldingStatus.*>/ && payload.contains("meldingKenmerk>123<")) {
	logger.info "match ophalenMeldingStatus, kenmerk 123"
	   result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/ophalenService/ophalenMeldingStatusResponse</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
            <soapenv:Body>
               <oph:ophalenMeldingStatusResponse xmlns:oph="http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd">
                  <oph:statusResponseList>
                     <!--Zero or more repetitions:-->
                     <oph:terugmeldResponseList>
                        <oph:terugmeld>
                           <oph:meldingKenmerk>123</oph:meldingKenmerk>
                           <oph:naamContact>PP Bakker</oph:naamContact>
                           <oph:telefoonContact>020-123</oph:telefoonContact>
                           <oph:emailContact>pp@abc</oph:emailContact>
                           <oph:tijdstempelAanlever>2009-08-14T11:29:09.743+02:00</oph:tijdstempelAanlever>
                           <oph:tagBR>TMF-REG1</oph:tagBR>
                           <oph:tagObject>TMF-PERSOON</oph:tagObject>
                           <oph:idObject>123</oph:idObject>
                           <oph:objectAttributen>
                              <!--Zero or more repetitions:-->
                              <oph:objectAttribuutList>
                                 <oph:idAttribuut>TMF-PERSOON-VOORNAAM</oph:idAttribuut>
                                 <oph:betwijfeldeWaarde>Petet</oph:betwijfeldeWaarde>
                                 <oph:voorstel>Peter</oph:voorstel>
                              </oph:objectAttribuutList>
                              <oph:objectAttribuutList>
                                 <oph:idAttribuut>TMF-PERSOON-TUSSENVOEGSEL</oph:idAttribuut>
                                 <oph:betwijfeldeWaarde>van der</oph:betwijfeldeWaarde>
                                 <oph:voorstel></oph:voorstel>
                              </oph:objectAttribuutList>
                              <oph:objectAttribuutList>
                                 <oph:idAttribuut>TMF-PERSOON-ACHTERNAAM</oph:idAttribuut>
                                 <oph:betwijfeldeWaarde>Baker</oph:betwijfeldeWaarde>
                                 <oph:voorstel>Bakker</oph:voorstel>
                              </oph:objectAttribuutList>
                           </oph:objectAttributen>
                           <oph:toelichting>Naam klopt niet</oph:toelichting>
                           <oph:status>ontvangen</oph:status>
                        </oph:terugmeld>
                        <oph:terugmeldMCore>
                           <oph:tmfKenmerk>123</oph:tmfKenmerk>
                           <oph:berichtSoort>Berichtsoort</oph:berichtSoort>
                           <oph:idOrganisatie>idOrganisatie</oph:idOrganisatie>
                           <oph:naamOrganisatie>naamOrganisatie</oph:naamOrganisatie>
                        </oph:terugmeldMCore>
                     </oph:terugmeldResponseList>
                  </oph:statusResponseList>
               </oph:ophalenMeldingStatusResponse>
            </soapenv:Body>
         </soapenv:Envelope>
    """
}

else if (payload =~ /<.*ophalenMeldingKenmerk.*>/) {
    logger.info "match ophalenMeldingKenmerk"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService/ophalenMeldingKenmerk</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
           <soapenv:Body>
            <oph:ophalenMeldingKenmerkResponse xmlns:oph="http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd">
            <oph:meldingMetaData>
               <oph:terugmeldMCore>
                  <oph:tmfKenmerk>123</oph:tmfKenmerk>
                  <oph:berichtSoort>berichtSoort</oph:berichtSoort>
                  <oph:idOrganisatie>OrganisatieId</oph:idOrganisatie>
                  <oph:naamOrganisatie>Organisatie</oph:naamOrganisatie>
               </oph:terugmeldMCore>
               <!--Optional:-->
               <oph:afdeling>Afdeling</oph:afdeling>
               <oph:tijdstempelOntvangst>2009-08-14T11:32:24.743+02:00</oph:tijdstempelOntvangst>
               <!--Optional:-->
               <oph:tijdstempelGemeld>2009-08-14T11:29:09.743+02:00</oph:tijdstempelGemeld>
               <oph:statusMelding>ontvangen</oph:statusMelding>
               <oph:tijdstempelStatus>2009-08-14T15:13:10.743+02:00</oph:tijdstempelStatus>
               <!--Optional:-->
               <oph:toelichting>Wat doen we hier mee?</oph:toelichting>
            </oph:meldingMetaData>
         </oph:ophalenMeldingKenmerkResponse>
            </soapenv:Body>
         </soapenv:Envelope>
    """
}

assert result != NOTHING

logger.debug "reply: ${result}"
	
return result

