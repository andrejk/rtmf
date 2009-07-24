package com.ovsoftware.ictu.osb.tmfportal.service.common;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Bijlage;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ContactGegevens;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ObjectAttribuut;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingKern;

/**
 * Dit is geen echte testklasse. TerugmeldingFactory heeft een statische
 * functie welke een Terugmelding-object terugleverd met een meldingkenmerk
 * gebaseerd op de huidige tijd in milliseconden.
 * 
 * @author ktinselboer
 *
 */
public class TerugmeldingFactory {

	/**
	 * Functie welke een Terugmelding-object teruggeeft met een meldingkenmerk
	 * op basis van de huidige tijd in milliseconden. Wordt gebruikt in test-
	 * scenario's.
	 * 
	 * @return Een Terugmelding met een meldingkenmerk obv de huidige datumtijd
	 */
	public static Terugmelding getTerugmelding() {
		//maak een terugmelding kenmerk op basis van de huidige tijd
		String meldingkenmerk = "test-" + 
								Converter.converteerGCnaarStringNL(new GregorianCalendar()) + 
								"-" +
								(new GregorianCalendar()).getTimeInMillis();
		
		String CmeldingKenmerk = meldingkenmerk;
		ContactGegevens Ccg = new ContactGegevens("naam",
									  			  "telefoon",
												  "email");
		GregorianCalendar CtijdstempelAanlevering = new GregorianCalendar();
		String CtagBR = "BRA";
		String CtagObject = "WPL";
		String CidObject = "idObject";
		String Ctoelichting = "toelichting";
		String Cstatus = "status";
		ArrayList<ObjectAttribuut> CoaLijst = new ArrayList<ObjectAttribuut>();
		ObjectAttribuut oa = new ObjectAttribuut("11.70", "betwijfeldewaarde", "voorstel", null);
		CoaLijst.add(oa);
		TerugmeldingKern Ctk = new TerugmeldingKern(meldingkenmerk,
	  			   "berichtsoort",
	  			   "idorganisatie",
	  			   "naamorganisatie");
		ArrayList<Bijlage> CbLijst = new ArrayList<Bijlage>();
		CbLijst.add(new Bijlage("%PDFinhoud van bijlage.txt".getBytes(), "bijlage.pdf"));
		
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
		
		return t;
	}
	
}
