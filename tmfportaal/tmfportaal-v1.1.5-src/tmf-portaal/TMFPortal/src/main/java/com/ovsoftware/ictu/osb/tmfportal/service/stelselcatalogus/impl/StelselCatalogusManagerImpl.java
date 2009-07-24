package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl;

import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.AttribuutInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.BasisRegistratieInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.ObjectInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.StelselCatalogusManager;

/**
 * Dit is de basis implementatie van de StelselCatalogusManager. Deze manager
 * heeft drie invokers, namelijk:
 * - BasisRegistratieInvoker
 * - ObjectInvoker
 * - AttribuutInvoker
 * Deze invokers kunnen worden geset dmv de constructor of setters. Daarnaast
 * zijn er getters beschikbaar om ze uit te lezen.
 * 
 * @author OVSoftware
 *
 */
public class StelselCatalogusManagerImpl implements StelselCatalogusManager {
	private BasisRegistratieInvoker basisRegistratieInvoker;
	private ObjectInvoker objectInvoker;
	private AttribuutInvoker attribuutInvoker;
	
	/**
	 * Setter voor basisRegistratieInvoker.
	 * 
	 * @param basisRegistratieInvoker De nieuwe waarde voor basisRegistratieInvoker
	 */
	public void setBasisRegistratieInvoker(BasisRegistratieInvoker basisRegistratieInvoker) {
		this.basisRegistratieInvoker = basisRegistratieInvoker;
	}
	
	/**
	 * Getter voor basisRegistratieInvoker.
	 * 
	 * @return basisRegistratieInvoker
	 */
	public BasisRegistratieInvoker getBasisRegistratieInvoker() {
		return basisRegistratieInvoker;
	}
	
	/**
	 * Getter voor objectInvoker.
	 * 
	 * @return objectInvoker
	 */
	public ObjectInvoker getObjectInvoker() {
		return objectInvoker;
	}
	
	/**
	 * Setter voor objectInvoker.
	 * 
	 * @param objectInvoker De nieuwe waarde voor objectInvoker
	 */
	public void setObjectInvoker(ObjectInvoker objectInvoker) {
		this.objectInvoker = objectInvoker;
	}
	
	/**
	 * Getter voor attribuutInvoker.
	 * 
	 * @return attribuutInvoker
	 */
	public AttribuutInvoker getAttribuutInvoker() {
		return attribuutInvoker;
	}
	
	/**
	 * Setter voor attribuutInvoker.
	 * 
	 * @param attribuutInvoker De nieuwe waarde voor attribuutInvoker
	 */
	public void setAttribuutInvoker(AttribuutInvoker attribuutInvoker) {
		this.attribuutInvoker = attribuutInvoker;
	}
}
