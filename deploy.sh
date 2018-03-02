#!/usr/bin/env bash

rm -rf target
mvn tomcat7:redeploy && echo -e "see \033[4mhttp://localhost:8080/khaneBeDoosh\033[0m"
