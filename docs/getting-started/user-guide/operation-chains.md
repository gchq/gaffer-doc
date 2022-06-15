# Operation Chains

The code for this example is [OperationChains](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/OperationChains.java).

In the previous examples we have had several steps to get data into Gaffer. As promised, we can now simplify this and use an operation chain.

We will show you a way of using an operation chain to both generate the elements from the data file and add them directly to the graph.
Operation chains are simply a list of operations in which the operations are executed sequentially, the output of one operation is passed in as the input to the next operation.

So adding elements from a CSV file can now be done as follows:


{% codetabs name="Java", type="java" -%}
final OperationChain<Void> addOpChain = new OperationChain.Builder()
        .first(new GenerateElements.Builder<String>()
                .generator(new RoadAndRoadUseElementGenerator())
                .input(IOUtils.readLines(StreamUtil.openStream(getClass(), dataPath)))
                .build())
        .then(new AddElements())
        .build();

graph.execute(addOpChain, user);
{%- endcodetabs %}


This chain consists of 2 operations.
The first, GenerateElements, which takes the data and an element generator and generates the Gaffer Edges.
The second, AddElements, simply takes the generated edges and adds them to the graph.
This operation chain can then be executed on the graph as before.

Another example of using an operation chain is when we are traversing the graph.


{% codetabs name="Java", type="java" -%}
final OperationChain<? extends Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed("M5"))
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .then(new GetElements.Builder()
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .build();

final Iterable<? extends Element> results = graph.execute(opChain, user);
{%- endcodetabs %}


This operation chain starts with a seed vertex and traverses down all outgoing edges using the [GetAdjacentIds](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAdjacentIds.html) operation.
It then returns all the following connected edges using the [GetElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html) operation:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3]]
Edge[source=11,destination=10,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=23,destination=24,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>2]]
Edge[source=28,destination=27,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]

```
We can continue to add operations to create longer operation chains.

Suppose we want to convert the edges returned back into csv format similar to the input file (junctionA, junctionB, count). Gaffer allows
us to convert graph elements back into objects using the [GenerateObjects](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateObjects.html) operation. We tell Gaffer how to convert the elements
using an [ObjectGenerator](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/generator/ObjectGenerator.html).


```java
public class RoadUseCsvGenerator implements OneToOneObjectGenerator<String> {
    @Override
    public String _apply(final Element element) {
        if (!(element instanceof Edge && "RoadUse".equals(element.getGroup()))) {
            throw new UnsupportedOperationException("Only RoadUse edges should be used");
        }

        final Edge edge = ((Edge) element);
        return edge.getSource() + "," + edge.getDestination() + "," + edge.getProperty("count");
    }
}
```

Now we'll add the operation to the operation chain we executed before (note that this time the return type has changed from Element to String):

final OperationChain<Iterable<? extends String>> opChainWithCSV =
        new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed("M5"))
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .then(new GetElements.Builder()
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .then(new GenerateObjects.Builder<String>()
                        .generator(new RoadUseCsvGenerator())
                        .build())
                .build();

final Iterable<? extends String> csvResults = graph.execute(opChainWithCSV, user);

When we execute this query we get:

```
10,11,3
11,10,1
23,24,2
28,27,1

```

You can see the data has been converted back into csv.

Operation chains work with any combination of operations where sequential operations have compatible output/input formats.

For more examples of different types of operations see [Operation Examples](../operations/contents).
