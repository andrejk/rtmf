package com.ovsoftware.ictu.osb.tmfportal.web.terugmelding;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.StelselCatalogusManager;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**
 * De Object controller beheert het object gedeelte van de terugmelding.
 * Alvorens de pagina wordt geladen, worden de gegevens over de objecten opgehaald.
 * De gegevens over de basisregistratie wordt tesamen met de objectegevens in de sessie geladen.
 * 
 * @author OVSoftware
 *
 */
public class ObjController implements Controller {
	
	private StelselCatalogusManager stelselCatalogus;

	/**
	 * Handelt het verzoek van de gebruiker af om doorgestuurd te worden
	 * naar terugmelding_Obj.jsp.
	 * 
	 * @param request Het http-request
	 * @param response De http-response
	 * 
	 * @return Een ModelAndView met daarin de verwijzing naar terugmelding_Obj.jsp
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");
		
		if (web == null ) {
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		if (!web.isTerugmeldingInSession()) {
			return web.generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}

		BasisRegistratie tempBR = new BasisRegistratie();
		tempBR.setTag(request.getParameter("BasisRegistratieTag"));

		web.getTerugmelding().setTagBR(tempBR.getTag());

		try {
			web.setTerugmeldingSAV(session, "objs",getStelselCatalogus().getObjectInvoker().getObjectTypen(tempBR));
		} catch (InvokerException e) {
			return web.generateErrorPage(session, WebHandler.COMMUNICATION_ERROR_5);
		}
		
		session.setAttribute("WebHandler",web);
		session.setAttribute("emptyBRObjectID", false);
		return new ModelAndView("/WEB-INF/jsp/terugmelding_Obj.jsp", "model","Step2");
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
