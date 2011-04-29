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
import groovy.text.GStringTemplateEngine
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException

//
// Dit script stelt de StUF vraag samen.
// de configuratie welke vraagtemplate gebruikt moet worden komt uit de propertie file die 
// in de mule-config.xml worden ge-injecteerd:
//      stufvraagtemplate   - Geeft aan per object per basisregistratie welke template gebruikt moet worden

def logger = Logger.getLogger("rtmfguc.script.ScBevragenNaarStufTransformer")

logger.debug "Incoming payload: ${payload}" 

def xmlSlurper = new XmlSlurper()
def bevragenRequest = xmlSlurper.parseText(payload)

String messageId = bevragenRequest.Header.MessageID.text()
logger.warn "De messageId (${messageId}) moet weer terug in de response transformer: hoe doen we dat? In de message properties stoppen?"

String brTag = bevragenRequest.Body.bevragen.brTag.text() // bv "GBA"
String objectTag = bevragenRequest.Body.bevragen.objectTag.text() // bv "01" (Natuurlijk Persoon)
String objectId = bevragenRequest.Body.bevragen.objectId.text()

logger.info "Gevonden in bevragen request: objectId: '${objectId}', brTag: '${brTag}', objectTag: '${objectTag}'"

// De inkomende bevragen request bevat: BRTag, objectTag en een objectId
// Op basis van de brTag en objectTag wordt een template bepaald
// TODO Op basis van de brTag en objectTag weten we de objectId? (bv. bsn nummer of kenteken?)

String templateKey = brTag + objectTag	

// TODO hoe dynamisch kan/moet dit (e.g. configureer alles in files)? 
// TODO Als we BAG hebben (een tweede "actuele waarden service", dan moet er een filter komen in
// de mule config om het bericht naar de juiste service te sturen op basis van brTag.

// TODO De bindings kunnen misschien ook bepaald worden per template, voor nu is het altijd bsn
def binding = ["objectId": objectId]

String templateToLoad = stufvraagtemplate.getProperty(templateKey)

logger.debug "Voor de brTag en objectId combi '${templateKey}' gebruik template '${templateToLoad}'"

// workaround: use default template when no mapping present for test cases
if (templateToLoad == null) {
	logger.warn "Geen template voor de key: ${templateKey}, gebruik een default persoon template"
	templateToLoad = "templates/DummyStUFVraagXML.template";
}

// TODO Misschien een nette exception gooien (met scriptnaam)? Nu wordt er met assert een stacktrace geprint...
assert templateToLoad != null : "Geen template voor de key: ${templateKey}"
def stream = getClass().getResourceAsStream(templateToLoad)

if (stream == null){
	throw new RtmfGucException("Template ${templateToLoad} is niet gevonden bij template key: ${templateKey}")
	assert false
}

// assert stream != null : "Template niet gevonden: ${templateToLoad}"
def xmlTemplate = stream.text

def engine = new GStringTemplateEngine()
template = engine.createTemplate(xmlTemplate).make(binding)

// explicitly set the type of result to String to prevent GString problems
String result = template.toString()

logger.debug "Outgoing result: ${result}" 

return result