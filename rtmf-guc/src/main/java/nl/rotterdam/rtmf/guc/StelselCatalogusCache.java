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
package nl.rotterdam.rtmf.guc;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

/**
 * Interface voor het vullen en raadplegen van de cache.
 * 
 * @author rweverwijk
 */
public interface StelselCatalogusCache {
	public static int EXPIRATIONINDAYS = 1;

	/**
	 * Voeg een key toe aan de cache (database) met een aantal attributen.
	 * 
	 * @param key Basisregistratie of Object tag.
	 * @param bron TerugmeldFaciliteit (TMF) of GegevensMagazijn (GM).
	 * @param bevraagbaar Geeft aan of de basisregistratie of het objecttype bevraagbaar is.
	 * @param objectnaam Is de naam van het betreffende object
	 * @throws RtmfGucException 
	 */
	void addKey(String key, String bron, Boolean bevraagbaar, String objectnaam) throws RtmfGucException;

	/**
	 * Voeg een key toe aan de cache (database) met een aantal attributen plus stufpath
	 * @param key Basisregistratie of Object tag
	 * @param bron TerugmeldFaciliteit (TMF) of GegevensMagazijn (GM)
	 * @param bevraagbaar Geeft aan of de basisregistratie of het objecttype bevraagbaar is
	 * 
	 * @param stufpath geeft aan waar actuele waarden in de stuf berichten zitten voor deze key/tag
	 * @param objectnaam Is de naam van het betreffende object
	 * @throws RtmfGucException 
	 */
	void addKey(String key, String bron, Boolean bevraagbaar, String stufpath, String objectnaam) throws RtmfGucException;

	/**
	 * Bepaald de services die moeten worden geroepen voor deze key (TMF, GM, of Both)
	 * 
	 * @param key
	 * @return
	 * @throws RtmfGucException
	 */
	String determineServiceCallsForKey(String key) throws RtmfGucException;

	/**
	 * Wandel door de payload heen, en zoek op basis van de basisregistratie tag
	 * naar welke service het bericht moet worden doorgestuurd. De tag die
	 * teruggegeven kan worden is:
	 * <ul>
	 * <li>Both
	 * <li>TMF
	 * <li>GM
	 * </ul>
	 * <p>
	 * 
	 * @param payloadAsString
	 *            Bericht welke ge-checked moet worden.
	 * @return Tag voor de service waarnaar het bericht moet worden doorgestuurd.
	 * @throws RtmfGucException
	 */
	String determineServiceCallsForMessage(String payloadAsString) throws RtmfGucException;

	/**
	 * Wandel door de payload heen, en zoek op basis van de basisregistratie tag
	 * naar welke service het bericht moet worden doorgestuurd. De tag die
	 * teruggegeven kan worden is:
	 * <ul>
	 * <li>TMF
	 * <li>GM
	 * </ul>
	 * <p>
	 * 
	 * @param payloadAsString
	 *            Bericht welke ge-checked moet worden.
	 * @return Tag voor de service waarnaar het bericht moet worden doorgestuurd.
	 * @throws RtmfGucException
	 */
	String determineServiceCallsForTerugmelding(String payloadAsString) throws RtmfGucException;

	/**
	 * Haal een stufpath op voor de gegeven key.
	 * 
	 * @param key Basisregistratie of Object tag.
	 * @return het stufpath
	 * @throws RtmfGucException (als er geen stufpath bekend is)
	 */
	String getStufpathForKey(String key) throws RtmfGucException;
	/**
	 * Haal de objectnaam op van een gegeven key
	 * 
	 * @param key Basisregistratie of Object tag 
	 * @return de naam van het object
	 * 
	 * @throws RtmfGucException 
	 */
	String getObjectNaam(String key) throws RtmfGucException;
}
