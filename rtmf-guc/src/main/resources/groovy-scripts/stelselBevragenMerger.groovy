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

def logger = Logger.getLogger("rtmfguc.script.StelselBevragenMerger")

// use "script binding" voor global constants: kunnen in het script en methodes worden gebruikt
STEL_NS_URI =  "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd"
STELL_NS_URI = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.2.xsd"
STELGM_NS_URI = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd"
SOAP_NS_URI = "http://schemas.xmlsoap.org/soap/envelope/"

logger.debug "Payload[0]: ${payload[0].getPayloadAsString()}"
logger.debug "Payload[1]: ${payload[1].getPayloadAsString()}"

//check de berichten
String bericht1 = payload[0].getPayloadAsString()
String bericht2 = payload[1].getPayloadAsString()
checkPayload(bericht1, bericht2)

def xmlParser = new XmlParser()
xmlParser.setTrimWhitespace(true)
def stel = new groovy.xml.Namespace(STEL_NS_URI, 'stel')
def stell = new groovy.xml.Namespace(STELL_NS_URI, 'stell')
def stelgm = new groovy.xml.Namespace(STELGM_NS_URI, 'stelgm')
def soapenv = new groovy.xml.Namespace(SOAP_NS_URI, 'soapenv')
def tmfBericht = getTMFBericht(bericht1, bericht2)
def gmBericht = getGMBericht(bericht1, bericht2)
def tmfDocument = xmlParser.parseText(tmfBericht)
def gmDocument = xmlParser.parseText(gmBericht)

// verwijder IOONamespace
verwijderIOONamespace(gmDocument,'stelselBevragen-V1.1-IOO.xsd','http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd','stel')

// Er kunnen nu 4 soorten berichten binnen komen voor het stelselbevragen.
// BasisregistratieList
if (gmDocument[soapenv.Body][stel.getBasisregistratieListResponse].size() == 1) {
	gmDocument[soapenv.Body][stel.getBasisregistratieListResponse][stel.basisregistratieList].each { basisregistratie ->
		// controleer of dit element al in de TMF response zit
	    if (tmfDocument[soapenv.Body][stel.getBasisregistratieListResponse].'**'.find{it[stel.tag].text() == basisregistratie[stel.tag].text()} == null) {
	        logger.debug "Adding basisregistratie ${basisregistratie}"
	        tmfDocument[soapenv.Body][stel.getBasisregistratieListResponse][0].children().add(basisregistratie)
	    }
	}
}
//ObjectTypeList
if (gmDocument[soapenv.Body][stel.getObjectTypeListResponse].size() == 1) {
	gmDocument[soapenv.Body][stel.getObjectTypeListResponse][stel.objectTypeList].each { objectType ->   
		// controleer of dit element al in de TMF response zit
		if (tmfDocument[soapenv.Body][stel.getObjectTypeListResponse].'**'.find{it[stel.tag].text() == objectType[stel.tag].text()} == null) {
	    	logger.info "Adding objectType with tag ${objectType[stel.tag].text()}"
			tmfDocument[soapenv.Body][stel.getObjectTypeListResponse][0].children().add(objectType)
	    } else if (/* TODO workaround: objectType[stel.bevraagbaar].text().equals('true')*/ true) {
	    	// Als een van beide responses bevraagbaar is zetten we het response op bevraagbaar
	    	logger.info "setting bevraagbaar true for objectType ${objectType[stel.tag].text()}"
	    	tmfDocument[soapenv.Body][stel.getObjectTypeListResponse].'**'.find{it[stel.tag].text() == objectType[stel.tag].text()}[stel.bevraagbaar][0].value = true
	    }
	}
}
//ObjectInfoAndValue
if (gmDocument[soapenv.Body][stel.getObjectInfoResponse].size() == 1 && tmfDocument[soapenv.Body][stell.getObjectInfoAndValuesResponse].size() == 1) {
	logger.debug "ObjectInfoAndValue mergen"
	logger.debug "De prefix van het getObjectInfoAndValuesResponse element is: ${tmfDocument[soapenv.Body][stell.getObjectInfoAndValuesResponse][stell.objectInfo][0].name }"
	verwijderIOONamespace(gmDocument,'stelselBevragen-V1.1.xsd','http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.2.xsd','ns2')
    gmDocument[soapenv.Body][stell.getObjectInfoResponse][stell.objectInfo][stell.attributen].each { attribute ->
        // controleer of dit element al in de TMF response zit
        if (tmfDocument[soapenv.Body][stell.getObjectInfoAndValuesResponse][stell.objectInfo].'**'.find{it[stell.code].text() == attribute[stell.code].text()} == null) {
            logger.info "Adding attribute with code ${attribute[stell.code].text()}"
            logger.debug "***stell:objectInfo***** " + tmfDocument[soapenv.Body][stell.getObjectInfoAndValuesResponse][stell.objectInfo]
            tmfDocument[soapenv.Body][stell.getObjectInfoAndValuesResponse][stell.objectInfo][0].children().add(attribute)
        }
    }
    // Als een van beide responses bevraagbaar is zetten we het response op bevraagbaar
    if (gmDocument[soapenv.Body][stell.getObjectInfoResponse][stell.objectInfo][stell.ObjectHeaderInfo][stell.bevraagbaar][0].text().equals('true')) {
        logger.info "Before Adding attribute true to stell:bevraagbaar"
        tmfDocument[soapenv.Body][stell.getObjectInfoAndValuesResponse][stell.objectInfo][stell.ObjectHeaderInfo][stel.bevraagbaar][0].value = true
		logger.info "After Adding attribute true to stell:bevraagbaar"
    }
}
//ObjectInfo
if (gmDocument[soapenv.Body][stel.getObjectInfoResponse].size() == 1 && tmfDocument[soapenv.Body][stel.getObjectInfoResponse].size() == 1) {
    gmDocument[soapenv.Body][stel.getObjectInfoResponse][stel.objectInfo][stel.attributen].each { attribute ->
    	// controleer of dit element al in de TMF response zit
		if (tmfDocument[soapenv.Body][stel.getObjectInfoResponse][stel.objectInfo].'**'.find{it[stel.code].text() == attribute[stel.code].text()} == null) {
			logger.info "Adding attribute with code ${attribute[stel.code].text()}"
			logger.debug "***stel:objectInfo***** " + tmfDocument[soapenv.Body][stel.getObjectInfoResponse][stel.objectInfo]
			tmfDocument[soapenv.Body][stel.getObjectInfoResponse][stel.objectInfo][0].children().add(attribute)
        }
    }
    // Als een van beide responses bevraagbaar is zetten we het response op bevraagbaar
	if (gmDocument[soapenv.Body][stel.getObjectInfoResponse][stel.objectInfo][stel.ObjectHeaderInfo][stel.bevraagbaar][0].text().equals('true')) {
		logger.info "Adding attribute true to stel:bevraagbaar"
		tmfDocument[soapenv.Body][stel.getObjectInfoResponse][stel.objectInfo][stel.ObjectHeaderInfo][stel.bevraagbaar][0].value = true
	}
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
xmlNodePrinter.print(tmfDocument)
def result = writer.toString()

// Workaround: de namespace wsa zit alleen op sommige van de wsa elementen die niet genest zijn, zet de wsa
// namespace op de soap Header. Waarschijnlijk gefixt in Groovy 1.6.x 
result = result.replace('<soapenv:Header', '<soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing"')
result = result.replace('<wsa:To xmlns:wsa="http://www.w3.org/2005/08/addressing">', '<wsa:To>')
result = result.replace('<wsa:Action xmlns:wsa="http://www.w3.org/2005/08/addressing">', '<wsa:Action>')
// fix for ticket #98 - Gecombineerde metadata bevat alleen voor GM een stel xml namespace prefix
result = result.replace('<getBasisregistratieListResponse', '<getBasisregistratieListResponse xmlns:stel="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd"')
result = result.replace('<getObjectTypeListResponse', '<getObjectTypeListResponse xmlns:stel="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd"')
result = result.replace('<getObjectInfoResponse', '<getObjectInfoResponse xmlns:stel="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd"')
result = result.replace('<ns2:getObjectInfoAndValuesResponse', '<ns2:getObjectInfoAndValuesResponse xmlns="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd" xmlns:stell="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.2.xsd"')

// Workaround: can be removed when Groovy in Mule is updated to 1.6.x
def replaced = result.replaceAll("\r", "").replaceAll("\n", "")

logger.debug "merge result: ${result}"
logger.debug "merge replaced (workaround with no newlines/whitespace): ${replaced}"

return replaced

def getTMFBericht(String bericht1, String bericht2) {
	return bericht1.contains("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd") ? bericht1: bericht2
}

def getGMBericht(String bericht1, String bericht2) {
    return bericht1.contains("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd") ? bericht1 : bericht2
}

def verwijderIOONamespace(node, checkUri, newUri, newPrefix) {
	if (node instanceof Node && node.name() instanceof QName) {
		if (node.name().toString().indexOf(checkUri) != -1) {
			node.name = new QName(newUri,node.name().getLocalPart(), newPrefix)
		}
		node.children().each {
			verwijderIOONamespace(it, checkUri, newUri, newPrefix)
		}
	}
}

def checkPayload(bericht1, bericht2) {
	assert (bericht1 instanceof String && bericht2 instanceof String), "De payloads zijn niet van het type String"
	boolean bericht1TMF = bericht1.contains("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd")
	boolean bericht2TMF = bericht2.contains("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd")
	boolean bericht1GM = bericht1.contains("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd")
	boolean bericht2GM = bericht2.contains("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd")
	assert (bericht1TMF && bericht2GM) || (bericht2TMF && bericht1GM), "De berichten uit de payload zijn gelijk aan elkaar. We krijgen (${bericht1TMF} && ${bericht2GM}) || (${bericht2TMF} && ${bericht1GM}) "
}