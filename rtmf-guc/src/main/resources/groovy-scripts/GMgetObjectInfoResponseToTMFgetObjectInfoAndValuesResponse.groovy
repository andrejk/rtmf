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

def logger = Logger.getLogger("rtmfguc.script.GMgetObjectInfoResponseToTMFgetObjectInfoAndValuesResponse")

// use "script binding" voor global constants: kunnen in het script en methodes worden gebruikt
STEL_NS_URI =  "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd"
STELL_NS_URI = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.2.xsd"
STELGM_NS_URI = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd"
SOAP_NS_URI = "http://schemas.xmlsoap.org/soap/envelope/"

def xmlParser = new XmlParser()
xmlParser.setTrimWhitespace(true)
def stel = new groovy.xml.Namespace(STEL_NS_URI, 'stel')
def stell = new groovy.xml.Namespace(STELL_NS_URI, 'stell')
def stelgm = new groovy.xml.Namespace(STELGM_NS_URI, 'stelgm')
def soapenv = new groovy.xml.Namespace(SOAP_NS_URI, 'soapenv')
logger.debug "payload: " + payload
def gmBericht = payload
def gmDocument = xmlParser.parseText(gmBericht)

// verwijder IOONamespace
verwijderIOONamespace(gmDocument,'stelselBevragen-V1.1-IOO.xsd','http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd','stel')

//ObjectInfoAndValue
if (gmDocument[soapenv.Body][stel.getObjectInfoResponse].size() == 1) {
	logger.debug "transform getObjectInfoResponse to getObjectInfoAndValuesResponse"
	verwijderIOONamespace(gmDocument,'stelselBevragen-V1.1.xsd','http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.2.xsd','ns2')
	
	node = gmDocument[soapenv.Body][stell.getObjectInfoResponse][stell.objectInfo][stell.ObjectHeaderInfo][stell.tag][0]
	node.name = new QName(node.name().getLocalPart());
	node = gmDocument[soapenv.Body][stell.getObjectInfoResponse][stell.objectInfo][stell.ObjectHeaderInfo][stell.naam][0]
	node.name = new QName( node.name().getLocalPart());
	node = gmDocument[soapenv.Body][stell.getObjectInfoResponse][stell.objectInfo][stell.ObjectHeaderInfo][stell.bevraagbaar][0]
	node.name = new QName(node.name().getLocalPart());
	node = gmDocument[soapenv.Body][stell.getObjectInfoResponse][stell.objectInfo][stell.ObjectHeaderInfo][stell.instructie][0]
	node.name = new QName(node.name().getLocalPart());
    
	node = gmDocument[soapenv.Body][stell.getObjectInfoResponse][0];
	node.name = new QName(node.name().getNamespaceURI(), 'getObjectInfoAndValuesResponse', node.name().getPrefix());
} else {
	return payload;
}

// generate output XML
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
xmlNodePrinter.print(gmDocument)
def result = writer.toString()

// Workaround: de namespace wsa zit alleen op sommige van de wsa elementen die niet genest zijn, zet de wsa
// namespace op de soap Header. Waarschijnlijk gefixt in Groovy 1.6.x
result = result.replaceAll("\r", "").replaceAll("\n", "")
result = result.replace('<soapenv:Header>', '<soapenv:Header xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wsa="http://www.w3.org/2005/08/addressing">')
result = result.replace('<wsa:Action xmlns:wsa="http://www.w3.org/2005/08/addressing">http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoResponse</wsa:Action>', '<wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoAndValuesResponse</wsa:Action>')
// fix for ticket #98 - Gecombineerde metadata bevat alleen voor GM een stel xml namespace prefix
result = result.replace('<ns2:getObjectInfoAndValuesResponse', '<ns2:getObjectInfoAndValuesResponse xmlns="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd"')
result = result.replace('<bevraagbaar xmlns="">', '<bevraagbaar>')



logger.debug "merge result: ${result}"

return result

def verwijderIOONamespace(node, checkUri, newUri, newPrefix) {
	def logger = Logger.getLogger("rtmfguc.script.GMgetObjectInfoResponseToTMFgetObjectInfoAndValuesResponse")
	logger.debug "node: " + node;
	if (node instanceof Node && node.name() instanceof QName) {
		logger.debug "node name: " + node.name().toString();
		if (node.name().toString().indexOf(checkUri) != -1) {
			node.name = new QName(newUri,node.name().getLocalPart(), newPrefix)
		}
		node.children().each {
			verwijderIOONamespace(it, checkUri, newUri, newPrefix)
		}
	}
}