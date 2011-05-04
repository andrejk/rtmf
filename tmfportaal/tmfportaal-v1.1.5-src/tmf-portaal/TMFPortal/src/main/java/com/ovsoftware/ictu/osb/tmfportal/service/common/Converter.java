package com.ovsoftware.ictu.osb.tmfportal.service.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.AttribuutType;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.AttribuutValueType;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.BasisregistratieType;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.ObjectHeaderType;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.ObjectType;
import com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite.ObjectValueType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite.AttachmentType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite.ContactType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite.ObjectAttribuutType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.ObjectAttribuutTypeList;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.TerugmeldMetaCoreType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.TerugmeldMetaType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.TerugmeldResponseType;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.TerugmeldResponseTypeList;
import com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.TerugmeldType;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectAttribuut;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectData;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Bijlage;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ContactGegevens;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ObjectAttribuut;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingKern;

/**
 * Converter van en naar verschillende types. Er zijn in het totaal vijf
 * data-modellen te onderscheiden:
 * - Extern StelselCatalogus (= gegenereerde klassen obv WSDL, WUS -> WUSlite)
 * - Intern StelselCatalogus
 * - Intern TMFCore
 * - Extern TMFCore WUS (= gegenereerde klassen obv WSDL, WUS  -> WUSlite)
 * - Extern TMFCore EBMS(= gegenereerde klassen obv WSDL, EBMS -> WUSlite)
 * 
 * De data-modellen zitten alle in verschillende packages. Dit is vooral van
 * belang bij een ObjectAttribuutType-object waarvan er twee versies bestaan,
 * te weten "Extern TMFCore WUS" en "Extern TMFCore EBMS".
 * 
 * @author ktinselboer
 */
public class Converter {
	
	/**
	 * Nep constructor die voorkomt dat je een instantie kunt aanmaken
	 * van deze utility klasse.
	 * 
	 * @throws UnsupportedOperationException
	 */
	protected Converter() {
		throw new UnsupportedOperationException();
	}

	private static Logger logger = Logger.getLogger(Converter.class);
	
	/**
	 * Converteert een ObjectType naar een BRObjectData-object.
	 * 
	 * @param ot Het te converteren ObjectType-object.
	 * @return Een BRObjectData-object met daarin metadata over
	 *  het object en een lijst met attributen zonder huidige waarde.
	 */
	public static BRObjectData converteerOTnaarBROD(ObjectType ot) {
		if (ot == null) { return null; }
		
		ArrayList<BRObjectAttribuut> broaLijst = new ArrayList<BRObjectAttribuut>();
		for (AttribuutType at : ot.getAttributen()) {
			BRObjectAttribuut broa = new BRObjectAttribuut(at.getCode(),
														   at.getNaam(),
														   "");
			
			broaLijst.add(broa);
		}
		
		BRObjectData brod = new BRObjectData(broaLijst,
											 ot.getObjectHeaderInfo().isBevraagbaar(),
											 ot.getObjectHeaderInfo().getInstructie(),
											 ot.getObjectHeaderInfo().getNaam(),
											 ot.getObjectHeaderInfo().getTag());

		return brod;
	}
	
	/**
	 * Converteert een ObjectValueType naar een BRObjectData-object.
	 * 
	 * @param ovt Het te converteren ObjectValueType-object.
	 * @return Een BRObjectData-object met daarin metadata over het object
	 * en een lijst met attributen met huidige waarde.
	 */
	public static BRObjectData converteerOVTnaarBROD(ObjectValueType ovt) {
		if (ovt == null) { return null; }
		
		ArrayList<BRObjectAttribuut> broaLijst = new ArrayList<BRObjectAttribuut>();
		for (AttribuutValueType avt : ovt.getAttributeValues()) {
			BRObjectAttribuut broa = new BRObjectAttribuut(avt.getAttribuutInfo().getCode(),
														   avt.getAttribuutInfo().getNaam(),
														   avt.getValue());
			
			broaLijst.add(broa);
		}
		
		BRObjectData brod = new BRObjectData(broaLijst,
											 ovt.getObjectInfo().isBevraagbaar(),
											 ovt.getObjectInfo().getInstructie(),
											 ovt.getObjectInfo().getNaam(),
											 ovt.getObjectInfo().getTag());

		return brod;
	}
	
	/**
	 * Converteert een GregorianCalendar naar een String in NL-formaat.
	 * 
	 * @param gc Het te converteren GregorianCalendar-object
	 * @return De string in het formaat 'dd-MM-yyyy HH:mm:ss'
	 */
	public static String converteerGCnaarStringNL(GregorianCalendar gc) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String formattedDate = formatter.format(gc.getTime());
		return formattedDate;
	}
	
	/**
	 * Converteert een XmlGregorianCalendar naar een GregorianCalendar.
	 * 
	 * @param xgc Het te converteren XMLGregorianCalendar-object
	 * @return Het equivalente GregorianCalendar-object 
	 */
	public static GregorianCalendar converteerXGCnaarGC(XMLGregorianCalendar xgc) {
		if (xgc==null) { return null; }
		return xgc.toGregorianCalendar();
	}
	
	/**
	 * Converteert een GregorianCalendar naar een XmlGregorianCalendar.
	 * 
	 * @param gcal Het te converteren GregorianCalendar-object
	 * @return Het equivalente XMLGregorianCalendar-object
	 */
	public static XMLGregorianCalendar converteerGCnaarXGC(GregorianCalendar gcal){
		if (gcal==null) { return null; }
		
		DatatypeFactory dtf;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException dce) {
			logger.error("ERROR: DataTypeConfigurationException in converteerGCnaarXGC", dce);
			return null;
		}
		XMLGregorianCalendar xmlGC = dtf.newXMLGregorianCalendar(gcal);
		
		return xmlGC;
	}
	
	
	/**
	 * Converteert een TerugmeldMetaType naar een TerugmeldingDetails-object.
	 * 
	 * @param tmt Het TerugmeldMetaType-object dat geconverteerd dient te worden
	 * @return Het equivalente TerugmeldingDetails-object.
	 */
	public static TerugmeldingDetails converteerTMTnaarTerugmeldingDetails(TerugmeldMetaType tmt) {
		if (tmt==null) { return null; }
		
		TerugmeldMetaCoreType tmct = tmt.getTerugmeldMCore();
		TerugmeldingKern tk = new TerugmeldingKern(tmct.getTmfKenmerk(),
												   tmct.getBerichtSoort(),
												   tmct.getIdOrganisatie(),
												   tmct.getNaamOrganisatie());
		
		TerugmeldingDetails td = new TerugmeldingDetails(tk,
														 tmt.getAfdeling(),
														 converteerXGCnaarGC(tmt.getTijdstempelOntvangst()),
														 converteerXGCnaarGC(tmt.getTijdstempelGemeld()),
														 tmt.getStatusMelding(),
														 converteerXGCnaarGC(tmt.getTijdstempelStatus()),
														 tmt.getToelichting());
		return td;
	}
	
	/**
	 * Converteert een TerugmeldResponseTypeList naar een lijst met Terugmelding-objecten.
	 * 
	 * @param trtl Het te converteren TerugmeldResponseTypeList-object
	 * @return Een lijst met Terugmelding-objecten
	 */
	public static ArrayList<Terugmelding> converteerTRTLnaarTerugmeldingLijst(TerugmeldResponseTypeList trtl) {
		if (trtl==null) { return null; }
		
		//pak het argument uit
		List<TerugmeldResponseType> trtList = trtl.getTerugmeldResponseList();
		
		//bouw de lijst met terugmeldingen op
		ArrayList<Terugmelding> terugmeldingenLijst = new ArrayList<Terugmelding>();
		for (TerugmeldResponseType trt : trtList) {
			
			//haal de trt uit elkaar
			TerugmeldType tt = trt.getTerugmeld();
			TerugmeldMetaCoreType tmct = trt.getTerugmeldMCore();
			
			//creeer alle objecten die nodig zijn om een Terugmelding aan te maken
			ContactGegevens cg = new ContactGegevens(tt.getNaamContact(),
													 tt.getTelefoonContact(),
													 tt.getEmailContact());
			ArrayList<ObjectAttribuut> oaLijst = converteerOATLnaarObjectAttribuutLijst(tt.getObjectAttributen());
			TerugmeldingKern tk = new TerugmeldingKern(tmct.getTmfKenmerk(),
													   tmct.getBerichtSoort(),
													   tmct.getIdOrganisatie(),
													   tmct.getNaamOrganisatie());
			ArrayList<Bijlage> bLijst = new ArrayList<Bijlage>(); //een TRTL bevat geen bijlagen
			
			//maak de terugmelding aan
			Terugmelding t = new Terugmelding(tt.getMeldingKenmerk(),
											  cg,
											  converteerXGCnaarGC(tt.getTijdstempelAanlever()),
											  tt.getTagBR(),
											  tt.getTagObject(),
											  tt.getIdObject(),
											  tt.getToelichting(),
											  oaLijst,
											  tk,
											  bLijst,
											  tt.getStatus());
			
			//voeg de terugmelding toe aan de lijst
			terugmeldingenLijst.add(t);
		}
		
		//return de lijst met terugmelding-objecten
		return terugmeldingenLijst;
	}
	
	/**
	 * 
	 * Converteert een ObjectAttribuutTypeList naar een lijst met ObjectAttribuut-objecten.
	 * LET OP: De ObjectAttribuutType-objecten worden hier behandeld als de WUS-WUSlite versie
	 * ivm het feit dat ze uit een ObjectAttribuutTypeList (=WWL type) komen.
	 * 
	 * @param oatl Het te converteren ObjectAttribuutTypeList-object
	 * @return Een lijst met ObjectAttribuut (WUS-WUSlite versie!) objecten
	 */
	public static ArrayList<ObjectAttribuut> converteerOATLnaarObjectAttribuutLijst(ObjectAttribuutTypeList oatl) {
		if (oatl==null) { return null; }
		
		ArrayList<ObjectAttribuut> oaLijst = new ArrayList<ObjectAttribuut>();
		
		for (com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite.ObjectAttribuutType oat : oatl.getObjectAttribuutList()) {			
			ObjectAttribuut oa = new ObjectAttribuut(oat.getIdAttribuut(),
													 oat.getBetwijfeldeWaarde(),
													 oat.getVoorstel(),
													 null);
			oaLijst.add(oa);
		}
		
		return oaLijst;
	}
	
	/**
	 * Converteert een lijst van ObjectAttribuut-objecten naar een lijst van ObjectAttribuutType-objecten.
	 * LET OP: Het gaat hier om de EBMS-WUSlite versie van ObjectAttribuutType.
	 * 
	 * @param oaLijst Een lijst met ObjectAttribuut-objecten
	 * @return Een lijst met ObjectAttribuutType (EBMS-WUSlite versie!) objecten
	 */
	public static ArrayList<ObjectAttribuutType> converteerOALijstnaarObjectAttribuutTypeLijst(ArrayList<ObjectAttribuut> oaLijst) {
		if (oaLijst == null) { return null; }
		
		ArrayList<ObjectAttribuutType> oatLijst = new ArrayList<ObjectAttribuutType>();
		
		for (ObjectAttribuut oa : oaLijst) {
			ObjectAttribuutType oat = new ObjectAttribuutType();
			oat.setAttribuutIdentificatie(oa.getIdAttribuut());
			oat.setBetwijfeldeWaarde(oa.getBetwijfeldeWaarde());
			oat.setVoorstel(oa.getVoorstel());
			
			oatLijst.add(oat);
		}
		
		return oatLijst;
	}
	
	/**
	 * Converteert een ContactGegevens-object naar een ContactType-object.
	 * 
	 * @param cg Het te converteren ContactGegevens-object
	 * @return Het equivalente ContactType object
	 */
	public static ContactType converteerCGnaarCT(ContactGegevens cg) {
		if (cg == null) { return null; }
		
		ContactType ct = new ContactType();
		ct.setEmail(cg.getEmail());
		ct.setNaam(cg.getNaam());
		ct.setTelefoon(cg.getTelefoon());
		
		return ct;
	}
	
	/**
	 * Converteert een Bijlage-lijst naar een AttachmentType-lijst.
	 * 
	 * @param aLijst Een lijst met Bijlage-objecten
	 * @return Een lijst met AttachmentType-objecten
	 */
	public static ArrayList<AttachmentType> converteerALijstNaarATLijst(ArrayList<Bijlage> aLijst) {
		if (aLijst == null) { return null; }
		
		ArrayList<AttachmentType> atLijst = new ArrayList<AttachmentType>();
		
		for (Bijlage a : aLijst) {
			AttachmentType at = new AttachmentType();
			at.setBase64Attachment(a.getInhoud());
			at.setFilename(a.getBestandsnaam());
			
			atLijst.add(at);
		}
		
		return atLijst;
	}
	
	/**
	 * Converteert een String naar een GregorianCalendar.
	 * 
	 * @param datumtijd Een string in het formaat 'dd-MM-yyyy' welke een geldige datum representeert
	 * @return Een GregorianCalendar ingesteld op de meegegeven datum
	 */
	public static GregorianCalendar converteerKorteSTRINGnaarGC(String datumtijd) {
		int jaar = Integer.parseInt(datumtijd.substring(6, 10)); //GB = 0,4  en NL = 6,10
		int maand = Integer.parseInt(datumtijd.substring(3, 5)); //GB = 5,7  en NL = 3,5
		int dag = Integer.parseInt(datumtijd.substring(0, 2));   //GB = 8,10 en NL = 0,2

		//                                     ||
		//                                     ||
		//LET OP: Maand is zero-based!         \/
		return new GregorianCalendar(jaar,maand-1,dag);
	}
	
	/**
	 * Converteert een lijst van ObjectHeaderType-objecten naar een lijst van BRObject-objecten.
	 * 
	 * @param objectTypeList Een lijst met ObjectHeaderType-objecten
	 * @return Een lijst met BRObject objecten
	 */
	public static ArrayList<BRObject> converteerObjectHeaderTypeLijstnaarBRObjectLijst(List<ObjectHeaderType> objectTypeList) {
		if (objectTypeList == null) { return null; }
		
		ArrayList<BRObject> broLijst = new ArrayList<BRObject>();
		
		for (ObjectHeaderType oht: objectTypeList) {
			BRObject bro = new BRObject();
			bro.setNaam(oht.getNaam());
			bro.setTag(oht.getTag());
			
			broLijst.add(bro);
		}
		
		return broLijst;
	}


	/**
	 * Converteert een lijst van BasisregistratieType-objecten naar een lijst van BasisRegistratie-objecten.
	 * 
	 * @param basisregistratieList Een lijst met BasisregistratieType-objecten
	 * @return Een lijst met BasisRegistratie objecten
	 */
	public static ArrayList<BasisRegistratie> converteerBasisregistratieTypeLijstnaarBRLijst(List<BasisregistratieType> basisregistratieList) {
		if (basisregistratieList == null) { return null; }
		
		ArrayList<BasisRegistratie> brLijst = new ArrayList<BasisRegistratie>();
		
		for (BasisregistratieType brType: basisregistratieList) {
			BasisRegistratie br = new BasisRegistratie();
			br.setNaam(brType.getNaam());
			br.setTag(brType.getTag());
			
			brLijst.add(br);
		}
		
		return brLijst;
	}

}
