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
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;

//
// Dit script vertaald de response van het verzenden m.b.v. SMTP van een terugmelding naar 
// een bericht wat de Tmfortaal begrijpt.
//
// Gebruik log4j als logging systeem
def logger = Logger.getLogger("rtmfguc.script.ZmResponseTransformer")

logger.debug "\nWelkom bij de ZM response transformer\n\n"

assert payload != null : "De payload is null"
assert message != null : "De message is null"

//
// Do some logging
//
logger.debug "De payload is: ${payload}\n"
logger.debug "Testen op aanwezigheid van een ExceptionPayload"
MuleMessage mMessage = (MuleMessage)message;
assert mMessage != null : "De MuleMessage is null"
boolean geenExceptiePayload = (null==mMessage.getExceptionPayload())
if (geenExceptiePayload) {
	logger.debug "Geen exception payload gevonden"
} else {
	logger.debug "Er IS een exception payload gevonden !!!"
	logger.debug "Exception code   : " + mMessage.getExceptionPayload().getCode()
	logger.debug "Exception message: " + mMessage.getExceptionPayload().getMessage()
}

// probeer het MessageID te vinden in het oorspronkelijke bericht
def finder = (payload =~ /<.*MessageID.*>(.*)<\/.*MessageID>/)
//"expect one result: one messageID"
assert  finder.count == 1, "ZmResponseTransformer: Geen MessageID gevonden"
//finder[0][0] is the whole message
def String wsaMessageId = finder[0][1] 

//
// Check voor een terugmelding
if (payload =~ /<.*terugmelding.*>/) {
	logger.info "match terugmelding"
	if (geenExceptiePayload) {
	// Vul het MessageID in
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:defrep="http://tmfportal.ovsoftware.com/services/defaultreply.xsd">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing"> 
               <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding</wsa:Action> 
               <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo> 
               <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To> 
           </soapenv:Header> 
            <soapenv:Body> 
               <defrep:response> 
                  <text>OK</text> 
               </defrep:response> 
            </soapenv:Body> 
         </soapenv:Envelope>
    """
	} else {
		// Vul het MessageID in
		// Vul de fout code in
		// Vul de foutboodschap in
	    result = """
	         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
	            xmlns:defrep="http://tmfportal.ovsoftware.com/services/defaultreply.xsd">
	            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
	            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding</wsa:Action>
	            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
	            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
	           </soapenv:Header>
	           <soapenv:Body>
	               <defrep:response>
	                  <text>
	      	    		Er is een fout opgetreden. Foutcode: ${mMessage.getExceptionPayload().getCode()} Foutboodschap: ${mMessage.getExceptionPayload().getMessage()}</text>
	               </defrep:response>
	            </soapenv:Body>
	         </soapenv:Envelope>
	    """		
	}
} else {
	// Geen terugmelding, dan maar gewoon doorgeven.
	result = payload
}

//
// En klaar is Klare
//
//
//Workaround: can be removed when Groovy in Mule is updated to 1.6.x
//
logger.debug "Het resultaat is: ${result}"
def replaced = result.replaceAll("\r", "").replaceAll("\n", "")
logger.debug "Terugmeld response merge replaced (workaround with no newlines/whitespace): ${replaced}"

return replaced