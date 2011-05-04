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
import java.util.Calendar;
import org.mule.api.MuleMessage;

import org.apache.log4j.Logger
import groovy.text.GStringTemplateEngine

def logger = Logger.getLogger("rtmfguc.script.NieuwZaaknummerTransformer")

logger.debug "In de NieuwZaaknummer transformer"
logger.debug "De payload is: ${payload}"

//
//Zet de template bindings
//
logger.debug "Invullen binding waarden ..."
Calendar calendar = Calendar.getInstance();
def binding = ["jaartal": String.valueOf(calendar.get(Calendar.YEAR)), 
               "maand": String.valueOf(calendar.get(Calendar.MONTH) + 1)]

//
// Haal de template op
//
logger.debug "Ophalen template"
String templateToLoad = "templates/NieuwZaaknummer.template"
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


