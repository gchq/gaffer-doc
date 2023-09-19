# Operations

Querying in Gaffer is all mainly based around whats called "Operations". You can
think of these as lots of small functions/commands that can be either standalone
or be chained together into an Operation Chain to form more complex query
pipelines.

If you have ever used a shell language such as
[bash](https://www.gnu.org/software/bash/) Gaffer queries work on a similar
principal where you have lots of smaller self contained commands (aka
Operations) that can work together to form more complicated use cases.

The general structure of an Operation when using JSON via the rest API looks
like the following:

```json
{
    "class": "Operation",
    "input": "Input"
}
```

As you can see its fairly simple, we first define the Operation we wish to use
with the `class` key which needs to be an available Operation in Gaffer. Then
if applicable, we give the Operation some sort of input using the `input` key.
Not all Operations require an input but more often that not some user input is
required, the [reference guide](../../../reference/operations-guide/core.md) has
a full list of all Operations and example usage.

!!! tip
    To get a list of all available Operations for a graph you can use the
    endpoint `/graph/operations`

## Operation Chains

The Operations in Gaffer can be chained together to form complex queries on the
graph. This page will give some general example usage of how you can chain
Operations together.

As an example of a simple operation, say we want to get all nodes and edges
based on their ID. To do this we can use the `GetElements` operation and set the
`EntitySeed` to the entity (e.g. node) or edge where we want to start the search.

!!! example ""

    Assuming the entity ID we wish to search from is `"v1"`.

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
        elements = g_connector.execute_operation(
            operation = gaffer.GetElements(input = [gaffer.EntitySeed(vertex = "v1")])
        )
        ```

    === "Java"

        ```java
        final GetElements operation = new GetElements.Builder()
            .input(new EntitySeed("v1"))
            .build();

        graph.execute(operation, user);
        ```

This can then be expanded into a chain by using the output from the
`GetElements` operation as the input to the `Count` operation to give a total of
how many entities the `GetElements` returned.

!!! example ""
    As you can see we have used the `OperationChain` to run two operations in a
    chain with the output of one being the input of the next.

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
        count = g_connector.execute_operation_chain(
            operation_chain = gaffer.OperationChain(
                operations=[
                    gaffer.GetElements(input = [gaffer.EntitySeed(vertex = "v1")]),
                    gaffer.Count()
                ]
            )
        )
        ```

    === "Java"

        ```java
        OperationChain<Long> countElements = new OperationChain.Builder()
            .first(new GetElements.Builder().input(new EntitySeed("v1")).build())
            .then(new Count<>())
            .build();

        Long result = graph.execute(countElements, user);
        ```

To chain operations its important to take note of what each operations input and
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
    The [reference guide](../../../reference/operations-guide/core.md) and
    [Javadoc](../../../reference/javadoc.md) can be valuable to understand what
    each `Operation` outputs and accepts.
