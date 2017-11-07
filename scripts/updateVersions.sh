#!/usr/bin/env bash

if [ -z "$1" -o -z "$2" ]; then
    echo "Usage: ./updateVersions.sh <new gaffer version> <new gaffer-tools version>"
    exit 1
fi

git reset --hard
git clean -fd
git checkout master
git pull

gafferVersion=$1
gafferToolsVersion=$2

git checkout -b updating-versions-$gafferVersion-$gafferToolsVersion


mvn -q org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.parent.version
oldGafferVersion=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=gaffer.version | grep -v '\['`
oldGafferToolsVersion=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=gaffer-tools.version | grep -v '\['`


sed -i '' "s/gaffer.version>$oldGafferVersion</gaffer.version>$gafferVersion</g" pom.xml
sed -i '' "s/gaffer-tools.version>$oldGafferToolsVersion</gaffer-tools.version>$gafferToolsVersion</g" pom.xml
sed -i '' "s/version>$oldGafferVersion</version>$gafferVersion</g" pom.xml
sed -i '' "s/gaffer2:$oldGafferVersion/gaffer2:$gafferVersion/g" NOTICES
sed -i '' "s/gaffer-tools:$oldGafferToolsVersion/gaffer-tools:$gafferToolsVersion/g" NOTICES

sed -i '' "s/>Version $oldGafferVersion</>Version $gafferVersion</g" docs/README.md

git add .
git commit -a -m "Updated versions"
git push -u origin updating-versions-$gafferVersion-$gafferToolsVersion
