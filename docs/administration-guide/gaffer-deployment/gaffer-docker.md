# Deploying Gaffer via Docker

As demonstrated in the [quickstart](./quickstart.md) its very simple to start up
a basic in memory gaffer graph using the available OCI (Open Container
Initiative) images.

However, for large scale graphs with persistent storage you will want to use a
different storage backend; the recommended one being Accumulo. To do this a
different deployment of containers are required. This guide will run through the
containers needed for a basic Accumulo cluster and how to configure and create
custom OCI images of Gaffer.

## Available OCI Images

Currently there are a few different images that can be used to run a Gaffer
deployment. The main ones are outlined in the following table and are all
available on [Docker Hub](https://hub.docker.com/u/gchq).

| Image | Description |
| ----- | ----------- |
| `gchq/accumulo` | This image is a containerised deployment of [Apache Accumulo](https://accumulo.apache.org/). This was created as historically there has not been an available official image from the maintainers of Accumulo; however, there has since been an [offical image](https://github.com/apache/accumulo-docker) made available but it is not currently in use in Gaffer. |
| `gchq/hdfs` | A custom image for running HDFS (Hadoop file system) via a container. Contains an official release of [Apache Hadoop](https://hadoop.apache.org/) which is used as the scalable data storage for Accumulo. |
| `gchq/gaffer` | This is the main container image for Gaffer that is built on on top of the `gchq/accumulo` image so includes a release of `zookeeper`, `hdfs` and `accumulo` along with the Gaffer libraries. Running this image simply runs the Accumulo instance not a Gaffer instance. |
| `gchq/gaffer-rest` | This is the REST API image containing the config files that can be used to configure the graph to connect to the chosen store, by default there are some pre-configured config file which can be overridden by a [bind-mount](#volumes-and-bind-mount) of alternatives. |

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

Creating a custom image can also be useful if you with to load custom extensions
to use with Gaffer (e.g. Jars) by default.

To create a custom image simply make a new `Dockerfile` and use one of the Gaffer
images as the base image like the following:

```dockerfile
FROM gchq/gaffer-rest

# Copy over the existing directory with store configs in
COPY custom/configs/gaffer/store /gaffer/store
```

Then build the new image using a suitable tool or just plain docker from the
current directory like:

```bash
docker build -t my-gaffer-rest .
```

!!! tip
    The `gchq/gaffer-rest` image allows adding additional Jars to the class path
    by adding them to the `/jars/lib` directory in the image.

## How Do I Run a Containerised Deployment?

After reading the previous sections you should have a good understanding of what
images are available for Gaffer and how to configure them to you needs. However,
before running a deployment backed by Accumulo you will need to know a bit of
background on Hadoop to understand how the data will scale and be distributed.

Usually when deploying a container image you simply run the image and everything
is contained locally to the container (hence the name). For larger scale graphs
this less desireable as we will usually want to be able to scale and load
balance the storage based on the volume of data; this is where Hadoop comes in.

### What is Hadoop/Accumulo?

[Apache Hadoop](https://hadoop.apache.org/) is an open-source software framework
used for distributed storage and processing of large datasets. Hadoop is
designed to handle various types of data, including structured, semi-structured,
and unstructured data. It is a highly scalable framework that allows users to
add nodes to the cluster as needed.

Hadoop has two main components: Hadoop Distributed File System (HDFS) and
MapReduce. HDFS is a distributed file system that provides high-throughput
access to data. MapReduce is a programming model used for processing large
datasets in parallel.

Accumulo is built on top of the HDFS to provide a key-value store with all the
same scalability and robustness of Hadoop.

### Running a Cluster via Docker for Gaffer

To run an Accumulo cluster on Docker containers, we will need a few different
containers that will work together. The general set of containers we will need
to run is the following:

- `zookeeper`
- `gchq/hdfs`
    - `namenode`
    - `datanode` (one or more)
- `gchq/gaffer` (accumulo)
    - `master`
    - `tserver`
    - `monitor`
    - `gc`
- `gchq/gaffer-rest`

#### ZooKeeper

Starting with [ZooKeeper](https://zookeeper.apache.org/), this is used by
Accumulo to provide distributed synchronization so is useful to start up first.

```bash
docker run \
       --detach \
       --hostname zookeeper \
       --name zookeeper \
       --env ZOO_SERVERS="server.1=zookeeper:2888:3888;2181" \
       --env ZOO_4LW_COMMANDS_WHITELIST="*" \
       --volume /data \
       --volume /datalog \
       zookeeper:3.7.1
```

The above docker command will run a ZooKeeper container with few bits of
configuration. The main part that is being set up is the hostname and ports.
With ZooKeeper you can also do this by providing a `zoo.cfg` file but as there
is not much to do for a small cluster so we can just pass the configuration in
via environment variables with `--env`. See the [official ZooKeeper docs](https://zookeeper.apache.org/)
for more information and configuration options.

#### Hadoop/HDFS

Next we can launch the Hadoop cluster, we will use the custom distribution of
the HDFS image `gchq/hdfs`. As mentioned before this provides a single-node
Hadoop cluster which we can simply run multiple times and link together to
extend into a multi-node cluster.

To run a Hadoop cluster we first need the configuration files for Hadoop which
we can then add into the running containers. For the purposes of this example
we will use the files from the [`gaffer-docker`](https://github.com/gchq/gaffer-docker/tree/develop/docker/hdfs/conf)
repository, but you may wish to edit these for your deployment and can read
more in the [official Hadoop docs](https://hadoop.apache.org/docs/r1.2.1/cluster_setup.html#Configuration+Files).

The first Hadoop container we need is a `namenode` container which runs the
Namenode service essentially acting as a master node. We can run this using
docker like the following:

```bash
docker run \
       --detach \
       --hostname hdfs-namenode \
       --name hdfs-namenode \
       --publish 9870:9870 \
       --env HADOOP_CONF_DIR="/etc/hadoop/conf" \
       --volume /custom/configs/hdfs:/etc/hadoop/conf \
       --volume /var/log/hadoop \
       --volume /data1 \
       --volume /data2 \
       gchq/hdfs:3.3.3 namenode
```

The configuration of the above `docker` command is fairly straight forward, we
make sure the port we have configured in the `core-site.xml` file is made
available on the host. We also set up the bind-mount and environment variables
so the custom configuration files are available and used. Finally there are a
few defined volumes for data, these can be changed to where ever you might want
to store the data; it is highly recommended to set these up correctly for your
available infrastructure.

Once the `namenode` has been created we now need to add the `datanode`(s) these
are additional nodes in the Hadoop cluster to store data. You can add multiple
data nodes to distribute the data across volumes and even machines (with some
additional setup).

To create a `datanode` container its much of the same steps as the `namenode`
however, we use a different command to run the `datanode` service. We can also
link the container with the `hdfs-namenode` so they are controlled together via
docker.

```bash
docker run \
       --detach \
       --link hdfs-namenode \
       --hostname hdfs-datanode1 \
       --name hdfs-datanode1 \
       --env HADOOP_CONF_DIR="/etc/hadoop/conf" \
       --volume /custom/configs/hdfs:/etc/hadoop/conf \
       --volume /var/log/hadoop \
       --volume /data1 \
       --volume /data2 \
       gchq/hdfs:3.3.3 datanode
```

!!! note
    It is recommended you configure the volumes used by your data nodes to fit
    your infrastructure and possibly run `datanode` containers across multiple
    machines and network them together to have full data distribution.

#### Accumulo

There are a few different containers that need to be started for an Accumulo
instance. They work on a similar principal to the Hadoop/HDFS containers where
the configuration is added via a bind-mount and the different services started
via the command passed to the container.

For Accumulo we will use the `gchq/gaffer` image which includes the libraries
for both Gaffer and Accumulo. For a deployment of Accumulo generally the
following nodes/containers are needed:

- `master` - This is the primary coordinating process. Must specify one node.
  Can specify a few for fault tolerance (note as of Accumulo v2.1 this is
  referred to as `manager`).
- `gc` - The Accumulo garbage collector. Must specify one node. Can specify a
  few for fault tolerance.
- `monitor` - Node where Accumulo monitoring web server is run.
- `tserver` - An Accumulo worker process.

=== "master node"

    ```bash
    docker run \
           --detach \
           --hostname accumulo-master \
           --name accumulo-master \
           --env ACCUMULO_CONF_DIR="/etc/accumulo/conf" \
           --env HADOOP_USER_NAME="hadoop" \
           --volume /custom/configs/accumulo:/etc/accumulo/conf \
           --volume /var/log/accumulo \
           gchq/gaffer:2.0.0-accumulo-2.0.1 master
    ```
=== "gc node"

    ```bash
    docker run \
           --detach \
           --hostname accumulo-gc \
           --name accumulo-gc \
           --env ACCUMULO_CONF_DIR="/etc/accumulo/conf" \
           --env HADOOP_USER_NAME="hadoop" \
           --volume /custom/configs/accumulo:/etc/accumulo/conf \
           --volume /var/log/accumulo \
           gchq/gaffer:2.0.0-accumulo-2.0.1 gc
    ```

=== "monitor node"

    ```bash
    docker run \
           --detach \
           --hostname accumulo-monitor \
           --name accumulo-monitor \
           --publish 9995:9995 \
           --env ACCUMULO_CONF_DIR="/etc/accumulo/conf" \
           --env HADOOP_USER_NAME="hadoop" \
           --volume /custom/configs/accumulo:/etc/accumulo/conf \
           --volume /var/log/accumulo \
           gchq/gaffer:2.0.0-accumulo-2.0.1 monitor
    ```

=== "tserver node"

    ```bash
    docker run \
           --detach \
           --hostname accumulo-tserver \
           --name accumulo-tserver \
           --env ACCUMULO_CONF_DIR="/etc/accumulo/conf" \
           --env HADOOP_USER_NAME="hadoop" \
           --volume /custom/configs/accumulo:/etc/accumulo/conf \
           --volume /var/log/accumulo \
           gchq/gaffer:2.0.0-accumulo-2.0.1 tserver
    ```
