/**
 * 
 */
package nl.rotterdam.rtmf.form.helper;

import java.util.ArrayList;
import java.util.List;

import nl.interaccess.zakenmagazijn.model.Stap;
import nl.interaccess.zakenmagazijn.model.ZaakDetail;
import nl.rotterdam.rtmf.form.pojo.Attribuut;

import org.apache.commons.lang.StringUtils;
import org.apache.xerces.dom.ElementNSImpl;
import org.apache.xerces.dom.TextImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author rweverwijk
 *
 */
public class ZaakDetailReadHelper extends ZaakReadHelper{

	/**
	 * @param zaak 
	 * @return
	 */
	public static String getToelichting(ZaakDetail zaak) {
		return getDubbellaagsElement(zaak, "terugmelding", "toelichting");
	}

	/**
	 * @param zaak
	 * @return
	 */
	public static String getStatusToelichting(ZaakDetail zaak) {
		return StringUtils.isNotBlank(zaak.getResultaattoelichting()) ? zaak.getResultaattoelichting() : "&nbsp;";
	}
	
	/**
	 * @param zaak
	 * @return
	 */
	public static String getDatumLaatsteStap(ZaakDetail zaak) {
		List<Stap> stappen = zaak.getStap();
		Stap laatsteStap = null;
		for (Stap stap : stappen) {
			if (laatsteStap == null) {
				laatsteStap = stap;
			} else if (stap.getStapvolgnummer().compareTo(laatsteStap.getStapvolgnummer()) > 0 && stap.getStapeinddatum() != null){
				laatsteStap = stap;
			}
		}
		if (laatsteStap != null && laatsteStap.getStapeinddatum() != null) {
			return String.format("%d-%d-%d", laatsteStap.getStapeinddatum().getDay(), laatsteStap.getStapeinddatum().getMonth(), laatsteStap.getStapeinddatum().getYear());
		} else if (laatsteStap != null && laatsteStap.getBegindatum() != null){
			return String.format("%d-%d-%d", laatsteStap.getBegindatum().getDay(), laatsteStap.getBegindatum().getMonth(), laatsteStap.getBegindatum().getYear());
		}
		return null;
	}

	/**
	 * @param zaak
	 * @return
	 */
	public static String getContactNaam(ZaakDetail zaak) {
		return getContactGegeven(zaak, "naam");		
	}

	

	/**
	 * @param zaak
	 * @return
	 */
	public static String getContactTelefoon(ZaakDetail zaak) {
		return getContactGegeven(zaak, "telefoon");
	}

	/**
	 * @param zaak
	 * @return
	 */
	public static String getContactEmail(ZaakDetail zaak) {
		return getContactGegeven(zaak, "email");
	}
	
	public static List<Attribuut> getAttributen(ZaakDetail zaak) {
		List<Attribuut> result = new ArrayList<Attribuut>();
		ElementNSImpl any = (ElementNSImpl) zaak.getFormulier().getAny();
		NodeList attributen = any.getElementsByTagNameNS("*", "attributen");
		for (int ai = 0; ai < attributen.getLength(); ai++) {
			Attribuut attribuut = new Attribuut();
			NodeList childNodes = attributen.item(ai).getChildNodes();
			for (int i =0; i < childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				if (item instanceof TextImpl) {
					
				} else if (item.getLocalName().equals("attribuutIdentificatie")) {
					attribuut.setId(item.getTextContent());
				} else if (item.getLocalName().equals("betwijfeldeWaarde")) {
					attribuut.setBetwijfeldeWaarde(item.getTextContent());
				} else if (item.getLocalName().equals("voorstel")) {
					attribuut.setVoorstel(item.getTextContent());
				}
			}
			result.add(attribuut);
		}
		return result;
	}
	
	/**
	 * @param zaak
	 */
	private static String getContactGegeven(ZaakDetail zaak, String gegeven) {
		return getDubbellaagsElement(zaak, "contactInfo", gegeven);
	}

	/**
	 * @param zaak
	 * @param gegeven
	 * @return
	 */
	private static String getDubbellaagsElement(ZaakDetail zaak, String parent, String gegeven) {
		ElementNSImpl any = (ElementNSImpl) zaak.getFormulier().getAny();
		NodeList elementsByTagName = any.getElementsByTagNameNS("*", parent);
		NodeList childNodes = elementsByTagName.item(0).getChildNodes();
		for (int i =0; i < childNodes.getLength(); i++) {
			Node item = childNodes.item(i);
			if (item.getLocalName().equals(gegeven)) {
				return item.getTextContent();
			}
		}
		return null;
	}	
}
