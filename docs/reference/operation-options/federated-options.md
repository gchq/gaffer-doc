# Operation Options for a Federated Store

This page details the available options that can be passed to a Gaffer instance
that is backed with a [Federated Store](../../administration-guide/gaffer-stores/simple-federated/configuration.md).
The options can be passed to any individual operation or overall operation
chain to affect when/how they are applied.

## Use Default Graph IDs

Key: `federated.useDefaultGraphIds`

Default: `None`

Explicitly specifies that the default Graph IDs from the `store.properties` file
should be used. Specifying this on an operation chain means the whole chain will
be sent to the default sub graph(s), and so merging from each graph will happen
at the end of the chain instead of after each operation, likely increasing
performance on longer chains.

!!! note
    If no graph ID options are specified e.g. `federated.graphIds`, the
    default graph IDs will still be used where applicable.

!!! example

    ```json
    {
        "class": "OperationChain",
        "options": {
            "federated.useDefaultGraphIds": true
        },
        "operations": [
            {
                "class": "GetElements",
                ,
                "input": [
                    {
                        "class": "EntitySeed",
                        "vertex": "1"
                    }
                ]
            },
            {
                "class": "Count"
            }
        ]
    }
    ```

## Specify Graph IDs

Key: `federated.graphIds` or `gaffer.federatedstore.operation.graphIds`

Default: `None`

List of graph IDs to submit the operation to, formatted as a comma separated
string e.g. `"graph1,graph2"`. If an option to set the graph IDs is not set
then the [default graph IDs](../store-properties/federated-store.md#gafferstorefederateddefaultgraphids)
will be used as a fallback.

!!! example

    ```json
    {
        "class": "GetAllElements",
        "options": {
            "federated.graphIds": "graph1,graph2,graph3"
        }
    }
    ```

## Exclude Graph IDs

Key: `federated.excludeGraphIds`

Default: `None`

Comma separated list of graph IDs to exclude from the query. If this is set any graph IDs on a
`federated.graphIds` option are ignored and instead, all graphs are executed on
except the ones specified.

!!! example

    ```json
    {
        "class": "GetAllElements",
        "options": {
            "federated.excludeGraphIds": "graph2,graph5"
        }
    }
    ```

## Aggregate Elements

Key: `federated.aggregateElements`

Default: [Set in store properties](../store-properties/federated-store.md#gafferstorefederateddefaultaggregateelements).

Should the element aggregator be used when merging element results. This will
override the default which might be set in the store properties. Enabling this
has some additional considerations you should be aware of, please see the [store guide](../../administration-guide/gaffer-stores/simple-federated/configuration.md#considerations)
for details.

!!! example

    ```json
    {
        "class": "GetAllElements",
        "options": {
            "federated.aggregateElements": true
        }
    }
    ```

## Separate Sub Graph Results

Key: `federated.separateResults`

Default: `false`

A boolean option to specify if the results from each graph should be kept
separate. If set, this will return a map where each key value is the graph ID
and its respective result.

!!! warning
    If using the Java API this can cause type casting issues if using generics
    as the output type will be unexpectedly changed. Casting to `Map<String, Object>`
    should avoid this when using this option.

!!! example

    ```json
    {
        "class": "GetAllElements",
        "options": {
            "federated.graphIds": "graph1,graph2",
            "federated.separateResults": true
        }
    }
    ```

    Result:

    ```json
    {
        "graph1": [
            ...
        ],
        "graph2": [
            ...
        ]
    }
    ```

## Skip Graph on Fail

Key: `federated.skipGraphOnFail`

Default: `false`

A boolean option to specify if the operation should continue even if it fails on
one or more of the sub graphs.

!!! example
    The vertex type might not be a string on all the default sub graphs so this
    allows the operation to still run and only give relevant things back.

    ```json
    {
        "class": "OperationChain",
        "options": {
            "federated.useDefaultGraphIds": true,
            "federated.skipGraphOnFail": true
        },
        "operations": [
            {
                "class": "GetElements",
                ,
                "input": [
                    {
                        "class": "EntitySeed",
                        "vertex": "1"
                    }
                ]
            },
            {
                "class": "Count"
            }
        ]
    }
    ```
