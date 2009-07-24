#!/bin/bash

# If MULE_BASE is not set, make it MULE_HOME
if [ -z "$MULE_BASE" ] ; then
  MULE_BASE="$MULE_HOME"
  export MULE_BASE
fi

export MULE_LIB=$MULE_LIB:./target/classes
exec "$MULE_BASE/bin/mule" -config ./target/classes/mule-config.xml,./target/classes/mule-config-mocks.xml

