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


