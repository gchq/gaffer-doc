# GafferPop Limitations

There are several restrictions with the current implementation. Many of these
can be attributed to the fundamental differences between the two technologies
but some features may also be yet to be implemented.

Current TinkerPop features not present in the GafferPop implementation:

- Unseeded queries run a `GetAllElements` with a configured limit applied,
  this limit can be configured per query or will default to 5000.
- Gaffer graphs are readonly to Gremlin queries.
- TinkerPop Graph Computer is not supported.
- TinkerPop Transactions are not supported.
- TinkerPop Lambdas are not supported.

Current known limitations or bugs:

- Proper [user authentication](../../../administration-guide/gaffer-deployment/gremlin.md#user-authentication)
  is only available if using a Gremlin server and the `GafferPopAuthoriser` class.
- Performance compared to standard Gaffer `OperationChain`s will likely be
  slower as multiple Gaffer `Operations` may utilised to perform one Gremlin
  step.
- Edge IDs in GafferPop are not the same as in standard Gremlin. Instead of `g.E(11)`
  edge IDs take the format `g.E("[source, dest]")` or `g.E("[source, label, dest]")`.
- The entity group `id` is reserved for an empty group containing only the
  vertex ID, this is currently used as a workaround for other limitations.
- Chaining `hasLabel()` calls together like `hasLabel("label1").hasLabel("label2")`
  will act like an OR rather than an AND in standard Gremlin. This means you
  may get results back when you realistically shouldn't.
