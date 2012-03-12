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
import nl.rotterdam.rtmf.guc.common.DateUtils


//
// Geen 'def' voor de logger, anders is de logger variabele
// niet bekent in de closure: datumTijdNaarZakenmagazijnFormaat
//
logger = Logger.getLogger("rtmfguc.script.TerugmeldingToStapTransformer")

def datumTijdNaarZakenmagazijnFormaat(String datumIn) {
	logger.debug "Datum in : ${datumIn}"
	String datumUit = datumIn.substring(0, datumIn.indexOf(".")).concat(".000+01:00");
	logger.debug "Datum uit: ${datumUit}"
	return datumUit
}

String forUpdateType = null


assert payload[0] != null : "Eerste payload is null"
assert payload[1] != null : "Tweede payload is null"

logger.debug "=================================================================================================="
logger.debug "En hier zijn de payloads"
logger.debug "=================================================================================================="
payload.eachWithIndex(){ p, i ->
	logger.debug "Payload[${i}]: ${p}"
	logger.debug "=================================================================================================="
}

//
//Definieer de gebruikte namespaces
//
def soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
def zaaknummer = new groovy.xml.Namespace('http://www.rotterdam.nl/zkn/zaaknummer', 'zaaknummer')
def aanmeld = new groovy.xml.Namespace('http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd')

//
//Definieer wat werk variabelen
//
String beginDatum = null
String zaakNummer = null

def resultaatCodes = "gemeld"
def resultaatOmschrijvingen = "De terugmelding is gemeld aan de bronhouder"

def resultaatCodeUpdate = "gemeld"
def stapTypeCodeUpdate = "ONTVANGEN"
def resultaatomschrijvingUpdate = "De terugmelding is gemeld aan de bronhouder"                               

def stapOmschrijvingCreate = "Het beoordelen van de terugmelding door de bronhouder"
def stapTypeCodeCreate = "BEOORDELEN"



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
			beginDatum = tDocument[soapenv.Body][aanmeld.terugmelding][aanmeld.tijdstempelAanlevering].text()
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
		
		logger.debug "znDocument: ${znDocument}"
		logger.debug "zr: ${znDocument[soapenv.Body][zaaknummer.zaaknummerResponse]}"
		if (znDocument[soapenv.Body][zaaknummer.zaaknummerResponse].size() == 1) {
			zaakNummer = znDocument[soapenv.Body][zaaknummer.zaaknummerResponse].text()
			logger.debug "Gevonden zaaknummer: ${zaakNummer}"
		}
	}
}

assert zaakNummer != null : "ZaakNummer is null"
logger.debug "Het Zaaknummer is: ${zaakNummer}"

//
//Bind de document data aan de template velden
//
def binding = ["Zaakidentificatie": zaakNummer,
                "Resultaatcode": resultaatCodes,
				"Resultaatomschrijving": resultaatOmschrijvingen,
				"Resultaattoelichting": "",
                "Begindatum": datumTijdNaarZakenmagazijnFormaat(beginDatum),
                "StaptypecodeUpdate": stapTypeCodeUpdate,
	        	"ResultaatcodeUpdate": resultaatCodeUpdate,
                "ResultaatomschrijvingUpdate": resultaatomschrijvingUpdate,
                "ResultaattoelichtingUpdate": "",
	        	"Stapeindedatum": DateUtils.getCurrentDateTimeStringValue(),
                "Stapomschrijving": stapOmschrijvingCreate,
	        	"Staptypecode": stapTypeCodeCreate]
assert binding != null : "Binding is null"
	
	
//
// Haal de template op
//
logger.debug "Ophalen template"
String templateToLoad = "templates/ZaakMetStapEnStatusUpdate.template"
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
