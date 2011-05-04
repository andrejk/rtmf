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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import nl.rotterdam.rtmf.guc.common.terugmelding.HardcodedNamespaceResolverTerugmelding;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;
import nl.rotterdam.rtmf.guc.transformer.helper.RTMFDataHandler;
import nl.rotterdam.rtmf.guc.transformer.helper.TerugmeldenXMLFilter;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class MoveAttachmentsToStoreTransformer extends
		AbstractMessageAwareTransformer {

	AttachmentStore store;
	
	@Override
	public Object transform(MuleMessage message, String outputEncoding)
			throws TransformerException {
		logger.info("Starting to move attachments to the store");
		/*
		 * Kopieer de originele payload naar het werk kopie
		 */
			filterAttachments(message);
		/*
		 * En klaar is Klara
		 */
		return message;
	}

	/**
	 * @param orginalPayload
	 * @return
	 */
	private Object stripAttachments(Object orginalPayload) {
		long start = System.currentTimeMillis();
		logger.debug("### start removing attachment");
		if (orginalPayload instanceof String) {
			Transformer transformer;
			StreamResult result = null;
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(new InputSource(new StringReader(
						(String)orginalPayload)));

				transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				
				/////////
				Node node = doc.getFirstChild();
				XPath xpath = XPathFactory.newInstance().newXPath();
				xpath.setNamespaceContext(new HardcodedNamespaceResolverTerugmelding());
				String expression = "//terugmelding/attachment";
				NodeList nodeList = (NodeList) xpath.evaluate(expression, node, XPathConstants.NODESET);
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node item = nodeList.item(i);
					item.getParentNode().removeChild(item);
				}
				
				/////////
				DOMSource source = new DOMSource(node);

				//initialize StreamResult with File object to save to file
				
				result = new StreamResult(new StringWriter());
				transformer.transform(source, result);
				String resultString = result.getWriter().toString();
				logger.debug(String.format("Het verwijderen van de attachments kost: %d miliseconden", System.currentTimeMillis() - start));
				logger.debug("Na het verwijderen van de attachments is dit de payload: " + resultString);
				return resultString;
			} catch (TransformerConfigurationException e) {
	        	throw new RtmfGucException("Er is een fout opgetreden bij het verwijderen van de attachments", e);
			} catch (TransformerFactoryConfigurationError e) {
				throw new RtmfGucException("Er is een fout opgetreden bij het verwijderen van de attachments", e);
			} catch (ParserConfigurationException e) {
				throw new RtmfGucException("Er is een fout opgetreden bij het verwijderen van de attachments", e);
			} catch (SAXException e) {
				throw new RtmfGucException("Er is een fout opgetreden bij het verwijderen van de attachments", e);
			} catch (IOException e) {
				throw new RtmfGucException("Er is een fout opgetreden bij het verwijderen van de attachments", e);
			} catch (javax.xml.transform.TransformerException e) {
				throw new RtmfGucException("Er is een fout opgetreden bij het verwijderen van de attachments", e);
			} catch (XPathExpressionException e) {
				throw new RtmfGucException("Er is een fout opgetreden bij het verwijderen van de attachments", e);
			}
		}
		return null;
	}
	
	public Object filterAttachments(MuleMessage message) {
		long start = System.currentTimeMillis();
		logger.debug("### start filtering attachment");
			
			try {
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();
				parserFactory.setNamespaceAware(true);
				
				XMLReader xmlReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
				RTMFDataHandler rtmfDataHandler = new RTMFDataHandler();
				TerugmeldenXMLFilter terugmeldenXMLFilter = new TerugmeldenXMLFilter();
				
				terugmeldenXMLFilter.setParent(xmlReader);
				terugmeldenXMLFilter.setContentHandler(rtmfDataHandler);
				terugmeldenXMLFilter.parse(new InputSource((InputStream)message.getPayload()));
				String result = rtmfDataHandler.getOutput();
				message.setPayload(result);
				message.setProperty("attachmentKey", message.getUniqueId());
				List<Attachment> attachments = rtmfDataHandler.getAttachments();
				logger.debug(String.format("Er zijn %d attachments gevonden", attachments.size()));
				store.storeAttachment(message.getUniqueId(), attachments);
				logger.debug(String.format("Het verwijderen van de attachments kost: %d miliseconden", System.currentTimeMillis() - start));
				logger.debug(String.format("Na het filteren van de attachment is dit de xml: %s", result));
				return result;
			} catch (SAXException e) {
				throw new RtmfGucException("Er is een fout opgetreden bij het verwijderen van de attachments", e);
			} catch (IOException e) {
				throw new RtmfGucException("Er is een fout opgetreden bij het verwijderen van de attachments", e);
			}
	}
	
	/**
	 * @param store the store to set
	 */
	public void setStore(AttachmentStore store) {
		this.store = store;
	}
}
