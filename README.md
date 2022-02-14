<img src="docs/assets/logoWithText.png" width="300">

Gaffer Doc
==========

This repository contains all the documentation for Gaffer, which is published [here](https://gchq.github.io/gaffer-doc/).

# Building the documentation
For instructions for building Gaffer's v1 documentation, see the v1docs branch.
The new Gaffer docs are built using [MkDocs](https://www.mkdocs.org) with the [Material theme](https://squidfunk.github.io/mkdocs-material/).

## Prerequisites
### Python
You need Python (version 3.6 or newer) installed to use MkDocs.

### MkDocs
You need MkDocs and the Material theme to generate the documentation. Installing the Material theme will pull in MkDocs as a dependency:

```bash
pip install mkdocs-material
```

### Mike (optional)
We use this for documentation versioning. Running `mike` is handled by GitHub Actions and isn't something most contributors will need to use. If you do want to install `mike`, use:

```bash
pip install mike
```
 
## Build the documentation site
To generate the documentation files and place them into the default `site` directory:

```bash
mkdocs build
```

## Serve the documentation site
Docs can be served locally (on `localhost:8000`) by using: 

```bash
mkdocs serve
```
