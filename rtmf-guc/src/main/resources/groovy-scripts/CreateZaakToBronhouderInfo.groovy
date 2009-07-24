
import org.apache.log4j.Logger
import org.mule.api.MuleMessage;

def logger = Logger.getLogger("rtmfguc.script.CreateZaakToBronhouderInfoTransformer")

// Deze transformer haalt uit het payload array 2 payloads:
// Payload 1: Het terugmeld bericht
// Payload 2: Het zaaknummer bericht.
//
// Van deze 2 payloads worden wordt de terugmelding teruggegeven aan de aanroeper, en 
// uit het zaaknummer bericht bericht wordt het zaaknummer gehaald , en dit wordt als 
// property aan de mulemessage gehangen.
//

def result = null
def terugmelding = null
def zNummer = null

assert payload != null : "Payload is null"

payload.each { p ->
	
	//logger.debug "Payload: ${p}"

	if (p =~ /<.*terugmelding.*>/) {
		logger.debug "match terugmelding"
		if (terugmelding==null) {
			logger.debug "Gevonden terugmelding: ${p}"
			terugmelding = p
		}
	}

	if (p =~ /<.*zaaknummerResponse.*>/) {
		logger.info "match zaaknummerResponse"
		//
		// Maak een document van de payload
		//
		def soapenv = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soapenv')
		def zaaknummer = new groovy.xml.Namespace('http://www.rotterdam.nl/zkn/zaaknummer', 'zaaknummer')
		def znBericht = p
		def xmlParser = new XmlParser()
		xmlParser.setTrimWhitespace(true)
		def znDocument = xmlParser.parseText(znBericht)
	
		if (znDocument[soapenv.Body][zaaknummer.zaaknummerResponse].size() == 1) {
			zNummer = znDocument[soapenv.Body][zaaknummer.zaaknummerResponse].text()
		}

		assert zNummer != null : "Geen zaaknummer bericht gevonden in de CreateZaakToBronhouderInfoTransformer"
		MuleMessage mMessage = (MuleMessage)message
		mMessage.setProperty("zaaknummer", zNummer)
		message = mMessage // is dit nodug ???
		
	}
}

assert terugmelding != null : "Geen terugmelding bericht gevonden in de CreateZaakToBronhouderInfoTransformer"

return terugmelding
