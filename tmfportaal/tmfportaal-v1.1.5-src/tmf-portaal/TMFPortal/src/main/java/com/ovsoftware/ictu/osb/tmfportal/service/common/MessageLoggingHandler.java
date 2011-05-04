package com.ovsoftware.ictu.osb.tmfportal.service.common;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Dit is een implementatie van een SOAPHandler waarin berichten gelogd
 * worden mbv Apache log4j.
 * 
 * @author OVSoftware
 *
 */
public class MessageLoggingHandler implements SOAPHandler<SOAPMessageContext> {

	private Logger logger = Logger.getLogger(MessageLoggingHandler.class);
	
	/**
	 * Constructor, roept super() aan.
	 */
	public MessageLoggingHandler() {
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
	 * @param context Wordt niet gebruikt
	 */
	@Override
	public void close(MessageContext context) {
	}

	/**
	 * Verhoogt tijdelijk het logging niveau, schrijft het bericht
	 * als error weg in het log en retourneert True zodat andere
	 * Handlers de fout evt kunnen afhandelen.
	 * 
	 * @param context De context met daarin het soap-bericht
	 * @return True
	 */
	@Override
	public boolean handleFault(SOAPMessageContext context) {		
		//start het foutbericht
		logger.error("!!! START FAULT !!!!!!!!!!!!!!!!");
		
		//onthoud het vorige level en set het level tijdelijk naar trace
		Level prevLevel = logger.getLevel();
		logger.setLevel(Level.TRACE);
		
		//log het fault-bericht (op trace niveau)
		handleMessage(context);		
		
		//zet het niveau weer terug
		logger.setLevel(prevLevel);
		
		//beeindig het foutbericht
		logger.error("!!! EINDE FAULT !!!!!!!!!!!!!!!!");
		
		//geef aan dat eventuele andere handlers de fout kunnen gaan afhandelen
		return true;
	}

	/**
	 * Logt de inhoud van het verzonden SOAP-bericht mbv log4j.
	 * 
	 * @param context Bevat de message die we willen loggen
	 * @return True
	 */
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		
		logger.trace("=== START ENVELOPE ==========");
		
		//HEADERS LOGGEN
		logger.trace("=== START HTTP HEADERS ===");
		Set<String> keys = context.keySet();
    	for (String s: keys) {
    		logger.trace(s + " :: " + context.get(s));
    	}
		logger.trace("=== EINDE HTTP HEADERS ===");        	
    	
        //BERICHT INHOUD LADEN
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			context.getMessage().writeTo(baos);
		} catch (Exception e) {
			logger.error("ERROR while trying to write the SOAP message to log4j", e);
		}
		
		//BERICHT LOGGEN (INKOMEND OF UITGAAND)
		logger.trace("=== START SOAP HEADERS + BODY ===");
		Boolean outbound = (Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound) {
			logger.trace("Uitgaand bericht naar web service provider:\n" + baos);
		} else {
			logger.trace("Inkomend bericht van web service provider:\n" + baos);
		}
		logger.trace("=== EINDE SOAP HEADERS + BODY ===");
			
		logger.trace("=== EINDE ENVELOPE ==========");

		return true;
	}
}
