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
import org.mule.message.DefaultExceptionPayload;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transport.NullPayload;

public class ValidateMessage extends AbstractMessageAwareTransformer {
	public static String exceptionMessageString = "<error><code>%s</code><message>%s</message><exceptionPayload>%s</exceptionPayload></error>";

	
	// fout codes die in error bericht voorkomen
	// voor basis validatie ValidateMessage
	public static final String VALIDATIE_CODE_STRING = "900";
	public static final String BINDING_FAULT_CODE_STRING = "901";
	public static final String GEEN_PAYLOAD_CODE_STRING = "902";
	public static final String GEEN_BODY_CODE_STRING = "913";
	// voor StUF berichten
	public static final String STUF_VALIDATIE_CODE_STRING = "910";
	public static final String STUF_STUFFOUT_CODE_STRING = "911";
	public static final String STUF_GEEN_STUURGEGEVENS_CODE_STRING = "912";
	public static final String STUF_GEEN_BODY_CODE_STRING = "913";
	// voor SOAP validatie ValidateSoapMessage
	public static final String SOAP_VALIDATIE_CODE_STRING = "920";
	public static final String SOAP_GEEN_SOAPSCHEMA_CODE_STRING = "921";
	public static final String SOAP_GEEN_ENVELOP_CODE_STRING = "922";
	public static final String SOAP_GEEN_HEADER_CODE_STRING = "923";
	public static final String SOAP_GEEN_BODY_CODE_STRING = "924";
	public static final String SOAP_FAULT_ELEMENT_FOUND = "925";
	// voor Stelselcatalogus codes
	public static final String STELSELCATALOGUS_VALIDATIE_CODE_STRING = "930";
	public static final String STELSELCATALOGUS_GEEN_STELSELCATALOGUSSCHEMA_CODE_STRING = "931";

	
	@Override
	public Object transform(MuleMessage message, String outputEncoding)
			throws TransformerException {
		String exceptionMessage = null;
		Logger log = Logger.getLogger(ValidateMessage.class.getName());

		log.debug("start ValidateMessage");


		try {
			log.debug("message : " + message.getPayloadAsString());

			// controleer of exception payload gevuld is
			exceptionMessage = checkExceptionPayload(message, exceptionMessage,
					log);
			// controleer of message payload gevuld is
			if (exceptionMessage == null)
				exceptionMessage = checkMessagePayload(message,
						exceptionMessage, log);
			// controle of er een binding fout op de aanroepende server is
			// voorgekomen
			if (exceptionMessage == null) {
				exceptionMessage = checkBindingFault(message, exceptionMessage,
						log);
			}
			// geen fout gevonden roep dan doValidate aan
			if (exceptionMessage == null) {
				exceptionMessage = doValidate(message, outputEncoding);
			}
		} catch (Exception e) {
			exceptionMessage = String.format(exceptionMessageString, VALIDATIE_CODE_STRING,"Error validate Message",e.getMessage());
		}

		// als er locaal geen fout gevonden is
		if (exceptionMessage != null) {
			log.debug("Set exception payload");
			message.setExceptionPayload(new DefaultExceptionPayload(
					new RtmfGucException(exceptionMessage)));
			throw new RtmfGucException(exceptionMessage);
		}
		//
		log.debug("end ValidateMessage");
		return message;
	}

	private String checkMessagePayload(MuleMessage message,
			String exceptionMessage, Logger log) throws Exception {
		log.debug("checkMessagePayload");
		// contoleer of message payload gevuld is
		if (message.getPayload() instanceof NullPayload) {
			exceptionMessage = String.format(exceptionMessageString, GEEN_PAYLOAD_CODE_STRING,
					"Payload is niet gevuld", "");
			log.warn("Payload is niet gevuld");
		}
		return exceptionMessage;
	}

	private String checkBindingFault(MuleMessage message,
			String exceptionMessage, Logger log) throws Exception {
		log.debug("checkBindingFault");
		if (message.getPayloadAsString().contains("Cannot bind to address")) {

			exceptionMessage = String.format(exceptionMessageString, BINDING_FAULT_CODE_STRING,
					"Binding fout op server", message.getPayload().toString());

			log.warn("BindingFault payload: " + message.getPayloadAsString());
		}
		return exceptionMessage;
	}

	private String checkExceptionPayload(MuleMessage message,
			String exceptionMessage, Logger log) {
		if (message.getExceptionPayload() != null) {
			log.debug("Exception payload is not null : "
					+ message.getExceptionPayload().toString());
			exceptionMessage = message.getExceptionPayload().getRootException()
					.toString();
		}
		return exceptionMessage;
	}

	public String doValidate(MuleMessage message, String outputEncoding) {
		return null;
	}

}
