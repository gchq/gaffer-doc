#!/usr/bin/env bash

set -e

if [ "$TRAVIS_BRANCH" == 'master' ] && [ "$TRAVIS_PULL_REQUEST" != 'true' ]; then
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis CI"
  git remote set-url origin https://${GITHUB_TOKEN}@github.com/gchq/gaffer-doc.git

  ./scripts/updateGhPages.sh
fi