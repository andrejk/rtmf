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
    // De cache haalt de objecten en attributen uit de berichten om later terug te
    // zoeken uit welke service ze kwamen: TMF of GM.
    storeTMFBericht(payload)
    return payload
	
    def String getBerichtType(payloadAsString) {
        return payloadAsString.contains("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd") ? 'TMF' : 'GM'
    }
    
    def void storeTMFBericht(String tmfBericht) {
        def slurper = new XmlSlurper()
        def document = slurper.parseText(tmfBericht).declareNamespace(stel: 'http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd', soapenv: 'http://schemas.xmlsoap.org/soap/envelope/', stel12: 'http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.2.xsd')
        processDocument(document, 'TMF')
    }
    
    def void storeGMBericht(String gmBericht) {
        def slurper = new XmlSlurper()
        def document = slurper.parseText(gmBericht).declareNamespace(stel: 'http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd', soapenv: 'http://schemas.xmlsoap.org/soap/envelope/')
        processDocument(document, 'GM')
    }
    
    /**
     * Hier wordt het document doorlopen en elk element wordt in de cache gezet
     */
    def processDocument(document, String bron) {
    	private Logger logger = Logger.getLogger("rtmfguc.script.CacheTransformerTMF")
        // BasisregistratieList
        if (document.'soapenv:Body'.'stel:getBasisregistratieListResponse'.size() == 1) {
            document.'soapenv:Body'.'stel:getBasisregistratieListResponse'.'stel:basisregistratieList'.each { basisregistratie ->
                logger.debug "Adding BasisregistratieList to cache: ${basisregistratie.'stel:tag'}"
                stelselCatalogusCache.addKey(basisregistratie.'stel:tag'.text(), bron, null,basisregistratie.'stel:naam'.text())
            }
        }
        // ObjectTypeList
        else if (document.'soapenv:Body'.'stel:getObjectTypeListResponse'.size() == 1) {
            document.'soapenv:Body'.'stel:getObjectTypeListResponse'.'stel:objectTypeList'.each { objectType ->
                logger.debug "Adding ObjectTypeList to cache: ${objectType.'stel:tag'}"
                stelselCatalogusCache.addKey(objectType.'stel:tag'.text(), bron, objectType.'stel:bevraagbaar'.text().equals('true'),objectType.'stel:naam'.text()) 
            }
        }
        // ObjectInfo
        else if (document.'soapenv:Body'.'stel:getObjectInfoResponse'.size() == 1) {
            document.'soapenv:Body'.'stel:getObjectInfoResponse'.'stel:objectInfo'.'stel:attributen'.each { attribute ->
                logger.debug "Adding objectInfo to cache: ${attribute.'stel:code'}"
                stelselCatalogusCache.addKey(attribute.'stel:code'.text(), bron, null,attribute.'stel:naam'.text())
            }
        }
    	// getObjectInfoAndValues
        else if (document.'soapenv:Body'.'stel12:getObjectInfoAndValuesResponse'.size() == 1) {
            document.'soapenv:Body'.'stel12:getObjectInfoAndValuesResponse'.'stel12:objectInfo'.'stel12:attributen'.each { attribute ->
                logger.debug "Adding objectInfo to cache: ${attribute.'stel12:code'}"
                stelselCatalogusCache.addKey(attribute.'stel12:code'.text(), bron, null,attribute.'stel12:naam'.text())
            }
        }
    }