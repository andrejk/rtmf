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
import java.util.List;

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
public class AddAttachmentToTerugmelding extends
		AbstractMessageAwareTransformer {

	/**
	 * @param store the store to set
	 */
	public void setStore(AttachmentStore store) {
		this.store = store;
	}

	private AttachmentStore store;

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
			message.setPayload(buildAttachmentList(message.getPayloadAsString(), (String)message.getProperty("attachmentKey")));
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
	public String buildAttachmentList(String payloadAsString, String key) {

		/*
		 * Maak een XPath query aan
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(new HardcodedNamespaceResolverTerugmelding());

		InputSource inputSource = new InputSource(new StringReader(
				payloadAsString));

		try {
			/*
			 * Haal een NodeList op van alle attachment nodes
			 */
			NodeList root = (NodeList) xpath.evaluate("//ns2:terugmelding",
					inputSource, XPathConstants.NODESET);
			Node terugmelding = root.getLength() > 0 ? root.item(0) : null;
			if (terugmelding != null) {
				Document doc = terugmelding.getOwnerDocument();

				List<Attachment> attachments = store.getAttachments(key);
				/*
				 * Kopieer van iedere attachment, de naam, en de data (pdf)
				 */
				for (Attachment attachment : attachments) {
					// create nodes
					Node attachmentNode = doc.createElementNS(terugmelding.getNamespaceURI(), terugmelding.getPrefix() + ":attachment");
					Node filename = doc.createElementNS(terugmelding.getNamespaceURI(), terugmelding.getPrefix() + ":filename");
					Node data = doc.createElementNS(terugmelding.getNamespaceURI(), terugmelding.getPrefix() + ":base64attachment");
					// fill values
					filename.setTextContent(attachment.getFilename());
					data.setTextContent(attachment.getData());
					// nest nodes
					attachmentNode.appendChild(filename);
					attachmentNode.appendChild(data);
					terugmelding.appendChild(attachmentNode);
				}
				return xmlToString(terugmelding.getParentNode().getParentNode());
			}

		} catch (XPathExpressionException xee) {
			throw new RtmfGucException(
					"Er is een exception opgetreden bij het evalueren van een XPath expressie", xee);
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
