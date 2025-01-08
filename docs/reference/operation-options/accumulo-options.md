# Operation Options for Accumulo

This page details options that can be added to operations that are operating on
an Accumulo backed Gaffer instance.

## General

### `accumulostore.operation.hdfs.skip_import`

Default: `false`

This is only relevant for a [`AddElementsFromHDFS`](../operations-guide/hdfs.md#addelementsfromhdfs)
operation. It will skip the final import of the elements essentially just doing a
dry-run to check everything is valid.

---

## Spark Accumulo

### `Hadoop_Configuration_Key`

Default: `None`

Relevant only to the `ImportKeyValuePairRDDToAccumulo` operation. Allows passing
a serialised HDFS configuration to use for the operation.

### `gaffer.accumulo.spark.directrdd.use_rfile_reader`

Default: `false`

This option, if enabled, allows reading RFiles directly rather than the usual
approach of obtaining them from Accumulo's tablet servers. This requires the
Hadoop user, running the Spark job, to have read access to the RFiles in the
Accumulo tablet.

!!! warning
    Data which has not been minor compacted will not be read if this option is
    used.

### `gaffer.accumulo.spark.rdd.use_batch_scanner`

Default: `false`

Sets if the Accumulo batch scanner should be used when converting to RDD from
Gaffer elements.
