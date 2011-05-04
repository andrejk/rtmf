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

final String NOTHING = "No result"

def logger = Logger.getLogger("rtmfguc.mock.tmf.stelsel")

String result = NOTHING
String payloadAsString = payload

def finder = (payloadAsString =~ /<.*MessageID.*>(.*)<\/.*MessageID>/)
// "expect one result: one messageID"
assert  finder.count == 1, "StelselBevragenTmfMockServices: de finder heeft geen match gevonden"
// finder[0][0] is the whole message
def wsaMessageId = finder[0][1] 

if (logger.isDebugEnabled()) {
	def text = new StringBuilder("*** properties incoming message ***\n")

	message.getPropertyNames().each { propertyName ->
    	text.append "${propertyName}:${message.getProperty(propertyName)}\n"
	}
	
	text.append "*** incoming message: ***\n"
	text.append payload
	text.append "\n"
	logger.debug text
}
    	
if (payload =~ /<.*getBasisregistratieList.*>/) {
	logger.info "match getBasisregistratieList"
    result = """
<soapenv:Envelope xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
	<soapenv:Header>
		<wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
		<wsa:MessageID>uuid:3e43c9c5-7e96-461a-8a83-95a1deab59b7</wsa:MessageID>
		<wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListResponse</wsa:Action>
		<wsa:RelatesTo>${wsaMessageId}</wsa:RelatesTo>
	</soapenv:Header>
	<soapenv:Body>
		<getBasisregistratieListResponse xmlns="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
			<basisregistratieList>
				<tag>BRI</tag>
				<naam>Basis Registratie Inkomen</naam>
			</basisregistratieList>
			<basisregistratieList>
				<tag>BR1</tag>
				<naam>Basisregistratie - TMF Test</naam>
			</basisregistratieList>
			<basisregistratieList>
				<tag>GBA</tag>
				<naam>Gemeentelijke Basisregistratie Persoonsgegevens</naam>
			</basisregistratieList>
			<basisregistratieList>
				<tag>BRA</tag>
				<naam>Basis Registratie Adressen</naam>
			</basisregistratieList>
			<basisregistratieList>
				<tag>BGR</tag>
				<naam>Basis Gebouwen Registratie</naam>
			</basisregistratieList>
			<basisregistratieList>
				<tag>WOZ</tag>
				<naam>Basisregistratie WOZ</naam>
			</basisregistratieList>
			<basisregistratieList>
				<tag>BRK</tag>
				<naam>Basisregistratie Kadaster</naam>
			</basisregistratieList>
			<!-- nog even voor de "oude" mocks structuur -->
            <basisregistratieList>
                <tag>TMF-REG1</tag>
                <naam>Personen (TMF)</naam>
            </basisregistratieList>
            <basisregistratieList>
                <tag>TMF-REG2</tag>
                <naam>Vervoer (TMF)</naam>
            </basisregistratieList>
		</getBasisregistratieListResponse>
	</soapenv:Body>
</soapenv:Envelope>
    """
}
else if (payload =~ /<.*getObjectTypeList.*>/ && payload.contains("TMF-REG1")) {
	logger.info "match getObjectTypeList & TMF-REG1"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stel="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeList</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
            <soapenv:Body>
            <stel:getObjectTypeListResponse>
               <!--Zero or more repetitions:-->
               <stel:objectTypeList>
                  <stel:tag>TMF-PERSOON</stel:tag>
                  <stel:naam>Persoon</stel:naam>
                  <stel:bevraagbaar>false</stel:bevraagbaar>
                  <stel:instructie>TEST-INSTRUCTIE 1</stel:instructie>
               </stel:objectTypeList>
               <stel:objectTypeList>
                  <stel:tag>TMF-GEBOUW</stel:tag>
                  <stel:naam>Gebouw</stel:naam>
                  <stel:bevraagbaar>false</stel:bevraagbaar>
                  <stel:instructie>TEST-INSTRUCTIE 2</stel:instructie>
               </stel:objectTypeList>
               <stel:objectTypeList>
                  <stel:tag>TMF-VERGUNNING</stel:tag>
                  <stel:naam>Vergunning</stel:naam>
                  <stel:bevraagbaar>false</stel:bevraagbaar>
                  <stel:instructie>TEST-INSTRUCTIE 3</stel:instructie>
               </stel:objectTypeList>
            </stel:getObjectTypeListResponse>
         </soapenv:Body>
      </soapenv:Envelope>
   """
}
else if (payload =~ /<.*getObjectTypeList.*>/ && payload.contains("TMF-REG2")) {
	logger.info "match getObjectTypeList & TMF-REG2"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stel="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeList</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
           <soapenv:Body>
            <stel:getObjectTypeListResponse>
               <!--Zero or more repetitions:-->
               <stel:objectTypeList>
                  <stel:tag>TMF-AUTO</stel:tag>
                  <stel:naam>Auto</stel:naam>
                  <stel:bevraagbaar>false</stel:bevraagbaar>
                  <stel:instructie>TEST-INSTRUCTIE 1</stel:instructie>
               </stel:objectTypeList>
               <stel:objectTypeList>
                  <stel:tag>TMF-VRACHTWAGEN</stel:tag>
                  <stel:naam>Vrachtwagen</stel:naam>
                  <stel:bevraagbaar>false</stel:bevraagbaar>
                  <stel:instructie>TEST-INSTRUCTIE 2</stel:instructie>
               </stel:objectTypeList>
               <stel:objectTypeList>
                  <stel:tag>TMF-BUS</stel:tag>
                  <stel:naam>Bus</stel:naam>
                  <stel:bevraagbaar>false</stel:bevraagbaar>
                  <stel:instructie>TEST-INSTRUCTIE 3</stel:instructie>
               </stel:objectTypeList>
            </stel:getObjectTypeListResponse>
         </soapenv:Body>
     </soapenv:Envelope>
    """
}
else if (payload =~ /<.*getObjectTypeList.*>/ && payload.contains("GBA")) {
	logger.info "match getObjectTypeList & GBA"
    result = """
    	<soapenv:Envelope xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
	<soapenv:Header>
		<wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
		<wsa:MessageID>uuid:a290bf2e-918e-461e-b40e-c633e97962ce</wsa:MessageID>
		<wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeListResponse</wsa:Action>
		<wsa:RelatesTo>${wsaMessageId}</wsa:RelatesTo>
	</soapenv:Header>
	<soapenv:Body>
		<getObjectTypeListResponse xmlns="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
			<objectTypeList>
				<tag>01</tag>
				<naam>Natuurlijk Persoon</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>06</tag>
				<naam>Overlijden</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>07</tag>
				<naam>Inschrijving</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>08</tag>
				<naam>Verblijfplaats</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>10</tag>
				<naam>Verblijfstitel</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>11</tag>
				<naam>Gezag</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>13</tag>
				<naam>Kiesrecht</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>05</tag>
				<naam>Verbintenis persoon</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>02</tag>
				<naam>Ouderschapsrelatie 1</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>09</tag>
				<naam>Kindrelatie</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>03</tag>
				<naam>Ouderschapsrelatie 2</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>04</tag>
				<naam>Nationaliteit</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
			<objectTypeList>
				<tag>12</tag>
				<naam>Reisdocument</naam>
				<bevraagbaar>false</bevraagbaar>
				<instructie/>
			</objectTypeList>
		</getObjectTypeListResponse>
	</soapenv:Body>
</soapenv:Envelope>
    """
}
else if (payload =~ /<.*getObjectTypeList.*>/) {
	logger.info "match getObjectTypeList"
    result = """
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stel="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
            <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeList</wsa:Action>
            <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
           </soapenv:Header>
           <soapenv:Body>
            <stel:getObjectTypeListResponse>
               <!--Zero or more repetitions:-->
               <stel:objectTypeList>
                  <stel:tag>TMF-PERSOON</stel:tag>
                  <stel:naam>Auto</stel:naam>
                  <stel:bevraagbaar>false</stel:bevraagbaar>
                  <stel:instructie>TEST-INSTRUCTIE 1</stel:instructie>
               </stel:objectTypeList>
               <stel:objectTypeList>
                  <stel:tag>TMF-GEBOUW</stel:tag>
                  <stel:naam>Vrachtwagen</stel:naam>
                  <stel:bevraagbaar>false</stel:bevraagbaar>
                  <stel:instructie>TEST-INSTRUCTIE 2</stel:instructie>
               </stel:objectTypeList>
            </stel:getObjectTypeListResponse>
         </soapenv:Body>
      </soapenv:Envelope>
    """
}
else if (payload =~ /<.*getObjectInfoAndValues.*>/ && payload.contains("GBA") && payload.contains("ObjectTag>01") ) {
    logger.info "match getObjectInfo GBA objecttag 08"
    result = """
        <soapenv:Envelope xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
   <soapenv:Header>
      <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
      <wsa:MessageID>uuid:feece554-206b-48b1-9af4-fdb1a30d3e77</wsa:MessageID>
      <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoAndValuesResponse</wsa:Action>
      <wsa:RelatesTo>uuid:aa397442-6492-4441-b647-64a348b4811b</wsa:RelatesTo>
   </soapenv:Header>
   <soapenv:Body>
      <ns2:getObjectInfoAndValuesResponse xmlns="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd" xmlns:ns2="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.2.xsd">
         <ns2:objectInfo>
            <ns2:ObjectHeaderInfo>
               <tag>01</tag>
               <naam>Natuurlijk Persoon</naam>
               <bevraagbaar>true</bevraagbaar>
               <instructie/>
            </ns2:ObjectHeaderInfo>
            <ns2:attributen>
               <ns2:code>01.01.10</ns2:code>
               <ns2:naam>A-nummer persoon</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.01.20</ns2:code>
               <ns2:naam>Burgerservicenummer persoon</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.02.10</ns2:code>
               <ns2:naam>Voornamen persoon</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.02.20</ns2:code>
               <ns2:naam>Adellijke titel/predikaat pers</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.02.30</ns2:code>
               <ns2:naam>Voorvoegsel geslachtsnaam pers</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.02.40</ns2:code>
               <ns2:naam>Geslachtsnaam persoon</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.03.10</ns2:code>
               <ns2:naam>Geboortedatum persoon</ns2:naam>
               <ns2:lengte>8</ns2:lengte>
               <ns2:toelichting>jjjjmmdd(8)</ns2:toelichting>
               <ns2:reguliereexpressie>[0-9][0-9][0-9][0-9](0[0-9]|1[012])(0[0-9]|[12][0-9]|3[01])</ns2:reguliereexpressie>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.03.20</ns2:code>
               <ns2:naam>Geboorteplaats persoon</ns2:naam>
               <ns2:attribuutwaarden>
                  <ns2:code>0</ns2:code>
                  <ns2:waarde>Onbekend</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>1</ns2:code>
                  <ns2:waarde>Adorp</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>2</ns2:code>
                  <ns2:waarde>Aduard</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>3</ns2:code>
                  <ns2:waarde>Appingedam</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>4</ns2:code>
                  <ns2:waarde>Baflo</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>5</ns2:code>
                  <ns2:waarde>Bedum</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>6</ns2:code>
                  <ns2:waarde>Beerta</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>7</ns2:code>
                  <ns2:waarde>Bellingwedde</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>8</ns2:code>
                  <ns2:waarde>Bierum</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>9</ns2:code>
                  <ns2:waarde>Ten Boer</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>10</ns2:code>
                  <ns2:waarde>Delfzijl</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>11</ns2:code>
                  <ns2:waarde>Eenrum</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>12</ns2:code>
                  <ns2:waarde>Ezinge</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>13</ns2:code>
                  <ns2:waarde>Finsterwolde</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>14</ns2:code>
                  <ns2:waarde>Groningen</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>15</ns2:code>
                  <ns2:waarde>Grootegast</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>16</ns2:code>
                  <ns2:waarde>Grijpskerk</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>17</ns2:code>
                  <ns2:waarde>Haren</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>18</ns2:code>
                  <ns2:waarde>Hoogezand-Sappemeer</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>19</ns2:code>
                  <ns2:waarde>Hefshuizen</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>20</ns2:code>
                  <ns2:waarde>Kantens</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>21</ns2:code>
                  <ns2:waarde>Kloosterburen</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>22</ns2:code>
                  <ns2:waarde>Leek</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>23</ns2:code>
                  <ns2:waarde>Leens</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>24</ns2:code>
                  <ns2:waarde>Loppersum</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>25</ns2:code>
                  <ns2:waarde>Marum</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>26</ns2:code>
                  <ns2:waarde>Meeden</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>27</ns2:code>
                  <ns2:waarde>Middelstum</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>28</ns2:code>
                  <ns2:waarde>Midwolda</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>29</ns2:code>
                  <ns2:waarde>Muntendam</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>30</ns2:code>
                  <ns2:waarde>Nieuwe Pekela</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>31</ns2:code>
                  <ns2:waarde>Nieuweschans</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>33</ns2:code>
                  <ns2:waarde>Oosterbroek</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>34</ns2:code>
                  <ns2:waarde>Almere</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>35</ns2:code>
                  <ns2:waarde>Oldehove</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>36</ns2:code>
                  <ns2:waarde>Oldekerk</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>37</ns2:code>
                  <ns2:waarde>Stadskanaal</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>38</ns2:code>
                  <ns2:waarde>Oude Pekela</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>39</ns2:code>
                  <ns2:waarde>Scheemda</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>40</ns2:code>
                  <ns2:waarde>Slochteren</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>41</ns2:code>
                  <ns2:waarde>Stedum</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>42</ns2:code>
                  <ns2:waarde>Termunten</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>43</ns2:code>
                  <ns2:waarde>Uithuizen</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>44</ns2:code>
                  <ns2:waarde>Uithuizermeeden</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>45</ns2:code>
                  <ns2:waarde>Ulrum</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>46</ns2:code>
                  <ns2:waarde>Usquert</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>47</ns2:code>
                  <ns2:waarde>Veendam</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>48</ns2:code>
                  <ns2:waarde>Vlagtwedde</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>49</ns2:code>
                  <ns2:waarde>Warffum</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>50</ns2:code>
                  <ns2:waarde>Zeewolde</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>51</ns2:code>
                  <ns2:waarde>Skarsterl</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>52</ns2:code>
                  <ns2:waarde>Winschoten</ns2:waarde>
               </ns2:attribuutwaarden>
               <ns2:attribuutwaarden>
                  <ns2:code>53</ns2:code>
                  <ns2:waarde>Winsum</ns2:waarde>
               </ns2:attribuutwaarden>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.03.30</ns2:code>
               <ns2:naam>Geboorteland persoon</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.04.10</ns2:code>
               <ns2:naam>Geslachtsaanduiding</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.20.10</ns2:code>
               <ns2:naam>Vorig A-nummer</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.20.20</ns2:code>
               <ns2:naam>Volgend A-nummer</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.61.10</ns2:code>
               <ns2:naam>Aanduiding naamgebruik</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.81.10</ns2:code>
               <ns2:naam>Registergemeente akte waaraan</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.81.20</ns2:code>
               <ns2:naam>Aktenummer van de akte waaraan</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.82.10</ns2:code>
               <ns2:naam>Gemeente waar de gegevens over</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.82.20</ns2:code>
               <ns2:naam>Datum van de ontlening van de</ns2:naam>
               <ns2:reguliereexpressie>[0-9][0-9][0-9][0-9](0[0-9]|1[012])(0[0-9]|[12][0-9]|3[01])</ns2:reguliereexpressie>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.82.30</ns2:code>
               <ns2:naam>Beschrijving van het document</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.83.10</ns2:code>
               <ns2:naam>Aanduiding gegevens in onderzo</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.83.20</ns2:code>
               <ns2:naam>Datum ingang onderzoek</ns2:naam>
               <ns2:lengte>8</ns2:lengte>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.83.30</ns2:code>
               <ns2:naam>Datum einde onderzoek</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.84.10</ns2:code>
               <ns2:naam>Indicatie onjuist dan wel stri</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.85.10</ns2:code>
               <ns2:naam>Ingangsdatum geldigheid met be</ns2:naam>
            </ns2:attributen>
            <ns2:attributen>
               <ns2:code>01.86.10</ns2:code>
               <ns2:naam>Datum van opneming met betrekk</ns2:naam>
            </ns2:attributen>
         </ns2:objectInfo>
      </ns2:getObjectInfoAndValuesResponse>
   </soapenv:Body>
</soapenv:Envelope>
    """
}

else if (payload =~ /<.*getObjectInfo.*>/ && payload.contains("GBA") && payload.contains("ObjectTag>08") ) {
	logger.info "match getObjectInfo GBA objecttag 08"
    result = """
    	<soapenv:Envelope xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Header>
      <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
      <wsa:MessageID>uuid:35b28089-6fda-4829-a8fc-58a7352dfbaa</wsa:MessageID>
      <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoResponse</wsa:Action>
      <wsa:RelatesTo>${wsaMessageId}</wsa:RelatesTo>
    </soapenv:Header>
    <soapenv:Body>
      <getObjectInfoResponse xmlns="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
        <objectInfo>
          <ObjectHeaderInfo>
            <tag>08</tag>
            <naam>Verblijfplaats</naam>
            <bevraagbaar>false</bevraagbaar>
            <instructie/>
          </ObjectHeaderInfo>
          <attributen>
            <code>08.09.10</code>
            <naam>Gemeente van inschrijving</naam>
          </attributen>
          <attributen>
            <code>08.09.20</code>
            <naam>Datum inschrijving in de gemee</naam>
          </attributen>
          <attributen>
            <code>08.10.10</code>
            <naam>Functie adres</naam>
          </attributen>
          <attributen>
            <code>08.10.20</code>
            <naam>Gemeentedeel</naam>
          </attributen>
          <attributen>
            <code>08.10.30</code>
            <naam>Datum aanvang adreshouding</naam>
          </attributen>
          <attributen>
            <code>08.11.10</code>
            <naam>Straatnaam</naam>
          </attributen>
          <attributen>
            <code>08.11.20</code>
            <naam>Huisnummer</naam>
          </attributen>
          <attributen>
            <code>08.11.30</code>
            <naam>Huisletter</naam>
          </attributen>
          <attributen>
            <code>08.11.40</code>
            <naam>Huisnummertoevoeging</naam>
          </attributen>
          <attributen>
            <code>08.11.50</code>
            <naam>Aanduiding bij huisnummer</naam>
          </attributen>
          <attributen>
            <code>08.11.60</code>
            <naam>Postcode</naam>
          </attributen>
          <attributen>
            <code>08.12.10</code>
            <naam>Locatiebeschrijving</naam>
          </attributen>
          <attributen>
            <code>08.13.10</code>
            <naam>Land waarnaar vertrokken</naam>
          </attributen>
          <attributen>
            <code>08.13.20</code>
            <naam>Datum vertrek uit Nederland</naam>
          </attributen>
          <attributen>
            <code>08.13.30</code>
            <naam>Adres buitenland waarnaar vert</naam>
          </attributen>
          <attributen>
            <code>08.13.40</code>
            <naam>Adres buitenland waarnaar vert</naam>
          </attributen>
          <attributen>
            <code>08.13.50</code>
            <naam>Adres buitenland waarnaar vert</naam>
          </attributen>
          <attributen>
            <code>08.14.10</code>
            <naam>Land vanwaar ingeschreven</naam>
          </attributen>
          <attributen>
            <code>08.14.20</code>
            <naam>Datum vestiging in Nederland</naam>
          </attributen>
          <attributen>
            <code>08.72.10</code>
            <naam>Omschrijving van de aangifte a</naam>
          </attributen>
          <attributen>
            <code>08.75.10</code>
            <naam>Indicatie document</naam>
          </attributen>
          <attributen>
            <code>08.83.10</code>
            <naam>Aanduiding gegevens in onderzo</naam>
          </attributen>
          <attributen>
            <code>08.83.20</code>
            <naam>Datum ingang onderzoek</naam>
          </attributen>
          <attributen>
            <code>08.83.30</code>
            <naam>Datum einde onderzoek</naam>
          </attributen>
          <attributen>
            <code>08.84.10</code>
            <naam>Indicatie onjuist</naam>
          </attributen>
          <attributen>
            <code>08.85.10</code>
            <naam>Ingangsdatum geldigheid met be</naam>
          </attributen>
          <attributen>
            <code>08.86.10</code>
            <naam>Datum van opneming met betrekk</naam>
          </attributen>
        </objectInfo>
      </getObjectInfoResponse>
    </soapenv:Body>
  </soapenv:Envelope>
    """
}
else if (payload =~ /<.*getObjectInfo.*>/ && payload.contains("GBA")) {
	logger.info "match getObjectInfo GBA lalala"
    result = """
    	<soapenv:Envelope xmlns:wsa="http://www.w3.org/2005/08/addressing" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
	<soapenv:Header>
		<wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
		<wsa:MessageID>uuid:ed914e82-83cd-4eb8-ac18-022131e2a28d</wsa:MessageID>
		<wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoResponse</wsa:Action>
		<wsa:RelatesTo>${wsaMessageId}</wsa:RelatesTo>
	</soapenv:Header>
	<soapenv:Body>
		<getObjectInfoResponse xmlns="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
			<objectInfo>
				<ObjectHeaderInfo>
					<tag>01</tag>
					<naam>Natuurlijk Persoon</naam>
					<bevraagbaar>true</bevraagbaar>
					<instructie/>
				</ObjectHeaderInfo>
				<attributen>
					<code>01.01.10</code>
					<naam>A-nummer persoon</naam>
				</attributen>
				<attributen>
					<code>01.01.20</code>
					<naam>Burgerservicenummer persoon</naam>
				</attributen>
				<attributen>
					<code>01.02.10</code>
					<naam>Voornamen persoon</naam>
				</attributen>
				<attributen>
					<code>01.02.20</code>
					<naam>Adellijke titel/predikaat pers</naam>
				</attributen>
				<attributen>
					<code>01.02.30</code>
					<naam>Voorvoegsel geslachtsnaam pers</naam>
				</attributen>
				<attributen>
					<code>01.02.40</code>
					<naam>Geslachtsnaam persoon</naam>
				</attributen>
				<attributen>
					<code>01.03.10</code>
					<naam>Geboortedatum persoon</naam>
				</attributen>
				<attributen>
					<code>01.03.20</code>
					<naam>Geboorteplaats persoon</naam>
				</attributen>
				<attributen>
					<code>01.03.30</code>
					<naam>Geboorteland persoon</naam>
				</attributen>
				<attributen>
					<code>01.04.10</code>
					<naam>Geslachtsaanduiding</naam>
				</attributen>
				<attributen>
					<code>01.20.10</code>
					<naam>Vorig A-nummer</naam>
				</attributen>
				<attributen>
					<code>01.20.20</code>
					<naam>Volgend A-nummer</naam>
				</attributen>
				<attributen>
					<code>01.61.10</code>
					<naam>Aanduiding naamgebruik</naam>
				</attributen>
				<attributen>
					<code>01.81.10</code>
					<naam>Registergemeente akte waaraan </naam>
				</attributen>
				<attributen>
					<code>01.81.20</code>
					<naam>Aktenummer van de akte waaraan</naam>
				</attributen>
				<attributen>
					<code>01.82.10</code>
					<naam>Gemeente waar de gegevens over</naam>
				</attributen>
				<attributen>
					<code>01.82.20</code>
					<naam>Datum van de ontlening van de </naam>
				</attributen>
				<attributen>
					<code>01.82.30</code>
					<naam>Beschrijving van het document </naam>
				</attributen>
				<attributen>
					<code>01.83.10</code>
					<naam>Aanduiding gegevens in onderzo</naam>
				</attributen>
				<attributen>
					<code>01.83.20</code>
					<naam>Datum ingang onderzoek</naam>
				</attributen>
				<attributen>
					<code>01.83.30</code>
					<naam>Datum einde onderzoek</naam>
				</attributen>
				<attributen>
					<code>01.84.10</code>
					<naam>Indicatie onjuist dan wel stri</naam>
				</attributen>
				<attributen>
					<code>01.85.10</code>
					<naam>Ingangsdatum geldigheid met be</naam>
				</attributen>
				<attributen>
					<code>01.86.10</code>
					<naam>Datum van opneming met betrekk</naam>
				</attributen>
			</objectInfo>
		</getObjectInfoResponse>
	</soapenv:Body>
</soapenv:Envelope>
"""
}
else if (payload =~ /<.*getObjectInfo.*>/) {
	logger.info "match getObjectInfo"
    result = """
     <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stel="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
    	<soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
    		<wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfo</wsa:Action>
        	<wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
        </soapenv:Header>
     	<soapenv:Body>
            <stel:getObjectInfoResponse>
               <stel:objectInfo>
                  <stel:ObjectHeaderInfo>
                     <stel:tag>TMF-PERSOON</stel:tag>
                     <stel:naam>Persoon</stel:naam>
                     <stel:bevraagbaar>false</stel:bevraagbaar>
                     <stel:instructie>TEST-INSTRUCTIE</stel:instructie>
                  </stel:ObjectHeaderInfo>
                  <!--Zero or more repetitions:-->
                  <stel:attributen>
                     <stel:code>TMF-PERSOON-VOORNAAM</stel:code>
                     <stel:naam>Voornaam</stel:naam>
                  </stel:attributen>
                  <stel:attributen>
                     <stel:code>TMF-PERSOON-TUSSENVOEGSEL</stel:code>
                     <stel:naam>Tussenvoegsel</stel:naam>
                  </stel:attributen>
                  <stel:attributen>
                     <stel:code>TMF-PERSOON-ACHTERNAAM</stel:code>
                     <stel:naam>Achternaam</stel:naam>
                  </stel:attributen>
               </stel:objectInfo>
            </stel:getObjectInfoResponse>
         </soapenv:Body>
      </soapenv:Envelope>
    """
}
else if (payload =~ /<.*bevragen.*>/) {
	logger.info "match bevragen"
    result = """
     <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:stel="http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd">
        <soapenv:Header xmlns:wsa="http://www.w3.org/2005/08/addressing">
           <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfo</wsa:Action>
           <wsa:RelatesTo RelationshipType="http://www.w3.org/2005/08/addressing/reply">${wsaMessageId}</wsa:RelatesTo>
           <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>
       </soapenv:Header>
       <soapenv:Body>
          <stel:bevragenResponse>
         <stel:objectinstantie>
                <stel:objectInfo>
                   <stel:tag>01</stel:tag>
                   <stel:naam>Natuurlijk Persoon</stel:naam>
                   <stel:bevraagbaar>false</stel:bevraagbaar>
                   <stel:instructie/>
                </stel:objectInfo>
                <!--Zero or more repetitions:-->
                <stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.01.10</stel:code>
				    <stel:naam>A-nummer persoon</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.01.20</stel:code>
				    <stel:naam>Burgerservicenummer persoon</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.02.10</stel:code>
				    <stel:naam>Voornamen persoon</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.02.20</stel:code>
				    <stel:naam>Adellijke titel/predikaat pers</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.02.30</stel:code>
				    <stel:naam>Voorvoegsel geslachtsnaam pers</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.02.40</stel:code>
				    <stel:naam>Geslachtsnaam persoon</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.03.10</stel:code>
				    <stel:naam>Geboortedatum persoon</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.03.20</stel:code>
				    <stel:naam>Geboorteplaats persoon</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.03.30</stel:code>
				    <stel:naam>Geboorteland persoon</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.04.10</stel:code>
				    <stel:naam>Geslachtsaanduiding</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.20.10</stel:code>
				    <stel:naam>Vorig A-nummer</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.20.20</stel:code>
				    <stel:naam>Volgend A-nummer</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.61.10</stel:code>
				    <stel:naam>Aanduiding naamgebruik</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.81.10</stel:code>
				    <stel:naam>Registergemeente akte waaraan</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.81.20</stel:code>
				    <stel:naam>Aktenummer van de akte waaraan</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.82.10</stel:code>
				    <stel:naam>Gemeente waar de gegevens over</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.82.20</stel:code>
				    <stel:naam>Datum van de ontlening van de</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.82.30</stel:code>
				    <stel:naam>Beschrijving van het document</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.83.10</stel:code>
				    <stel:naam>Aanduiding gegevens in onderzo</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.83.20</stel:code>
				    <stel:naam>Datum ingang onderzoek</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.83.30</stel:code>
				    <stel:naam>Datum einde onderzoek</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.84.10</stel:code>
				    <stel:naam>Indicatie onjuist dan wel stri</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.85.10</stel:code>
				    <stel:naam>Ingangsdatum geldigheid met be</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
				<stel:attributeValues>
				  <stel:attribuutInfo>
				    <stel:code>01.86.10</stel:code>
				    <stel:naam>Datum van opneming met betrekk</stel:naam>
				  </stel:attribuutInfo>
				  <stel:value>Onbekend</stel:value>
				</stel:attributeValues>
             </stel:objectinstantie>
          </stel:bevragenResponse>
       </soapenv:Body>
    </soapenv:Envelope>
	"""
}

assert result != NOTHING

logger.debug "reply: ${result}"

return result