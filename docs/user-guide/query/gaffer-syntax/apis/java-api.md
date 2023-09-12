# Querying Using Java

As Gaffer is written in Java there is native support to allow use of all its
public classes. Using Gaffer via the Java interface does differ from the rest
API and `gafferpy` but is fully featured with extensive
[Javadocs](https://gchq.github.io/Gaffer/overview-summary.html).

## Querying a Graph

Using Java to Query a graph unlike the other APIs requires a reference to a
`Graph` object that essentially represents a graph.

With the other APIs you would connect directly to a running instance via the
rest interface; however, to do this with Java you would need to configure a
`Graph` object with a proxy store.
