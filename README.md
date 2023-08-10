<img src="docs/assets/logoWithText.png" width="300">

Gaffer Doc
==========

This repository contains all the documentation for Gaffer, which is published [here](https://gchq.github.io/gaffer-doc/).

# Building the documentation
For instructions for building Gaffer's v1 documentation, see the v1docs branch.
The current Gaffer docs are built using [MkDocs](https://www.mkdocs.org) with the [Material theme](https://squidfunk.github.io/mkdocs-material/).
We use [Mike](https://github.com/jimporter/mike) for documentation versioning. Running `mike` is handled by GitHub Actions, so it isn't something most contributors will need to use unless you make changes to that part of the project.

## Prerequisites
### Python
You need Python (version 3.8 or newer) installed to use MkDocs.

### MkDocs dependencies
You need MkDocs and the Material theme to generate the documentation. The versions we are using can be installed from the `requirements.txt`:

```bash
pip install -r requirements.txt
```
 
## Build the documentation site
To generate static documentation files and place them into the default `site` directory:

```bash
mkdocs build
```

## Build and serve the documentation site (recommended)
Docs can be build and served locally (on `localhost:8000`) by using:

```bash
mkdocs serve
```

This automatically updates and refreshes when local changes are made. Although it might take a few seconds due to the amount of content.