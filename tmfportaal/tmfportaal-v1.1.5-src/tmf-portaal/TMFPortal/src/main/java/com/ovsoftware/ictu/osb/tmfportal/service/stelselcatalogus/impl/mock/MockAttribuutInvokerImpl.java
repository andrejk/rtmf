package com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.impl.mock;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.AttribuutInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectAttribuut;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectData;
import com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie;

/**
 * Mock implementatie van de AttribuutInvoker. De attributen voor een
 * bepaalde basisregistratie en object worden geladen uit een resource-
 * bestand (xml). Daarnaast is het ook mogelijk om waardes te laten uit
 * een resource-bestand (xml).
 * 
 * @author ktinselboer
 *
 */
public class MockAttribuutInvokerImpl implements AttribuutInvoker {

	private Logger logger = Logger.getLogger(MockAttribuutInvokerImpl.class);
	
	/**
	 * 
	 * Haalt een lijst met attributen op voor een bepaalde basisregistratie
	 * en object. Deze lijst en wat metadata mbt het object worden geretourneerd
	 * in de vorm van een BRObjectData-object. LET OP: Zonder huidige waardes.
	 * 
	 * @param basisRegistratie De basisregistratie waarvoor de attributen opgehaald moeten worden
	 * @param basisRegistratieObject Het object waarvoor de attributen opgehaald moeten worden
	 * 
	 * @return Een BRObjectData-object met informatie over het object en een lijst met BRObjectAttribuut-objecten
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BRObjectData getBasisAttributen(BasisRegistratie basisRegistratie,
															BRObject basisRegistratieObject) {
		
		ApplicationContext ac = new ClassPathXmlApplicationContext(basisRegistratie.getTag()+"-"+basisRegistratieObject.getTag()+"-attributen.xml");
		Map<String, BRObjectAttribuut> attributen = ac.getBeansOfType(BRObjectAttribuut.class);
		
		BRObjectData brod = new BRObjectData(new ArrayList<BRObjectAttribuut>(attributen.values()),
											heeftObjectInstantieBestand(basisRegistratie, basisRegistratieObject),
											"Instructie",
											basisRegistratieObject.getNaam(),
											basisRegistratieObject.getTag());
		
		return brod;
	}

	/**
	 * 
	 * Haalt een lijst met attributen op voor een bepaalde basisregistratie
	 * en object. Deze lijst en wat metadata mbt het object worden geretourneerd
	 * in de vorm van een BRObjectData-object. LET OP: Met huidige waardes.
	 * 
	 * @param basisRegistratie De basisregistratie waarvoor de attributen opgehaald moeten worden
	 * @param basisRegistratieObject Het object waarvoor de attributen opgehaald moeten worden
	 * @param objectKey Het id voor het object, bijv BSN-nummer.
	 * 
	 * @return Een BRObjectData-object met informatie over het object en een lijst met BRObjectAttribuut-objecten
	 */
	@Override
	public BRObjectData getBasisAttributenValues(BasisRegistratie basisRegistratie,
												  BRObject basisRegistratieObject,
												  String objectKey) {
		BRObjectData brod =  null;
		try {
			ApplicationContext ac = new ClassPathXmlApplicationContext(basisRegistratie.getTag()+"-"+basisRegistratieObject.getTag()+"-attributen-"+objectKey+".xml");
			brod = (BRObjectData) ac.getBean("brod");
		} catch (BeansException e){
			return null;
		}
				
		return brod;
	}
	
	/**
	 * Checkt of er een objectinstantie-bestand bestaat in de map "src/main/resources" van
	 * het formaat "tagBR-tagBRO-attributen-*.xml".
	 * 
	 * @param br De basisregistratie
	 * @param bro Het basisregistratie-object
	 * 
	 * @return True indien opvraagbaar, anders false
	 */
	//checkt of er een objectinstantie-bestand bestaat in de map "src/main/resources" van
	//het formaat "tagBR-tagBRO-attributen-*.xml"
	private boolean heeftObjectInstantieBestand(BasisRegistratie br, BRObject bro) {
		//runtime map (indien gedeployed)
		File file = new File("WEB-INF/classes");
		
		if (!file.exists()) {
			//map bestaat niet in package...gebruik test map
			file = new File("src/test/resources");
			
			if (!file.exists()) { 
				logger.error("Kon zowel de map 'WEB-INF/classes' als 'src/test/resources' niet vinden in heeftObjectInstantieBestand!");
				return false;
			}
		}
		
		BevraagbaarFilter bf = new BevraagbaarFilter(br.getTag(), bro.getTag());		
		if (file.list(bf) == null) { return false; }
		return (file.list(bf).length != 0);
	}
	
	//inner class welke gebruikt wordt om de lijst met bestanden te filteren
	class BevraagbaarFilter implements FilenameFilter {

		private String tagBR;
		private String tagBRO;
		
		/**
		 * Constructor voor het aanmaken van een BevraagbaarFilter.
		 * 
		 * @param tagBR De basisregistratie tag die moet terugkomen in de bestandsnaam
		 * @param tagBRO De tag van het basisregistratieobject die moet terugkomen in de bestandsnaam
		 */
		public BevraagbaarFilter(String tagBR, String tagBRO) {
			this.tagBR = tagBR;
			this.tagBRO = tagBRO;
		}
		
		/**
		 * Retourneert of een bestandsnaam voldoet aan het filter. Dat wil zeggen of deze van het
		 * formaat "tagBR-tagBRO-attributen-*.xml".
		 * 
		 * @param dir De map die doorzocht wordt
		 * @param name De naam van het bestand in die map
		 * 
		 * @return boolean True indien de bestandsnaam door het filter komt, anders False.
		 */
		@Override
		public boolean accept(File dir, String name) {
			return (name.startsWith(tagBR + "-" + tagBRO + "-attributen-") && name.endsWith(".xml"));
		}
				
	}

}
