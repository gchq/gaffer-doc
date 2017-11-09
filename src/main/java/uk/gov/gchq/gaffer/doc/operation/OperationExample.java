/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.gchq.gaffer.doc.operation;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import uk.gov.gchq.gaffer.commonutil.Required;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.graph.Walk;
import uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator;
import uk.gov.gchq.gaffer.doc.operation.generator.ElementWithVaryingGroupsGenerator;
import uk.gov.gchq.gaffer.doc.util.Example;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements;
import uk.gov.gchq.gaffer.operation.io.Output;
import uk.gov.gchq.gaffer.store.Context;
import uk.gov.gchq.gaffer.store.schema.Schema;
import uk.gov.gchq.gaffer.user.User;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class OperationExample extends Example {
    private static final Logger ROOT_LOGGER = Logger.getRootLogger();
    protected boolean skipEndOfMethodBreaks = false;
    private final Graph graph;
    private boolean complex;

    public OperationExample(final Class<? extends Operation> classForExample) {
        super(classForExample);
        ROOT_LOGGER.setLevel(Level.OFF);

        this.graph = createSimpleExampleGraph();
    }

    public OperationExample(final Class<? extends Operation> classForExample, final String description) {
        super(classForExample, description);
        ROOT_LOGGER.setLevel(Level.OFF);

        this.graph = createSimpleExampleGraph();
    }

    public OperationExample(final Class<? extends Operation> classForExample, final String description, final boolean complex) {
        super(classForExample, description);
        ROOT_LOGGER.setLevel(Level.OFF);

        this.graph = (complex) ? createComplexExampleGraph() : createSimpleExampleGraph();
        this.complex = complex;
    }

    @Override
    protected void printDescription() {
        super.printDescription();
        printRequiredFields();
    }

    protected Graph getGraph() {
        return graph;
    }

    protected void showJavaExample(final String description) {
        log("#### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            log(description + "\n");
        }
        printJava(getJavaSnippet(3));

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
    }

    protected void showExample(final OperationChain operation,
                               final String description) {
        log("#### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            log(description + "\n");
        }
        printJavaJsonPython(operation, 3);

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
    }

    protected void showExample(final Operation operation,
                               final String description) {
        log("#### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            log(description);
        }
        printJavaJsonPython(operation, 3);

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
    }

    protected void runExampleNoResult(final Operation operation,
                                      final String description) {
        log("#### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            log(description + "\n");
        }
        printJavaJsonPython(operation, 3);

        try {
            getGraph().execute(operation, createContext());
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
    }

    protected <RESULT_TYPE> RESULT_TYPE runExample(
            final Output<RESULT_TYPE> operation, final String description) {
        log("#### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            log(description + "\n");
        }

        if (complex) {
            printComplexGraphAsAscii();
        } else {
            printSimpleGraphAsAscii();
        }

        printJavaJsonPython(operation, 3);

        final RESULT_TYPE results;
        try {
            results = getGraph().execute(operation, createContext());
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }

        logResult(results);

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
        return results;
    }

    protected <RESULT_TYPE> RESULT_TYPE runExample(
            final OperationChain<RESULT_TYPE> operationChain,
            final String description) {
        log("#### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            log(description);
            log("");
        }

        if (complex) {
            printComplexGraphAsAscii();
        } else {
            printSimpleGraphAsAscii();
        }

        printJavaJsonPython(operationChain, 3);

        final RESULT_TYPE result;
        try {
            result = getGraph().execute(operationChain, createContext());
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }

        logResult(result);

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
        return result;
    }

    protected void endOfMethod() {
        log(getEndOfMethodString());
    }

    protected String getEndOfMethodString() {
        return METHOD_DIVIDER;
    }

    public <RESULT_TYPE> void logResult(final RESULT_TYPE result) {
        log("Result:\n");

        log("\n{% codetabs name=\"Java\", type=\"java\" -%}");

        if (result instanceof Iterable) {
            for (final Object item : (Iterable) result) {
                if (item instanceof Walk) {
                    final Walk walk = (Walk) item;
                    log(Walk.class.getName() + walk.getVerticesOrdered()
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(" --> ", "[ ", " ]")));
                } else {
                    log(item.toString());
                }
            }
        } else if (result instanceof Map) {
            final Map<?, ?> resultMap = (Map) result;
            for (final Map.Entry<?, ?> entry : resultMap.entrySet()) {
                log(entry.getKey() + ":");
                if (entry.getValue() instanceof Iterable) {
                    for (final Object item : (Iterable) entry.getValue()) {
                        log("    " + item.toString());
                    }
                } else {
                    log("    " + entry.getValue().toString());
                }
            }
        } else if (result instanceof Stream) {
            final Stream stream = (Stream) result;
            stream.forEach(item -> log(item.toString()));
        } else if (result instanceof Object[]) {
            final Object[] array = (Object[]) result;
            for (int i = 0; i < array.length; i++) {
                log(array[i].toString());
            }
        } else if (result instanceof JavaRDD) {
            final List<Element> elements = ((JavaRDD) result).collect();
            for (final Element e : elements) {
                log(e.toString());
            }
        } else if (result instanceof Dataset) {
            final Dataset<Row> dataset = ((Dataset) result);
            final String resultStr = dataset.showString(100, 20);
            log(resultStr.substring(0, resultStr.length() - 2));
        } else if (result instanceof Schema) {
            log(getJson(result));
        } else {
            log(result.toString());
        }

        try {
            final String json = getJson(result);
            log("\n{%- language name=\"JSON\", type=\"json\" -%}");
            log(json);
        } catch (final Exception e) {
            // ignore error - just don't display the json
        }

        log("{%- endcodetabs %}\n");
    }

    protected Graph createSimpleExampleGraph() {
        final Graph graph = new Graph.Builder()
                .config(StreamUtil.graphConfig(getClass()))
                .addSchemas(StreamUtil.openStreams(getClass(), "operation/schema"))
                .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
                .build();

        // Create data generator
        final ElementGenerator dataGenerator = new ElementGenerator();

        // Load data into memory
        final List<String> data;
        try {
            data = IOUtils.readLines(StreamUtil.openStream(getClass(), "operation/data.txt"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        //add the edges to the graph using an operation chain consisting of:
        //generateElements - generating edges from the data (note these are directed edges)
        //addElements - add the edges to the graph
        final OperationChain addOpChain = new OperationChain.Builder()
                .first(new GenerateElements.Builder<String>()
                        .generator(dataGenerator)
                        .input(data)
                        .build())
                .then(new AddElements())
                .build();

        try {
            graph.execute(addOpChain, new User());
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }

        return graph;
    }

    protected Graph createComplexExampleGraph() {
        final Graph graph = new Graph.Builder()
                .config(StreamUtil.graphConfig(getClass()))
                .addSchemas(StreamUtil.openStreams(getClass(), "operation/schema"))
                .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
                .build();

        // Create data generator
        final ElementWithVaryingGroupsGenerator dataGenerator = new ElementWithVaryingGroupsGenerator();

        // Load data into memory
        final List<String> data;
        try {
            data = IOUtils.readLines(StreamUtil.openStream(getClass(), "operation/complexData.txt"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        //add the edges to the graph using an operation chain consisting of:
        //generateElements - generating edges from the data (note these are directed edges)
        //addElements - add the edges to the graph
        final OperationChain addOpChain = new OperationChain.Builder()
                .first(new GenerateElements.Builder<String>()
                        .generator(dataGenerator)
                        .input(data)
                        .build())
                .then(new AddElements())
                .build();

        try {
            graph.execute(addOpChain, new User());
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }

        return graph;
    }

    protected void simpleGraphAsImage() {
        log("<img src=\"images/complex.png\" width=\"300\">");
    }

    protected void complexGraphAsImage() {
        log("<img src=\"images/complex.png\" width=\"300\">");
    }

    protected void printSimpleGraphAsAscii() {
        log("Using this complex directed graph:");
        log("\n```");
        log("");
        log("    --> 4 <--");
        log("  /     ^     \\");
        log(" /      |      \\");
        log("1  -->  2  -->  3");
        log("         \\");
        log("           -->  5");
        log("```\n");
    }

    protected void printComplexGraphAsAscii() {
        log("Using this directed graph:");
        log("\n```");
        log("");
        log("             --> 7 <--");
        log("           /           \\");
        log("          /             \\");
        log("         6  -->  3  -->  4");
        log("         ^         \\");
        log("        /          /");
        log("8 -->  5  <--  2  <");
        log("      ^        ^");
        log("     /        /");
        log("1 --         /");
        log(" \\          /");
        log("   --------");
        log("```\n");
    }

    protected void printRequiredFields() {
        log("### Required fields");
        boolean hasRequiredFields = false;
        for (final Field field : getClassForExample().getDeclaredFields()) {
            final Required[] annotations = field.getAnnotationsByType(Required.class);
            if (null != annotations && annotations.length > 0) {
                if (!hasRequiredFields) {
                    hasRequiredFields = true;
                    log("The following fields are required: ");
                }
                final String name = field.getName();
                log("- " + name);
            }
        }

        if (!hasRequiredFields) {
            log("No required fields");
        }

        log("\n");
    }

    protected Context createContext() {
        return new Context(new User("user01"));
    }
}
