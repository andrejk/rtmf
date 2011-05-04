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
package nl.rotterdam.rtmf.guc.cache;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;


import nl.rotterdam.rtmf.guc.StelselCatalogusCache;
import nl.rotterdam.rtmf.guc.cache.entity.CacheItem;
import nl.rotterdam.rtmf.guc.common.DocumentParser;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

public abstract class DetermineServiceCall implements StelselCatalogusCache {
	private Logger logger = Logger.getLogger(DetermineServiceCall.class
			.getName());

	protected abstract CacheItem getCacheItemByKey(String key)
			throws SQLException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.rotterdam.rtmf.guc.StelselCatalogusCache#
	 * determineServiceCallsForTerugmelding(java.lang.String)
	 */
	public String determineServiceCallsForTerugmelding(String payloadAsString)
			throws RtmfGucException {
		String result = null;
		String tagValue = null;

		if (payloadAsString.contains(":objectTag")) {
			tagValue = DocumentParser.parseTmfDocument(payloadAsString,
					"/Envelope/Body/terugmelding/objectTag/text()");
			result = determineServiceCallsForKey(tagValue);
		} else {
			throw new RtmfGucException(String.format(
					"No match found in cache for message: %s", payloadAsString));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.rotterdam.rtmf.guc.StelselCatalogusCache#determineServiceCallsForMessage
	 * (java.lang.String)
	 */
	public String determineServiceCallsForMessage(String payloadAsString)
			throws RtmfGucException {
		String result = null;
		String tagValue = null;
		// BasisregistratieList
		if (payloadAsString.contains("getBasisregistratieList")) {
			result = "Both";
		}
		// ObjectTypeList
		else if (payloadAsString.contains("getObjectTypeList")) {
			tagValue = DocumentParser.parseTmfDocument(payloadAsString,
					"/Envelope/Body/getObjectTypeList/BRTag/text()");
			result = determineServiceCallsForKey(tagValue);
		}
		// getObjectInfoAndValues
		else if (payloadAsString.contains("getObjectInfoAndValues")) {
			tagValue = DocumentParser.parseTmfDocument(payloadAsString,
					"/Envelope/Body/getObjectInfoAndValues/ObjectTag/text()");
			result = determineServiceCallsForKey(tagValue);
		}
		// ObjectInfo
		else if (payloadAsString.contains("getObjectInfo")) {
			tagValue = DocumentParser.parseTmfDocument(payloadAsString,
					"/Envelope/Body/getObjectInfo/ObjectTag/text()");
			result = determineServiceCallsForKey(tagValue);
		}
		// bevragen
		else if (payloadAsString.contains("bevragen")) {
			tagValue = DocumentParser.parseTmfDocument(payloadAsString,
					"/Envelope/Body/bevragen/objectTag/text()");
			result = determineServiceCallsForKey(tagValue);
		} else {
			throw new RtmfGucException(String.format(
					"Geen match gevonden in de cache voor key: %s",
					payloadAsString));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.rotterdam.rtmf.guc.StelselCatalogusCache#determineServiceCallsForKey
	 * (java.lang.String)
	 */
	public String determineServiceCallsForKey(String key)
			throws RtmfGucException {
		String result = null;
		// Verwijder whitespace; komt soms voor door XPath expressies
		key = key.trim();
		// cacheItem ophalen
		CacheItem item = null;
		try {
			item = getCacheItemByKey(key);
		} catch (SQLException e) {
			throw new RtmfGucException(
					String
							.format(
									"Er is een fout opgetreden bij het ophalen van het cacheItem met key: %s",
									key), e);
		}

		if (item == null) {
			throw new RtmfGucException(
					String
							.format(
									"De cache heeft de service niet kunnen achterhalen voor de key: %s",
									key));
		}
		// bepaal datum van verlopen cache
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, - StelselCatalogusCache.EXPIRATIONINDAYS );

		Date verlopenCache = c.getTime();

		if (item.isGm() && item.getGmUpdateddate().after(verlopenCache)
				&& item.isTmf()
				&& item.getTmfUpdateddate().after(verlopenCache)) {
			result = "Both";
		} else if (item.isGm() && item.getGmUpdateddate().after(verlopenCache)) {
			result = "GM";
		} else if (item.isTmf()
				&& item.getTmfUpdateddate().after(verlopenCache)) {
			result = "TMF";
		}
		logger.info(String.format("Servicecall voor key %s gaat naar %s.",key,result));
		if (result == null) {
			throw new RtmfGucException(
					String
							.format(
									"De cache heeft de service niet kunnen achterhalen voor de key: %s. Mogelijk is cache verouderd.",
									key));
		}

		return result;
	}
}
