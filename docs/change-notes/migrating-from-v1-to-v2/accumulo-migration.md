# Accumulo Migration

This page contains information on changes to the Accumulo/Hadoop versions supported by Gaffer and how to continue using the previously supported versions.

## Accumulo 2 & Hadoop 3 become default versions

From the `2.0.0-alpha-0.3` release of Gaffer, the default version of Accumulo has been upgraded to [Accumulo 2.0.1](https://accumulo.apache.org/release/accumulo-2.0.1/). Hadoop has also been upgraded to the latest version (currently 3.3.3). This is because Hadoop 2.x is not compatible with Accumulo 2.

## Retained support for Accumulo 1 & Hadoop 2

Support for certain versions of Accumulo 1 and Hadoop 2 (specifically 1.9.3 & 2.6.5) has been retained and can be enabled by using a Maven profile when building from source (see below). This facilitates testing with these versions and creates shaded JARs (e.g. spring-rest exec, accumulo-store iterators) with the appropriate versions of supporting libraries. As described in the source docs, other versions of Accumulo 1.x and Hadoop 2.x might also work.

### Availability of Gaffer artifacts supporting Accumulo 1

The shaded JARs differ based on the versions of the bundled libraries and only the default version (Accumulo 2.0.1) is published to the Maven Central repository. The 'legacy' version must be built locally.

### Building Gaffer with the 'legacy' profile

To build Gaffer using Accumulo 1.9.3 and Hadoop 2.6.5, the 'legacy' Maven profile needs to be used. This is enabled by supplying `-Dlegacy=true` as an extra argument at the command line when running Maven. For example, `mvn clean install -Pcoverage -Dlegacy=true` will perform a full build/test of Gaffer with this profile enabled. Java 11 cannot be used with this profile because only Hadoop 3.3.0 and higher support it.

With the 'legacy' Maven profile active, the filenames of all shaded JARs produced are appended with `-legacy`. This is to differentiate them from the default shaded JARs which contain different libraries and different library versions. A default Gaffer Accumulo REST API JAR will not work with an Accumulo 1 cluster, and the 'legacy' version will not work with Accumulo 2 because the bundled libraries are specific to the version of Accumulo.

## Migrating from Accumulo 1 to 2

See [the Accumulo documentation](https://accumulo.apache.org/docs/2.x/administration/upgrading) for guidance on upgrading from Accumulo 1 to 2. Of particular significance is the [deprecation of the dynamic reloading classpath directory functionality](https://accumulo.apache.org/release/accumulo-2.0.0/#removed-default-dynamic-reloading-classpath-directory-libext) in Accumulo 2. This affects where and how the Gaffer iterators JAR can be installed. See the Accumulo store documentation for these installation details.

Otherwise, no Accumulo specific Gaffer configuration needs to be changed and migrating from Accumulo 1 to 2 should be as simple as swapping the Gaffer dependency versions/JARs, although this has not been actively tested.
