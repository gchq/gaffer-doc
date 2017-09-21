#!/bin/bash
set -e

gafferVersion=1.0.0-RC2
gafferToolsVersion=1.0.0-RC2
korypheVersion=1.0.0-RC1

# Update javadocs
cd docs/javadoc
if [ ! -f gaffer/$gafferVersion ]; then
    rm -rf gaffer
    curl -L https://github.com/gchq/Gaffer/tarball/gh-pages > gaffer.tar.gz
    tar -xvf gaffer.tar.gz
    mv gchq-Gaffer-* gaffer
    rm -rf gaffer.tar.gz
    echo '' > gaffer/$gafferVersion
else
    echo 'Gaffer Javadoc already up to date'
fi
if [ ! -f gaffer-tools/$gafferToolsVersion ]; then
    rm -rf gaffer-tools
    curl -L https://github.com/gchq/gaffer-tools/tarball/gh-pages > gaffer-tools.tar.gz
    tar -xvf gaffer-tools.tar.gz
    mv gchq-gaffer-tools-* gaffer-tools
    rm -rf gaffer-tools.tar.gz
    echo '' > gaffer-tools/$gafferToolsVersion
else
    echo 'Gaffer Tools Javadoc already up to date'
fi
if [ ! -f koryphe/$korypheVersion ]; then
    rm -rf koryphe
    curl -L https://github.com/gchq/koryphe/tarball/gh-pages > koryphe.tar.gz
    tar -xvf koryphe.tar.gz
    mv gchq-koryphe-* koryphe
    rm -rf koryphe.tar.gz
    echo '' > koryphe/$korypheVersion
else
    echo 'Koryphe Javadoc already up to date'
fi
