# What is Gaffer?

Gaffer is a graph database framework, it acts similarly to an interface
providing a graph data structure on top of a chosen storage technology to enable
storage of large graphs and traversal of it's nodes and edges. In a nutshell
Gaffer allows you to take data, convert it into a graph, store it in a database
and then run queries and analytics on it.

The high level interactions of loading data and querying are demonstrated in the
diagrams below.

```mermaid
flowchart TD
    subgraph Graph Query
        G{{Schema}} -.-> F
        F([Query]) ---> H(Gaffer)
        J(key-value store) <--> H
        H --> K([Result])
    end
    subgraph Data Input
        A(Data)-->B{{Schema}}
        B --> C(Gaffer)
        C --> D(key-value store)
    end
```

!!! note
    Knowledge of the [Gaffer schema](../schema.md) in use is usually required to
    formulate a graph query but technically optional hence the dotted connection.

## Why would you want to use Gaffer?

Conceptually a graph database/data platform can have a large variety of uses
where it'll provide specific advantages over other data storage/analytic
platforms which will be discussed in the ["What is Graph?"](./what-is-a-graph.md)
section of these docs. Gaffer provides an extensible and straightforward way to
insert, manage and query the graph data stored in the underlying storage systems
(ie. Accumulo).

Gaffer also has some key features not always found in other graph database
technologies, including but not limited to:

- [Aggregation.](./what-is-aggregation.md)
- [Fine grained security.](../../administration-guide/security/security-guide.md)
- [Graph Federation.](../../administration-guide/gaffer-stores/federated-store.md)
