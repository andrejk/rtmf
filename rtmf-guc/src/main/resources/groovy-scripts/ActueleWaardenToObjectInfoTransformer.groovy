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
/*
 * Deze transformer zorgt ervoor dat bij een call naar actuele waarden een 
 * call gedaan kan worden naar de ObjectInfo om de attributen toe te voegen.
 * Dit is nodig omdat de TMFPortaal verwacht dat alle elementen, dus ook de elementen
 * zonder actuele waarden, terug komen. Omdat TMF geen actuele waarden ondersteunt halen 
 * we de attributen op om de lijst compleet te maken.
 */
import org.apache.log4j.Logger
import groovy.text.GStringTemplateEngine

def logger = Logger.getLogger("rtmfguc.script.ActueleWaardenToObjectInfoTransformer")

logger.debug "Binnenkomende payload: ${payload}"

def xmlSlurper = new XmlSlurper()
def actueleWaardenRequest = xmlSlurper.parseText(payload)

String brTag = actueleWaardenRequest.Body.bevragen.brTag.text()
String objectTag = actueleWaardenRequest.Body.bevragen.objectTag.text()
String messageId = actueleWaardenRequest.Header.MessageID.text()

logger.debug "Gevonden: brTag: '${brTag}' objectTag: '${objectTag}'"

def binding = ["messageId": messageId, "brTag": brTag, "objectTag": objectTag] 
String templateToLoad = "templates/ActueleWaardeNaarObjectInfo.template"

def stream = getClass().getResourceAsStream(templateToLoad)
assert stream != null : "Template niet gevonden: ${templateToLoad}"
def xmlTemplate = stream.text
def engine = new GStringTemplateEngine()
def template = engine.createTemplate(xmlTemplate).make(binding)

String result = template.toString();

logger.debug "Result: ${result}"

return result