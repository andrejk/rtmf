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
import org.mule.api.MuleMessage

final String NOTHING = "No result"

def logger = Logger.getLogger("rtmfguc.mock.tmf.NieuwZaaknummerResponseTransformer")

String result = NOTHING
String payloadAsString = payload

logger.debug "De binnenkomende payload is: ${payload}"

//
//Slurp de payload om makkelijk bij de verschillende onderdelen te kunnen komen.
//
def xmlSlurper = new XmlSlurper()
def zakenNummerRequest = xmlSlurper.parseText(payload)

String zaaknummer =  zakenNummerRequest.Body.zaaknummerResponse.text()
assert zaaknummer != null : "Geen nieuw zaaknummer gevonden!"
logger.debug "Het zaaknummer is: ${zaaknummer}"

MuleMessage muleMessage = (MuleMessage)message
muleMessage.setProperty("zaaknummer", zaaknummer)

return muleMessage
