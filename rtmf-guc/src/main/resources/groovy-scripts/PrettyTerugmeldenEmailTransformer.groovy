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
import groovy.text.GStringTemplateEngine;

import org.apache.log4j.Logger
import org.mule.api.MuleMessage;

//
//Definieer globale variabelen
//
logger = Logger.getLogger("rtmfguc.script.PrettyEmailTransformer")
soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
aanmeld = new groovy.xml.Namespace('http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd')

assert payload[0] != null : "Eerste payload is null"
assert payload[1] != null : "Tweede payload is null"
assert emailTemplate != null : "Email template is null"
logger.debug "Hier is de template naam: ${emailTemplate}"
def stream = getClass().getResourceAsStream(emailTemplate)
assert stream != null : "Stream is null"
def template = stream.text
assert template != null : "Template is null"
logger.debug "De template:\n ${template}"

assert stelselCatalogusCache != null : "stelselCatalogus is null"

//
// Eerst een aantal 'conveniant' classen definieren
//
class Attribuut {
	String tag;
	String betwijfeldeWaarde;
	String voorstel

	//
	// We moeten hier een soort van formattering toepassen
	//
	String toString() {
		return """
        ID                  : ${naam}
        Betwijfelde waarde  : ${betwijfeldeWaarde}
        Voorstel            : ${voorstel}
		"""
	}
}

class Basisregistratie {
	String tag
	String objectTag
	String objectId
	String toelichting
	String objectNaam
}

class Contact {
	String naam
	String telefoon
	String email
}

//
//Definieer wat werk variabelen
//
meldingKenmerk = null
tijdstempel = null
br = null
ct = null
at = []
String zaakNummer = null

if (payload instanceof String[]) {
	payload.each { p ->
		if (p =~ /<.*terugmelding.*>/) {
			logger.info "match terugmelding"
			extractTerugmeldData(p)
		}
	}
} else {
	extractTerugmeldData(payload)
}

MuleMessage mMessage = (MuleMessage)message
zaakNummer = mMessage.getProperty("zaaknummer")
if (zaakNummer==null || zaakNummer.equals("")) {
	zaakNummer = "Zaaknummer niet gevonden!"
}

//
// Check aanwezigheid van data
//
assert br != null : "Basisregistratie is null"
assert ct != null : "Contact is null"
assert at != null : "Attributen is null"
assert at.size() != 0 : "Attributen is leeg"
assert tijdstempel != null : "tijdsstempel is null"
logger.debug "tijdsstempel is: ${tijdstempel}"

//
// Bind de document data aan de template velden
//
def binding = ["Zaakidentificatie": zaakNummer,
               "meldingKenmerk": meldingKenmerk, 
               "tijdstempelAanlevering": datumTijdNaarEmailFormaat(tijdstempel),
               "basisregistratie": br,
               "attributen": at,
               "contact": ct,
			   "adresMeerInfo": adresMeerInfo,
			   "zmwebPrefix": zmwebPrefix]
assert binding != null : "Binding is null"

logger.debug "Aanmaken TemplateEngine"
def engine = new GStringTemplateEngine()
logger.debug "Vullen template"
filledTemplate = engine.createTemplate(template).make(binding)
String result = filledTemplate.toString()
logger.debug "Het resultaat is:\n ${result}"

//
// En klaar is Klara
//

((MuleMessage)message).setPayload(result)

return (MuleMessage)message

def extractTerugmeldData(p) {
	logger.info "Extractterugmelding data ${p}"
	
	//
	// Maak een document van de payload
	//
	def inBericht = p
	def xmlParser = new XmlParser()
	xmlParser.setTrimWhitespace(true)
	def document = xmlParser.parseText(inBericht)
	
	//
	// Haal diverese waarden uit het document en sla ze tijdelijke op
	//
	if (document[soapenv.Body][aanmeld.terugmelding].size() == 1) {
		meldingKenmerk = document[soapenv.Body][aanmeld.terugmelding][aanmeld.meldingKenmerk].text()
		tijdstempel = document[soapenv.Body][aanmeld.terugmelding][aanmeld.tijdstempelAanlevering].text()
		logger.debug "tijdstempel direct = ${tijdstempel}"

		// ophalen van object tag
		String objectTag =  document[soapenv.Body][aanmeld.terugmelding][aanmeld.objectTag].text();
		
		logger.debug "objectTag : " + objectTag
		
		br = new Basisregistratie(tag: document[soapenv.Body][aanmeld.terugmelding][aanmeld.basisRegistratie].text(),
								  objectTag: objectTag,
								  objectId: document[soapenv.Body][aanmeld.terugmelding][aanmeld.objectIdentificatie].text(),
								  toelichting: document[soapenv.Body][aanmeld.terugmelding][aanmeld.toelichting].text(),
		                          objectNaam: stelselCatalogusCache.getObjectNaam(objectTag))
		ct = new Contact(naam: document[soapenv.Body][aanmeld.terugmelding][aanmeld.contactInfo][aanmeld.naam].text(),
						 telefoon: document[soapenv.Body][aanmeld.terugmelding][aanmeld.contactInfo][aanmeld.telefoon].text(),
						 email: document[soapenv.Body][aanmeld.terugmelding][aanmeld.contactInfo][aanmeld.email].text())
	
		document[soapenv.Body][aanmeld.terugmelding][aanmeld.attributen].each { attribute ->
			at.add(new Attribuut(tag: attribute[aanmeld.attribuutIdentificatie].text(),
								 betwijfeldeWaarde: attribute[aanmeld.betwijfeldeWaarde].text(),
								 voorstel: attribute[aanmeld.voorstel].text()))
		}
		
	}
}

def datumTijdNaarEmailFormaat(String datumIn) {
    if (datumIn != null && datumIn.length() == 29) {
        return datumIn.substring(0, datumIn.indexOf("+") -7).replace("T", " ");
    }
}