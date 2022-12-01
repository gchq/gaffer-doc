# Export To Csv

The ToCsv operation allows a user to export data from a running Gaffer instance to an iterable of CSV formatted strings.
This operation can be chained together with ExportToLocalFile, which will take the iterable of strings and write them to a 
local file. The ToCsv operation requires a CsvGenerator, this can be built by the user, alternatively it can be autogenerated by 
supplying the operation with a CsvFormat. CsvFormats that are currently supported include those used by [Neo4j](./Neo4jFormat.md) and AWS's 
[Neptune](./NeptuneFormat.md) graphs, both of which are based on the openCypher.


## Example
The following example shows how to build a chained operation in order to export data from a Gaffer graph and write it to a local file.
In this instance the example will be outputting the elements from Gaffer's Road Traffic Example into an openCypher formatted CSV file
compatible with Neo4j.


```
{
  "class": "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations": [
    {
    "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    {
    "class": "uk.gov.gchq.gaffer.operation.impl.output.ToCsv",
    "csvFormat" : {
      "class": "uk.gov.gchq.gaffer.data.generator.Neo4jFormat"
    },
    "includeHeader": true
    },
    {
    "class": "uk.gov.gchq.gaffer.operation.impl.export.localfile.ExportToLocalFile",
    "filePath": "User/provided/filepath"
    }
    ]
}
```