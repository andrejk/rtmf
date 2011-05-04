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
import org.mule.transport.NullPayload
import org.mule.message.DefaultExceptionPayload
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException

// Gebruik log4j als logging systeem
def logger = Logger.getLogger("rtmfguc.script.ZakenmagazijnResponseTransformer")
assert payload != null : "De payload is null"

logger.debug "=================================================================================================="
logger.debug "En hier is de payload"
logger.debug "=================================================================================================="
logger.debug "Payload: ${payload}"
logger.debug "=================================================================================================="

//
// De inhoud van de response uit het Zakenmagazijn checken.
// Als de response een 'operatieFout' heeft, dan moet het
// response bericht in de ExceptionPayload van de message
// gezet worden.
//
// De responses kunnen twee foermaten hebben:
// 1 - Formaat van de ZakenmagazijnManager services (zaakCreatie, stapCreatie, StatusCreatie)
//     Retour berichten
// 2 - Formaat van de Zaaknummer service
//     Berichten met een SOAP Fault er in
//
//Definieer de gebruikte namespaces
//
def env = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'env')
def retour = new groovy.xml.Namespace('http://www.interaccess.nl/webplus/statuswfm_v2', 'ns2')
def soap = new groovy.xml.Namespace('http://schemas.xmlsoap.org/soap/envelope/', 'soap')
def result = null

if (payload =~ /<.*Retour.*>/) {
	logger.info "match Retour"
	//
	// Maak een document van de payload
	//
	def rBericht = payload
	def xmlParser = new XmlParser()
	xmlParser.setTrimWhitespace(true)
	def rDocument = xmlParser.parseText(rBericht)
	 
	//
	// Haal diverese waarden uit het document en sla ze tijdelijke op
	//
	if (rDocument[env.Body][retour.Retour][retour.Operatiefout].size() == 1) {
		if ((rDocument[env.Body][retour.Retour][retour.Operatiefout].text()!=null) &&
			(!(rDocument[env.Body][retour.Retour][retour.Operatiefout].text()==""))) {
			logger.debug "Binnen"
			logger.debug "De operatiefout is: ${rDocument[env.Body][retour.Retour][retour.Operatiefout].text()}"
			//
			// Operatiefout gevonden
			//
			logger.debug "Operatiefout gevonden; Ophalen MuleMessage"
			MuleMessage mMessage = (MuleMessage)message
			logger.debug "Set een ExceptionPayload"
			mMessage.setExceptionPayload(new DefaultExceptionPayload(new RtmfGucException(payload)))
			message = mMessage // is dit nodig ???
			result = NullPayload.getInstance()	
		} else {
			logger.debug "Geen operatiefout gevonden"
			result = payload
		}
	} else {
		logger.debug "Geen operatiefout gevonden"
		result = payload
	}
	
} else if (payload =~ /<.*Fault.*>/) {
	logger.info "match Fault"
	//
	// Maak een document van de payload
	//
	def fBericht = payload
	def xmlParser = new XmlParser()
	xmlParser.setTrimWhitespace(true)
	def fDocument = xmlParser.parseText(fBericht)

	//
	// Haal diverese waarden uit het document en sla ze tijdelijke op
	//
	if (fDocument[env.Body][env.Fault].size() == 1) {
		logger.debug "Blijkbaar is er een faultcode"
		logger.debug "Fault: ${fDocument[env.Body][env.Fault]}"
		//logger.debug "Code: ${fDocument[env.Body][env.Fault][0].faultcode.text()}"
		
		if ((fDocument[env.Body][env.Fault][0].faultcode.text()!=null) &&
			(!(fDocument[env.Body][env.Fault][0].faultcode.text()==""))) {
			logger.debug "Binnen"
			logger.debug "De faultcode   is: ${fDocument[env.Body][env.Fault][soap.faultcode].text()}"
			logger.debug "De faultstring is: ${fDocument[env.Body][env.Fault][soap.faultstring].text()}"
			//
			// Faultcode gevonden
			//
			logger.debug "Faultcode gevonden; Ophalen MuleMessage"
			MuleMessage mMessage = (MuleMessage)message
			logger.debug "Set een ExceptionPayload"
			mMessage.setExceptionPayload(new DefaultExceptionPayload(new RtmfGucException(payload)))
			message = mMessage // is dit nodig ???
			result = NullPayload.getInstance()	
		} else {
			logger.debug "Geen faultcode gevonden"
			result = payload
		}
	} else {
		logger.debug "Geen Fault gevonden"
		result = payload
	}

} else {
	// Blijkbaar een ander bericht, gewoon doorgaan.
	result = payload
}

assert result != null : "Geen result om terug te sturen."

return result