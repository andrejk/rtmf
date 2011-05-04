package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl;

import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.IntrekInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.OpvraagInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TerugmeldInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock.MockIntrekInvokerImpl;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock.MockOpvraagInvokerImpl;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock.MockTerugmeldInvokerImpl;

import junit.framework.TestCase;

public class TestTMFCoreManagerImpl extends TestCase {
	
	/**
	 * Test de setters en getters van TMFCoreManagerImpl.
	 */
	public void testGettersAndSetters() {
		//creeer manager
		TMFCoreManagerImpl tmf = new TMFCoreManagerImpl();
		
		//creeer invokers
		IntrekInvoker ii = new MockIntrekInvokerImpl();
		OpvraagInvoker oi = new MockOpvraagInvokerImpl();
		TerugmeldInvoker ti = new MockTerugmeldInvokerImpl();
		
		//set
		tmf.setIntrekInvoker(ii);
		tmf.setOpvraagInvoker(oi);
		tmf.setTerugmeldInvoker(ti);
		
		//get
		IntrekInvoker ii2 = tmf.getIntrekInvoker();
		OpvraagInvoker oi2 = tmf.getOpvraagInvoker();
		TerugmeldInvoker ti2 = tmf.getTerugmeldInvoker();
				
		//vergelijk
		assertEquals("IntrekInvokers niet gelijk!", ii, ii2);
		assertEquals("OpvraagInvokers niet gelijk!", oi, oi2);
		assertEquals("TerugmeldInvokers niet gelijk!", ti, ti2);		
	}

}
