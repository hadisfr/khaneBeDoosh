#!/usr/bin/env bash

rm -rf target
if [[ CATALINA_HOME ]]; then
    rm -rf $CATALINA_HOME/webapps/khaneBeDoosh*
fi
