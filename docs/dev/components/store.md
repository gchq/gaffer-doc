# Store

The [store module](https://github.com/gchq/Gaffer/tree/master/core/store) defines the API for Store implementations. The abstract Store class handles Operations by delegating the Operations to their registered handlers.

See the [Store Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/package-summary.html) for further documentation.

## Writing a Store

When implementing a Store, the main task is to write handlers for the operations your Store chooses to support. This can be tricky, but the [Store Integration test suite](./integration-test.md) should be used by all Store implementations to validate these operation handlers. When writing these handlers you should implement `OperationHandler` or `OutputOperationHandler` depending on whether the operation has an output.

Store implementations need to define a set of `StoreTraits`. These traits tell Gaffer the abilities the Store has. For example the ability to aggregate or filter elements.

## Schema

In addition to `OperationHandlers` the other large part of the store module is the Schema. The Schema is what defines what is in the Graph and how it should be persisted, compacted (summarised) and validated.
