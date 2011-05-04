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
import nl.rotterdam.rtmf.guc.bronhouder.bean.BronhouderInfo
import nl.rotterdam.rtmf.guc.bronhouder.catalogus.BronhouderCatalogus
import nl.rotterdam.rtmf.guc.StelselCatalogusCache;


def logger = Logger.getLogger("rtmfguc.script.TerugmeldingToZaakTransformer")
assert payload[0] != null : "Eerste payload is null"
assert payload[1] != null : "Tweede payload is null"

logger.debug "=================================================================================================="
logger.debug "En hier zijn de payloads"
logger.debug "=================================================================================================="
payload.eachWithIndex(){ p, i ->
	logger.debug "Payload[${i}]: ${p}"
	logger.debug "=================================================================================================="
}
payload[0] = payload[0].toString().replace("<?xml version=\"1.0\" ?>","")
logger.debug "=================================================================================================="
logger.debug "Payload 0 na replacing vna xml version"
logger.debug "=================================================================================================="
logger.debug "Payload " + payload[0]
logger.debug "=================================================================================================="

//
//Definieer de gebruikte namespaces
//
def soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
def aanmeld = new groovy.xml.Namespace('http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd')
def zaaknummer = new groovy.xml.Namespace('http://www.rotterdam.nl/zkn/zaaknummer', 'zaaknummer')

class TerugmeldingInfo {
	String startDatum
	String meldingKenmerk
	String basisRegistratie //tagBR
	String objectTag //tagObject
	String objectIdentificatie //idObject
	String contactNaam
	String objectnaam //beschrijving van item

	// Let op: Indien ook het originele terugmelding bericht tussen de <Formulier></Formulier> tags staat,
	// Dan hier ook nog ff de Basisregistratie, obejctTag, en objectId toevoegen
	
	String toString() {
		return "Terugmelding meldingKenmerk: ${meldingKenmerk} tagBR: ${basisRegistratie} tagObject: ${objectTag} idObject: ${objectIdentificatie} StartDatum: ${startDatum} Contact: ${contactNaam} Objectnaam: ${objectnaam}";
	}
}

//
//Definieer wat werk variabelen
//
def tInfo = null
String zaakNummer = null
def formulier = null

payload.each { p ->
	if (p =~ /<.*terugmelding.*>/) {
		logger.info "match terugmelding"
		//
		// Maak een document van de payload
		//
		def tBericht = p
		def xmlParser = new XmlParser()
		xmlParser.setTrimWhitespace(true)
		def tDocument = xmlParser.parseText(tBericht)
		 
		//
		// Haal diverese waarden uit het document en sla ze tijdelijke op
		//
		if (tDocument[soapenv.Body][aanmeld.terugmelding].size() == 1) {
			tInfo = new TerugmeldingInfo(startDatum: tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.tijdstempelAanlevering].text(),
										 meldingKenmerk: tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.meldingKenmerk].text(),
										 basisRegistratie: tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.basisRegistratie].text(),
										 objectTag: tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.objectTag].text(),
										 objectIdentificatie: tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.objectIdentificatie].text(),
										 contactNaam: tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.contactInfo][aanmeld.naam].text(),
										 objectnaam:tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.attributen][aanmeld.objectnaam].text())
			logger.debug("aanpassing gedaan")
			//
			// Maak een terugmeld bericht aan waar de attachments zijn uitgehaald.
			//
			if (tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.attachment].size() > 0) {
				logger.debug "Attachment(s) gevonden"
				tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.attachment].each { attachment ->
					attachment.parent().remove(attachment)
				}

				//
				// Genereer de output XML
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
				xmlNodePrinter.print(tDocument)
				def newDoc = writer.toString()
				
				// Workaround: de namespace wsa zit alleen op sommige van de wsa elementen die niet genest zijn, zet de wsa
				// namespace op de soap Header. Waarschijnlijk gefixt in Groovy 1.6.x 
				newDoc = newDoc.replace('<S:Header', '<S:Header xmlns:wsa="http://www.w3.org/2005/08/addressing"')
				newDoc = newDoc.replace('<To>', '<To xmlns="http://www.w3.org/2005/08/addressing">')
				newDoc = newDoc.replace('<Action>', '<Action xmlns="http://www.w3.org/2005/08/addressing">')
				newDoc = newDoc.replace('<ReplyTo>', '<ReplyTo xmlns="http://www.w3.org/2005/08/addressing">')
				newDoc = newDoc.replace('<MessageID>', '<MessageID xmlns="http://www.w3.org/2005/08/addressing">')

				formulier = newDoc.replaceAll("\r", "").replaceAll("\n", "")
				logger.debug "\n\nAangepaste document:\n${newDoc}\n\n"

			} else {
				logger.debug "Geen Attachments gevonden; Binnenkomende bericht kopieren naar formulier tag"
				formulier = tBericht
			}

		}
	}
	
	if (p =~ /<.*zaaknummerResponse.*>/) {
		logger.info "match zaaknummerResponse"
		//
		// Maak een document van de payload
		//
		def znBericht = p
		def xmlParser = new XmlParser()
		xmlParser.setTrimWhitespace(true)
		def znDocument = xmlParser.parseText(znBericht)
	
		if (znDocument[soapenv.Body][zaaknummer.zaaknummerResponse].size() == 1) {
			zaakNummer = znDocument[soapenv.Body][zaaknummer.zaaknummerResponse].text()
		}
	
	}
}

assert tInfo != null : "TerugmeldingInfo is null"
logger.debug "tInfo: ${tInfo}"
assert zaakNummer != null : "ZaakNummer is null"

//
// Probeer de bronhouder info bovenwater te krijgen
//
assert bronhouderCatalogus != null : "Bronhouder catalogus is niet gevonden"
assert tInfo.basisRegistratie != null : "Basisregistratie niet gevonden; Niet in staat om de BronhouderInfo op te vragen."

BronhouderInfo bhi = null
List bronhouders = ((BronhouderCatalogus)bronhouderCatalogus).getBronhouderInfoByBasisregistratie(tInfo.basisRegistratie)
bhi = bronhouders.isEmpty() ? null : bronhouders.get(0)
assert bhi != null : "Geen BronhouderInfo gevonden."


//
// Haal wat Bronhouder informatie op
//
String naamBronhouder = null
String codeBronhouder = null
naamBronhouder = bhi.getNaam()
codeBronhouder = bhi.getCode()
assert naamBronhouder != null : "Niet mogelijk om de 'naam' van de Bronhouder te achterhalen."
assert codeBronhouder != null : "Niet mogelijk om de 'code' van de Bronhouder te achterhalen."


//
// Stelselcatalogus
//
logger.debug "begin uitvragen stelselcataloguscache"
logger.debug "tInfo.objectTag : " + tInfo.objectTag

assert stelselCatalogusCache != null : "Stelsel catalogus cache is niet gevonden"

def objectnaam = stelselCatalogusCache.getObjectNaam(tInfo.objectTag)
logger.debug "objectnaam uit cataloguscache : " + objectnaam
//
// Bind de document data aan de template velden
//
def binding = ["oehId": codeBronhouder,
               "oehNaam": naamBronhouder,
               "mdwAchternaam": tInfo.contactNaam,
               "Zaakidentificatie": zaakNummer,
               "Startdatum": tInfo.startDatum, 
               "meldingKenmerk": tInfo.meldingKenmerk,
               "basisRegistratie": tInfo.basisRegistratie,
               "objectTag": tInfo.objectTag,
               "objectIdentificatie": tInfo.objectIdentificatie,
			   "objectnaam": objectnaam,
               "formulier": formulier]
assert binding != null : "Binding is null"
	
//
// Haal de template op
//
logger.debug "Ophalen template"
String templateToLoad = "templates/Zaak.template"
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
