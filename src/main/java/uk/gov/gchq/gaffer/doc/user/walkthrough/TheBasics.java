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
package uk.gov.gchq.gaffer.doc.user.walkthrough;

import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.accumulostore.AccumuloProperties;
import uk.gov.gchq.gaffer.accumulostore.SingleUseMockAccumuloStore;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.doc.user.generator.RoadUseElementGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.graph.GraphConfig;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.store.schema.Schema;
import uk.gov.gchq.gaffer.store.schema.SchemaEdgeDefinition;
import uk.gov.gchq.gaffer.store.schema.TypeDefinition;
import uk.gov.gchq.gaffer.user.User;
import uk.gov.gchq.koryphe.impl.binaryoperator.Sum;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;
import uk.gov.gchq.koryphe.impl.predicate.IsTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TheBasics extends UserWalkthrough {
    public TheBasics() {
        super("The Basics", "RoadUse", RoadUseElementGenerator.class);
    }

    @Override
    public CloseableIterable<? extends Element> run() throws OperationException, IOException {
        // [generate] Create some edges from the simple data file using our Road Use generator class
        // ---------------------------------------------------------
        final List<Element> elements = new ArrayList<>();
        final RoadUseElementGenerator dataGenerator = new RoadUseElementGenerator();
        for (final String line : IOUtils.readLines(StreamUtil.openStream(getClass(), dataPath))) {
            elements.add(dataGenerator._apply(line));
        }
        // ---------------------------------------------------------
        print("Elements generated from the data file.");
        for (final Element element : elements) {
            print("GENERATED_EDGES", element.toString());
        }
        print("");


        Graph graph;
        // [graph from files] Create a graph using config, schema and store properties files
        // ---------------------------------------------------------
        graph = new Graph.Builder()
                .config(StreamUtil.openStream(getClass(), graphConfigPath))
                .addSchemas(StreamUtil.openStreams(getClass(), schemaPath))
                .storeProperties(StreamUtil.openStream(getClass(), storePropertiesPath))
                .build();
        // ---------------------------------------------------------


        // [graph] Create a graph using config, schema and store properties from java
        // ---------------------------------------------------------
        final GraphConfig config = new GraphConfig.Builder()
                .graphId(getClass().getSimpleName())
                .build();

        final Schema schema = new Schema.Builder()
                .edge("RoadUse", new SchemaEdgeDefinition.Builder()
                        .description("A directed edge representing vehicles moving from junction A to junction B.")
                        .source("junction")
                        .destination("junction")
                        .directed("true")
                        .property("count", "count.long")
                        .build())
                .type("junction", new TypeDefinition.Builder()
                        .description("A road junction represented by a String.")
                        .clazz(String.class)
                        .build())
                .type("count.long", new TypeDefinition.Builder()
                        .description("A long count that must be greater than or equal to 0.")
                        .clazz(Long.class)
                        .validateFunctions(new IsMoreThan(0L, true))
                        .aggregateFunction(new Sum())
                        .build())
                .type("true", new TypeDefinition.Builder()
                        .description("A simple boolean that must always be true.")
                        .clazz(Boolean.class)
                        .validateFunctions(new IsTrue())
                        .build())
                .build();

        final AccumuloProperties properties = new AccumuloProperties();
        properties.setStoreClass(SingleUseMockAccumuloStore.class);
        properties.setInstance("instance1");
        properties.setZookeepers("zookeeper1");
        properties.setUser("user01");
        properties.setPassword("password");

        graph = new Graph.Builder()
                .config(config)
                .addSchema(schema)
                .storeProperties(properties)
                .build();
        // ---------------------------------------------------------


        // [user] Create a user
        // ---------------------------------------------------------
        final User user = new User("user01");
        // ---------------------------------------------------------


        // [add] Add the edges to the graph
        // ---------------------------------------------------------
        final AddElements addElements = new AddElements.Builder()
                .input(elements)
                .build();
        graph.execute(addElements, user);
        // ---------------------------------------------------------
        print("The elements have been added.");


        // [get] Get all the edges that contain the vertex "10"
        // ---------------------------------------------------------
        final GetElements query = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(new View.Builder()
                        .edge("RoadUse")
                        .build())
                .build();
        final CloseableIterable<? extends Element> results = graph.execute(query, user);
        // ---------------------------------------------------------
        print("\nAll edges containing the vertex 10. The counts have been aggregated.");
        for (final Element e : results) {
            print("GET_ELEMENTS_RESULT", e.toString());
        }

        return results;
    }

    public static void main(final String[] args) throws OperationException, IOException {
        final TheBasics walkthrough = new TheBasics();
        walkthrough.run();
    }
}
