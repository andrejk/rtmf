import org.mule.api.transport.PropertyScope
import org.apache.log4j.Logger
    // De preCache zorgt ervoor dat we bij het opslaan van de elementen in de 
    // cache kunnen achterhalen wat de parent was van de aanroep.
    // Dit hebben we nodig omdat een tag van een element opzichzelf niet uniek is.
    // Bij het response bericht van ObjectTypeList krijg je echter niet mee van welke basisregistratie
    // dit de ObjectTypen van zijn.
    def slurper = new XmlSlurper()
    def document = slurper.parseText(payload)
    processDocument(document)
    return payload
    
    /**
     * Hier wordt het document doorlopen en de tag wordt in de message property gezet.
     */
    def processDocument(document) {
        def logger = Logger.getLogger("rtmfguc.script.PreCacheTransformer")
        // ObjectTypeList
        if (document.Body.getObjectTypeList.size() == 1) {
            String brTag = document.Body.getObjectTypeList.BRTag.text()
            logger.debug "De waarde van de brTag is: ${brTag}"
            message.setProperty("parentTag", brTag, PropertyScope.OUTBOUND )
        }
    }