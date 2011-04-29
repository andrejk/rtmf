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

//
// Dit script loopt door een Terugmelding bericht heen, en verwijderd de attributen 
// welke niet aanwezig zijn in de gevraagde service. Er zijn twee properties welke
// in de mule-config.xml worden ge-injecteerd:
// 		stelselCatalogusCache - De cache waarin te vinden is of een attribuut in de TMF, het ZM of beide zit
//		service               - Geeft aan voor welke service het bericht moet worden aangepast (TMF of ZM)
//
// Toegevoegde SNEAKY hack: De cache kent alleen GM geen ZM, dus als er om ZM gevraagd wordt, dan de service
//                          omzetten naar GM.
//

// Gebruik log4j als logging systeem
def logger = Logger.getLogger("rtmfguc.script.TerugmeldenSplitter")
assert payload != null
logger.debug "Payload: ${payload}"
assert service != null
def zervice = serviceHack(service)

def inBericht = payload
def xmlParser = new XmlParser()
xmlParser.setTrimWhitespace(true)
def document = xmlParser.parseText(inBericht)

//
// Definieer de gebruikte namespaces
//
def soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
def aanmeld = new groovy.xml.Namespace('http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd')

//
// Kijk of de attributen bij de TMF horen of bij het Rotterdamse Zakenmagazijn
//
if (document[soapenv.Body][aanmeld.terugmelding].size() == 1) {
	document[soapenv.Body][aanmeld.terugmelding][aanmeld.attributen].each { attribute ->

		String attr = null;
		// Haal de attribuutIdentificatie op
		attr = attribute[aanmeld.attribuutIdentificatie].text()
		assert attr != null
		if (attr!=null) {
			//
			// Haal de service op waar deze key bij hoort (Both/TMF/GM)
			//
			String voorService = stelselCatalogusCache.determineServiceCallsForKey(attr)
			assert voorService != null

			logger.debug """
+------------------------------------------------------------
| Attribuut                : ${attr}
| Bericht moet naar        : ${service}
| Gevonden service in cache: ${voorService}"""

			//
			// Controleer of dit element (attribuut) naar de gevraagde service mag worden
			// gezonden. Indoen niet, verwijder dan het gehele attribuut.
			//
			if (voorService != null) {
				if ("Both".equalsIgnoreCase(voorService)) {
					logger.debug "| Both: Yes"
					// Het attribuut behoort zowel tot TMF als ZM. 
					// Landelijk is gaat voor, dus indien deze split
					// wordt aangeroepen voor de GM/ZM, dan dient het
					// attribuut verwijderd te worden. 
					if ("GM".equalsIgnoreCase(zervice)) {
						logger.debug "| Verwijderen attribuut !!!"
						attribute.parent().remove(attribute)
					}
				} else {
					logger.debug "| Both: No"
					if (!voorService.equalsIgnoreCase(zervice)) {
						// Het attribuut behoort niet tot zowel TMF als ZM, EN
						// het attribuut behoort niet tot de gevraagde service,
						// dus verwijderen van het attribuut uit het document
						logger.debug "| Verwijderen attribuut !!!"
						attribute.parent().remove(attribute)
					}
				}
			}
			logger.debug "+------------------------------------------------------------"
		}
	}
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
xmlNodePrinter.print(document)
def result = writer.toString()

// Workaround: de namespace wsa zit alleen op sommige van de wsa elementen die niet genest zijn, zet de wsa
// namespace op de soap Header. Waarschijnlijk gefixt in Groovy 1.6.x 
result = result.replace('<S:Header', '<S:Header xmlns:wsa="http://www.w3.org/2005/08/addressing"')
result = result.replace('<To>', '<To xmlns="http://www.w3.org/2005/08/addressing">')
result = result.replace('<Action>', '<Action xmlns="http://www.w3.org/2005/08/addressing">')
result = result.replace('<ReplyTo>', '<ReplyTo xmlns="http://www.w3.org/2005/08/addressing">')
result = result.replace('<MessageID>', '<MessageID xmlns="http://www.w3.org/2005/08/addressing">')


// TODO Workaround: can be removed when Groovy in Mule is updated to 1.6.x
def replaced = result.replaceAll("\r", "").replaceAll("\n", "")


assert replaced != null

logger.debug """
----------------------------------------------------------------------------
Resultaat na verwerking door terugmeldSplitter
----------------------------------------------------------------------------
Splits resultaat: ${replaced}
----------------------------------------------------------------------------"""

return replaced


// TODO deze hack is wellicht niet nodig
// Verander ZM naar GM
// De cache kent geen ZM, dus wanneer we een ZM krijgen, deze omzetten naar GM.
//
def serviceHack(String svc) {
	String hackedService = null
	if ("ZM".equalsIgnoreCase(svc)) {
		hackedService = "GM"
	} else {
		hackedService = svc
	}
	return hackedService
}

