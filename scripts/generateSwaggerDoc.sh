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
tar -xf core-rest-${gafferVersion}.war
rm -rf META-INF WEB-INF
rm -rf core-rest-${gafferVersion}.war

mkdir -p v2
mkdir -p latest
cp -r ../../swagger-doc-config/stub/* .
sed -i '' "s/\"basePath\": .*/\"basePath\": \"\/v2\",/g" v2/swagger.json
cp v2/swagger.json latest/

sed -i '' "s/supportedSubmitMethods: \['get', 'post', 'put', 'delete'\]/supportedSubmitMethods: \[\]/g" lib/gaffer.js
cat ../../swagger-doc-config/stub-requests.js >> lib/gaffer.js
