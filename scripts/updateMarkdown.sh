#!/bin/bash
set -e

mvn clean install -Pquick

mkdir -p getting-started

echo "Generating user-guide"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.user.walkthrough.UserWalkthroughRunner > docs/getting-started/user-guide.md

echo "Generating dev-guide"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.dev.walkthrough.DevWalkthroughRunner > docs/getting-started/dev-guide.md

echo "Generating properties-guide"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.properties.walkthrough.PropertiesWalkthroughRunner > docs/getting-started/properties-guide.md

echo "Generating operation-examples"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.operation.OperationExamplesRunner > docs/getting-started/operation-examples.md

echo "Generating accumulo-operation-examples"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.operation.accumulo.AccumuloOperationExamplesRunner > docs/getting-started/accumulo-operation-examples.md

echo "Generating spark-operation-examples"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.operation.spark.SparkOperationExamplesRunner > docs/getting-started/spark-operation-examples.md

echo "Generating predicate-examples"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.predicate.PredicateExamplesRunner > docs/getting-started/predicate-examples.md


mkdir -p docs/components/core
mkdir -p docs/components/example
components="core/serialisation core/data core/operation core/store core/graph example/road-traffic";
set +e
for component in $components; do
    echo "Fetching component: $component"
    curl https://raw.githubusercontent.com/gchq/Gaffer/master/$component/README.md -o docs/components/$component.md
    sed -i '' '/This page has been copied from/d' docs/components/$component.md > /dev/null 2>&1
    sed -i '/This page has been copied from/d' docs/components/$component.md > /dev/null 2>&1
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