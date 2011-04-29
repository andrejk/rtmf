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

def logger = Logger.getLogger("rtmfguc.script.IntrekkingToStapUpdateTransformer")

assert payload != null : "Payload is null"
assert payload.length == 3 : "Payload is niet 3"
		logger.debug "=================================================================================================="
		logger.debug "En hier is de payloads"
		logger.debug "=================================================================================================="
		logger.debug "payload[0]: ${payload[0]}"
		logger.debug "=================================================================================================="
		logger.debug "payload[2]: ${payload[2]}"
		logger.debug "=================================================================================================="
//
//Definieer de gebruikte parameters
//
String zaakNummer = null
String beginDatum = null
String stapTypeCode = null
String stapEindDatum = null
String toelichting = null


//
// Maak een document van de payload
//
def xmlSlurper = new XmlSlurper()

def iDocument = xmlSlurper.parseText(payload[0])
def detailDocument = xmlSlurper.parseText(payload[2])



if (iDocument.Body.intrekking.size() == 1) {
	zaakNummer = iDocument.Body.intrekking.betreftTmfKenmerk.text()
	toelichting = iDocument.Body.intrekking.toelichting.text()
	stapEindDatum = iDocument.Body.intrekking.tijdstempelAanlevering.text()
}
hoogsteNummer = -1
detailDocument.Body.ZaakDetailResponse.Stap.each { stap ->
	huidigeNummer = stap.Stapvolgnummer.toInteger()
	if (huidigeNummer > hoogsteNummer) {
		hoogsteNummer = huidigeNummer
		stapTypeCode = stap.Staptypecode.text()
		beginDatum = stap.Begindatum.text()
	}
}

assert zaakNummer != null : "ZaakNummer is null"
logger.debug "Het Zaaknummer is: ${zaakNummer}"

//
//Bind de document data aan de template velden
//
def binding = ["Zaakidentificatie": zaakNummer,
			   "Begindatum": beginDatum,
			   "Staptypecode": stapTypeCode,
			   "Stapeindedatum": datumTijdNaarZakenmagazijnFormaat(stapEindDatum),
			   "toelichting": toelichting,
			   "Resultaatomschrijving": "Ingetrokken door bronhouder",
               "Resultaattoelichting": toelichting,
               "Resultaatcode": "ingetr",
               "Resultaatomschrijving": "Ingetrokken"]
assert binding != null : "Binding is null"
	
	
//
// Haal de template op
//
logger.debug "Ophalen template"
String templateToLoad = "templates/StapUpdate.template"
def stream = getClass().getResourceAsStream(templateToLoad)
assert stream != null : "Template niet gevonden: ${templateToLoad}"
def xmlTemplate = stream.text 

//
// Vul de template in
def engine = new GStringTemplateEngine()
template = engine.createTemplate(xmlTemplate).make(binding)

/// explicitly set the type of result to String to prevent GString problems
def result = template.toString()
logger.debug "Outgoing result: ${result}" 

return result;

def datumTijdNaarZakenmagazijnFormaat(String datumIn) {
	if (datumIn != null) {
		return datumIn.substring(0, datumIn.indexOf(".")).concat(".000+01:00");
	}
}