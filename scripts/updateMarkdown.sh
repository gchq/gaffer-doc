#!/bin/bash
set -e

mvn clean install -Pquick

mkdir -p getting-started

echo "Generating user-guide"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.user.walkthrough.UserWalkthroughRunner > getting-started/user-guide.md

echo "Generating dev-guide"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.dev.walkthrough.DevWalkthroughRunner > getting-started/dev-guide.md

echo "Generating properties-guide"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.properties.walkthrough.PropertiesWalkthroughRunner > getting-started/properties-guide.md

echo "Generating operation-examples"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.operation.OperationExamplesRunner > getting-started/operation-examples.md

echo "Generating accumulo-operation-examples"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.operation.accumulo.AccumuloOperationExamplesRunner > getting-started/accumulo-operation-examples.md

echo "Generating spark-operation-examples"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.operation.spark.SparkOperationExamplesRunner > getting-started/spark-operation-examples.md

echo "Generating predicate-examples"
java -cp target/doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.predicate.PredicateExamplesRunner > getting-started/predicate-examples.md


mkdir -p components/core
mkdir -p components/example
components="core/serialisation core/data core/operation core/store core/graph example/road-traffic";
set +e
for component in $components; do
    echo "Fetching component: $component"
    curl https://raw.githubusercontent.com/gchq/Gaffer/master/$component/README.md -o components/$component.md
    sed -i '' '/This page has been copied from/d' components/$component.md > /dev/null 2>&1
    sed -i '/This page has been copied from/d' components/$component.md > /dev/null 2>&1
done
set -e

mkdir -p stores
stores="accumulo-store hbase-store map-store proxy-store parquet-store federated-store";
set +e
for store in $stores; do
    echo "Fetching store: $store"
    curl https://raw.githubusercontent.com/gchq/Gaffer/master/store-implementation/$store/README.md -o stores/$store.md
    sed -i '' '/The master copy of this page/d' stores/$store.md > /dev/null 2>&1
    sed -i '/The master copy of this page/d' stores/$store.md > /dev/null 2>&1
done
set -e

# Remove copyright headers
files=`grep -rl "Copyright 20" components stores README.md`
for file in $files; do
    tail -n +14 $file > .tmpFile
    mv .tmpFile $file
done