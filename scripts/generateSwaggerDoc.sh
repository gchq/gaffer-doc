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

if [ ! -f ../../swagger-doc-config/rest-${gafferVersion}.war ]; then
    curl http://repo1.maven.org/maven2/uk/gov/gchq/gaffer/core-rest/${gafferVersion}/core-rest-${gafferVersion}.war -o ../../swagger-doc-config/rest-${gafferVersion}.war
fi
mv ../../swagger-doc-config/rest-${gafferVersion}.war .
tar -xf rest-${gafferVersion}.war
rm -rf META-INF WEB-INF
mv rest-${gafferVersion}.war ../../swagger-doc-config/

mkdir -p v2
mkdir -p latest
cp -r ../../swagger-doc-config/stub/* .
sed -i '' "s/\"basePath\": .*/\"basePath\": \"\/v2\",/g" v2/swagger.json
cp v2/swagger.json latest/

sed -i '' "s/supportedSubmitMethods: \['get', 'post', 'put', 'delete'\]/supportedSubmitMethods: \[\]/g" lib/gaffer.js
cat ../../swagger-doc-config/stub-requests.js >> lib/gaffer.js
