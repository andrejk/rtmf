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
/*
 * Dit script voegt de resultaten van actuele waarden van GM en de objectType van 
 * stelselcatalogus samen tot 1 actueleWaarden response in het formaat van de TMFPortaal. 
 */
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.QName
import org.mule.transport.NullPayload
import org.apache.log4j.Logger

def logger = Logger.getLogger("rtmfguc.script.MergeActueleWaarden")

// use "script binding" voor global constants: kunnen in het script en methodes worden gebruikt
STEL_NS_URI = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd"
STELGM_NS_URI = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd"
SOAP_NS_URI = "http://schemas.xmlsoap.org/soap/envelope/"

assert payload[0] != null : "Eerste payload is null"
assert payload[1] != null : "Tweede payload is null"

logger.debug """
==================================================================================================
En hier zijn de payloads
==================================================================================================
Payload[0]: ${payload[0].getPayloadAsString()}"
Payload[1]: ${payload[1].getPayloadAsString()}"
=================================================================================================="""

def xmlParser = new XmlParser()
def stel = new groovy.xml.Namespace(STEL_NS_URI, 'stel')
def stelgm = new groovy.xml.Namespace(STELGM_NS_URI, 'stelgm')
def soapenv = new groovy.xml.Namespace(SOAP_NS_URI, 'soapenv')
xmlParser.setTrimWhitespace(true)

String bericht1 = payload[0].getPayloadAsString()
String bericht2 = payload[1].getPayloadAsString()
def tmfDocument = xmlParser.parseText(getTMFBericht(bericht1, bericht2))
def gmDocument = xmlParser.parseText(getGMBericht(bericht1, bericht2))

//Voeg alle GM attributen toe aan het tmf document
gmDocument[soapenv.Body][stelgm.bevragenResponse][stelgm.objectinstantie][stelgm.attributeValues].each { attribuut ->
	//logger.debug "GM attribuut '${attribuut[stelgm.attribuutInfo][stelgm.code].text()}'"
	if (tmfDocument[soapenv.Body][stel.bevragenResponse][stel.objectinstantie].'**'.find{it[stel.attribuutInfo][stel.code].text() == attribuut[stelgm.attribuutInfo][stelgm.code].text()} == null) {
		logger.debug "Er wordt een attribuut '${attribuut}' toegevoegd"
		tmfDocument[soapenv.Body][stel.bevragenResponse][stel.objectinstantie][0].children().add(attribuut)
	} else { 
		logger.debug "Gevonden in TMF document !!!" 
		logger.debug "Stuf waarden kopieren ..."
		def tmfAttVal = tmfDocument[soapenv.Body][stel.bevragenResponse][stel.objectinstantie].'**'.find{it[stel.attribuutInfo][stel.code].text() == attribuut[stelgm.attribuutInfo][stelgm.code].text()}
		if (tmfAttVal!=null) {
			logger.debug "TMF attribute gevonden, kopieren maar"
			logger.debug "TMF waarde : ${tmfAttVal[stel.value].text()}"
			logger.debug "Stuf waarde: ${attribuut[stelgm.value].text()}"
			tmfAttVal[stel.value][0].value = attribuut[stelgm.value].text()
		}
	}
}

//vervang IOONamespace voor de landelijke namespace
String prefix = achterhaalPrefix(tmfDocument)
logger.debug "Namespace tmf: ${prefix}"

vervangIOONamespace(gmDocument, prefix) 

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
xmlNodePrinter.print(tmfDocument)
def result = writer.toString()

// TODO Workaround: de namespace wsa zit alleen op sommige van de wsa elementen die niet genest zijn, zet de wsa
// namespace op de soap Header. Waarschijnlijk gefixt in Groovy 1.6.x
logger.debug "result response without workarounds: ${result}"

//
// Zorg ervoor dat de soapenv namespace aanwezig is
//
if (!(result =~ /<.*xmlns:soapenv=.*>/)) {
  logger.debug "Toevoegen 'xmlns:soapenv=' namespace"
  result = result.replace('<soapenv:Envelope', '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" ')
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
// TODO Workaround: can be removed when Groovy in Mule is updated to 1.6.x
//
def replaced = result.replaceAll("\r", "").replaceAll("\n", "")

// De waarde "Onbekend" mag niet in het uiteindelijke resultaat komen. Deze vervangen we voor een ""
replaced = replaced.replaceAll("value>Onbekend</", "value></")

logger.debug "Terugmeld response merge result: ${result}"
logger.debug "Terugmeld response merge replaced (workaround with no newlines/whitespace): ${replaced}"

return replaced

def getTMFBericht(String bericht1, String bericht2) {
    return bericht1.contains(STEL_NS_URI) ? bericht1: bericht2
}

def getGMBericht(String bericht1, String bericht2) {
    return bericht1.contains(STELGM_NS_URI) ? bericht1 : bericht2
}

def vervangIOONamespace(node, prefix) {
    if (node instanceof Node && node.name() instanceof QName) {
    	// TODO let op hard coded string!
        if (node.name().toString().indexOf('stelselBevragen-V1.1-IOO.xsd') != -1) {
            node.name = new QName(STEL_NS_URI, node.name().getLocalPart(), prefix)
        }
        node.children().each {
        	vervangIOONamespace(it, prefix)
        }
    }
}

/**
 * Achterhaal de prefix van het element bevragenResponse zodat 
 * deze gebruikt kan worden om de prefix van IOO te vervangen
 */
def achterhaalPrefix(node) {
	def logger = Logger.getLogger("rtmfguc.script.MergeActueleWaarden")
    if (node instanceof Node && node.name() instanceof QName) {
		String localPart = node.name().getLocalPart() 
		logger.debug "achterhaalNamespace: ${localPart}"
        if (localPart.equals('bevragenResponse')) {
        	logger.debug "result achterhaalNamespace: ${node.name().getPrefix()}"
            return node.name().getPrefix();
        }
		
	        for (Object it : node.children()) {
	        	String s = achterhaalPrefix(it)
				logger.debug "na recursie binnen ${localPart} result: ${s}"
	            if (s != null) {
	            	return s
	            }
	        }
		
    }
}