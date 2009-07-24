import org.apache.log4j.Logger
import groovy.text.GStringTemplateEngine
import org.mule.api.MuleMessage;
import nl.rotterdam.rtmf.guc.bronhouder.bean.BronhouderInfo


def logger = Logger.getLogger("rtmfguc.script.TerugmeldingToZaakUpdateTransformer")

assert updateType != null : "Het updateType is null"
String forUpdateType = updateType.toLowerCase()
logger.debug "Het updateType is: ${forUpdateType}"

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
String zaakNummer = null

def resultaatCodes = ["gemeld": "gemeld",
                      "ingetrokken": "ingetrokken"]
def resultaatOmschrijvingen = ["gemeld": "De terugmelding is gemeld aan de bronhouder",
                               "ingetrokken": "Bla bla bla, HIER MOET NOG IETS INTERESSANTS KOMEN TE STAAN, Bla bla bla"]

payload.each { p ->
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

assert zaakNummer != null : "ZaakNummer is null"
logger.debug "Het Zaaknummer is: ${zaakNummer}"

//
//Bind de document data aan de template velden
//
def binding = ["Zaakidentificatie": zaakNummer,
			   "Resultaatcode": resultaatCodes.get("gemeld"),
			   "Resultaatomschrijving": resultaatOmschrijvingen.get("gemeld"),
			   "Resultaattoelichting": ""]
assert binding != null : "Binding is null"
	
	
//
// Haal de template op
//
logger.debug "Ophalen template"
String templateToLoad = "templates/ZaakUpdate.template"
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





