/**
 * 
 */
package nl.rotterdam.rtmf.service;

import java.util.List;
import java.util.Map;

import nl.interaccess.zakenmagazijn.model.Zaak;
import nl.interaccess.zakenmagazijn.model.ZaakDetail;
import nl.rotterdam.rtmf.exception.ZMWebException;

/**
 * @author rweverwijk
 *
 */
public interface IZaakService {
	void statusBijwerken(ZaakDetail zaak, String nieuweStatus, String toelichting) throws ZMWebException;
	
	List<Zaak> getZaken(Map<String, Object> parameters);
	
	ZaakDetail getZaak(String zaakIdentificatie);
}
