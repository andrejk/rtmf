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

import nl.rotterdam.rtmf.guc.common.RTMFStringUtils;

import org.apache.log4j.Logger
import org.mule.api.ExceptionPayload
import org.mule.api.MuleMessage

//
// Deze transformer kijkt eerst of er een exception payload is. 
// Indien die er is, zal deze worden teruggegeven. Indien er geen
// exception payload is, dan wordt het payload array doorzocht naar
// OK bericht uit de ZM services boom. Deze wordt dan teruggegeven.
//

def logger = Logger.getLogger("rtmfguc.script.ZmCleanPayloadArray")

logger.debug "\nWelkom bij de ZM payload array cleaner\n\n"

assert payload != null : "De payload is null"
assert message != null : "De message is null"

//
// Do some logging
//
//logger.debug "De payload is: ${payload}\n"
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


String result = null

if (geenExceptiePayload) {	
	logger.debug "Door het payload array heen wandelen"
	String[] pAr = RTMFStringUtils.payloadToArray(payload)
	logger.debug "Lengte pAr: ${pAr.length}"
	pAr.each { p ->
		logger.debug "Payload: ${p}"
		if (p =~ /<.*response.*>/) {
			result = p
			if (result.startsWith("{")) {
				result = result.substring(1);
			}
			if (result.endsWith("}")) {
				result = result.substring(0, result.length() - 1);
			}
			result = result.trim()
		}
	}
} else {
	result = null
}

logger.debug "Hier is het resultaat: \n${result}"


return result
