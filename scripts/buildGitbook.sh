#!/bin/bash

set -e

echo "Installing gitbook"
gitbook install
gitbook build
