# Accumulo Store Properties

This page details all properties that are specific to [accumulo store types](../../administration-guide/gaffer-stores/accumulo-store.md).
All properties are specified in the `store.properties` file alongside
any [common properties](./common.md).

## Required Properties

### **`accumulo.instance`**

Default: `None`

The instance name of your Accumulo cluster.

### **`accumulo.zookeepers`**

Default: `None`

A comma separated list of the Zookeeper servers that your Accumulo cluster is
using. Each server should specify the hostname and port separated by a colon,
i.e. `host:port`.

###  **`accumulo.user`**

Default: `None`

The name of your Accumulo user.

### **`accumulo.password`**

Default: `None`

The password for the Accumulo user you specified using the `accumulo.user` property.

---

## Advanced Properties

This section contains some more advanced properties. Generally sensible defaults
are in place for many of these so only configure them if required.

### `accumulo.namespace`

Default: `""`

The namespace to use for the table in Accumulo. The default is to use the
default Accumulo namespace, which is the empty string.

### `gaffer.store.accumulo.keypackage.class`

Default: `ByteEntityKeyPackage`

The full name of the class to be used as the key-package.

### `accumulo.batchScannerThreads`

Default: `10`

The number of threads to use when `BatchScanner`s are created to query Accumulo.

### `accumulo.entriesForBatchScanner`

Default: `50000`

The maximum number of ranges that should be given to an Accumulo `BatchScanner`
at any one time. This can restrict some results being returned so you may wish
to change if you are intending to start with very wide queries.

### `accumulo.clientSideBloomFilterSize`

Default: `838860800` (100MB)

The size in bits of the Bloom filter used in the client during operations such
as `GetElementsBetweenSets`.

### `accumulo.falsePositiveRate`

Default: `0.0002`

The desired rate of false positives for Bloom filters that are passed to an
iterator in operations such as `GetElementsBetweenSets`.

### `accumulo.maxBloomFilterToPassToAnIterator`

Default: `8388608` (1MB)

The maximum size in bits of Bloom filters that will be created in an iterator on
Accumulo's tablet server during operations such as `GetElementsBetweenSets`.

### `accumulo.maxBufferSizeForBatchWriterInBytes`

Default: `1000000`

The size of the buffer in bytes used in Accumulo `BatchWriter`s when data is
being ingested.

### `accumulo.maxTimeOutForBatchWriterInMilliseconds`

Default: `1000`

The maximum latency used in Accumulo `BatchWriter`s when data is being ingested
in milliseconds.

### `accumulo.numThreadsForBatchWriter`

Default: `10`

The number of threads used in Accumulo `BatchWriter`s when data is being
ingested.

### `accumulo.file.replication`

Default: `None` (uses HDFS default)

The number of replicas of each file in tables created by Gaffer. If this is not
set then your general Accumulo setting will apply, which is normally the same as
the default on your HDFS instance.

### `gaffer.store.accumulo.enable.validator.iterator`

Default: `true`

This specifies whether the validation iterator is applied.
