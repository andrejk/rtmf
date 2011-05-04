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

import groovy.xml.StreamingMarkupBuilder
import groovy.xml.QName
import org.mule.transport.NullPayload
import org.apache.log4j.Logger
import nl.rotterdam.rtmf.guc.common.NamespaceFixer

//
// Dit script voegt de resultaten van twee ophaal stromen (een naar de TMF en een naar het ZM) 
// samen tot een response naar de caller
//
// De payload met de ovsoftware headers is de tmf payload die we gebruiken als basis
//


// Gebruik log4j als logging systeem
def logger = Logger.getLogger("rtmfguc.script.OphalenResponseMerger")

assert payload[0] != null : "Eerste payload is null"
assert payload[1] != null : "Tweede payload is null"

String tmfBericht = getTMFBericht(payload[0].getPayloadAsString(), payload[1].getPayloadAsString())
String zmBericht = getZMBericht(payload[0].getPayloadAsString(), payload[1].getPayloadAsString())

logger.debug "=================================================================================================="
logger.debug "En hier zijn de payloads"
logger.debug "=================================================================================================="
logger.debug "Zakenmagazijn bericht: ${zmBericht}"
logger.debug "=================================================================================================="
logger.debug "tmf bericht: ${tmfBericht}"
logger.debug "aantal payloads: ${payload.length}"
if (payload.length > 2) {
	logger.debug "bericht zaakDetail: ${payload[2].getPayloadAsString()}"
}
logger.debug "=================================================================================================="
def xmlParser = new XmlParser()
xmlParser.setTrimWhitespace(true)

def zmDocument = xmlParser.parseText(zmBericht)
def tmfDocument = xmlParser.parseText(tmfBericht)

//
// Definieer de gebruikte namespaces
//
def soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
def oph = new groovy.xml.Namespace('http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd', 'oph')
def zmo = new groovy.xml.Namespace('http://www.interaccess.nl/webplus/statuswfm_v2', 'zmo')

if (tmfBericht.contains("ophalenMeldingStatusResponse")) {
	// We doorlopen alle orginele tmf elementen om ervoor te zorgen dat de zaakidentificatie 
	// bij het meldingkenmerk komt te staan.
	logger.debug "voor vervormen meldingKenmerk"
	tmfDocument[soapenv.Body][oph.ophalenMeldingStatusResponse][oph.statusResponseList][oph.terugmeldResponseList].each { terugmelding ->
		if (!terugmelding.children().isEmpty()) {
			terugmelding[oph.terugmeld][oph.meldingKenmerk][0].setValue(terugmelding[oph.terugmeld][oph.meldingKenmerk].text() + " - (B:${terugmelding[oph.terugmeldMCore][oph.tmfKenmerk].text()})")
		}
	}
	logger.debug "na vervormen meldingKenmerk"
	// Doorlopen van het zakenmagazijn bericht en dat vullen in het TMF bericht
	zmDocument[soapenv.Body][zmo.ZaakQueryResponse][zmo.Zaak].each { attribuut ->
		if (!attribuut.children().isEmpty()) {
			logger.debug "Toevoegen van een response"
			maakNieuweTerugMeldResponse(tmfDocument[soapenv.Body][oph.ophalenMeldingStatusResponse][oph.statusResponseList][0], attribuut, payload[2])
		}
	}
} else if (tmfBericht.contains("ophalenMeldingKenmerkResponse")) {
	// Doorlopen van het zakenmagazijn bericht en dat vullen in het TMF bericht
	logger.info "Dit moet nog gebouwd worden."
}

//
//generate output XML 
//
/*
	TMFPortal doesnt deal with newlines and whitespaces that XmlNodePrinter spits out
    see also: http://jira.codehaus.org/browse/GROOVY-3265
    This is fixed in groovy version 1.6-rc-2 and up
    When Mule is ugraded (http://www.mulesoft.org/jira/browse/MULE-4194), the workaround code can be replaced by:
    	def emptyString = ""
    	def addLines = false
    	new XmlNodePrinter(new IndentPrinter(printWriter, emptyString, addLines))
*/
def writer = new StringWriter()
def printWriter = new PrintWriter(writer)
def emptyString = ""
def xmlNodePrinter = new XmlNodePrinter(printWriter, emptyString)
xmlNodePrinter.print(tmfDocument)
def result = writer.toString()

// Workaround: de namespace wsa zit alleen op sommige van de wsa elementen die niet genest zijn, zet de wsa
// namespace op de soap Header. Waarschijnlijk gefixt in Groovy 1.6.x
logger.debug("result response: ${result}")
logger.debug " "

//
// Zorg ervoor dat de soapenv namespace aanwezig is
//
if (!(result =~ /<.*xmlns:soapenv=.*>/)) {
  logger.debug "Toevoegen 'xmlns:soapenv=' namespace"
  result = result.replace('<soapenv:Envelope', '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" ')
}

//
// Voeg de wsa addressing toe
//
result = result.replace('<soapenv:Header', '<soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing"')

//
// Zorg ervoor dat het RelationshipType aanwezig is
//
//if (!(result =~ /<.*RelationshipType=.*>/)) {
//  result = result.replace('<wsa:RelatesTo', '<wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply"')
//}

// 
//Workaround: can be removed when Groovy in Mule is updated to 1.6.x
//
def replaced = result.replaceAll("\r", "").replaceAll("\n", "")

logger.debug "Ophaal response merge result: ${result}"
logger.debug "Ophaal response merge replaced (workaround with no newlines/whitespace): ${replaced}"

return replaced

def maakNieuweTerugMeldResponse(parent, node, zaakDetails) {
	def logger = Logger.getLogger("rtmfguc.script.OphalenResponseMerger")
	def zmo = new groovy.xml.Namespace('http://www.interaccess.nl/webplus/statuswfm_v2', 'zmo')
	def xmlParser = new XmlParser()
    xmlParser.setTrimWhitespace(true)
    def soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
    def aan = new groovy.xml.Namespace('http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd', 'aan')
	// zoek de zaakDetail obv ZaakIdentificatie
	def zaakDetailBody = xmlParser.parseText(zaakDetails.getPayload().get(node[zmo.Zaakidentificatie].text()))[soapenv.Body]
	def formulierBody = zaakDetailBody[zmo.ZaakDetailResponse][zmo.Formulier][soapenv.Envelope][soapenv.Body]
	
	// achterhaal welke prefix we moeten gebruiken
	prefix = parent.name().getPrefix()
	logger.debug "Begin met aanmaken TerugMeldResponseList"
	Node terugmeldResponseList = new Node(parent, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "terugmeldResponseList", prefix))
	Node terugmeld = new Node(terugmeldResponseList, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "terugmeld", prefix))
	Node meldingKenmerk = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "meldingKenmerk", prefix), node[zmo.Trefwoord].text() + " - (K:${node[zmo.Zaakidentificatie].text()})")
	Node naamContact = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "naamContact", prefix), formulierBody[aan.terugmelding][aan.contactInfo][aan.naam].text())
	Node telefoonContact = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "telefoonContact", prefix), formulierBody[aan.terugmelding][aan.contactInfo][aan.telefoon].text())
	Node emailContact = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "emailContact", prefix), formulierBody[aan.terugmelding][aan.contactInfo][aan.email].text())
	Node tijdstempelAanlever = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "tijdstempelAanlever", prefix), formulierBody[aan.terugmelding][aan.tijdstempelAanlevering].text())
	Node tagBR = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "tagBR", prefix), node[zmo.Kenmerk].'**'.find{it[zmo.kenmerkBron].text() == "basisRegistratie"}[zmo.kenmerk].text())
	Node tagObject = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "tagObject", prefix), node[zmo.Kenmerk].'**'.find{it[zmo.kenmerkBron].text() == "objectTag"}[zmo.kenmerk].text())
	Node idObject = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "idObject", prefix), node[zmo.Kenmerk].'**'.find{it[zmo.kenmerkBron].text() == "objectIdentificatie"}[zmo.kenmerk].text())
	Node objectAttributen = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "objectAttributen", prefix))
		// Deze gegevens moeten we uit de resultaten halen van de ZaakDetail uit payload[2]
		formulierBody[aan.terugmelding][aan.attributen].each { attribuut ->
			logger.debug "Nieuw attribuut wordt toegevoegd. ${attribuut[aan.attribuutIdentificatie].text()}"
			Node objectAttribuutList = new Node(objectAttributen, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "objectAttribuutList", prefix))
			Node idAttribuut = new Node(objectAttribuutList, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "idAttribuut", prefix), attribuut[aan.attribuutIdentificatie].text())
			Node betwijfeldeWaarde = new Node(objectAttribuutList, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "betwijfeldeWaarde", prefix), attribuut[aan.betwijfeldeWaarde].text())
			Node voorstel = new Node(objectAttribuutList, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "voorstel", prefix), attribuut[aan.voorstel].text())
		}
	logger.debug("de toelichting is: ${formulierBody[aan.terugmelding][aan.toelichting].text()}")
	Node toelichting = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "toelichting", prefix), formulierBody[aan.terugmelding][aan.toelichting].text())
	Node status = new Node(terugmeld, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "status", prefix), transformToTmf(node[zmo.Resultaatcode].text()))
	Node terugmeldMCore = new Node(terugmeldResponseList, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "terugmeldMCore", prefix))
	Node tmfKenmerk = new Node(terugmeldMCore, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "tmfKenmerk", prefix), node[zmo.Zaakidentificatie].text())
	Node berichtSoort = new Node(terugmeldMCore, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "berichtSoort", prefix), "Terugmelding")
	Node idOrganisatie = new Node(terugmeldMCore, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "idOrganisatie", prefix))
	Node naamOrganisatie = new Node(terugmeldMCore, new QName("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd", "naamOrganisatie", prefix))
	
	
}

def getTMFBericht(String bericht1, String bericht2) {
    return bericht1.contains("http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd") ? bericht1: bericht2
}

def getZMBericht(String bericht1, String bericht2) {
    return bericht1.contains("http://www.interaccess.nl/webplus/statuswfm_v2") ? bericht1 : bericht2
}

/**
 * Methode voor het transformeren van ZM status naar TMF status
 * TODO RW FIXEN!!!
 */
def transformToTmf(tmfStatus) {
	tmfStatus = tmfStatus.equals('ingetr') ? 'ingetrokken' : tmfStatus
	tmfStatus = tmfStatus.equals('onderzoek') ? 'in onderzoek' : tmfStatus
	tmfStatus = tmfStatus.equals('nietontv') ? 'niet ontvankelijk' : tmfStatus    
    return tmfStatus
}