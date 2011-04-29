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

def logger = Logger.getLogger("rtmfguc.mock.zm.terugmelden")

def String result = NOTHING
def String payloadAsString = payload

def finder = (payloadAsString =~ /<.*MessageID.*>(.*)<\/.*MessageID>/)
// "expect one result: one messageID"
assert  finder.count == 1, "TerugmeldenZmMockServices: de finder heeft geen match gevonden"
// finder[0][0] is the whole message
def String wsaMessageId = finder[0][1] 

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
    	
if (payload =~ /<.*terugmelding.*>/) {
	logger.info "match terugmelding"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
            xmlns:defrep="http://tmfportal.ovsoftware.com/services/defaultreply.xsd">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
            <soapenv:Body>
               <defrep:response>
                  <text>OK</text>
               </defrep:response>
            </soapenv:Body>
         </soapenv:Envelope>
    """
}

if (payload =~ /<.*intrekking.*>/) {
	logger.info "match intrekking"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
           xmlns:defrep="http://tmfportal.ovsoftware.com/services/defaultreply.xsd">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/intrekking</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
            <soapenv:Body>
               <defrep:response>
                  <text>OK</text>
               </defrep:response>
            </soapenv:Body>
         </soapenv:Envelope>
    """
}

assert result != NOTHING

logger.debug "reply: ${result}"
	
return result