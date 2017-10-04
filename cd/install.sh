#!/usr/bin/env bash

set -e

if [ "$TRAVIS_BRANCH" != 'master' ] || [ "$TRAVIS_PULL_REQUEST" == 'true' ]; then
    echo "Running install script: mvn -q install -P quick,travis,build-extras -B -V"
    mvn -q install -P quick,travis,build-extras -B -V
fi