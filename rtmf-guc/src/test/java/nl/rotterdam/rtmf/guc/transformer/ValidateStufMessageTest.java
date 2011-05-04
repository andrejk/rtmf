/*
 * Copyright (c) 2009-2011 Gemeente Rotterdam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the European Union Public Licence (EUPL),
 * version 1.1 (or any later version).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * European Union Public Licence for more details.
 *
 * You should have received a copy of the European Union Public Licence
 * along with this program. If not, see
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
*/
package nl.rotterdam.rtmf.guc.transformer;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.tck.FunctionalTestCase;

/**
 * @author tarem
 * 
 */
public class ValidateStufMessageTest extends FunctionalTestCase {
	private static String validStufMessage = "<BG:prsLa01 xmlns:BG=\"http://www.egem.nl/StUF/sector/bg/0300\" xmlns:StUF=\"http://www.egem.nl/StUF/StUF0300\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n"
			+ "   <BG:stuurgegevens>\r\n"
			+ "      <BG:versieStUF>0300</BG:versieStUF>\r\n"
			+ "      <BG:sectormodel>BG</BG:sectormodel>\r\n"
			+ "      <BG:versieSectormodel>0300</BG:versieSectormodel>\r\n"
			+ "      <BG:berichtcode>La01</BG:berichtcode>\r\n"
			+ "      <BG:zender>\r\n"
			+ "         <StUF:organisatie>Belasting</StUF:organisatie>\r\n"
			+ "         <StUF:applicatie>AAA</StUF:applicatie>\r\n"
			+ "         <StUF:administratie>a</StUF:administratie>\r\n"
			+ "         <StUF:gebruiker>ONTWDDS</StUF:gebruiker>\r\n"
			+ "      </BG:zender>\r\n"
			+ "      <BG:ontvanger>\r\n"
			+ "         <StUF:organisatie>Belasting</StUF:organisatie>\r\n"
			+ "         <StUF:applicatie>AAA</StUF:applicatie>\r\n"
			+ "         <StUF:administratie>a</StUF:administratie>\r\n"
			+ "      </BG:ontvanger>\r\n"
			+ "      <BG:referentienummer>1257349229351</BG:referentienummer>\r\n"
			+ "      <BG:tijdstipBericht>20091104164029351</BG:tijdstipBericht>\r\n"
			+ "      <BG:crossRefnummer>7000</BG:crossRefnummer>\r\n"
			+ "      <BG:entiteittype>PRS</BG:entiteittype>\r\n"
			+ "   </BG:stuurgegevens>\r\n"
			+ "   <BG:body>\r\n"
			+ "      <BG:parametersAntwoord>\r\n"
			+ "         <StUF:indicatorVervolgvraag>false</StUF:indicatorVervolgvraag>\r\n"
			+ "         <StUF:indicatorAfnemerIndicatie>false</StUF:indicatorAfnemerIndicatie>\r\n"
			+ "      </BG:parametersAntwoord>\r\n"
			+ "      <BG:object StUF:entiteittype=\"PRS\" StUF:sleutelVerzendend=\"9000000000000310046\">\r\n"
			+ "         <BG:a-nummer>4962794725</BG:a-nummer>\r\n"
			+ "         <BG:bsn>075771548</BG:bsn>\r\n"
			+ "         <BG:naamPrsGrp>\r\n"
			+ "            <BG:voornamen>Marije</BG:voornamen>\r\n"
			+ "            <BG:geslachtsnaam>Schansman</BG:geslachtsnaam>\r\n"
			+ "         </BG:naamPrsGrp>\r\n"
			+ "         <BG:geboortedatum>19810629</BG:geboortedatum>\r\n"
			+ "         <BG:geboorteplaatsGrp></BG:geboorteplaatsGrp>\r\n"
			+ "         <BG:geslachtsaanduiding>V</BG:geslachtsaanduiding>\r\n"
			+ "         <BG:heeftAlsInschrijvingsAdres StUF:entiteittype=\"PRSADRINS\">\r\n"
			+ "            <BG:gerelateerde StUF:entiteittype=\"ADR\" StUF:sleutelVerzendend=\"410046\">\r\n"
			+ "               <BG:straatHuisnummerGrp>\r\n"
			+ "                  <BG:straatnaam>Wollefoppenweg</BG:straatnaam>\r\n"
			+ "                  <BG:huisnummer>91</BG:huisnummer>\r\n"
			+ "                  <BG:huisletter>C</BG:huisletter>\r\n"
			+ "                  <BG:huisnummertoevoeging StUF:noValue=\"geenWaarde\" xsi:nil=\"true\"/>\r\n"
			+ "               </BG:straatHuisnummerGrp>\r\n"
			+ "               <BG:postcode>3069LX</BG:postcode>\r\n"
			+ "               <BG:woonplaatsnaam>Rotterdam</BG:woonplaatsnaam>\r\n"
			+ "               <BG:gemeentecode>599</BG:gemeentecode>\r\n"
			+ "            </BG:gerelateerde>\r\n"
			+ "         </BG:heeftAlsInschrijvingsAdres>\r\n"
			+ "         <BG:heeftAlsVerblijfsAdres StUF:entiteittype=\"PRSADRVBL\">\r\n"
			+ "            <BG:gerelateerde StUF:entiteittype=\"ADR\" StUF:sleutelVerzendend=\"510046\">\r\n"
			+ "               <BG:straatHuisnummerGrp>\r\n"
			+ "                  <BG:straatnaam>Wollefoppenweg</BG:straatnaam>\r\n"
			+ "                  <BG:huisnummer>91</BG:huisnummer>\r\n"
			+ "                  <BG:huisletter>C</BG:huisletter>\r\n"
			+ "                  <BG:huisnummertoevoeging StUF:noValue=\"geenWaarde\" xsi:nil=\"true\"/>\r\n"
			+ "               </BG:straatHuisnummerGrp>\r\n"
			+ "               <BG:postcode>3069LX</BG:postcode>\r\n"
			+ "               <BG:woonplaatsnaam>Rotterdam</BG:woonplaatsnaam>\r\n"
			+ "               <BG:gemeentecode>599</BG:gemeentecode>\r\n"
			+ "            </BG:gerelateerde>\r\n"
			+ "         </BG:heeftAlsVerblijfsAdres>\r\n"
			+ "      </BG:object>\r\n" + "   </BG:body>\r\n" + "</BG:prsLa01>";

	private static String validStufFoutMessage = "<foutBericht xmlns=\"http://www.egem.nl/StUF/StUF0300\">\r\n"
			+ "		   <stuurgegevens>\r\n"
			+ "    <berichtsoort>Fo02</berichtsoort>\r\n"
			+ "    <entiteittype>PRS</entiteittype>\r\n"
			+ "    <sectormodel>BG</sectormodel>\r\n"
			+ "    <versieStUF>0300</versieStUF>\r\n"
			+ "    <versieSectormodel>0300</versieSectormodel>\r\n"
			+ "    <zender>\r\n"
			+ "       <applicatie>AAA</applicatie>\r\n"
			+ "       <gebruiker>ONTWDDS</gebruiker>\r\n"
			+ "    </zender>\r\n"
			+ "    <ontvanger>\r\n"
			+ "       <applicatie>AAA</applicatie>\r\n"
			+ "    </ontvanger>\r\n"
			+ "    <referentienummer>1257235360550</referentienummer>\r\n"
			+ "    <tijdstipBericht>20091103090240</tijdstipBericht>\r\n"
			+ "    <fout>\r\n"
			+ "       <crossRefNummer>1256296678225</crossRefNummer>\r\n"
			+ "    </fout>\r\n"
			+ " </stuurgegevens>\r\n"
			+ " <body>\r\n"
			+ "    <code>StUF058</code>\r\n"
			+ "    <plek>server</plek>\r\n"
			+ "    <omschrijving>Failed to search PRS records from statement: null</omschrijving>\r\n"
			+ " </body>\r\n" + "</foutBericht>";

	/**
	 * Test method for
	 * {@link nl.rotterdam.rtmf.guc.transformer.ValidateStufMessage#doValidate(org.mule.api.MuleMessage, java.lang.String)}
	 * .
	 */
	@Test
	public void testTransformMissingBodyElement() {
		ValidateStufMessage validateStufMessage = new ValidateStufMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(
				validStufMessage.replace("body", ""));

		try {
			validateStufMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			e.printStackTrace();
			assertTrue(e.getMessage().contains(
					"Geen body aanwezig StUF bericht"));
			assertTrue(e.getMessage().contains("<code>913</code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}

	@Test
	public void testTransformMissingStuurgegevensElement() {
		ValidateStufMessage validateStufMessage = new ValidateStufMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(
				validStufFoutMessage);

		try {
			validateStufMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			e.printStackTrace();
			assertTrue(e.getMessage().contains("StUF fout versie 300"));
			assertTrue(e.getMessage().contains("<code>911</code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}

	@Test
	public void testTransformStufFoutbericht() {
		ValidateStufMessage validateStufMessage = new ValidateStufMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(
				validStufMessage.replace("stuurgegevens", ""));

		try {
			validateStufMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			e.printStackTrace();
			assertTrue(e.getMessage().contains(
					"Geen stuurgegevens aanwezig in StUF bericht"));
			assertTrue(e.getMessage().contains("<code>912</code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}

	@Test
	public void testTransformValidStufMessage() {
		ValidateStufMessage validateStufMessage = new ValidateStufMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(
				validStufMessage);

		try {
			validateStufMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			fail("RtmfGucException not expected");
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}

		try {
			assertTrue(defaultMuleMessage.getPayloadAsString().contains(
					validStufMessage));
		} catch (Exception e) {
			fail("Exception not expected");
		}
	}

	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml";
	}

	@Override
	public void doSetUp() throws Exception {
		super.doSetUp();

		// zorg ervoor dat mule blijft draaien en niet bij elke methode opnieuw
		// opstart
		setDisposeManagerPerSuite(true);
	}

}
