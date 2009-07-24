package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus;

import java.util.ArrayList;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

/**
 * Interface welke geimplementeerd dient te worden door een concrete
 * implementatie bijvoorbeeld MockObjectInvokerImp.
 * 
 * @author OVSoftware
 *
 */
public interface ObjectInvoker {
	
	/**
	 * Haalt een lijst van objecten op voor een gegeven basisregistratie.
	 * 
	 * @param basisRegistratie De basisregistratie waarvoor de lijst met objecten moet worden opgehaald
	 * @return De lijst met objecten voor de gegeven basisregistratie
	 * @throws InvokerException als er in de invoker fouten optreden
	 */
	ArrayList<BRObject> getObjectTypen(BasisRegistratie basisRegistratie) throws InvokerException;
}
