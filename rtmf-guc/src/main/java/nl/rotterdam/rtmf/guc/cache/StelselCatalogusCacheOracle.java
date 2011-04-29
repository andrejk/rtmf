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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import nl.rotterdam.rtmf.guc.cache.entity.CacheItem;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;

/**
 * Oracle implementatie voor de cache.
 * 
 * Let erop dat deze class threadsafe moet zijn, meerdere mule threads spreken
 * deze class tegelijk aan.
 * 
 * @author rweverwijk
 */
public class StelselCatalogusCacheOracle extends DetermineServiceCall {

	private Logger logger = Logger.getLogger(StelselCatalogusCacheOracle.class
			.getName());
	private Connection conn = null;
	private OracleDataSource dataSource = null;
	/**
	 * Het formaat hiervan gaat in delen van een dag.
	 * Voor de leesbaarheid is gekozen voor 24ste om in uren te geven
	 * 1 uur: 1/24, een halve dag: 12/24, een dag 1, 2 dagen 2/1 etc.
	 */
	private String cacheCleanupInterval = "12/24";

	public OracleDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(OracleDataSource dataSource) {
		this.dataSource = dataSource;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			String message = "Geen connection kunnen maken " + e.getMessage();
			logger.error(message);
			throw new RtmfGucException(message);
		}
	}
	
	public void setCacheCleanupInterval(String cacheCleanupInterval) {
		this.cacheCleanupInterval = cacheCleanupInterval;
	}
	
	private Connection getConnection() throws SQLException {
		if (conn.isClosed()) {
			logger.error("Connection closed trying to reopen");
			try {
				conn = dataSource.getConnection();
			} catch (SQLException e) {
				String message = "Geen connection kunnen maken " + e.getMessage();
				logger.error(message);
				throw new RtmfGucException(message);
			}
		}
		return conn;
	}

	// een object om inserts en updates op te synchen, anders krijg je race
	// conditions
	// met het snel achter elkaar aanroepen van "addKey" voor dezelfde key
	// (ging regelmatig mis bij
	// BevraagObjectTypeListTest.testGetObjectTypeListBeideResources())
	private static Object insertUpdateLock = new Object();

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.rotterdam.rtmf.guc.StelselCatalogusCache#addKey(java.lang.String,
	 * java.lang.String, java.lang.Boolean)
	 */
	public void addKey(String key, String bron, Boolean bevraagbaar,
			String objectnaam) throws RtmfGucException {
		this.addKey(key, bron, bevraagbaar, null, objectnaam);
	}

	/**
	 * Query de database om te kijken of een key aanwezig is in de database.
	 * 
	 * @param key
	 *            De key om te controleren.
	 * @return true als de key in de database staat, anders false.
	 * @throws SQLException
	 */
	private boolean containsKey(String key) throws SQLException {
		Statement st = getConnection().createStatement();
		ResultSet rs = st.executeQuery(String.format(
				"select count(*) from RTMF_CACHE where KEY = '%s'", key));
		rs.next();
		int aantal = rs.getInt(1);
		if (aantal > 1) {
			throw new RtmfGucException(
					String
							.format(
									"Meer dan 1 record gevonden voor key: %s, gevonden aantal: %s",
									key, aantal));
		}
		boolean isKeyInDatabase = (aantal != 0);
		logger.debug(String.format("Key '%s' in cache: %s", key,
				isKeyInDatabase));
		st.close();
		return isKeyInDatabase;
	}

	/**
	 * Haal een CacheItem uit de database op basis van zijn key
	 * 
	 * @param key
	 *            de key waarop gezocht moet worden.
	 * @return De gevonden CacheItem of null als er geen CacheItem gevonden is
	 *         met de key
	 * @throws SQLException
	 */
	protected CacheItem getCacheItemByKey(String key) throws SQLException {
		CacheItem result = null;
		Statement stmt = getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(String.format(
				"select * from RTMF_CACHE where KEY = '%s'", key));
		int numberOfResults = 0;
		while (rs.next()) {
			numberOfResults++;
			result = transformToCacheItem(rs);
		}
		if (numberOfResults > 1) {
			throw new RtmfGucException(
					String
							.format(
									"Meer dan 1 record gevonden voor key: %s, gevonden aantal: %s",
									key, numberOfResults));
		}
		stmt.close();
		return result;
	}

	/**
	 * Sla de cacheItem op, of update een bestaande. De cache wordt op gezien
	 * als een cache voor het opslaan van de plek waar iets wel aanwezig is. De
	 * gegevens worden dus ook alleen verwerkt als de velden op true staan. 
	 * 
	 * Gebruik een cleanup script welke op basis van de tijd van laatste
	 * wijziging oude elementen opruimt.
	 * 
	 * @param item
	 * @throws SQLException
	 */
	private void save(CacheItem item) throws SQLException {
		cleanupItems();
		logger.debug("Start save");
		if (item == null) {
			throw new NullPointerException(
					"Een null item kan niet worden gesaved in de cache");
		}
		// voorkom race condities met insert/update
		synchronized (insertUpdateLock) {
			Statement stmt = getConnection().createStatement();

			if (containsKey(item.getKey())) {
				logger.debug("Start containsKey");

				StringBuffer sb = new StringBuffer();
				sb.append("update RTMF_CACHE set ");
				sb.append(item.isGm() ? "GM = 'y', GMDATEMODIFIED = SYSDATE" : "");
				sb.append(item.isTmf() ? "TMF = 'y', TMFDATEMODIFIED = SYSDATE" : "");
				sb
						.append(item.isBevraagbaarGM() ? ", BEVRAAGBAARGM = 'y'"
								: "");
				sb.append(item.isBevraagbaarTMF() ? ", BEVRAAGBAARTMF = 'y'"
						: "");
				// alleen het stufpath updaten als er een nieuwe is
				if (item.isStufPathPresent()) {
					sb.append(", STUFPATH = '").append(item.getStufpath())
							.append("'");
				}
				sb.append(", OBJECTNAAM = '" + item.getObjectNaam() + "'");
				sb.append(String.format(" where key = '%s'", item.getKey()));
				logger.info("Update statement : " + sb.toString());
				stmt.executeUpdate(sb.toString());
			} else {
				logger.debug("Start else");
				StringBuffer sb = new StringBuffer();
				sb.append("insert into RTMF_CACHE (key");
				sb.append(item.isGm() ? ",GM" : "");
				sb.append(item.isTmf() ? ",TMF" : "");
				sb.append(item.isBevraagbaarGM() ? ",BEVRAAGBAARGM" : "");
				sb.append(item.isBevraagbaarTMF() ? ",BEVRAAGBAARTMF" : "");
				sb.append(",DATEADDED");
				sb.append(item.isGm() ? ",GMDATEMODIFIED":"");
				sb.append(item.isTmf() ? ",TMFDATEMODIFIED":"");
				sb.append(",STUFPATH");
				sb.append(",OBJECTNAAM");
				sb.append(") values (");
				sb.append(String.format("'%s'", item.getKey()));
				sb.append(item.isGm() ? String.format(",'%s'",
						transformToString(item.isGm())) : "");
				sb.append(item.isTmf() ? String.format(",'%s'",
						transformToString(item.isTmf())) : "");
				sb.append(item.isBevraagbaarGM() ? String.format(",'%s'",
						transformToString(item.isBevraagbaarGM())) : "");
				sb.append(item.isBevraagbaarTMF() ? String.format(",'%s'",
						transformToString(item.isBevraagbaarTMF())) : "");
				sb.append(",SYSDATE");
				sb.append(item.isGm() ? ",SYSDATE":"");
				sb.append(item.isTmf() ? ",SYSDATE":"");
				sb.append(",'").append(item.getStufpath() + "'");
				sb.append(",'").append(item.getObjectNaam());
				sb.append("')");
				logger.debug("statement: " + sb.toString());
				logger.info("Insert statement : " + sb.toString());
				stmt.execute(sb.toString());
			}
			stmt.close();
		} // end synchronized
	}
	
	/**
	 * Deze methode kan gebruikt worden om keys (basisregistraties, objecten en attributen)
	 * die al een bepaalde tijd niet gebruikt zijn op nee te zetten in de database
	 * De gedachte hierachter is het volgende:
	 * Als een bronsysteem (landelijke en Rotterdam) een poos GBA gegevens teruggeeft, maar op een
	 * bepaald moment bieden ze die gegevens niet meer aan. Zonder deze functie zou de databse echter
	 * op Y blijven staan en gaan we er vanuit dat het systeem het nog steeds aanbied.
	 * Omdat we dat willen oplossen...
	 */
	private void cleanupItems() throws SQLException{
		long currentTimeMillis = System.currentTimeMillis();
		logger.debug("start cleanup old key elements");
		
		String updateLandelijk = String.format("update RTMF_CACHE set TMF = 'n', BEVRAAGBAARTMF = 'n', TMFDATEMODIFIED = null where TMFDATEMODIFIED < SYSDATE - (%s)", cacheCleanupInterval);
		String updateRotterdam = String.format("update RTMF_CACHE set GM = 'n', BEVRAAGBAARGM = 'n', GMDATEMODIFIED = null where GMDATEMODIFIED < SYSDATE - (%s)", cacheCleanupInterval);
		String deleteOldRows = "delete from RTMF_CACHE where nvl(TMF, 'n') = 'n' and nvl(GM, 'n') = 'n'";
		
		Statement stmt = getConnection().createStatement();
		stmt.execute(updateLandelijk);
		stmt.execute(updateRotterdam);
		stmt.execute(deleteOldRows);
		stmt.close();
		
		logger.debug(String.format("cleanup finished in %d millis", System.currentTimeMillis() - currentTimeMillis));
	}

	/**
	 * simpele transformatie om een boolean te transfomeren naar een formaat
	 * welke Oracle aan kan.
	 * 
	 * @param input
	 * @return y : n
	 */
	private Object transformToString(boolean input) {
		return input ? "y" : "n";
	}

	/**
	 * Simpele tranformatie welke bij een y of Y true returneerd anders false
	 * 
	 * @param input
	 * @return
	 */
	private boolean transformToBoolean(String input) {
		return "y".equalsIgnoreCase(input);
	}

	/**
	 * Transform een ResultSet in een CacheItem. We gaan er hier vanuit dat de
	 * ResultSet op de juiste plek staat. We gaan zelf niet door de ResultSet
	 * lopen. We verwerken alleen het huidige element.
	 * 
	 * @param rs
	 *            ResultSet welke op de huidige plek staat.
	 * @return een CacheItem gevult met de elementen uit de ResultSet.
	 * @throws SQLException
	 */
	private CacheItem transformToCacheItem(ResultSet rs) throws SQLException {
		String key = rs.getString("KEY");
		boolean isTmf = transformToBoolean(rs.getString("TMF"));
		boolean isGm = transformToBoolean(rs.getString("GM"));
		boolean isBevraagbaarTmf = transformToBoolean(rs
				.getString("BEVRAAGBAARTMF"));
		boolean isBevraagbaarGm = transformToBoolean(rs
				.getString("BEVRAAGBAARGM"));
		Date dateAdded = rs.getDate("DATEADDED");
		Date dateTMFModified = rs.getDate("TMFDATEMODIFIED");
		Date dateGMModified = rs.getDate("GMDATEMODIFIED");
		String stufpath = rs.getString("STUFPATH");
		String objectnaam = rs.getString("OBJECTNAAM");
		CacheItem result = new CacheItem(key, dateAdded, dateTMFModified, dateGMModified, isTmf,
				isBevraagbaarTmf, isGm, isBevraagbaarGm, stufpath, objectnaam);
		return result;
	}

	@Override
	public String getStufpathForKey(String key) {
		CacheItem item;
		try {
			item = getCacheItemByKey(key);
		} catch (SQLException cause) {
			throw new RtmfGucException(String.format(
					"Kan de CacheItem voor key '%s' niet ophalen.", key), cause);
		}
		// dit is misschien te sterk, een exception gooien, maar eerst even
		// aanzien in test fase
		if (item == null) {
			throw new RtmfGucException(String.format(
					"Kan geen cache item vinden voor key: %s", key));
		}
		String stufpath = item.getStufpath();
		// checken of er een stuf path is... misschien niet de juiste plek hier,
		// het kan waarschijnlijk gewoon
		// voorkomen dat een tag geen stufpath heeft (bv. een TMF tag): wie
		// heeft verantwoording dit te checken?
		if (CacheItem.NO_STUFF_PATH.equals(stufpath)) {
			throw new RtmfGucException(String.format(
					"Kan geen stufpath vinden voor key: %s, item: %s", key,
					item));
		}
		return stufpath;
	}

	@Override
	public void addKey(String key, String bron, Boolean bevraagbaar,
			String stufpath, String objectnaam) throws RtmfGucException {

		boolean isTMF = "TMF".equals(bron);
		boolean isGM = "GM".equals(bron);
		boolean isBevraagbaar = (bevraagbaar == null ? false : bevraagbaar);
		Date dateAdded = new Date();

		// waarom een trim op de key?
		CacheItem item;
		if (stufpath != null) {
			item = new CacheItem(key.trim(), dateAdded, isTMF, isBevraagbaar,
					isGM, isBevraagbaar, stufpath, objectnaam);
		} else {
			item = new CacheItem(key.trim(), dateAdded, isTMF, isBevraagbaar,
					isGM, isBevraagbaar, objectnaam);
		}
		logger.debug("Voor toevoegen van key t");
		try {
			save(item);
		} catch (SQLException e) {
			throw new RtmfGucException(
					String
							.format(
									"Er is een fout opgetreden bij het opslaan van de key: %s bron: %s bevraagbaar: %b",
									key, bron, bevraagbaar), e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.rotterdam.rtmf.guc.StelselCatalogusCache#getObjectNaam(java.lang.String
	 * )
	 */
	@Override
	public String getObjectNaam(String key) throws RtmfGucException {
		CacheItem item;
		try {
			item = getCacheItemByKey(key);
		} catch (SQLException cause) {
			throw new RtmfGucException(String.format(
					"Kan de CacheItem voor key '%s' niet ophalen.", key), cause);
		}
		// dit is misschien te sterk, een exception gooien, maar eerst even
		// aanzien in test fase
		if (item == null) {
			throw new RtmfGucException(String.format(
					"Kan geen cache item vinden voor key: %s", key));
		}
		String objectNaam = item.getObjectNaam();
		// checken of er een stuf path is... misschien niet de juiste plek hier,
		// het kan waarschijnlijk gewoon
		// voorkomen dat een tag geen stufpath heeft (bv. een TMF tag): wie
		// heeft verantwoording dit te checken?
		if (objectNaam == null) {
			throw new RtmfGucException(String.format(
					"Kan geen objectnaam, vinden voor key: %s, item: %s", key,
					item));
		}
		return objectNaam;
	}

}
