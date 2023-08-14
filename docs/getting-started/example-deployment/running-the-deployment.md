# Running the Deployment

To run the containers there are a couple of options but primarily the two main ways are with either
Docker Compose or Kubernetes (via Helm), the [gaffer-docker](https://github.com/gchq/gaffer-docker)
repository has some examples of how to run both.

## Docker Compose

For this example we will use a slightly modified version of the docker compose config file used in
the repository.

??? example "docker-compose.yaml"
    ```yaml
    version: "3.7"

    services:

      zookeeper:
        image: zookeeper:${ZOOKEEPER_VERSION}
        healthcheck:
          test: echo ruok | nc 127.0.0.1 2181 | grep imok
          interval: 30s
          timeout: 5s
          retries: 3
        container_name: zookeeper
        hostname: zookeeper
        environment:
        - ZOO_SERVERS=server.1=zookeeper:2888:3888;2181
        - ZOO_4LW_COMMANDS_WHITELIST=*
        volumes:
        - /data
        - /datalog

      hdfs-namenode:
        image: gchq/hdfs:${HADOOP_VERSION}
        depends_on:
          zookeeper:
            condition: service_healthy
        healthcheck:
          test: curl -f http://localhost:9870 || exit 1
          interval: 30s
          timeout: 10s
          retries: 3
        command: namenode
        container_name: hdfs-namenode
        hostname: hdfs-namenode
        environment:
        - HADOOP_CONF_DIR=${HADOOP_CONF_DIR}
        ports:
        - 9870:9870
        volumes:
        - ./configs/hdfs:${HADOOP_CONF_DIR}:ro
        - /var/log/hadoop
        - /data1
        - /data2

      hdfs-datanode:
        image: gchq/hdfs:${HADOOP_VERSION}
        depends_on:
          hdfs-namenode:
            condition: service_healthy
        command: datanode
        container_name: hdfs-datanode
        hostname: hdfs-datanode
        environment:
        - HADOOP_CONF_DIR=${HADOOP_CONF_DIR}
        volumes:
        - ./configs/hdfs:${HADOOP_CONF_DIR}:ro
        - /var/log/hadoop
        - /data1
        - /data2

      accumulo-master:
        image: gchq/gaffer:${GAFFER_VERSION}-accumulo-${ACCUMULO_VERSION}
        depends_on:
          hdfs-namenode:
            condition: service_healthy
        healthcheck:
          test: cat /proc/net/tcp | grep 270F
          interval: 30s
          timeout: 5s
          retries: 3
          start_period: 10s
        build:
          context: .
          args:
            GAFFER_VERSION: ${GAFFER_VERSION}
            BASE_IMAGE_NAME: gchq/accumulo
            BASE_IMAGE_TAG: ${ACCUMULO_VERSION}
        command: master
        container_name: accumulo-master
        hostname: accumulo-master
        environment:
        - ACCUMULO_CONF_DIR=${ACCUMULO_CONF_DIR}
        # There doesn't seem to be an easy way (with docker-compose) to init our
        # HDFS instance with the right permissions so that Accumulo can create the
        # file structure it needs. Using the following workaround to allow
        # accumulo to "auth" with HDFS as the super user so that it can:
        - HADOOP_USER_NAME=hadoop
        volumes:
        - ./configs/accumulo:${ACCUMULO_CONF_DIR}:ro
        - /var/log/accumulo

      accumulo-tserver:
        image: gchq/gaffer:${GAFFER_VERSION}-accumulo-${ACCUMULO_VERSION}
        depends_on:
          accumulo-master:
            condition: service_healthy
        healthcheck:
          test: cat /proc/net/tcp | grep 270D
          interval: 30s
          timeout: 5s
          retries: 3
        command: tserver
        container_name: accumulo-tserver
        hostname: accumulo-tserver
        environment:
        - ACCUMULO_CONF_DIR=${ACCUMULO_CONF_DIR}
        # There doesn't seem to be an easy way (with docker-compose) to init our
        # HDFS instance with the right permissions so that Accumulo can create the
        # file structure it needs. Using the following workaround to allow
        # accumulo to "auth" with HDFS as the super user so that it can:
        - HADOOP_USER_NAME=hadoop
        volumes:
        - ./configs/accumulo:${ACCUMULO_CONF_DIR}:ro
        - /var/log/accumulo

      accumulo-monitor:
        image: gchq/gaffer:${GAFFER_VERSION}-accumulo-${ACCUMULO_VERSION}
        depends_on:
          accumulo-master:
            condition: service_healthy
        command: monitor
        container_name: accumulo-monitor
        hostname: accumulo-monitor
        environment:
        - ACCUMULO_CONF_DIR=${ACCUMULO_CONF_DIR}
        # There doesn't seem to be an easy way (with docker-compose) to init our
        # HDFS instance with the right permissions so that Accumulo can create the
        # file structure it needs. Using the following workaround to allow
        # accumulo to "auth" with HDFS as the super user so that it can:
        - HADOOP_USER_NAME=hadoop
        ports:
        - 9995:9995
        volumes:
        - ./configs/accumulo:${ACCUMULO_CONF_DIR}:ro
        - /var/log/accumulo

      accumulo-gc:
        image: gchq/gaffer:${GAFFER_VERSION}-accumulo-${ACCUMULO_VERSION}
        depends_on:
          accumulo-master:
            condition: service_healthy
        command: gc
        container_name: accumulo-gc
        hostname: accumulo-gc
        environment:
        - ACCUMULO_CONF_DIR=${ACCUMULO_CONF_DIR}
        # There doesn't seem to be an easy way (with docker-compose) to init our
        # HDFS instance with the right permissions so that Accumulo can create the
        # file structure it needs. Using the following workaround to allow
        # accumulo to "auth" with HDFS as the super user so that it can:
        - HADOOP_USER_NAME=hadoop
        volumes:
        - ./configs/accumulo:${ACCUMULO_CONF_DIR}:ro
        - /var/log/accumulo

      gaffer-rest:
        image: gchq/gaffer-rest:${GAFFER_VERSION}-accumulo-${ACCUMULO_VERSION}
        depends_on:
          accumulo-tserver:
            condition: service_healthy
        ports:
        - 8080:8080
        volumes:
        - ./configs/gaffer/application.properties:/gaffer/config/application.properties:ro
        - ./configs/gaffer/data:/gaffer/data:ro
        - ./configs/gaffer/graph:/gaffer/graph:ro
        - ./configs/gaffer/schema:/gaffer/schema:ro
        - ./configs/gaffer/store:/gaffer/store:ro
    ```

If you are not familiar with docker or docker compose there are plenty of
[resources](https://docs.docker.com/compose/) online to get up to speed but essentially, it is a
config file that will run up multiple containers with various bits of configuration which is a lot
easier than typing out multiple docker commands!

Some key bits you may want to configure in the file are the shared volumes (under the `volumes`
section) as, the locations in the example file assume you use the project structure from the
[example setup](./project-setup.md) but if you change any of the locations then they will need
updating.

To run up the cluster its as easy as running the following from the root of the project.

```bash
docker compose up
```

The above configuration will start the following containers:

- Zookeeper
- HDFS
    - Datanode
    - Namenode
- Accumulo
    - Monitor
    - GC
    - tServer
    - Master
- Gaffer Rest API

The web UIs for nodes in cluster can then be accessed at the following addresses:

- Access the HDFS NameNode web UI at: <http://localhost:9870>
- Access the Accumulo Monitor UI at: <http://localhost:9995>
- Access the Gaffer REST API at: <http://localhost:8080/rest/>

## Environment Variables

As you can probably see the example is using a few environment variables in the
`docker-compose.yaml` file, these set things such as the container versions and a couple of file
locations. The use of these variables are recommended as it can make it easier to update container
versions and other aspecs of the containers.

A basic set of these environment variables are shown below which can be saved in a `.env` file and
sourced before running the containers.

```bash
ZOOKEEPER_VERSION="3.7.1"
GAFFER_VERSION="2.0.0"
ACCUMULO_VERSION="2.0.1"
HADOOP_VERSION="3.3.3"
ACCUMULO_CONF_DIR="/etc/accumulo/conf"
HADOOP_CONF_DIR="/etc/hadoop/conf"
```
