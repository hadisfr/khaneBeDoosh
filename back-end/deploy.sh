#!/usr/bin/env bash

rm -rf target src/main/webapp/doc
mkdir src/main/webapp/doc
apidoc -i src/main/java/ -o src/main/webapp/doc
mvn tomcat7:redeploy && echo -e "see \033[4mhttp://localhost:8080/khaneBeDoosh\033[0m"
pushd migrate > /dev/null
./create.sh
./push.sh
popd > /dev/null
