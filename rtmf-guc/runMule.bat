@echo off
setlocal

call mvn compile -o
cd %~dp0

if "%MULE_BASE%" == "" SET MULE_BASE=%MULE_HOME%

SET MULE_LIB=%MULE_LIB%;".\target\classes";"../rtmf-guc-mocks/target/classes";"D:\iteye\mvnrepos\nl\rotterdam\ioo\guc_algemeen\guc_algemeen_componenten\1.1.0\guc_algemeen_componenten-1.1.0.jar";"../guc-generic/target/classes";"D:\iteye\mvnrepos\hsqldb\hsqldb\1.8.0.10\hsqldb-1.8.0.10.jar";"D:\iteye\mvnrepos\com\oracle\ojdbc14\10.2.0.4.0\ojdbc14-10.2.0.4.0.jar";"D:\iteye\mvnrepos\org\apache\httpcomponents\httpclient\4.0.1\httpclient-4.0.1.jar";"D:\iteye\mvnrepos\org\apache\httpcomponents\httpcore\4.0.1\httpcore-4.0.1.jar"

call "%MULE_BASE%\bin\mule.bat" -config "../guc-generic/target/classes/guc_generic_config.xml,target/classes/rtmfguc-config.xml,target/classes/rtmfguc-zm-config.xml,../rtmf-guc-mocks/target/classes/rtmfguc-mocks-config.xml,../rtmf-guc-mocks/target/classes/rtmfguc-mocks-zm-config.xml" -M-Dmule.props="classpath:guc_generic.properties,classpath:rtmfguc_default.properties,classpath:rtmfguc_env.properties,classpath:rtmfguc-mocks_default.properties,classpath:rtmfguc-mocks_env.properties,classpath:rtmfguc-mailsender.properties,C:\Documents and Settings\rweverwijk/rtmfguc_env.properties" 
