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

import java.util.List;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;

import nl.rotterdam.rtmf.guc.common.DocumentParser;

public class ValidateStufMessage extends ValidateMessage {


	@Override
	public String doValidate(MuleMessage message, String outputEncoding) {
		Logger log = Logger.getLogger(ValidateMessage.class.getName());
		String exceptionMessage = null;

		try {
			// controleer op stuf foutbericht
			exceptionMessage = checkStufFoutbericht0300(message, log,
					exceptionMessage);

			if (exceptionMessage == null) {
				exceptionMessage = checkStuurgegevensAanwezig(message, log,
						exceptionMessage);
			}

			if (exceptionMessage == null) {
				exceptionMessage = checkBodygegevensAanwezig(message, log,
						exceptionMessage);
			}

		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		}

		return exceptionMessage;
	}

	private String checkBodygegevensAanwezig(MuleMessage message, Logger log,
			String exceptionMessage) throws Exception {
		if (!message.getPayloadAsString().contains("body")) {
			String foutBericht = "Geen body aanwezig StUF bericht";

			log.error(foutBericht);

			exceptionMessage = String.format(exceptionMessageString,
					ValidateMessage.STUF_GEEN_BODY_CODE_STRING, "Stuf Fout", foutBericht);
		}
		return exceptionMessage;
	}

	private String checkStuurgegevensAanwezig(MuleMessage message, Logger log,
			String exceptionMessage) throws Exception {
		log.debug("CheckStuurgegevensAanwezig");
		if (!message.getPayloadAsString().contains("stuurgegevens")) {
			String foutBericht = "Geen stuurgegevens aanwezig in StUF bericht";

			log.error(foutBericht);

			exceptionMessage = String.format(exceptionMessageString,
					ValidateMessage.STUF_GEEN_STUURGEGEVENS_CODE_STRING, "Stuf Fout", foutBericht);
		}
		return exceptionMessage;
	}

	private String checkStufFoutbericht0300(MuleMessage message, Logger log,
			String exceptionMessage) throws Exception {
		if (message.getPayloadAsString().contains(
				"<foutBericht xmlns=\"http://www.egem.nl/StUF/StUF0300\">")) {

			String foutBericht = null;
			try {
				// foutBericht = (String) DocumentParser.parseDocument(message
				// .getPayloadAsString(),
				// "/foutBericht/body/*/text()",
				// XPathConstants.STRING);
				String xpathString = "/foutBericht/body/*";

				log
						.debug("Elementen ophalen voor het vullen van het foutbericht xpath: "
								+ xpathString);
				List<String> s = DocumentParser.getAllValues(message
						.getPayloadAsString(), xpathString);

				if (s != null) {
					for (int i = 0; i < s.size(); i++) {
						foutBericht += ";" + s.get(i).toString();
					}
				}

				log.debug("Fout bericht : " + foutBericht);

				exceptionMessage = String.format(exceptionMessageString,
						ValidateMessage.STUF_STUFFOUT_CODE_STRING, "StUF fout versie 300",
						foutBericht);

			} catch (Exception e1) {
				exceptionMessage = String.format(exceptionMessageString,
						ValidateMessage.STUF_VALIDATIE_CODE_STRING, "Exception bij valideren", e1
								.getMessage());
			}
			log
					.debug("Foutbericht gevonden "
							+ message.getPayload().toString());
		}
		return exceptionMessage;
	}

}
