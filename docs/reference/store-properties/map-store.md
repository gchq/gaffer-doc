# Map Store Properties

This page details all properties that are specific to [map store types](../../administration-guide/gaffer-stores/map-store.md).
All properties are specified in the `store.properties` file alongside
any [common properties](./common.md).

## General Properties

### `gaffer.store.mapstore.map.factory`

Default: `None`

This allows you to implement your own map factory to use different Map
implementations like Hazelcast and MapDB.

### `gaffer.store.mapstore.map.factory.config`

Default `None`

Provides a configuration file that will be used for the map factory you
have specified.

### `gaffer.store.mapstore.createIndex`

Default: `true`

Controls if an index should be created.

### `gaffer.store.mapstore.static`

Default: `false`

Controls if the Map Store is static (only one instance per JVM).

### `gaffer.store.mapstore.map.ingest.buffer.size`

Default: `0`

Size of the buffer to use when adding objects in batches. By default
this is 0 meaning batches are not used.
