#!/usr/bin/bash
#connect sys/manager@//localhost/XE as sysdba
#@@src/main/sql/dba/create_rtmfguc.sql
sqlplus /nolog <<EOS
connect rtmfguc/rtmfguc@//localhost/XE
@@src/main/sql/rtmfguc_deploy_all.sql
EOS

