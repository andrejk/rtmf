/**
 * 
 */
package nl.rotterdam.rtmf.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import junit.framework.TestCase;
import nl.interaccess.zakenmagazijn.model.ZaakDetail;
import nl.rotterdam.rtmf.exception.ZMWebMailException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.Wiser;

/**
 * @author rweverwijk
 * Test class voor het versturen van een nieuwe status via email
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"test-zmweb-config.xml"})
public class EmailServiceTest extends TestCase {
	private Wiser smtpServer;
	
	private static final String zaakDetailXML = "<ns2:ZaakDetailResponse xmlns:ns2=\"http://www.interaccess.nl/webplus/statuswfm_v2\">" + 
			"         <ns2:Subject/>" + 
			"         <ns2:Zaak_verantwoordelijke_oeh>" + 
			"            <ns2:oeh_id>pzr</ns2:oeh_id>" + 
			"            <ns2:oeh_naam>GBA</ns2:oeh_naam>" + 
			"         </ns2:Zaak_verantwoordelijke_oeh>" + 
			"         <ns2:Geen_zaak_initiator>true</ns2:Geen_zaak_initiator>" + 
			"         <ns2:Zaakidentificatie>TMD.09.11.00001</ns2:Zaakidentificatie>" + 
			"         <ns2:Startdatum>2009-11-24T11:38:15.000+01:00</ns2:Startdatum>" + 
			"         <ns2:Zaaktypecode>TMDG</ns2:Zaaktypecode>" + 
			"         <ns2:Zaaktypeomschrijving>Terugmelding Rotterdamse kerngegevens</ns2:Zaaktypeomschrijving>" + 
			"         <ns2:Einddatum xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"         <ns2:Einddatumgepland xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"         <ns2:Zaakomschrijving xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" +
			"         <ns2:Kenmerk>" + 
			"            <ns2:kenmerk>Natuurlijk Persoon</ns2:kenmerk>" + 
			"            <ns2:kenmerkBron>objectNaam</ns2:kenmerkBron>" + 
			"         </ns2:Kenmerk>" + 
			"         <ns2:Kenmerk>" + 
			"            <ns2:kenmerk>GBA</ns2:kenmerk>" + 
			"            <ns2:kenmerkBron>basisRegistratie</ns2:kenmerkBron>" + 
			"         </ns2:Kenmerk>" + 
			"         <ns2:Kenmerk>" + 
			"            <ns2:kenmerk>126946036</ns2:kenmerk>" + 
			"            <ns2:kenmerkBron>objectIdentificatie</ns2:kenmerkBron>" + 
			"         </ns2:Kenmerk>" + 
			"         <ns2:Kenmerk>" + 
			"            <ns2:kenmerk>01</ns2:kenmerk>" + 
			"            <ns2:kenmerkBron>objectTag</ns2:kenmerkBron>" + 
			"         </ns2:Kenmerk>" + 
			"		  <ns2:Trefwoord>JC-20091124-001</ns2:Trefwoord>" +
			"         <ns2:Resultaatcode>gemeld</ns2:Resultaatcode>" + 
			"         <ns2:Resultaatomschrijving>De terugmelding is gemeld aan de bronhouder</ns2:Resultaatomschrijving>" + 
			"         <ns2:Resultaattoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"         <ns2:Zaaktoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"         <ns2:Uiterlijkeeinddatumafdoening xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"         <ns2:Oge_id>0</ns2:Oge_id>" + 
			"         <ns2:Formulier>" + 
			"            <S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"               <S:Header>" + 
			"                  <To:To xmlns:To=\"http://www.w3.org/2005/08/addressing\" xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/rtmfguc/terugmeldService</To:To>" + 
			"                  <Action:Action xmlns:Action=\"http://www.w3.org/2005/08/addressing\" xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action:Action>" + 
			"                  <ReplyTo:ReplyTo xmlns:ReplyTo=\"http://www.w3.org/2005/08/addressing\" xmlns=\"http://www.w3.org/2005/08/addressing\">" + 
			"                     <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>" + 
			"                  </ReplyTo:ReplyTo>" + 
			"                  <MessageID:MessageID xmlns:MessageID=\"http://www.w3.org/2005/08/addressing\" xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:1d610c73-867c-4f61-b774-0c68d5538f7a</MessageID:MessageID>" + 
			"               </S:Header>" + 
			"               <S:Body>" + 
			"                  <ns2:terugmelding xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\">" + 
			"                     <ns2:meldingKenmerk>JC-20091124-001</ns2:meldingKenmerk>" + 
			"                     <ns2:tijdstempelAanlevering>2009-11-24T11:38:15.718+01:00</ns2:tijdstempelAanlevering>" + 
			"                     <ns2:basisRegistratie>GBA</ns2:basisRegistratie>" + 
			"                     <ns2:objectTag>01</ns2:objectTag>" + 
			"                     <ns2:objectIdentificatie>126946036</ns2:objectIdentificatie>" + 
			"                     <ns2:toelichting>Test met Rdamse bijnaam</ns2:toelichting>" + 
			"                     <ns2:attributen>" + 
			"                        <ns2:attribuutIdentificatie>01.86.11</ns2:attribuutIdentificatie>" + 
			"                        <ns2:betwijfeldeWaarde>Onbekend</ns2:betwijfeldeWaarde>" + 
			"                        <ns2:voorstel>Bertus</ns2:voorstel>" + 
			"                     </ns2:attributen>" +
			"                     <ns2:attributen>" + 
			"                        <ns2:attribuutIdentificatie>01.86.12</ns2:attribuutIdentificatie>" + 
			"                        <ns2:betwijfeldeWaarde>Niet goed</ns2:betwijfeldeWaarde>" + 
			"                        <ns2:voorstel>BétôrÇ!</ns2:voorstel>" + 
			"                     </ns2:attributen>" + 
			"                     <ns2:contactInfo>" + 
			"                        <ns2:naam>John Copier</ns2:naam>" + 
			"                        <ns2:telefoon>010-12345678</ns2:telefoon>" + 
			"                        <ns2:email>rtmfguc@localhost</ns2:email>" + 
			"                     </ns2:contactInfo>" + 
			"                  </ns2:terugmelding>" + 
			"               </S:Body>" + 
			"            </S:Envelope>" + 
			"         </ns2:Formulier>" + 
			"         <ns2:Status>" + 
			"            <ns2:Zaakidentificatie>TMD.09.11.00001</ns2:Zaakidentificatie>" + 
			"            <ns2:Geen_status_zetter>true</ns2:Geen_status_zetter>" + 
			"            <ns2:Datumstatusgezet>2009-11-24T11:38:15.000+01:00</ns2:Datumstatusgezet>" + 
			"            <ns2:Statuscode>ON</ns2:Statuscode>" + 
			"            <ns2:Statusomschrijving>Ontvangen</ns2:Statusomschrijving>" + 
			"            <ns2:Statustoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Statusvolgnummer>1</ns2:Statusvolgnummer>" + 
			"            <ns2:Oge_id>0</ns2:Oge_id>" + 
			"         </ns2:Status>" + 
			"         <ns2:Stap>" + 
			"            <ns2:Zaakidentificatie>TMD.09.11.00001</ns2:Zaakidentificatie>" + 
			"            <ns2:Geen_stap_uitvoerder>true</ns2:Geen_stap_uitvoerder>" + 
			"            <ns2:Geen_stap_verantwoordelijke>true</ns2:Geen_stap_verantwoordelijke>" + 
			"            <ns2:Begindatum>2009-11-24T11:38:15.000+01:00</ns2:Begindatum>" + 
			"            <ns2:Stapeinddatum xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Einddatumgepland xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Normdoorlooptijd xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Procedurecode xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Procedureomschrijving xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Rappeldatum xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Resultaatcode xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Resultaatomschrijving xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Resultaattoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Stapomschrijving>Het beoordelen van de terugmelding door de bronhouder</ns2:Stapomschrijving>" + 
			"            <ns2:Staptypecode>BEOORDELEN</ns2:Staptypecode>" + 
			"            <ns2:Stapvolgnummer>2</ns2:Stapvolgnummer>" + 
			"            <ns2:Staptoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Oge_id>0</ns2:Oge_id>" + 
			"         </ns2:Stap>" + 
			"         <ns2:Stap>" + 
			"            <ns2:Zaakidentificatie>TMD.09.11.00001</ns2:Zaakidentificatie>" + 
			"            <ns2:Geen_stap_uitvoerder>true</ns2:Geen_stap_uitvoerder>" + 
			"            <ns2:Geen_stap_verantwoordelijke>true</ns2:Geen_stap_verantwoordelijke>" + 
			"            <ns2:Begindatum>2009-11-24T11:38:15.000+01:00</ns2:Begindatum>" + 
			"            <ns2:Stapeinddatum>2009-11-24T11:38:20.000+01:00</ns2:Stapeinddatum>" + 
			"            <ns2:Einddatumgepland xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Normdoorlooptijd xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Procedurecode xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Procedureomschrijving xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Rappeldatum xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Resultaatcode>gemeld</ns2:Resultaatcode>" + 
			"            <ns2:Resultaatomschrijving>De terugmelding is gemeld aan de bronhouder</ns2:Resultaatomschrijving>" + 
			"            <ns2:Resultaattoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Stapomschrijving>Het ontvangen van de terugmelding door de bronhouder</ns2:Stapomschrijving>" + 
			"            <ns2:Staptypecode>ONTVANGEN</ns2:Staptypecode>" + 
			"            <ns2:Stapvolgnummer>1</ns2:Stapvolgnummer>" + 
			"            <ns2:Staptoelichting xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>" + 
			"            <ns2:Oge_id>0</ns2:Oge_id>" + 
			"         </ns2:Stap>" + 
			"      </ns2:ZaakDetailResponse>";
	@Autowired
	IEmailService emailService;
	/**
	 * Test method for {@link nl.rotterdam.rtmf.service.EmailService#emailNieuweStatus(nl.interaccess.zakenmagazijn.model.ZaakDetail, java.lang.String, java.lang.String)}.
	 * @throws UnsupportedEncodingException 
	 * @throws JAXBException 
	 * @throws ZMWebMailException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testEmailNieuweStatus() throws UnsupportedEncodingException, JAXBException, ZMWebMailException {
		InputStream is = new ByteArrayInputStream(zaakDetailXML.getBytes("UTF-8"));
		
		JAXBContext newInstance = JAXBContext.newInstance("nl.interaccess.zakenmagazijn.model");
		JAXBElement<ZaakDetail> unmarshal = (JAXBElement<ZaakDetail>) newInstance.createUnmarshaller().unmarshal(is);
		ZaakDetail detail = (ZaakDetail) unmarshal.getValue();
		assertNotNull(emailService);
		emailService.emailNieuweStatus(detail, "nieuwe test status", "nieuwe toelichting");
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		// start smtp server mock
		smtpServer = new Wiser();
		smtpServer.setPort(18089);
		smtpServer.start();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (null != smtpServer) {
			smtpServer.stop();
		}
		super.tearDown();
	}

}
