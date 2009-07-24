#!/bin/bash

# If MULE_BASE is not set, make it MULE_HOME
if [ -z "$MULE_BASE" ] ; then
  MULE_BASE="$MULE_HOME"
  export MULE_BASE
fi

export MULE_LIB=$MULE_LIB:./target/classes
export MULE_LIB=$MULE_LIB:../rtmf-guc-mocks/target/classes
export MULE_LIB=$MULE_LIB:$HOME/.m2/repository/nl/rotterdam/ioo/guc_algemeen/guc_algemeen_componenten/1.1.0/guc_algemeen_componenten-1.1.0.jar
export MULE_LIB=$MULE_LIB:../guc-generic/target/classes
export MULE_LIB=$MULE_LIB:$HOME/.m2/repository/hsqldb/hsqldb/1.8.0.10/hsqldb-1.8.0.10.jar
export MULE_LIB=$MULE_LIB:$HOME/.m2/repository/com/oracle/ojdbc14/10.2.0.4.0/ojdbc14-10.2.0.4.0.jar
export MULE_LIB=$MULE_LIB:$HOME/.m2/repository/org/apache/httpcomponents/httpclient/4.0.1/httpclient-4.0.1.jar
export MULE_LIB=$MULE_LIB:$HOME/.m2/repository/org/apache/httpcomponents/httpcore/4.0.1/httpcore-4.0.1.jar

export MULE_CONFIG=../guc-generic/target/classes/guc_generic_config.xml
export MULE_CONFIG=$MULE_CONFIG,target/classes/rtmfguc-config.xml
export MULE_CONFIG=$MULE_CONFIG,target/classes/rtmfguc-zm-config.xml
export MULE_CONFIG=$MULE_CONFIG,../rtmf-guc-mocks/target/classes/rtmfguc-mocks-config.xml
export MULE_CONFIG=$MULE_CONFIG,../rtmf-guc-mocks/target/classes/rtmfguc-mocks-zm-config.xml

export MULE_PROPS=classpath:guc_generic.properties
export MULE_PROPS=$MULE_PROPS,classpath:rtmfguc_default.properties
export MULE_PROPS=$MULE_PROPS,classpath:rtmfguc_env.properties
export MULE_PROPS=$MULE_PROPS,classpath:rtmfguc-mocks_default.properties
export MULE_PROPS=$MULE_PROPS,classpath:rtmfguc-mocks_env.properties
export MULE_PROPS=$MULE_PROPS,classpath:rtmfguc-mailsender.properties
export MULE_PROPS=$MULE_PROPS,$HOME/rtmfguc_env.properties

echo "MULE_LIBS=$MULE_LIB"
echo "MULE_CONFIG=$MULE_CONFIG"
echo "MULE_PROPS=$MULE_PROPS"

echo exec "$MULE_BASE/bin/mule" -config $MULE_CONFIG -M-Dmule.props=$MULE_PROPS
exec "$MULE_BASE/bin/mule" -config $MULE_CONFIG -M-Dmule.props=$MULE_PROPS

