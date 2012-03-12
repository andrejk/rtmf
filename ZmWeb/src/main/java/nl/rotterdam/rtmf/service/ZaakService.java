/**
 * 
 */
package nl.rotterdam.rtmf.service;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import nl.interaccess.zakenmagazijn.model.CodeQueryType;
import nl.interaccess.zakenmagazijn.model.KenmerkGrpType;
import nl.interaccess.zakenmagazijn.model.Keuze;
import nl.interaccess.zakenmagazijn.model.ObjectFactory;
import nl.interaccess.zakenmagazijn.model.Stap;
import nl.interaccess.zakenmagazijn.model.StapUpdate;
import nl.interaccess.zakenmagazijn.model.Status;
import nl.interaccess.zakenmagazijn.model.Zaak;
import nl.interaccess.zakenmagazijn.model.ZaakDetail;
import nl.interaccess.zakenmagazijn.model.ZaakDetailRequest;
import nl.interaccess.zakenmagazijn.model.ZaakQuery;
import nl.interaccess.zakenmagazijn.model.ZaakQueryResponse;
import nl.interaccess.zakenmagazijn.model.ZaakUpdate;
import nl.interaccess.zakenmagazijn.model.ZaakidentificatieQueryType;
import nl.rotterdam.ioo.zm.client.impl.ZakenmagazijnWsClient;
import nl.rotterdam.rtmf.exception.ZMWebException;
import nl.rotterdam.rtmf.exception.ZMWebMailException;
import nl.rotterdam.rtmf.form.helper.SelectOption;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

/**
 * @author rweverwijk
 *
 */
@Service
public class ZaakService implements IZaakService{
	@Autowired
	private ZakenmagazijnWsClient zakenMagazijnClient;
	@Autowired
	private IEmailService emailService;
	private ObjectFactory objectFactory = new ObjectFactory();
	private static final Map<String, String> statusOmschrijvingen = new HashMap<String, String>();
	private static final Map<String, String> stapOmschrijvingen = new HashMap<String, String>();
	static {
		statusOmschrijvingen.put("ON", "Ontvangen");
		statusOmschrijvingen.put("IB", "In behandeling");
		statusOmschrijvingen.put("AH", "Afgehandeld");
		
		stapOmschrijvingen.put("ONTVANGEN", "Ontvangen");
		stapOmschrijvingen.put("BEOORDELEN", "Beoordelen");
		stapOmschrijvingen.put("ONDERZOEK", "Onderzoeken");
		stapOmschrijvingen.put("STAKEN", "Afgehandeld");
	}
	
	Logger logger = Logger.getLogger(ZaakService.class); 
	
	/* (non-Javadoc)
	 * @see nl.rotterdam.rtmf.service.IZaakService#statusBijwerken(nl.interaccess.zakenmagazijn.model.ZaakDetail, java.lang.String, java.lang.String)
	 */
	public void statusBijwerken(ZaakDetail zaak, String nieuweStatus,
			String toelichting) throws ZMWebException {
		long start = System.currentTimeMillis();
		try {
			updateZaak(zaak, nieuweStatus, toelichting);
			updateStap(zaak, nieuweStatus, toelichting);
			if (nieuweStatus.equals("onderzoek")) {
				stapCreatie(zaak, nieuweStatus);
			}
			statusCreatie(zaak, nieuweStatus);
		} catch (Exception e) {
			throw new ZMWebException(e);
		}
		try {
			emailService.emailNieuweStatus(zaak, nieuweStatus, toelichting);
		} catch (Exception e) {
			throw new ZMWebMailException(e);
		}
		logger.info(String.format("service status bijwerken duurde %d miliseconden", System.currentTimeMillis() - start));
	}
	/* (non-Javadoc)
	 * @see nl.rotterdam.rtmf.service.IZaakService#getZaken(java.util.Map)
	 */
	public List<Zaak> getZaken(Map<String, Object> parameters) {
		long start = System.currentTimeMillis();
		ZaakQuery zaakQuery = createZaakQuery(parameters);
		ZaakQueryResponse zaakQueryResponse = zakenMagazijnClient.zaakQuery(zaakQuery);
		logger.info(String.format("service zaakQuery duurde %d miliseconden", System.currentTimeMillis() - start));
		return zaakQueryResponse.getZaak();		
	}
	/* (non-Javadoc)
	 * @see nl.rotterdam.rtmf.service.IZaakService#getZaak(java.lang.String)
	 */
	public ZaakDetail getZaak(String zaakIdentificatie) {
		long start = System.currentTimeMillis();
		ZaakDetailRequest zaakDetailRequest = new ZaakDetailRequest();
		zaakDetailRequest.setIdentificatie(zaakIdentificatie);
		ZaakDetail zaakQueryDetail = zakenMagazijnClient.zaakQueryDetail(zaakDetailRequest);
		logger.info(String.format("service zaakQueryDetail duurde %d miliseconden", System.currentTimeMillis() - start));
		return zaakQueryDetail;
	}
	/**
	 * @param zaak
	 * @param nieuweStatus
	 */
	private void updateZaak(ZaakDetail zaak, String nieuweStatus, String toelichting) {
		long start = System.currentTimeMillis();
		ZaakUpdate zaakUpdate = createZaakUpdate(zaak, nieuweStatus, toelichting);
		zakenMagazijnClient.zaakUpdate(zaakUpdate);
		logger.info(String.format("service zaakUpdate duurde %d miliseconden", System.currentTimeMillis() - start));
	}
	
	/**
	 * @param zaak
	 * @param nieuweStatus
	 * @param toelichting
	 */
	private void updateStap(ZaakDetail zaak, String nieuweStatus,
			String toelichting) {
		long start = System.currentTimeMillis();
		StapUpdate stapUpdate = createStapUpdate(zaak, nieuweStatus,
				toelichting);
		zakenMagazijnClient.stapUpdate(stapUpdate );
		logger.info(String.format("service stapUpdate duurde %d miliseconden", System.currentTimeMillis() - start));
	}
	/**
	 * @param zaak
	 * @param nieuweStatus
	 */
	private void stapCreatie(ZaakDetail zaak, String nieuweStatus) {
		long start = System.currentTimeMillis();
		Stap stap = createStapCreatie(zaak, nieuweStatus);
		zakenMagazijnClient.stapCreatie(stap);
		logger.info(String.format("service stapCreatie duurde %d miliseconden", System.currentTimeMillis() - start));
	}
	/**
	 * @param zaak
	 * @param nieuweStatus
	 */
	private void statusCreatie(ZaakDetail zaak, String nieuweStatus) {
		long start = System.currentTimeMillis();
		Status status = createStatusCreatie(zaak, nieuweStatus);
		zakenMagazijnClient.statusCreatie(status );
		logger.info(String.format("service statusCreatie duurde %d miliseconden", System.currentTimeMillis() - start));
	}
	/**
	 * @param zaak
	 * @param nieuweStatus
	 * @return
	 */
	private ZaakUpdate createZaakUpdate(ZaakDetail zaak, String nieuweStatus, String toelichting) {
		ZaakUpdate zaakUpdate = new ZaakUpdate();
		zaakUpdate.setZaakidentificatie(zaak.getZaakidentificatie());
		zaakUpdate.setResultaatcode(objectFactory.createZaakUpdateResultaatcode(nieuweStatus));
		zaakUpdate.setResultaatomschrijving(objectFactory.createZaakUpdateResultaatomschrijving(resolveResultaatOmschrijving(nieuweStatus)));
		zaakUpdate.setResultaattoelichting(objectFactory.createZaakUpdateResultaattoelichting(toelichting));
		zaakUpdate.setOgeId("0");
		if (nieuweStatus.equals("onderzocht") || nieuweStatus.equals("nietontv") || nieuweStatus.equals("gestaakt")) {
			zaakUpdate.setEinddatum(objectFactory.createZaakUpdateEinddatum(new XMLGregorianCalendarImpl(new GregorianCalendar())));
		}
		return zaakUpdate;
	}
	/**
	 * @param zaak
	 * @param nieuweStatus
	 * @param toelichting
	 * @return
	 */
	private StapUpdate createStapUpdate(ZaakDetail zaak, String nieuweStatus,
			String toelichting) {
		StapUpdate stapUpdate = new StapUpdate();
		stapUpdate.setZaakidentificatie(zaak.getZaakidentificatie());
		Stap laatsteStap = getLaatsteStap(zaak);
		stapUpdate.setBegindatum(laatsteStap.getBegindatum());
		stapUpdate.setStaptypecode(laatsteStap.getStaptypecode());
		stapUpdate.setStapvolgnummer(laatsteStap.getStapvolgnummer());
		stapUpdate.setStapeinddatum(objectFactory.createStapUpdateStapeinddatum(new XMLGregorianCalendarImpl(new GregorianCalendar())));
		stapUpdate.setResultaatcode(objectFactory.createStapUpdateResultaatcode(nieuweStatus));
		stapUpdate.setResultaatomschrijving(objectFactory.createStapUpdateResultaatomschrijving(resolveResultaatOmschrijving(nieuweStatus)));
		stapUpdate.setResultaattoelichting(objectFactory.createStapUpdateResultaattoelichting(toelichting));
		stapUpdate.setOgeId("0");
		return stapUpdate;
	}
	/**
	 * @param zaak
	 * @param nieuweStatus
	 * @return
	 */
	private Stap createStapCreatie(ZaakDetail zaak, String nieuweStatus) {
		Stap stap = new Stap();
		stap.setZaakidentificatie(zaak.getZaakidentificatie());
		stap.setGeenStapUitvoerder(Keuze.TRUE);
		stap.setGeenStapVerantwoordelijke(Keuze.TRUE);
		stap.setBegindatum(new XMLGregorianCalendarImpl(new GregorianCalendar()));
		stap.setStaptypecode(resolveStapTypeCode(nieuweStatus));
		stap.setStapomschrijving(resolveStapomschrijving(stap.getStaptypecode()));
		stap.setOgeId("0");
		return stap;
	}
	/**
	 * @param zaak
	 * @param nieuweStatus
	 * @return
	 */
	private Status createStatusCreatie(ZaakDetail zaak, String nieuweStatus) {
		Status status = new Status();
		status.setZaakidentificatie(zaak.getZaakidentificatie());
		status.setGeenStatusZetter(Keuze.TRUE);
		status.setDatumstatusgezet(new XMLGregorianCalendarImpl(new GregorianCalendar()));
		status.setStatuscode(resolveStatusCode(nieuweStatus));
		status.setStatusomschrijving(resolveStatusOmschrijving(status.getStatuscode()));
		status.setOgeId("0");
		return status;
	}
	/**
	 * Bepaal de laatste stap van de zaak
	 * @param zaak
	 * @return stap met het hoogste stapVolgnummer
	 */
	private Stap getLaatsteStap(ZaakDetail zaak) {
		List<Stap> stappen = zaak.getStap();
		Stap stap = null;
		for (Stap huidigeStap : stappen) {
			if (stap == null) {
				stap = huidigeStap;
			} else if(huidigeStap.getStapvolgnummer().compareTo(stap.getStapvolgnummer()) > 0) {
				stap = huidigeStap;
			}
		}
		return stap;
	}
	/**
	 * @param nieuweStatus
	 * @return
	 */
	private String resolveResultaatOmschrijving(String nieuweStatus) {
		int indexOf = SelectOption.statusList.indexOf(new SelectOption(nieuweStatus, null));
		if (indexOf != -1) {
			return SelectOption.statusList.get(indexOf).getValue() + " door bronhouder";
		}
		return null;
	}
	/**
	 * @param nieuweStatus
	 * @return
	 */
	private String resolveStatusCode(String nieuweStatus) {
		String result = null;
		if (nieuweStatus != null) {
			if (nieuweStatus.equals("onderzoek") || nieuweStatus.equals("ingetr")) {
				result = "IB";
			} else if (nieuweStatus.equals("gestaakt") || nieuweStatus.equals("onderzocht") 
					|| nieuweStatus.equals("nietontv")) {
				result = "AH";
			}
		}
			
		return result;
	}
	
	/**
	 * bepaal de status omschrijving obv statusCode
	 * @param statusCode
	 * @return
	 */
	private String resolveStatusOmschrijving(String statusCode) {
		if (statusCode != null) {
			return statusOmschrijvingen.get(statusCode);
		}
		return null;
	}
	
	/**
	 * @param nieuweStatus
	 * @return
	 */
	private String resolveStapTypeCode(String nieuweStatus) {
		String result = null;
		if (nieuweStatus != null) {
			if (nieuweStatus.equals("onderzoek")) {
				result = "ONDERZOEK";
			} else if (nieuweStatus.equals("ingetr")) {
				result = "STAKEN";
			}
		}
			
		return result;
	}
	/**
	 * @param stapCode
	 * @return
	 */
	private String resolveStapomschrijving(String stapCode) {
		if (stapCode != null) {
			return stapOmschrijvingen.get(stapCode);
		}
		return null;
	}
	
	/**
	 * @param parameters
	 * @return
	 */
	private ZaakQuery createZaakQuery(final Map<String, Object> parameters) {
		ZaakQuery zaakQuery = new ZaakQuery();
		
		// MeldingKenmerk
		List<String> trefwoord = zaakQuery.getTrefwoord();
		trefwoord.add((String)parameters.get("meldingskenmerk"));
		
		//Zaaknummer
		if (StringUtils.isNotBlank((String)parameters.get("zaaknummer"))) {
			ZaakidentificatieQueryType zaakIdentificatie = objectFactory.createZaakidentificatieQueryType();
			zaakIdentificatie.setValue((String)parameters.get("zaaknummer"));
			zaakQuery.getZaakidentificatie().add(zaakIdentificatie);
		}
		//ObjectIdentificatie
//		if (StringUtils.isNotBlank((String)parameters.get("objectIdentificatie"))) {
			List<KenmerkGrpType> kenmerken = zaakQuery.getKenmerk();
			KenmerkGrpType objectIdentificatie = new KenmerkGrpType();
			objectIdentificatie.setKenmerkBron("objectIdentificatie");
			objectIdentificatie.setKenmerk((String)parameters.get("objectIdentificatie"));
			kenmerken.add(objectIdentificatie);
//		}
		//vanafdatum
		XMLGregorianCalendarImpl vanafDatum = new XMLGregorianCalendarImpl(); 
		if (!parameters.get("vanafDatum").equals("null-null-null")) {
			String[] vanafDatumDelen = ((String)parameters.get("vanafDatum")).split("-");
			vanafDatum.setDay(Integer.parseInt(vanafDatumDelen[0]));
			vanafDatum.setMonth(Integer.parseInt(vanafDatumDelen[1]));
			vanafDatum.setYear(Integer.parseInt(vanafDatumDelen[2]));
			
			zaakQuery.setStartdatumVanaf(vanafDatum);
		}
		//totdatum
		if (!parameters.get("totDatum").equals("null-null-null")) {
			XMLGregorianCalendarImpl totDatum = new XMLGregorianCalendarImpl();
			String[] totDatumDelen = ((String)parameters.get("totDatum")).split("-");
			totDatum.setDay(Integer.parseInt(totDatumDelen[0]));
			totDatum.setMonth(Integer.parseInt(totDatumDelen[1]));
			totDatum.setYear(Integer.parseInt(totDatumDelen[2]));
			try {
				totDatum.add(
						DatatypeFactory.newInstance().newDuration(true, 0, 0, 1, 0, 0, 0));
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			zaakQuery.setStartdatumTot(totDatum);
		}
		//statusTerugmelding
		if (StringUtils.isNotBlank((String)parameters.get("statusTerugmelding"))) {
			CodeQueryType codeQueryType = new CodeQueryType();
			codeQueryType.setValue((String)parameters.get("statusTerugmelding"));
			ObjectFactory objectFactory = new ObjectFactory();
			JAXBElement<CodeQueryType> createZaakQueryResultaatcode = objectFactory.createZaakQueryResultaatcode(codeQueryType);
			zaakQuery.setResultaatcode(createZaakQueryResultaatcode);
		}
		return zaakQuery;
	}
}
