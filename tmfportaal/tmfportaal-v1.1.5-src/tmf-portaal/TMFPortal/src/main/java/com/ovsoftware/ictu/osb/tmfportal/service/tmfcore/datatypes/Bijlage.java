package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import java.io.Serializable;

/**
 * Een lijst met Bijlage-objecten wordt gebruikt in een nieuw
 * Terugmelding-object. Een Bijlage bestaat uit een bestandsnaam
 * en een byte array.
 * 
 * @author ktinselboer
 *
 */
public class Bijlage implements Serializable{
	
	private static final long serialVersionUID = 3163004849987069825L;
	private byte[] inhoud;
	private String bestandsnaam;
	
	/**
	 * Constructor voor Bijlage.
	 * 
	 * @param inhoud Een byte-array met de inhoud van een bestand
	 * @param bestandsnaam De bestandsnaam van de bijlage
	 */
	public Bijlage(byte[] inhoud, String bestandsnaam) {
		super();
		this.inhoud = inhoud.clone();
		this.bestandsnaam = bestandsnaam;
	}

	/**
	 * Getter voor inhoud.
	 * 
	 * @return Een byte-array met de inhoud van de bijlage
	 */
	public byte[] getInhoud() {
		return inhoud.clone();
	}

	/**
	 * Getter voor bestandsnaam.
	 * 
	 * @return De bestandsnaam voor deze bijlage
	 */
	public String getBestandsnaam() {
		return bestandsnaam;
	}

	/**
	 * Setter voor inhoud.
	 * 
	 * @param inhoud De nieuwe waarde voor inhoud
	 */
	public void setInhoud(byte[] inhoud) {
		this.inhoud = inhoud.clone();
	}

	/**
	 * Setter voor bestandsnaam.
	 * 
	 * @param bestandsnaam De nieuwe waarde voor bestandsnaam
	 */
	public void setBestandsnaam(String bestandsnaam) {
		this.bestandsnaam = bestandsnaam;
	}
}
