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

def logger = Logger.getLogger("rtmfguc.script.RestoreMeldingKenmerkTransformer")
//
// Dit groovy script moet het meldingkenmerk welke we in de 
// ophalenMergeTransformer aanpassen weer terug brengen naar het oorspronkelijke formaat.
//

assert payload != null : "De payload is null"
logger.debug "De payload: ${payload}"

def xmlParser = new XmlParser()
xmlParser.setTrimWhitespace(true)
def soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
def oph = new groovy.xml.Namespace('http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd', 'oph')
def tmfDocument = xmlParser.parseText(payload)
//basis payload voor het ophalen van een detail in het zakenmagazijn
if (payload.contains("ophalenMeldingKenmerk")) {
	meldingKenmerk = tmfDocument[soapenv.Body][oph.ophalenMeldingKenmerk][oph.meldingKenmerk].text()
	meldingKenmerk = meldingKenmerk.substring(0, meldingKenmerk.lastIndexOf(" - (B"))
	tmfDocument[soapenv.Body][oph.ophalenMeldingKenmerk][oph.meldingKenmerk][0].setValue(meldingKenmerk)
}

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