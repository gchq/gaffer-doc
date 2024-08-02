# GafferPop Custom Functions

This page details each additional function that is available in
the Gaffer Gremlin API (GafferPop). These functions may be exclusive
to GafferPop so are likely not available in other databases that
support the Gremlin query language.

## OpenCypher Extensions

The following are additional functions provided by the [OpenCypher extensions](https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-extensions)
for Gremlin.

## cypherToString

Can be used to cast the input value to a `String` type.

!!! example

    ```groovy
    g.V().values('age').map(cypherToString()).toList()
    ```
