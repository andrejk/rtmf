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
import org.mule.api.MuleMessage;
import groovy.text.GStringTemplateEngine

// Gebruik log4j als logging systeem
def logger = Logger.getLogger("rtmfguc.script.OphalenZaakDetailsNaarOphalenMeldingKenmerkTransformer.groovy")
assert payload != null : "De payload is null"

logger.debug "=================================================================================================="
logger.debug "En hier is de payload"
logger.debug "=================================================================================================="
logger.debug "Payload: ${payload}"
logger.debug "=================================================================================================="

//
//Definieer de gebruikte namespaces
//
def env = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'env')
def zdr = new groovy.xml.Namespace('http://www.interaccess.nl/webplus/statuswfm_v2', 'ns2')

String zaakNummer = null
String berichtSoort = "Terugmelding"
String organisatieId = null
String organisatie = null
String afdeling = null
String tijdstempelGemeld = null 
String tijdstempelOntvangst = null
int statusVolgnummer = 0
String status = null
String tijdstempelStatus = null
String toelichting = null

int stapVolgnummer = 0
String stapTypeCode = null
String stapBeginDatum = null



if (payload =~ /<.*ZaakDetailResponse.*>/) {
	logger.debug "match ZaakDetailResponse"
	//
	// Maak een document van de payload
	//
	def zdrBericht = payload
	def xmlParser = new XmlParser()
	xmlParser.setTrimWhitespace(true)
	def zdrDocument = xmlParser.parseText(zdrBericht)

	//
	// Haal diverese waarden uit het document en sla ze tijdelijke op
	//
	if (zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Zaakidentificatie].size() > 0) {
		logger.debug "Binnen"
		//
		// Ophalen algemene info
		//
		zaakNummer = zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Zaakidentificatie].text()
		organisatieId = zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Zaak_verantwoordelijke_oeh][zdr.oeh_id].text()
		organisatie = zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Zaak_verantwoordelijke_oeh][zdr.oeh_naam].text()
		// LET OP: hetzelfde als 'organisatie' ???
		afdeling = zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Zaak_verantwoordelijke_oeh][zdr.oeh_naam].text()
		tijdstempelGemeld = zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Startdatum].text()
		// LET OP: hetzelfde als 'tijdstempelGemeld' ??? 
		tijdstempelOntvangst = zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Startdatum].text()

		//
		// Ophalen laatste status info
		//
		status = transformToTMF(zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Resultaatcode].text())
		toelichting = zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Resultaattoelichting].text()
//		zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Status].each { ztatus ->
//			String sNum = ztatus[zdr.Statusvolgnummer].text()
//			logger.debug "statusNum: ${sNum}"
//			if (Integer.parseInt(sNum) > statusVolgnummer) {
//				statusVolgnummer = Integer.parseInt(sNum)
//                tijdstempelStatus = ztatus[zdr.Datumstatusgezet].text()
//			}
//		}
		
		//
        // Ophalen laatste stap info voor het ophalen van de toelichting
        //
        zdrDocument[env.Body][zdr.ZaakDetailResponse][zdr.Stap].each { ztap ->
            String sNum = ztap[zdr.Stapvolgnummer].text()
            logger.debug "stapNum: ${sNum}"
            if (Integer.parseInt(sNum) > stapVolgnummer) {
            	stapVolgnummer = Integer.parseInt(sNum)
            	tijdstempelStatus = ztap[zdr.Stapeinddatum].text()
				if (tijdstempelStatus == null || tijdstempelStatus.equals("")) {
					tijdstempelStatus = ztap[zdr.Begindatum].text()
				}
		        logger.debug "Er is een tijdstempelStatus gevonden in de stap: ${tijdstempelStatus}"
            }
        }
	}
}

assert zaakNummer != null : "Zaak identificatie nummer niet gevonden."
assert tijdstempelGemeld != null : "tijdstempelGemeld is niet gevonden."
assert status != null : "status is niet gevonden."
assert tijdstempelStatus != null : "tijdstempelStatus is niet gevonden."

//
// Deze transformer wordt aangeroepen door zowel de 'Ophalen' code als
// de 'Intrekken' code. Voor het 'Ophalen' stuk wordt de data in een
// 'OphalenMeldingKenmerkResponse' bericht gezet. Voor het 'Intrekken' stuk
// wordt de data in een aantal properties van de aanroepende MuleMessage
// gezet, en weer teruggegeven (via de MuleMessage) aan de aanroeper.
//

//
// Bind de document data aan de template velden
//
def binding = ["zaakNummer": zaakNummer,
               "berichtsoort": berichtSoort,
               "organisatieId": organisatieId,
               "organisatie": organisatie,
               "afdeling": afdeling,
               "tijdstempelOntvangst": tijdstempelOntvangst,
               "tijdstempelGemeld": tijdstempelGemeld,
               "status": status,
               "tijdstempelStatus": tijdstempelStatus,
               "toelichting": toelichting]
assert binding != null : "Binding is null"
	
//
// Haal de template op
//
logger.debug "Ophalen template"
String templateToLoad = "templates/OphalenMeldingKenmerkResponse.template"
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

def transformToTMF(tmfStatus) {	
	tmfStatus = tmfStatus.equals('ingetr') ? 'ingetrokken' : tmfStatus
	tmfStatus = tmfStatus.equals('onderzoek') ? 'in onderzoek' : tmfStatus
	tmfStatus = tmfStatus.equals('nietontv') ? 'niet ontvankelijk' : tmfStatus
    return tmfStatus
}