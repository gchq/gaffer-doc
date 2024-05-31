# Managing dependencies in Gaffer

Gaffer is a large project with a lot of dependency and transitive dependency management required. This page covers some ways to get information about and more easily visualise and understand the dependencies for given Maven modules (or even the whole project).

For more information on how Maven handles dependencies, [see this Maven guide](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html).


## Get a simple list of direct dependencies

This outputs the direct (non-transitive) dependencies for all Maven modules in the project. For specific modules append `-pl :module-name` to the command.
```
mvn dependency:list -DexcludeTransitive=true -Dsort=true
```
If `-DexcludeTransitive=true` is not used it will also print transitive dependencies, but without showing where they come from. The tree option (explained below) is better for getting this kind of information.

For more info, [see the Maven dependency plugin documentation for the 'list' goal](https://maven.apache.org/plugins/maven-dependency-plugin/list-mojo.html).

### Output to File

The user property `-DoutputFile=deps.txt` can be used to place output for each module into the root directory **of that module**. It is also possible to aggregate all results into a single file using `mvn dependency:list -B -DappendOutput=true -DoutputFile=$(pwd)/mvn-dep-list.txt`.

However, any files created contain terminal escape characters for adding colour (even when using `-B` - this is likely a bug). Another option is to use `-B` to turn off colour, piping the output through `sort --unique` (which also aggregates the dependencies) and appending that to a file - instead of using the plugin's file output option.


## Get a tree showing Dependencies

This produces a tree showing the **resolved** dependencies for all Maven modules in the project. For specific modules append `-pl :module-name` to the command (as before).
```
mvn dependency:tree
```

To include **all** dependencies (resolved, transitive and duplicate) in the tree and to see information on versions which have been managed (via `dependencyManagement`), append `-Dverbose`.

This verbose flag is very useful for finding out which dependencies are affected by version management and version conflicts.

Here's a simplified tree output with the verbose output explained (expand the :material-plus-circle-outline: symbols):
```c
uk.gov.gchq.gaffer:core:pom:2.0.0
+- org.slf4j:slf4j-api:jar:1.7.36:compile
+- org.slf4j:slf4j-reload4j:jar:1.7.36:compile
|  +- (org.slf4j:slf4j-api:jar:1.7.36:compile - omitted for duplicate) // (1)!
|  \- ch.qos.reload4j:reload4j:jar:1.2.18.3:compile (version managed from 1.2.19) // (2)!
+- org.junit.jupiter:junit-jupiter:jar:5.9.0:test
|  +- org.junit.jupiter:junit-jupiter-api:jar:5.9.0:test
\- org.mockito:mockito-junit-jupiter:jar:4.6.1:test
   +- org.mockito:mockito-core:jar:4.6.1:test
   \- (org.junit.jupiter:junit-jupiter-api:jar:5.8.2:test - omitted for conflict with 5.9.0) // (3)!
```

1. This dependency was omitted because it was already resolved (found) earlier. If a dependency with the same version is seen more than once, subsequent instances are ignored as **duplicates**.
2. This dependency has had its version changed (managed) from the version defined in the parent transitive dependency's POM to the version for this dependency given in the `dependencyManagement` section of our POM. The "version managed from" in the brackets is the version which would have been used if it hadn't been overridden by specifying a managed version.
3. This dependency was omitted because it was already resolved (found) earlier. If a dependency with different versions is seen more than once, subsequent instances are ignored as **conflicts**. In this case, the conflicting version is a transitive dependency and the resolved dependency is a direct dependency (declared in the module POM) which takes priority.

This verbose output is also useful for discovering which dependencies need to have exclusions added when using [Maven dependency exclusions](https://maven.apache.org/guides/introduction/introduction-to-optional-and-excludes-dependencies.html#dependency-exclusions). As an example, replacing Log4j with Reload4j can be done be excluding all Log4j dependencies and adding Reload4j as a project dependency (from a Java perspective this works because both use the same package name). This requires an exclusion section to be added for every dependency which includes Log4j as a transitive dependency.

Without the verbose output, only a single resolved Log4j dependency is shown. In a project with 5 dependencies with Log4j as a sub-dependency, if only the resolved dependency were to be excluded, then another would become the resolved dependency. With this approach, the tree would need to be printed repeatedly until all these dependencies had been seen and excluded. With the verbose output these dependencies are all shown initially.

For more info, [see the Maven dependency plugin documentation for the 'tree' goal](https://maven.apache.org/plugins/maven-dependency-plugin/tree-mojo.html).

### Output to File

Unlike `dependency:list`, the `tree` plugin goal does output to file correctly. This puts all tree output into a single file called `mvn-dep-tree.txt` in the current directory:
```
mvn dependency:tree -DoutputFile=$(pwd)/mvn-dep-tree.txt -DappendOutput=true
```

## Plotting graphs of dependencies

### Using the built-in Maven plugin

In addition to text output, this can also produce `.dot` files which can be used to create dependency graph visualisations (in combination with [Graphviz](https://en.wikipedia.org/wiki/Graphviz)). The resulting files are simple and lack positioning information. They only work well if the number of dependencies is low (e.g. `-Dverbose=true` not used).

This visualisation was produced using the plugin and [Graphviz `sfdp`](https://graphviz.org/docs/layouts/sfdp/).

```bash
mvn dependency:tree -DoutputType=dot -DoutputFile=$(pwd)/core.dot -pl :core # Create the .dot file
sfdp -x -Goverlap=false -Gsplines=true -Tsvg core.dot -o core.svg # Create the SVG image
```

It has too much empty space to be very useful.

![Image title](core.svg)

### Using the Depgraph Maven plugin

A much better tool for creating `.dot` files is the Depgraph Maven plugin. This creates graphs with optimised node positioning, better formatting and more options than the built-in plugin.

The main options available are `showClassifiers`, `showDuplicates`, `showConflicts`, `showVersions`, `showGroupIds` & `showTypes`. All of these options are disabled by default, but have been enabled in the Gaffer POM.

It's important to note that this plugin does not have an equivalent to the verbose mode of `dependency:tree`, and it does not have a way to shown that a dependency's version has been changed using dependency management.

For more info on the plugin, see the [Depgraph GitHub](https://github.com/ferstl/depgraph-maven-plugin) and [plugin docs](https://ferstl.github.io/depgraph-maven-plugin/graph-mojo.html).

An example is shown below. The `dot` tool is also [part of Graphviz](https://graphviz.org/docs/layouts/dot/).

```bash
mvn depgraph:graph -pl :map-store -Dscope=compile -DoutputDirectory=$(pwd)
dot -Tsvg dependency-graph.dot -o filename.svg
```

The black dotted lines show duplicates and the red ones show conflicts which were ignored during dependency resolution.

![Image title](map-store.svg)

It's also possible to create an aggregated graph of all dependencies in a project using `depgraph:aggregate` (which doesn't support showing duplicates or conflicts). For Gaffer this creates [a very large image](gaffer-complete.svg).