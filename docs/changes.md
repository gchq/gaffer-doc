# Changes

Below is a summary of the changes introduced with Gaffer version 2 compared to version 1.

## Removal of Deprecated code
Gaffer 1 deprecated code has been removed. [See this dedicated page for full details](deprecations.md)

## Removal of HBase and Parquet stores
The HBase and Parquet stores have been removed from Gaffer in version 2. [An issue was made](https://github.com/gchq/Gaffer/issues/2367) to understand the levels of usage for the HBase store. It was then decided to remove both stores as this would make introducing various improvements easier in the long term. HBase and Parquet remain available in Gaffer version 1 with the option to add them back into version 2 at a future date.

## Dependency up-versioning
Dependencies have been updated to newer versions (where possible the latest version).

## Java 11 Support
Gaffer now builds and runs on Java 11 instead of Java 8.

## Improved Accumulo Support
Gaffer now supports Accumulo 2, in addition to Accumulo 1.

## Federated Store Improvements
Full details TBD

## Named Operation Improvements
It is now possible for Named Operations to reference each other. This allows one NamedOperation to be called from inside another. Other improvements TBD.
