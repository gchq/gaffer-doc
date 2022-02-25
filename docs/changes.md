# Changes

Below is a summary of intended changes that will be made in Gaffer version 2 compared to version 1.
Important to note is that this represents the current roadmap, also available on [GitHub](https://github.com/gchq/Gaffer/milestones), but the order of alphas and features may change.

## Alpha 1 - [released](https://github.com/gchq/Gaffer/releases/tag/gaffer2-2.0.0-alpha-0.1)
### Removal of Deprecated code
All of Gaffer 1's deprecated code has been removed. [See this dedicated page for full details](deprecations.md)

### Removal of HBase and Parquet stores
The HBase and Parquet stores have been removed from Gaffer in version 2. [An issue was made](https://github.com/gchq/Gaffer/issues/2367) to understand the levels of usage for the HBase store. It was then decided to remove both stores as this would make introducing various improvements easier in the long term. HBase and Parquet remain available in Gaffer version 1. At some point in Gaffer 2's future, they may be readded.

## Alpha 2
### Dependency up-versioning
Dependencies will be updated, where possible to the latest version, removing vulnerabilities.

### Java 11 Support
Gaffer will build and run on Java 11 instead of Java 8.

## Alpha 3
### Federated Store Improvements
Full details TBD.

## Alpha 4
### "Maestro" changes to Stores and Operations
Full details TBD.

## Alpha 5
### More Federated Store fixes
Full details TBD.

## Alpha 6
### Accumulo 2 Support
Gaffer 2 will support an Accumulo 2 store, in addition to the Accumulo 1 store.
## Alpha 7
### Named Operation Improvements
It will be possible for Named Operations to reference each other. This would allow one NamedOperation to be called from inside another. Other improvements TBD.
