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

    storeGMBericht(payload)
	def logger = Logger.getLogger("rtmfguc.script.CacheTransformer")
	logger.debug "Payload in de cacheTransformer: ${payload}"
    return payload
	
    def String getBerichtType(payloadAsString) {
        return payloadAsString.contains("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd") ? 'TMF' : 'GM'
    }
    
    def void storeTMFBericht(String tmfBericht) {
    	
        def slurper = new XmlSlurper()
        def document = slurper.parseText(tmfBericht).declareNamespace(stel: 'http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd', soapenv: 'http://schemas.xmlsoap.org/soap/envelope/')
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
    	def logger = Logger.getLogger("rtmfguc.script.CacheTransformer")
        // BasisregistratieList
        if (document.'soapenv:Body'.'stel:getBasisregistratieListResponse'.size() == 1) {
            document.'soapenv:Body'.'stel:getBasisregistratieListResponse'.'stel:basisregistratieList'.each { basisregistratie ->
            	logger.debug "Adding BasisregistratieList to cache: ${basisregistratie.'stel:tag'}"
            	def noStufpath = null
            	def noBevraagbaar = null
                stelselCatalogusCache.addKey(basisregistratie.'stel:tag'.text(), bron, noBevraagbaar, noStufpath, basisregistratie.'stel:naam'.text())
            }
        }
        // ObjectTypeList
        else if (document.'soapenv:Body'.'stel:getObjectTypeListResponse'.size() == 1) {
            document.'soapenv:Body'.'stel:getObjectTypeListResponse'.'stel:objectTypeList'.each { objectType ->
            	logger.debug "Adding ObjectTypeList to cache: ${objectType.'stel:tag'}"
            	def noStufpath = null
                stelselCatalogusCache.addKey(objectType.'stel:tag'.text(), bron, objectType.'stel:bevraagbaar'.text().equals('true'), noStufpath,objectType.'stel:naam'.text()) 
            }
        }
        // ObjectInfo
        else if (document.'soapenv:Body'.'stel:getObjectInfoResponse'.size() == 1) {
            document.'soapenv:Body'.'stel:getObjectInfoResponse'.'stel:objectInfo'.'stel:attributen'.each { attribute ->
            	logger.debug "Adding objectInfo to cache: ${attribute.'stel:code'}"
            	def noBevraagbaar = null
            	// alleen stuf paden in GM
            	def stufpath = (bron == 'GM' ? attribute.'stel:stufpath'.text() : null)
                stelselCatalogusCache.addKey(attribute.'stel:code'.text(), bron, noBevraagbaar, stufpath,attribute.'stel:naam'.text())
            }
        }
    }