package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus;

/**
 * Interface welke geimplementeerd dient te worden door een concrete
 * implementatie bijvoorbeeld StelselCatalogusManagerImpl.
 * 
 * @author OVSoftware
 *
 */
public interface StelselCatalogusManager {
	
	/**
	 * Setter voor basisRegistratieInvoker.
	 * 
	 * @param basisRegistratieInvoker De nieuwe waarde voor basisRegistratieInvoker
	 */
	void setBasisRegistratieInvoker(BasisRegistratieInvoker basisRegistratieInvoker);
	
	/**
	 * Getter voor basisRegistratieInvoker.
	 * 
	 * @return basisRegistratieInvoker
	 */
	BasisRegistratieInvoker getBasisRegistratieInvoker();
	
	/**
	 * Getter voor objectInvoker.
	 * 
	 * @return objectInvoker
	 */
	ObjectInvoker getObjectInvoker();
	
	/**
	 * Setter voor objectInvoker.
	 * 
	 * @param objectInvoker De nieuwe waarde voor objectInvoker
	 */
	void setObjectInvoker(ObjectInvoker objectInvoker);
	
	/**
	 * Getter voor attribuutInvoker.
	 * 
	 * @return attribuutInvoker
	 */
	AttribuutInvoker getAttribuutInvoker();
	
	/**
	 * Setter voor attribuutInvoker.
	 * 
	 * @param attribuutInvoker De nieuwe waarde voor attribuutInvoker
	 */
	void setAttribuutInvoker(AttribuutInvoker attribuutInvoker);
}
