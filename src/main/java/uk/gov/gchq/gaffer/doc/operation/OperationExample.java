/*
 * Copyright 2016-2020 Crown Copyright
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
import uk.gov.gchq.gaffer.doc.util.DocUtil;
import uk.gov.gchq.gaffer.doc.util.Example;
import uk.gov.gchq.gaffer.doc.walkthrough.WalkthroughStrSubstitutor;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.graph.GraphConfig;
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
import java.util.stream.StreamSupport;

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
        print("### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            print(description + "\n");
        }
        printJava(getJavaSnippet(3));

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
    }

    protected void showExample(final OperationChain operation,
                               final String description) {
        print("### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            print(description + "\n");
        }
        printJavaJsonPython(operation, 3);

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
    }

    protected void showExample(final Operation operation,
                               final String description) {
        print("### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            print(description);
        }
        printJavaJsonPython(operation, 3);

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
    }

    protected void runExampleNoResult(final Operation operation,
                                      final String description) {
        print("### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            print(description + "\n");
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
        print("### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            print(description + "\n");
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

        printResult(results);

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
        return results;
    }

    protected <RESULT_TYPE> RESULT_TYPE runExample(
            final OperationChain<RESULT_TYPE> operationChain,
            final String description) {
        print("### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            print(description);
            print("");
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

        printResult(result);

        if (!skipEndOfMethodBreaks) {
            endOfMethod();
        }
        return result;
    }

    protected void endOfMethod() {
        print(getEndOfMethodString());
    }

    protected String getEndOfMethodString() {
        return METHOD_DIVIDER;
    }

    public <RESULT_TYPE> void printResult(final RESULT_TYPE result) {
        print("Result:");

        print("\n{% codetabs name=\"Java\", type=\"java\" -%}");
        if (result instanceof Iterable) {
            for (final Object item : (Iterable) result) {
                if (item instanceof Walk) {
                    final Walk walk = (Walk) item;
                    print(Walk.class.getName() + walk.getVerticesOrdered()
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(" --> ", "[ ", " ]")));
                } else if (item instanceof Iterable) {
                    print(StreamSupport.stream(((Iterable) item).spliterator(), false)
                            .map(o -> o == null ? "null" : o.toString())
                            .collect(Collectors.joining(" --> ", "[ ", " ]")).toString());
                } else {
                    print(item.toString());
                }
            }
        } else if (result instanceof Map) {
            final Map<?, ?> resultMap = (Map) result;
            for (final Map.Entry<?, ?> entry : resultMap.entrySet()) {
                print(entry.getKey() + ":");
                if (entry.getValue() instanceof Iterable) {
                    for (final Object item : (Iterable) entry.getValue()) {
                        print("    " + item.toString());
                    }
                } else {
                    print("    " + entry.getValue().toString());
                }
            }
        } else if (result instanceof Stream) {
            final Stream stream = (Stream) result;
            stream.forEach(item -> print(item.toString()));
        } else if (result instanceof Object[]) {
            final Object[] array = (Object[]) result;
            for (int i = 0; i < array.length; i++) {
                print(array[i].toString());
            }
        } else if (result instanceof JavaRDD) {
            final List<Element> elements = ((JavaRDD) result).collect();
            for (final Element e : elements) {
                print(e.toString());
            }
        } else if (result instanceof Dataset) {
            final Dataset<Row> dataset = ((Dataset) result);
            final String resultStr = dataset.showString(100, 20, false);
            print(resultStr.substring(0, resultStr.length() - 2));
        } else if (result instanceof Schema) {
            print(DocUtil.getJson(result));
        } else if (null != result) {
            print(result.toString());
        } else {
            throw new RuntimeException("Operation result was null");
        }

        try {
            final String json = DocUtil.getFullJson(result);
            print(WalkthroughStrSubstitutor.JSON_CODE_MARKER);
            print(json);
        } catch (final Exception e) {
            // ignore error - just don't display the json
        }

        print("{%- endcodetabs %}\n");
    }

    protected Graph createSimpleExampleGraph() {
        final Graph graph = new Graph.Builder()
                .config(new GraphConfig.Builder()
                        .json(StreamUtil.graphConfig(getClass()))
                        .graphId(getClass().getSimpleName())
                        .build())
                .addSchemas(StreamUtil.openStreams(getClass(), "operations/schema"))
                .storeProperties(StreamUtil.openStream(getClass(), "singleusemapstore.properties"))
                .build();

        // Create data generator
        final ElementGenerator dataGenerator = new ElementGenerator();

        // Load data into memory
        final List<String> data;
        try {
            data = IOUtils.readLines(StreamUtil.openStream(getClass(), "operations/data.txt"));
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
                .config(new GraphConfig.Builder()
                        .json(StreamUtil.graphConfig(getClass()))
                        .graphId(getClass().getSimpleName())
                        .build())
                .addSchemas(StreamUtil.openStreams(getClass(), "operations/schema"))
                .storeProperties(StreamUtil.openStream(getClass(), "singleusemapstore.properties"))
                .build();

        // Create data generator
        final ElementWithVaryingGroupsGenerator dataGenerator = new ElementWithVaryingGroupsGenerator();

        // Load data into memory
        final List<String> data;
        try {
            data = IOUtils.readLines(StreamUtil.openStream(getClass(), "operations/complexData.txt"));
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
        print("<img src=\"images/complex.png\" width=\"300\">");
    }

    protected void complexGraphAsImage() {
        print("<img src=\"images/complex.png\" width=\"300\">");
    }

    protected void printSimpleGraphAsAscii() {
        print("Using this directed graph:");
        print("\n```");
        print("");
        print("    --> 4 <--");
        print("  /     ^     \\");
        print(" /      |      \\");
        print("1  -->  2  -->  3");
        print("         \\");
        print("           -->  5");
        print("```\n");
    }

    protected void printComplexGraphAsAscii() {
        print("Using this complex directed graph:");
        print("\n```");
        print("");
        print("                 --> 7 <--");
        print("               /           \\");
        print("              /             \\");
        print("             6  -->  3  -->  4");
        print(" ___        ^         \\");
        print("|   |       /          /");
        print(" -> 8 -->  5  <--  2  <");
        print("          ^        ^");
        print("         /        /");
        print("    1 --         /");
        print("     \\          /");
        print("       --------");
        print("```\n");
    }

    protected void printRequiredFields() {
        print("## Required fields");
        boolean hasRequiredFields = false;
        for (final Field field : getClassForExample().getDeclaredFields()) {
            final Required[] annotations = field.getAnnotationsByType(Required.class);
            if (null != annotations && annotations.length > 0) {
                if (!hasRequiredFields) {
                    hasRequiredFields = true;
                    print("The following fields are required: ");
                }
                final String name = field.getName();
                print("- " + name);
            }
        }

        if (!hasRequiredFields) {
            print("No required fields");
        }

        print("\n");
    }

    protected Context createContext() {
        return new Context(new User("user01"));
    }
}
