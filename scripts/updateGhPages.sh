#!/bin/bash

# Partly copied from http://sangsoonam.github.io/2016/08/02/publish-gitbook-to-your-github-pages.html
set -e

# install the plugins and build the static site
gitbook install && gitbook build

# checkout latest version of the gh-pages branch
git checkout master
git pull
git branch -fqD gh-pages > /dev/null 2>&1 || true
git checkout gh-pages

# copy the static site files into the current directory.
cp -R _book/* .

# remove 'node_modules' and '_book' directory
git clean -fx node_modules
git clean -fx _book

# add all files
git add .

# commit
git commit -a -m "Updated docs"

# push to the origin
git push origin gh-pages

# checkout the master branch
git checkout master