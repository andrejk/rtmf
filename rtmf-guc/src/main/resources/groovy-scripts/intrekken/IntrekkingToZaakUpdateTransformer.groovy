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

def logger = Logger.getLogger("rtmfguc.script.IntrekkingToZaakUpdateTransformer")

assert payload != null : "Payload is null"
logger.debug "=================================================================================================="
logger.debug "En hier is de payload"
logger.debug "=================================================================================================="
logger.debug "${payload}"
logger.debug "=================================================================================================="

//
//Definieer de gebruikte namespaces
//
def soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
def intrekken = new groovy.xml.Namespace('http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd')
String zaakNummer = null

if (payload =~ /<.*intrekking.*>/) {
		logger.info "match intrekking"
		//
		// Maak een document van de payload
		//
		def iBericht = payload
		def xmlParser = new XmlParser()
		xmlParser.setTrimWhitespace(true)
		def iDocument = xmlParser.parseText(iBericht)
	
		if (iDocument[soapenv.Body][intrekken.intrekking].size() == 1) {
			zaakNummer = iDocument[soapenv.Body][intrekken.intrekking][intrekken.betreftTmfKenmerk].text()
			Resultaattoelichting = iDocument[soapenv.Body][intrekken.intrekking][intrekken.toelichting].text()
		}	
	}

assert zaakNummer != null : "ZaakNummer is null"
logger.debug "Het Zaaknummer is: ${zaakNummer}"

//
//Bind de document data aan de template velden
//
def binding = ["Zaakidentificatie": zaakNummer,
               "Resultaatcode": "ingetr",
               "Resultaatomschrijving": "Ingetrokken door bronhouder",
			   "Resultaattoelichting" : Resultaattoelichting]
assert binding != null : "Binding is null"
	
	
//
// Haal de template op
//
logger.debug "Ophalen template"
String templateToLoad = "templates/ZaakUpdate.template"
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
