#!/bin/bash

set -e

gafferToolsVersion=$1
pythonShellPath=$2

echo "Gaffer tools version: $gafferToolsVersion"
echo "python path = $pythonShellPath"
if [ ! -f gaffer-python-shell/$gafferToolsVersion ]; then
    rm -rf gaffer-python-shell
    rm -rf gaffer-tools.tar.gz

    if [[ "$pythonShellPath" != "" ]]; then
        if [ -d $pythonShellPath ]; then
            echo "Copying python shell from: $pythonShellPath"
            mkdir gaffer-python-shell
            cp -r $pythonShellPath/* gaffer-python-shell/
        else
            echo "Python shell could not be found at: $pythonShellPath"
            exit 1
        fi
    else
        curl -L http://github.com/gchq/gaffer-tools/tarball/gaffer-tools-$gafferToolsVersion > gaffer-tools.tar.gz
        tar -xvf gaffer-tools.tar.gz
        rm -rf gaffer-tools.tar.gz
        mv gchq-gaffer-tools-*/python-shell gaffer-python-shell
        rm -rf gchq-gaffer-tools-*
    fi

    # Add a marker for the version
    echo '' > gaffer-python-shell/$gafferToolsVersion

    # Add fromJsonRunner
    echo 'from gafferpy.fromJson import *' > gaffer-python-shell/src/fromJsonRunner.py
fi