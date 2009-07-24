package com.ovsoftware.ictu.osb.tmfportal.web.terugmelding;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.StelselCatalogusManager;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**
 * De Basisregistratie controller beheert het BR gedeelte van de terugmelding
 * Alvorens de pagina wordt geladen, worden de mogelijke basisregistraties ingeladen in de sessie.
 * 
 * @author OVSoftware
 *
 */
public class BRController implements Controller {
	private StelselCatalogusManager stelselCatalogus;

	/**
	 * Handelt het verzoek van de gebruiker om een nieuwe terugmelding aan te maken.
	 * 
	 * @param arg0 Het http-request
	 * @param arg1 De http-response
	 * 
	 * @return ModelAndView die de gebruiker doorstuurt naar terugmelding_BR.jsp	
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) {

		// Er is altijd een sessie aanwezig dus we halen hem hier op.
		HttpSession session = arg0.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");
		
		if (web == null) {
			web = new WebHandler();
		}
		web.startNewTerugmeldingSession();
		
		try {
			web.setTerugmeldingSAV(session, "brs", getStelselCatalogus().getBasisRegistratieInvoker().getBasisRegistraties());
		} catch (InvokerException e) {
			return web.generateErrorPage(session, WebHandler.COMMUNICATION_ERROR_5);
		}
		
		session.setAttribute("WebHandler", web);

		return new ModelAndView("/WEB-INF/jsp/terugmelding_BR.jsp", "model", "Step1");
	}

	/**
	 * Setter voor stelselCatalogus.
	 * 
	 * @param stelselCatalogus De nieuwe waarde voor stelselCatalogus
	 */
	public void setStelselCatalogus(StelselCatalogusManager stelselCatalogus) {
		this.stelselCatalogus = stelselCatalogus;
	}

	/**
	 * Getter voor stelselCatalogus.
	 * 
	 * @return De huidige waarde van stelselCatalogus
	 */
	public StelselCatalogusManager getStelselCatalogus() {
		return stelselCatalogus;
	}
}
