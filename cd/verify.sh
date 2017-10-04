#!/usr/bin/env bash

set -e


echo "Running verify script: mvn -q verify -P travis,analyze -B"
mvn -q verify -P travis,analyze -B
echo "Running verify script: mvn -q verify -P travis,test -B"
mvn -q verify -P travis,test -B
./scripts/updateJavadocs.sh
./scripts/updateMarkdown.sh