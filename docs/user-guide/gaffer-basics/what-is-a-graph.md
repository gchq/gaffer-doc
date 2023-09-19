# What is Graph?

## Introduction to Graph Technology 

Graph databases are built to allow you to store and navigate relationships. Their value lies in allowing you to analyse and gain insight into relationships between different data sources to see the bigger picture more easily than through tables or documents. They allow you to store and examine data in a flexible way, similar to how you might sketch ideas on a whiteboard or in a mind map. 

Graph databases use nodes (also known as vertexes) to store data entities, and edges to store the relationships between these entities. An edge will always have a starting node, an end node, a type and a direction. They may describe parent-child relationships, actions, ownership and much more. There is no limit to the number and different types of relationships a node may have.

Below is a simple example with two nodes representing people who have a relationship of 'friends' connecting them:

!!! example "Relationship between Dave and Chris"
    ```mermaid 
    flowchart TD
        subgraph Relationship
            E((Person - Dave))
            -- FRIENDS -->
            F((Person - Chris))  
        end
    ```

### Traversing a graph

Any graph in a graph database can be traversed along specific edges or the entire graph in order to find patterns, paths or communities. The traversing of these relationships is fast as all relationships are stored explicitly. This means queries and algorithms exploring relationships can be run in seconds rather than hours or days as the relationships do not need to be calculated at query time. Therefore, the key benefit of graph databases is their ability to perform efficient and effective analysis of the connections between nodes to uncover patterns or groupings as well as potential connections in our data.

Take the example below which is an extension of the previous graph, as you can see we have added an additional node and relationship into the graph. Suddenly, even with this small example, we can start to draw inferences and analytics about potential connections between nodes. For example, there is a potential connection between nodes 'Dave' and 'Steve' which might mean that if we were a social media company we could prompt "Dave" with a notification to see if they might know "Steve".

!!! example "A potential relationship between Steve and Dave"
    ```mermaid
    flowchart TD
        subgraph Relationship
            E((Person - Dave))
            -- FRIENDS -->
            F((Person - Chris)) 
            -- FRIENDS -->
            B((Person - Steve)) 
            B-. POTENTIAL RELATIONSHIP .->E;
        end
    ```

## Key Terms

| Term        | Description                          |
| :---------- | :----------------------------------- |
| Node        | A node is an entity within a graph  |
| Edge        | An edge is a connection between two nodes. |
| Properties  | A property is a key/value pair that stores data on both edges and entities |
| Vertex      | Vertex is another term for Node |
