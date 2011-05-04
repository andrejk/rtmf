package com.ovsoftware.ictu.osb.tmfportal.service.common;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

/**
 * Deze SOAPHandler verandert de OSBGateway name-spaces van response berichten.
 * 
 * Elke OSBGateway gebruikt een eigen namespace voor default response berichten voor ebMS services.
 * Het TMF Portaal gebruikt deze namespaces van JAX-B xml naar object mapping. Hierbij wordt er gebruik
 * gemaakt van de namespace: "http://tmfportal.ovsoftware.com/services/defaultreply.xsd".
 * 
 * Deze handler vervangt alle OSBGateway namespaces in de default namespace zoals deze wordt begrepen door
 * de JAX-B mapping.
 * 
 * @author pboevink
 *
 */
public class NameSpaceFixerHandler implements SOAPHandler<SOAPMessageContext> {

	private Logger logger = Logger.getLogger(NameSpaceFixerHandler.class);
	private static final String DEFAULT_NAMESPACE = "http://tmfportal.ovsoftware.com/services/defaultreply.xsd";
	private static final String EBMS_RESPONSE_IDENTIFIER = "defaultreply.xsd";
	
	/**
	 * @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
	 * @return Set &lt;QName&gt; getter
	 */
	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	/**
	 * @see javax.xml.ws.handler.soap.SOAPHandler#close()
	 * @param context MessageContext
	 */
	@Override
	public void close(MessageContext context) {
	}

	/**
	 * @see javax.xml.ws.handler.soap.SOAPHandler#handleFault()
	 * @param context SOAPMessageContext
	 * @return boolean indicating to kick the remaining handlers
	 */
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	/**
	 * Vervangt de namespace uri van alle default response berichten.
	 * 
	 * @param context Bevat de message die we willen bewerken
	 * @return True
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		SOAPMessage message = context.getMessage();
		try {
			Boolean outbound = (Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			if (!outbound) {
				//only handle incoming response messages
				
				Iterator it = message.getSOAPBody().getChildElements();
				while (it.hasNext()) {
					Node n = (Node)it.next();
					if (n instanceof SOAPElement) {
						String prefix = ((SOAPElement)n).getElementQName().getPrefix();
						String uri = ((SOAPElement)n).getElementQName().getNamespaceURI();
						String localPart = ((SOAPElement)n).getElementQName().getLocalPart();
						if (uri.contains(EBMS_RESPONSE_IDENTIFIER)) {
							((SOAPElement)n).setElementQName(new QName(DEFAULT_NAMESPACE, localPart, prefix));
						}
					}
				}
			}
		} catch (SOAPException e) {
			logger.error("SOAPException in handleMessage", e);
		}
		return true;
	}

}
