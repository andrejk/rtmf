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
package nl.rotterdam.rtmf.guc.filter;

import java.io.StringReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import nl.rotterdam.rtmf.guc.common.terugmelding.HardcodedNamespaceResolverTerugmelding;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * TerugmeldCacheFilter kijkt in de payload of er basisregistratie tag. Deze tag
 * zal worden vergeleken met de opgegeven tag in 'expectedService'. Indien de
 * tags overeenkomen zal 'true' teruggegeven worden.
 * 
 * @author Enno Buis
 * 
 */
public class TerugmeldCacheFilter extends CacheFilterBase {

	private static Logger logger = Logger.getLogger(TerugmeldCacheFilter.class
			.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mule.api.routing.filter.Filter#accept(org.mule.api.MuleMessage)
	 */
	@Override
	public boolean accept(MuleMessage message) {
		boolean result = false;
		String payloadAsString;
		try {
			payloadAsString = message.getPayloadAsString();
		} catch (Exception cause) {
			throw new RtmfGucException(
					"Message payload could not be transformed to a String",
					cause);
		}
		String targetServiceCalls = cache
				.determineServiceCallsForTerugmelding(payloadAsString);

		/*
		 * Kijk of de 'expectedService' gelijk is aan 'Both'. Indien dit het
		 * geval is, dan moeten ook alle attributen worden nagekeken. Indien
		 * alle attributen de waarde 'Both' teruggeven, dan moet voor het object
		 * eigenlijk de waarde 'TMF' gelden, daar het landelijke 'TMF' leidend
		 * is, en dan de attributen voor Rotterdam uit de payload worden
		 * gefiltered in 'TerugmeldenSplitter.groovy' en er een mail zonder
		 * attributen naar Rotterdam gestuurd zou worden.
		 */
		if (expectedService.equalsIgnoreCase("Both")) {
			/*
			 * Alle attributen zijn voor 'Both'. Dat wil zeggen dat alle
			 * attributen OOK voor het landelijke TMF zijn. Daar het landelijke
			 * TMF leidend is wordt nu de check op 'Both' op false gezet. De
			 * check die later uitgevoerd gaat worden op 'TMF' zal true
			 * teruggeven.
			 */
			result = targetServiceCalls.equals(expectedService)
					&& hasGmOnlyAttributes(payloadAsString);
		} else if (expectedService.equalsIgnoreCase("TMF")) {
			if (targetServiceCalls.equalsIgnoreCase("TMF")) {
				/*
				 * Er is gevraagd om TMF, en de cache geeft TMF terug, dus: true
				 */
				result = true;
			} else if (targetServiceCalls.equalsIgnoreCase("Both")) {
				/*
				 * Er is gevraagd om TMF, maar er komt Both terug, dan ook nog
				 * alle attributen checken. De attributen moeten dan allemaal
				 * Both teruggeven.
				 */
				result = !hasGmOnlyAttributes(payloadAsString);
			}
		} else if (expectedService.equalsIgnoreCase("GM")) {
			/*
			 * Er is gevraagd om GM, en de cache geeft GM terug, dus: true
			 */
			result = targetServiceCalls.equals(expectedService);
		}

		logger.debug("expectedService : " + expectedService);
		logger.debug("Het resultaat is: " + result);
		return result;
	}

	/**
	 * Kijk of een payload attributen bevat welke voor GM bedoelt zijn.
	 * @param payloadAsString
	 * @return 
	 */
	private boolean hasGmOnlyAttributes(String payloadAsString) {
		boolean result = false;

		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(new HardcodedNamespaceResolverTerugmelding());
		String expression = "//ns2:terugmelding/ns2:attributen/ns2:attribuutIdentificatie";
		InputSource inputSource = new InputSource(new StringReader(
				payloadAsString));
		try {
			NodeList nodes = (NodeList) xpath.evaluate(expression, inputSource,
					XPathConstants.NODESET);
			logger.debug("Aantal gevonden attribute nodes: "
					+ nodes.getLength());

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				String attribute = node.getTextContent();
				logger.debug("Attribute: " + attribute);
				String service = cache.determineServiceCallsForKey(attribute);
				logger.debug("Service: " + service);
				if (service.equalsIgnoreCase("GM")) {
					result = true;
					break;
				}
			}

		} catch (XPathExpressionException xee) {
			throw new RtmfGucException(
					String
							.format(
									"Er is een exception opgetreden bij het evalueren van een XPath expressie: %s",
									expression), xee);
		}

		return result;
	}

}
