#!/bin/bash

# If MULE_BASE is not set, make it MULE_HOME
if [ -z "$MULE_BASE" ] ; then
  MULE_BASE="$MULE_HOME"
  export MULE_BASE
fi

confpath=""; 

SCRIPT_PATH=`readlink -f $0`
RTMFGUC_PATH=`dirname $SCRIPT_PATH`
for file in `ls $RTMFGUC_PATH/../guc-generic/src/main/resources/*.xml`; do confpath=$confpath,$file; done; 
for file in `ls $RTMFGUC_PATH/../rtmf-guc-mocks/src/main/resources/*.xml`; do confpath=$confpath,$file; done; 
for file in `ls $RTMFGUC_PATH/../rtmf-guc/src/main/resources/*.xml | grep -ivE "(test|mock)" `; do confpath=$confpath,$file; done;
echo "confpath=$confpath"

MULE_PROPS=classpath:guc_generic.properties,classpath:rtmfguc_default.properties,classpath:rtmfguc_env.properties,classpath:rtmfguc-mocks_default.properties,classpath:rtmfguc-mocks_env.properties,classpath:rtmfguc-mailsender.properties,file:$HOME/rtmfguc_env.properties

#export MULE_LIB=$MULE_LIB:$RTMFGUC_PATH/../guc-generic/target/ojdbc14-10.2.0.4.0.jar
#export MULE_LIB=$MULE_LIB:$RTMFGUC_PATH/../guc-generic/target/guc_algemene_componenten-1.2.0.jar
#export MULE_LIB=$MULE_LIB:$RTMFGUC_PATH/../guc-generic/target/guc-generic-1.0.jar

echo "mule_props=$MULE_PROPS"
export MULE_LIB=$MULE_LIB:$RTMFGUC_PATH/../target/classes
echo "mule_lib=$MULE_LIB"
exec "$MULE_BASE/bin/mule" -config $confpath -Dmule.props=$MULE_PROPS


