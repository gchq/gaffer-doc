# Developer Guide

This development guide will take you through the steps required to start development work with Gaffer.
This guide is not for end users of Gaffer and is instead targeted at those who wish to develop Gaffer or build Gaffer extensions.

## Source Control

Development of Gaffer is done on the
[GCHQ/Gaffer](https://github.com/gchq/Gaffer) GitHub repository, or other Gaffer
GitHub repositories under the [GCHQ
Organization](https://github.com/orgs/gchq/repositories).

## Repositories

The core Java [Gaffer repo](https://github.com/gchq/Gaffer) contains the main Gaffer product.
If you are completely new to Gaffer you can try out our [Road Traffic Demo](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/README.md) or look at our example [deployment guide](../development-guide/example-deployment/project-setup.md).

The [gafferpy repo](https://github.com/gchq/gafferpy) contains a Python shell that can execute operations.

The [gaffer-docker repo](https://github.com/gchq/gaffer-docker) contains the code needed to run Gaffer using Docker or Kubernetes.
More information about running a containerised instance of Gaffer can be found in our [adminstration guide](../administration-guide/introduction.md).

It is also worth noting the [koryphe repo](https://github.com/gchq/koryphe) as this is a key dependency for Gaffer.
It contains an extensible functions library for filtering, aggregating and transforming data based on the Java Function API.

## Building Gaffer

### Build Instructions

The latest instructions for building Gaffer [are in our
README](https://github.com/gchq/Gaffer/blob/develop/README.md#building-and-deploying).

### Supported Platforms

A recent Linux distribution is recommended, although it should be possible to
build Gaffer on any system which has the latest version of Java 8 or 11 (the
Gaffer codebase uses Java 8). Running tests on Windows is not recommended due to
complexities with native libraries.

## Contributing

We welcome contributions to the project. See our [ways of working for more
detail](ways-of-working.md). All contributors must sign the [GCHQ Contributor
Licence Agreement](https://cla-assistant.io/gchq/Gaffer).

You can quickly and easily contribute towards Gaffer using a [remote coding
environment](remote-coding-environments.md) such as GitHub Codespaces or Gitpod.
