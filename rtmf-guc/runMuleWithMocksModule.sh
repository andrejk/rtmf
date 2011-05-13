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
echo "mule_props=$MULE_PROPS"

export MULE_LIB=$RTMFGUC_PATH/../guc-generic/target/ojdbc14-10.2.0.4.0.jar
export MULE_LIB=$MULE_LIB:$RTMFGUC_PATH/../guc-generic/target/guc_algemeen_componenten-1.2.0.jar
export MULE_LIB=$MULE_LIB:$RTMFGUC_PATH/../guc-generic/target/guc-generic-1.0.jar
export MULE_LIB=$MULE_LIB:$RTMFGUC_PATH/target/classes
export MULE_LIB=$MULE_LIB:$RTMFGUC_PATH/../rtmf-guc-mocks/target/classes
export MULE_LIB=$MULE_LIB:$RTMFGUC_PATH/../guc-generic/target/classes
export MULE_LIB=$MULE_LIB:$HOME/.m2/repository/org/apache/httpcomponents/httpclient/4.0.1/httpclient-4.0.1.jar
export MULE_LIB=$MULE_LIB:$HOME/.m2/repository/org/apache/httpcomponents/httpcore/4.0.1/httpcore-4.0.1.jar
echo "mule_lib=$MULE_LIB"

echo exec "$MULE_BASE/bin/mule" -config $confpath -M-Dmule.props=$MULE_PROPS
exec "$MULE_BASE/bin/mule" -config $confpath -M-Dmule.props=$MULE_PROPS


