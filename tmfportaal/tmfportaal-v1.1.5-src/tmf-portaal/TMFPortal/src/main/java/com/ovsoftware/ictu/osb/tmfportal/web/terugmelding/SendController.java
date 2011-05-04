package com.ovsoftware.ictu.osb.tmfportal.web.terugmelding;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TMFCoreManager;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**
 * De SendController zorgt voor het uiteindelijk versturen van de terugmelding.
 * Wanneer de terugmelding succesvol verloopt, wordt de sessie meteen geleegd.
 * Wanneer er problemen zijn getoont, wordt er een error getoont.
 * @author OVSoftware
 * 
 */
public class SendController implements Controller {
	private TMFCoreManager tmfCore = null;

	/**
	 * Handelt het verzoek van de gebruiker af om de terugmelding te versturen.
	 * Hierna wordt de gebruiker doorgestuurd naar main.jsp.
	 * 
	 * @param request Het http-request
	 * @param response De http-response
	 * 
	 * @return Een ModelAndView met een verwijzing naar main.jsp
	 * 
	 * @throws IOException
	 *             indien de bijlage niet gelezen kan worden!
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");

		if (web == null ) {
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		if (!web.isTerugmeldingInSession()) {
			return web.generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
			
		try{
			tmfCore.getTerugmeldInvoker().melding(web.getTerugmelding());
			web.stopTerugmeldingSession(session);
			return web.generateSuccessPage(session, WebHandler.TERUGMELDING_GESLAAGD_0);
		} catch (InvokerException ex) {
			return web.generateErrorPage(session, WebHandler.ERROR_SENDING_TERUGMELDING_3);
		}
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
	 * @return tmfCore De huidige waarde voor tmfCore
	 */
	public TMFCoreManager getTmfCore() {
		return tmfCore;
	}
}
