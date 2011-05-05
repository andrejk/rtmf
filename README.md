# RTMF #


RTMF staat voor Rotterdamse Terugmeld Faciliteit. RTMF is een koppeling tussen Digimelding en Digimelding gateway die ervoor zorgt dat gemeenten Digimelding kunnen gebruiken voor het terugmelding op de eigen gegevens.

## Licentie ##

This program is free software: you can redistribute it and/or modify it under the terms of the European Union Public Licence (EUPL), version 1.1 (or any later version). Full text: http://www.osor.eu/eupl/eupl-v1.1/nl/EUPL%20v.1.1%20-%20Licentie.pdf

## Ontwikkelen ##

### Voorbereiding ###

Voordat RTMF gecompileerd en getest kan worden dienen de volgende stappen uitgevoerd te worden:

* Installatie database schema tbv cachen berichten,
* Lokale configuratie aanpassen,
* Afhankelijkheden (libraries) installeren.
* Mule 2.2.1 installeren
* Tomcat 6 installeren

#### Database ####
--------

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

#### Configuratie ####

RTMF maakt gebruik van een aantal configuratie bestanden:

* rtmfguc_env.properties - configuratie voor RTMF Mule applicatie
* tmfportal.properties - configuratie voor TMF Portal (Digimelding)

Voorbeelden van deze bestanden zijn te vinden in rtmf-guc/src/test/resources. Voor ontwikkeldoeleinden kan het best gebruik gemaakt worden van een configuratie naar mock services. Je kunt deze in je $HOME directory plaatsen.

#### Libraries ####

RTMF heeft een aantal afhankelijkheden nodig die door maven niet uit publieke repositories gedownload kan worden. Je zult deze zelf in een lokale repository of in je lokale cache moeten zetten:

* TMFPortal - opensource software te downloaden bij Logius: http://www.logius.nl/producten/gegevensuitwisseling/digimelding/
* subethasmtp - http://code.google.com/p/subethasmtp/downloads/list
* oracle jdbc - ojdbc14, versie 10.2.0.4.0

Een aantal afhankelijkheden zijn niet beschikbaar, de gebruiker van de code van RTMF dient hiervoor zelf een alternatief te realiseren:

* guc_algemeen_componenten - Rotterdam specifieke Mule ESB componenten voor oa logging.
* zm-client - Koppeling met Rotterdams zakenmagazijn.

Deze afhankelijkheden houden in dat dit project momenteel niet zonder aanpassingen gecompileerd kan worden.

### Build ###

Het RTMF project maakt gebruikt van maven 2. De build werkt nog niet met Maven 3.


#### Selenium ####

Voor het draaien van de integratie tests is een Selenium installatie noodzakelijk. Je kunt deze hier downloaden: http://seleniumhq.org/download/

Indien je alleen de selenium tests wilt uitvoeren kun je maven als volgt starten:
```
mvn -Pstandalone -Dtest=*UseCaseTest* integration-test
```


## Testen ##

### Alle services gemocked ###

* TMFPortal / Digimelding
  TMFPortal war file in webapps folder van je tomcat installatie plaatsen. Hernoemen naar ROOT.war (TMFPortal 1.2.5 draait alleen als root web applicatie).     Vervolgens tomcat starten:

  ```
  CATALINA_OPTS=-Dtmfportal.props=/tmp/tmfportal.properties ./startup.sh
  ```

* Mule starten
  ```
  cd rtmf/rtm-guc
  EXPORT MULE_HOME=$HOME/programs/mule-standalone-2.2.1
  runMuleWithMocksModule.sh
  ```

### Met lokale services ###

## Opleveren ##
