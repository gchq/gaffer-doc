#!/bin/bash
set -e

if [ -z "$1" ]; then
    echo "Usage: ./scripts/generateSwaggerDoc.sh <gaffer version>"
    exit 1
fi

gafferVersion=$1

rm -rf docs/swagger-doc
mkdir docs/swagger-doc
cd docs/swagger-doc

mv ../../swagger-doc-config/core-rest-${gafferVersion}.war .
jar xvf core-rest-${gafferVersion}.war
rm -rf META-INF WEB-INF
rm -rf core-rest-${gafferVersion}.war

mkdir -p v2
mkdir -p latest
cp -r ../../swagger-doc-config/stub/* .
set +e
sed -i "s/\"basePath\": .*/\"basePath\": \"\/v2\",/g" v2/swagger.json > /dev/null 2>&1
sed -i '' "s/\"basePath\": .*/\"basePath\": \"\/v2\",/g" v2/swagger.json > /dev/null 2>&1
set -e
cp v2/swagger.json latest/

set +e
sed -i "s/supportedSubmitMethods: \['get', 'post', 'put', 'delete'\]/supportedSubmitMethods: \[\]/g" lib/gaffer.js > /dev/null 2>&1
sed -i '' "s/supportedSubmitMethods: \['get', 'post', 'put', 'delete'\]/supportedSubmitMethods: \[\]/g" lib/gaffer.js > /dev/null 2>&1
set -e
cat ../../swagger-doc-config/stub-requests.js >> lib/gaffer.js
