package com.ovsoftware.ictu.osb.tmfportal.web.overzicht;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TMFCoreManager;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**
 * De search controller beheert de zoekpagina voor de terugmelding.
 * Alvorens de pagina wordt getoont, worden de mogelijke statussen ingeladen.
 * 
 * @author OVSoftware
 *
 */
public class SearchController implements Controller{

	private TMFCoreManager tmfCore;

	/**
	 * Handelt het verzoek van de gebruiker af om doorgestuurd te worden naar
	 * de zoek-pagina (overzich_search.jsp). Deze functie zet de lijst met statussen
	 * eerst nog in de sessie voor gebruik op de volgende pagina.
	 * 
	 * @param arg0 Het http-request
	 * @param arg1 De http-response
	 * 
	 * @return De ModelAndView die de gebruiker doorstuurt naar overzicht_search.jsp
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) {
		
		HttpSession session = arg0.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");
		
		if (web == null) {
			web = new WebHandler();
		}
		web.startNewOverzichtEnIntrekkenSession(session);
		session.setAttribute("WebHandler", web);
		return new ModelAndView("/WEB-INF/jsp/overzicht_search.jsp", "model", "nix");
	}
	
	/**
	 * Setter voor tmfCore.
	 * 
	 * @param tmfCore De nieuwe waarde van tmfcore
	 */
	public void setTmfCore(TMFCoreManager tmfCore) {
		this.tmfCore = tmfCore;
	}

	/**
	 * Getter voor tmfCore. 
	 * 
	 * @return tmfCore De huidige waarde van tmfCore
	 */
	public TMFCoreManager getTmfCore() {
		return tmfCore;
	}
}
