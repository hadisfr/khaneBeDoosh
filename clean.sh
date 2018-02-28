#!/usr/bin/env bash

if [[ CATALINA_HOME ]]; then
    rm -rf $CATALINA_HOME/webapps/khaneBeDoosh*
fi
