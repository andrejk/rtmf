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

// Gebruik log4j als logging systeem
def logger = Logger.getLogger("rtmfguc.script.ZakenmagazijnResponseOkMock")
assert payload != null : "Geen payload gevonden"

if (zervice==null) {
	logger.debug "Er is niet opgegeven voor welke service deze response zou moeten zijn :-("
} else {
	logger.debug "Deze Zakenmagazijn response is voor de service: ${zervice}"
}

String result = """<env:Envelope xmlns:env="http://schemas.xmlsoap.org/soap/envelope/">
<env:Header/>
<env:Body>
   <ns2:Retour xmlns:ns2="http://www.interaccess.nl/webplus/statuswfm_v2">
      <ns2:Operatiestatus>operatie succesvol</ns2:Operatiestatus>
      <ns2:Operatiefout/>
   </ns2:Retour>
</env:Body>
</env:Envelope>
"""

return result