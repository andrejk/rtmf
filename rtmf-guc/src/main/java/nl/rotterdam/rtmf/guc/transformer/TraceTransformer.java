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

import java.util.Iterator;

import nl.rotterdam.rtmf.guc.common.RTMFStringUtils;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class TraceTransformer extends AbstractMessageAwareTransformer {

	@SuppressWarnings("unchecked")
	@Override
	public Object transform(MuleMessage message, String outputEncoding)
			throws TransformerException {
		Logger log = Logger.getLogger(endpoint.getName());
		
		Iterator<String> propIterator = message.getPropertyNames().iterator();
		String propString = null;
		while(propIterator.hasNext()){
			String key = propIterator.next();
			propString += key + " = " + message.getProperty(key) + ", ";
		}

	
		String logMsg = 
			"messageId=" + message.getUniqueId()
		  + ", correlationId=" + message.getCorrelationId()
		  + ", replyTo=" + message.getReplyTo() 
		  + ", soapAction=" + message.getProperty("Soapaction")
		  + ", muleOriginatingEndpoint=" + message.getProperty("MULE_ORIGINATING_ENDPOINT")
          + ", protocol=" + endpoint.getProtocol() + ", endpoint properties=" + endpoint.getProperties().toString() + ", uri=" + endpoint.getEndpointURI()
          ;
		if(log.isDebugEnabled() && message.getAttachmentNames().size()>0) {
			Iterator aNames = message.getAttachmentNames().iterator();
			while (aNames.hasNext()) {
				logMsg += ", attachment=" + (String)aNames.next();
			}
		}
		if(log.isDebugEnabled() || message.getExceptionPayload() != null ){
			try {
				String[] payloads = RTMFStringUtils.payloadToArray(message.getPayloadAsString());
				for (int i=0; i < payloads.length; i++) {
					logMsg += ", payload[" + i + "]=" + message.getPayloadAsString();
				}
			} catch (Exception e) {
				logMsg += ", payload=" + message.getPayload();
			}
		}
		if(message.getExceptionPayload() != null){
			logMsg += ", exceptionPayload=" + message.getExceptionPayload() + ", properties=" + propString;
		}
		
		if (message.getExceptionPayload() != null) {
			log.error(logMsg);
		}
		else if (log.isDebugEnabled()) {
			log.debug(logMsg);
		} 
		else {
			log.info(logMsg);
		}

		return message.getPayload();
	}
}