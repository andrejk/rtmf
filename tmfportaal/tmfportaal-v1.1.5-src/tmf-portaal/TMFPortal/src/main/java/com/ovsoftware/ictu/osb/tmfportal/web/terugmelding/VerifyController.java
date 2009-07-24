package com.ovsoftware.ictu.osb.tmfportal.web.terugmelding;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.StelselCatalogusManager;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectAttribuut;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectData;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ObjectAttribuut;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**
 * De VerifyController beheert de pagina waarbij de contactgegevens gecontoroleerd dienen te worden
 * Wanneer de data correct is ingevuld, wordt de volgende pagina geladen.
 * @author OVSoftware
 * 
 */
public class VerifyController implements Controller{
	private StelselCatalogusManager stelselCatalogus;
	private int aantalBijlagen;
	private int totalUploadSize;
	/**
	 * @param request Het http-request
	 * @param response De http-response
	 * 
	 * @return Retourneert super.handleRequest(request, response)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");
		
		// check if the webhandler exists
		if (web == null ) {
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		// session check
		if (!web.isTerugmeldingInSession()) {
			return web.generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		// error check fo the max file size of an attachement
		if(request.getParameter("fileError") !=null) {
			return web.generateErrorPage(session, WebHandler.MAX_FILE_SIZE_8);
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
				break;
			}
		}
				
		BRObjectData brObjectData = null;
		try{
			brObjectData =  getStelselCatalogus().getAttribuutInvoker().getBasisAttributen(tempBR, tempBRObject);
		} catch (Exception ex) {
			return web.generateErrorPage(session, WebHandler.COMMUNICATION_ERROR_5);
		}
			 
		Iterator<BRObjectAttribuut> j = brObjectData.getBroaLijst()	.iterator();
			
		BRObjectAttribuut bRObjAttr = null;
		ArrayList<ObjectAttribuut> objectAttribuutLijst = new ArrayList<ObjectAttribuut>();
		
		while (j.hasNext()) {
			bRObjAttr = j.next();
			
			ObjectAttribuut objectAttribuut = new ObjectAttribuut(bRObjAttr.getTag(),
																request.getParameter("old" + bRObjAttr.getTag()),
																request.getParameter("new" + bRObjAttr.getTag()),
																bRObjAttr.getNaam());

				
			if (!request.getParameter("old" + bRObjAttr.getTag()).equals(request.getParameter("new" + bRObjAttr.getTag()))){
				objectAttribuutLijst.add(objectAttribuut);
			}
		}
		
		if (objectAttribuutLijst.size() == 0) {
			return web.generateErrorPage(session, WebHandler.EMPTY_ATTRIBUTE_LIST_7); // 7 is niet genoeg wijzigingen
		}

		web.getTerugmelding().setObjectAttribuutLijst(objectAttribuutLijst);
		web.setTerugmeldingSAV(session, "aantalBijlagen", getAantalBijlagen());
		web.setTerugmeldingSAV(session,"totalUploadSize",getTotalUploadSize());
		
		session.setAttribute("WebHandler", web);
		session.setAttribute("emptyReason", false);
		session.setAttribute("lengthReason", false);
		session.setAttribute("emptyMeldingskenmerk",false);
		session.setAttribute("existMeldingskenmerk",false);
		session.setAttribute("maxfilesize", false);
		
		
		return new ModelAndView("/WEB-INF/jsp/terugmelding_Verify.jsp", "model","Step2");
	}

	/**
	 * Setter voor stelselCatalogus.
	 * 
	 * @param stelselCatalogus De nieuwe waarde van stelselCatalogus
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
	/**
	 * Setter voor aantalBijlagen.
	 * 
	 * @param aantalBijlagen het aantal bijlagen dat toegevoegd mag worden
	 */
	public void setAantalBijlagen(int aantalBijlagen) {
		this.aantalBijlagen = aantalBijlagen;
	}

	/**
	 * Getter voor aantalBijlagen.
	 *  
	 * @return De huidige waarde van aantalBijlagen
	 */
	public int getAantalBijlagen() {
		return aantalBijlagen;
	}
	
	/**
	 * Setter voor totalUploadSize.
	 * 
	 * @param totalUploadSize de totale max upload size
	 */
	public void setTotalUploadSize(int totalUploadSize) {
		this.totalUploadSize = totalUploadSize;
	}

	/**
	 * Getter voor totalUploadSize.
	 *  
	 * @return De huidige waarde van totalUploadSize
	 */
	public int getTotalUploadSize() {
		return totalUploadSize;
	}

}
