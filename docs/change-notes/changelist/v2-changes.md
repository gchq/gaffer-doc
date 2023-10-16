# Gaffer 2 Changelist

Below is a summary of changes that have been made in Gaffer version 2.

Each breaking change has been put into one of 3 tiers, where they are also relevant to the tiers below:

- **user**: end users might have to change their Gaffer queries
- **admin**: Gaffer admins that manage graphs might have to change their config or deployment environment
- **developer**: developers of Gaffer or Gaffer extensions might need to make code changes

### Accumulo 2 Support
The Accumulo store now supports Accumulo 2 and Hadoop 3 by default, with support for Accumulo 1 and Hadoop 2 retained. See the [Accumulo Migration page](../migrating-from-v1-to-v2/accumulo-migration.md) for more information about this change.

### Federated Store Improvements
The Federated Operation was added to greatly improve flexibility of using a Federated Store.
!!! danger "Breaking change | user | [alpha-0.4](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.4)"
    To migrate, please see the [Federated Store Changes page](../migrating-from-v1-to-v2/federation-changes.md).

### Cache Improvements and fixes
All "caches" within Gaffer received a lot of bug fixes which should make them significantly more stable and consistent over time. This should improve usability of FederatedStores, NamedOperations and NamedViews.
!!! danger "Breaking change | admin | [alpha-0.6](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.6)"
    The cache will need to be reloaded, as the new internal cache interface has changed.  To do this, export all of the contents of your cache, upgrade, then re-add everything manually.

### Removal of Deprecated code
All of Gaffer 1's deprecated code has been removed.
!!! danger "Breaking change | user | [alpha-0.1](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.1)"
    To migrate, please see the [deprecations](../migrating-from-v1-to-v2/deprecations.md) page.

### Dependency Upgrades
Dependencies have been updated, where possible to the latest version, removing vulnerabilities.
!!! danger "Breaking change | developer | [alpha-0.2](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.2)"
    You will need to migrate your dependencies to be compatible with Gaffer 2's new dependency versions. Please see the [dependencies](../migrating-from-v1-to-v2/dependencies.md) page for full details.

### Federated and Proxy store fixes
A lot of bugs have been fixed that should facilitate FederatedStores with ProxyStores in them.
!!! danger "Breaking change | developer | [alpha-0.5](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.5)"
    The unique store trait `DYNAMIC_SCHEMA` has been removed from Gaffer. Simply removing it from custom FederatedStore implementations should be an adequate fix.

### Removal of CloseableIterable
The `CloseableIterable` class has been removed so Operations like `GetAllElements` now return an `Iterable` instead, but the result still implements `Closeable`.
!!! danger "Breaking change | developer | [alpha-0.2](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.2)"
    Everywhere `CloseableIterable` was used in client code should be replaced with an `Iterable`:
    ```java
    final CloseableIterable<? extends Element> results = graph.execute(new GetAllElements(), USER);
    ```
    ```java
    final Iterable<? extends Element> results = graph.execute(new GetAllElements(), USER);
    ```

### Removal of HBase and Parquet stores
The HBase and Parquet stores have been removed from Gaffer in version 2. We made posts for both the [HBase](https://github.com/gchq/Gaffer/issues/2367) and [Parquet](https://github.com/gchq/Gaffer/discussions/2557) stores to understand the levels of usage. It was then decided to remove both stores as this would make introducing various improvements easier in the long term. HBase and Parquet remain available in Gaffer version 1. In the future, they could be reimplemented for Gaffer 2, though we do not plan to currently.
!!! danger "Breaking change | admin | [alpha-0.1](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.1)"
    We would recommend instead using an Accumulo Store. If you would like these store implementations in Gaffer 2, or any other potential store for that matter, please [make an issue](https://github.com/gchq/Gaffer/issues/new?assignees=&labels=enhancement&projects=&template=feature_request.md&title=) on GitHub.

### Gaffer now builds with Java 8 and Java 11
There is now a maven profile that will swap dependency versions so you can build Gaffer with Java 11. The code has also been updated to build with both Java versions.

### Accumulo Kerberos Authentication Support
The Accumulo store now supports authenticating to Accumulo and HDFS using Kerberos, in addition to username/password. For more information, see the [Kerberos support page](../migrating-from-v1-to-v2/accumulo-kerberos.md).

### CSV Import and Export
Basic support for importing and exporting [CSVs](../../user-guide/query/gaffer-syntax/import-export/csv.md) has been added.

### All operations can now be used within NamedOperations
Previously, `GetElementsBetweenSets` could not be used within a NamedOperation as it used `inputB`. `GetElementsBetweenSets` and `inputB` have both been deprecated and instead you should use `GetElementsBetweenSetsPairs`.
??? example
    Old operation now deprecated:
    ```json
    {
        "class": "GetElementsBetweenSets",
        "input": [
            {
                "class": "EntitySeed",
                "vertex": "firstInput"
            }
        ],
        "inputB": [
            {
                "class": "EntitySeed",
                "vertex": "secondInput"
            }
        ]
    }
    ```
    New operation that will work within NamedOperations:
    ```json
    {
        "class": "GetElementsBetweenSetsPairs",
        "input": {
            "class": "Pair",
            "first": {
                "ArrayList" : [
                    {
                        "class": "EntitySeed",
                        "vertex": "firstInput"
                    }
                ]
            },
            "second": {
                "ArrayList" : [
                    {
                        "class": "EntitySeed",
                        "vertex": "secondInput"
                    }
                ]
            }
        }
    }
    ```

### Ability to set OperationDeclarations during AddGraph
This will mean subgraphs added to FederatedStores can have additional operation handlers set when they are added. You can directly provide the OperationsDeclarations json to the store properties with `gaffer.store.operation.declarations.json`.
??? example
    ``` json
    {
        "class": "AddGraph",
        "graphId": "myGraph",
        "schema": {}, // (1)!
        "storeProperties": {
            "gaffer.store.class": "MapStore",
            "gaffer.store.operation.declarations.json": {
                "operations": [
                    {
                        "operation": "ImportFromLocalFile", // (2)!
                        "handler": {
                            "class": "ImportFromLocalFileHandler"
                        }
                    }
                ]
            }
        }
    }
    ```

    1. Schema left empty for brevity
    2. This example operation enables file import. Read more in the [CSV](../../user-guide/query/gaffer-syntax/import-export/csv.md) docs.
