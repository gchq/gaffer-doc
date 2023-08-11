# Components/Maven Modules

Gaffer has lots of components, which can be split into different categories. This section of the developer doc provides more detail on the most important components.

## Key components

For more developer information on these key components, see their associated page.

- [Operation](operation.md): Classes for the Operation interfaces and core operation implementations.
- [Cache](cache.md): Classes for the Gaffer cache service.
- [Graph](graph.md): Contains the Gaffer `Graph` object and related utilities.
- [Data](data.md): Classes defining Gaffer's data objects: `Element`, `Edge` and `Entity`.
- [Store](store.md): Contains the Gaffer `Store` object and related classes.
- [Serialisation](serialisation.md): Contains classes for Serialisation in Gaffer. 
- [Accumulo Store](accumulo-store.md): A store implemented using Apache Accumulo.
- [Core REST](core-rest.md): Classes which provide a Gaffer REST API using Jersey/JAX-RS.
- [Spring REST](spring-rest.md): Implementation of the Gaffer REST API deployed in a Spring Boot container.

## Project Architecture (Maven)

Gaffer uses Maven and from this perspective the project is made up of multiple Maven modules. All components are Maven modules, but not all modules are components as some are just parent POMs (see below).
Maven modules are not the same as Java modules (Java 9+), which Gaffer doesn't use or support. [See here for more info on multi-module builds and the Maven reactor](https://maven.apache.org/guides/mini/guide-multiple-modules.html).

Gaffer's project structure involves three kinds of module/POM, mostly based on the [Maven packaging type](https://maven.apache.org/pom.html#Packaging). We can call these:

- Parent modules (`pom`)
- JAR modules (`jar`)
- WAR only/Demo modules (`war`).

Parent modules consist of a single POM file (parent POM) which defines various properties, dependencies and settings to be inherited by JAR modules or other parent modules.

JAR modules are not inherited by other modules, and in addition to the POM they contain code which is compiled into artifacts (always 'jar' and sometimes also `war`). Some contain only code used for demos.

WAR only/Demo modules are not inherited by other modules, they contain only a POM and potentially config files and are used for either creating WAR archives for the REST API or are used for running demos using Maven plugins and other modules.

Gaffer has 47 modules in total. When building the complete project, Maven automatically runs the build in a specific order because of dependencies between modules.
The paths for all POMs in the Gaffer project can be seen by running `find . -name pom.xml` from the project/repository root.

The diagram below shows all modules and their type (green diamond for Parent POM, blue for JAR and red for WAR/Demo):

``` mermaid
graph LR
    PP{uk.gov.gchq.gaffer:gaffer2} --> C{core}
    PP --> I[integration-test]:::JAR
    PP --> SI{store-implementation}
    PP --> RI{rest-api}
    PP --> L{library}
    PP --> E{example}
    C --> operation:::JAR
    C --> cache:::JAR
    C --> access:::JAR
    C --> G[graph]:::JAR
    C --> type:::JAR
    C --> data:::JAR
    C --> exception:::JAR
    C --> store:::JAR
    C --> common-util:::JAR
    C --> serialisation:::JAR
    SI --> accumulo-store:::JAR
    SI --> map-store:::JAR
    SI --> proxy-store:::JAR
    SI --> federated-store:::JAR
    RI --> spring-rest:::JAR
    RI --> common-rest:::JAR
    RI --> map-rest:::WarDemo
    RI --> accumulo-rest:::WarDemo
    RI --> core-rest:::JAR
    L --> tinkerpop:::JAR
    L --> sketches-library:::JAR
    L --> CL{cache-library}
    L --> hdfs-library:::JAR
    L --> bitmap-library:::JAR
    L --> time-library:::JAR
    L --> flink-library:::JAR
    L --> S{spark}
    CL --> hazelcast-cache-service:::JAR
    CL --> jcs-cache-service:::JAR
    S --> spark-accumulo-library:::JAR
    S --> spark-library:::JAR
    E --> RT{road-traffic}
    E --> B{basic}
    E --> federated-demo:::JAR
    RT --> road-traffic-model:::JAR
    RT --> road-traffic-demo:::JAR
    RT --> road-traffic-generators:::JAR
    B --> basic-model:::JAR
    B --> basic-rest:::WarDemo

    classDef parentPOM fill:lightgreen;
    classDef JAR fill:lightblue;
    classDef WarDemo fill:lightcoral;
    
    class PP,C,SI,RI,L,E,CL,S,RT,B parentPOM
```
