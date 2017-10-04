#!/usr/bin/env bash

set -e

if [ "$TRAVIS_BRANCH" != 'master' ] || [ "$TRAVIS_PULL_REQUEST" == 'true' ]; then
    echo "Running verify script: mvn -q verify -P travis,analyze -B"
    mvn -q verify -P travis,analyze -B
    echo "Running verify script: mvn -q verify -P travis,test -B"
    mvn -q verify -P travis,test -B
    ./scripts/updateMarkdown.sh
fi