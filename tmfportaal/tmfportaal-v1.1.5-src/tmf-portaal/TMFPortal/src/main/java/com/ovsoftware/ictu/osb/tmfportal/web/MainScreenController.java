package com.ovsoftware.ictu.osb.tmfportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * De main screen controller beheert het sessie gedeelte voor de eerste pagina.
 * Wanneer naar de hoofd pagina wordt genavigeerd, wordt ten alle tijde de sessie geleegd.
 * 
 * @author OVSoftware
 *
 */
public class MainScreenController implements Controller{

	/**
	 * Haalt de huidige datum op, slaat deze op en gaat door naar main.jsp.
	 * 
	 * @param arg0 Het http-request
	 * @param arg1 Het http-response
	 * 
	 * @return Een ModelAndView met daarin de huidige datumtijd
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) {
		
		// gooi de sessie leeg
		if (arg0.getSession() == null) {
			arg0.getSession(true);
		}
		
		WebHandler web = new WebHandler();
		
		// add WebHandler
		arg0.getSession().setAttribute("WebHandler", web);
		
		return new ModelAndView("/WEB-INF/jsp/main.jsp", "model", "nix");
	}

}
