# Operation

The [operation module](https://github.com/gchq/Gaffer/tree/master/core/operation) contains the `Operation` interfaces and core operation implementations.

It is assumed that all Gaffer graphs will be able to handle these core operations.

An `Operation` implementation defines an operation to be processed on a graph, or on a set of results which are returned by another operation. An `Operation` class contains the configuration required to tell Gaffer how to carry out the operation. For example, the `AddElements` operation contains the elements to be added.
The `GetElements` operation contains the seeds to use to find elements in the graph and the filters to apply to the query.
The operation classes themselves should not contain the logic to carry out the operation (as this may vary between the different supported store types), just the configuration.

For each operation, each Gaffer store will have an `OperationHandler`, where the processing logic is contained. This enables operations to be handled differently in each store.

Operations can be chained together to form an `OperationChain`. When an operation chain is executed on a Gaffer graph the output of one operation is passed to the input of the next.

An `OperationChain.Builder` is provided to help with constructing a valid operation chain - it ensures the output type of an operation matches the input type of the next.

## How to write an Operation

Operations should be written to be as generic as possible to allow them to be applied to different graphs/stores.

Operations must be JSON serialisable in order to be used via the REST API - i.e. there must be a public constructor and all the fields should have getters and setters.

Operation implementations need to implement the `Operation` interface and the extra interfaces they wish to make use of. For example an operation that takes a single input value should implement the `Input` interface.

Here is a list of some of the common interfaces:

- `uk.gov.gchq.gaffer.operation.io.Input`
- `uk.gov.gchq.gaffer.operation.io.Output`
- `uk.gov.gchq.gaffer.operation.io.InputOutput` - Use this instead of Input and Output if your operation takes both input and output.
- `uk.gov.gchq.gaffer.operation.io.MultiInput` - Use this in addition if your operation takes multiple inputs. This will help with JSON serialisation.
- `uk.gov.gchq.gaffer.operation.Validatable`
- `uk.gov.gchq.gaffer.operation.graph.OperationView`
- `uk.gov.gchq.gaffer.operation.graph.GraphFilters`
- `uk.gov.gchq.gaffer.operation.graph.SeededGraphFilters`

Each operation implementation should have a corresponding unit test class that extends the `OperationTest` class.

Operation implementations should override the close method and ensure all closeable fields are closed.

The core Gaffer operations need to be registered in the `Store` class, with their respective handlers. The `StoreTest` unit test also needs similar information.

### Annotations

Any fields that are required should be annotated with the Required annotation.
As well, each `Operation` class now also requires the Since annotation, detailing to which version of Gaffer it was introduced. To demonstrate:
```java
@Since("1.4.0")
public class NewOperation extends Operation {

    @Required
    private String requiredField;
    ...
}
```

### Builder

All implementations should also have a static inner `Builder` class that implements the required builders. For example:

```java
public static class Builder extends Operation.BaseBuilder<GetElements, Builder>
        implements InputOutput.Builder<GetElements, Iterable<? extends ElementId>, CloseableIterable<? extends Element>, Builder>,
        MultiInput.Builder<GetElements, ElementId, Builder>,
        SeededGraphFilters.Builder<GetElements, Builder>,
        Options.Builder<GetElements, Builder> {
    public Builder() {
            super(new GetElements());
    }
}
```

### Operation Scores

For use with a `ScoreOperationChain`, some `Operation`s may require a custom way of calculating an associated score, therefore an implementation of the `ScoreResolver` interface may be required.
There is a `DefaultScoreResolver` to which the custom implementation should delegate, in a manner specific to the new Operation. For more info, see [ScoreOperationChain](/docs/reference/stores-guide/stores.md#scoreoperationchain) and [ScoreOperationChainExample](../../reference/operations-guide/misc.md#scoreoperationchain).

### Documentation

Class-level Javadoc should be provided for each `Operation`, giving a description of the functionality, and any configuration information that may not immediately be obvious.
Member-level Javadoc is not strictly necessary, but good practice for explanations/clarifications of complex methods.

To assist users of the new Operation, it is best practice to provide documentation, and simple usage examples in [gaffer-doc](https://github.com/gchq/gaffer-doc).
