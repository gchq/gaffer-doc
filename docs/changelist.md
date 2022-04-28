# Gaffer 2 Changelist  

Below is a summary of intended changes that will be made in Gaffer version 2.   
**Note:** this represents the current roadmap which is not final (also available on [GitHub](https://github.com/gchq/Gaffer/milestones)) but **the features may change**.

## Alpha 1 | [released](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.1)
### Removal of Deprecated code
All of Gaffer 1's deprecated code has been removed. Please see the [deprecations](deprecations.md) page for full details.

### Removal of HBase and Parquet stores
The HBase and Parquet stores have been removed from Gaffer in version 2. We made posts for both the [HBase](https://github.com/gchq/Gaffer/issues/2367) and [Parquet](https://github.com/gchq/Gaffer/discussions/2557) stores to understand the levels of usage. It was then decided to remove both stores as this would make introducing various improvements easier in the long term. HBase and Parquet remain available in Gaffer version 1. In the future, they could be reimplemented for Gaffer 2, though we do not plan to currently.

## Alpha 2 | [released](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.1)
### Dependency Upgrades
Dependencies have been updated, where possible to the latest version, removing vulnerabilities. Please see the [dependencies](dependencies.md) page for full details.

### Gaffer now builds with Java 8 and Java 11
There is now a maven profile that will swap dependency versions so you can build Gaffer with Java 11. The code has also been updated to build with both Java versions.

### Removal of CloseableIterable
The CloseableIterable class has been removed so Operations like GetAllElements now return an Iterable instead, but the result still implements Closeable.

### Known issues
The road traffic example rest has a bug in this release that means the example data isn't loaded in. This means that the gafferpy tests will fail as there is no data, but it still works.

## Future alphas
### New Accumulo 2 Store
There will be a new Accumulo 2 Store added that uses Hadoop 3 and Accumulo 2. This store will be able to federate to Gaffer 2 Accumulo 1 stores.

### Federated Store improvements and fixes
Multiple bug fixes and improvements to the Federated Store. Full details TBD.

### Named Operation Improvements
Some changes and improvements to Named Operations are planned. Full details TBD.
