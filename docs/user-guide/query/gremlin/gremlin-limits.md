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

- Proper user authentication is only available if using a Gremlin server and
  the `GafferPopAuthoriser` class.
- Performance compared to standard Gaffer `OperationChain`s will likely be
  slower as multiple Gaffer `Operations` may utilised to perform one Gremlin
  step.
- The ID of an Edge must either be made up of its source and
  destination IDs, e.g. `[source, dest]`, or its source, label and destination,
  e.g. `[source, label, dest]`. To use this in a seeded query you must
  format it like `g.E("[source, dest]")` or a list like
  `g.E(["[source1, dest1]","[source2, label, dest2]"])`
- The entity group `id` is reserved for an empty group containing only the
  vertex ID, this is currently used as a workaround for other limitations.
- Chaining `hasLabel()` calls together like `hasLabel("label1").hasLabel("label2")`
  will act like an OR rather than an AND in standard Gremlin. This means you
  may get results back when you realistically shouldn't.
