# Extending Gaffer

Extending Gaffer can mean a few things to a developer, this guide aims to cover
general use cases for how and why you may want to extend the capabilities or add
customisation to Gaffer.

## Writing Custom Classes

As Gaffer is Java based to create and load additional libraries you will first
need to write your custom classes. Gaffer
allows for many places to use custom classes such as, custom operations,
aggregation functions, element generators etc.

Depending on what type of class you are writing, e.g. an `Operation`, you may
need to extend or implement one of the interface classes already in Gaffer.
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

In Gaffer we use the term [Operation](./project-structure/components/operation.md)
to describe some component of a manipulation/query affecting a graph, these are
chained together in `OperationChains` and this is how we execute
manipulations/queries to our graphs. Whilst there are a wealth of existing
operations and their associated handlers that are provided out of the box with
Gaffer. In this section we'll talk about how you might go about adding a new
operation/handler with some simple examples.

#### The Operation Interface

All Operations must adhere to and implement the [`Operation`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/Operation.html)
interface, this interface describes the building blocks of an operation.

One important distinction here is that an implementation of `Operation` should not
contain the actual logic for an `Operation`, think of these as a task definition
or an instruction manual for what an `Operation` should do. This then goes hand in
hand with our `OperationHandler`(s) which define the actual functionality,
either generically or split into multiple handlers for different stores.

#### Selecting an Interface

In general, most `Operation` implementations don't actually directly implement the
`Operation` Interface, they actually implement one or more extended interfaces.
For example the `Input` and `Output` interfaces extend on top of the core `Operation`
interface and concordantly the `InputOutput` interface is a combination of these.
These all live inside the Gaffer Core library under the `Operation` subdirectory
(see the [component breakdown](./project-structure/components/operation.md) for
more details).

#### Implementing an Interface

The example we'll use here is the
[`ToSet`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToSet.html)
Operation, this takes an `Iterable` of `Elements` and converts it into a
`Set`, a handy way to remove duplicate `Elements` if you're doing multiple hops
and end up getting duplicates back.

Taking a look at the [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToSet.html)
for this class you should be able to see this class it implements both
`InputOutput`, with an input type of `Iterable<? extends T>` and an output type
of `Set<? extends T>` and `MultiInput<T>`. Within the class it also implements
all the requisite methods eg, `shallowClone()`, `setInput()`, `getOptions()`
etc.

#### How to Write the Handler

Using the same `ToSet` Operation from before, now that we have this definition
of an operation, we need to actually tell Gaffer how to do it. We do this by
defining either a single generic `Handler` and linking it to the Operation or in
many cases, we might want different implementations based on the store being used
so we'd write a number of operation handlers and decide which one is needed at
runtime.

Here is the [`ToSetHandler`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/operation/handler/output/ToSetHandler.html)
which implements the appropriate `Handler` interface (in this case the
`OutputOperationHandler`). It takes in the `ToSet` Operation that has been built
with all the options etc. defined and then does the actual `doOperation()` logic to
stream our Iterable's contents into a `LinkedHashSet`. This and most other
handlers live inside the [Store library](./project-structure/components/store.md).

#### How does Gaffer know which Handler to use?

Handlers are registered in the `Store` class via the `addOpHandlers()` method,
specifically for our example  `addOperationHandler(ToSet.class, new
ToSetHandler<>())` is called to register it. Once this is done we can then
use our `Operation` and its `Handler` in a user defined `OperationChain` to effect
some query on our graph data.

You can alternatively use the an operations declarations config file (typically
called `operationsDeclarations.json`) which can be used to define the
`Operation` and its Handler like below:

```json
{
    "operations": [
        {
            "operation": "uk.gov.gchq.gaffer.operation.impl.output.ToSet",
            "handler": {
                "class": "uk.gov.gchq.gaffer.store.operation.handler.output.ToSetHandler"
            }
        }
    ]
}
```

### Writing an Element/Object Generator

There are two types of generators used, one to transform a domain object into a
Gaffer `Element` and one to transform the other way. These generators are the
`ElementGenerator` and `ObjectGenerator`.

Living within the Core/Data section of the repository these generator interfaces
are quite simple and as with `Operation` are extended by other interfaces to
provide a more nuanced set of interfaces you could choose to use over the core
Element/Object generator interfaces.

As an example we will walk through the one of the existing generators that can
take a Neo4j CSV file and convert the entries to Gaffer `Elements`.

```java
/**
 * An {@link OpenCypherCsvElementGenerator}s that will generate
 * Gaffer Elements from Neo4j CSV strings.
 */
@Since("2.0.0")
@Summary("Generates elements from a Neo4j CSV")
public class Neo4jCsvElementGenerator extends OpenCypherCsvElementGenerator {
    @Override
    protected LinkedHashMap<String, String> getFields() {
        return new Neo4jCsvGenerator().getFields();
    }
}
```

This class may appear very simple but the real functionality for this generator
is in the `OpenCypherCsvElementGenerator` that it extends. This generator is an
abstract class that implements the `ElementGenerator` interface and has a lot of
functionality for handling Open Cypher CSVs.

You can view the [full class in the repository](https://github.com/gchq/Gaffer/blob/develop/core/data/src/main/java/uk/gov/gchq/gaffer/data/generator/OpenCypherCsvElementGenerator.java)
which has the logic for converting the CSV format but the key bit to take note
of is the `apply(final Iterable<? extends String> strings)` method. This method
is the main override which will be passed the lines of a CSV file to convert and
return a `Iterable<? extends Element>` e.g. a list of `Elements`.

This pattern is repeated across many of the different generators and can be used
as inspiration for your custom generator. As with the Operations so long as
you're implementing the appropriate interfaces it's hard to go far wrong.

### Writing an Aggregation Function

We can provide a custom aggregation function in a number of ways, most of the
aggregation functionality is provided by the [Koryphe library](https://github.com/gchq/koryphe)
sat adjacent to Gaffer. If we want to provide a new "generic" aggregator we
would add it in this Koryphe library. For example lets take a look at the very
simple `Max` comparator, this takes a pair of Java 8 `Comparables` and finds the
highest value one, this function is applied as an aggregation.

```java
@Since("1.0.0")
@Summary("Calculates the max value")
public class Max extends KorypheBinaryOperator<Comparable> {
    @Override
    protected Comparable _apply(final Comparable a, final Comparable b) {
        return a.compareTo(b) >= 0 ? a : b;
    }
}
```

If we want to add something very specific to a store type or some other
restriction we can add this into the appropriate Store location within Gaffer,
An example of this is the `HyperLogLogPlusAggregator` in the Sketches library,
this merges HLLPs together.

```java
@Since("1.0.0")
@Summary("Aggregates HyperLogLogPlus objects")
@Deprecated
public class HyperLogLogPlusAggregator extends KorypheBinaryOperator<HyperLogLogPlus> {
    @Override
    protected HyperLogLogPlus _apply(final HyperLogLogPlus a, final HyperLogLogPlus b) {
        try {
            a.addAll(b);
        } catch (final CardinalityMergeException exception) {
            throw new RuntimeException("An Exception occurred when trying to aggregate the HyperLogLogPlus objects", exception);
        }
        return a;
    }
}
```

There is another way to add aggregators by loading a JAR into Accumulo and
accessing it on the classpath, this is more something an administrator
customising their usage of the platform might do rather than a developer. For
development purposes it's usually good to check if you can make use of an
existing function in Koryphe first, if not then you can then write your own to
be loaded into the classpath. As long as the new function extends a
`KorypheBinaryOperator<T>` then it can be used as an aggregation function for
Gaffer.

## Loading Custom Libraries

Once you have written your custom classes to make them available the simplest way is to
compile to a JAR and load on the Java class path at runtime.

If you are using the container images this is as simple as adding your JAR(s) to
a directory in the image, this is explained in detail in the [deployment guide](../administration-guide/gaffer-deployment/gaffer-docker/gaffer-images.md#adding-additional-libraries).
