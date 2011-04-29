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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;

import nl.rotterdam.rtmf.guc.common.RTMFStringUtils;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.util.Base64;

public class AddPayloadAsAttachmentTransformer extends
		AbstractMessageAwareTransformer {

	static final String CONTENT_TYPE_XML = "text/xml; charset=\"utf-8\"";
	static final String CONTENT_TYPE_PDF = "application/pdf";

	static final String TERUGMELDING = "terugmelding.xml";

	static final String XPATH_ATTACHMENT = "//terugmelding/attachment";

	private AttachmentStore store;

	public AddPayloadAsAttachmentTransformer() {
		this.setReturnClass(MuleMessage.class);
	}

	@Override
	public Object transform(MuleMessage message, String outputEncoding)
			throws TransformerException {

		/*
		 * Kloon de MuleMessage zodat we een werk kopie hebben
		 */
//		MuleMessage myMessage = (MuleMessage) SerializationUtils.clone(message);

		/*
		 * Bouw een lijst met bericht attachments (toegevoegde pdf bestanden
		 * vanuit de portal)
		 */
		List<PayloadDataSource> atList = null;

			/*
			 * Payload zou een array van payloads kunnen zijn.
			 */
			String terugmeldingOfIntrekking = null;
			String[] payloads = null;
			try {
				payloads = RTMFStringUtils.payloadToArray(message.getPayloadAsString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RtmfGucException(
						"Er is een fout opgetreden tijdens het toevoegen van de Terugmelding attachments aan de DataHandler, omdat: " + e.getMessage(),
						e.getCause());
			}
			logger.debug("Aantal gevonden payloads: " + payloads.length);
			for (int i=0; i <payloads.length; i++) {
				String aPayload = payloads[i];
				logger.debug("Payload[" + i + "]: " + aPayload);
				
				if (aPayload.contains("terugmelding")
						|| aPayload.contains("intrekking")) {
					terugmeldingOfIntrekking = aPayload;
					break;
				}
			}
			logger.debug("De payload is: " + terugmeldingOfIntrekking);

			atList = buildAttachmentList(CONTENT_TYPE_PDF,
					message);
		

		/*
		 * Voeg de losse attachments nu toe als attachments aan de MuleMessage
		 * werk kopie
		 */
		if (atList != null && atList.size() > 0) {
			logger.debug("Aantal attachment PayloadDatasources: "
					+ atList.size());
			for (PayloadDataSource apds : atList) {
				DataHandler atHandler = new DataHandler(apds);
				try {
					message.addAttachment(atHandler.getName(), atHandler);
				} catch (Exception e) {
					throw new RtmfGucException(
							"Er is een fout opgetreden tijdens het toevoegen van een Terugmelding attachment aan de MuleMessage.",
							e.getCause());
				}
			}
		}

		/*
		 * Maak nu van de MuleMessage een attachment welke aan de werk kopie
		 * gehangen wordt
		 */
		PayloadDataSource terugmeldDS = null;
		try {
			terugmeldDS = buildPayloadDataSource(TERUGMELDING,
					CONTENT_TYPE_XML, message.getPayloadAsBytes());
		} catch (Exception e) {
			throw new RtmfGucException(
					"Er is een fout opgetreden tijdens het toevoegen van de Terugmelding aan de DataHandler.",
					e.getCause());
		}
		DataHandler handlerTerugmelding = new DataHandler(terugmeldDS);

		/*
		 * Voeg de MuleMessage aan de attachments toe
		 */
		try {
			message.addAttachment("terugmelding", handlerTerugmelding);
		} catch (Exception e) {
			throw new RtmfGucException(
					"Er is een fout opgetreden tijdens het toevoegen van de Terugmelding als attachment aan de MuleMessage.",
					e.getCause());
		}

		/*
		 * Kopieer de originele payload naar het werk kopie
		 */
		message.setPayload(message.getPayload());

		/*
		 * En klaar is Klara
		 */
		return message;
	}

	

	/**
	 * Maak een PayloadDataset object aan.
	 * 
	 * @param name
	 *            Dataset naam
	 * @param contentType
	 *            ContentType (bv text/xml)
	 * @param data
	 *            De eigenlijke data
	 * @return PayloadDataSource
	 */
	private PayloadDataSource buildPayloadDataSource(String name,
			String contentType, byte[] data) {
		PayloadDataSource pds = new PayloadDataSource();
		pds.setName(name);
		pds.setContentType(contentType);
		pds.setData(data);

		return pds;
	}

	/**
	 * Bouw een lijst op van PayloadDatasets. Deze PayloadDatases bevatten de
	 * attachments welke door de portal met het terugmeld bericht zijn
	 * meegestuurd.
	 * 
	 * @param contentType
	 *            ContentType (bv application/pdf)
	 * @param payloadAsString
	 *            De attachment data
	 * @return List<PayloadDataSource>
	 */
	private List<PayloadDataSource> buildAttachmentList(String contentType,
			MuleMessage message) {
		List<PayloadDataSource> atList = new ArrayList<PayloadDataSource>();
		if (message.getProperty("attachmentKey") != null) {
			List<Attachment> attachments = store.getAttachments((String) message
					.getProperty("attachmentKey"));
			if (attachments != null) {
			logger.debug(String.format("There are %d attachments found", attachments.size()));
				for (Attachment attachment : attachments) {
		
					PayloadDataSource pds = new PayloadDataSource();
					pds.setContentType(contentType);
		
					pds.setName(attachment.getFilename());
		
					/*
					 * De attachment in het terugmeldbericht is een base64 encoded
					 * 'String'. Ls deze 'String' niet ge-decode wordt, dan wordt het
					 * mime-type iets van text/plain, en kan de pdf niet meer gelezen
					 * worden. Daarom de 'String' decoden, en wegschrijven als een
					 * byte-array.
					 */
					if (attachment.getData() != null) {
						byte[] buf;
						try {
							buf = Base64.decode(attachment.getData());
						} catch (Exception e) {
							throw new RtmfGucException(
									"Er is een exception opgetreden bij het decoderen van een attachment",
									e);
						}
						pds.setData(buf);
					}
					logger.debug(String.format("The attachment with filename %s is added to the list", attachment.getFilename()));
					atList.add(pds);
		
				}
			} else {
				logger.debug("There are no attachments found");
			}
		} else {
			logger.info("There is no attachmentKey found with the message");
		}
		return atList;
	}

	/**
	 * Een convenient class welke een DateSource implementeerd die aan een
	 * DataHandler gehangen kan worden.
	 * 
	 * @author Enno Buis
	 * 
	 */
	private class PayloadDataSource implements javax.activation.DataSource {

		private byte[] data = new byte[0];
		private String contentType = null;
		private String name = null;

		public void setData(byte[] data) {
			this.data = data;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public void setName(String name) {
			this.name = name;
		}

		public InputStream getInputStream() {
			return new ByteArrayInputStream(data);
		}

		public OutputStream getOutputStream() {
			return null;
		}

		public String getContentType() {
			return contentType;
		}

		public String getName() {
			return name;
		}

	}
	
	/**
	 * @param store the store to set
	 */
	public void setStore(AttachmentStore store) {
		this.store = store;
	}

}
