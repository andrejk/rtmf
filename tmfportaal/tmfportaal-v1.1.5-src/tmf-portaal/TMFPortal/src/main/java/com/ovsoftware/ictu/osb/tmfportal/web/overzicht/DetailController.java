package com.ovsoftware.ictu.osb.tmfportal.web.overzicht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import com.ovsoftware.ictu.osb.tmfportal.service.common.Converter;
import com.ovsoftware.ictu.osb.tmfportal.service.common.Statussen;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TMFCoreManager;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ObjectAttribuut;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**
 * De detail controller beheert de detailpagina die behoort bij een specifieke terugmelding.
 * Alvorens de pagina wordt getoont, dienen een aantal attributen aan de sessie te worden verbonden.
 * Deze informatie wordt opgehaald aan de hand van de ingevulde waarde van de vorige  pagina.
 * 
 * @author OVSoftware
 *
 */
public class DetailController implements Controller{

	private TMFCoreManager tmfCore;

	/**
	 * Handelt een verzoek voor details mbt een bepaalde terugmelding af. Alle details mbt
	 * die melding worden opgevraagd bij de webservice en vervolgens wordt het resultaat in
	 * de sessie opgeslagen. Daarna wordt de gebruiker doorgestuurd naar overzicht_detail.jsp.
	 * 
	 * @param arg0 Het http-request
	 * @param arg1 De http-response
	 * 
	 * @return ModelAndView met alle details van een terugmelding erin.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) {
		HttpSession session = arg0.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");

		if (web == null){
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		if (!web.isOverzichtEnIntrekkenInSession()){
			return web.generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}

		String meldingKenmerk = StringEscapeUtils.unescapeHtml(arg0.getParameter("meldingKenmerkLink"));
		
		Terugmelding terugmelding = null;
		
		Iterator<Terugmelding> i = ((ArrayList<Terugmelding>) web.getOverzichtEnIntrekkenSAV(session, "terugmeldingen")).iterator();
		
		while(i.hasNext()){
			Terugmelding temp = i.next();
			if(temp.getMeldingKenmerk().equals(meldingKenmerk)){
				terugmelding = temp;
				break;
			}
		}
		if (terugmelding == null) {
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		//contact gegevens
		 web.setOverzichtEnIntrekkenSAV(session, "contactNaam", terugmelding.getContactGegevens().getNaam());
		 web.setOverzichtEnIntrekkenSAV(session, "contactTelefoon", terugmelding.getContactGegevens().getTelefoon());	
		 web.setOverzichtEnIntrekkenSAV(session, "contactEmail", terugmelding.getContactGegevens().getEmail());
		 web.setOverzichtEnIntrekkenSAV(session, "terugmeldingToelichting", terugmelding.getToelichting());
		 
		 
		 //Maak een arraylist met bijlage namen. (is op het moment van oplevering nog niet beschikbaar in dit stadium)
		 /*ArrayList<String> bijlagen = new ArrayList<String>();
		 if (terugmelding.getBijlageLijst() != null){
			 for(int j =0; j < terugmelding.getBijlageLijst().size(); j++) {
				 bijlagen.add(terugmelding.getBijlageLijst().get(j).getBestandsnaam());
			 }
		 }
		 web.setOverzichtEnIntrekkenSAV(session, "bijlagen", bijlagen);*/
		 
		 
		//melding gegevens
		 web.setOverzichtEnIntrekkenSAV(session, "meldingKenmerk", meldingKenmerk);
		 web.setOverzichtEnIntrekkenSAV(session, "tmfKenmerk", terugmelding.getTerugmeldingKern().getTmfKenmerk());
		
		//basisregistratie en object gegevens
		 web.setOverzichtEnIntrekkenSAV(session, "basisregistratie", terugmelding.getTagBR());
		 web.setOverzichtEnIntrekkenSAV(session, "objecttype", terugmelding.getTagObject());
		 web.setOverzichtEnIntrekkenSAV(session, "objectidentificatie",terugmelding.getIdObject());
		
		//attribuut gegevens
		ArrayList<ObjectAttribuut> attribuutlijst = terugmelding.getObjectAttribuutLijst();
		Collections.sort(attribuutlijst, new ByAttribuutID());
		web.setOverzichtEnIntrekkenSAV(session, "attribuutlijst", attribuutlijst);
		
		
		//Belangrijke data
		web.setOverzichtEnIntrekkenSAV(session, "timestampAanlevering", Converter.converteerGCnaarStringNL(terugmelding.getTijdstempelAanlevering()));	
		
		// ophalen van terugmelding details.
		TerugmeldingDetails terugmeldingDetails = null;
		try {
			terugmeldingDetails = getTmfCore().getOpvraagInvoker().ophalenMeldingDetails(meldingKenmerk);
		} catch (Exception ex) {
			return web.generateErrorPage(session, WebHandler.COMMUNICATION_ERROR_5); //connection error
		}
		
		if (terugmeldingDetails.getTijdstempelGemeld() != null) {
			web.setOverzichtEnIntrekkenSAV(session, "gemeldTijdstempel",Converter.converteerGCnaarStringNL(terugmeldingDetails.getTijdstempelGemeld()));
		} else {
			web.setOverzichtEnIntrekkenSAV(session, "gemeldTijdstempel", "");
		}
		
		web.setOverzichtEnIntrekkenSAV(session, "ontvangstTijdstempel",Converter.converteerGCnaarStringNL(terugmeldingDetails.getTijdstempelOntvangst()));
		web.setOverzichtEnIntrekkenSAV(session, "statusTijdstempel",Converter.converteerGCnaarStringNL(terugmeldingDetails.getTijdstempelStatus()));
		web.setOverzichtEnIntrekkenSAV(session, "statusTerugmelding",terugmeldingDetails.getStatusMelding());
		web.setOverzichtEnIntrekkenSAV(session, "toelichtingStatus",terugmeldingDetails.getToelichtingStatus());
		
		arg0.getSession().setAttribute("intrekbaar", Statussen.isIntrekbaar(terugmeldingDetails.getStatusMelding()));
		return new ModelAndView("/WEB-INF/jsp/overzicht_detail.jsp", "model", "nix");
	}
	
	/**
	 * Setter voor TMFCoreManager.
	 * 
	 * @param tmfCore De te zetten waarde voor de TMFCoreManager
	 */
	public void setTmfCore(TMFCoreManager tmfCore) {
		this.tmfCore = tmfCore;
	}

	/**
	 * Getter voor TMFCoreManager.
	 * 
	 * @return tmfCoreManager De huidige waarde van TMFCoreManager
	 */
	public TMFCoreManager getTmfCore() {
		return tmfCore;
	}
}


/**
 * De ByAttribuutID classe wordt gebruikt voor het sorteren van het overzicht.
 * 
 * @author OVSoftware
 */
class ByAttribuutID implements java.util.Comparator<ObjectAttribuut>, Serializable {

	private static final long serialVersionUID = 7601896287583900842L;

	/**
	 * Vergelijkt twee objecten (ObjectAttribuuten).
	 * 
	 * @param o1 Een ObjectAttribuut
	 * @param o2 Nog een ObjectAttribuut
	 * 
	 * @return Het resultaat van de compareTo vergelijking.
	 */
	@Override
	public int compare(ObjectAttribuut o1, ObjectAttribuut o2) {
		return o1.getIdAttribuut().compareTo(o2.getIdAttribuut());
	}
}
