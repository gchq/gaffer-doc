# Gaffer 2 Changelist  

Below is a summary of intended changes that will be made in Gaffer version 2 compared to version 1.  
**Important:** this represents the current roadmap which is a WIP (also available on [GitHub](https://github.com/gchq/Gaffer/milestones)) but **the order of alphas and features may change**.

## Alpha 1 | [released](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.1)
### Removal of Deprecated code
All of Gaffer 1's deprecated code has been removed. Please see the [deprecations](deprecations.md) page for full details.

### Removal of HBase and Parquet stores
The HBase and Parquet stores have been removed from Gaffer in version 2. We made posts for both the [HBase](https://github.com/gchq/Gaffer/issues/2367) and [Parquet](https://github.com/gchq/Gaffer/discussions/2557) stores to understand the levels of usage. It was then decided to remove both stores as this would make introducing various improvements easier in the long term. HBase and Parquet remain available in Gaffer version 1. In the future, they could be reimplemented for Gaffer 2, though we do not plan to currently.

## Alpha 2 | [in development](https://github.com/gchq/Gaffer/milestone/120)
### Dependency Upgrades
Dependencies will be updated, where possible to the latest version, removing vulnerabilities. Please see the [dependencies](dependencies.md) page for full details.

### New Accumulo 2 Store
There will be a new Accumulo 2 Store added that uses Hadoop 3 and Accumulo 2. This store will be able to federate to Gaffer 2 Accumulo 1 stores.

## Alpha 3 | [in development](https://github.com/gchq/Gaffer/milestone/121)
### Federated Store improvements and fixes
Multiple bug fixes and improvements to the Federated Store. Full details TBD.

## Alpha 4 | [in development](https://github.com/gchq/Gaffer/milestone/122)
### "Maestro" changes to Stores and Operations
A breaking change to the core Gaffer API to make operations more uniform. Full details TBD.

## Alpha 5 | [in development](https://github.com/gchq/Gaffer/milestone/123)
### More Federated Store improvements
More improvements to the Federated Store, including some breaking changes. Full details TBD.

## Alpha 6 | [in development](https://github.com/gchq/Gaffer/milestone/124)
### Named Operation Improvements
It will be possible for Named Operations to reference each other. This would allow one NamedOperation to be called from inside another. Full details TBD.
