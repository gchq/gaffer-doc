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
    if [ -n "$GITHUB_TOKEN" ]; then
        echo 'GITHUB_TOKEN found, authorising to GitHub API with it'
        githubCommitHistory=$(curl -Ss -H "Accept: application/vnd.github.v3+json" -H "Authorization: Bearer "$GITHUB_TOKEN $restUrl)
    else
        echo 'GITHUB_TOKEN not found, not using GitHub API authorisation'
        githubCommitHistory=$(curl -Ss -H "Accept: application/vnd.github.v3+json" $restUrl)
    fi
    commitSha=$(echo $githubCommitHistory | sed -nE 's/.*Updated javadoc - '$2'.,..tree...\{..sha....([[:alnum:]]*).*/\1/p')

    if [ -z "$commitSha" ]; then
        echo 'Error finding '$1' Javadoc for version '$2
        echo 'curl output:'$githubCommitHistory
        exit 1
    fi
    echo 'Found '$1' Javadoc with version '$2
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
