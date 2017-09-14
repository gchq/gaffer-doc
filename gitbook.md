# Building the documentation
Gaffer's documentation is built using [GitBook](https://www.gitbook.com). 

## Prerequisites
### NPM
You need NPM to install the GitBook command line toolchain. To get NPM install [node](https://nodejs.org/en/).

### GitBook command line tools

```bash
npm install -g gitbook-cli
```

## Proxy
If you are running gitbook behind a proxy, then ensure you have set the following:

```bash
npm config set proxy http://proxy.company.com:8080
npm config set https-proxy http://proxy.company.com:8080
```

## Build the book
GitBook uses plugins, e.g. anchorjs allows us to create links to headings within a file. You need to install these plugins first. The below commands should be run in the project root.

```bash
gitbook install
```

You can build the documentation like this:

```bash
gitbook build
```

Or you can run the GitBook server which will watch your files as you work and server them on `localhost:4000`.

```bash
gitbook serve
