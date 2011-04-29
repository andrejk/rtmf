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
result = """
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
	<S:Header>
		<To xmlns="http://www.w3.org/2005/08/addressing">http://localhost:63081/rtmfguc/terugmeldService</To>
		<Action xmlns="http://www.w3.org/2005/08/addressing">intrekkingtmf-aanmelden-00000003271987420000</Action>
		<ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
			<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
		</ReplyTo>
		<MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:24cf9fd5-7b2a-4437-8f85-d7b02ef52776
		</MessageID>
	</S:Header>
	<S:Body>
		<ns2:intrekking
			xmlns:ns2="http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd"
			xmlns:ns3="http://tmfportal.ovsoftware.com/services/defaultreply.xsd"
			xmlns:ns4="http://tmfportal.ovsoftware.com/services">
			<ns2:meldingKenmerk>Intrekking-7d853cff-93b1-420d-9c36-6d70601c64bf-1256225482778</ns2:meldingKenmerk>
			<ns2:tijdstempelAanlevering>2009-10-22T17:31:22.778+02:00</ns2:tijdstempelAanlevering>
			<ns2:betreftTmfKenmerk>7d853cff-93b1-420d-9c36-6d70601c64bf</ns2:betreftTmfKenmerk>
			<ns2:toelichting>Intrekken tessie</ns2:toelichting>
		</ns2:intrekking>
	</S:Body>
</S:Envelope>
"""

return result
