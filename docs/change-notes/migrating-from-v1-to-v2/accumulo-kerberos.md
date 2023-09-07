# Accumulo Kerberos Support

This page contains information on Kerberos Authentication support for Gaffer's Accumulo Store. This functionality was introduced in version `2.0.0-alpha-0.3.1` of Gaffer.

## Using the Accumulo Store with Kerberos

### Prerequisites
To use Gaffer's Accumulo Store with Kerberos authentication:

- The Accumulo cluster to connect with must be correctly configured to use Kerberos.
- A principal for the system/host Gaffer will be running on must be created in the Key Distribution Center (KDC) database.
- The Gaffer principal should use the standard [`primary/instance@realm`](https://web.mit.edu/kerberos/krb5-1.5/krb5-1.5.4/doc/krb5-user/What-is-a-Kerberos-Principal_003f.html) format. Using principals without an _instance_ qualification has not been tested.
- A keytab for the Gaffer principal must be created and transferred to the Gaffer host.
- The Gaffer principal must have been added as an Accumulo user with suitable permissions granted.
- Kerberos client utilities should be installed on the host and `krb5.conf` must be correctly configured.
- An Accumulo client configuration should be available on the host and contain the correct options to enable Kerberos.
- The Gaffer store.properties should state that Kerberos is to be used, specify the principal name and the keytab path.

The sections below cover some of these points in more detail.

### Accumulo user for Gaffer

When Kerberos is used with Accumulo, [any client with a principal can connect without requiring an Accumulo user to have been created previously](https://accumulo.apache.org/docs/2.x/security/kerberos#kerberosauthenticator). This works by creating an Accumulo user automatically when a new client connects. These users are not granted any permissions.

Users can still be created manually via the Accumulo shell, with Gaffer's full principal (with all components) given as the username. Permissions to create and read tables can then be granted to this user. If this isn't done, Accumulo will create the user automatically when Gaffer first connects. In this case Gaffer will fail to start as the required permissions will not have been granted - they can then be granted via the shell and Gaffer restarted.

### Accumulo Client configuration
Depending on the version of Accumulo used, an [`accumulo-client.properties` (2.x)](https://accumulo.apache.org/docs/2.x/security/kerberos#configuration) or [`client.conf` (1.x)](https://accumulo.apache.org/1.10/accumulo_user_manual.html#_configuration_3) must be populated as described in the respective version of the Accumulo documentation. The only value which needs to be altered is the Kerberos server primary. This should reflect the primary part of the principals used by the Accumulo cluster.

The location of this config file can be specified using the `ACCUMULO_CLIENT_CONF_PATH` environment variable. If this is not set, then [default paths will be checked](https://accumulo.apache.org/docs/2.x/apidocs/org/apache/accumulo/core/client/ClientConfiguration.html#loadDefault()).

Other than this file, Accumulo libraries and configuration files do not need to be installed on the Gaffer host. 

### Gaffer `store.properties` configuration
In addition to the usual [Accumulo Store settings](../../administration-guide/gaffer-stores/accumulo-store.md#properties-file), these extra options must be specified for Kerberos:
```
accumulo.kerberos.enable=true
accumulo.kerberos.principal=gaffer/host.domain@REALM.NAME
accumulo.kerberos.keytab=/gaffer/config/gaffer.keytab
```
The `accumulo.username` and `accumulo.password` values do not need to be set and are ignored when `accumulo.kerberos.enable` is true.

The `kinit` Kerberos command does not need to be used, although it might be useful for ensuring the client principal works correctly. All Kerberos ticket management, renewal and re-login is handled automatically.

### Specifying a different `krb5.conf`
If the `krb5.conf` in the default system location is not suitable, or if it's stored in a non-standard location, then 
custom a custom `krb5.conf` location can be specified when starting Gaffer by setting the system property value `java.security.krb5.conf`. The simplest way to do this is by using the option flag `-Djava.security.krb5.conf=/my/path/to/krb5.conf` when launching the Gaffer JAR.

## Federation Considerations
Due to the way Kerberos is implemented in Accumulo, it is not possible for Gaffer to use multiple principals at the same time. For the `FederatedStore`, this prevents adding graphs which are on different Accumulo clusters, if those clusters require different principals. In practice this is unlikely to be a problem, as different Accumulo clusters would only need separate client principals if they were on separate Kerberos Realms or using different KDCs.

This only impacts Accumulo clusters which require Kerberos. It doesn't impact on adding graphs which are stored in clusters using basic authentication and not Kerberos. Nor does it affect adding graphs from a Kerberos cluster and also adding graphs from a non Kerberos cluster in the same `FederatedStore`.

If this limitation is a problem, it can be worked around by running additional Gaffer instances and connecting to them using a `ProxyStore` in the `FederatedStore`, rather than connecting directly using an `AccumuloStore`.

## HDFS Considerations
When using the [`AddElementsFromHdfs`](https://gchq.github.io/gaffer-doc/v1docs/getting-started/operations/addelementsfromhdfs.html) operation Gaffer acts as a HDFS client. When Kerberos is used ([Hadoop Secure Mode](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SecureMode.html)), HDFS clients must have [native libraries](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/NativeLibraries.html) installed and configured correctly; else Hadoop will raise a Runtime Exception stating that "Secure IO is not possible without native code extensions".

The HDFS client also requires the Hadoop configuration files `core-site.xml` and `hdfs-site.xml` to both be present and configured as below. The location of these files can be specified using the `HADOOP_CONF_DIR` environment variable.

```xml
<!--Properties in core-site.xml-->
<property>
	<name>hadoop.security.authentication</name>
	<value>kerberos</value>
</property>
<property>
	<name>hadoop.security.authorization</name>
	<value>true</value>
</property>
```

In particular, `hdfs-site.xml` requires the `yarn.resourcemanager.principal` property to be set to the HDFS client principal - should be the same one as in the Gaffer Store properties. If this is missing Hadoop will fail to connect and raise an IO Exception with "Can't get Master Kerberos principal for use as renewer".

```xml
<!--Properties in hdfs-site.xml-->
<property>
    <name>yarn.resourcemanager.principal</name>
    <value>primary/instance@realm</value>
</property>
```

Note that the `core-site.xml` and `hdfs-site.xml` files are **only** required if `AddElementsFromHdfs` is going to be used. For Accumulo connections the Hadoop properties (from `core-site.xml`) used for enabling Kerberos are set automatically in Gaffer's connection code.

## Spark Accumulo Library
The [Spark Accumulo Library](https://github.com/gchq/Gaffer/tree/master/library/spark) has not yet been updated to support Kerberos. This prevents [Spark Operations](https://gchq.github.io/gaffer-doc/v1docs/getting-started/spark-operations/contents.html) from being used with an `AccumuloStore` which has Kerberos authentication enabled. It is on the backlog for support to be added in future.

## Troubleshooting
Kerberos is not easy to configure and familiarity with Kerberos concepts is recommended. There are [some useful links to introductory information in the Accumulo Kerberos docs](https://accumulo.apache.org/docs/2.x/security/kerberos#overview).

Improperly configured DNS will cause problems with Kerberos. Ensure all hostnames used in Principals resolved correctly, include reverse lookup. Due to how the system's hostname is used by the Hadoop Kerberos libraries, a mismatch between the configured hostname and the hostname resolved by a reverse lookup can prevent authentication from working correctly.

Various environment variables can be set for debugging Kerberos, [see the Hadoop docs for more information](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SecureMode.html#Troubleshooting). These variables are applicable to Accumulo ([see docs](https://accumulo.apache.org/docs/2.x/security/kerberos#debugging)) because its Kerberos implementation uses Hadoop libraries. The Gaffer logging level (set in `log4.xml`) should be increased to at least `INFO` when using these environment variables.
