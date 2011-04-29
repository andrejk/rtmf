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
package nl.rotterdam.rtmf.guc.cache.entity;

import java.util.Date;

/**
 * Class om de cache gegevens in bij te houden.
 * 
 * TODO: kan dit een read-only object worden met alleen constructors? Maakt code
 * stabieler.
 * 
 * @author rweverwijk
 * 
 */
public class CacheItem {

	public static final String NO_STUFF_PATH = "[no stufpath]";

	private String key;
	private Date dateAdded;
	private boolean tmf = false;
	private boolean bevraagbaarTMF = false;
	private Date tmfUpdateddate;
	private boolean gm = false;
	private boolean bevraagbaarGM = false;
	private Date gmUpdateddate;
	private String stufpath;
	private String objectNaam;

	public String getStufpath() {
		return stufpath;
	}

	public void setStufpath(String stufpath) {
		if (stufpath == null) {
			this.stufpath = NO_STUFF_PATH;
		} else {
			this.stufpath = stufpath;
		}
	}

	/**
	 * Constructor met key
	 * 
	 * @param key
	 */
	public CacheItem(String key) {
		super();
		this.key = key;
	}

	/**
	 * Constructor om meteen alle waarden te zetten.
	 * 
	 * @param key
	 * @param dateAdded
	 * @param tmfUpdateddate
	 * @param gmUpdateddate
	 * @param tmf
	 * @param bevraagbaarTMF
	 * @param gm
	 * @param bevraagbaarGM
	 * @param stufpath
	 *            een Xpath expressie waar de actuele waarde uit een stuf
	 *            bericht te halen is
	 */
	public CacheItem(String key, Date dateAdded, Date tmfUpdateddate,
			Date gmUpdateddate, boolean tmf, boolean bevraagbaarTMF,
			boolean gm, boolean bevraagbaarGM, String stufpath,
			String objectnaam) {
		super();

		setKey(key);
		setDateAdded(dateAdded);
		setTmfUpdateddate(tmfUpdateddate);
		setGmUpdateddate(gmUpdateddate);
		this.tmf = tmf;
		this.gm = gm;
		this.bevraagbaarGM = bevraagbaarGM;
		this.bevraagbaarTMF = bevraagbaarTMF;
		setStufpath(stufpath);
		this.objectNaam = objectnaam;
	}

	/**
	 * Constructor voor het zetten van de waardes zonder parameter dateModified
	 * parameters
	 * 
	 * @param key
	 * @param dateAdded
	 * @param tmf
	 * @param bevraagbaarTMF
	 * @param gm
	 * @param bevraagbaarGM
	 * @param stufpath
	 * @param objectnaam
	 */
	public CacheItem(String key, Date dateAdded, boolean tmf,
			boolean bevraagbaarTMF, boolean gm, boolean bevraagbaarGM,
			String stufpath, String objectnaam) {
		this(key, dateAdded, (tmf ? dateAdded : null), (gm ? dateAdded : null),
				tmf, bevraagbaarTMF, gm, bevraagbaarGM, stufpath, objectnaam);
	}

	/**
	 * Constructor voor het zetten van bepaalde waarden. Deze constructor zal
	 * dateAdded doorgegven aan GMUpdatedDate en/of TMFUpdatedDate als het item
	 * is van GM en/of TMF.
	 * 
	 * @param key
	 * @param dateAdded
	 * @param tmf
	 * @param bevraagbaarTMF
	 * @param gm
	 * @param bevraagbaarGM
	 */
	public CacheItem(String key, Date dateAdded, boolean tmf,
			boolean bevraagbaarTMF, boolean gm, boolean bevraagbaarGM,
			String objectnaam) {
		this(key, dateAdded, (tmf ? dateAdded : null), (gm ? dateAdded : null),
				tmf, bevraagbaarTMF, gm, bevraagbaarGM, NO_STUFF_PATH,
				objectnaam);
	}

	/**
	 * default constructor
	 */
	public CacheItem() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheItem other = (CacheItem) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format(
						"CacheItem[key=%s; tmf=%b; gm=%b; bevraagbaarGM=%b; bevraagbaarTMF=%b, stufpath=%s, objectnaam=%s]",
						key, tmf, gm, bevraagbaarGM, bevraagbaarTMF, stufpath,
						objectNaam);
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		if (key == null) {
			throw new NullPointerException("Key mag geen null value hebben");
		}
		this.key = key;
	}

	/**
	 * @return the dateAdded
	 */
	public Date getDateAdded() {
		return dateAdded;
	}

	/**
	 * @param dateAdded
	 *            the dateAdded to set
	 */
	public void setDateAdded(Date dateAdded) {
		if (dateAdded == null) {
			throw new NullPointerException(
					"dateAdded mag geen null value hebben");
		}
		this.dateAdded = dateAdded;
	}

	/**
	 * @return the tmf
	 */
	public boolean isTmf() {
		return tmf;
	}

	/**
	 * @param tmf
	 *            the tmf to set
	 */
	public void setTmf(boolean tmf) {
		this.tmf = tmf;
	}

	/**
	 * @return the bevraagbaarTMF
	 */
	public boolean isBevraagbaarTMF() {
		return bevraagbaarTMF;
	}

	/**
	 * @param bevraagbaarTMF
	 *            the bevraagbaarTMF to set
	 */
	public void setBevraagbaarTMF(boolean bevraagbaarTMF) {
		this.bevraagbaarTMF = bevraagbaarTMF;
	}

	/**
	 * @return the gm
	 */
	public boolean isGm() {
		return gm;
	}

	/**
	 * @param gm
	 *            the gm to set
	 */
	public void setGm(boolean gm) {
		this.gm = gm;
	}

	/**
	 * @return the bevraagbaarGM
	 */
	public boolean isBevraagbaarGM() {
		return bevraagbaarGM;
	}

	/**
	 * @param bevraagbaarGM
	 *            the bevraagbaarGM to set
	 */
	public void setBevraagbaarGM(boolean bevraagbaarGM) {
		this.bevraagbaarGM = bevraagbaarGM;
	}

	/**
	 * Returns true if a stufpath is present.
	 */
	public boolean isStufPathPresent() {
		return !(stufpath == null || NO_STUFF_PATH.equals(stufpath));
	}

	/**
	 * @param objectname
	 *            the objectname to set
	 */
	public void setObjectNaam(String objectnaam) {
		this.objectNaam = objectnaam;
	}

	/**
	 * @return de naam van het object
	 */
	public String getObjectNaam() {
		return objectNaam;
	}

	/**
	 * 
	 * @param tmfUpdateddate
	 */
	public void setTmfUpdateddate(Date tmfUpdateddate) {
		this.tmfUpdateddate = tmfUpdateddate;
	}

	/**
	 * 
	 * @return
	 */
	public Date getTmfUpdateddate() {
		return tmfUpdateddate;
	}

	/**
	 * 
	 * @param gmUpdateddate
	 */
	public void setGmUpdateddate(Date gmUpdateddate) {
		this.gmUpdateddate = gmUpdateddate;
	}

	/**
	 * 
	 * @return
	 */
	public Date getGmUpdateddate() {
		return gmUpdateddate;
	}
}
