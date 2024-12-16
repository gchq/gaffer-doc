# Common Store Properties

This page details all properties that are common across all stores.
All properties are specified in the `store.properties` file and are
generally optional except from the ones in **bold**.

## General Properties

### Gaffer Store Class

Key: **`gaffer.store.class`**

Default: `None`

Sets the class to use when constructing the Gaffer Store class.
This essentially tells Gaffer what store backend to use by specifying the
Class name of the connector e.g. `uk.gov.gchq.gaffer.accumulostore.AccumuloStore`

### Gaffer Store Properties Class

Key: **`gaffer.store.properties.class`**

Default: `uk.gov.gchq.gaffer.store.StoreProperties`

The class name to use for the Gaffer Store Properties class. Usually this
is set to be inline with the store class you have chosen e.g. for an
Accumulo store you would pick: `uk.gov.gchq.gaffer.accumulostore.AccumuloProperties`

### Gaffer Schema Class

Key: `gaffer.store.schema.class`

Default: `gaffer.store.schema.Schema`

Allows setting a different class to use for serialising Gaffer Schemas.
Generally this will not need to be changed.

### Operation Declarations File

Key: `gaffer.store.operation.declarations`

Default: `None`

Allows specifying a location of a [`operationDeclarations.json`](../../administration-guide/gaffer-config/config.md#operations-declarations-json)
file which can be used to enable additional Gaffer operations for use.
Multiple files can be specified using comma operation if required.

### Operation Declarations JSON

Key: `gaffer.store.operation.declarations.json`

Default: `None`

Set a JSON string of operation declarations. It is recommended to use a
separate file and specify using the [alternative property](#operation-declarations-file)
where possible instead.

### Store Admin Auth

Key: `gaffer.store.admin.auth`

Default: `None`

Allows specifying an Auth String to associate with administrator users.
This Auth will be checked against the user performing an operation to see if
the user object contains it, if so the user is considered an admin.

### Store Reflection Packages

Key: `gaffer.store.reflection.packages`

Default: `None`

Reflection Packages to add to Koryphe [ReflectionUtil](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/util/ReflectionUtil.html).

### Gaffer Debug Error Mode

Key: `gaffer.error-mode.debug`

Default: `false`

Controls technical debugging by methods calling [`DebugUtil`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/commonutil/DebugUtil.html).

---

## Job Tracker Properties

### Job Tracker Enable

Key: `gaffer.store.job.tracker.enabled`

Default: `false`

Boolean option to set if the Job Tracker should be enabled.

### Job Executor Threads

Key: `gaffer.store.job.executor.threads`

Default: `50`

Number of threads to be used by the Job Tracker [ExecutorService](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/commonutil/ExecutorService.html).
This is only relevant if the Job tracker is enabled.

### Reschedule Jobs on Start

Key: `gaffer.store.job.rescheduleOnStart`

Default: `false`

Reschedule any pending Jobs in the cache on start up. This can
have an adverse effect on start up time so is disabled by default
meaning any pending Jobs that did not finish execution are effectively
canceled if the Gaffer is shutdown or restarted.

---

## Named Operation/View Properties

### Named Operation Enable

Key: `gaffer.store.namedoperation.enabled`

Default: `true`

Boolean option to set if Named Operations can be used.

### Nested Named Operations

Key: `gaffer.named.operation.nested`

Default: `false`

Controls if `NamedOperations` are allowed to reference/nest other
`NamedOperations`.

### Named View Enable

Key: `gaffer.store.namedview.enabled`

Default: `true`

Boolean option to set if Named Views can be used.

---

## Cache Properties

### Cache Service Default Class

Key: `gaffer.cache.service.default.class`

Default: `None`

Fully-qualified class name of a Gaffer cache implementation to use as the
default. The default cache is used for a range of things but you may
wish to use different implementations for specific areas, this can be set using
the respective properties. Use cases might include using a larger cache for
things such as the Job Tracker, or using a shared implementation so multiple
Graphs can have access to the same `NamedOperations`.

### Cache Service Job Tracker Class

Key: `gaffer.cache.service.jobtracker.class`

Default: `None`

Used to configure a different Gaffer cache implementation to use for the Job
Tracker.

### Cache Service Named View Class

Key: `gaffer.cache.service.namedview.class`

Default: `None`

Used to configure a different Gaffer cache implementation to use for the
Named Views.

### Cache Service Named Operation Class

Key: `gaffer.cache.service.namedoperation.class`

Default: `None`

Used to configure a different Gaffer cache implementation to use for the Named
Operations.

### Cache Config File

Key: `gaffer.cache.config.file`

Default: `None`

Location to a config file to use with a Gaffer cache implementation.

### Cache Default Suffix

Key: `gaffer.cache.service.default.suffix`

Default: `<graphId>`

String to use as the default cache suffix, by default it will use the Graph ID.

### Cache Named Operation Suffix

Key: `gaffer.cache.service.named.operation.suffix`

Default: `None`

String to override the default suffix used by Named Operation cache.

### Cache Named View Suffix

Key: `gaffer.cache.service.named.view.suffix`

Default: `None`

String to override the default suffix used by Named View cache.

### Cache Job Tracker Suffix

Key: `gaffer.cache.service.job.tracker.suffix`

Default: `None`

String to override the default suffix used by Job Tracker cache.

---

## Serialiser Properties

### JSON Serialiser Class

Key: `gaffer.serialiser.json.class`

Default: `uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser`

Class name String for setting a custom serialiser class. The
class should extend [JSONSerialiser](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiser.html).

### JSON Serialiser Modules

Key `gaffer.serialiser.json.modules`

Default: `None`

Class Name String for registering classes that implement the [JSONSerialiserModules](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiserModules.html) interface. Multiple classes can be specified separating with commas.

### JSON Strict

Key: `gaffer.serialiser.json.strict`

Default: `false`

Controls if unknown fields should be ignored when serialising JSON (sets
[Jackson FAIL_ON_UNKNOWN_PROPERTIES](https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/DeserializationFeature.html#FAIL_ON_UNKNOWN_PROPERTIES)
internally).
