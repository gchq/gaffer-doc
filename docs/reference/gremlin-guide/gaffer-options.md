# Custom Options for Gremlin

This page details the available options you can pass in a Gremlin
`with()` step when using the GafferPop API for querying.

## Operation Options

Key: `operationOptions`

Allows passing options to the underlying Gaffer Operations, this is the same as
the `options` field on a standard JSON query. This will be applied to all
operations in the query.

Note that any options should be passed as a list or dictionary.

!!! example

    === "Groovy"

        ```groovy
        g.with("operationOptions", ["gaffer.federatedstore.operation.graphIds:graphA"]).V().toList()
        ```

    === "Python"

        ```python
        g.with_("operationOptions", ["gaffer.federatedstore.operation.graphIds:graphA"]).V().to_list()
        ```

        or

        ```python
        g.with_("operationOptions", {"gaffer.federatedstore.operation.graphIds": "graphA"}).V().to_list()
        ```

## GetAllElements Limit

Key `getAllElementsLimit`

Limits the amount of elements returned if performing an unseeded query e.g. a
`GetAllElements` operation. This will override the default for the current
query, see the [admin guide](../../administration-guide/gaffer-deployment/gremlin.md#configuring-the-gafferpop-library)
for more detail on setting up defaults.

!!! example

    ```groovy
    g.with("getAllElementsLimit", 100).V().toList()
    ```

## Has Step Filter Stage

Key: `hasStepFilterStage`

Controls which phase the filtering from a Gremlin `has()` stage is applied to
the results. This will apply to all `has()` steps in the query and override the
default, see the [admin guide](../../administration-guide/gaffer-deployment/gremlin.md#configuring-the-gafferpop-library)
for more detail on setting up defaults.

!!! example

    ```groovy
    g.with("hasStepFilterStage", "PRE_AGGREGATION").V().has("count" P.gt(1)).toList()
    ```

## Cypher Query

Key: `cypher`

Translates the given Cypher query to Gremlin and executes it on the Graph. This
can be used in combination with other `with()` steps as they will be protected
from translation.

!!! example

    ```groovy
    g.with("cypher", "MATCH (p:person) RETURN p").call().toList()
    ```
