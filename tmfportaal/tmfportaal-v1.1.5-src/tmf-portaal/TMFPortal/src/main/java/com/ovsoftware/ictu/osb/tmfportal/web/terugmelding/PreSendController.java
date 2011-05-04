package com.ovsoftware.ictu.osb.tmfportal.web.terugmelding;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.StelselCatalogusManager;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TMFCoreManager;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Bijlage;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ContactGegevens;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.web.WebHandler;

/**

 * @author OVSoftware
 * 
 */
public class PreSendController implements Controller {
	private static final String FILE_EXTENSION = ".pdf";
	private static final byte[] FILE_HEADER = "%PDF".getBytes();
	
	private StelselCatalogusManager stelselCatalogus;
	private TMFCoreManager tmfCore;

	/**
	 * 
	 * @param request Het http-request
	 * @param response De http-response
	 * 
	 * @return Een ModelAndView met 
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) {
			
		HttpSession session = request.getSession();
		WebHandler web = (WebHandler) session.getAttribute("WebHandler");
		boolean error = false;
		if (web == null ) {
			return new WebHandler().generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		if (!web.isTerugmeldingInSession()) {
			return web.generateErrorPage(session, WebHandler.SESSION_EXPIRED_0);
		}
		session.setAttribute("emptyReason", false);
		session.setAttribute("lengthReason", false);
		session.setAttribute("emptyMeldingskenmerk",false);
		session.setAttribute("existMeldingskenmerk",false);
		session.setAttribute("maxfilesize", false);
		session.setAttribute("filenameerror", false);
		session.setAttribute("filecontenterror", false);
		
		if (request.getParameter("reason").equals("")) {
			session.setAttribute("emptyReason", true);
			error = true;
		}
		if (request.getParameter("reason").length() > 255) {
			session.setAttribute("lengthReason", true);
			error = true;
		}
		if (request.getParameter("meldingkenmerk").equals("")){
			session.setAttribute("emptyMeldingskenmerk", true);
			error = true;
		}
		String mKenmerk = request.getParameter("meldingkenmerk");
		// check if meldingkenmerk allready exists.
		if (mKenmerk != null && !mKenmerk.equals("")){ 
			ArrayList<Terugmelding> terugmeldingen = new ArrayList<Terugmelding>();

			try {
				terugmeldingen = tmfCore.getOpvraagInvoker().ophalenMeldingen(null, null, null, mKenmerk, null);
			} catch (Exception ex) {
				return web.generateErrorPage(session, WebHandler.COMMUNICATION_ERROR_5); //5 is communicatie fout
			}
			
			if (terugmeldingen.size()>0){
				session.setAttribute("existMeldingskenmerk", true);
				error = true;
			}
		}
		
		int aantalBijlagen =(Integer)web.getTerugmeldingSAV(session,"aantalBijlagen");
		String fileName ="";
		byte[] fileContent = null;
		long totalFileSize = 0;
		long maxFileSize = Long.parseLong(web.getTerugmeldingSAV(session,"totalUploadSize")+"");
		
		ArrayList<Bijlage> bijlageLijst = new ArrayList<Bijlage>();
		
		if (!error) {
			for (int i = 0 ; i < aantalBijlagen ; i++) {
				try {
					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
					MultipartFile multipart = multipartRequest.getFile("file"+i);
					fileName = multipart.getOriginalFilename();
					fileContent = multipart.getBytes();
					totalFileSize += multipart.getSize();
				} catch (Exception e) {
					return web.generateErrorPage(session, WebHandler.FILE_ERROR_6); // file error
				}
				if (!fileName.equals("") || !fileName.trim().equals("")){
					if (!fileName.toLowerCase().endsWith(FILE_EXTENSION)) {
						session.setAttribute("filenameerror", true);
						error = true;
					} else if (!isFileContentValid(fileContent)) {
						session.setAttribute("filecontenterror", true);
						error = true;
					}
					
					Bijlage b = new Bijlage(fileContent, fileName);
					bijlageLijst.add(b);
				}
			}

			if (totalFileSize > maxFileSize) { 
				session.setAttribute("maxfilesize", true);
				error = true;
			} 
		}
		web.getTerugmelding().setMeldingKenmerk(request.getParameter("meldingkenmerk"));
		web.getTerugmelding().setToelichting(request.getParameter("reason"));
				
		ContactGegevens contactGegevens = new ContactGegevens(request.getParameter("name"),request.getParameter("phonenumber"),request.getParameter("email"));
		
		web.getTerugmelding().setBijlageLijst(bijlageLijst);
		web.getTerugmelding().setContactGegevens(contactGegevens);
		
		if(error) {			
			return new ModelAndView("/WEB-INF/jsp/terugmelding_Verify.jsp");
		}
		session.setAttribute("emptyReason", false);
		session.setAttribute("lengthReason", false);
		session.setAttribute("emptyMeldingskenmerk",false);
		session.setAttribute("existMeldingskenmerk",false);
		session.setAttribute("maxfilesize", false);
		session.setAttribute("filenameerror", false);
		session.setAttribute("filecontenterror", false);
		return new ModelAndView ("/WEB-INF/jsp/terugmelding_Send.jsp");
	}
	
	private boolean isFileContentValid(byte[] fileContent) {
		if (fileContent == null || fileContent.length < FILE_HEADER.length) {
			return false;
		}

		for (int i = 0; i < FILE_HEADER.length; i++) {
			if (fileContent[i] != FILE_HEADER[i]) {
				return false;
			}
		}

		return true;
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
