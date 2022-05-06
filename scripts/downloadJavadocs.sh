#!/bin/bash
set -e

gafferVersion=$1
gafferToolsVersion=$2
korypheVersion=$3

# Update javadocs
mkdir -p docs/javadoc
cd docs/javadoc

fetchJavadoc () {
    rm -rf $1
    restUrl='https://api.github.com/repos/gchq/'$1'/commits?sha=gh-pages' #Requests to this API are ratelimited without a token
    commitShaRegex='.*message.*'$2'.,.*.tree...{.*sha....([[:alnum:]]*).,'
    githubCommitHistory=$(curl -Ss -H "Accept: application/vnd.github.v3+json" $restUrl)
    if [[ "$githubCommitHistory" =~ $commitShaRegex ]]; then
        commitSha="${BASH_REMATCH[1]}"
    else
        echo 'Error finding '$1' Javadoc for version '$2
        echo 'curl output:'$githubCommitHistory
        exit 1
    fi
    curl -Ss -L 'https://github.com/gchq/'$1'/archive/'$commitSha'.zip' > $1.zip
    unzip -q $1.zip
    mv *-$commitSha $1
    rm $1.zip
    echo '' > $1/$2
}

if [ ! -f gaffer/$gafferVersion ]; then
    fetchJavadoc 'gaffer' $gafferVersion
else
    echo 'Gaffer Javadoc already up to date'
fi
if [ ! -f gaffer-tools/$gafferToolsVersion ]; then
    fetchJavadoc 'gaffer-tools' $gafferToolsVersion
else
    echo 'Gaffer Tools Javadoc already up to date'
fi
if [ ! -f koryphe/$korypheVersion ]; then
    fetchJavadoc 'koryphe' $korypheVersion
else
    echo 'Koryphe Javadoc already up to date'
fi
