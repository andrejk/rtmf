package com.ovsoftware.ictu.osb.tmfportal.web.overzicht;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TMFCoreManager;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**
 * De overzicht controller beheert de pagina die de resultaten toont van de zoek
 * query. De controller voegt de betreffende informatie toe aan de sessie
 * alvorens de pagina wordt getoont.
 * 
 * @author OVSoftware
 * 
 */
public class OverzichtController implements Controller {

	private TMFCoreManager tmfCore;
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * Handelt een verzoek voor een overzichtspagina met terugmeldingen af. De
	 * functie vult een lijst met Terugmelding-objecten en stuurt de gebruiker
	 * door naar overzicht.jsp.
	 * 
	 * @param arg0
	 *            Het http-request
	 * @param arg1
	 *            De http-response
	 * 
	 * @return De ModelAndView om de gebruiker door te sturen naar overzicht.jsp
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) {

		HttpSession session = arg0.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");


		if (web == null) {
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		if (!web.isOverzichtEnIntrekkenInSession()) {
			return web.generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}

		// melding kenmerk 
		String meldingkenmerk = arg0.getParameter("meldingkenmerk");
		web.setOverzichtEnIntrekkenSAV(session, "meldingkenmerk", meldingkenmerk); //update
		if (meldingkenmerk.equals("")) {
			meldingkenmerk = null;
		}

		// tmf kenmerk
		String tmfkenmerk = arg0.getParameter("tmfkenmerk");
		web.setOverzichtEnIntrekkenSAV(session, "tmfkenmerk", tmfkenmerk); //update
		if (tmfkenmerk.equals("")) {
			tmfkenmerk = null;
		}

		// state
		String state = arg0.getParameter("state");
		web.setOverzichtEnIntrekkenSAV(session, "state", state); //update
		if (state.equals("")){
			state = null;
		}

		// Checking from date
		GregorianCalendar fromDate = null;
		GregorianCalendar toDate = null;

		String dd = arg0.getParameter("fdd");
		if (dd.length()==1) {
			dd = "0" + dd;
		}
		web.setOverzichtEnIntrekkenSAV(session, "fdd", dd); //update
		String mm = arg0.getParameter("fmm");
		if (mm.length()==1) {
			mm = "0" + mm;
		}
		web.setOverzichtEnIntrekkenSAV(session, "fmm", mm); //update
		String yyyy = arg0.getParameter("fyyyy");
		web.setOverzichtEnIntrekkenSAV(session, "fyyyy", yyyy); //update
		
		int validFrom = 0;
		// format string is ddMMyyy, dd = dag in maand, MM = maand in jaar, yyyy
		// = jaar
		if (!dd.equals("") || !mm.equals("") || !yyyy.equals("")) {
			// geen lege waarden
			validFrom= validateDate(dd + mm + yyyy, "ddMMyyyy");
			if (validFrom == 0){
				// string vormt een geldige datum
				fromDate = new GregorianCalendar(Integer.parseInt(yyyy),
						(Integer.parseInt(mm) - 1), Integer.parseInt(dd));
				
			}
		}

		dd = arg0.getParameter("tdd");
		if (dd.length()==1) {
			dd = "0" + dd;
		}
		web.setOverzichtEnIntrekkenSAV(session, "tdd", dd); //update
		mm = arg0.getParameter("tmm");
		if (mm.length()==1) {
			mm = "0" + mm;
		}
		web.setOverzichtEnIntrekkenSAV(session, "tmm", mm); //update
		yyyy = arg0.getParameter("tyyyy");
		web.setOverzichtEnIntrekkenSAV(session, "tyyyy", yyyy); //update

		int validTo = 0;
		if (!dd.equals("") || !mm.equals("") || !yyyy.equals("")) {
			// geen lege waarden
			validTo = validateDate(dd + mm + yyyy, "ddMMyyyy");
			
			if (validTo == 0){
				// string vormt een geldige datum
				toDate = new GregorianCalendar(Integer.parseInt(yyyy),
						(Integer.parseInt(mm) - 1), Integer.parseInt(dd));
			}
		}
		
		ArrayList<Terugmelding> terugmeldingen = new ArrayList<Terugmelding>();
		if (validFrom == 0 && validTo == 0) {
			if (fromDate != null && toDate !=null){
				if (fromDate.after(toDate)) { 
					return web.generateErrorPage(session, WebHandler.INVALID_FROMEXCEEDSTO_11);
				}
			}
			// alles ging goed, invullen van de lijst met terugmeldingen
			try {
				terugmeldingen = tmfCore.getOpvraagInvoker().ophalenMeldingen(
						state, fromDate, toDate, meldingkenmerk, tmfkenmerk);
			} catch (Exception ex) {
				return web.generateErrorPage(session, WebHandler.COMMUNICATION_ERROR_5); //5 is communicatie fout
			}
		} else {
			if (validFrom > 0) { 
				return web.generateErrorPage(session, WebHandler.INVALID_FROMDATE_9);
			} else if (validTo > 0) { 
				return web.generateErrorPage(session, WebHandler.INVALID_TODATE_10);
			}
			
		}

		logger.debug("aantal terugmeldingen" + terugmeldingen.size());
		if (terugmeldingen.size() == 0) {
			web.setOverzichtEnIntrekkenSAV(session, "search_error", "true");
			session.setAttribute("WebHandler", web);
			return new ModelAndView("WEB-INF/jsp/overzicht_search.jsp",
					"model", "noresults");
		}
		Collections.sort(terugmeldingen, new ByDateTime());
		web.setOverzichtEnIntrekkenSAV(session, "search_error", "false");
		web.setOverzichtEnIntrekkenSAV(session, "terugmeldingen", terugmeldingen);
		session.setAttribute("WebHandler", web);
		return new ModelAndView("/WEB-INF/jsp/overzicht.jsp", "model", "results");
	}

	/**
	 * Setter voor tmfCore.
	 * 
	 * @param tmfCore
	 *            De nieuwe waarde voor tmfCore
	 */
	public void setTmfCore(TMFCoreManager tmfCore) {
		this.tmfCore = tmfCore;
	}

	/**
	 * Getter voor tmfCore.
	 * 
	 * @return De waarde van tmfCore
	 */
	public TMFCoreManager getTmfCore() {
		return tmfCore;
	}

	/**
	 * Controleerd of de string datum notatie naar een datum omgezet kan worden.
	 * Controleerd voor format errors en illegale data (Dus een verkeerde maand
	 * of dag wordt gedetecteerd).
	 * 
	 * @param dateStr
	 *            de datum in string formaat
	 * @param formatStr
	 *            de opbouw van de string.
	 * @return 	0 if the date is valid.
	 * 			1 if there was an invalid date format
	 * 			2 if the date contains illegal values
	 * 			3 if the date was after today
	 * 			99 if the formatStr was null
	 */
	private int validateDate(String dateStr, String formatStr) {
		//Date now = new Date();
		if (formatStr == null) {
			return 99;
		}
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		Date testDate = null;

		try {
			testDate = df.parse(dateStr);
		} catch (ParseException e) {
			// invalid date format
			return 1;
		}
		// now test for legal values of parameters
		if (!df.format(testDate).equals(dateStr)) {
			return 2;
		}
		//if (testDate.after(now)) {
		//	return 4;
		//}
		return 0;
	}
}

/**
 * De ByDateTime classe wordt gebruikt voor het sorteren van het overzicht.
 * 
 * @author OVSoftware
 * 
 */
class ByDateTime implements java.util.Comparator<Terugmelding>, Serializable {

	private static final long serialVersionUID = 3464455434462924210L;

	/**
	 * Vergelijkt twee objecten (terugmeldingen).
	 * 
	 * @param o1  Een Terugmelding
	 * @param o2  Nog een Terugmelding
	 * 
	 * @return Het resultaat van de compareTo vergelijking.
	 */
	@Override
	public int compare(Terugmelding o1, Terugmelding o2) {
		return o2.getTijdstempelAanlevering().compareTo(
				o1.getTijdstempelAanlevering());
	}

}
