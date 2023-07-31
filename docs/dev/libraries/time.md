# Time Library

This library contains classes that represent concepts relating to time.

For example, there is a class (`RBMBackedTimestampSet`) that can be used to represent a set of timestamps. Internally this stores its state in a Roaring Bitmap for efficiency reasons.
There is also a class that stores up to a maximum number N of timestamps. If more than N timestamps are added then a uniform random sample of the timestamps, of size at most N, is stored. Serialisers and aggregators for the above classes are provided.

To use this library, you will need to include the following dependency:

```
 <dependency>
  <groupId>uk.gov.gchq.gaffer</groupId>
  <artifactId>time-library</artifactId>
  <version>${gaffer.version}</version>
</dependency>
```
