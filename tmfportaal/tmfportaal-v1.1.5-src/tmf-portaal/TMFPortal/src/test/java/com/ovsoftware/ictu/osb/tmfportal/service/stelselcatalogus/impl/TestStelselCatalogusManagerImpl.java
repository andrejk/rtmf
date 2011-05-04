package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl;

import junit.framework.TestCase;

import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.AttribuutInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.BasisRegistratieInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.ObjectInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.StelselCatalogusManagerImpl;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.mock.MockAttribuutInvokerImpl;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.springconfig.SpringBasisRegistratieInvokerImpl;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.springconfig.SpringObjectInvokerImpl;

public class TestStelselCatalogusManagerImpl extends TestCase{
	
	/**
	 * Test de setters en getters van StelselCatalogusManagerImpl.
	 */
	public void testGettersAndSetters() {
		//creeer manager
		StelselCatalogusManagerImpl sc = new StelselCatalogusManagerImpl();
		
		//creeer invokers
		AttribuutInvoker ai = new MockAttribuutInvokerImpl();
		BasisRegistratieInvoker bri = new SpringBasisRegistratieInvokerImpl();
		ObjectInvoker oi = new SpringObjectInvokerImpl();
		
		//set
		sc.setAttribuutInvoker(ai);
		sc.setBasisRegistratieInvoker(bri);
		sc.setObjectInvoker(oi);
		
		//get
		AttribuutInvoker ai2 = sc.getAttribuutInvoker();
		BasisRegistratieInvoker bri2 = sc.getBasisRegistratieInvoker();
		ObjectInvoker oi2 = sc.getObjectInvoker();
				
		//vergelijk
		assertEquals("AttribuutInvokers niet gelijk!", ai, ai2);
		assertEquals("BasisRegistratieInvokers niet gelijk!", bri, bri2);
		assertEquals("ObjectInvokers niet gelijk!", oi, oi2);
	}

}
