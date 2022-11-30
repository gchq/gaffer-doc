# Flink Operations

These Operations are part of the Flink library and are only available if they have been specifically enabled.
This is done by registering the Flink operations and their handlers with your store. You need to add the following to your store properties file:
```
gaffer.store.operation.declarations=FlinkOperationDeclarations.json
```

## AddElementsFromFile

Adds elements from a file, requires a filename and an elementGenerator. [Javadoc](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromFile.html)

### Example

=== "Java"

    ``` java
    final AddElementsFromFile op = new AddElementsFromFile.Builder()
            .filename("filename")
            .generator(ElementGenerator.class)
            .parallelism(1)
            .validate(true)
            .skipInvalidElements(false)
            .build();
    ```

=== "JSON"
    
    ``` json
    {
    "class" : "AddElementsFromFile",
    "filename" : "filename",
    "elementGenerator" : "ElementGenerator",
    "parallelism" : 1,
    "skipInvalidElements" : false,
    "validate" : true
    }
    ```

=== "Python"
    
    ``` python
    g.AddElementsFromFile( 
    filename="filename", 
    element_generator="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
    parallelism=1, 
    validate=True, 
    skip_invalid_elements=False 
    )
    ```

## AddElementsFromKafka

Adds elements from a Kafka, requires a topic, groupId, bootstrapServers and an elementGenerator. [Javadoc](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromKafka.html)

### Example

=== "Java"

    ``` java
    final AddElementsFromKafka op = new AddElementsFromKafka.Builder()
            .bootstrapServers("hostname1:8080,hostname2:8080")
            .groupId("groupId1")
            .topic("topic1")
            .generator(ElementGenerator.class)
            .parallelism(1)
            .validate(true)
            .skipInvalidElements(false)
            .build();
    ```

=== "JSON"
    
    ``` json
    {
    "class" : "AddElementsFromKafka",
    "topic" : "topic1",
    "groupId" : "groupId1",
    "bootstrapServers" : [ "hostname1:8080,hostname2:8080" ],
    "elementGenerator" : "ElementGenerator",
    "parallelism" : 1
    }
    ```

=== "Python"
    
    ``` python
    g.AddElementsFromKafka( 
    topic="topic1", 
    group_id="groupId1", 
    bootstrap_servers=[ 
        "hostname1:8080,hostname2:8080" 
    ], 
    element_generator="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
    parallelism=1 
    )
    ```

## AddElementsFromSocket

Adds elements from a Socket, requires a hostname, port and an elementGenerator. [Javadoc](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromSocket.html)

### Example

=== "Java"

    ``` java
    final AddElementsFromSocket op = new AddElementsFromSocket.Builder()
            .hostname("localhost")
            .port(8080)
            .delimiter(",")
            .generator(ElementGenerator.class)
            .parallelism(1)
            .validate(true)
            .skipInvalidElements(false)
            .build();
    ```

=== "JSON"
    
    ``` json
    {
    "class" : "AddElementsFromSocket",
    "hostname" : "localhost",
    "port" : 8080,
    "elementGenerator" : "ElementGenerator",
    "delimiter" : ",",
    "parallelism" : 1,
    "skipInvalidElements" : false,
    "validate" : true
    }
    ```

=== "Python"
    
    ``` python
    g.AddElementsFromSocket( 
    hostname="localhost", 
    port=8080, 
    element_generator="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
    parallelism=1, 
    validate=True, 
    skip_invalid_elements=False, 
    delimiter="," 
    )
    ```