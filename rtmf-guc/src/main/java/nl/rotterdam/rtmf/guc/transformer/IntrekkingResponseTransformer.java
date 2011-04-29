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
import org.mule.transport.NullPayload;

public class IntrekkingResponseTransformer extends
		AbstractMessageAwareTransformer {
	
	private final String SUCCES_PAYLOAD = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">" + 
			"      <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/intrekking</wsa:Action>" + 
			"      <wsa:RelatesTo RelationshipType=\"http://www.w3.org/2005/08/addressing/reply\">uuid:%s</wsa:RelatesTo>" + 
			"      <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>" + 
			"   </soapenv:Header>" + 
			"   <soapenv:Body>" + 
			"      <defrep:response xmlns:defrep=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\">" + 
			"         <text>OK</text>" + 
			"      </defrep:response>" + 
			"   </soapenv:Body>" + 
			"</soapenv:Envelope>";
	
	private final String FOUT_PAYLOAD = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"	            <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">" + 
			"	            <wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/terugMeldenService/terugmelding</wsa:Action>" + 
			"	            <wsa:RelatesTo RelationshipType=\"http://www.w3.org/2005/08/addressing/reply\">uuid:%s</wsa:RelatesTo>" + 
			"	            <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>" + 
			"	           </soapenv:Header>" + 
			"	           <soapenv:Body>" + 
			"	               <defrep:response xmlns:defrep=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\">" + 
			"	                  <text>" + 
			"	      	    		Er is een fout opgetreden Foutcode: %d Foutboodschap: %s</text>" + 
			"	               </defrep:response>" + 
			"	            </soapenv:Body>" + 
			"	         </soapenv:Envelope>";
	
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
		boolean succes = false;
		int foutCode = 0;
		String foutBericht = "";
		if (message.getPayload() instanceof NullPayload
				&& message.getExceptionPayload() == null) {
				succes = true;
			} 
		 else if (message.getExceptionPayload() != null) {
			foutCode = message.getExceptionPayload().getCode();
			foutBericht = message.getExceptionPayload().getMessage();
		} else {
			foutCode = Integer.parseInt(ValidateMessage.VALIDATIE_CODE_STRING);
			foutBericht = "Er is een onbekende fout opgetreden bij het verwerken van de intrekking voor het Rotterdamse zakenmagazijn.";
		}

		if (succes) {
			message.setPayload(String.format(SUCCES_PAYLOAD, message.getCorrelationId()));
		} else {
			message.setPayload(String.format(FOUT_PAYLOAD, message.getCorrelationId(), foutCode, foutBericht));
		}
		return message;
	}
}
