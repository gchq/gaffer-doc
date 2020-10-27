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

import com.google.common.collect.Iterables;
import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.function.ElementFilter;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseElementGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.SeedMatching;
import uk.gov.gchq.gaffer.operation.data.EdgeSeed;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.user.User;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Filtering extends UserWalkthrough {
    public Filtering() {
        super("Filtering", "RoadAndRoadUse", RoadAndRoadUseElementGenerator.class);
    }

    @Override
    public CloseableIterable<? extends Element> run() throws OperationException, IOException {
        // [generate] Create some edges from the simple data file using our Road Use generator class
        // ---------------------------------------------------------
        final List<Element> elements = new ArrayList<>();
        final RoadAndRoadUseElementGenerator dataGenerator = new RoadAndRoadUseElementGenerator();
        for (final String line : IOUtils.readLines(StreamUtil.openStream(getClass(), dataPath))) {
            Iterables.addAll(elements, dataGenerator._apply(line));
        }
        // ---------------------------------------------------------
        print("Elements generated from the data file.");
        for (final Element element : elements) {
            print("GENERATED_EDGES", element.toString());
        }
        print("");


        // [graph] Create a graph using our schema and store properties
        // ---------------------------------------------------------
        final Graph graph = new Graph.Builder()
                .config(getDefaultGraphConfig())
                .addSchemas(StreamUtil.openStreams(getClass(), schemaPath))
                .storeProperties(getDefaultStoreProperties())
                .build();
        // ---------------------------------------------------------


        // [user] Create a user
        // ---------------------------------------------------------
        final User user = new User("user01");
        // ---------------------------------------------------------


        // [add] add the edges to the graph
        // ---------------------------------------------------------
        final AddElements addElements = new AddElements.Builder()
                .input(elements)
                .build();
        graph.execute(addElements, user);
        // ---------------------------------------------------------
        print("The elements have been added.");


        print("\nRoadUse edges related to vertex 10. The counts have been aggregated\n");
        // [get simple] get all the edges that contain the vertex "10"
        // ---------------------------------------------------------
        final GetElements getElements = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(new View.Builder()
                        .edge("RoadUse")
                        .build())
                .build();
        final CloseableIterable<? extends Element> results = graph.execute(getElements, user);
        // ---------------------------------------------------------
        printJsonAndPython("GET_SIMPLE", getElements);
        for (final Element e : results) {
            print("GET_ELEMENTS_RESULT", e.toString());
        }


        // [get] rerun previous query with a filter to return only edges with a count more than 2
        // ---------------------------------------------------------
        final GetElements getEdgesWithCountMoreThan2 = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(new View.Builder()
                        .edge("RoadUse", new ViewElementDefinition.Builder()
                                .postAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(2L))
                                        .build())
                                .build())
                        .build())
                .build();
        final CloseableIterable<? extends Element> filteredResults = graph.execute(getEdgesWithCountMoreThan2, user);
        // ---------------------------------------------------------
        printJsonAndPython("GET", getEdgesWithCountMoreThan2);
        print("\nAll edges containing the vertex 10 with an aggregated count more than than 2\n");
        for (final Element e : filteredResults) {
            print("GET_ELEMENTS_WITH_COUNT_MORE_THAN_2_RESULT", e.toString());
        }

        // [get edges with seedmatching]
        // ---------------------------------------------------------
        final GetElements getEdgesWithSeedMatching = new GetElements.Builder()
                .input(new EdgeSeed("source", "dest", true))
                .seedMatching(SeedMatching.SeedMatchingType.EQUAL)
                .build();
        // ---------------------------------------------------------
        printJsonAndPython("GET_EDGES_WITH_SEEDMATCHING", getEdgesWithSeedMatching);

        // [get edges without seedmatching]
        // ---------------------------------------------------------
        final GetElements getEdgesWithoutSeedMatching = new GetElements.Builder()
                .input(new EdgeSeed("source", "dest", true))
                .view(new View.Builder()
                        .edge("group1")
                        .build())
                .build();
        // ---------------------------------------------------------
        printJsonAndPython("GET_EDGES_WITHOUT_SEEDMATCHING", getEdgesWithoutSeedMatching);

        // [get entities with seedmatching]
        // ---------------------------------------------------------
        final GetElements getEntitiesWithSeedMatching = new GetElements.Builder()
                .input(new EntitySeed("vertex"))
                .seedMatching(SeedMatching.SeedMatchingType.EQUAL)
                .build();
        // ---------------------------------------------------------
        printJsonAndPython("GET_ENTITIES_WITH_SEEDMATCHING", getEntitiesWithSeedMatching);

        // [get entities without seedmatching]
        // ---------------------------------------------------------
        final GetElements getEntitiesWithoutSeedMatching = new GetElements.Builder()
                .input(new EntitySeed("vertex"))
                .view(new View.Builder()
                        .entity("group1")
                        .build())
                .build();
        // ---------------------------------------------------------
        printJsonAndPython("GET_ENTITIES_WITHOUT_SEEDMATCHING", getEntitiesWithoutSeedMatching);

        return filteredResults;
    }

    public static void main(final String[] args) throws OperationException, IOException {
        System.out.println(new Filtering().walkthrough());
    }
}
