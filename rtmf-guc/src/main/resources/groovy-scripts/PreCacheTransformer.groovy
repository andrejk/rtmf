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