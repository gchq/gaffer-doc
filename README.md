<img src="docs/logos/logoWithText.png" width="300">

Gaffer Doc
==========

This repository contains all the documentation for Gaffer, which is published [here](https://gchq.github.io/gaffer-doc/).

# Building the documentation
Gaffer's documentation is built using [Honkit](https://github.com/honkit/honkit). 

## Prerequisites
### Node
You need Node to install and use Honkit.

### Java & Maven
You need Java 8 and Maven to generate the documentation.
 
## Build the documentation site
To generate the documentation files, download other documentation sources and compile these into a website using Honkit, run the following:

```bash
mvn -q install -P buildHonkit -V
```

## Serve the documentation site
Once the docs site has been built, it can be served (on `localhost:4000`) by using: 

```bash
npm run serve
```
