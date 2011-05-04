/**
 * 
 */
package nl.rotterdam.rtmf.form.helper;

import java.util.List;

import nl.interaccess.zakenmagazijn.model.KenmerkGrpType;
import nl.interaccess.zakenmagazijn.model.Zaak;
import nl.interaccess.zakenmagazijn.model.ZaakDetail;

/**
 * @author rweverwijk
 *
 */
public class ZaakReadHelper {
	public static String getDatumIngediend(Zaak zaak) {
		if (zaak != null) {
			return String.format("%d-%d-%d", zaak.getStartdatum().getDay(), zaak.getStartdatum().getMonth(), zaak.getStartdatum().getYear());
		}
		return null;
	}
	
	public static String getZaaknummer(Zaak zaak) {
		if (zaak != null) {
			return zaak.getZaakidentificatie();
		}
		return null;
	}
	
	public static String getStatus(Zaak zaak) {
		if (zaak != null) {
			int indexOf = SelectOption.statusList.indexOf(new SelectOption(zaak.getResultaatcode(), ""));
			if (indexOf != -1) {
				return ((SelectOption)SelectOption.statusList.get(indexOf)).getValue();
			}
			return "Onbekende status: " + zaak.getResultaatcode();
		}
		return null;
	}
	
	public static String getMeldingskenmerk(Zaak zaak) {
		List<String> trefwoord = zaak.getTrefwoord();
		if (trefwoord != null && !trefwoord.isEmpty()) {
			return trefwoord.get(0);
		}
		return null;
	}
	
	/**
	 * @param zaak
	 * @return
	 */
	public static Object getObjectTag(ZaakDetail zaak) {
		return getKenmerkByName(zaak, "objectTag");
	}
	
	public static String getObjectNaam(Zaak zaak) {
		return getKenmerkByName(zaak, "objectNaam");
	}
	
	public static String getObjectIdentificatie(Zaak zaak) {
		return getKenmerkByName(zaak, "objectIdentificatie");
	}
	
	public static String getBasisRegistratie(Zaak zaak) {
		return getKenmerkByName(zaak, "basisRegistratie");
	}
	
	private static String getKenmerkByName(Zaak zaak, String name) {
		if (zaak != null) {
			List<KenmerkGrpType> kenmerken = zaak.getKenmerk();
			for (KenmerkGrpType kenmerk : kenmerken) {
				if (kenmerk.getKenmerkBron().equals(name)) {
					return kenmerk.getKenmerk();
				}
			}
		}
		return null;
	}
}
