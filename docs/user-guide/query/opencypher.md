# openCypher in Gaffer

!!! warning
    The openCypher API is still experimental, it is provided by a
    translation layer to Gremlin from the [OpenCypher project](https://github.com/opencypher/cypher-for-gremlin).
    Due to this, the implementation may experience the same [limitations](../query/gremlin/gremlin-limits.md)
    as the Gremlin API. It's performance is unknown but likely slower than
    Gremlin or Standard Gaffer Operations.

## openCypher Querying

Generally the syntax and features of using openCypher in Gaffer are the same as
using Cypher in other graph databases. Most of the features you will have used
in standard Cypher should be available. The layer in Gaffer targets
[openCypher v9](https://s3.amazonaws.com/artifacts.opencypher.org/openCypher9.pdf)
meaning any features outside of that version cannot be guaranteed.

Full guides on querying using Cypher are available elsewhere however, a few useful
queries to get you started are available here. Translation of what exact Gremlin query
this maps to is provided.

!!! example ""
    Seeded vertex query using string IDs.

    === "cypher"
        ```cypher
        MATCH (n) WHERE ID(n) IN ['0', '1', '2', '3'] RETURN n
        ```

    === "Gremlin"
        ```groovy
        g.V().has('~id', within('0', '1', '2', '3')).project('n').by(__.valueMap().with('~tinkerpop.valueMap.tokens')).toList()
        ```

!!! example ""
    Seeded edge query using string IDs.

    === "cypher"
        ```cypher
        MATCH (s)-[r]->(d) WHERE ID(r) IN ['[0, 1]', '[2, 3]'] RETURN r
        ```

    === "Gremlin"
        ```groovy
        g.E().has('~id', within('[0, 1]', '[2, 3]')).project('r').by(__.project('  cypher.element', '  cypher.inv', '  cypher.outv').by(__.valueMap().with('~tinkerpop.valueMap.tokens')).by(__.inV().id()).by(__.outV().id())).toList()
        ```

!!! example ""
    Filtering on group and properties.

    === "cypher"
        ```cypher
        MATCH (n:person) WHERE n.age > toInteger(25) AND n.`full-name` CONTAINS 'John' RETURN n
        ```

    === "Gremlin"
        ```groovy
        g.V().as('n').hasLabel('person').has('full-name', containing('John')).where(__.constant(25d).map(cypherToInteger()).is(neq('  cypher.null')).as('  GENERATED1').select('n').values('age').where(gt('  GENERATED1'))).select('n').project('n').by(__.choose(neq('  cypher.null'), __.valueMap().with('~tinkerpop.valueMap.tokens'))).toList()
        ```

!!! example ""
    Transform and project on properties.

    === "cypher"
        ```cypher
        MATCH (n) RETURN (n.age * 1000), reverse(n.name)
        ```

    === "Gremlin"
        ```groovy
        g.V().as('n').project('(n.age * 1000)', 'reverse(n.name)').by(__.constant(1000).as('__GENERATED1').select('n').choose(neq('  cypher.null'), __.choose(__.values('age'), __.values('age'), __.constant('  cypher.null'))).choose(__.or(__.is(eq('  cypher.null')), __.select('__GENERATED1').is(eq('  cypher.null'))), __.constant('  cypher.null'), __.math('_ * __GENERATED1'))).by(__.choose(neq('  cypher.null'), __.choose(__.values('name'), __.values('name'), __.constant('  cypher.null'))).map(cypherReverse())).toList()
        ```

## Limitations and Considerations

There are a few limitations you need to be aware of using this API. Generally
these stem from the translation layer but are also due to a fundamental
difference in the way Gaffer and Cypher are intended to be used.

- If using the `with()` step all numbers are longs by default you need to
  specifically change them to integers if required e.g. `toInteger(1)`. You can
  also change them to floats with `toFloat(1.2)`.
- How data is returned is different to normal Gremlin. It will be returned as
  key value maps where each `RETURN` in the cypher query is a key.
- Currently the version of the openCypher translator is stuck at v1.0.0 due to
  the Gaffer scala version. This means not all features of openCypher are
  available e.g. no `replace()` function. The [reference guide](../../reference/gremlin-guide/custom-functions.md)
  attempts to document all custom Cypher functions available.
- Need to be considerate of how it maps to Gremlin and Gaffer as something like
  this: `MATCH (n) WHERE (n:person OR n:software)` will do a `GetAllElements`
  with nothing in the Gaffer View. In this case you should use an `OPTIONAL MATCH` instead.
- Gaffer groups or properties with a `-` in require wrapping in back ticks.
