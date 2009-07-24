package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.springconfig;

import java.util.ArrayList;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

import junit.framework.TestCase;

public class TestSpringObjectInvokerImpl extends TestCase {
	
	/**
	 * Test de getObjectTypen-functie van SpringObjectInvokerImpl voor het Kadaster (BRK)
	 */
	public void testGetObjectTypenKadaster() {
		
		BasisRegistratie br = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		
		SpringObjectInvokerImpl oi = new SpringObjectInvokerImpl();
		
		ArrayList<BRObject> broLijst = null;
		try {
			broLijst = oi.getObjectTypen(br);
		} catch (InvokerException e) {
			fail(e.getStackTrace().toString());
		}
		
		assertEquals(broLijst.get(0).getTag(), "OZ");
		assertEquals(broLijst.get(1).getTag(), "ZR");
		assertEquals(broLijst.get(2).getTag(), "HYP");
		assertEquals(broLijst.get(3).getTag(), "BS");
		assertEquals(broLijst.get(4).getTag(), "PER");
		assertEquals(broLijst.get(5).getTag(), "ST");

	}
}
