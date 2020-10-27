/*
 * Copyright 2017-2020 Crown Copyright
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

import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.function.ElementFilter;
import uk.gov.gchq.gaffer.data.elementdefinition.view.GlobalViewElementDefinition;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements;
import uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan;
import uk.gov.gchq.gaffer.user.User;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

import java.io.IOException;

public class Views extends UserWalkthrough {
    public Views() {
        super("Views", "RoadAndRoadUseWithTimesAndCardinalities", RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator.class);
    }

    public CloseableIterable<? extends Element> run() throws OperationException, IOException {
        // [graph] create a graph using our schema and store properties
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


        // [add] Create a data generator and add the edges to the graph using an operation chain consisting of:
        // generateElements - generating edges from the data (note these are directed edges)
        // addElements - add the edges to the graph
        // ---------------------------------------------------------
        final OperationChain<Void> addOpChain = new OperationChain.Builder()
                .first(new GenerateElements.Builder<String>()
                        .generator(new RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator())
                        .input(IOUtils.readLines(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalities/data.txt")))
                        .build())
                .then(new AddElements())
                .build();

        graph.execute(addOpChain, user);
        // ---------------------------------------------------------
        print("The elements have been added.");


        // [view with groups]
        // ---------------------------------------------------------
        final View viewWithGroups = new View.Builder()
                .edge("RoadUse")
                .edge("RoadHasJunction")
                .entity("Cardinality")
                .build();
        // ---------------------------------------------------------
        printJsonAndPythonWithClass("VIEW_WITH_GROUPS", viewWithGroups);

        // [view with filters]
        // ---------------------------------------------------------
        final View viewWithFilters = new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2L))
                                .build())
                        .build())
                .entity("Cardinality", new ViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(5L))
                                .select("hllp")
                                .execute(new HyperLogLogPlusIsLessThan(10L))
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------
        printJsonAndPythonWithClass("VIEW_WITH_FILTERS", viewWithFilters);

        // [view with removed properties]
        // ---------------------------------------------------------
        final View viewWithRemovedProperties = new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .properties("count")
                        .build())
                .entity("Cardinality", new ViewElementDefinition.Builder()
                        .excludeProperties("hllp", "edgeGroups")
                        .build())
                .build();
        // ---------------------------------------------------------
        printJsonAndPythonWithClass("VIEW_WITH_REMOVED_PROPERTIES", viewWithRemovedProperties);

        // [view with global filter] run query with a global filter to return only elements with a count more than 2
        // ---------------------------------------------------------
        final View viewWithGlobalFilter = new View.Builder()
                .globalElements(new GlobalViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2L))
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------
        printJsonAndPythonWithClass("VIEW_WITH_GLOBAL_FILTER", viewWithGlobalFilter);


        // [view with global and specific filters]
        // ---------------------------------------------------------
        final View globalAndSpecificView = new View.Builder()
                .globalElements(new GlobalViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(0L))
                                .build())
                        .build())
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2L))
                                .build())
                        .build())
                .entity("Cardinality")
                .build();
        // ---------------------------------------------------------
        printJsonAndPythonWithClass("VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS", globalAndSpecificView);

        // [view with global and specific filters expanded]
        // ---------------------------------------------------------
        final View globalAndSpecificViewExpanded = new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2L))
                                .select("count")
                                .execute(new IsMoreThan(0L))
                                .build())
                        .build())
                .entity("Cardinality", new ViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(0L))
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------
        printJsonAndPythonWithClass("VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS_EXPANDED", globalAndSpecificViewExpanded);


        // [view with global removed properties]
        // ---------------------------------------------------------
        final View viewWithGlobalRemovedProperties = new View.Builder()
                .globalElements(new GlobalViewElementDefinition.Builder()
                        .properties("count")
                        .build())
                .build();
        // ---------------------------------------------------------
        printJsonAndPythonWithClass("VIEW_WITH_GLOBAL_REMOVED_PROPERTIES", viewWithGlobalRemovedProperties);


        // [view with global and specific removed properties]
        // ---------------------------------------------------------
        final View viewWithGlobalAndSpecificRemovedProperties = new View.Builder()
                .globalElements(new GlobalViewElementDefinition.Builder()
                        .properties("count")
                        .build())
                .edge("RoadUse")
                .entity("Cardinality", new ViewElementDefinition.Builder()
                        .properties("hllp")
                        .build())
                .build();
        // ---------------------------------------------------------
        printJsonAndPythonWithClass("VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES", viewWithGlobalAndSpecificRemovedProperties);

        // [view with global and specific removed properties expanded]
        // ---------------------------------------------------------
        final View viewWithGlobalAndSpecificRemovedPropertiesExpanded = new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .properties("count")
                        .build())
                .entity("Cardinality", new ViewElementDefinition.Builder()
                        .properties("hllp")
                        .build())
                .build();
        // ---------------------------------------------------------
        printJsonAndPythonWithClass("VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES_EXPANDED", viewWithGlobalAndSpecificRemovedPropertiesExpanded);

        return null;
    }

    public static void main(final String[] args) throws OperationException, IOException {
        System.out.println(new Views().walkthrough());
    }
}
