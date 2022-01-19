# Changes

Below is a summary of the changes introduced with Gaffer version 2 compared to version 1.

## Removal of Deprecated code
Gaffer 1 deprecated code has been removed. [See this dedicated page for full details](deprecations.md)

## Removal of HBase and Parquet stores
The HBase and Parquet stores have been removed from Gaffer in version 2. [An issue was made](https://github.com/gchq/Gaffer/issues/2367) to understand the levels of usage for the HBase store. It was then decided to remove both stores as this would make introducing various improvements easier in the long term. HBase and Parquet remain available in Gaffer version 1 and may be added back into to version 2 at a future date.

## Dependency up-versioning
Dependencies have been updates to newer versions (where possible the latest version).

## Java 11 upgrade
Gaffer now builds and runs on Java 11 instead of Java 8.

## Other Changes
Other more minor changes will be covered below using level 3 headings.
