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

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class RemoveAttachmentsFromStoreTransformer extends
		AbstractMessageAwareTransformer {

	AttachmentStore store;
	
	@Override
	public Object transform(MuleMessage message, String outputEncoding)
			throws TransformerException {
			
		Object property = message.getProperty("attachmentKey");
		if (property != null && property instanceof String) {
			store.removeAttachment((String)property);
		} else {
			logger.info("De property attachmentKey is niet gevonden in de muleMessage. De attachment kan nu niet verwijderd worden uit de store.");
		}
		
		return message;
	}
	
	/**
	 * @param store the store to set
	 */
	public void setStore(AttachmentStore store) {
		this.store = store;
	}
}
