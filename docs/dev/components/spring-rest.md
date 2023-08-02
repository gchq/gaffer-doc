# Spring REST API

The [Spring REST API module](https://github.com/gchq/Gaffer/tree/master/rest-api/spring-rest) is an implementation of the Gaffer REST API deployed in a Spring Boot container.
It is relatively new compared to the [core REST](./core-rest.md) war which uses Jersey/JAX-RS.

The Spring REST should provide the following benefits:

* Easier to extend through Spring Boot plugins
* Easier to add dependencies at deployment time (no need to re-build a `.war` file)
* Easier to deploy (you only need Java)

However, going forward into Gaffer v2.0 we hope this to become the standard for how we build and deploy REST APIs.

### Implemented Features
* Operations endpoint
* Graph configuration endpoint
* Properties endpoint
* Status endpoint

### Features we're yet to implement
* Chunked endpoint

### Features we don't plan to implement
* Custom Swagger UI with operation chain builder
* Supporting older versions of the API


### How to run
With Maven from the root of the project:
```bash
mvn spring-boot:run -pl :spring-rest -Pdemo
```

With Java using the 'exec' `.jar` directly:
```
java \
-Dgaffer.schemas=/path/to/schemas \
-Dgaffer.storeProperties=/path/to/store.properties \
-Dgaffer.graph.config=/path/to/graphConfig.json \
-jar spring-rest-2.0.0-exec.jar
```

You can alternatively add the Gaffer system properties to your `application.properties` file.

Once running, open the browser to `http://localhost:8080/rest`.

You can change the context root by changing the `server.servlet.context-path` value in `application.properties`.