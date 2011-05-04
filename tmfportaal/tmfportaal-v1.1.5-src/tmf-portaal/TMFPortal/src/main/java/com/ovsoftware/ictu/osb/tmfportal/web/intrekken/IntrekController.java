package com.ovsoftware.ictu.osb.tmfportal.web.intrekken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TMFCoreManager;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**
 * Deze class functioneerd als een controller voor het Intrekken van een terugmelding.
 * De controller checkt of de gegevens die in het formulier zijn ingeladen,
 * voldoen aan de eisen die daar voor zijn opgesteld. 
 * 
 * Wanneer de ingevulde waarden in het formulier voldoen aan de voorwaarden,
 * wordt de succesview geladen met daarin de bevestiging dat de intrekking is geslaagd.
 * 
 * Wanneer de ingevlude waarden niet voldoen, wordt de pagina opnieuw geladen.
 * Achter de velden die niet voldoen, wordt getoont wat er mankeert.
 * 
 *  @author OVSoftware
 */
public class IntrekController extends SimpleFormController{

	private TMFCoreManager tmfCore;

	/**
	 * Laat de super het verzoek afhandelen.
	 * 
	 * @param request Het http-request
	 * @param response De http-response
	 * 
	 * @return ModelAndView van super
	 * 
	 * @throws Exception van super
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");

		if (web == null){
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		if (!web.isOverzichtEnIntrekkenInSession()){
			return web.generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		
		return super.handleRequest(request, response);
	}
	
	/**
	 * Setter voor tmfCore.
	 * 
	 * @param tmfCore De nieuwe waarde van tmfCore
	 */
	public void setTmfCore(TMFCoreManager tmfCore) {
		this.tmfCore = tmfCore;
	}

	/**
	 * Getter van tmfCore.
	 * 
	 * @return tmfCore De huidige waarde van tmfCore
	 */
	public TMFCoreManager getTmfCore() {
		return tmfCore;
	}
	
	/**
	 * Retourneert of dit een form submissie is.
	 * De juiste form submissie wordt bepaald door een hidden field op de pagina.
	 * Wanneer deze wordt gevonden , betekend dat de POST van de juiste pagina is gegeven.
	 * 
	 * @param request Het http-request
	 * 
	 * @return True indien de juiste POST is opgevangen
	 */
	protected boolean isFormSubmission(HttpServletRequest request) {
		return (request.getParameter("intrekkenpost") != null);
	}

	/**
	 * Handelt een submit van de intrekking af.
	 * Probeert een intrekking uit te voeren.
	 * 
	 * Wanneer de intrekking slaagd, wordt de sessie geleegd.
	 * De sessie wordt vervogens gevuld met gegevens voor de pageview.
	 * 
	 * Wanneer er iets fout gaat tijdens het intrekken van een terugmelding, 
	 * wordt er een pagina getoont met daarin de melding dat de intrekking is mislukt.
	 * 
	 * @param request Het http-request
	 * @param response De http-response
	 * @param command Een IntrekFormData-object
	 * @param errors Een lijst waaraan foutmeldingen kunnen worden toegevoegd.
	 * 
	 * @return super.onSubmit(request, response, command, errors)
	 * 
	 * @throws Exception van super
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		HttpSession session = request.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");

		if (web == null){
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		if (!web.isOverzichtEnIntrekkenInSession()){
			return web.generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		
		IntrekFormdata data = (IntrekFormdata) command;
		try {
			getTmfCore().getIntrekInvoker().intrekking((String)web.getOverzichtEnIntrekkenSAV(session, "tmfKenmerk"), data.getReason());
			// Geen exception betekend dat de intrekking geslaagd is.
			// intrekking van terugmelding is geslaag, opruimen en melding gegevens inladen
			web.stopOverzichtEnInrekkenSession(session);
			return web.generateSuccessPage(session, WebHandler.INTREKKING_GESLAAGD_1); //inrekking succesvol
		} catch (InvokerException ex){
			return web.generateErrorPage(session, WebHandler.ERROR_SENDING_INTREKKING_4); //error
		}
	}
}
