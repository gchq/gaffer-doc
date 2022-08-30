# ImportCsv

The ImportCsv operation allows a user to import data into a running Gaffer instance by simply
providing the path to a CSV file. The data within the CSV must adhere to the openCypher [format](./openCypher). 
As described in the previous link there exists small differences in the headers used between Neptune and neo4j, 
the ImportCsv operation handles both, it also handles both entities and edges specified within the same file.

## Example
The following example shows how to load a CSV using the openCypher CSV format into a Gaffer graph.
This step requires a running Gaffer instance.
#### openCypher load format
 ```
 :ID,  name:String,  age:Int,  lang:String,  :LABEL,  :START_ID,  :END_ID,   :TYPE,  weight:Double
  v1,      "marko",       29,             ,  person,           ,         ,        ,
  v2,        "lop",         ,       "java",software,           ,         ,        ,
  e1,             ,         ,             ,        ,         v1,       v2, created,            0.4
 ```



From the rest-api execute the following operation from POST /graph/operation/execute. As the user you must provide the
file path to the CSV file containing your data.
#### Operation to Execute
 ```
 {
     "class": "uk.gov.gchq.gaffer.operation.impl.add.ImportCsv",
     "filename": "path/to/data.csv"
 }
 ```