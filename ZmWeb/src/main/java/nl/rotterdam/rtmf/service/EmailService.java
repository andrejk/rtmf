/**
 * 
 */
package nl.rotterdam.rtmf.service;

import java.io.StringWriter;
import java.util.Properties;

import nl.interaccess.zakenmagazijn.model.ZaakDetail;
import nl.rotterdam.rtmf.exception.ZMWebMailException;
import nl.rotterdam.rtmf.form.helper.ZaakDetailReadHelper;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rweverwijk
 *
 */
@Service
public class EmailService implements IEmailService{

	
	@Autowired
	private Properties properties;
	@Autowired
	private VelocityEngine velocity;
	/* (non-Javadoc)
	 * @see nl.rotterdam.rtmf.service.IEmailService#emailNieuweStatus(nl.interaccess.zakenmagazijn.model.ZaakDetail, java.lang.String, java.lang.String)
	 */
	public void emailNieuweStatus(ZaakDetail zaak, String nieuweStatus,
			String toelichting) throws ZMWebMailException {
		try {
			SimpleEmail email = new SimpleEmail();
			email.setHostName(properties.getProperty("rtmf.zmweb.smtp.server.host"));
			email.setSmtpPort(Integer.parseInt(properties.getProperty("rtmf.zmweb.smtp.server.port")));
			email.setFrom(properties.getProperty("rtmf.zmweb.email.from.address"), properties.getProperty("rtmf.zmweb.email.from.name"));
			String contactEmail = ZaakDetailReadHelper.getContactEmail(zaak);
			String contactNaam = ZaakDetailReadHelper.getContactNaam(zaak);
			try {
				email.addTo(contactEmail, contactNaam);
			} catch (EmailException e) {
				// in dit geval is het email adres welke door de gebruiker in de tmfPortal
				// ingevuld is niet juist of niet ingevuld. Omdat dit buiten ons bereik ligt
				// hebben we geen andere oplossing dan deze mail niet te versturen zonder foutmelding.
				return;
			}
			email.setSubject("Status wijziging terugmelding");
			email.setMsg(createBody(zaak, nieuweStatus, toelichting));
			email.send();
		} catch (Exception e) {
			throw new ZMWebMailException(e);
		}		
	}
	/**
	 * @param zaak
	 * @param nieuweStatus
	 * @param toelichting
	 * @return
	 * @throws ZMWebMailException 
	 */
	@SuppressWarnings("static-access")
	private String createBody(ZaakDetail zaak, String nieuweStatus,
			String toelichting) throws ZMWebMailException {

		try {
			Template template = velocity.getTemplate("nl/rotterdam/rtmf/service/emailTemplate.vm");

            VelocityContext context = new VelocityContext();
			context.put("meldingKenmerk", ZaakDetailReadHelper.getMeldingskenmerk(zaak));
			context.put("toelichting", ZaakDetailReadHelper.getToelichting(zaak));
			
			context.put("zaakidentificatie", ZaakDetailReadHelper.getZaaknummer(zaak));
			context.put("begindatum", ZaakDetailReadHelper.getDatumIngediend(zaak));
			
			context.put("basisRegistratie", ZaakDetailReadHelper.getBasisRegistratie(zaak));
			context.put("objectTag", ZaakDetailReadHelper.getObjectTag(zaak));
			context.put("objectNaam", ZaakDetailReadHelper.getObjectNaam(zaak));
			context.put("objectIdentificatie", ZaakDetailReadHelper.getObjectIdentificatie(zaak));
			
			context.put("nieuweStatus", nieuweStatus);
			context.put("nieuweStatusToelichting", toelichting);
			
			context.put("attributen", ZaakDetailReadHelper.getAttributen(zaak));
			context.put("adresMeerInfo", "www.nognietinpropertiefile.nl");
			StringWriter sw = new StringWriter();
			template.merge(context, sw);
			return sw.toString();
		} catch (Exception e) {
			throw new ZMWebMailException(e);
		}
	}
	
}
