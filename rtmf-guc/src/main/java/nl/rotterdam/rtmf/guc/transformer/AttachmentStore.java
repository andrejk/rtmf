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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author rweverwijk
 *
 */
public class AttachmentStore {
	private Logger logger = Logger.getLogger(AttachmentStore.class);
	private static Map<String, List<Attachment>> attachments = new HashMap<String, List<Attachment>>();
	
	/**
	 * Methode om attachments op te slaan.
	 * @param key
	 * @param attachment
	 * @return true als het opslaan goed gegaan is, false als het niet goed gegaan is.
	 */
	public boolean storeAttachment(String key, List<Attachment> attachment) {
		logger.debug("Attachments toegevoegd met key: " + key);
		return attachments.put(key, attachment) != null;
	}
	
	/**
	 * Methode om attachments te verwijderen uit de store op basis van de key
	 * @param key de key waarmee de te verwijderen attachments in de store staat.
	 * @return true als het verwijderen goed is gegaan. False als het verwijderen niet goed gegaan is
	 * of als het element niet aanwezig was in de store.
	 */
	public boolean removeAttachment(String key) {
		boolean result = attachments.remove(key) != null;
		if (result) {
			logger.debug("Attachments verwijderd met key: " + key);
		} else {
			logger.debug("Attachments is NIET verwijderd met key: " + key);
		}
		return result;
	}
	
	public List<Attachment> getAttachments(String key) {
		return attachments.get(key);
	}
}
