#!/usr/bin/env bash

set -e

npm install -g gitbook-cli > /dev/null 2>&1

echo "Running install script: mvn -q install -P buildGitbook,travis,build-extras -B -V"
mvn -q install -P buildGitbook,travis,build-extras -B -V
