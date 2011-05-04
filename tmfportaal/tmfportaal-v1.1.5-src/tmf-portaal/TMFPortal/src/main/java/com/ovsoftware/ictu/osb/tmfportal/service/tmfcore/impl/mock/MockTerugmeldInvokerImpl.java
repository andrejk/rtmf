package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock;

import java.util.GregorianCalendar;

import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TerugmeldInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingKern;

/**
 * MockTerugmeldInvokerImpl verzorgt het opslaan van een nieuwe Terugmelding.
 * LET OP: De TerugmeldingKern wordt vervangen in de melding-functie, evenals
 * het tijdstempelAanlevering!
 * 
 * @author ktinselboer
 */
public class MockTerugmeldInvokerImpl implements TerugmeldInvoker {
	
	private int tmfKenmerkTeller = 1;
	
	/**
	 * Voegt een nieuwe Terugmelding toe aan de lijst met terugmeldingen.
	 * Deze worden door de mock-implementatie alleen runtime bijgehouden.
	 * LET OP: De TerugmeldingKern in het meegegeven Terugmelding-object
	 * wordt door deze functie vervangen, evenals de tijdstempelAanlevering.
	 * 
	 * @param terugmelding Een nieuwe toe te voegen terugmelding.
	 */
	@Override
	public void melding(Terugmelding terugmelding){
		
		//creeer een nieuw tmfKenmerk -- ipv een echte submit naar TMF Core
		String tmfKenmerk = "TMF-" + tmfKenmerkTeller;
	
		//creeer eerst een Terugmelding-object
		TerugmeldingKern terugmeldingKern = new TerugmeldingKern(tmfKenmerk,
																 "0",
																 "ID-OVS",
																 "OVSoftware");
		
		
		//maak een GregorianCalendar met de huidige tijd aan
		GregorianCalendar gcNu = new GregorianCalendar();
		
		//vervang tijdstempelAanlevering
		terugmelding.setTijdstempelAanlevering(gcNu);
		
		//vervang TerugmeldingKern
		terugmelding.setTerugmeldingKern(terugmeldingKern);
				
		//creeer daarna een TerugmeldingDetails-object
		TerugmeldingDetails terugmeldingDetails = new TerugmeldingDetails(terugmeldingKern,
																		  "Werkvloer",
																		  gcNu,
																		  gcNu,
																		  "gemeld", 
																		  gcNu,
																		  "StatusToelichting");
		
		//voeg het nieuwe TerugmeldingCompleet-object toe aan de lijst
		MockOpvraagInvokerImpl.addTerugmeldingCompleet(terugmelding, terugmeldingDetails);
	}

}
