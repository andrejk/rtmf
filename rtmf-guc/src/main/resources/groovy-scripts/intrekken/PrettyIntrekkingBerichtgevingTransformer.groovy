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
import org.mule.api.MuleMessage;

def logger = Logger.getLogger("rtmfguc.script.PrettyIntrekkingBerichtgevingTransformer")

assert payload != null : "Payload is null"
assert payload.length >= 3 : "Payload is niet minimaal 3"
assert template != null : "Email template is null"

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
String toelichtingZaak = null
String beginDatum = null
String stapTypeCode = null
String stapEindDatum = null
String toelichting = null
String contactPersoonNaam = null
String contactPersoonTelefoon = null
String contactPersoonEmail = null
String meldingKenmerk = null
String meldingKenmerkZaak = null
String basisRegistratie = null
String objectTag = null
String objectIdentificatie = null
String objectNaam = null
List at = new ArrayList()

class Attribuut {
    String tag;
    String naam;
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

//
// Maak een document van de payload
//
def xmlSlurper = new XmlSlurper()

def iDocument = xmlSlurper.parseText(payload[0])
def detailDocument = xmlSlurper.parseText(payload[2])



if (iDocument.Body.intrekking.size() == 1) {
    zaakNummer = iDocument.Body.intrekking.betreftTmfKenmerk.text()
    toelichtingZaak = iDocument.Body.intrekking.toelichting.text()
    stapEindDatum = iDocument.Body.intrekking.tijdstempelAanlevering.text()
	meldingKenmerkZaak = iDocument.Body.intrekking.meldingKenmerk.text()
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

// formulier gegevens
def terugMelding = detailDocument.Body.ZaakDetailResponse.Formulier.Envelope.Body.terugmelding
contactPersoonNaam = terugMelding.contactInfo.naam.text()
contactPersoonTelefoon = terugMelding.contactInfo.telefoon.text()
contactPersoonEmail = terugMelding.contactInfo.email.text()
meldingKenmerk = terugMelding.meldingKenmerk.text()
basisRegistratie = terugMelding.basisRegistratie.text()
objectTag = terugMelding.objectTag.text()
objectIdentificatie = terugMelding.objectIdentificatie.text()
toelichting = terugMelding.toelichting.text()


objectNaam = detailDocument.Body.ZaakDetailResponse.Kenmerk.find{it.kenmerkBron.text()=="objectNaam"}.kenmerk.text()
logger.debug "object naam : " + objectNaam

// attributen
terugMelding.attributen.each { attribute ->
			logger.debug "Adding an attribute"
            at.add(new Attribuut(tag: attribute.attribuutIdentificatie.text(),
                                 betwijfeldeWaarde: attribute.betwijfeldeWaarde.text(),
                                 voorstel: attribute.voorstel.text()))
        }



assert zaakNummer != null : "ZaakNummer is null"
logger.debug "Het Zaaknummer is: ${zaakNummer}"
logger.debug "Er zijn ${at}"
//
//Bind de document data aan de template velden
//
def binding = ["Zaakidentificatie": zaakNummer,
			   "meldingKenmerk": meldingKenmerk,
			   "meldingKenmerkZaak": meldingKenmerkZaak,
			   "basisRegistratie": basisRegistratie,
			   "objectTag": objectTag,
			   "objectIdentificatie": objectIdentificatie,
			   "objectNaam": objectNaam,
               "Begindatum": datumTijdNaarEmailFormaat(beginDatum),
               "Staptypecode": stapTypeCode,
               "Stapeindedatum": datumTijdNaarEmailFormaat(stapEindDatum),
               "toelichting": toelichting,
			   "toelichtingZaak": toelichtingZaak,
               "Resultaatomschrijving": "Ingetrokken door bronhouder",
               "Resultaattoelichting": toelichting,
               "Resultaatcode": "ingetr",
               "Resultaatomschrijving": "Ingetrokken",
			   "contactPersoonNaam": contactPersoonNaam,
			   "contactPersoonTelefoon": contactPersoonTelefoon,
			   "contactPersoonEmail": contactPersoonEmail,
			   "attributen": at,
			   "adresMeerInfo": adresMeerInfo,
			   "zmwebPrefix": zmwebPrefix]
assert binding != null : "Binding is null"
    
    
//
// Haal de template op
//
logger.debug "Ophalen template"
def stream = getClass().getResourceAsStream(template)
assert stream != null : "Template niet gevonden: ${template}"
def xmlTemplate = stream.text 

//
// Vul de template in
def engine = new GStringTemplateEngine()
template = engine.createTemplate(xmlTemplate).make(binding)

/// explicitly set the type of result to String to prevent GString problems
def result = template.toString()
logger.debug "Outgoing result: ${result}" 
((MuleMessage)message).setPayload(result)

return (MuleMessage)message

def datumTijdNaarEmailFormaat(String datumIn) {
    if (datumIn != null && datumIn.length() == 29) {
        return datumIn.substring(0, datumIn.indexOf("+") -7).replace("T", " ");
    }
}