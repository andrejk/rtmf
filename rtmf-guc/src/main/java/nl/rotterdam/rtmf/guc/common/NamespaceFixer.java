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
package nl.rotterdam.rtmf.guc.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * Deze NamespaceFixer verandert de OSBGateway name-spaces van response
 * berichten.
 * 
 * Elke OSBGateway gebruikt een eigen namespace voor default response berichten
 * voor ebMS services. Het TMF Portaal maakt gebruik van de namespace:
 * "http://tmfportal.ovsoftware.com/services/defaultreply.xsd".
 * 
 * Deze handler vervangt alle OSBGateway namespaces in de default namespace
 * zoals deze wordt begrepen door het TMF portaal.
 * 
 * @author Enno Buis
 * 
 */
public class NamespaceFixer {

	private static Logger logger = Logger
			.getLogger("rtmfguc.common.NamspaceFixer");
	private static final String DEFAULT_NAMESPACE = "http://tmfportal.ovsoftware.com/services/defaultreply.xsd";
	private static final String EBMS_RESPONSE_IDENTIFIER = "defaultreply.xsd";

	/**
	 * Vervangt de namespace uri van alle default response berichten.
	 * 
	 * @param payloadAsString
	 *            Bevat de message die we willen bewerken
	 * @return Aangepaste message
	 */
	@SuppressWarnings("unchecked")
	public static String fixResponseNamespace(String payloadAsString) {

		String result = null;
		String prefix = null;
		String uri = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Aanpassen namespaces in payloadAsString: "
					+ payloadAsString);
		}

		try {
			// Create SoapMessage
			MessageFactory msgFactory = MessageFactory.newInstance();
			SOAPMessage message = msgFactory.createMessage();
			SOAPPart soapPart = message.getSOAPPart();

			// Load the SOAP text into a stream source
			byte[] buffer = payloadAsString.getBytes();
			ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
			StreamSource source = new StreamSource(stream);

			// Set contents of message
			soapPart.setContent(source);
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();

			Iterator it = message.getSOAPBody().getChildElements();
			while (it.hasNext()) {
				Node node = (Node) it.next();
				if (node instanceof SOAPElement) {

					// Haal de verschillende elementen op uit de QName.
					prefix = ((SOAPElement) node).getElementQName().getPrefix();
					uri = ((SOAPElement) node).getElementQName()
							.getNamespaceURI();
					String localPart = ((SOAPElement) node).getElementQName()
							.getLocalPart();

					if (logger.isDebugEnabled()) {
						logger.debug("Prefix: " + prefix);
						logger.debug("Uri   : " + uri);
						logger.debug("Local : " + localPart);
					}

					if (uri.contains(EBMS_RESPONSE_IDENTIFIER)) {
						if (logger.isDebugEnabled()) {
							logger.debug("Yep, de node 'uri' bevat de string '"
									+ EBMS_RESPONSE_IDENTIFIER
									+ "', dus gaan we de node 'fixen' ...");
						}

						// Maak eenzelfde node aan, maar nu zonder namespace.
						((SOAPElement) node).setElementQName(new QName("",
								localPart, prefix));

						// Verwijder de namespace defenitie uit de envelope
						// declaratie.
						envelope.removeAttributeNS(uri, prefix);

						// Voeg de 'nieuwe' (voor de TmfPortal geschikte)
						// namespace
						// in de envelope defenitie.
						envelope.setAttribute("xmlns:" + prefix,
								DEFAULT_NAMESPACE);
					}
				}
			}

			// Converteer naar resultaat string
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			message.writeTo(baos);
			result = baos.toString("UTF-8");

			if (logger.isDebugEnabled()) {
				logger.debug("Aangepaste namespaces in payloadAsString: "
						+ result);
			}

		} catch (SOAPException e) {
			throw new RtmfGucException("SOAPException in doFix: "
					+ payloadAsString, e);
		} catch (IOException e) {
			throw new RtmfGucException("IOException in doFix: "
					+ payloadAsString, e);
		} catch (DOMException e) {
			throw new RtmfGucException("DOMException in doFix: "
					+ payloadAsString, e);
		}

		// En klaar is Klara ...
		return result;
	}
}
