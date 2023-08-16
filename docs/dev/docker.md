# Gaffer Docker

The [gaffer-docker](https://github.com/gchq/gaffer-docker) repository contains all code needed to run Gaffer using Docker.

All the files needed to get started using Gaffer in Docker are contained in the ['docker'](https://github.com/gchq/gaffer-docker/tree/develop/docker) sub-folder.

In this directory you can find the Dockerfiles and docker compose files for building container images for:

- [Gaffer](https://github.com/gchq/gaffer-docker/tree/develop/docker/gaffer)
- [Gaffer's REST API](https://github.com/gchq/gaffer-docker/tree/develop/docker/gaffer-rest)
- [Gaffer's Road Traffic Example](https://github.com/gchq/gaffer-docker/tree/develop/docker/gaffer-road-traffic-loader)
- [HDFS](https://github.com/gchq/gaffer-docker/tree/develop/docker/hdfs)
- [Accumulo](https://github.com/gchq/gaffer-docker/tree/develop/docker/accumulo)
- [Gaffer's Integration Tests](https://github.com/gchq/gaffer-docker/tree/develop/docker/gaffer-integration-tests)
- [gafferpy Jupyter Notebook](https://github.com/gchq/gaffer-docker/tree/develop/docker/gaffer-pyspark-notebook)
- [Gaffer's JupyterHub Options Server](https://github.com/gchq/gaffer-docker/tree/develop/docker/gaffer-jhub-options-server)
- [Spark](https://github.com/gchq/gaffer-docker/tree/develop/docker/spark-py)

Each directory contains a README with more specific information on what these images are for and how to build them.

Please note that some of these containers will only be useful if utilised by the Helm Charts under Kubernetes, and may not be possible to run on their own.

## Requirements

Before you can build and run these containers you will need to install Docker along with the compose plugin. Information on how to do this can be found in the [docker docs](https://docs.docker.com/get-docker/).
