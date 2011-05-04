#!/bin/bash
if [[ -f TMFPortalWeb.war ]] ; then 
  rm -f TMFPortalWeb.war*
fi
if [[ -f ROOT.war ]] ; then 
  rm -f ROOT.war*
fi
jar cvf TMFPortalWeb.war -C TMFPortalWeb *
cp TMFPortalWeb.war ROOT.war
ls -ltra

