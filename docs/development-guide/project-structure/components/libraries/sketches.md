# Sketches Library

The [sketches library](https://github.com/gchq/Gaffer/tree/master/library/sketches-library) module contains various libraries for sketches.

In order to make use of the sketches libraries you will need to include this library as a dependency:
```xml
 <dependency>
  <groupId>uk.gov.gchq.gaffer</groupId>
  <artifactId>sketches-library</artifactId>
  <version>${gaffer.version}</version>
</dependency>
```

For information on configuring and using sketches, see the [Cardinality guide](../../../getting-started/guide/cardinality.md#how-to-add-cardinality-to-your-graph) (for configuring `SketchesJsonModules` expand "Additional config").