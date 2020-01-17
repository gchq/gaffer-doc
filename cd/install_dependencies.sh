#!/usr/bin/env bash

set +e
# A quick command to see if the pom is resolvable
gaffer_version=`mvn help:evaluate -Dexpression=gaffer.version -q -DforceStdout`
return_value=$?
set -e
if [[ ${return_value} -ne 0 ]]; then
    echo "Building Koryphe from source"
    cd .. && git clone https://github.com/gchq/koryphe.git && cd koryphe && git checkout master && mvn clean install -Pquick -q
    echo "Building Gaffer from source"
    cd .. && git clone https://github.com/gchq/gaffer.git && cd gaffer && git checkout master && mvn clean install -Pquick -q
    echo "Building Tools from source"
    cd .. && git clone https://github.com/gchq/gaffer-tools.git && cd gaffer-tools && git checkout master && mvn clean install -Pquick -q
fi