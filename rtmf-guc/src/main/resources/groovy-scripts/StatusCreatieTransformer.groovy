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

def logger = Logger.getLogger("rtmfguc.script.TerugmeldingNaarStapCreatie")

//
// Om een StapCreatie te kunnen doen, hebben we in ieder geval een zaakIdentificatie nummer nodig.
//
// De status creatie kan gebrukt worden voor zowel een Terugmelding als een Intrekking. In het geval 
// van een Intrekking is het stapVolgNummer nodig. Dit moet in ieder geval groter zijn dan '1'.
// Stap 1 is de terugmelding, maar het is niet zo dat het stapVolgNummer voor een Intrekking 
// automatisch '2' is. Er kunnen ook nog updates gedaan zijn.
//

assert payload != null : "Payload is null"
logger.debug "Incoming payload: ${payload}"
assert zaakIdentificatie != null : "zaakIdentificatie is null"
logger.debug "zaakIdentificatie: ${zaakIdentificatie}"

def xmlSlurper = new XmlSlurper()
def terugmeldRequest = xmlSlurper.parseText(payload)

String datumStatusGezet = terugmeldRequest.Body.terugmelding.tijdstempelAanlevering.text()
String statusCode = ""
String statusOmschrijving = ""
String statusToelichting = ""
String statusVolgNummer = ""

if (payload =~ /<.*terugmelding.*>/) {
	statusCode = "ON"
	statusOmschrijving = "De terugmelding door RTMF ontvangen"
	statusToelichting = ""
	statusVolgNummer = "1"
} else if (payload =~ /<.*intrekking.*>/) {
	statusCode = "ON"
	statusOmschrijving = "De intrekking door RTMF ontvangen"
	statusToelichting = ""
	// Weetnog niet welk nummer hier ingevuld moet worden
	statusVolgNummer = "1"
} else {
	assert true : "Er is geen terugmelding of intrekking bericht gevonden."
}

//
// Zet de template bindings
//
def binding = ["zaakIdentificatie": zaakIdentificatie, 
               "datumStatusGezet": datumStatusGezet, 
               "statusCode": statusCode,
               "statusOmschrijving": statusOmschrijving,
               "statusToelichting": statusToelichting,
               "stapVolgNummer": stapVolgNummer]

//
// Haal de template op
//
String templateToLoad = "templates/StapCreatie.template"
def stream = getClass().getResourceAsStream(templateToLoad)
assert stream != null : "Template niet gevonden: ${templateToLoad}"
def xmlTemplate = stream.text 

//
// Vul de template in
//
def engine = new GStringTemplateEngine()
template = engine.createTemplate(xmlTemplate).make(binding)

// explicitly set the type of result to String to prevent GString problems
String result = template.toString()

logger.debug "Outgoing result: ${result}" 

return result;
