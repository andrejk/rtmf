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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DocumentParser {
	/**
	 * Wandel door de payload heen, en zoek naar een Tag door middel van een
	 * XPath query. De gevonden tag waarde wordt teruggegeven.
	 * 
	 * @param payloadAsString
	 *            De payload welke doorzocht moet worden
	 * @param tagPath
	 *            XPath query waar de/een tag gevonden kan worden in het
	 *            document
	 * @return De tag waarde
	 * @throws RtmfGucException
	 */
	public static String parseTmfDocument(String payloadAsString, String tagPath)
			throws RtmfGucException {
		return (String)parseDocument(payloadAsString, tagPath, XPathConstants.STRING);
	}
	
	public static Object parseDocument(String payloadAsString, String tagPath, QName resultType)
			throws RtmfGucException {
		Object value = null;
		try {
			// Maak een nieuw document aan
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(false);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(
					payloadAsString.getBytes("UTF-8")));

			// Maak een XPath Query aan
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile(tagPath);

			value = expr.evaluate(doc, resultType);

		} catch (ParserConfigurationException e) {
			throw new RtmfGucException(e);
		} catch (SAXException e) {
			throw new RtmfGucException(
					"De payload kan niet verwerkt worden. Er zit een fout in de structuur van het bericht: "
							+ payloadAsString, e);
		} catch (IOException e) {
			throw new RtmfGucException(
					"Er is een fout opgetreden bij het inlezen van de payload",
					e);
		} catch (XPathExpressionException e) {
			throw new RtmfGucException(
					String
							.format(
									"Er is een fout opgestreden bij het doorlopen van de payload op basis van de xpath: %s",
									tagPath), e);
		}

		return value;
	}
	
	/**
	 * Deze methode haalt alle waarden op uit de xml. De standaard methode geeft alleen het eerste resultaat, 
	 * deze methode geeft alle gevonden waarden.
	 * @param payloadAsString De te verwerken xml
	 * @param xpathExpression De xpath expressie welke het resultaat moet opleveren.
	 * @return List met elke gevonden waarde als String.
	 */
	public static List<String> getAllValues(String payloadAsString, String xpathExpression) {
		List<String> result = new ArrayList<String>();
		NodeList nodeList = (NodeList) DocumentParser.parseDocument(payloadAsString, xpathExpression, XPathConstants.NODESET);
		if (nodeList.getLength() != 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node item = nodeList.item(i);
				result.add(item.getFirstChild().getNodeValue());
			}
		}
		return result;
	}
}
