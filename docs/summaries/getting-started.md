# Getting Started

Getting Started
---------------


### Guides and examples

* [User Guide](../getting-started/user-guide.md)
* [Dev Guide](../getting-started/dev-guide.md)
* [Properties Guide](../getting-started/properties-guide.md)
* [Operation examples](../getting-started/operation-examples.md)
* [Accumulo operation examples](../getting-started/accumulo-operation-examples.md)
* [Spark operation examples](../getting-started/spark-operation-examples.md)
* [Predicate examples](../getting-started/predicate-examples.md)


### Try it out

We have a demo available to try that is based around a small uk road use dataset. See the [Road Traffic Example](../components/example/road-traffic.md) to try it out.

### Building and Deploying

To build Gaffer run `mvn clean install -Pquick` in the top-level directory. This will build all of Gaffer's core libraries and some examples of how to load and query data.

See [stores](stores.md) for a list of available Gaffer Stores to chose from and the relevant documentation for each.

### Inclusion in other projects

Gaffer is hosted on [Maven Central](https://mvnrepository.com/search?q=uk.gov.gchq.gaffer) and can easily be incorporated into your own projects.

To use Gaffer the only required dependencies are the Gaffer graph module and a store module which corresponds to the data storage framework to utilise (currently limited to Apache Accumulo):

```
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>graph</artifactId>
    <version>${gaffer.version}</version>
</dependency>
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>accumulo-store</artifactId>
    <version>${gaffer.version}</version>
</dependency>
```

This will include all other mandatory dependencies. Other (optional) components can be added to your project as required.

### Contributing

We have some detailed information on our [ways of working page](../other/ways-of-working.md).

But in brief:

- Sign the [GCHQ Contributor Licence Agreement](https://github.com/gchq/Gaffer/wiki/GCHQ-OSS-Contributor-License-Agreement-V1.0)
 - Push your changes to your fork.
 - Submit a pull request.