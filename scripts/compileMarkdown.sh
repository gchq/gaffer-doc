#!/bin/bash


# Before running this script you will need to run:
# mvn clean install -Pquick

set -e

mkdir -p docs/getting-started
mkdir -p docs/components/core
mkdir -p docs/components/example
components="core/serialisation core/data core/operation core/store core/graph rest-api example/road-traffic example/federated-demo";
set +e
for component in $components; do
    echo "Fetching component: $component"
    curl https://raw.githubusercontent.com/gchq/Gaffer/master/$component/README.md -o docs/components/$component.md
    sed -i '' '/This page has been copied from/d' docs/components/$component.md > /dev/null 2>&1
    sed -i '/This page has been copied from/d' docs/components/$component.md > /dev/null 2>&1
done
set -e

mkdir -p docs/components/tool
components="ui python-shell deployment slider";
set +e
for component in $components; do
    echo "Fetching component: $component"
    curl https://raw.githubusercontent.com/gchq/gaffer-tools/master/$component/README.md -o docs/components/tool/$component.md
    sed -i '' '/This page has been copied from/d' docs/components/tool/$component.md > /dev/null 2>&1
    sed -i '/This page has been copied from/d' docs/components/tool/$component.md > /dev/null 2>&1
done
set -e

mkdir -p docs/stores
stores="accumulo-store hbase-store map-store proxy-store parquet-store federated-store";
set +e
for store in $stores; do
    echo "Fetching store: $store"
    curl https://raw.githubusercontent.com/gchq/Gaffer/master/store-implementation/$store/README.md -o docs/stores/$store.md
    sed -i '' '/The master copy of this page/d' docs/stores/$store.md > /dev/null 2>&1
    sed -i '/The master copy of this page/d' docs/stores/$store.md > /dev/null 2>&1
done
set -e

# Remove copyright headers
files=`grep -rl "Copyright 20" docs/components docs/stores`
for file in $files; do
    tail -n +14 $file > .tmpFile
    mv .tmpFile $file
done
files=`grep -rl "limitations under the License." docs/components docs/stores`
for file in $files; do
    tail -n +3 $file > .tmpFile
    mv .tmpFile $file
done