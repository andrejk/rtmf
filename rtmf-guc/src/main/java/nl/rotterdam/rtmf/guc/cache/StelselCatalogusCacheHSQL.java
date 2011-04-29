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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import nl.rotterdam.rtmf.guc.cache.entity.CacheItem;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.apache.log4j.Logger;

/**
 * HSQL implementatie voor de cache.
 * 
 * Let erop dat deze class threadsafe moet zijn, meerdere mule threads spreken
 * deze class tegelijk aan.
 * 
 * @author rweverwijk
 */
public class StelselCatalogusCacheHSQL extends DetermineServiceCall {

	private Logger logger = Logger.getLogger(StelselCatalogusCacheHSQL.class
			.getName());
	private Connection conn;

	private static int numberOfClasses = 0;

	// een object om inserts en updates op te synchen, anders krijg je race
	// conditions
	// met het snel achter elkaar aanroepen van "addKey" voor dezelfde key
	// (ging regelmatig mis bij
	// BevraagObjectTypeListTest.testGetObjectTypeListBeideResources())
	private static Object insertUpdateLock = new Object();

	/**
	 * default constructor welke ervoor zorgt dat de database geinitialiseerd
	 * is.
	 * 
	 * @throws RtmfGucException
	 */
	public StelselCatalogusCacheHSQL() throws RtmfGucException {
		numberOfClasses++;
		logger.debug("Aantal StelselCalalogusCacheHSQL classes: "
				+ numberOfClasses);
		initDb();
	}

	/**
	 * Initialiseer de HSQL database. Als er nog geen tabel aangemaakt is wordt
	 * er een cache tabel aangemaakt waar de CacheItems in opgeslagen worden.
	 * 
	 * @throws RtmfGucException
	 */
	private void initDb() throws RtmfGucException {
		if (conn == null) {
			try {
				Class.forName("org.hsqldb.jdbcDriver").newInstance();
				String url = "jdbc:hsqldb:mem:cacheDB";
				conn = DriverManager.getConnection(url, "sa", "");
				conn.setAutoCommit(false);
				ResultSet rs = conn.getMetaData().getTables(null, null,
						"CACHE", null);
				if (!rs.next()) {
					Statement createTableStmt = conn.createStatement();
					createTableStmt
							.execute("CREATE TABLE CACHE(KEY VARCHAR(256) NOT NULL PRIMARY KEY,DATEADDED DATETIME, TMFDATEMODIFIED DATETIME, GMDATEMODIFIED DATETIME,TMF VARCHAR(1),BEVRAAGBAARTMF VARCHAR(1),GM VARCHAR(1),BEVRAAGBAARGM VARCHAR(1),STUFPATH VARCHAR(1024),OBJECTNAAM VARCHAR(1024))");
				}
			} catch (InstantiationException e) {
				throw new RtmfGucException(
						"De HSQLDS kon niet aangemaakt worden.", e);
			} catch (IllegalAccessException e) {
				throw new RtmfGucException(
						"De HSQLDS kon niet aangemaakt worden.", e);
			} catch (ClassNotFoundException e) {
				throw new RtmfGucException(
						"De HSQLDS kon niet aangemaakt worden.", e);
			} catch (SQLException e) {
				throw new RtmfGucException(
						"De database connectie kon niet aangemaakt worden.", e);
			}
		}
	}

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
		logger.debug("Voor toevoegen van key");
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

	/**
	 * Query de database om te kijken of een key aanwezig is in de database.
	 * 
	 * @param key
	 *            De key om te controleren.
	 * @return true als de key in de database staat, anders false.
	 * @throws SQLException
	 */
	private boolean containsKey(String key) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(String.format(
				"select count(*) from cache where KEY = '%s'", key));
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
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(String.format(
				"select * from cache where KEY = '%s'", key));
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
		return result;
	}

	/**
	 * Sla de cacheItem op, of update een bestaande. De cache wordt op gezien
	 * als een cache voor het opslaan van de plek waar iets wel aanwezig is. De
	 * gegevens worden dus ook alleen verwerkt als de velden op true staan. Met
	 * false waarden wordt NIETS gedaan (TODO: waarom niet?).
	 * 
	 * Gebruik een cleanup script welke op basis van de tijd van laatste
	 * wijziging oude elementen opruimt.
	 * 
	 * @param item
	 * @throws SQLException
	 */
	private void save(CacheItem item) throws SQLException {
		if (item == null) {
			throw new NullPointerException(
					"Een null item kan niet worden gesaved in de cache");
		}
		// voorkom race condities met insert/update
		// TODO: moet in een database transaction
		synchronized (insertUpdateLock) {
			Statement stmt = conn.createStatement();

			if (containsKey(item.getKey())) {
				StringBuffer sb = new StringBuffer();
				sb.append("update cache set ");
				// zet 
				sb.append(item.isGm() ? "GM = 'y', GMDATEMODIFIED = NOW " : "");
				sb.append(item.isTmf() ? "TMF = 'y', TMFDATEMODIFIED = NOW " : "");
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
				StringBuffer sb = new StringBuffer();
				sb.append("insert into cache (key");
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
				sb.append(",NOW");
				sb.append(item.isGm() ? ",NOW":"");
				sb.append(item.isTmf() ? ",NOW":"");
				sb.append(",'").append(item.getStufpath()).append("'");
				sb.append(",'").append(item.getObjectNaam()).append("'");
				sb.append(")");
				logger.info("Insert statement : " + sb.toString());
				stmt.execute(sb.toString());
			}
		} // end synchronized
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
