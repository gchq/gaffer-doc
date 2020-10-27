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
import uk.gov.gchq.gaffer.data.element.function.ElementTransformer;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseElementGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.traffic.transform.DescriptionTransform;
import uk.gov.gchq.gaffer.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Transforms extends UserWalkthrough {
    public Transforms() {
        super("Transforms", "RoadAndRoadUse", RoadAndRoadUseElementGenerator.class);
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


        // [add] Add the edges to the graph
        // ---------------------------------------------------------
        final AddElements addElements = new AddElements.Builder()
                .input(elements)
                .build();
        graph.execute(addElements, user);
        // ---------------------------------------------------------
        print("The elements have been added.");


        // [get simple] get all the edges that contain the vertex "1"
        // ---------------------------------------------------------
        final GetElements getEdges = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .build();
        final CloseableIterable<? extends Element> results = graph.execute(getEdges, user);
        // ---------------------------------------------------------
        print("\nAll edges containing the vertex 10. The counts and 'things' have been aggregated\n");
        for (final Element e : results) {
            print("GET_ELEMENTS_RESULT", e.toString());
        }


        // [transform] Create a description transient property using an element transformer
        // ---------------------------------------------------------
        final ElementTransformer descriptionTransformer = new ElementTransformer.Builder()
                .select("SOURCE", "DESTINATION", "count")
                .execute(new DescriptionTransform())
                .project("description")
                .build();
        // ---------------------------------------------------------
        printJson("TRANSFORM", descriptionTransformer);

        // [get] Add the element transformer to the view and run the query
        // ---------------------------------------------------------
        final GetElements getEdgesWithDescription = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(new View.Builder()
                        .edge("RoadUse", new ViewElementDefinition.Builder()
                                .transientProperty("description", String.class)
                                .transformer(descriptionTransformer)
                                .build())
                        .build())
                .build();
        final CloseableIterable<? extends Element> resultsWithDescription = graph.execute(getEdgesWithDescription, user);
        // ---------------------------------------------------------
        printJsonAndPython("GET", getEdgesWithDescription);
        print("\nWe can add a new property to the edges that is calculated from the aggregated values of other properties\n");
        for (final Element e : resultsWithDescription) {
            print("GET_ELEMENTS_WITH_DESCRIPTION_RESULT", e.toString());
        }


        // [get with no count]
        // ---------------------------------------------------------
        final GetElements getEdgesWithDescriptionAndNoCount = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(new View.Builder()
                        .edge("RoadUse", new ViewElementDefinition.Builder()
                                .transientProperty("description", String.class)
                                .transformer(descriptionTransformer)
                                .excludeProperties("count")
                                .build())
                        .build())
                .build();
        final CloseableIterable<? extends Element> resultsWithDescriptionAndNoCount = graph.execute(getEdgesWithDescriptionAndNoCount, user);
        // ---------------------------------------------------------
        printJsonAndPython("GET_WITH_NO_COUNT", getEdgesWithDescriptionAndNoCount);
        print("\nAnd the result without the count property:\n");
        for (final Element e : resultsWithDescriptionAndNoCount) {
            print("GET_ELEMENTS_WITH_DESCRIPTION_AND_NO_COUNT_RESULT", e.toString());
        }

        return resultsWithDescriptionAndNoCount;
    }

    public static void main(final String[] args) throws OperationException, IOException {
        System.out.println(new Transforms().walkthrough());
    }
}
