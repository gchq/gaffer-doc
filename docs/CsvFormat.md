# CSV Data Format

To be able to import CSV data into Gaffer, a user must either build an ElementGenerator
that is capable of converting that data into a Gaffer element, alternatively they can simply provide 
one of the supported CsvFormats. The CsvFormat acts as a template for the creation of the respective 
ElementGenerator.The format itself acts as a mapping and allows Gaffer to distinguish between vertices, edges 
and their associated properties. Gaffer currently supports formats used by [Amazon Neptune](./NeptuneFormat.md)
and [Neo4j](./Neo4jFormat.md).
 
