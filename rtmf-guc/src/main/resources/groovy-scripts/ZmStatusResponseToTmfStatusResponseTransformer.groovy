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


def logger = Logger.getLogger("rtmfguc.script.ZmStatusResponseToTmfStatusResponseTransformer")

assert payload != null : "Er is geen payload teruggekomen van de ZM status service!"
logger.debug """
ZmStatusResponseToTmfStatusResponseTransformer payload: 
${payload}
"""
String payloadAsString = payload
def finder = (payloadAsString =~ /<.*MessageID.*>(.*)<\/.*MessageID>/)
//"expect one result: one messageID"
assert  finder.count == 1, "ZmStatusResponseToTmfStatusResponseTransformer: de finder heeft geen match gevonden"
//finder[0][0] is the whole message
def String wsaMessageId = finder[0][1] 

class Terugmelding {
	String meldingKenmerk
	String tagBR
	String tagObject
	String idObject
	String startDatum
	String resultaatCode
	String zaakIdentificatie

	// Let op: Indien ook het originele terugmelding bericht tussen de <Formulier></Formulier> tags staat,
	// Dan hier ook nog ff de Basisregistratie, obejctTag, en objectId toevoegen
	
	String toString() {
		return "Terugmelding meldingKenmerk: '${meldingKenmerk}' tagBR: ${tagBR} tagObject: '${tagObject}' idObject: ${idObject} tartDatum: '${startDatum}' resultaatCode: '${resultaatCode}' zaakIdentificatie: '${zaakIdentificatie}'";
	}
}

String result

// Lege response van het zakenmagazijn
if (payload.contains("Zaak/>") || payload.contains("Zaak />")) {
	String templateToLoad = "templates/TmfStatusEmptyResponse.template"
	def stream = getClass().getResourceAsStream(templateToLoad)
	assert stream != null : "Template niet gevonden: ${templateToLoad}"
	logger.debug "Definieer binding, en laad de template file in ..."
	def binding = ["wsaMessageId": wsaMessageId] 
    logger.debug "Bouw een XML template en een TemplateEngine"
   	def xmlTemplate = stream.text
   	def engine = new GStringTemplateEngine()
   	logger.debug "Vul de template met de binding ..."
   	def template = engine.createTemplate(xmlTemplate).make(binding)
   	result = template.toString()

} else {
	// Za(a)k(en) teruggekregen
	def xmlSlurper = new XmlSlurper()
	def zmStatusResponse = xmlSlurper.parseText(payload)
	
	// maak een lijst aan van terugmeldingen
	
	def terugmeldingen = []
	
	zmStatusResponse.Body.ZaakQueryResponse.Zaak.each { zaak ->
		def tm = new Terugmelding()
	
		logger.debug "Ok, een 'zaak' parsen"
		tm.meldingKenmerk = zaak.Trefwoord
		zaak.Kenmerk.each { km ->
			String bron = km.kenmerkBron.text()
			logger.debug "kenmerk    : ${km.kenmerk.text()}"
			logger.debug "kenmerkBron: ${bron}"
			if (bron.equalsIgnoreCase("tagBR")) {
				tm.tagBR = km.kenmerk.text()
			} else if (bron.equalsIgnoreCase("tagObject")) {
				tm.tagObject = km.kenmerk.text()
			} else if  (bron.equalsIgnoreCase("idObject")) {
				tm.idObject = km.kenmerk.text()
			}
		}
	
		tm.startDatum = zaak.Startdatum.text()
		tm.resultaatCode = zaak.Resultaatcode.text()
		tm.zaakIdentificatie = zaak.Zaakidentificatie.text()
	
		// Let op: Indien ook het originele terugmelding bericht tussen de <Formulier></Formulier> tags staat,
		// Dan hier ook nog ff de Basisregistratie, obejctTag, en objectId toevoegen
	
	    logger.debug "Voeg een element toe: $tm"
	    terugmeldingen << tm 	
	}
	
	logger.debug "Definieer binding, en laad de template file in ..."
	def binding = ["wsaMessageId": wsaMessageId, "terugmeldingen": terugmeldingen] 
	String templateToLoad = "templates/TmfStatusResponse.template"
	def stream = getClass().getResourceAsStream(templateToLoad)
	assert stream != null : "Template niet gevonden: ${templateToLoad}"
	logger.debug "Bouw een XML template en een TemplateEngine"
	def xmlTemplate = stream.text
	def engine = new GStringTemplateEngine()
	logger.debug "Vul de template met de binding ..."
	def template = engine.createTemplate(xmlTemplate).make(binding)
	
	result = template.toString() 
}

logger.debug "Et voila, het resultaat: ${result}"
return result

