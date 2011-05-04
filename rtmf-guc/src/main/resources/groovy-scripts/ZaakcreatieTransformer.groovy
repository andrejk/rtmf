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

def logger = Logger.getLogger("rtmfguc.script.TerugmeldingNaarZaakCreatieTransformer")

//
// LET OP: Deze transformer is niet meer up-to-date, en wordt niet gebruikt.
//

//
// Om een ZaakCreatie te kunnen doen, hebben we in ieder geval de payload nodig.
//

assert payload != null : "Payload is null"
logger.debug "Incoming payload: ${payload}"

//
// Slurp de payload om makkelijk bij de verschillende onderdelen te kunnen komen.
//
def xmlSlurper = new XmlSlurper()
def terugmeldRequest = xmlSlurper.parseText(payload)

String meldingKenmerk = terugmeldRequest.Body.terugmelding.meldingKenmerk.text()
String tijdstempelAanlevering = terugmeldRequest.Body.terugmelding.tijdstempelAanlevering.text()
String basisRegistratie = terugmeldRequest.Body.terugmelding.basisRegistratie.text()
String objectTag = terugmeldRequest.Body.terugmelding.objectTag.text()
String objectIdentificatie = terugmeldRequest.Body.terugmelding.objectIdentificatie.text()
String toelichting = terugmeldRequest.Body.terugmelding.toelichting.text()
String contactNaam = terugmeldRequest.Body.terugmelding.contactInfo.naam.text()
String contacttelefoon = terugmeldRequest.Body.terugmelding.contactInfo.telefoon.text()
String contactEmail = terugmeldRequest.Body.terugmelding.contactInfo.email.text()


if (basisRegistratie.equalsIgnoreCase("BRA")) {
	String natuurlijkObjectIdentificatie = objectIdentificatie
	String nietNatuurlijkObjectIdentificatie = ""
} else {
	String natuurlijkObjectIdentificatie = ""
	String nietNatuurlijkObjectIdentificatie = objectIdentificatie	
}

//
//Zet de template bindings
//
def binding = ["natuurlijkBSN": natuurlijkObjectIdentificatie, 
               "nietnatuurlijkBSN": natuurlijkObjectIdentificatie, 
               "Initiator": contactNaam,
               "Startdatum": tijdstempelAanlevering,
               "Kenmerk": meldingKenmerk,
               "Zaaktoelichting": toelichting,
               "Terugmelding": payload]

//
// Haal de template op
//
String templateToLoad = "templates/ZaakCreatie.template"
	   def stream = getClass().getResourceAsStream(templateToLoad)
	   assert stream != null : "Template niet gevonden: ${templateToLoad}"
	   def xmlTemplate = stream.text 

//
// Vul de template in
//def engine = new GStringTemplateEngine()
template = engine.createTemplate(xmlTemplate).make(binding)

/// explicitly set the type of result to String to prevent GString problems
String result = template.toString()

logger.debug "Outgoing result: ${result}" 

return result;


