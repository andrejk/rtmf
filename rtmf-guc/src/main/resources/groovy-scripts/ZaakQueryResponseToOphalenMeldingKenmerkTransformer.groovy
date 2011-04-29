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

def logger = Logger.getLogger("rtmfguc.script.OphalenZmTransformer")
//
// Dit groovy script moet een TMF verzoek tot ophalen van status informatie omzetten
// naar een status (ophalenMeldingStatus) en detail (OphalenMeldingKenmerk) informatie verzoek voor het zakenmagazijn.
//

assert payload != null : "De payload is null"
logger.debug "De payload: ${payload}"

//basis payload voor het ophalen van een detail in het zakenmagazijn
baseZaakDetailRequestPayload = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stat="http://www.interaccess.nl/webplus/statuswfm_v2">
   <soapenv:Header/>
   <soapenv:Body>
      <stat:ZaakDetailRequest>
         <stat:Identificatie>%s</stat:Identificatie>
         <stat:Oge_id>0</stat:Oge_id>
      </stat:ZaakDetailRequest>
   </soapenv:Body>
</soapenv:Envelope>
"""

//basis payload voor het zoeken naar zaken in het zakenmagazijn.
baseZaakQueryPayload = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stat="http://www.interaccess.nl/webplus/statuswfm_v2">
	<soapenv:Header/>
	<soapenv:Body>
	   <stat:ZaakQuery returnVerzoekIdentificatie="false">
	      <stat:Zaaktypecode exact="true">TMDG</stat:Zaaktypecode>
	      %s%s%s%s%s
	       <stat:Oge_id>0</stat:Oge_id>
	   </stat:ZaakQuery>
	</soapenv:Body>
</soapenv:Envelope>
"""
// Dit zijn de variabele delen van ZaakQuery
baseZaakidentificatie = '<stat:Zaakidentificatie exact="true">%s</stat:Zaakidentificatie>'
baseResultaatCode = '<stat:Resultaatcode exact="true">%s</stat:Resultaatcode>'
baseStartdatumVanaf = '<stat:StartdatumVanaf>%s</stat:StartdatumVanaf>'
baseStartdatumTot = '<stat:StartdatumTot>%s</stat:StartdatumTot>'
baseMeldingKenmerk = """<stat:Trefwoord>%s</stat:Trefwoord>"""

// uitlezen van de gegevens uit het TMFBericht
if (payload.contains("ophalenMeldingStatus")){
	def tmfBericht = new XmlSlurper().parseText(payload)
	tmfKenmerk = tmfBericht.Body.ophalenMeldingStatus.tmfKenmerk.text()
	meldingKenmerk = tmfBericht.Body.ophalenMeldingStatus.meldingKenmerk.text()
	status = tmfBericht.Body.ophalenMeldingStatus.meldingStatusRequest.status.text()
	vanDatum = transformDatum(tmfBericht.Body.ophalenMeldingStatus.meldingStatusRequest.vanDatum.text())
	totDatum = transformDatum(tmfBericht.Body.ophalenMeldingStatus.meldingStatusRequest.totDatum.text())
	// De statusen van ZM en TMF zijn niet exact het zelfde. Zet deze om.
	status = transformToZakenMagazijn(status)
	
	// Als je een element binnen hebt gekregen zet je die om in een deel van de payload.
	tmfKenmerk = isEmptyOrNull(tmfKenmerk) ? "" : String.format(baseZaakidentificatie, tmfKenmerk)
	meldingKenmerk = isEmptyOrNull(meldingKenmerk) ? "" : String.format(baseMeldingKenmerk, meldingKenmerk)
	status = isEmptyOrNull(status) ? "" : String.format(baseResultaatCode, status)
	vanDatum = isEmptyOrNull(vanDatum) ? "" : String.format(baseStartdatumVanaf, vanDatum)
	totDatum = isEmptyOrNull(totDatum) ? "" : String.format(baseStartdatumTot, totDatum)
	
	logger.debug "het tmfKenmerk: ${tmfKenmerk}"
	logger.debug "het meldingKenmerk: ${meldingKenmerk}"
	logger.debug "het status: ${status}"
	logger.debug "het vanDatum: ${vanDatum}"
	logger.debug "het totDatum: ${totDatum}"
	
	// Maar de payload op basis van de base en alle deel onderdelen. Deze zijn alleen gevult als ze gebruikt worden.
	result = String.format(baseZaakQueryPayload, tmfKenmerk, status, vanDatum, totDatum, meldingKenmerk)
	logger.debug "result payload voor ophalen status: ${result}"
} else if (payload.contains("ophalenMeldingKenmerk")) {
	def tmfBericht = new XmlSlurper().parseText(payload)
	meldingKenmerk = tmfBericht.Body.ophalenMeldingKenmerk.meldingKenmerk.text()
	result = String.format(baseZaakDetailRequestPayload, meldingKenmerk)
}

return result

/**
 * Methode voor het transformeren van TMF status naar ZM status
 */
def transformToZakenMagazijn(tmfStatus) {
	result = tmfStatus.equals('ingetrokken') ? 'ingetr' : tmfStatus
	result = tmfStatus.equals('in onderzoek') ? 'onderzoek' : tmfStatus
	result = tmfStatus.equals('niet ontvankelijk') ? 'nietontv' : tmfStatus
}

/**
 * Het datum formaat van ZM is inclusief datum.
 */
def transformDatum(inDatum) {
	result = ""
	if (!isEmptyOrNull(inDatum)) {
		result = inDatum.substring(0,10) + 'T00:00:00.000'
	}
	return result
}

def isEmptyOrNull(value) {
	if (value == null || value.equals("")) {
		return true
	}
	return false
}