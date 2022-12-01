# ImportFromCsv

The following operations; ImportFromLocalFile, CsvToElements and AddElements when chained together allow a user 
to import data into a running Gaffer instance by firstly providing the path to a CSV file and secondly selecting 
one of the CVS formats current supported by Gaffer ie [Neo4jFormat](./Neo4jFormat.md) or [NeptuneFormat](./NeptuneFormat.md). 
The data within the CSV must adhere to the openCypher [format](./CsvFormat). 


## Example
The following example shows how to build a chained operation in order to import data from a local file into a running Gaffer instance.
The data to be imported adheres to Amazon's Neptune Format.

 ```
{
  "class": "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations": [
    {
    "class": "uk.gov.gchq.gaffer.operation.impl.export.localfile.ImportFromLocalFile",
    "filePath": "User/provided/filepath"
    },
    {
    "class": "uk.gov.gchq.gaffer.operation.impl.add.CsvToElements",
    "csvFormat" : {
      "class": "uk.gov.gchq.gaffer.data.generator.NeptuneFormat"
    }
    },
    {
    "class": "uk.gov.gchq.gaffer.operation.impl.add.AddElements",
    }
    ]
}
 ```
