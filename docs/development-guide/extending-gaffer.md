# Extending Gaffer

Extending Gaffer can mean a few things to a user, this guide aims to cover
general use cases for how and why you may want to extend the capabilities or add
customisation to Gaffer.

## Writing Custom Classes

As Gaffer is Java based to create and load additional libraries you will first
need to write your custom classes. Now these can be for a range of uses, Gaffer
allows for many places to use custom classes such as, custom operations,
aggregation functions, element generators etc.

Depending on what type of class you are writing e.g. an `Operation` you may
need extend or implement one of the interface classes already in Gaffer.
Assuming you are using Maven for package management you can add Gaffer as
a dependency to gain access to its classes like below:

```xml
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>${gaffer.component}</artifactId>
    <version>${gaffer.version}</version>
</dependency>
```

!!! tip
    See the [components breakdown](./project-structure/components/components.md)
    for a list of key Gaffer modules.

### Writing an Operation

!!! note
    TODO

### Writing an Element Generator

!!! note
    TODO

### Writing an Aggregation Function

!!! note
    TODO

## Loading Custom Libraries

Once you have written to make custom classes available the simplest way is to
compile to a JAR and load on the Java class path at runtime.

If you are using the container images this is as simple as adding your JAR(s) to
a directory in the image, this is explained in detail in the [deployment
guide](../administration-guide/gaffer-deployment/gaffer-docker/gaffer-images.md#adding-additional-libraries).
