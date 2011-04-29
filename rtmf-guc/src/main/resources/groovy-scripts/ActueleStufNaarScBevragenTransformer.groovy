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
import javax.xml.xpath.XPathExpressionException;

import mx4j.log.Logger;

import java.util.List;

import org.apache.log4j.Logger
import groovy.text.GStringTemplateEngine
import javax.xml.xpath.XPathFactory
import javax.xml.xpath.XPathConstants
import javax.xml.parsers.DocumentBuilderFactory
import java.util.regex.Pattern;

// TODO cache wordt niet meer gebruikt door de call naar getObjectInfo: verwijder (ook stufpath uit cache!)

// de stelselCatalogusCache wordt geinject door Spring in de mule config

def logger = Logger.getLogger("rtmfguc.script.ActueleStufNaarScBevragenTransformer")

// use "script binding" voor global constants: kunnen in het script en methodes worden gebruikt
STUF_300_NS_URI = "http://www.egem.nl/StUF/StUF0300"
STELGM_NS_URI = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd"

def getSTUFBericht(payload) {
	return payload[0].getPayloadAsString().contains(STUF_300_NS_URI) ? payload[0]: payload[1]
}

def getObjectInfoBericht(payload) {
	return payload[0].getPayloadAsString().contains(STELGM_NS_URI) ? payload[0] : payload[1]
}

assert payload[0] != null && !(payload[0] instanceof org.mule.transport.NullPayload): "Eerste payload is null"
assert payload[1] != null && !(payload[1] instanceof org.mule.transport.NullPayload): "Tweede payload is null"

logger.debug """
==================================================================================================
En hier zijn de payloads
==================================================================================================
Payload[0]: ${payload[0].getPayloadAsString()}"
Payload[1]: ${payload[1].getPayloadAsString()}"
=================================================================================================="""
// prepare for xpath 
def builder     = DocumentBuilderFactory.newInstance().newDocumentBuilder()
def inputStream = new ByteArrayInputStream(getSTUFBericht(payload).payload.bytes)
def stufXml     = builder.parse(inputStream).documentElement

def xpath = XPathFactory.newInstance().newXPath()

// TODO bepaal de orginele wsa message id van de request voor de reply: zit die ergens in de stuf message?
def xmlSlurper = new XmlSlurper()
def objectInfoResponse = xmlSlurper.parseText(getObjectInfoBericht(payload).getPayloadAsString())
String messageId = getObjectInfoBericht(payload).getCorrelationId()

// maak een lijst aan van attributeValue objecten

class AttributeValue {
	String tag
	String naam
	String stufpath
	String actueleWaarde
	
	String toString() {
		return "attributeValue code: '${tag}' naam: '${naam}' actueleWaarde: '${actueleWaarde}' stufpath: '${stufpath}'";
	}
}

def attributeValues = []

objectInfoResponse.Body.getObjectInfoResponse.objectInfo.attributen.each { attribuut ->
	
    def value = new AttributeValue()

    value.tag = attribuut.code.text()
    value.naam = attribuut.naam.text()
	
    value.stufpath = null
    // Haal met behulp van het stufpath de actuele waarde uit het stufbericht.
	// Als we een onbekend stufpath tegenkomen (lees: ?) dan zetten we de value op Onbekend
    String actueleWaarde
    if (value.stufpath == "?" || value.stufpath == "Onbekend") {
		logger.debug "empty stufpath"
		
    	actueleWaarde = ""
    }
    else {
		// split de lijst met ';'
		List<String> patt = Arrays.asList(Pattern.compile(";").split(attribuut.stufpath.text()))

		// verwerk de lijst
		//als er een waarde gevonden is uit de loop stappen anders door gaan met volgende item
		for(String pat : patt) {
			//			pat
			// TODO workaround: het vervangen van het leement prs met prsLa01
			String path = pat.replaceFirst('/prs', '/prsLa01')
			logger.debug "path : " + path
			try{
			// bevraag het stuf bericht met de xpath
			actueleWaarde = xpath.evaluate(path, stufXml, XPathConstants.STRING ) 
			// Let op: de XPath kan een niet bestaand path zijn voor specifieke berichten... actuele waarde is dan leeg
			if (actueleWaarde == "") {
				logger.info "xpath '${path}' geeft geen resultaat!"
			}
			else {
				// een waarde gevonden
				// value element vullen met stufpath en stoppen met de for lus
				logger.debug "xpath '${path}' results in: ${actueleWaarde}"
				value.stufpath = path
				break
			}
			}
			catch( XPathExpressionException e ){
				logger.warn "xpath expressie '${path}' is invalide"
				actueleWaarde = "";
				}
							
		}
    }
    value.actueleWaarde = (actueleWaarde == "") ? "" : actueleWaarde
    		
    logger.debug "Voeg een element toe: $value"
    attributeValues << value 
}

def binding = ["messageId": messageId, "attributeValues": attributeValues] 
String templateToLoad = "templates/ActueleWaardeNaarBevragenResponse.template"
	
def stream = getClass().getResourceAsStream(templateToLoad)
assert stream != null : "Template niet gevonden: ${templateToLoad}"
def xmlTemplate = stream.text
def engine = new GStringTemplateEngine()
def template = engine.createTemplate(xmlTemplate).make(binding)

String result = template.toString()

logger.debug "Result: ${result}"

return result
