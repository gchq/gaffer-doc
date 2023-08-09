# Quickstart

The quickest way to get up and running with Gaffer is through its container
images. To start up a simple map store based instance with some default
schemas simply pull and run the `gaffer-rest` image.

```bash
docker pull gchq/gaffer-rest:2.0.0
```

```bash
docker run -p 8080:8080 gchq/gaffer-rest:2.0.0
```

The Swagger rest API should be available at
[http://127.0.0.1:8080/rest](http://127.0.0.1:8080/rest) to try out. Be aware
that, as the image uses the map store backend by default, all graph data will
be saved in memory so killing the container will mean you will lose any data
added to the graph.

!!! tip
    Please see the [possible deployments](#possible-deployments) section
    for a basic look at the different storage options Gaffer supports.

If you wish to add custom schema to try out you can mount these into the
container at start up to configure the graph. By default the `gaffer-rest`
image looks under `/gaffer/schema` meaning you can mount over this directory
with a directory containing your custom schema.

```bash
docker run -p 8080:8080 -v /path/to/your/schema:/gaffer/schema gchq/gaffer-rest:2.0.0
```

!!! info

    The [example deployment](example-deployment/project-setup.md) section
    provides a full walkthrough of a basic Gaffer deployment, and covers how to
    write a simple Gaffer Schema to create a custom graph with an Accumulo
    based data store.

## Possible Deployments

As Gaffer essentially works as a framework to structure and save data into a
data store, the storage option is one of the largest considerations when
deploying a new graph. A few technologies are supported by Gaffer; however,
some are more widely used than others, the main types you might want to use
are:

- **Accumulo Store** - The main recommended data store for Gaffer implemented
    by [Apache Accumulo](https://accumulo.apache.org/).
- **Map Store** - In memory JVM store, useful for quick prototyping.
- **Proxy Store** - This provides a way to hook into an existing Gaffer store,
    when used all operations are delegated to the chosen Gaffer Rest API.
- **Federated Store** - Similar to a proxy store however, this will forward all
    requests to a collection of sub graphs but merge the responses so they
    appear as one graph.

Once the storage option has been chosen, the deployment can be setup and
started using one or more of the available Gaffer container images.

!!! info

    Please see the `gaffer-docker` documentation for more information on
    available images and deployments.
