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

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;

/**
 * @author tarem
 * 
 */
public class ValidateStelselCatalogusMessage extends ValidateSoapMessage {
	// fout codes die in error bericht voorkomen
	private static final String STELSELCATALOGUS_ERROR_STRING = "StelselCatalogus Fout";

	Logger log = Logger.getLogger(ValidateStelselCatalogusMessage.class
			.getName());
	String exceptionMessage = null;

	@Override
	public String doValidate(MuleMessage message, String outputEncoding) {

		exceptionMessage = super.doValidate(message, outputEncoding);
		log.debug("Start Validate StelselCatalogus");
		try {
			if (exceptionMessage == null) {
				checkNamespaceStelselBevragen(message);
			}
		} catch (Exception e) {
			exceptionMessage = String.format(exceptionMessageString,
					STELSELCATALOGUS_VALIDATIE_CODE_STRING, "Error validate Message", e
							.getMessage());
		}

		return exceptionMessage;
	}

	private void checkNamespaceStelselBevragen(MuleMessage message)
			throws Exception {
		log.debug("Check namespace stelsel bevragen");
		if (!message.getPayloadAsString().contains(
				"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1")) {
			String foutBericht = "Geen namespace voor stelselbevragen in bericht";

			log.warn(foutBericht);

			exceptionMessage = String
					.format(
							exceptionMessageString,
							ValidateMessage.STELSELCATALOGUS_GEEN_STELSELCATALOGUSSCHEMA_CODE_STRING,
							STELSELCATALOGUS_ERROR_STRING, foutBericht);
		}
	}
}
