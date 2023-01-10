# Federated Store Changes

This page contains information on the changes to Gaffer's Federated Store. This functionality was introduced in version `2.0.0-alpha-0.4` of Gaffer.  
The main changes were the addition of the [Federated Operation](#the-federated-operation), and a change to how results are [merged](#default-results-merging) by default.

## The Federated Operation

The `FederatedOperationChain` was removed and replaced with a new Operation, the `FederatedOperation`. This was added to improve the control you have over how operations are federated.  
The Federated Operation has 3 key parameters: `operation`, `graphIds` and `mergeFunction`:
``` json
{
    "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
    "operation": {
        "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "graphIds": [ "graphA", "graphB" ],
    "mergeFunction": {
        "class": "uk.gov.gchq.gaffer.federatedstore.util.ConcatenateMergeFunction"
    }
}
```

### Required parameter: operation

This is the Operation you wish to be federated to the subgraphs. This can be a single Operation or an OperationChain. If you use an OperationChain, then the whole chain will be sent together to the subgraphs.  

### Optional parameter: graphIds

This is a list of graph IDs which you want to send the operation to. If you have an OperationChain and wish to send each operation to a different graph, then each operation in the chain will need to be wrapped in a FederatedOperation with the `graphIds` set to the required graph. If instead you want to send the whole OperationChain to the same graphs, then this field can be set on one FederatedOperation where the `operation` is an OperationChain.  

If this parameter is not provided, then the default graph IDs for the store will be used. This is the `storeConfiguredGraphIds` for the store if set, if not set then all graphIds will be used.

### Optional parameter: mergeFunction

The `mergeFunction` parameter is the BiFunction you want to use when merging the results from the subgraphs.  

If this parameter is not provided, then the store's `storeConfiguredMergeFunctions` will be used, which have default values but can be overriden. For example, by default when GetElements is used as the operation inside a FederatedOperation, then `ApplyViewToElementsFunction` will be used to merge the results. Instead, this can be overriden, like in the example to something like `ConcatenateMergeFunction`, or even a custom Function.


## Migrating to a FederatedOperation

Previously, graphIds were selected with the now deprecated option: `gaffer.federatedstore.operation.graphIds`. This is being partially supported temporarily while users migrate to using a FederatedOperation, but there are some scenarios in which the option will no longer work.

### Sending an Operation to specific stores

As mentioned, the `gaffer.federatedstore.operation.graphIds` option is still being temporarily supported so if you have an Operation using that option, it should continue to work. It will still work if the option is being used **inside** an OperationChain. However, if the option is being used **on** an OperationChain, then see [below](#breaking-change).  
Despite the option being still supported, we still recommend you migrate to using a FederatedOperation.  

#### Deprecated graphIds option on a single Operation
```json
{
    "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "options": {
        "gaffer.federatedstore.operation.graphIds": "graphA"
    }
}
```

#### New FederatedOperation graphIds on a single Operation
``` json
{
    "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
    "operation": {
        "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "graphIds": [ "graphA" ]
}
```

#### Deprecated graphIds option inside an OperationChain
```json
{
    "class": "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations": [
        {
            "class": "ExampleOperation1",
            "options": {
                "gaffer.federatedstore.operation.graphIds": "graphA"
            }
        },
        {
            "class": "ExampleOperation2",
            "options": {
                "gaffer.federatedstore.operation.graphIds": "graphB"
            }
        }
    ]
}
```

#### New FederatedOperation graphIds inside an OperationChain
```json
{
    "class": "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations": [
        {
            "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
            "operation": {
                "class": "ExampleOperation1"
            },
            "graphIds": [ "graphA" ]
        },
        {
            "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
            "operation": {
                "class": "ExampleOperation2"
            },
            "graphIds": [ "graphB" ]
        }
    ]
}
```

### Breaking change

#### No longer supported graphIds option on an OperationChain
Previously, if you wanted to send an entire OperationChain to a specific graph, you could use the `graphIds` option on the chain, like so:  
```json
{
    "class": "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations": [
        {
            "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
        }
    ],
    "options": {
        "gaffer.federatedstore.operation.graphIds": "graphA"
    }
}
```

#### New FederatedOperation graphIds on an OperationChain
This is no longer supported, and you should instead wrap the chain in a FederatedOperation:
``` json
{
    "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
    "operation": {
        "class": "uk.gov.gchq.gaffer.operation.OperationChain",
        "operations": [
            {
                "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
            }
        ]
    },
    "graphIds": [ "graphA" ]
}
```

## Default results merging

As described above, FederatedStores now have `storeConfiguredMergeFunctions` that dictate how the FederatedStore will merge results from different subgraphs dependent on the Operation.  

In places, these new defaults do differ from previous behaviour, hence results will too. This can be overriden on a per Operation basis using the `mergeFunction` parameter described above, or a per store basis by overriding `storeConfiguredMergeFunctions`.  
The previous behaviour was that all Operation results were concatenated together, this is now a mergeFunction within Gaffer called `ConcatenateMergeFunction`. Therefore, if you wanted a FederatedOperation to use this old behaviour, you can set the `mergeFunction` to `ConcatenateMergeFunction` (as shown [above](#the-federated-operation)).  

### New Merge function examples

By default, `GetElements` results will be merged with `ApplyViewToElementsFunction`. This uses the View from the operation and applies it to all of the results, meaning the results are now aggregated and filtered using the Schema too. This makes the results look like they came from one graph, rather than getting back a list of Elements from different subgraphs.  

By default, `GetTraits` results will be merged with `CollectionIntersect`. This returns the intersection of common store traits from the subgraphs, rather than all of the traits concatenate, meaning it is a better representation of traits all the subgraphs have.  

### Default storeConfiguredMergeFunctions

| Operation         | Merge function              |
|-------------------|-----------------------------|
| GetElements       | ApplyViewToElementsFunction |
| GetAllElements    | ApplyViewToElementsFunction |
| GetSchema         | MergeSchema                 |
| GetTraits         | CollectionIntersect         |
| others            | ConcatenateMergeFunction    |

## Other changes

Previously, if the FederatedStore received an Operation it did not have an OperationHandler for locally, the Operation would fail to execute. Now, the Operation will just be handed over to the subgraphs to be executed, as they may have the correct OperationHandlers. This should make it easier to use a ProxyStore with a FederatedStore, as the FederatedStore will no longer need to have every OperationHandler present on the remote ProxyStore.  
