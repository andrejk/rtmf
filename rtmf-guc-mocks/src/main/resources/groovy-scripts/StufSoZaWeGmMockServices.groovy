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
import org.apache.log4j.Logger

def logger = Logger.getLogger("rtmfguc.script.StufSoZaWeGmMockService")

String NO_MATCH_FOUND = "No match found to return correct message in the StufSoZaWeGmMockService"
String result = NO_MATCH_FOUND

logger.debug "incoming payload: ${payload}"

if (payload =~ /bsn>78548718</) {
	logger.info "match bsn: 78548718"
	
result = """
<BG:prsLa01 xmlns:StUF="http://www.egem.nl/StUF/StUF0300" xmlns:BG="http://www.egem.nl/StUF/sector/bg/0300" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<BG:stuurgegevens>
   <BG:versieStUF>0300</BG:versieStUF>
   <BG:sectormodel>BG</BG:sectormodel>
   <BG:versieSectormodel>0300</BG:versieSectormodel>
   <BG:berichtcode>La01</BG:berichtcode>
   <BG:zender>
      <StUF:organisatie>Belasting</StUF:organisatie>
      <StUF:applicatie>AAA</StUF:applicatie>
      <StUF:administratie>a</StUF:administratie>
      <StUF:gebruiker>ONTWDDS</StUF:gebruiker>
   </BG:zender>
   <BG:ontvanger>
      <StUF:organisatie>Belasting</StUF:organisatie>
      <StUF:applicatie>AAA</StUF:applicatie>
      <StUF:administratie>a</StUF:administratie>
   </BG:ontvanger>
   <BG:referentienummer>10552170</BG:referentienummer>
   <BG:tijdstipBericht>2008082609544458</BG:tijdstipBericht>
   <BG:crossRefnummer>7000</BG:crossRefnummer>
   <BG:entiteittype>PRS</BG:entiteittype>
</BG:stuurgegevens>
<BG:body>
   <BG:parametersAntwoord>
      <StUF:indicatorVervolgvraag>false</StUF:indicatorVervolgvraag>
      <StUF:indicatorAfnemerIndicatie>false</StUF:indicatorAfnemerIndicatie>
   </BG:parametersAntwoord>
   <BG:object StUF:entiteittype="PRS" StUF:sleutelVerzendend="359488">
      <BG:a-nummer>3095146104</BG:a-nummer>
      <BG:bsn>78548718</BG:bsn>
      <BG:naamPrsGrp>
         <BG:voornamen>Antonia Wilhelmina</BG:voornamen>
         <BG:voorvoegselGeslachtsnaam>van den</BG:voorvoegselGeslachtsnaam>
         <BG:geslachtsnaam>Heuvel</BG:geslachtsnaam>
      </BG:naamPrsGrp>
      <BG:geboortedatum>19290917</BG:geboortedatum>
      <BG:geboorteplaatsGrp>
         <BG:geboorteplaats>899</BG:geboorteplaats>
         <BG:codeGeboorteland>6030</BG:codeGeboorteland>
      </BG:geboorteplaatsGrp>
      <BG:geslachtsaanduiding>V</BG:geslachtsaanduiding>
      <BG:datumOverlijden xsi:nil="true" StUF:noValue="geenWaarde" />
      <BG:indicatieGeheim>0</BG:indicatieGeheim>
      <BG:codeGemeenteVanInschrijving>599</BG:codeGemeenteVanInschrijving>
      <BG:datumInschrijvingGemeente>20080225</BG:datumInschrijvingGemeente>
      <BG:codeLandEmigratie xsi:nil="true" StUF:noValue="geenWaarde" />
      <BG:datumVertrekUitNederland xsi:nil="true" StUF:noValue="geenWaarde" />
      <BG:verblijfstitelGrp>
         <BG:verblijfstitel xsi:nil="true" StUF:noValue="geenWaarde" />
         <BG:datumVerkrijgingVerblijfstitel xsi:nil="true" StUF:noValue="geenWaarde" />
         <BG:datumVerliesVerblijfstitel xsi:nil="true" StUF:noValue="geenWaarde" />
      </BG:verblijfstitelGrp>
      <BG:aanduidingNaamgebruik>V</BG:aanduidingNaamgebruik>
      <BG:heeftAlsCorrespondentieAdres StUF:entiteittype="PRSADRCOR">
         <BG:gerelateerde StUF:entiteittype="ADR" StUF:noValue="geenWaarde" />
      </BG:heeftAlsCorrespondentieAdres>
      <BG:heeftAlsInschrijvingsAdres StUF:entiteittype="PRSADRINS">
         <BG:gerelateerde StUF:entiteittype="ADR" StUF:sleutelVerzendend="43421">
            <BG:adresBuitenlandGrp>
               <BG:adresBuitenland1 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:adresBuitenland2 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:adresBuitenland3 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:landcode xsi:nil="true" StUF:noValue="geenWaarde" />
            </BG:adresBuitenlandGrp>
            <BG:straatHuisnummerGrp>
               <BG:straatnaam>Bollandstraat</BG:straatnaam>
               <BG:huisnummer>61</BG:huisnummer>
               <BG:huisletter xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:huisnummertoevoeging xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:locatieomschrijving xsi:nil="true" StUF:noValue="geenWaarde" />
            </BG:straatHuisnummerGrp>
            <BG:postcode>3076CC</BG:postcode>
            <BG:woonplaatsnaam>Rotterdam</BG:woonplaatsnaam>
            <BG:ingangsdatum xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:gemeentecode>599</BG:gemeentecode>
         </BG:gerelateerde>
         <BG:tijdvakRelatie>
            <StUF:beginRelatie>20080225</StUF:beginRelatie>
            <StUF:eindRelatie xsi:nil="true" StUF:noValue="geenWaarde" />
         </BG:tijdvakRelatie>
      </BG:heeftAlsInschrijvingsAdres>
      <BG:heeftAlsVerblijfsAdres StUF:entiteittype="PRSADRVBL">
         <BG:gerelateerde StUF:entiteittype="ADR" StUF:sleutelVerzendend="43421">
            <BG:adresBuitenlandGrp>
               <BG:adresBuitenland1 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:adresBuitenland2 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:adresBuitenland3 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:landcode xsi:nil="true" StUF:noValue="geenWaarde" />
            </BG:adresBuitenlandGrp>
            <BG:straatHuisnummerGrp>
               <BG:straatnaam>Bollandstraat</BG:straatnaam>
               <BG:huisnummer>61</BG:huisnummer>
               <BG:huisletter xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:huisnummertoevoeging xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:locatieomschrijving xsi:nil="true" StUF:noValue="geenWaarde" />
            </BG:straatHuisnummerGrp>
            <BG:postcode>3076CC</BG:postcode>
            <BG:woonplaatsnaam>Rotterdam</BG:woonplaatsnaam>
            <BG:ingangsdatum xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:gemeentecode>599</BG:gemeentecode>
         </BG:gerelateerde>
         <BG:tijdvakRelatie>
            <StUF:beginRelatie>20080225</StUF:beginRelatie>
            <StUF:eindRelatie xsi:nil="true" StUF:noValue="geenWaarde" />
         </BG:tijdvakRelatie>
      </BG:heeftAlsVerblijfsAdres>
      <BG:heeftAlsNationaliteit StUF:entiteittype="PRSNAT">
         <BG:gerelateerde StUF:entiteittype="NAT">
            <BG:code>1</BG:code>
         </BG:gerelateerde>
         <BG:datumVerkrijging xsi:nil="true" StUF:noValue="waardeOnbekend" />
         <BG:datumVerlies xsi:nil="true" StUF:noValue="geenWaarde" />
      </BG:heeftAlsNationaliteit>
      <BG:isGetrouwdGeregistreerdPartnerMet StUF:entiteittype="PRSPRSHUW">
         <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="359489">
            <BG:a-nummer xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:bsn xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:naamPrsGrp>
               <BG:voornamen>Johan Coenraad</BG:voornamen>
               <BG:voorvoegselGeslachtsnaam xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:geslachtsnaam>Sollie</BG:geslachtsnaam>
            </BG:naamPrsGrp>
            <BG:geboortedatum>19250201</BG:geboortedatum>
            <BG:geboorteplaatsGrp>
               <BG:geboorteplaats>166</BG:geboorteplaats>
               <BG:codeGeboorteland>6030</BG:codeGeboorteland>
            </BG:geboorteplaatsGrp>
            <BG:geslachtsaanduiding>M</BG:geslachtsaanduiding>
         </BG:gerelateerde>
         <BG:datumSluiting>19530804</BG:datumSluiting>
         <BG:soortVerbintenis>H</BG:soortVerbintenis>
         <BG:datumOntbinding>19730322</BG:datumOntbinding>
      </BG:isGetrouwdGeregistreerdPartnerMet>
      <BG:heeftAlsKinderen StUF:entiteittype="PRSPRSKND">
         <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="359487">
            <BG:a-nummer>2983232195</BG:a-nummer>
            <BG:bsn>47902723</BG:bsn>
            <BG:naamPrsGrp>
               <BG:voornamen>Nicolaas Peter</BG:voornamen>
               <BG:voorvoegselGeslachtsnaam xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:geslachtsnaam>Sollie</BG:geslachtsnaam>
            </BG:naamPrsGrp>
            <BG:geboortedatum>19600524</BG:geboortedatum>
            <BG:geslachtsaanduiding>M</BG:geslachtsaanduiding>
         </BG:gerelateerde>
      </BG:heeftAlsKinderen>
      <BG:heeftAlsKinderen StUF:entiteittype="PRSPRSKND">
         <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="376315">
            <BG:a-nummer>7367636836</BG:a-nummer>
            <BG:bsn>114027754</BG:bsn>
            <BG:naamPrsGrp>
               <BG:voornamen>Johan Coenraad</BG:voornamen>
               <BG:voorvoegselGeslachtsnaam xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:geslachtsnaam>Sollie</BG:geslachtsnaam>
            </BG:naamPrsGrp>
            <BG:geboortedatum>19630201</BG:geboortedatum>
            <BG:geslachtsaanduiding>M</BG:geslachtsaanduiding>
         </BG:gerelateerde>
      </BG:heeftAlsKinderen>
      <BG:heeftAlsKinderen StUF:entiteittype="PRSPRSKND">
         <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="376314">
            <BG:a-nummer>9584389261</BG:a-nummer>
            <BG:bsn>91405439</BG:bsn>
            <BG:naamPrsGrp>
               <BG:voornamen>Jacques</BG:voornamen>
               <BG:voorvoegselGeslachtsnaam xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:geslachtsnaam>Sollie</BG:geslachtsnaam>
            </BG:naamPrsGrp>
            <BG:geboortedatum>19540424</BG:geboortedatum>
            <BG:geslachtsaanduiding>M</BG:geslachtsaanduiding>
         </BG:gerelateerde>
      </BG:heeftAlsKinderen>
      <BG:heeftAlsOuders StUF:entiteittype="PRSPRSOUD">
         <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="376313">
            <BG:a-nummer xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:bsn xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:naamPrsGrp>
               <BG:voornamen>Nicolaas</BG:voornamen>
               <BG:voorvoegselGeslachtsnaam>van den</BG:voorvoegselGeslachtsnaam>
               <BG:geslachtsnaam>Heuvel</BG:geslachtsnaam>
            </BG:naamPrsGrp>
            <BG:geboortedatum>18980210</BG:geboortedatum>
            <BG:geslachtsaanduiding>M</BG:geslachtsaanduiding>
         </BG:gerelateerde>
      </BG:heeftAlsOuders>
      <BG:heeftAlsOuders StUF:entiteittype="PRSPRSOUD">
         <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="376312">
            <BG:a-nummer xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:bsn xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:naamPrsGrp>
               <BG:voornamen>Clasina Adriana</BG:voornamen>
               <BG:voorvoegselGeslachtsnaam>van</BG:voorvoegselGeslachtsnaam>
               <BG:geslachtsnaam>Batenburg</BG:geslachtsnaam>
            </BG:naamPrsGrp>
            <BG:geboortedatum>19010628</BG:geboortedatum>
            <BG:geslachtsaanduiding>V</BG:geslachtsaanduiding>
         </BG:gerelateerde>
      </BG:heeftAlsOuders>
   </BG:object>
</BG:body>
</BG:prsLa01>
"""
}

if (payload =~ /bsn>126946036</) {
logger.info "match bsn: 126946036"

result = """<?xml version="1.0" encoding="UTF-8"?>
<BG:prsLa01 xmlns:BG="http://www.egem.nl/StUF/sector/bg/0300" xmlns:StUF="http://www.egem.nl/StUF/StUF0300" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <BG:stuurgegevens>
      <BG:versieStUF>0300</BG:versieStUF>
      <BG:sectormodel>BG</BG:sectormodel>
      <BG:versieSectormodel>0300</BG:versieSectormodel>
      <BG:berichtcode>La01</BG:berichtcode>
      <BG:zender>
         <StUF:organisatie>Belasting</StUF:organisatie>
         <StUF:applicatie>AAA</StUF:applicatie>
         <StUF:administratie>a</StUF:administratie>
         <StUF:gebruiker>ONTWDDS</StUF:gebruiker>
      </BG:zender>
      <BG:ontvanger>
         <StUF:organisatie>Belasting</StUF:organisatie>
         <StUF:applicatie>AAA</StUF:applicatie>
         <StUF:administratie>a</StUF:administratie>
      </BG:ontvanger>
      <BG:referentienummer>1249033417830</BG:referentienummer>
      <BG:tijdstipBericht>20090731114337830</BG:tijdstipBericht>
      <BG:crossRefnummer>7000</BG:crossRefnummer>
      <BG:entiteittype>PRS</BG:entiteittype>
   </BG:stuurgegevens>
   <BG:body>
      <BG:parametersAntwoord>
         <StUF:indicatorVervolgvraag>false</StUF:indicatorVervolgvraag>
         <StUF:indicatorAfnemerIndicatie>false</StUF:indicatorAfnemerIndicatie>
      </BG:parametersAntwoord>
      <BG:object StUF:entiteittype="PRS" StUF:sleutelVerzendend="9000000000000032509">
         <BG:a-nummer>1281434532</BG:a-nummer>
         <BG:bsn>126946036</BG:bsn>
         <BG:naamPrsGrp>
            <BG:voornamen>Everard</BG:voornamen>
            <BG:voorvoegselGeslachtsnaam StUF:noValue="geenWaarde" xsi:nil="true"/>
            <BG:geslachtsnaam>Daniëls</BG:geslachtsnaam>
         </BG:naamPrsGrp>
         <BG:geboortedatum>19381107</BG:geboortedatum>
         <BG:geboorteplaatsGrp>
            <BG:geboorteplaats>63</BG:geboorteplaats>
            <BG:codeGeboorteland>9030</BG:codeGeboorteland>
         </BG:geboorteplaatsGrp>
         <BG:geslachtsaanduiding>Man</BG:geslachtsaanduiding>
         <BG:datumOverlijden StUF:noValue="geenWaarde" xsi:nil="true"/>
         <BG:indicatieGeheim>Geen beperking</BG:indicatieGeheim>
         <BG:codeGemeenteVanInschrijving>599</BG:codeGemeenteVanInschrijving>
         <BG:datumInschrijvingGemeente>19650729</BG:datumInschrijvingGemeente>
         <BG:codeLandEmigratie StUF:noValue="geenWaarde" xsi:nil="true"/>
         <BG:datumVertrekUitNederland StUF:noValue="geenWaarde" xsi:nil="true"/>
         <BG:verblijfstitelGrp>
            <BG:verblijfstitel StUF:noValue="geenWaarde" xsi:nil="true"/>
            <BG:datumVerkrijgingVerblijfstitel StUF:noValue="geenWaarde" xsi:nil="true"/>
            <BG:datumVerliesVerblijfstitel StUF:noValue="geenWaarde" xsi:nil="true"/>
         </BG:verblijfstitelGrp>
         <BG:aanduidingNaamgebruik>Eigen geslachtsnaam</BG:aanduidingNaamgebruik>
         <BG:heeftAlsVerblijfsAdres StUF:entiteittype="PRSADRVBL">
            <BG:gerelateerde StUF:entiteittype="ADR" StUF:sleutelVerzendend="146232">
               <BG:adresBuitenlandGrp>
                  <BG:adresBuitenland1 StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:adresBuitenland2 StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:adresBuitenland3 StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:landcode StUF:noValue="geenWaarde" xsi:nil="true"/>
               </BG:adresBuitenlandGrp>
               <BG:straatHuisnummerGrp>
                  <BG:straatnaam>De Cordesstraat</BG:straatnaam>
                  <BG:huisnummer>145</BG:huisnummer>
                  <BG:huisletter StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:huisnummertoevoeging StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:locatieomschrijving StUF:noValue="geenWaarde" xsi:nil="true"/>
               </BG:straatHuisnummerGrp>
               <BG:postcode>3151BK</BG:postcode>
               <BG:woonplaatsnaam StUF:noValue="geenWaarde" xsi:nil="true"/>
               <BG:ingangsdatum StUF:noValue="geenWaarde" xsi:nil="true"/>
               <BG:gemeentecode>599</BG:gemeentecode>
            </BG:gerelateerde>
         </BG:heeftAlsVerblijfsAdres>
         <BG:heeftAlsNationaliteit StUF:entiteittype="PRSNAT">
            <BG:gerelateerde StUF:entiteittype="NAT">
               <BG:code>0001</BG:code>
            </BG:gerelateerde>
            <BG:datumVerkrijging StUF:noValue="geenWaarde" xsi:nil="true"/>
            <BG:datumVerlies StUF:noValue="geenWaarde" xsi:nil="true"/>
         </BG:heeftAlsNationaliteit>
         <BG:isGetrouwdGeregistreerdPartnerMet StUF:entiteittype="PRSPRSHUW">
            <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="925066">
               <BG:a-nummer>9328580531</BG:a-nummer>
               <BG:bsn>126798849</BG:bsn>
               <BG:naamPrsGrp>
                  <BG:voornamen>Alida Johanna Willempje</BG:voornamen>
                  <BG:voorvoegselGeslachtsnaam StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:geslachtsnaam>Koster</BG:geslachtsnaam>
               </BG:naamPrsGrp>
               <BG:geboorteplaatsGrp>
                  <BG:geboorteplaats StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:codeGeboorteland StUF:noValue="geenWaarde" xsi:nil="true"/>
               </BG:geboorteplaatsGrp>
               <BG:geboortedatum>19420426</BG:geboortedatum>
               <BG:geslachtsaanduiding>Vrouw</BG:geslachtsaanduiding>
            </BG:gerelateerde>
            <BG:datumSluiting>19630409</BG:datumSluiting>
            <BG:datumOntbinding StUF:noValue="geenWaarde" xsi:nil="true"/>
            <BG:soortVerbintenis>Huwelijk</BG:soortVerbintenis>
         </BG:isGetrouwdGeregistreerdPartnerMet>
         <BG:heeftAlsKinderen StUF:entiteittype="PRSPRSOUD">
            <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="1372665">
               <BG:a-nummer>5853741506</BG:a-nummer>
               <BG:bsn>126799015</BG:bsn>
               <BG:naamPrsGrp>
                  <BG:voornamen>Tanja Joanne</BG:voornamen>
                  <BG:voorvoegselGeslachtsnaam StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:geslachtsnaam>Daniëls</BG:geslachtsnaam>
               </BG:naamPrsGrp>
               <BG:geboortedatum>19670812</BG:geboortedatum>
               <BG:geslachtsaanduiding>Vrouw</BG:geslachtsaanduiding>
            </BG:gerelateerde>
         </BG:heeftAlsKinderen>
         <BG:heeftAlsKinderen StUF:entiteittype="PRSPRSOUD">
            <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="1656901">
               <BG:a-nummer>2108163219</BG:a-nummer>
               <BG:bsn>126799106</BG:bsn>
               <BG:naamPrsGrp>
                  <BG:voornamen>Robert Henry</BG:voornamen>
                  <BG:voorvoegselGeslachtsnaam StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:geslachtsnaam>Daniëls</BG:geslachtsnaam>
               </BG:naamPrsGrp>
               <BG:geboortedatum>19691005</BG:geboortedatum>
               <BG:geslachtsaanduiding>Man</BG:geslachtsaanduiding>
            </BG:gerelateerde>
         </BG:heeftAlsKinderen>
         <BG:heeftAlsKinderen StUF:entiteittype="PRSPRSOUD">
            <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="633550">
               <BG:a-nummer>7027079896</BG:a-nummer>
               <BG:bsn>126798928</BG:bsn>
               <BG:naamPrsGrp>
                  <BG:voornamen>Marcel Jules</BG:voornamen>
                  <BG:voorvoegselGeslachtsnaam StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:geslachtsnaam>Daniëls</BG:geslachtsnaam>
               </BG:naamPrsGrp>
               <BG:geboortedatum>19640705</BG:geboortedatum>
               <BG:geslachtsaanduiding>Man</BG:geslachtsaanduiding>
            </BG:gerelateerde>
         </BG:heeftAlsKinderen>
         <BG:heeftAlsOuders StUF:entiteittype="PRSPRSOUD">
            <BG:gerelateerde StUF:entiteittype="PRS">
               <BG:a-nummer StUF:noValue="geenWaarde" xsi:nil="true"/>
               <BG:bsn StUF:noValue="geenWaarde" xsi:nil="true"/>
               <BG:naamPrsGrp>
                  <BG:voornamen>Jules Eugène</BG:voornamen>
                  <BG:voorvoegselGeslachtsnaam StUF:noValue="geenWaarde" xsi:nil="true"/>
                  <BG:geslachtsnaam>Daniëls</BG:geslachtsnaam>
               </BG:naamPrsGrp>
               <BG:geboortedatum>19041030</BG:geboortedatum>
               <BG:geslachtsaanduiding>Man</BG:geslachtsaanduiding>
            </BG:gerelateerde>
         </BG:heeftAlsOuders>
         <BG:heeftAlsOuders StUF:entiteittype="PRSPRSOUD">
            <BG:gerelateerde StUF:entiteittype="PRS">
               <BG:a-nummer StUF:noValue="geenWaarde" xsi:nil="true"/>
               <BG:bsn StUF:noValue="geenWaarde" xsi:nil="true"/>
               <BG:naamPrsGrp>
                  <BG:voornamen>Karolina Antonia Theodora</BG:voornamen>
                  <BG:voorvoegselGeslachtsnaam>de</BG:voorvoegselGeslachtsnaam>
                  <BG:geslachtsnaam>Grood</BG:geslachtsnaam>
               </BG:naamPrsGrp>
               <BG:geboortedatum>19081027</BG:geboortedatum>
               <BG:geslachtsaanduiding>Vrouw</BG:geslachtsaanduiding>
            </BG:gerelateerde>
         </BG:heeftAlsOuders>
      </BG:object>
   </BG:body>
</BG:prsLa01>"""
}


if (payload =~ /bsn>199040382</) {
	logger.info "match bsn: 199040382"

result="""<BG:prsLa01 xmlns:StUF="http://www.egem.nl/StUF/StUF0300" xmlns:BG="http://www.egem.nl/StUF/sector/bg/0300" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<BG:stuurgegevens>
   <BG:versieStUF>0300</BG:versieStUF>
   <BG:sectormodel>BG</BG:sectormodel>
   <BG:versieSectormodel>0300</BG:versieSectormodel>
   <BG:berichtcode>La01</BG:berichtcode>
   <BG:zender>
      <StUF:organisatie>Belasting</StUF:organisatie>
      <StUF:applicatie>AAA</StUF:applicatie>
      <StUF:administratie>a</StUF:administratie>
      <StUF:gebruiker>ONTWDDS</StUF:gebruiker>
   </BG:zender>
   <BG:ontvanger>
      <StUF:organisatie>Belasting</StUF:organisatie>
      <StUF:applicatie>AAA</StUF:applicatie>
      <StUF:administratie>a</StUF:administratie>
   </BG:ontvanger>
   <BG:referentienummer>10552170</BG:referentienummer>
   <BG:tijdstipBericht>2008082609544458</BG:tijdstipBericht>
   <BG:crossRefnummer>7000</BG:crossRefnummer>
   <BG:entiteittype>PRS</BG:entiteittype>
</BG:stuurgegevens>
<BG:body>
   <BG:parametersAntwoord>
      <StUF:indicatorVervolgvraag>false</StUF:indicatorVervolgvraag>
      <StUF:indicatorAfnemerIndicatie>false</StUF:indicatorAfnemerIndicatie>
   </BG:parametersAntwoord>
   <BG:object StUF:entiteittype="PRS" StUF:sleutelVerzendend="2150848">
      <BG:a-nummer>2063193504</BG:a-nummer>
      <BG:bsn>199040382</BG:bsn>
      <BG:naamPrsGrp>
         <BG:voornamen>Mohamed</BG:voornamen>
         <BG:voorvoegselGeslachtsnaam xsi:nil="true" StUF:noValue="geenWaarde" />
         <BG:geslachtsnaam>Boufarra</BG:geslachtsnaam>
      </BG:naamPrsGrp>
      <BG:geboortedatum>19860720</BG:geboortedatum>
      <BG:geboorteplaatsGrp>
         <BG:geboorteplaats>546</BG:geboorteplaats>
         <BG:codeGeboorteland>6030</BG:codeGeboorteland>
      </BG:geboorteplaatsGrp>
      <BG:geslachtsaanduiding>M</BG:geslachtsaanduiding>
      <BG:datumOverlijden xsi:nil="true" StUF:noValue="geenWaarde" />
      <BG:indicatieGeheim>0</BG:indicatieGeheim>
      <BG:codeGemeenteVanInschrijving>599</BG:codeGemeenteVanInschrijving>
      <BG:datumInschrijvingGemeente>20080508</BG:datumInschrijvingGemeente>
      <BG:codeLandEmigratie xsi:nil="true" StUF:noValue="geenWaarde" />
      <BG:datumVertrekUitNederland xsi:nil="true" StUF:noValue="geenWaarde" />
      <BG:verblijfstitelGrp>
         <BG:verblijfstitel xsi:nil="true" StUF:noValue="geenWaarde" />
         <BG:datumVerkrijgingVerblijfstitel xsi:nil="true" StUF:noValue="geenWaarde" />
         <BG:datumVerliesVerblijfstitel xsi:nil="true" StUF:noValue="geenWaarde" />
      </BG:verblijfstitelGrp>
      <BG:aanduidingNaamgebruik>E</BG:aanduidingNaamgebruik>
      <BG:heeftAlsCorrespondentieAdres StUF:entiteittype="PRSADRCOR">
         <BG:gerelateerde StUF:entiteittype="ADR" StUF:noValue="geenWaarde" />
      </BG:heeftAlsCorrespondentieAdres>
      <BG:heeftAlsInschrijvingsAdres StUF:entiteittype="PRSADRINS">
         <BG:gerelateerde StUF:entiteittype="ADR" StUF:sleutelVerzendend="15453">
            <BG:adresBuitenlandGrp>
               <BG:adresBuitenland1 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:adresBuitenland2 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:adresBuitenland3 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:landcode xsi:nil="true" StUF:noValue="geenWaarde" />
            </BG:adresBuitenlandGrp>
            <BG:straatHuisnummerGrp>
               <BG:straatnaam>Alverstraat</BG:straatnaam>
               <BG:huisnummer>131</BG:huisnummer>
               <BG:huisletter xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:huisnummertoevoeging xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:locatieomschrijving xsi:nil="true" StUF:noValue="geenWaarde" />
            </BG:straatHuisnummerGrp>
            <BG:postcode>3192TM</BG:postcode>
            <BG:woonplaatsnaam>Hoogvliet Rotterdam</BG:woonplaatsnaam>
            <BG:ingangsdatum xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:gemeentecode>599</BG:gemeentecode>
         </BG:gerelateerde>
         <BG:tijdvakRelatie>
            <StUF:beginRelatie>20080508</StUF:beginRelatie>
            <StUF:eindRelatie xsi:nil="true" StUF:noValue="geenWaarde" />
         </BG:tijdvakRelatie>
      </BG:heeftAlsInschrijvingsAdres>
      <BG:heeftAlsVerblijfsAdres StUF:entiteittype="PRSADRVBL">
         <BG:gerelateerde StUF:entiteittype="ADR" StUF:sleutelVerzendend="15453">
            <BG:adresBuitenlandGrp>
               <BG:adresBuitenland1 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:adresBuitenland2 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:adresBuitenland3 xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:landcode xsi:nil="true" StUF:noValue="geenWaarde" />
            </BG:adresBuitenlandGrp>
            <BG:straatHuisnummerGrp>
               <BG:straatnaam>Alverstraat</BG:straatnaam>
               <BG:huisnummer>131</BG:huisnummer>
               <BG:huisletter xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:huisnummertoevoeging xsi:nil="true" StUF:noVelalue="geenWaarde" />
               <BG:locatieomschrijving xsi:nil="true" StUF:noValue="geenWaarde" />
            </BG:straatHuisnummerGrp>
            <BG:postcode>3192TM</BG:postcode>
            <BG:woonplaatsnaam>Hoogvliet Rotterdam</BG:woonplaatsnaam>
            <BG:ingangsdatum xsi:nil="true" StUF:noValue="waardeOnbekend" />
            <BG:gemeentecode>599</BG:gemeentecode>
         </BG:gerelateerde>
         <BG:tijdvakRelatie>
            <StUF:beginRelatie>20080508</StUF:beginRelatie>
            <StUF:eindRelatie xsi:nil="true" StUF:noValue="geenWaarde" />
         </BG:tijdvakRelatie>
      </BG:heeftAlsVerblijfsAdres>
      <BG:heeftAlsNationaliteit StUF:entiteittype="PRSNAT">
         <BG:gerelateerde StUF:entiteittype="NAT">
            <BG:code>131</BG:code>
         </BG:gerelateerde>
         <BG:datumVerkrijging xsi:nil="true" StUF:noValue="waardeOnbekend" />
         <BG:datumVerlies xsi:nil="true" StUF:noValue="geenWaarde" />
      </BG:heeftAlsNationaliteit>
      <BG:heeftAlsNationaliteit StUF:entiteittype="PRSNAT">
         <BG:gerelateerde StUF:entiteittype="NAT">
            <BG:code>1</BG:code>
         </BG:gerelateerde>
         <BG:datumVerkrijging xsi:nil="true" StUF:noValue="waardeOnbekend" />
         <BG:datumVerlies xsi:nil="true" StUF:noValue="geenWaarde" />
      </BG:heeftAlsNationaliteit>
      <BG:isGetrouwdGeregistreerdPartnerMet StUF:entiteittype="PRSPRSHUW">
         <BG:gerelateerde StUF:entiteittype="PRS" StUF:noValue="geenWaarde" />
      </BG:isGetrouwdGeregistreerdPartnerMet>
      <BG:heeftAlsOuders StUF:entiteittype="PRSPRSOUD">
         <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="1844783">
            <BG:a-nummer>5784806368</BG:a-nummer>
            <BG:bsn>22497432</BG:bsn>
            <BG:naamPrsGrp>
               <BG:voornamen>Aârab Mhamed</BG:voornamen>
               <BG:voorvoegselGeslachtsnaam xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:geslachtsnaam>Boufarra</BG:geslachtsnaam>
            </BG:naamPrsGrp>
            <BG:geboortedatum StUF:indOnvolledigeDatum="M">19470101</BG:geboortedatum>
            <BG:geslachtsaanduiding>M</BG:geslachtsaanduiding>
         </BG:gerelateerde>
      </BG:heeftAlsOuders>
      <BG:heeftAlsOuders StUF:entiteittype="PRSPRSOUD">
         <BG:gerelateerde StUF:entiteittype="PRS" StUF:sleutelVerzendend="1844782">
            <BG:a-nummer>2850124317</BG:a-nummer>
            <BG:bsn>175653197</BG:bsn>
            <BG:naamPrsGrp>
               <BG:voornamen>Malika</BG:voornamen>
               <BG:voorvoegselGeslachtsnaam xsi:nil="true" StUF:noValue="geenWaarde" />
               <BG:geslachtsnaam>Rrhioua</BG:geslachtsnaam>
            </BG:naamPrsGrp>
            <BG:geboortedatum StUF:indOnvolledigeDatum="M">19570101</BG:geboortedatum>
            <BG:geslachtsaanduiding>V</BG:geslachtsaanduiding>
         </BG:gerelateerde>
      </BG:heeftAlsOuders>
   </BG:object>
</BG:body>
</BG:prsLa01>"""
}


if (payload =~ /bsn>666</) {
	logger.info "match bsn: 666"
	
	result="""<foutBericht xmlns="http://www.egem.nl/StUF/StUF0300">
		   <stuurgegevens>
    <berichtsoort>Fo02</berichtsoort>
    <entiteittype>PRS</entiteittype>
    <sectormodel>BG</sectormodel>
    <versieStUF>0300</versieStUF>
    <versieSectormodel>0300</versieSectormodel>
    <zender>
       <applicatie>AAA</applicatie>
       <gebruiker>ONTWDDS</gebruiker>
    </zender>
    <ontvanger>
       <applicatie>AAA</applicatie>
    </ontvanger>
    <referentienummer>1257235360550</referentienummer>
    <tijdstipBericht>20091103090240</tijdstipBericht>
    <fout>
       <crossRefNummer>1256296678225</crossRefNummer>
    </fout>
 </stuurgegevens>
 <body>
    <code>StUF058</code>
    <plek>server</plek>
    <omschrijving>Failed to search PRS records from statement: null</omschrijving>
 </body>
</foutBericht>"""
}
if (payload =~ /bsn>600</) {
	logger.info "match bsn: 600"
	result="""Cannot bind to address "http://localhost:9090/gm/sozawa" No component registered on that endpoint"""
}

logger.debug "Result: ${result}"
assert result != NO_MATCH_FOUND
return result