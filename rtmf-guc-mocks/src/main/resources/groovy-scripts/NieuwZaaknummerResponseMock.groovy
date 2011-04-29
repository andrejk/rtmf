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
import groovy.text.GStringTemplateEngine;
import org.apache.log4j.Logger

final String NOTHING = "No result"

def logger = Logger.getLogger("rtmfguc.mock.tmf.NieuwZaaknummerResponseMock")

String result = NOTHING
String SOAP_NS_URI = "http://schemas.xmlsoap.org/soap/envelope/"
String SOAP_ZAAK_URI = "http://www.rotterdam.nl/zkn/zaaknummer"

logger.debug "De binnenkomende payload is: ${payload}"
    	
if (payload =~ /<.*nieuwZaaknummerRequest.*>/) {

	logger.debug "'nieuwZaaknummerRequest' gevonden in de payload"
	
	def xmlParser = new XmlParser()
	xmlParser.setTrimWhitespace(true)
	def document = xmlParser.parseText(payload)
	def soapenv = new groovy.xml.Namespace(SOAP_NS_URI, 'soapenv')
	def zaak = new groovy.xml.Namespace(SOAP_ZAAK_URI, 'zaak')

	logger.debug "Document: ${document}"
	logger.debug "Body: ${document[soapenv.Body]}"
	logger.debug "Zaak: ${document[soapenv.Body][zaak.nieuwZaaknummerRequest]}"
	if (document[soapenv.Body][zaak.nieuwZaaknummerRequest].size() == 1) {
		logger.debug "Zoeken naar het jaar nummer"
		String jaarKort = document[soapenv.Body][zaak.nieuwZaaknummerRequest][zaak.jaartal].text()
		if (jaarKort.length()>2) {
			jaarKort = jaarKort.substring(2)
		}
		logger.debug "Het jaarnummer is: ${jaarKort}"
		logger.debug "Zoeken naar de maand"
		String maandNummer = document[soapenv.Body][zaak.nieuwZaaknummerRequest][zaak.maand].text()
		logger.debug "De maand is: ${maandNummer}"
		def binding = ["jaarKort": jaarKort, 
		               "maandNummer": maandNummer]
	    //
	    // Haal de template op
	    //
	    logger.debug "Ophalen template"
	    String templateToLoad = "templates/NieuwZaaknummerResponse.template"
	    def stream = getClass().getResourceAsStream(templateToLoad)
	    assert stream != null : "Template niet gevonden: ${templateToLoad}"
	    def xmlTemplate = stream.text 
	
	    //
	    // Vul de template in
	    def engine = new GStringTemplateEngine()
	    template = engine.createTemplate(xmlTemplate).make(binding)
	
	    /// explicitly set the type of result to String to prevent GString problems
	    result = template.toString()
	} else {
		result = "Foutje"
	}

}

assert result != NOTHING : "NieuwZaaknummer repsonse Mock geeft geen resultaat!"

logger.debug "reply: ${result}"
	
return result