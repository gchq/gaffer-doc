# How to Deploy with Accumulo on Docker

After reading the [previous page](./gaffer-images.md) you should have a good
understanding of what images are available for Gaffer and how to configure them
to your needs. However, before running a deployment backed by Accumulo you will
need to know a bit of background on Hadoop to understand how the data will scale
and be distributed.

Usually when deploying a container image you simply run the image and everything
is contained locally to the container (hence the name). For larger scale graphs
this is less desirable as we will usually want to be able to scale and load
balance the storage based on the volume of data; this is where Hadoop comes in.

!!! tip
    Please see the [Accumulo Store page](../../gaffer-stores/accumulo-store.md)
    for more information on Accumulo and Hadoop.

## Running a Cluster via Docker for Gaffer

To run an Accumulo cluster on Docker containers, we will need a few different
containers that will work together. The general set of containers we will need
to run is the following:

**`zookeeper`**

**`gchq/hdfs`**

- `namenode`
- `datanode` (one or more)

**`gchq/gaffer`** (accumulo)

- `master`
- `tserver`
- `monitor`
- `gc`

**`gchq/gaffer-rest`**

The guide here will walk through the set up of each container using standard Docker
but it may be more practical to use a tool such as docker compose. An example docker
compose file can be found in the [gaffer-docker repository](https://github.com/gchq/gaffer-docker/blob/develop/docker/gaffer/docker-compose.yaml).

Before starting any containers we need to create a network so all the containers
can talk to each other. To do this we simply run the following command to make a
network and name it appropriately:

```bash
docker network create gaffer-example
```

!!! note
    If you are using something like docker compose or host networking (e.g. with
    `--net=host`) you can skip the network creation step.

### ZooKeeper

Starting with [ZooKeeper](https://zookeeper.apache.org/), this is used by
Accumulo to provide distributed synchronization so is useful to start up first.

```bash
docker run \
       --detach \
       --name zookeeper \
       --hostname zookeeper \
       --net gaffer-example \
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

### Hadoop/HDFS

Next we can launch the Hadoop cluster, we will use the custom distribution of
the HDFS image `gchq/hdfs`. As mentioned before this provides a single-node
Hadoop cluster which we can run multiple times to extend into a multi-node
cluster.

To run a Hadoop cluster we first need the configuration files for Hadoop which
we can then add into the running containers. As a starting point you can use the
files from the
[`gaffer-docker`](https://github.com/gchq/gaffer-docker/tree/develop/docker/hdfs/conf)
repository, but you may wish to edit these for your deployment and can read more
in the [official Hadoop docs](https://hadoop.apache.org/docs/r1.2.1/cluster_setup.html#Configuration+Files).

The first Hadoop container we need is a `namenode` container which runs the
Namenode service essentially acting as a master node. We can run this using
docker like the following:

```bash
docker run \
       --detach \
       --name hdfs-namenode \
       --hostname hdfs-namenode \
       --net gaffer-example \
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
available on the host (e.g. port 9870). We also set up the bind-mount and
environment variables so the custom configuration files are available and used.
Finally there are a few defined volumes for data, these can be changed to where
ever you might want to store the data; it is highly recommended to set these up
correctly for your available infrastructure.

Once the `namenode` has been created we now need to add the `datanode`(s) these
are additional nodes in the Hadoop cluster to store data. You can add multiple
data nodes to distribute the data across volumes and even machines (with some
additional setup).

To create a `datanode` container its much of the same steps as the `namenode`
however, we use a different command to run the `datanode` service.

```bash
docker run \
       --detach \
       --name hdfs-datanode1 \
       --hostname hdfs-datanode1 \
       --net gaffer-example \
       --env HADOOP_CONF_DIR="/etc/hadoop/conf" \
       --volume /custom/configs/hdfs:/etc/hadoop/conf \
       --volume /var/log/hadoop \
       --volume /data1 \
       --volume /data2 \
       gchq/hdfs:3.3.3 datanode
```

!!! note
    It is recommended you configure the volumes used by your data nodes to fit
    your infrastructure and the amount of data you are expecting to store. You
    can also run `datanode` containers across multiple machines and network them
    together to have full data distribution.

### Accumulo

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
    This node needs to be running before starting the others.

    ```bash
    docker run \
           --detach \
           --name accumulo-master \
           --hostname accumulo-master \
           --net gaffer-example \
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
           --name accumulo-gc \
           --hostname accumulo-gc \
           --net gaffer-example \
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
           --name accumulo-monitor \
           --hostname accumulo-monitor \
           --net gaffer-example \
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
           --name accumulo-tserver \
           --hostname accumulo-tserver \
           --net gaffer-example \
           --env ACCUMULO_CONF_DIR="/etc/accumulo/conf" \
           --env HADOOP_USER_NAME="hadoop" \
           --volume /custom/configs/accumulo:/etc/accumulo/conf \
           --volume /var/log/accumulo \
           gchq/gaffer:2.0.0-accumulo-2.0.1 tserver
    ```

!!! note
    Please see the [official Accumulo docs](https://accumulo.apache.org/docs/2.x/configuration/overview)
    for more information on configuring the deployment.

### REST API

The final container we need to start up is the REST API, this essentially gives
the front end so we can use containers together in a Gaffer cluster. The REST
API container is also where the configuration for the graph is applied, such as
the schema files and store properties.

To start up the REST API it is a similar process to the other containers;
however, there are a few more bind-mounts that need defining to configure the
graph (you can also build a custom image with files baked in).

```bash
docker run \
       --detach \
       --name gaffer-rest \
       --net gaffer-example \
       --publish 8080:8080 \
       --volume /custom/configs/application.properties:/gaffer/config/application.properties \
       --volume /custom/configs/graph:/gaffer/graph \
       --volume /custom/configs/schema:/gaffer/schema \
       --volume /custom/configs/store:/gaffer/store \
       gchq/gaffer-rest:2.0.0-accumulo-2.0.1 monitor
```

!!! note
    The `gaffer-rest` image comes with some default configuration files and
    graph schema, you'll likely want to configure these for your project so
    please see the pages on [gaffer configs](../../gaffer-config/config.md) and
    [graph schema](../../gaffer-config/schema.md) for more information.
