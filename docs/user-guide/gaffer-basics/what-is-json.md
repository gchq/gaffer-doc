# What is JSON?

JavaScript Object Notation is a commonly used data format that can be used for storing and
transporting data; notably the industry standard for modern web requests. It's
constructed of a set of key value pairs as exampled:

```json title="JSON Example"
{
    "name": "example name",
    "team": "example team",
    "interests": ["stuff", "more stuff"],
    "pets": 2,
    "location": {
        "country": "GB",
        "region": "somewhere"
    },
    "present": true,
    "appendices": null
}
```

!!! note ""

    For further reading on JSON please see the [official website](https://www.json.org/json-en.html).

## JSON Data Types

JSON objects are defined by the contents of a pair of braces: `{}`, these then
contain the various comma separated key value pairs that make up a block of data
to be stored or transported. JSON has a number of supported data types and some
notable exceptions.

### Supported Data Types

- **Strings** - A simple string of characters e.g. `"A String"`.
- **Numbers** - Number representation e.g. `1`.
- **Objects** - Nested JSON object e.g. the `"location"` object in the example.
- **Arrays** - A list of entries, denoted by `[]` e.g. `"interests"` in the
  example.
- **Booleans** - `true` or `false` values e.g. `"present"` in the example.
- **Null** - Represents no value e.g. `"appendices"` in the example.

!!! note
    The JSON in Gaffer is serialised via the Jackson Databind library so please
    refer to its [documentation for more details](https://github.com/FasterXML/jackson-databind/wiki)
    on supported JSON data types and features.

### Unsupported Data Types

- **Dates** - There is no inbuilt date standard for JSON, most people use a
  `String` to represent a date value.
- **Undefined** - Undefined is a special state where something does not exist,
  this is different to in concept to null.

## How is JSON used in Gaffer?

JSON in Gaffer is largely used as an interchange format for data and can be found
mainly in the API but also in many configuration files. The following sections
give a quick introduction as to where and how JSON is used in Gaffer.

### JSON in the Gaffer API

Gaffer provides a number of communication methods so that you can
programmatically interact with a graph, as of Version 2.0 this is provided in 3
ways: A HTTP REST API, a Python API and a Native Java interface. Of these 3 both the
Python and REST API use JSON as their method of data transportation, although
the Python API obfuscates JSON behind the `gafferpy` client library.

Here is an example of the structure of an operations request using JSON via the
REST API:

```json
{
    "class": "OperationChain",
    "operations": [
        {
            "class": "GetAllElements"
        },
        {
            "class": "Count"
        }
    ]
}
```

### JSON in Gaffer configs

Gaffer also utilises JSON as a method of storing configuration data for the
graph, the most notable area of this being the Gaffer schema. A full break down
of the Gaffer schema can be found in the [graph schema guide](../schema.md).

Other notable configuration files are:

- `graphConfig.json` - The main graph configuration file.
- `operationsDeclarations.json` - Used to activate additional operations for use
  in the graph.
