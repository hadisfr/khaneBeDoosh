#!/usr/bin/env bash

rm -rf target src/main/webapp/doc
if [[ CATALINA_HOME ]]; then
    rm -rf $CATALINA_HOME/webapps/khaneBeDoosh*
fi
