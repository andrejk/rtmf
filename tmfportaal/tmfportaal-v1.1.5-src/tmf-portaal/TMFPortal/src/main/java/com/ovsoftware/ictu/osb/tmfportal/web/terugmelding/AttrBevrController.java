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
 * De attribuut bevraag controller beheert het bevraag gedeelte van de attribuut controller.
 * Alvorens de pagina wordt geladen, controleerd de controller of de basisregistratie / object
 * combinatie bevraagbaar is. Wanneer dit het geval is, en er is daadwerkelijk een object id 
 * ingevuld, dan wordt deze gekoppelt aan de bevraag knop doormiddel van een sessie attribuut.
 * 
 * @author OVSoftware
 *
 */
public class AttrBevrController implements Controller {
	
	private StelselCatalogusManager stelselCatalogus;

	/**
	 * Haalt de lijst met attributen en waardes op en stuurt de gebruiker
	 * door naar terugmelding_Attr.jsp.
	 * 
	 * @param request Het http-request
	 * @param response De http-response
	 * 
	 * @return De ModelAndView waarmee de gebruiker wordt doorgestuurd naar terugmelding_Attr.jsp
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
			
			if (tempBRObject.getTag().equals(web.getTerugmelding().getTagObject())) {
									
				try {
					if(!web.getTerugmelding().getIdObject().equals("")) {
						web.setTerugmeldingSAV(session, "attrs", getStelselCatalogus().getAttribuutInvoker().getBasisAttributenValues(tempBR, tempBRObject,web.getTerugmelding().getIdObject()));
					} else {
						web.setTerugmeldingSAV(session, "attrs", getStelselCatalogus().getAttribuutInvoker().getBasisAttributen(tempBR, tempBRObject));
					}
				} catch (InvokerException ex) {
					return web.generateErrorPage(session, WebHandler.COMMUNICATION_ERROR_5); //communicatie error
				}
			}
		}
		return new ModelAndView("/WEB-INF/jsp/terugmelding_Attr.jsp", "model", "Step3");
	}

	/**
	 * Setter voor stelselCatalogus.
	 * 
	 * @param stelselCatalogus De te zetten waarde voor stelselCatalogus
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
