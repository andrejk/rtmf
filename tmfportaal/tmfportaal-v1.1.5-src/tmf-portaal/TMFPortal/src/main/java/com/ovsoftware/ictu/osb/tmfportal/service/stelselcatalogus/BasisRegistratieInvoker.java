package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus;

import java.util.ArrayList;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

/**
 * Interface welke geimplementeerd dient te worden door een concrete
 * implementatie bijvoorbeeld SpringBasisRegistratieInvokerImpl.
 * 
 * @author OVSoftware
 *
 */
public interface BasisRegistratieInvoker {
	/**
	 * Haalt de lijst met BasisRegistratie-objecten op.
	 * 
	 * @return Een lijst met BasisRegistratie-objecten
	 * @throws InvokerException als er in de invoker fouten optreden
	 */
	ArrayList<BasisRegistratie> getBasisRegistraties() throws InvokerException;
}
