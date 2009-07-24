package com.ovsoftware.ictu.osb.tmfportal.web.terugmelding;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.StelselCatalogusManager;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**
 * De Attribute controller beheert het attribuut gedeelte van de terugmelding.
 * Alvorens de pagina wordt weergegeven, worden de mogelijke attributen opgehaald.
 * Gegevens over het Obejct worden opgeslagen in de sessie.
 * @author OVSoftware
 *
 */
public class AttrController implements Controller {
	/** Logger voor deze klasse. */
	private StelselCatalogusManager stelselCatalogus;

	/**
	 * Haalt de lijst met attributen zonder waardes op en stuurt de gebruiker
	 * door naar terugmelding_Attr.jsp.
	 * 
	 * @param request Het http-request
	 * @param response De http-response
	 * 
	 * @return De ModelAndView waarmee de gebruiker wordt doorgestuurd naar
	 *         terugmelding_Attr.jsp
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");
		
		// controleer of de sessie niet verlopen is
		if (web == null ) {
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		if (!web.isTerugmeldingInSession()) {
			return web.generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}

		if(request.getParameter("BRObjectID") == "") {
			session.setAttribute("emptyBRObjectID", true);
			return new ModelAndView("/WEB-INF/jsp/terugmelding_Obj.jsp", "model","Step2");
		}
		
		BasisRegistratie tempBR = new BasisRegistratie();
		tempBR.setTag(web.getTerugmelding().getTagBR());
		BRObject tempBRObject = null;
		
		Iterator<BRObject> i;
		
		try {
			i = getStelselCatalogus().getObjectInvoker().getObjectTypen(tempBR).iterator();
		} catch (Exception ex) {
			return web.generateErrorPage(session, WebHandler.COMMUNICATION_ERROR_5);
		}
		
		while (i.hasNext()) {
			tempBRObject = i.next();
			
			if (tempBRObject.getTag().equals(request.getParameter("BRObjectTag"))) {

				web.getTerugmelding().setTagObject(request.getParameter("BRObjectTag"));
				web.getTerugmelding().setIdObject(request.getParameter("BRObjectID"));

				try {
					web.setTerugmeldingSAV(session, "Bevraagbaar", getStelselCatalogus().getAttribuutInvoker().getBasisAttributen(tempBR,tempBRObject).isBevraagbaar());
					web.setTerugmeldingSAV(session, "attrs", getStelselCatalogus().getAttribuutInvoker().getBasisAttributen(tempBR, tempBRObject));
				} catch (InvokerException e){
					return web.generateErrorPage(session, WebHandler.COMMUNICATION_ERROR_5);
				}
			}
		}
		session.setAttribute("emptyBRObjectID", false);
		session.setAttribute("WebHandler", web);
		return new ModelAndView("/WEB-INF/jsp/terugmelding_Attr.jsp", "model","Step3");
	}

	/**
	 * Setter voor stelselCatalogus.
	 * 
	 * @param stelselCatalogus
	 *            De te zetten waarde voor stelselCatalogus
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
