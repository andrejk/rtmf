package com.ovsoftware.ictu.osb.tmfportal.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.ovsoftware.ictu.osb.tmfportal.service.common.Statussen;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ContactGegevens;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;

/**
 * @author rvries
 *
 */
public class WebHandler implements Serializable{

	private static final long serialVersionUID = 7112356217908894106L;
	
	private String tagTerugmelding = "terugmelding";
	private String tagOverzichtEnIntrekken = "overzichtEnIntrekken";
	private boolean sessionRunningTerugmelding;
	private boolean sessionRunningOverzichtEnIntrekken;
	private Terugmelding terugmelding;
	
	public static final int SESSION_EXPIRED_0 = 0;
	public static final int NO_OBJECTS_FOUND_1 = 1;
	public static final int NO_ATTRIBUTES_FOUND_2 = 2;
	public static final int ERROR_SENDING_TERUGMELDING_3 = 3;
	public static final int ERROR_SENDING_INTREKKING_4 = 4;
	public static final int COMMUNICATION_ERROR_5 = 5;
	public static final int FILE_ERROR_6 = 6;
	public static final int EMPTY_ATTRIBUTE_LIST_7 = 7;
	public static final int MAX_FILE_SIZE_8 = 8;
	public static final int INVALID_FROMDATE_9 = 9;
	public static final int INVALID_TODATE_10 = 10;
	public static final int INVALID_FROMEXCEEDSTO_11 = 11;
	public static final int DEFAULT_ERROR_99 = 99;
	public static final int DEFAULT_WARNING_100 = 100;

	public static final int TERUGMELDING_GESLAAGD_0 = 0;
	public static final int INTREKKING_GESLAAGD_1 = 1;
	
	/**
	 * @param session HttpSession
	 * @param attributeName de naam van het attribuut
	 * @return Het Object dat behoort bij de attributeName, een lege string als er nog nix in staat.
	 */
	public Object getTerugmeldingSAV(HttpSession session, String attributeName) {
		if (session.getAttribute(tagTerugmelding+"_"+attributeName) == null) {
			session.setAttribute(tagTerugmelding+"_"+attributeName, "");
		}

		return session.getAttribute(tagTerugmelding+"_"+attributeName);
	}
	
	/**
	 * @param session HttpSession
	 * @param name de naam van het attribuut
	 * @param value de waarde van het attribuut
	 */
	public void setTerugmeldingSAV(HttpSession session, String name, Object value) {
			session.setAttribute(tagTerugmelding+"_"+name, value);
	}
	
	/**
	 * @param session HttpSession
	 * @param attributeName de naam van het attribuut
	 * @return Het Object dat behoort bij de attributeName, een lege string als er nog nix in staat.
	 */
	public Object getOverzichtEnIntrekkenSAV(HttpSession session, String attributeName) {
		if (session.getAttribute(tagOverzichtEnIntrekken+"_"+attributeName) == null) {
			session.setAttribute(tagOverzichtEnIntrekken+"_"+attributeName, "");
		}

		return session.getAttribute(tagOverzichtEnIntrekken+"_"+attributeName);
	}
	
	/**
	 * @param session HttpSession
	 * @param name de naam van het attribuut
	 * @param value de waarde van het attribuut
	 */
	public void setOverzichtEnIntrekkenSAV(HttpSession session, String name, Object value) {
			session.setAttribute(tagOverzichtEnIntrekken+"_"+name, value);
	}
	
	/**
	 * @return the tagTerugmelding
	 */
	public String getTagTerugmelding() {
		return tagTerugmelding;
	}

	/**
	 * @param tagTerugmelding the tagTerugmelding to set
	 */
	public void setTagTerugmelding(String tagTerugmelding) {
		this.tagTerugmelding = tagTerugmelding;
	}

	/**
	 * @param tagOverzicht the tagOverzicht to set
	 */
	public void setTagOverzicht(String tagOverzicht) {
		this.tagOverzichtEnIntrekken = tagOverzicht;
	}

	/**
	 * @return the tagOverzicht
	 */
	public String getTagOverzicht() {
		return tagOverzichtEnIntrekken;
	}

	/**
	 * @param session HttpSession
	 * @param errorCode de code die hoort bij de error melding
	 * @return ModelAndView met de verwijzing naar de error pagina
	 */
	public ModelAndView generateErrorPage(HttpSession session, int errorCode){
		session.setAttribute("Type", "error");	
		session.setAttribute("Code", errorCode); 
		return new ModelAndView("/WEB-INF/jsp/pageview.jsp", "model", "error");	
	}
	
	/**
	 * @param session HttpSession
	 * @param successCode De code die hoort bij de success melding
	 * @return ModelAndView met de verwijzing naar de success pagina
	 */
	public ModelAndView generateSuccessPage(HttpSession session, int successCode){ 
		session.setAttribute("Type", "success");	
		session.setAttribute("Code", successCode); 
		return new ModelAndView("/WEB-INF/jsp/pageview.jsp", "model", "success");	
		
	}
	
	/**
	 * Start een nieuwe sessie voor de terugmelding.
	 */
	public void startNewTerugmeldingSession(){
		sessionRunningTerugmelding = true;
		
		// nieuw terugmelding object creeren met lege waarden
		setTerugmelding(new Terugmelding());
		terugmelding.setTagBR("");
		terugmelding.setTagObject("");
		terugmelding.setIdObject("");
		terugmelding.setToelichting("");
		terugmelding.setMeldingKenmerk("");
		ContactGegevens contact = new ContactGegevens();
		contact.setEmail("");
		contact.setNaam("");
		contact.setTelefoon("");
		terugmelding.setContactGegevens(contact);
	}
	
	/**
	 * Stopt de sessie die behoort bij een terugmelding.
	 * @param session HttpSession
	 */
	@SuppressWarnings("unchecked")
	public void stopTerugmeldingSession(HttpSession session){
		sessionRunningTerugmelding = false;
		
		// verwijder alle terugmelding gerelateerde sessie objecten
		Enumeration<String> attributes = session.getAttributeNames();
		while (attributes.hasMoreElements()) {
		  String attribute = attributes.nextElement();
		  if(attribute.startsWith(tagTerugmelding)){
			  session.removeAttribute(attribute);
		  }
		}
	}
	
	/**
	 * Getter voor terugmeldingInSession.
	 * 
	 * @return true als er een sessie loopt voor de terugmelding
	 */
	public boolean isTerugmeldingInSession(){
		return sessionRunningTerugmelding;
	}

	/**
	 * Start een nieuwe sessie voor het overzicht.
	 * @param session HttpSession
	 */
	public void startNewOverzichtEnIntrekkenSession(HttpSession session){
		sessionRunningOverzichtEnIntrekken = true;
		// zet zoek error op false
		setOverzichtEnIntrekkenSAV(session, "search_error", "false");
		// vul waarden als leeg in.
		setOverzichtEnIntrekkenSAV(session, "meldingkenmerk", "");
		setOverzichtEnIntrekkenSAV(session, "tmfkenmerk", "");
		setOverzichtEnIntrekkenSAV(session, "state", "");
		setOverzichtEnIntrekkenSAV(session, "fdd", "");
		setOverzichtEnIntrekkenSAV(session, "fmm", "");
		setOverzichtEnIntrekkenSAV(session, "fyyyy", "");
		setOverzichtEnIntrekkenSAV(session, "tdd", "");
		setOverzichtEnIntrekkenSAV(session, "tmm", "");
		setOverzichtEnIntrekkenSAV(session, "tyyyy", "");
		
		// nieuw status lijst invullen
		ArrayList<String> states = new ArrayList<String>();
		states.add("");
		states.addAll(Statussen.getValues());
		setOverzichtEnIntrekkenSAV(session, "states", states);
		setOverzichtEnIntrekkenSAV(session, "toelichting", "");
	}
	
	/**
	 * Stopt de sessie die behoort bij een overzicht.
	 * @param session HttpSession
	 */
	@SuppressWarnings("unchecked")
	public void stopOverzichtEnInrekkenSession(HttpSession session){
		sessionRunningOverzichtEnIntrekken = false;
		
		// verwijder alle terugmelding gerelateerde sessie objecten
		Enumeration<String> attributes = session.getAttributeNames();
		while (attributes.hasMoreElements()) {
		  String attribute = attributes.nextElement();
		  if(attribute.startsWith(tagOverzichtEnIntrekken)){
			  session.removeAttribute(attribute);
		  }
		}
	}
	
	/**
	 * Getter voor boolean overzichtEnIntrekkenInSession.
	 * 
	 * @return true als er een sessie loopt voor het overzicht
	 */
	public boolean isOverzichtEnIntrekkenInSession(){
		return sessionRunningOverzichtEnIntrekken;
	}
	
	/**
	 * Setter.
	 * 
	 * @param terugmelding the terugmelding to set
	 */
	public void setTerugmelding(Terugmelding terugmelding) {
		this.terugmelding = terugmelding;
	}

	/**
	 * Getter.
	 * 
	 * @return the terugmelding
	 */
	public Terugmelding getTerugmelding() {
		return terugmelding;
	}
}
