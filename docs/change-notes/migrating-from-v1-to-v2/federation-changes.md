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

This is the Operation you wish to be federated to the subgraphs. This can be a single Operation or an OperationChain. If you use an OperationChain, then the whole chain will be sent to the subgraphs.  

### Optional parameter: graphIds

This is a list of graph IDs which you want to send the operation to.  

If the user does not specify `graphIds` in the Operation, then the `storeConfiguredGraphIds` for that store will be used. If the admin has not configured the `storeConfiguredGraphIds` then all graphIds will be used.  

For information on sending different operations in one chain to different subgraphs, see [below](#breaking-change-removal-of-federatedoperationchain).  

### Optional parameter: mergeFunction

The `mergeFunction` parameter is the Function you want to use when merging the results from the subgraphs.  

If the user does not specify a `mergeFunction` then it will be selected from the `storeConfiguredMergeFunctions` for that store. If the admin has not configured the `storeConfiguredMergeFunctions`, it will contain pre-populated `mergeFunctions`. Lastly, if a suitable `mergeFunction` is not found then a default `ConcatenateMergeFunction` is used.  

For example, when GetElements is used as the operation inside a FederatedOperation and the user hasn't specified a `mergeFunction`, the pre-populated `ApplyViewToElementsFunction` will be selected from `storeConfiguredMergeFunctions`, unless the admin configured it to use something else.  


## Migrating to a FederatedOperation

Previously, graphIds were selected in queries with the now deprecated option: `gaffer.federatedstore.operation.graphIds`. This is being supported while users migrate to using a FederatedOperation.  

### Sending an Operation to specific stores

As mentioned, the `gaffer.federatedstore.operation.graphIds` option is still being supported so if you have an Operation using that option, it will continue to work.  
Despite the option still being supported, we recommend you migrate to using a FederatedOperation.  

The `gaffer.federatedstore.operation.graphIds` option does not work an OperationChain. Previously, if you wanted to send an entire OperationChain to specific graphs, then you had to use a FederatedOperationChain. This has been replaced by a FederatedOperation with an OperationChain as the payload. For migration, see [below](#breaking-change-removal-of-federatedoperationchain).  

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

## Breaking change: Removal of FederatedOperationChain

The FederatedOperationChain has been removed, and where you would have used it before you should instead use a FederatedOperation with an OperationChain inside.  

This is useful if you have an OperationChain and want to send different parts of the chain to different subgraphs.

#### Individually sending a sequence of Operations to a subgraph
You could send a sequence of operations within one chain to the same subgraph using `graphIds`, however, this is not always efficient:
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
            "graphIds": [ "graphA" ]
        },
        {
            "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
            "operation": {
                "class": "ExampleOperation3"
            },
            "graphIds": [ "graphB" ]
        }
    ]
}
```

#### Removed FederatedOperationChain sending a sequence of operations to a subgraph
It is more efficient to group together sequences of Operations that will go to the same subgraph.
This used to be done with a FederatedOperationChain:
```json
{
    "class": "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations": [
        {
            "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperationChain",
            "operations": {
                [
                    "class": "ExampleOperation1",
                    "class": "ExampleOperation2"
                ]
            },
            "options": {
                "gaffer.federatedstore.operation.graphIds": "graphA"
            }
        },
        {
            "class": "ExampleOperation3",
            "options": {
                "gaffer.federatedstore.operation.graphIds": "graphB"
            }
        }
    ]
}
```


#### New FederatedOperation sending a sequence of operations to a subgraph
Now you should instead wrap an OperationChain inside a FederatedOperation:
```json
{
    "class": "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations": [
        {
            "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
            "operation": {
                "class": "uk.gov.gchq.gaffer.operation.OperationChain",
                "operations": {
                    [
                        "class": "ExampleOperation1",
                        "class": "ExampleOperation2"
                    ]
                }
            },
            "graphIds": [ "graphA" ]
        },
        {
            "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
            "operation": {
                "class": "ExampleOperation3"
            },
            "graphIds": [ "graphB" ]
        }
    ]
}
```

## Default results merging

As described above, FederatedStores now have `storeConfiguredMergeFunctions` that dictate how the FederatedStore will merge results from different subgraphs dependent on the Operation.  

In places, these new defaults do differ from previous behaviour, hence results will too. This can be overriden on a per Operation basis using the `mergeFunction` parameter described above, or a per store basis by overriding `storeConfiguredMergeFunctions`.  
The previous behaviour was that all Operation results were concatenated together, this is now a mergeFunction within Gaffer called `ConcatenateMergeFunction`. Therefore, if you wanted a FederatedOperation to use this old behaviour, you can set the `mergeFunction` to `ConcatenateMergeFunction` (as shown [above](#the-federated-operation)).  

### New Merge function examples

By default, `GetElements` results will be merged with `ApplyViewToElementsFunction`. This uses the View from the operation and applies it to all of the results, meaning the results are now re-aggregated and re-filtered using the Schema, locally in the FederatedStore. This makes the results look like they came from one graph, rather than getting back a list of Elements from different subgraphs.  

By default, `GetTraits` results will be merged with `CollectionIntersect`. This returns the intersection of common store traits from the subgraphs. This behaviour is the same, but now it can be overriden.  

By default, `GetSchema` results will be merged with `MergeSchema`. This returns an aggregated schema from the subgraphs, unless there is a conflict. This behaviour is the same, but now it can be overriden. For example, you may wish to use the `ConcatenateMergeFunction` if there is a schema conflict.  

### Default storeConfiguredMergeFunctions

| Operation         | Merge function              |
|-------------------|-----------------------------|
| GetElements       | ApplyViewToElementsFunction |
| GetAllElements    | ApplyViewToElementsFunction |
| GetSchema         | MergeSchema                 |
| GetTraits         | CollectionIntersect         |
| others            | ConcatenateMergeFunction    |




## Cache Name Suffixes

Gaffer Caches now include suffixes in the names of cache entries. This allows for multiple cache entries for different graphs to co-exist using the same cache implementation without any conflicts.

These suffixes can be customised which allows for graphs to share the same cache entry if desired.
For example, load balancing Federated Store instances sharing same set of sub-graphs, or a shared cache entry for Named Operations allowing multiple graphs to use the same set of these operations.

If you are upgrading from Gaffer 1.x, then you may need to examine how you currently use caches and whether any of your graphs rely on sharing the same cache (e.g. sharing Named Operations between Federated Store sub-graphs).

For details on configuring cache suffixes, see the cache section of the [Store Guide](../../administration-guide/gaffer-stores/store-guide.md#caches).
