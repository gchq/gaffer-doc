# Using the Java API

As Gaffer is written in Java there is native support to allow use of all its
public classes. Using Gaffer via the Java interface does differ from the REST
API and `gafferpy` but is fully featured with extensive
[Javadocs](https://gchq.github.io/Gaffer/overview-summary.html). However, you
will of course need to be familiar with writing and running Java code in order
to utilise this form of the API.

## Querying a Graph

Using Java to query a graph, unlike the other APIs, requires a reference to a
`Graph` object that essentially represents a graph.

With the other APIs, you would connect directly to a running instance via the
REST interface; however, to do this with Java you would need to configure a
`Graph` object with a [proxy store](../../administration-guide/gaffer-stores/proxy-store.md).

!!! example ""
    The following example uses the `ProxyStore.Builder()` to configure a `Graph`
    to connect to the required address (in this case
    `http://localhost:8080/rest`).

    ```java
    Graph graph = new Graph.Builder()
        .store(new ProxyStore.Builder()
                .graphId(uniqueNameOfYourGraph)
                .host("localhost")
                .port(8080)
                .contextRoot("rest")
                .build())
        .build();
    ```

Once the connection to a graph is made you can run queries and operations on
it using the available classes and builders.

!!! example ""
    The following operation chain gets all the elements in the graph then will
    count them and store the result in a `Long`.

    ```java
    OperationChain<Long> countAllElements = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new Count<>())
        .build();

    Long result = graph.execute(countAllElements, user);
    ```

If you're interested in learning more about the `Graph` class please see the
[developers guide](../../development-guide/project-structure/components/graph.md)
on the subject.

!!! note
    One other thing to note about the Java API is that the `execute()` method
    requires you to have a `User`. This is part of Gaffer's fine grain security,
    see the [admin guide](../../administration-guide/security/security-guide.md)
    for more information.
