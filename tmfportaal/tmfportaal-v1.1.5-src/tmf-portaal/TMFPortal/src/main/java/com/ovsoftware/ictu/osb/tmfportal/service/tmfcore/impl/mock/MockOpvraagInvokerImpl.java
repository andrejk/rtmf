package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.common.Statussen;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.OpvraagInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.mock.TerugmeldingCompleet;

/**
 * MockOpvraagInvokerImpl leest een lijst van terugmeldingen uit een resource-
 * bestand (xml). De bijgehouden terugmeldingen kunnen runtime worden gewijzigd
 * (ingetrokken) en er kunnen nieuwe terugmeldingen aan de lijst worden toegevoegd.
 * Deze worden niet permanent opgeslagen. Na het beeindigen van de sessie zijn ook
 * de aangemaakte of ingetrokken meldingen dus weer verdwenen.
 * 
 * @author ktinselboer
 *
 */
public class MockOpvraagInvokerImpl implements OpvraagInvoker {

	private static ArrayList<TerugmeldingCompleet> terugmeldingCompleetLijst = new ArrayList<TerugmeldingCompleet>();

	private static Logger logger = Logger.getLogger(MockOpvraagInvokerImpl.class);
	
	/**
	 * Lege constructor ivm Spring beans.
	 */
	public MockOpvraagInvokerImpl(){}
	
	/**
	 * Constructor voor een MockOpvraagManagerImpl. De constructor laad een lijst met
	 * TerugmeldingCompleet-objecten uit een xml bestand.
	 * 
	 * @param strTerugmeldingCompleet De bestandsnaam van het xml-bestand
	 */
	@SuppressWarnings("unchecked")
	public MockOpvraagInvokerImpl(String strTerugmeldingCompleet) {
		//laad lijst met TerugmeldingCompleet's uit xml bestand
		ApplicationContext acTerugmeldingCompleet = new ClassPathXmlApplicationContext(strTerugmeldingCompleet);
		Map<String, TerugmeldingCompleet> mapTerugmeldingCompleet = acTerugmeldingCompleet.getBeansOfType(TerugmeldingCompleet.class);
		MockOpvraagInvokerImpl.terugmeldingCompleetLijst.addAll(mapTerugmeldingCompleet.values());
	}
	
	/**
	 * Dit is de Mock-implementatie van de OpvraagInvoker.
	 * 
	 * Deze functie wordt gebruikt in de view om een lijst met eerder gedane terugmeldingen
	 * op te vragen. De functie roept een webservice op de OSB Gateway aan, waarna
	 * de OSB Gateway een webservice aanroept op TMF Core. Zie ook use case UC046S.
	 * 
	 * @param status			De status waarop gefilterd moet worden, mag null zijn.
	 * @param vanDatum			Het begin van het tijdbereik waarop gefilterd moet worden, mag null zijn.
	 * @param totDatum			Het eind van het tijdbereik waarop gefilterd moet worden, mag null zijn.
	 * @param meldingKenmerk	Het eigen kenmerk van de organisatie die de melding plaatste waarop gefilterd moet worden, mag null zijn.
	 * @param tmfKenmerk		Het door TMF Core uitgegeven kenmerk waarop gefilterd moet worden, mag null zijn.
	 * @return 					De lijst met terugmeldingen die voldoet aan de opgegeven parameters.
	 */
	@Override
	public ArrayList<Terugmelding> ophalenMeldingen(String status,
													GregorianCalendar vanDatum,
													GregorianCalendar totDatum,
													String meldingKenmerk,
													String tmfKenmerk) {
		//creeer de collectie
		ArrayList<Terugmelding> terugmeldingLijst = new ArrayList<Terugmelding>();
		for (TerugmeldingCompleet terugmeldingCompleet : terugmeldingCompleetLijst) {
			terugmeldingLijst.add(terugmeldingCompleet.getTerugmelding());
		}
		
		//filter de collectie
		ArrayList<Terugmelding> gefilterdeLijst = filterTerugmeldingLijst(terugmeldingLijst, status, vanDatum, totDatum, meldingKenmerk, tmfKenmerk);
		
		//return de lijst
		return gefilterdeLijst;		
	}
		
	/**
	 * Dit is de Mock-implementatie van de OpvraagInvoker.
	 * 
	 * Deze functie wordt gebruikt in de view om de details van een enkele melding
	 * op te vragen. De functie roept een webservice op de OSB Gateway aan, waarna
	 * de OSB Gateway een webservice aanroept op TMF Core. Zie ook use case UC045S.
	 * 
	 * @param meldingKenmerk	Het eigen kenmerk van de organisatie die de melding plaatste waarop gefilterd moet worden.
	 * @return 					De details van de gevonden melding of null indien geen melding gevonden kon worden.
	 */
	@Override
	public TerugmeldingDetails ophalenMeldingDetails(String meldingKenmerk) {
		//initialiseer het resultaat
		TerugmeldingDetails terugmeldingDetails = null;
		
		//doorloop de lijst om te kijken of het gewenste TerugmeldingDetails-object er in zit
		for (TerugmeldingCompleet terugmeldingCompleetItem : terugmeldingCompleetLijst) {
			String gevraagd = meldingKenmerk;
			String gevonden = terugmeldingCompleetItem.getTerugmelding().getMeldingKenmerk();
			if (gevraagd.equals(gevonden)) {
				terugmeldingDetails = terugmeldingCompleetItem.getTerugmeldingDetails();
				break;
			}
		}
		
		//retourneer de gevonden waarde (of null)
		return terugmeldingDetails;	
	}

	
	/**
	 * Filtert de terugmeldingLijst adhv de criteria.
	 * 
	 * @param terugmeldingLijst De te filteren lijst met terugmeldingen
	 * @param status De status waarop gefilterd dient te worden (kan ook null zijn)
	 * @param vanDatum De vanaf-datum waarop gefilterd dient te worden (kan ook null zijn)
	 * @param totDatum De tot-datum waarop gefilterd dient te worden (kan ook null zijn)
	 * @param meldingKenmerk Het meldingKenmerk waarop gefilterd dient te worden (kan ook null zijn)
	 * @param tmfKenmerk Het TMF-kenmerk waarop gefilterd dient te worden (kan ook null zijn)
	 * @return De gefilterde lijst met terugmeldingen
	 */
	private ArrayList<Terugmelding> filterTerugmeldingLijst(ArrayList<Terugmelding> terugmeldingLijst,
															String status,
															GregorianCalendar vanDatum,
															GregorianCalendar totDatum,
															String meldingKenmerk,
															String tmfKenmerk) {
		ArrayList<Terugmelding> result = new ArrayList<Terugmelding>();
		
		for (Terugmelding t : terugmeldingLijst) {
			
			//begin met true want indien alle null dan voldoet t ook
			boolean voldoetAanEisen = true;
			
			//CHECK STATUS
			if (status!=null) {
				if (Statussen.geldigeStatus(status)) {
					//haal details op
					TerugmeldingDetails td = ophalenMeldingDetails(t.getMeldingKenmerk());
					
					//check status				
					voldoetAanEisen = (status.equals(td.getStatusMelding()));
				} else {
					//geen geldige status
					return result;
				}
			}
		
			//CHECK VANDATUM
			if (vanDatum != null) {				
				voldoetAanEisen = (vanDatum.compareTo(t.getTijdstempelAanlevering()) < 0);
			}
			
			//CHECK TOTDATUM
			if (totDatum != null) {
				voldoetAanEisen = (totDatum.compareTo(t.getTijdstempelAanlevering()) > 0);
			}
			
			//CHECK MELDINGKENMERK
			if (meldingKenmerk != null) {
				voldoetAanEisen = (meldingKenmerk.equals(t.getMeldingKenmerk()));
			}
			
			//CHECK TMFKENMERK
			if (tmfKenmerk != null) {
				voldoetAanEisen = (tmfKenmerk.equals(t.getTerugmeldingKern().getTmfKenmerk()));
			}
			
			//Indien voldoetAanEisen dan opnemen in resultaat
			if (voldoetAanEisen) { result.add(t); }
		}
		
		return result;
	}
	
	/**
	 * Adder voor terugmeldingCompleetLijst, nodig ivm toevoegen melding.
	 * 
	 * @param terugmelding De toe te voegen Terugmelding
	 * @param terugmeldingDetails De bijbehorende TerugmeldingDetails
	 */
	public static void addTerugmeldingCompleet(Terugmelding terugmelding, TerugmeldingDetails terugmeldingDetails) {
		TerugmeldingCompleet tc = new TerugmeldingCompleet(terugmelding, terugmeldingDetails);
		MockOpvraagInvokerImpl.terugmeldingCompleetLijst.add(tc);
	}
	
	/**
	 * Replacer voor terugmeldingCompleetLijst, nodig ivm intrekken melding.
	 * 
	 * @param tmfKenmerk Het ID voor de in te trekken terugmelding
	 * @param toelichting De toelichting op de intrekking
	 * 
	 * @throws InvokerException Indien de melding niet gevonden kan worden
	 */
	public static void trekinMelding(String tmfKenmerk, String toelichting) throws InvokerException {
		//doorloop terugmeldingDetailsLijst
		for (int i=0; i < terugmeldingCompleetLijst.size(); i++) {
			TerugmeldingCompleet terugmeldingCompleetItem = terugmeldingCompleetLijst.get(i);
			
			String gevraagd = tmfKenmerk;
			String gevonden = terugmeldingCompleetItem.getTerugmeldingDetails().getTerugmeldingKern().getTmfKenmerk();
			
			//indien terugmeldingDetails met hetzelfde tmfKenmerk wordt gevonden dan
			if (gevraagd.equals(gevonden)) {
				
				String huidigeStatus = terugmeldingCompleetLijst.get(i).getTerugmeldingDetails().getStatusMelding();

				if (huidigeStatus.equals("gemeld") || huidigeStatus.equals("in onderzoek")) {
					//transitie naar status 'ingetrokken' is toegestaan
					//verwijder oude details
					terugmeldingCompleetLijst.remove(i);
					
					//bewerk de lokale kopie (=update status naar ingetrokken)
					TerugmeldingDetails dt = terugmeldingCompleetItem.getTerugmeldingDetails();
					dt.setStatusMelding("ingetrokken");
					terugmeldingCompleetItem.setTerugmeldingDetails(dt);
					
					//bewerk de lokale kopie (=update toelichting)
					Terugmelding t = terugmeldingCompleetItem.getTerugmelding();
					t.setToelichting(toelichting);
					terugmeldingCompleetItem.setTerugmelding(t);
					
					//voeg de bewerkte lokale kopie weer in op dezelfde plek
					terugmeldingCompleetLijst.add(i, terugmeldingCompleetItem);
					
					//ingetrokken
					return;
				} else {
					//transitie naar status 'ingetrokken' is niet toegestaan
					logger.error("Kon melding niet intrekken. Huidige status is namelijk '" + huidigeStatus + "' en niet 'gemeld' of 'in onderzoek'.");
				
					throw new InvokerException();
				}
			}
		}
		
		//indien terugmelding niet wordt gevonden (zou niet mogen, maar goed)
		throw new InvokerException();
	}
	
	/**
	 * Leegt de terugmeldingCompleetLijst. Nodig ivm test-scenario's.
	 */
	public static void clearAll() {
		terugmeldingCompleetLijst.clear();
	}
}
