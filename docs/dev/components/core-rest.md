# Core REST API

The [Core REST API module](https://github.com/gchq/Gaffer/tree/master/rest-api/core-rest) contains a Gaffer REST API.

Gaffer Stores have modules extending the core-rest and adds in the dependency for the Gaffer Store. So if you want to use the Accumulo Store REST API, you can use the `accumulo-rest` `.war`, or `map-rest` for the Map Store.

For an example of using the core REST API please see the [example/road-traffic module](https://github.com/gchq/Gaffer/tree/master/example/road-traffic).

## How to modify the Core REST API for your project

You can easily make changes or additions to the core REST API for your project. You will need to create a new Maven module to build your core REST API. In your POM you should configure the `maven-dependency-plugin` to download the core Gaffer REST API `.war` and extract it. When Maven builds your module it will unpack the core war, add your files and repackage the war. If you wish to override a file in the core war then you can do this by including your own file with exactly the same name and path.

Example `maven-dependency-plugin` configuration:
```xml
<build>
    <sourceDirectory>src/main/java</sourceDirectory>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            <version>3.5</version>
            <dependencies>
                <dependency>
                    <groupId>uk.gov.gchq.gaffer</groupId>
                    <artifactId>core-rest</artifactId> <!-- Or your chosen store, e.g 'accumulo-rest' -->
                    <version>${gaffer.version}</version>
                    <type>war</type>
                </dependency>
            </dependencies>
            <executions>
                <execution>
                    <id>unpack</id>
                    <phase>compile</phase>
                    <goals>
                        <goal>unpack</goal>
                    </goals>
                    <configuration>
                        <artifactItems>
                            <artifactItem>
                                <groupId>uk.gov.gchq.gaffer</groupId>
                                <artifactId>core-rest</artifactId> <!-- Or your chosen store, e.g 'accumulo-rest' -->
                                <version>${gaffer.version}</version>
                                <type>war</type>
                                <overWrite>false</overWrite>
                                <outputDirectory>
                                    ${project.build.directory}/${project.artifactId}-${project.version}
                                </outputDirectory>
                            </artifactItem>
                        </artifactItems>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

So, if you want to change the CSS for the core REST API you can override the custom.css file:
```
<module>/src/main/webapp/css/custom.css
```

There are also various system properties you can use to configure to customise the Swagger UI.
For example:
```
gaffer.properties.app.title=Road Traffic Example
gaffer.properties.app.description=Example using road traffic data
gaffer.properties.app.banner.description=DEMO
gaffer.properties.app.banner.colour=#1b75bb
gaffer.properties.app.logo.link=https://github.com/gchq/Gaffer
gaffer.properties.app.logo.src=images/iconCircle.png
```
