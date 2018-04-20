#!/bin/sh

sudo chown $(whoami):admin khaneBeDoosh.db
cp khaneBeDoosh.db $CATALINA_HOME/webapps/khaneBeDoosh/WEB-INF/khaneBeDoosh.db
