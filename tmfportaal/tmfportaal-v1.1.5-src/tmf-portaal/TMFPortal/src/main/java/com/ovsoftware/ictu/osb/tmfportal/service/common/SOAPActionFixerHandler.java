package com.ovsoftware.ictu.osb.tmfportal.service.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

/**
 * Deze SOAPHandler verandert de Action-header in zowel de set van
 * HTTP-headers als de SOAP-headers. Er wordt gezocht naar oude waardes
 * (action_old) uit settings.properties, welke indien ze gevonden wordt
 * vervangen wordt door de waarde van de bijbehorende action_new property.
 * 
 * @author ktinselboer
 *
 */
public class SOAPActionFixerHandler implements SOAPHandler<SOAPMessageContext> {

	private Logger logger = Logger.getLogger(SOAPActionFixerHandler.class);

	/**
	 * Constructor, de standaard logger zal worden gebruikt.
	 */
	public SOAPActionFixerHandler() {
		super();
	}

	/**
	 * Retourneert null.
	 * 
	 * @return null
	 */
	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	/**
	 * Retourneert void.
	 * 
	 * @param context Bevat het SOAP-bericht, wordt niet gebruikt
	 */
	@Override
	public void close(MessageContext context) {
	}

	/**
	 * Retourneert altijd true, geeft afhandeling fout door aan
	 * de volgende SOAPHandler, te weten MessageLoggingHandler.
	 * 
	 * @param context Bevat het SOAP-bericht
	 * @return True
	 */
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	/**
	 * Vervangt de 'Action' header in de HTTP-headers en in de
	 * SOAP-headers. Als een van de waardes behorend bij een key
	 * uit het settings.properties bestand die eindigt op "action_old"
	 * wordt gevonden, dan wordt deze vervangen door de waarde van
	 * de bijbehorende key die eindigt op "action_new".
	 * 
	 * @param context Bevat de message die we willen bewerken
	 * @return True
	 */
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		
		//STAP 1/2 - SOAP
		//Haal SOAP-Header op
		SOAPMessage message = context.getMessage();
		SOAPHeader header = null;
		try {
			header = message.getSOAPHeader();
		} catch (SOAPException se) {
			logger.error("SOAPException in handleMessage", se);
			return true;
		}
		//Haal header 'Action' of 'wsa:Action' op
		NodeList list = header.getElementsByTagName("Action");
		if (list.getLength() == 0) { list = header.getElementsByTagName("wsa:Action"); }
		if (list.getLength() == 0) {
			logger.error("Could not find HTTP header 'Action' or 'wsa:Action'!");
			return true;
		}
		//Vervang SOAP-Header 'Action' indien nodig
		String httpOud = list.item(0).getFirstChild().getTextContent();
		String httpNieuw = correctAction(httpOud);
		list.item(0).getFirstChild().setNodeValue(httpNieuw);
		logger.trace("HTTP_OUD   = " + httpOud);
		logger.trace("HTTP_NIEUW = " + httpNieuw);
				
		//STAP 2/2 - HTTP
		//Vervang HTTP-Header 'Action' indien nodig
		Set<Entry<String, Object>> httpHeaders = context.entrySet();
		Iterator<Entry<String, Object>> it = httpHeaders.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			if (entry.getKey().equals("javax.xml.ws.soap.http.soapaction.uri")) {
				String soapOud = (String) entry.getValue();
				String soapNieuw = correctAction(soapOud);
				entry.setValue(soapNieuw);
				logger.trace("SOAP_OUD   = " + soapOud);
				logger.trace("SOAP_NIEUW = " + soapNieuw);
			}
		}

		return true;
	}
	
	/**
	 * Controleert of de waarde action_old voorkomt als waarde van een key
	 * die eindigt op "action_old" in het bestand settings.properties. Indien
	 * dat het geval is dan wordt de waarde van de key teruggegeven die gelijk
	 * is aan de key behorend bij action_old, maar die eindigt op action_new.
	 * Met andere woorden: een waarde van een x.action_old key wordt vervangen
	 * door de waarde van de x.action_new key.
	 * 
	 * @param actionOld De waarde van de key eindigend op action_old
	 * @return De correcte waarde, dwz de waarde van de parameter of de waarde
	 * van de bijbehorende key die eindigt op action_new
	 */
	private String correctAction(String actionOld) {		
		//haal de key+value paren op voor alle properties eindigend op action_old
		ArrayList<KeyValuePair> kvpOldLijst = WebserviceSettings.getSettingsEndingWith("action_old"); 
		
		//action_old aanwezig in settings met keys eindigend op action_old?
		String actionOldKey = null;
		for (KeyValuePair kvp : kvpOldLijst) {
			if (kvp.getValue().equals(actionOld)) {
				actionOldKey = kvp.getKey();
				break;
			}
		}
		
		//check of er toevallig geen correctie nodig is
		if (actionOldKey == null) { return actionOld; }
		
		//vervang old door new in de key
		String actionNewKey = actionOldKey.substring(0, actionOldKey.indexOf(".action_old")) + ".action_new";
		
		//retourneer de nieuwe waarde		
		return WebserviceSettings.getValueOfSetting(actionNewKey);
	}
}
