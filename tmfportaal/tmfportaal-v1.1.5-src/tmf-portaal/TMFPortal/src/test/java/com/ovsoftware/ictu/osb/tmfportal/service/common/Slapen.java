package com.ovsoftware.ictu.osb.tmfportal.service.common;

import org.apache.log4j.Logger;

/**
 * Utility klasse voor het slapen tijdens testen.
 * 
 * @author ktinselboer
 */
public class Slapen {
	
	private static Logger logger = Logger.getLogger(Slapen.class);
	
	/**
	 * Nep constructor die voorkomt dat je een instantie kunt aanmaken
	 * van deze utility klasse.
	 * 
	 * @throws UnsupportedOperationException
	 */
	protected Slapen() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Houdt een kleine powernap. Wordt gebruikt tijdens het testen van de
	 * webservices welke enige tijd nodig hebben om een verzoek te verwerken.
	 */
	public static void kleinePowernap() {
		int seconden = 40;
		logger.trace("Ik ga even " + seconden + " seconden slapen...");
		try {
			Thread.sleep(seconden * 1000);
		} catch (InterruptedException e) {
			logger.error("InterruptedException tijdens mijn powernap!", e);
		}
		logger.trace("...en ik ben weer wakker!");		
	}
}
