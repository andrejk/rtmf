package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectAttribuut;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectData;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

public class TestMockStelselCatalogus extends TestCase {
	private ApplicationContext ac;

	/**
	 * Lees de StelselCatalogus in.
	 */
	public void setUp() throws IOException {
		ac = new FileSystemXmlApplicationContext("src/test/resources/springapp-mock-servlet.xml");
	}
	
	/**
	 * Test of de basisregistraties correct zijn ingeladen.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void testMockBasisRegistratie() throws ServletException, IOException {
		StelselCatalogusManager sc = (StelselCatalogusManager) ac.getBean("stelselCatalogus");
		
		Collection<BasisRegistratie> brCollection = null;
		try {
			brCollection = sc.getBasisRegistratieInvoker().getBasisRegistraties();
		} catch (InvokerException e) {
			fail(e.getStackTrace().toString());
		}
		assertNotNull("Basisregistraties is leeg", brCollection);
		assertEquals("Aantal klopt niet", 4, brCollection.size());
	}

	/**
	 * Test of de Objecten correct zijn ingeladen.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void testMockObjecten() throws ServletException, IOException {
		StelselCatalogusManager sc = (StelselCatalogusManager) ac.getBean("stelselCatalogus");
		BasisRegistratie br = new BasisRegistratie("BRA", "");
		Collection<BRObject> objectCollection = null;
		try {
			objectCollection = sc.getObjectInvoker().getObjectTypen(br);
		} catch (InvokerException e) {
			fail(e.getStackTrace().toString());
		}
		assertNotNull("Objecten voor BRA is leeg", objectCollection);
		assertEquals("Aantal klopt niet", 3, objectCollection.size());
	}

	/**
	 * Test of de Attributen correct zijn ingeladen.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void testMockAttributen() throws ServletException, IOException {
		StelselCatalogusManager sc = (StelselCatalogusManager) ac.getBean("stelselCatalogus");
		
		BasisRegistratie br = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		
		BRObject brObj = new BRObject("PER", "Persoon");
		
		ArrayList<BRObjectAttribuut> broaLijst = null;
		try {
			broaLijst = sc.getAttribuutInvoker().getBasisAttributen(br, brObj).getBroaLijst();
		} catch (InvokerException ie) {
			assertNull("InvokerException in testMockAttributen", ie);
		}
		assertNotNull("Attributen voor BRK - PER is leeg", broaLijst);
		assertEquals("Aantal klopt niet", 2, broaLijst.size());
	}

	/**
	 * Test of de waardes van de attributen correct zijn ingeladen.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void testMockAttribuutWaarden() throws ServletException, IOException {
		StelselCatalogusManager sc = (StelselCatalogusManager) ac.getBean("stelselCatalogus");
		
		BasisRegistratie br = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		
		BRObject brObj = new BRObject("PER", "Persoon");
		
		String objId = "123";
		
		BRObjectData brod = null;
		try {
			brod = sc.getAttribuutInvoker().getBasisAttributenValues(br, brObj, objId);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testMockAttribuutWaarden", ie);
		}
		assertNotNull("Attributen voor BRK - PER - 123 is leeg", brod);
		assertEquals("Aantal klopt niet", 2, brod.getBroaLijst().size());
		assertEquals("Voornaam klopt niet", "Peter", brod.getBroaLijst().get(0).getWaarde());
	}
	
	/**
	 * Test of een BRK-OZ object inderdaad niet bevraagbaar is.
	 */
	public void testNietBevraagbaar() {
		StelselCatalogusManager sc = (StelselCatalogusManager) ac.getBean("stelselCatalogus");
		
		BasisRegistratie br = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		
		BRObject brObj = new BRObject("OZ", "");
		
		BRObjectData brod = null;
		try {
			brod = sc.getAttribuutInvoker().getBasisAttributen(br, brObj);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testNietBevraagbaar", ie);
		}
		
		String foutmelding = "Wel bevraagbaar, terwijl " + br.getTag() + "-" + brObj.getTag() + " dat niet zou moeten zijn!";
		assertTrue(foutmelding, !brod.isBevraagbaar());		
	}
	
	/**
	 * Test dat een BRK-PER object inderdaad bevraagbaar is.
	 */
	public void testWelBevraagbaar() {
		StelselCatalogusManager sc = (StelselCatalogusManager) ac.getBean("stelselCatalogus");
		
		BasisRegistratie br = new BasisRegistratie("BRK", "Basisregistratie Kadaster");
		
		BRObject brObj = new BRObject("PER", "Persoon");
		
		BRObjectData brod = null;
		try {
			brod = sc.getAttribuutInvoker().getBasisAttributen(br, brObj);
		} catch (InvokerException ie) {
			assertNull("InvokerException in testWelBevraagbaar", ie);
		}
		
		String foutmelding = "Niet bevraagbaar, terwijl " + br.getTag() + "-" + brObj.getTag() + " dat wel zou moeten zijn!";
		assertTrue(foutmelding, brod.isBevraagbaar());		
	}

}
