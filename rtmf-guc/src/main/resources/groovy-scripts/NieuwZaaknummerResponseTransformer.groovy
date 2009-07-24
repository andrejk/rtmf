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
