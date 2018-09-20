/*
 * Copyright 2018 Crown Copyright
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

package uk.gov.gchq.gaffer.doc.dev.walkthrough;

import com.google.common.collect.Iterables;
import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseElementGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.graph.GraphConfig;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.user.User;

import java.util.ArrayList;
import java.util.List;

public class SchemaMigrations extends DevWalkthrough {
    public SchemaMigrations() {
        super("Schema Migration", "RoadAndRoadUse", RoadAndRoadUseElementGenerator.class);
    }

    @Override
    public Iterable<? extends Element> run() throws Exception {
        // [generate] Create some edges from the simple data file using our Road Use generator class
        // ---------------------------------------------------------
        final List<Element> elements = new ArrayList<>();
        final RoadAndRoadUseElementGenerator dataGenerator = new RoadAndRoadUseElementGenerator();
        for (final String line : IOUtils.readLines(StreamUtil.openStream(getClass(), dataPath))) {
            Iterables.addAll(elements, dataGenerator._apply(line));
        }
        // ---------------------------------------------------------

        // [graph no migration] Create a graph using our schema and store properties with no migration
        // ---------------------------------------------------------
        final Graph graphNoMigration = new Graph.Builder()
                .config(getDefaultGraphConfig())
                .addSchemas(StreamUtil.openStreams(getClass(), schemaPath))
                .storeProperties(getDefaultStoreProperties())
                .build();
        // ---------------------------------------------------------

        // [user] Create a user
        // ---------------------------------------------------------
        final User user = new User("user01");
        // ---------------------------------------------------------


        // [add no migration] Add the edges to the graph with no migration
        // ---------------------------------------------------------
        final AddElements addElements = new AddElements.Builder()
                .input(elements)
                .build();
        graphNoMigration.execute(addElements, user);
        // ---------------------------------------------------------
        print("The elements have been added.");

        // [get no migration] Get all the edges related to vertex 10 without and SchemaMigration
        // ---------------------------------------------------------
        final GetElements getElements = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .build();
        final CloseableIterable<? extends Element> results = graphNoMigration.execute(getElements, user);
        // ---------------------------------------------------------
        print("\nNotice that the edges have a property of class Long");
        for (final Element e : results) {
            print("GET_ELEMENTS_NO_MIGRATION_RESULT", e.toString());
        }

        // [graph migration] Create a graph using our schema and store properties with no migration
        // ---------------------------------------------------------
        final GraphConfig graphConfig = new GraphConfig.Builder().json(StreamUtil.openStream(getClass(), "graphConfigWithSchemaMigration.json")).build();

        final Graph graphMigration = new Graph.Builder()
                .config(graphConfig)
                .addSchemas(StreamUtil.openStreams(getClass(), "RoadAndRoadUse/schemaWithSchemaMigration"))
                .storeProperties(getDefaultStoreProperties())
                .build();
        // ---------------------------------------------------------

        printJson("MIGRATED_SCHEMA", graphMigration.getSchema());
        printJson("MIGRATION_CONFIG", graphConfig.getHooks().get(0));

        // [add migration] Add the edges to the graph
        // ---------------------------------------------------------
        graphMigration.execute(addElements, user);
        // ---------------------------------------------------------
        print("The elements have been added.");

        // [get migration] Get all the edges related to vertex 10
        // ---------------------------------------------------------
        final GetElements getElementsWithMigration = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .build();
        final CloseableIterable<? extends Element> resultsWithMigration = graphMigration.execute(getElementsWithMigration, user);
        // ---------------------------------------------------------
        print("\nNotice that the edges now have a property of class Integer not Long");
        for (final Element e : resultsWithMigration) {
            print("GET_ELEMENTS_MIGRATION_RESULT", e.toString());
        }
        return resultsWithMigration;
    }

    public static void main(final String[] args) throws Exception {
        final SchemaMigrations walkthrough = new SchemaMigrations();
        System.out.println(walkthrough.walkthrough());
    }
}
