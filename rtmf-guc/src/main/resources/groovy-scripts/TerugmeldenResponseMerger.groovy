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

import groovy.xml.StreamingMarkupBuilder
import groovy.xml.QName
import org.mule.transport.NullPayload
import org.apache.log4j.Logger
import nl.rotterdam.rtmf.guc.common.NamespaceFixer

//
// Dit script voegt de resultaten van twee terugmeldingen (een naar de TMF en een naar het ZM0) 
// samen tot een response naar de caller
//
// Helaas is uit de payloads niet te achterhalen vanaf welke service (TMF of ZM) de resultaten komen :-()
//


// Gebruik log4j als logging systeem
def logger = Logger.getLogger("rtmfguc.script.TerugmeldenResponseMerger")
assert payload[0] != null : "Eerste payload is null"
assert payload[1] != null : "Tweede payload is null"
logger.debug "=================================================================================================="
logger.debug "En hier zijn de payloads"
logger.debug "=================================================================================================="
logger.debug "Payload[0]: ${payload[0].getPayloadAsString()}"
logger.debug "Payload[1]: ${payload[1].getPayloadAsString()}"
logger.debug "=================================================================================================="

def xmlParser = new XmlParser()
xmlParser.setTrimWhitespace(true)

String bericht1 = NamespaceFixer.fixResponseNamespace(payload[0].getPayloadAsString())
String bericht2 = NamespaceFixer.fixResponseNamespace(payload[1].getPayloadAsString())

logger.debug "Payload[0]: ${bericht1}"
logger.debug "Payload[1]: ${bericht2}"
def document1 = xmlParser.parseText(bericht1)
def document2 = xmlParser.parseText(bericht2)

//
// Definieer de gebruikte namespaces
//
def soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
def defreply = new groovy.xml.Namespace('http://tmfportal.ovsoftware.com/services/defaultreply.xsd', 'defrep')

//Terugmeld Response
if (document2[soapenv.Body][defreply.response].size() == 1) {
	document2[soapenv.Body][defreply.response].each { response ->
		document1[soapenv.Body][0].children().add(response)
	}
}

//
//generate output XML
//
/*
	TMFPortal doesnt deal with newlines and whitespaces that XmlNodePrinter spits out
    see also: http://jira.codehaus.org/browse/GROOVY-3265
    This is fixed in groovy version 1.6-rc-2 and up
    When Mule is ugraded (http://www.mulesoft.org/jira/browse/MULE-4194), the workaround code can be replaced by:
    	def emptyString = ""
    	def addLines = false
    	new XmlNodePrinter(new IndentPrinter(printWriter, emptyString, addLines))
*/
def writer = new StringWriter()
def printWriter = new PrintWriter(writer)
def emptyString = ""
def xmlNodePrinter = new XmlNodePrinter(printWriter, emptyString)
xmlNodePrinter.print(document1)
def result = writer.toString()

// Workaround: de namespace wsa zit alleen op sommige van de wsa elementen die niet genest zijn, zet de wsa
// namespace op de soap Header. Waarschijnlijk gefixt in Groovy 1.6.x
logger.debug("result response: ${result}")
logger.debug " "

//
// Zorg ervoor dat de soapenv namespace aanwezig is
//
if (!(result =~ /<.*xmlns:soapenv=.*>/)) {
  logger.debug "Toevoegen 'xmlns:soapenv=' namespace"
  result = result.replace('<soapenv:Envelope', '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" ')
}

//
// Zorg ervoor dat de default reply namespace aanwezig is
//
if (!(result =~ /<*soapenv:Body xmlns:defrep=.*>/)) {
	logger.debug("adding defrep to envelope")
	result = result.replace('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"', 
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:defrep="http://tmfportal.ovsoftware.com/services/defaultreply.xsd" ')
	logger.debug("removing defrep from response")
	result = result.replace('defrep:response xmlns:defrep="http://tmfportal.ovsoftware.com/services/defaultreply.xsd"', 'defrep:response')
}

//
// Voeg de wsa addressing toe
//
result = result.replace('<soapenv:Header', '<soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing"')

//
// Zorg ervoor dat het RelationshipType aanwezig is
//
if (!(result =~ /<.*RelationshipType=.*>/)) {
  result = result.replace('<wsa:RelatesTo', '<wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply"')
}

//result = result.replace('<wsa:Action>', '<wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding')
//result = result.replace('<wsa:To>', '<wsa:To>http://www.w3.org/2005/08/addressing/anonymous')

// 
//Workaround: can be removed when Groovy in Mule is updated to 1.6.x
//
def replaced = result.replaceAll("\r", "").replaceAll("\n", "")

logger.debug "Terugmeld response merge result: ${result}"
logger.debug "Terugmeld response merge replaced (workaround with no newlines/whitespace): ${replaced}"

return replaced

