# Operations

Querying in Gaffer is all mainly based around whats called "Operations". You can
think of these as lots of small functions/commands that can be either standalone
or be chained together into an Operation Chain to form more complex query
pipelines.

If you have ever used a shell language such as
[bash](https://www.gnu.org/software/bash/) Gaffer queries work on a similar
principal where you have lots of smaller self contained commands (aka
Operations) that can work together to form more complicated use cases.

The general structure of an Operation when using JSON via the REST API looks
like the following:

```json
{
    "class": "Operation",
    "input": "Input"
}
```

Using Operations is fairly simple, we first define the Operation we wish to use
with the `class` key which needs to be an available Operation in Gaffer. Then
if applicable, we give the Operation some sort of input using the `input` key.
Not all Operations require an input but more often that not some user input is
required, the [reference guide](../../../reference/operations-guide/operations.md) has
a full list of all Operations and example usage.

!!! tip
    To get a list of all available Operations for a graph you can use the
    endpoint `/graph/operations`

## Operation Chains

The Operations in Gaffer can be chained together to form complex graph queries.
This page will give some general example usage of how you can chain Operations
together.

As an example of a simple operation, say we want to get all entities and edges
based on their ID. To do this we can use the `GetElements` operation and set the
`EntitySeed` to the vertex or edge where we want to start the search.

!!! example ""

    Assuming the entity ID we wish to search from is `"v1"`.

    === "Java"

        ```java
        final GetElements operation = new GetElements.Builder()
            .input(new EntitySeed("v1"))
            .build();

        graph.execute(operation, user);
        ```

    === "JSON"

        ```json
        {
            "class": "GetElements",
            "input": [
                {
                    "class": "EntitySeed",
                    "vertex": "v1"
                }
            ]
        }
        ```

    === "Python"

        ```python
        elements = gc.execute_operation(
            operation = g.GetElements(input = [g.EntitySeed(vertex = "v1")])
        )
        ```


This can then be expanded into a chain by using the output from the
`GetElements` operation as the input to the `Count` operation to give a total of
how many entities the `GetElements` returned.

!!! example ""
    As you can see we have used the `OperationChain` to run two operations in a
    chain with the output of one being the input of the next.

    === "Java"

        ```java
        OperationChain<Long> countElements = new OperationChain.Builder()
            .first(new GetElements.Builder().input(new EntitySeed("v1")).build())
            .then(new Count<>())
            .build();

        Long result = graph.execute(countElements, user);
        ```

    === "JSON"

        ```json
        {
            "class" : "OperationChain",
            "operations" : [
                {
                    "class": "GetElements",
                    "input": [
                        {
                            "class": "EntitySeed",
                            "vertex": "v1"
                        }
                    ]
                },
                {
                    "class" : "Count"
                }
            ]
        }
        ```
    === "Python"

        ```python
        count = gc.execute_operation_chain(
            operation_chain = g.OperationChain(
                operations=[
                    g.GetElements(input = [g.EntitySeed(vertex = "v1")]),
                    g.Count()
                ]
            )
        )
        ```

To chain operations it's important to take note of what each operations input and
outputs are, say if you want to chain two together like the following:

!!! example ""
    The output of `Operation1` needs to be compatible with the input of
    `Operation2`.

    ```json
    {
        "class" : "OperationChain",
        "operations" : [
            {
                "class": "Operation1"
            },
            {
                "class" : "Operation2"
            }
        ]
    }
    ```

!!! tip
    The [reference guide](../../../reference/operations-guide/operations.md) and
    [Javadoc](../../../reference/javadoc.md) can be valuable to understand what
    each `Operation` outputs and accepts.

## Lazy Results

Operation results are lazy (where possible) so that results are lazily loaded whilst a
user consumes each result. This could be directly in Java or as part of an Operation
Chain, e.g. when running `GetAllElements` followed by `Limit` of 10, only the first 10
elements will be retrieved because of this laziness. This avoids a situation where vast
quantities of elements might be retrieved, only the first 10 to be returned; which would
be very inefficient.

For example, executing a `GetAllElements` on an Accumulo Store backed graph:

```java
final Iterable<? extends Element> elements = graph.execute(new GetAllElements(), getUser());
```

As this `elements` [`Iterable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html)
is lazy, the query is only executed on Accumulo when the object is used to iterate through results.
Iterating through the results a second time results in the Accumulo query being executed again.

As the query is not executed until the `Iterable` is consumed, if in the meantime another element
is added to the graph, then the results from the `Iterable` will contain that new element.

For this reason you should be very careful running an `AddElements` with a lazy iterable returned
from a query on the same Graph. The problem which could arise is that the `AddElements` will lazily
consume the iterable of elements, potentially causing duplicates to be added.

To do a Get followed by an Add on the same Graph, we recommend consuming and caching the Get results
first. For a small number of results, this can be done using the `ToList` operation in the chain:

```java
new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new ToList<>())
                .then(new AddElements())
                .build();
```

For a large number of results, they could be added to a cache temporarily:

```java
new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new ExportToGafferResultCache<>())
                .then(new DiscardOutput())
                .then((Operation) new GetGafferResultCacheExport())
                .then(new AddElements())
                .build()
```

For more details on these Operations, see the sections for [ToList](../../../reference/operations-guide/core.md#tolist)
and [AddElements](../../../reference/operations-guide/core.md#addelements), and the example of using
[GetAllElements with Limit](../../../reference/operations-guide/core.md#limit) in the Core Operations
reference guide, plus the [ExportToGafferResultCache](../../../reference/operations-guide/export.md#exporttogafferresultcache)
and [GetGafferResultCacheExport](../../../reference/operations-guide/export.md#getgafferresultcacheexport)
sections of the Export Operations reference guide.