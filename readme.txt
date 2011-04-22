RTMF
====

RTMF staat voor Rotterdamse Terugmeld Faciliteit. RTMF is een koppeling tussen Digimelding en Digimelding gateway die ervoor zorgt dat gemeenten Digimelding ook kunnen gebruiken voor het terugmelding op de eigen gegevens.

Ontwikkelen
===========

RTMF heeft een aantal afhankelijkheden nodig die door maven niet uit publieke repositories gedownload kan worden. Je zult deze zelf in een lokale repository of in je lokale cache moeten zetten.

mvn install:install-file -Dfile=guc_algemeen_componenten-1.2.0.jar -DgroupId=nl.rotterdam.ioo.guc_algemeen -DartifactId=nl.rotterdam.ioo.guc_algemeen -Dversion=1.2.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=guc_algemeen_componenten-1.1.0.jar -DgroupId=nl.rotterdam.ioo.guc_algemeen -DartifactId=nl.rotterdam.ioo.guc_algemeen -Dversion=1.1.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=zm-client-0.9.jar -DgroupId=nl.rotterdam.ioo.zakenmagazijn -DartifactId=zm-client -Dversion=0.9 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -DgroupId=org.osb.tmf-portaal -DartifactId=TMFPortal -Dversion=1.2.5 -Dpackaging=war -Dfile=TMFPortalWeb.war
mvn install:install-file -DgroupId=org.subethamail -DartifactId=subethasmtp -Dversion=3.1.1 -Dpackaging=jar -Dfile=subethasmtp-3.1.1.jar

mvn clean install

Testen
======

Testen kan op verschillende manieren:
* alle afhankelijkheden mocken
* gebruik maken van draaiende services

De volgende componten dienen te draaien voor een test:
* Oracle database (voor cachen van berichten in de bus)
* TMF Portal (standaard tmf website)
* RTMF-GUC in Mule
* Eventuele mock services ook in Mule

Configuratie van de componenten:
* TMFPortal kun je lokaal configureren via $HOME/tmfport.properties
* Mule kun je lokaal configureren via $HOME/rtmfguc_env.properties

install rtmfguc-db
build rtmfguc

start tomcat 6.0
CATALINA_OPTS=-Dtmfportal.props=/tmp/tmfportal.properties ./startup.sh


Settings guc-generic

MULE_HOME=/home/akoelewijn/programs/mule-standalone-2.2.1/ ./runMule.sh 

Opleveren
=========

