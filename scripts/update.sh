#!/bin/bash

git checkout master
git reset --hard
git pull
./updateJavadocs.sh
./updateMarkdown.sh
git add .
git commit -a -m "Updated docs"
git push origin master

./updateGhPages.sh
