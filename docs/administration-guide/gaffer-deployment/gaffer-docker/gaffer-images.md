# Gaffer Images

As demonstrated in the [quickstart](../quickstart.md) its very simple to start
up a basic in memory gaffer graph using the available Open Container Initiative
(OCI) images.

For large scale graphs with persistent storage you will want to use a different
storage backend to a Map Store; the recommended one being Accumulo. To do this a
different deployment of containers are required. This guide will run through the
containers needed for a basic Accumulo cluster and how to configure and create
custom images of Gaffer.

## Available Images

Currently there are a few different images that can be used to run a Gaffer
deployment. The main ones are outlined in the following table and are all
available on [Docker Hub](https://hub.docker.com/u/gchq).

| Image | Description |
| ----- | ----------- |
| `gchq/accumulo` | This image is a containerised deployment of [Apache Accumulo](https://accumulo.apache.org/). This was created as historically there has not been an available official image from the maintainers of Accumulo. There has since been an [offical image](https://github.com/apache/accumulo-docker) made available however, it is not well supported so not currently in use in Gaffer. |
| `gchq/hdfs` | A custom image for running HDFS (Hadoop file system) via a container. Contains an official release of [Apache Hadoop](https://hadoop.apache.org/) which is used as the scalable data storage for Accumulo. |
| `gchq/gaffer` | This is the main container image for Gaffer that is built on on top of the `gchq/accumulo` image so includes a release of `zookeeper`, `hdfs` and `accumulo` along with the Gaffer libraries. Running this image simply runs an Accumulo instance but with the Gaffer libraries loaded to allow Graph creation. |
| `gchq/gaffer-rest` | This is the REST API image containing the files that can be used to configure the graph to connect to the chosen store, by default there are some pre-configured config files which can be overridden by a [bind-mount](#volumes-and-bind-mount) of alternatives. |

!!! note
    There are a few other images available; however, they are less frequently
    used or purely example images, please see the [`gaffer-docker`](https://github.com/gchq/gaffer-docker/tree/develop/docker)
    repository for more details.

## Volumes and bind-mount

To change and configure the graph that is deployed you will need to override
the default files in the images by default. You can of course create a custom
image with different config files however, it can be more flexible to just
bind-mount over the current files.

To do this you will need to know the location of the files in the image you
want to override but in many cases you can mount over an entire directory
for example:

!!! example ""
    The path `/custom/configs` is some path on the host system with different
    config files in that can be mounted in when running the image.

    ```bash
    docker run \
           -p 8080:8080 \
           -v /custom/configs/gaffer/graph:/gaffer/graph \
           -v /custom/configs/gaffer/schema:/gaffer/schema \
           -v /custom/configs/gaffer/store:/gaffer/store \
           gchq/gaffer-rest:2.0.0
    ```

## Custom Images

To avoid managing a file on the host and bind-mount it, the configuration can be
baked into the image. This works well if the configuration itself is rather
static and the same across all environments.

Creating a custom image can also be useful if you want to load custom extensions
to use with Gaffer (e.g. Jars) by default.

To create a custom image simply make a new `Dockerfile` and use one of the Gaffer
images as the base image like the following:

```dockerfile
FROM gchq/gaffer-rest:latest

# Copy over the existing directory with store configs in
COPY ./custom/configs/gaffer/store /gaffer/store
```

Then build the new image using a suitable tool or just plain Docker from the
current directory like:

```bash
docker build -t my-gaffer-rest .
```

### Adding Additional Libraries

By default with the Gaffer deployment you get access to the:

- Sketches library
- Time library
- Bitmap Library
- JCS cache library

If you want more libraries than this (either one of ours of one of your own) you
will need to customise the docker images and use them in place of the defaults.

At the moment, the `gchq/gaffer-rest` image uses a runnable jar file located at
`/gaffer/jars`. When it runs it includes the `/gaffer/jars/lib` on the
classpath. This is empty by default because all the dependencies are
bundled in to the JAR. However, if you wanted to add your own jars, you can add
then to this directory like the following:

```dockerfile
FROM gchq/gaffer-rest:latest
COPY ./custom-lib:1.0-SNAPSHOT.jar /gaffer/jars/lib/
```

For an Accumulo deployment, you may wish to add additional libraries to the
classpath to enable the use of new iterators. To do this you need to update the
`gchq/gaffer` image and add the JARs to the `/opt/accumulo/lib/ext` directory:

```dockerfile
FROM gchq/gaffer:latest
COPY ./my-library-1.0-SNAPSHOT.jar /opt/accumulo/lib/ext
```

!!! note
    This path is different in Accumulo v1 please see the [migration page](../../../change-notes/migrating-from-v1-to-v2/accumulo-migration.md)
    for more detail.
