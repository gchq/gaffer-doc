# Bitmap Library

The [bitmap library](https://github.com/gchq/Gaffer/tree/master/library/bitmap-library) module contains various libraries for Bitmaps.

In order to make use of the bitmap libraries you will need to include this library as a dependency:
```
 <dependency>
  <groupId>uk.gov.gchq.gaffer</groupId>
  <artifactId>bitmap-library</artifactId>
  <version>${gaffer.version}</version>
</dependency>
```

## Registering

You can register the BitmapJsonModules using the store or system property: `gaffer.serialiser.json.modules`. This property takes a CSV of classes, so you can use multiple json modules.

```
gaffer.serialiser.json.modules=uk.gov.gchq.gaffer.bitmap.serialisation.json.BitmapJsonModules
```
