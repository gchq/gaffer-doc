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

## Get Elements Limit

Key `getElementsLimit`

Limits the amount of elements that can be returned for each `GetElements` or
`GetAllElements` query ran by TinkerPop. This applies a Gaffer `Limit`
operation in the translated operation chain. This will override the default for
the current query, see the [admin guide](../../administration-guide/gaffer-deployment/gremlin.md#configuring-the-gafferpop-library)
for more detail on setting up defaults.

!!! example

    ```groovy
    g.with("getElementsLimit", 100).V().toList()
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

## Include Orphaned Vertices

Key: `includeOrphanedVertices`

The option to set if orphaned vertices should be included in the result.
Orphaned vertices are deemed as vertices on an edge that have no
associated Gaffer entity with them. Enabling this will likely result in slower
query performance as each vertex on an edge needs to be checked. The orphaned
vertices returned will be in a special `id` group. This will override the default for
the current query, see the [admin guide](../../administration-guide/gaffer-deployment/gremlin.md#configuring-the-gafferpop-library)
for more detail on setting up defaults.

!!! example
    ```groovy
    g.with("includeOrphanedVertices", "true").V().toList()
    ```
