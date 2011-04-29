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

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import nl.rotterdam.rtmf.guc.common.terugmelding.HardcodedNamespaceResolverTerugmelding;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author rweverwijk
 * 
 */
public class TMFObjectAndValuesRequestToGMObjectInfo extends
		AbstractMessageAwareTransformer {

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
		try {
			logger.debug("Het bericht voor transformatie: " + message.getPayloadAsString());
			String transformedMessage = transformMessage(message.getPayloadAsString());
			if (transformedMessage != null) {
				message.setPayload(transformedMessage);
				logger.debug("De payload na transformer: " + transformedMessage);
			}
		} catch (Exception e) {
			throw new RtmfGucException("Er is een fout opgetreden bij het ophalen van de payload als String", e);
		}
		return message;
	}

	/**
	 * Voer de attachments uit de store toe aan de terugmeld payload
	 * 
	 * @param payloadAsString
	 *            De attachment data
	 * @return List<PayloadDataSource>
	 */
	public String transformMessage(String payloadAsString) {

		/*
		 * Maak een XPath query aan
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(new HardcodedNamespaceResolverTerugmelding());
		String brExpression = "//stell:getObjectInfoAndValues/stel:BRTag";
		String objectExpression = "//stell:getObjectInfoAndValues/stel:ObjectTag";

		InputSource inputSource = new InputSource(new StringReader(
				payloadAsString));

		try {
			/*
			 * Haal een NodeList op van alle attachment nodes
			 */
			NodeList root = (NodeList) xpath.evaluate("//stell:getObjectInfoAndValues",
					inputSource, XPathConstants.NODESET);
			Node getObjectInfoAndValues = root.getLength() > 0 ? root.item(0) : null;
			if (getObjectInfoAndValues != null) {
				logger.debug("De node is gevonden");
				// creeer nodes
				Document doc = getObjectInfoAndValues.getOwnerDocument();
				Node getObjectInfo = doc.createElementNS("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd", "steli:getObjectInfo");
				Node brTag = doc.createElementNS("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd", "steli:BRTag");
				Node objectTag = doc.createElementNS("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd", "steli:ObjectTag");
				//values
				String btValue = (String)xpath.evaluate(brExpression,
						new InputSource(new StringReader(
								payloadAsString)), XPathConstants.STRING);
				brTag.setTextContent(btValue);
				String objectValue = (String)xpath.evaluate(objectExpression,
						new InputSource(new StringReader(
								payloadAsString)), XPathConstants.STRING);
				objectTag.setTextContent(objectValue);
				//nodes nesten
				getObjectInfo.appendChild(brTag);
				getObjectInfo.appendChild(objectTag);
				Node parentNode = getObjectInfoAndValues.getParentNode();
				parentNode.removeChild(getObjectInfoAndValues);
				parentNode.appendChild(getObjectInfo);
				
				
				return xmlToString(getObjectInfo.getParentNode().getParentNode());
			}
			logger.debug("Het object is null");
		} catch (XPathExpressionException xee) {
			throw new RtmfGucException(
									"Er is een exception opgetreden bij het evalueren van een XPath expressie: ", xee);
		}
		return null;
	}
	
	public static String xmlToString(Node node) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        }  catch (TransformerConfigurationException e) {
        	throw new RtmfGucException("Er is een fout opgetreden bij het wegschrijven van de payload zonder attachment", e);
        } catch (javax.xml.transform.TransformerException e) {
			throw new RtmfGucException("Er is een fout opgetreden bij het wegschrijven van de payload zonder attachment", e);
		}
    }

}
