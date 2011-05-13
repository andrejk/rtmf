# RTMF #

RTMF staat voor Rotterdamse Terugmeld Faciliteit. RTMF is een koppeling tussen Digimelding en Digimelding gateway die ervoor zorgt dat gemeenten Digimelding kunnen gebruiken voor het terugmelding op de eigen gegevens.

## Licentie ##

This program is free software: you can redistribute it and/or modify it under the terms of the European Union Public Licence (EUPL), version 1.1 (or any later version). Full text: http://www.osor.eu/eupl/eupl-v1.1/nl/EUPL%20v.1.1%20-%20Licentie.pdf

## Project ##

Het RTMF project bestaat uit de volgende sub-projecten

* guc-generic - Generieke Mule voorzieningen van de gemeente rotterdam. Het RTMF project bevat slechts een subset nodig voor het ontwikkelen van de RTMF software, iedere gemeente dient hier zelf een echte oplossing te voorzien
* rtmf-guc-mocks - Mocks van de services waarmee RTMF praat: 

    * Gemeentelijk stelselcatalogus
    * Gemeentelijke gegevensmagazijn
    * Gemeentelijke zakenmagazijn
    * Landelijke stelselcatalogus
    * Landelijke TMF core (digimelding)

* rtmf-guc - RTMF component dat verbinding tussen alle onderdelen verzorgt mbv Mule ESB.
* rtmf-guc-db - Database objecten tbv cachen berichten in RTMF
* ZmWeb - Web interface dat door gemeentelijke bronhouder gebruikt kan worden voor terugmeldingen in te zien en van status te wijzigen.
* rtmf-package - Script voor maken oplevering

## Ontwikkelen ##

### Voorbereiding ###

Voordat RTMF gecompileerd en getest kan worden dienen de volgende stappen uitgevoerd te worden:

* Installatie database schema tbv cachen berichten,

  Voor het cachen van berichten wordt gebruik gemaakt van een Oracle Database. Tijdens het builden van de software dient er een Oracle database beschikbaar te zijn, ivm de unit tests.

  ```
  SQL> create user rtmfguc identified by rtmfguc;
  User created.
  SQL> grant connect, resource to rtmfguc;              
  Grant succeeded.
  > /rtmf-guc-db/src/main/sql$ sqlplus rtmfguc/rtmfguc@XE
  SQL*Plus: Release 10.2.0.1.0 - Production on Sun Mar 27 23:07:58 2011
  Copyright (c) 1982, 2005, Oracle.  All rights reserved.
  Connected to:
  Oracle Database 10g Express Edition Release 10.2.0.1.0 - Production
  SQL> @rtmfguc_deploy_all.sql
  ```

* Lokale configuratie aanpassen,
    RTMF maakt gebruik van een aantal configuratie bestanden:

    * rtmfguc_env.properties - configuratie voor RTMF Mule applicatie
    * tmfportal.properties - configuratie voor TMF Portal (Digimelding)

    Voorbeelden van deze bestanden zijn te vinden in rtmf-guc/src/test/resources. Voor ontwikkeldoeleinden kan het best gebruik gemaakt worden van een configuratie naar mock services. Je kunt deze in je $HOME directory plaatsen.

* Afhankelijkheden (libraries) installeren.

    RTMF heeft een aantal afhankelijkheden nodig die door maven niet uit publieke repositories gedownload kan worden. Je zult deze zelf in een lokale repository of in je lokale cache moeten zetten:

    * TMFPortal - opensource software te downloaden bij Logius: http://www.logius.nl/producten/gegevensuitwisseling/digimelding/
    * subethasmtp - http://code.google.com/p/subethasmtp/downloads/list
    * oracle jdbc - ojdbc14, versie 10.2.0.4.0

    Een aantal afhankelijkheden zijn niet beschikbaar, de gebruiker van de code van RTMF dient hiervoor zelf een alternatief te realiseren:

    * guc_algemeen_componenten - Rotterdam specifieke Mule ESB componenten voor oa logging.
    * zm-client - Koppeling met Rotterdams zakenmagazijn.

    Deze afhankelijkheden houden in dat dit project momenteel niet zonder aanpassingen gecompileerd kan worden.

* Mule 2.2.1 installeren
* Tomcat 6 of 7 installeren

### Build ###

Het RTMF project maakt gebruikt van maven 2. De build werkt nog niet met Maven 3.

Normale build gebeurt op de standaard maven manier:

```
mvn clean install
```

Een enkele unit test kan als volgt uitgevoerd worden:

```
mvn -Dtest=<naam van de test> test
```

Indien dit een integratie test is dient eerst selenium gestart te worden. Je kunt deze hier downloaden: http://seleniumhq.org/download/. Nu selenium starten met: ```java -jar selenium*.jar```. Vervolgens kun je de integratie tests draaien. Bijvoorbeeld:

```
.../selenium$ java -jar selenium-server-standalone-2.0b3.jar
.../apache-tomcat-6.0.32$ CATALINA_OPTS=-Dtmfportal.config=/tmp/tmfportal.properties bin/startup.sh
.../rtmf-guc$ mvn -o -Dtest=*TerugmeldingUseCaseTest* test
```

Het is ook mogelijk om selenium automatisch te laten starten door maven. Gebruik hiervoor het profiel standalone:

```
mvn -Pstandalone integration-test
```

## Testen ##

### Alle services gemocked ###

TMFPortal en RTMF lokaal opstarten, alle services (Gegevensmagazijn, OSB Gateway, Zakenmagazijn, Landelijk Stelselcatalogus, TMF-Core) worden gemocked.

Stappen:

* TMFPortal / Digimelding
  TMFPortal war file in webapps folder van je tomcat installatie plaatsen. Hernoemen naar ROOT.war (TMFPortal 1.2.5 draait alleen als root web applicatie). Vervolgens tomcat starten:

  ```
  CATALINA_OPTS=-Dtmfportal.config=/tmp/tmfportal.properties ./startup.sh
  ```

* Mule starten

  ```
  cd rtmf/rtm-guc
  EXPORT MULE_HOME=$HOME/programs/mule-standalone-2.2.1
  runMuleWithMocksModule.sh
  ```

* Oracle database starten
* Browser openen op http://localhost:8080/. Terugmelden op natuurlijk persoon met BSN 78548718 levert actuele waarden op.

### Met lokale services ###

## Documentatie ##

De maven pom van rtmf-guc bevat een goal om diagrammen te genereren die de mule configuraties visualiseren. Dit wordt gedaan mbv een groovy script dat de mule xml bestanden vertaald naar graphviz dot bestanden. Vervolgens wordt graphviz gebruikt om pdf en png bestanden te genereren.

De diagrammen kunnen ook handmatig gegenereerd worden:

```
groovy gen-mule-graphs.groovy
```

## Opleveren ##
