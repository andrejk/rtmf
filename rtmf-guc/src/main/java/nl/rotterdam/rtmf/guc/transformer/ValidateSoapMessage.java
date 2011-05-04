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

public class ValidateSoapMessage extends ValidateMessage {
	// fout codes die in error bericht voorkomen
	private static final String SOAP_ERROR_STRING = "SOAP Fout";

	Logger log = Logger.getLogger(ValidateSoapMessage.class.getName());
	String exceptionMessage = null;

	@Override
	public String doValidate(MuleMessage message, String outputEncoding) {
		log.debug("Start ValidateSoapMessage");
		exceptionMessage = null;

		try {
			log.debug(message.getPayloadAsString());

			if (exceptionMessage == null) {
				// valideer of soap schema in bericht is opgenomen
				checkSoapSchema(message);
			}

			if (exceptionMessage == null) {
				// valideer of envelop element aanwezig is
				checkSoapEnvelopElement(message);
			}

			if (exceptionMessage == null) {
				// valideer of header element aanwezig is
				checkSoapHeaderElement(message);
			}
			
			if (exceptionMessage == null) {
				// valideer of body element aanwezig is
				checkSoapBodyElement(message);
			}
			
			if (exceptionMessage == null) {
				checkSoapFaultElement(message);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			exceptionMessage = String.format(exceptionMessageString,
					ValidateMessage.SOAP_VALIDATIE_CODE_STRING, SOAP_ERROR_STRING,
					e.getMessage());
		}

		return exceptionMessage;
	}

	/**
	 * @param message
	 */
	private void checkSoapFaultElement(MuleMessage message) throws Exception {
		log.debug("Check soap fault message");
		if (message.getPayloadAsString().matches(".*Body>.*Fault.*faultcode.*Fault>.*Body>.*")) {
			String foutBericht = String.format("Er is een Fault element gevonden in de massage: %s", message.getPayloadAsString());
			exceptionMessage = String.format(exceptionMessageString,
					ValidateMessage.SOAP_FAULT_ELEMENT_FOUND,
					SOAP_ERROR_STRING, foutBericht);
		}
	}

	private void checkSoapSchema(MuleMessage message) throws Exception {
		log.debug("Check soap schema");

		if (!message.getPayloadAsString().contains(
				"=\"http://schemas.xmlsoap.org/soap/envelope/\"")) {
			String foutBericht = "Geen valide soap envelope schema aanwezig";

			log.warn(foutBericht);

			exceptionMessage = String.format(exceptionMessageString,
					ValidateMessage.SOAP_GEEN_SOAPSCHEMA_CODE_STRING, SOAP_ERROR_STRING,
					foutBericht);

		}
	}

	private void checkSoapHeaderElement(MuleMessage message) throws Exception {
		log.debug("Check soap Header");
		if (!message.getPayloadAsString().contains("Header")) {
			String foutBericht = "Element header ontbreekt";
			log.warn(foutBericht);

			exceptionMessage = String.format(exceptionMessageString,
					ValidateMessage.SOAP_GEEN_HEADER_CODE_STRING, SOAP_ERROR_STRING,
					foutBericht);

		}
	}

	private void checkSoapBodyElement(MuleMessage message) throws Exception {
		log.debug("Check soap Body");
		if (!message.getPayloadAsString().contains(
				"Body")) {
			String foutBericht = "Element body ontbreekt";

			log.warn(foutBericht);

			exceptionMessage = String.format(exceptionMessageString,
					ValidateMessage.SOAP_GEEN_BODY_CODE_STRING, SOAP_ERROR_STRING,
					foutBericht);
		}
	}

	private void checkSoapEnvelopElement(MuleMessage message) throws Exception {
		log.debug("Check soap envelop");
		if (!message.getPayloadAsString().contains("Envelop")) {
			String foutBericht = "Element envelop ontbreekt";
	
			log.warn(foutBericht);
	
			exceptionMessage = String.format(exceptionMessageString,
					ValidateMessage.SOAP_GEEN_ENVELOP_CODE_STRING, SOAP_ERROR_STRING,
					foutBericht);
		}
	}
}
