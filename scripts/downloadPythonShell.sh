#!/bin/bash

gafferToolsVersion=$1
echo "Gaffer tools version: $gafferToolsVersion"

if [ ! -f gaffer-python-shell/$gafferToolsVersion ]; then
    rm -rf gaffer-python-shell
    rm -rf gaffer-tools.tar.gz

    curl -L https://github.com/gchq/gaffer-tools/tarball/gaffer-tools-$gafferToolsVersion > gaffer-tools.tar.gz
    tar -xvf gaffer-tools.tar.gz */python-shell
    rm -rf gaffer-tools.tar.gz

    mv gchq-gaffer-tools-*/python-shell gaffer-python-shell
    rmdir gchq-gaffer-tools-*

    # Add a marker for the version
    echo '' > gaffer-python-shell/$gafferToolsVersion
fi