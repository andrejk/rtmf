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
package nl.rotterdam.rtmf.guc.transformer;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

/**
 * Deze transformer zorgt ervoor dat als er geen header in het soap bericht zit
 * deze wordt toegevoegd. Dit is alleen geimplementeerd voor de stelselbevragen
 * services.
 * 
 * @author rweverwijk
 * 
 */
public class AddSoapHeaderTransformer extends AbstractMessageAwareTransformer {
	private final String baseHeader = "<soap:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To><wsa:MessageID>uuid:%s</wsa:MessageID><wsa:Action>%s</wsa:Action><wsa:RelatesTo>uuid:%s</wsa:RelatesTo></soap:Header><soap:Body>";
	private final String baseActionHeader = "<wsa:Action xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">%s</wsa:Action></soapenv:Header>";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mule.transformer.AbstractMessageAwareTransformer#transform(org.mule
	 * .api.MuleMessage, java.lang.String)
	 */
	@Override
	public Object transform(MuleMessage message, String outputEncoding)
			throws TransformerException {
		Logger log = Logger.getLogger(AddSoapHeaderTransformer.class);
		String payload = null;
		try {
			payload = message.getPayloadAsString();
		} catch (Exception e) {
			throw new RtmfGucException(
					"Er is een fout opgetreden tijdens het toevoegen van de soap header",
					e.getCause());
		}
		if ((!payload.contains("Header>")) && payload.contains("<soap:Body>")) {
			log.warn("There is no wsa header set. We adding a default one.");
			String header = null;
			if (payload.contains("getBasisregistratieListResponse")) {
				header = String
						.format(
								baseHeader,
								message.getUniqueId(),
								"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListResponse",
								message.getCorrelationId());
			} else if (payload.contains("getObjectTypeListResponse")) {
				header = String
						.format(
								baseHeader,
								message.getUniqueId(),
								"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeListResponse",
								message.getCorrelationId());
			} else if (payload.contains("getObjectInfoResponse")) {
				header = String
						.format(
								baseHeader,
								message.getUniqueId(),
								"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoResponse",
								message.getCorrelationId());
			} else if (payload.contains("bevragenResponse")) {
				header = String
						.format(
								baseHeader,
								message.getUniqueId(),
								"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/bevragenResponse",
								message.getCorrelationId());
			}
			if (header != null) {
				payload = payload.replace("<soap:Body>", header);
			}
		} else if(payload.contains("</soapenv:Header>") && !payload.contains("Action>")) {
			String actionHeader = null;
			if (payload.contains("getBasisregistratieListResponse")) {
				actionHeader = String.format(
								baseActionHeader, "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListResponse");
			} else if (payload.contains("getObjectTypeListResponse")) {
				actionHeader = String
						.format(
								baseActionHeader, "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectTypeListResponse");
			} else if (payload.contains("getObjectInfoResponse")) {
				actionHeader = String
						.format(
								baseActionHeader, "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoResponse");
			} else if (payload.contains("bevragenResponse")) {
				actionHeader = String
						.format(
								baseActionHeader,"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/bevragenResponse");
			}
			if (actionHeader != null) {
				payload = payload.replace("</soapenv:Header>",actionHeader);
			}
		 }else {
			log
					.warn("The payload has a wsa header so nothing will be transformed in this transfomer");
		}
		return payload;
	}
}
