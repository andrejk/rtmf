package com.ovsoftware.ictu.osb.tmfportal.service.common;

/**
 * Deze klasse wordt gebruikt in Settings.
 * 
 * @author ktinselboer
 *
 */
public class KeyValuePair {
	
	private String key;
	private String value;
	
	/**
	 * Constructor voor een KVP.
	 * 
	 * @param key Sleutel uit het settings-bestand
	 * @param value Waarde uit het settings-bestand
	 */
	public KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Retourneert de sleutel.
	 * 
	 * @return De sleutel
	 */
	public String getKey() {
		return this.key;
	}
	
	/**
	 * Retourneert de waarde.
	 * 
	 * @return De waarde
	 */
	public String getValue() {
		return this.value;
	}
}
