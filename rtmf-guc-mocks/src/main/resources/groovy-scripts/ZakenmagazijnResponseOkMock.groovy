
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