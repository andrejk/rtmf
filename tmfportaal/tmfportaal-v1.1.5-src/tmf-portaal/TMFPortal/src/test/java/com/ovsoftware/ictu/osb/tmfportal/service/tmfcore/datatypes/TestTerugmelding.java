package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

public class TestTerugmelding extends TestCase {
	
	/**
	 * Test de constructor en getters van Terugmelding.
	 */
	public void testConstructorAndGetters() {
		String CmeldingKenmerk = "meldingkenmerk";
		ContactGegevens Ccg = new ContactGegevens("naam",
									  			  "telefoon",
												  "email");
		GregorianCalendar CtijdstempelAanlevering = new GregorianCalendar();
		String CtagBR = "tagBR";
		String CtagObject = "tagObject";
		String CidObject = "idObject";
		String Ctoelichting = "toelichting";
		String Cstatus = "status";
		ArrayList<ObjectAttribuut> CoaLijst = new ArrayList<ObjectAttribuut>();
		TerugmeldingKern Ctk = new TerugmeldingKern("tmfkenmerk",
	  			   "berichtsoort",
	  			   "idorganisatie",
	  			   "naamorganisatie");
		ArrayList<Bijlage> CbLijst = new ArrayList<Bijlage>();
		
		Terugmelding t = new Terugmelding(CmeldingKenmerk,
										  Ccg,
										  CtijdstempelAanlevering,
										  CtagBR,
										  CtagObject,
										  CidObject,
										  Ctoelichting,
										  CoaLijst,
										  Ctk,
										  CbLijst,
										  Cstatus);
		
		String meldingKenmerk = t.getMeldingKenmerk();
		ContactGegevens cg = t.getContactGegevens();
		GregorianCalendar tijdstempelAanlevering = t.getTijdstempelAanlevering();
		String tagBR = t.getTagBR();
		String tagObject = t.getTagObject();
		String idObject = t.getIdObject();
		String toelichting = t.getToelichting();
		ArrayList<ObjectAttribuut> oaLijst = t.getObjectAttribuutLijst();
		TerugmeldingKern tk = t.getTerugmeldingKern();
		ArrayList<Bijlage> bLijst = t.getBijlageLijst();
		
		assertEquals("MeldingKenmerk niet gelijk", CmeldingKenmerk, meldingKenmerk);
		assertEquals("ContactGegevens niet gelijk", Ccg, cg);
		assertEquals("TijdstempelAanlevering niet gelijk", CtijdstempelAanlevering, tijdstempelAanlevering);
		assertEquals("TagBR niet gelijk", CtagBR, tagBR);
		assertEquals("TagObject niet gelijk", CtagObject, tagObject);
		assertEquals("IDObject niet gelijk", CidObject, idObject);
		assertEquals("Toelichting niet gelijk", Ctoelichting, toelichting);
		assertEquals("oaLijst niet gelijk", CoaLijst, oaLijst);
		assertEquals("TerugmeldingKern niet gelijk", Ctk, tk);
		assertEquals("bLijst niet gelijk", CbLijst, bLijst);
	}
	
	/**
	 * Test de setters en getters van Terugmelding.
	 */
	public void testSettersAndGetters() {
		String CmeldingKenmerk = "meldingkenmerk";
		ContactGegevens Ccg = new ContactGegevens("naam",
									  			  "telefoon",
												  "email");
		GregorianCalendar CtijdstempelAanlevering = new GregorianCalendar();
		String CtagBR = "tagBR";
		String CtagObject = "tagObject";
		String CidObject = "idObject";
		String Ctoelichting = "toelichting";
		ArrayList<ObjectAttribuut> CoaLijst = new ArrayList<ObjectAttribuut>();
		TerugmeldingKern Ctk = new TerugmeldingKern("tmfkenmerk",
	  			   "berichtsoort",
	  			   "idorganisatie",
	  			   "naamorganisatie");
		ArrayList<Bijlage> CbLijst = new ArrayList<Bijlage>();
		
		Terugmelding t = new Terugmelding();
		t.setMeldingKenmerk(CmeldingKenmerk);
		t.setContactGegevens(Ccg);
		t.setTijdstempelAanlevering(CtijdstempelAanlevering);
		t.setTagBR(CtagBR);
		t.setTagObject(CtagObject);
		t.setIdObject(CidObject);
		t.setToelichting(Ctoelichting);
		t.setObjectAttribuutLijst(CoaLijst);
		t.setTerugmeldingKern(Ctk);
		t.setBijlageLijst(CbLijst);
				
		String meldingKenmerk = t.getMeldingKenmerk();
		ContactGegevens cg = t.getContactGegevens();
		GregorianCalendar tijdstempelAanlevering = t.getTijdstempelAanlevering();
		String tagBR = t.getTagBR();
		String tagObject = t.getTagObject();
		String idObject = t.getIdObject();
		String toelichting = t.getToelichting();
		ArrayList<ObjectAttribuut> oaLijst = t.getObjectAttribuutLijst();
		TerugmeldingKern tk = t.getTerugmeldingKern();
		ArrayList<Bijlage> bLijst = t.getBijlageLijst();
		
		assertEquals("MeldingKenmerk niet gelijk", CmeldingKenmerk, meldingKenmerk);
		assertEquals("ContactGegevens niet gelijk", Ccg, cg);
		assertEquals("TijdstempelAanlevering niet gelijk", CtijdstempelAanlevering, tijdstempelAanlevering);
		assertEquals("TagBR niet gelijk", CtagBR, tagBR);
		assertEquals("TagObject niet gelijk", CtagObject, tagObject);
		assertEquals("IDObject niet gelijk", CidObject, idObject);
		assertEquals("Toelichting niet gelijk", Ctoelichting, toelichting);
		assertEquals("oaLijst niet gelijk", CoaLijst, oaLijst);
		assertEquals("TerugmeldingKern niet gelijk", Ctk, tk);
		assertEquals("bLijst niet gelijk", CbLijst, bLijst);
	}
}
