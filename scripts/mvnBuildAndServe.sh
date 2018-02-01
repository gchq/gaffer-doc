#!/bin/bash

set -e

mvn clean install -PbuildGitbook,serveGitbook $@