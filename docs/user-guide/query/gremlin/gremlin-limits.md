# GafferPop Limitations

There are several restrictions with the current implementation. Many of these
can be attributed to the fundamental differences between the two technologies
but some features may also be yet to be implemented.

Current TinkerPop features not present in the GafferPop implementation:

- Property index for allowing unseeded queries.
- Using a Gaffer user for authentication. Access control and authorisations are not supported
as these are based on users.
- Removal of entities/edges or properties - base Gaffer currently does not
  support this.
- TinkerPop Graph Computer is not supported.
- Graph Variables can't be updated - there is limited support for this in base
  Gaffer as usually requires shutting down and restarting the Graph.
- TinkerPop Transactions are not supported.

Current known limitations or bugs:

- The entity group `id` is reserved for an empty group containing only the
  vertex ID, this is currently used as a workaround for other limitations.
- When you get the in or out Vertex directly off an Edge it will not contain any
  actual properties or be in correct group/label - it just returns a vertex in
  the `id` group. This is due to Gaffer allowing multiple entities to be
  associated with the source and destination vertices of an Edge.
- Limited support for updating properties, this has partial support via Gaffer's
  ingest aggregation mechanism.
- Performance compared to standard Gaffer OperationChains is hampered due to
  a custom `TraversalStratergy` not being implemented.
- The ID of an Edge follows a specific format that is made up of its source and
  destination IDs like `[source, dest]`. To use this in a seeded query you must
  format it like `g.E("[source, dest]")` or a list like
  `g.E(["[source1, dest1]","[source2, dest2]"])`
- Issues seen using `hasKey()` and `hasValue()` in same query.
- May experience issues using the `range()` query function.
- May experience issues using the `where()` query function.
