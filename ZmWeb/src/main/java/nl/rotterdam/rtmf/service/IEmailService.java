/**
 * 
 */
package nl.rotterdam.rtmf.service;

import nl.interaccess.zakenmagazijn.model.ZaakDetail;
import nl.rotterdam.rtmf.exception.ZMWebMailException;

/**
 * @author rweverwijk
 * Dit is een service om het mogelijk te maken om een email te versturen vanaf de applicatie
 */
public interface IEmailService {
	void emailNieuweStatus(ZaakDetail zaak, String nieuweStatus, String toelichting) throws ZMWebMailException;
}
