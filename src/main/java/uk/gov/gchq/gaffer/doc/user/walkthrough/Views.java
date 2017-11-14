/*
 * Copyright 2017 Crown Copyright
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
import uk.gov.gchq.gaffer.data.elementdefinition.view.GlobalViewElementDefinition;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseElementGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.user.User;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Views extends UserWalkthrough {
    public Views() {
        super("View", "RoadAndRoadUse", RoadAndRoadUseElementGenerator.class);
    }

    @Override
    public CloseableIterable<? extends Element> run() throws OperationException, IOException {
        // [generate] Create some edges from the simple data file using our Road Use generator class
        // ---------------------------------------------------------
        final List<Element> elements = new ArrayList<>();
        final RoadAndRoadUseElementGenerator dataGenerator = new RoadAndRoadUseElementGenerator();
        for (final String line : IOUtils.readLines(StreamUtil.openStream(getClass(), "RoadAndRoadUse/data.txt"))) {
            Iterables.addAll(elements, dataGenerator._apply(line));
        }
        // ---------------------------------------------------------
        log("Elements generated from the data file.");
        for (final Element element : elements) {
            log("GENERATED_EDGES", element.toString());
        }
        log("");


        // [graph] Create a graph using our schema and store properties
        // ---------------------------------------------------------
        final Graph graph = new Graph.Builder()
                .config(StreamUtil.graphConfig(getClass()))
                .addSchemas(StreamUtil.openStreams(getClass(), "RoadAndRoadUse/schema"))
                .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
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
        log("The elements have been added.");


        // [get group specific filter] run query with a group specific filter
        // ---------------------------------------------------------
        final View viewWithGroupSpecificFilter = new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2L))
                                .build())
                        .build())
                .build();
        final GetElements getEdgesWithGroupSpecificFilter = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(viewWithGroupSpecificFilter)
                .build();
        final CloseableIterable<? extends Element> groupSpecificFilteredResults = graph.execute(getEdgesWithGroupSpecificFilter, user);
        // ---------------------------------------------------------
        log("\nAll edges containing the vertex 10 with an aggregated `count` more than 2, using a group specific filter\n");
        for (final Element e : groupSpecificFilteredResults) {
            log("GET_ELEMENTS_WITH_GROUP_SPECIFIC_FILTER", e.toString());
        }


        // [get global filter] run query with a global filter to return only edges with a count more than 2
        // ---------------------------------------------------------
        final View viewWithGlobalFilter = new View.Builder()
                .globalEdges(new GlobalViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2L))
                                .build())
                        .build())
                .build();

        viewWithGlobalFilter.expandGlobalDefinitions();

        final GetElements getEdgesWithGlobalFilter = new GetElements.Builder()
                .input(new EntitySeed("10"), new EntitySeed("11"))
                .view(viewWithGlobalFilter)
                .build();
        final CloseableIterable<? extends Element> globallyFilteredResults = graph.execute(getEdgesWithGlobalFilter, user);
        // ---------------------------------------------------------
        log("\nAll edges containing the vertex 10 with an aggregated `count` more than 2, using a global filter\n");
        for (final Element e : globallyFilteredResults) {
            log("GET_ELEMENTS_WITH_GLOBAL_FILTER", e.toString());
        }


        // [get global and group specific filter] run query with a global filter to return only edges with a count more than 0
        // and then an edge specific filter to return only edges with a count more than 2
        // ---------------------------------------------------------
        final View viewWithGlobalAndGroupSpecificFilter = new View.Builder()
                .globalEdges(new GlobalViewElementDefinition.Builder()
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
                .build();

        viewWithGlobalAndGroupSpecificFilter.expandGlobalDefinitions();

        final GetElements getEdgesWithGlobalAndGroupSpecificFilter = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(viewWithGlobalAndGroupSpecificFilter)
                .build();
        final CloseableIterable<? extends Element> globallyAndGroupSpecificFilteredResults = graph.execute(getEdgesWithGlobalAndGroupSpecificFilter, user);
        // ---------------------------------------------------------
        log("\nAll edges containing the vertex 10 with an aggregated `count` more than 2, using a global filter concatenated with a group specific filter\n");
        for (final Element e : globallyAndGroupSpecificFilteredResults) {
            log("GET_ELEMENTS_WITH_GLOBAL_AND_GROUP_SPECIFIC_FILTER", e.toString());
        }

        // [get group specific property] run query with a group specific property to return the count property
        // ---------------------------------------------------------
        final View viewWithGroupSpecificProperty = new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .properties("count")
                        .build())
                .build();
        final GetElements getEdgesWithGroupSpecificProperty = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(viewWithGroupSpecificProperty)
                .build();
        final CloseableIterable<? extends Element> groupSpecificPropertyResults = graph.execute(getEdgesWithGroupSpecificProperty, user);
        // ---------------------------------------------------------
        log("\nAll edges containing the vertex 10 and the property `count`, using group specific properties\n");
        for (final Element e : groupSpecificPropertyResults) {
            log("GET_ELEMENTS_WITH_GROUP_SPECIFIC_PROPERTY", e.toString());
        }

        // [get global property] run query with a global property to return the count property
        // ---------------------------------------------------------
        final View viewWithGlobalProperty = new View.Builder()
                .globalEdges(new GlobalViewElementDefinition.Builder()
                        .properties("count")
                        .build())
                .build();
        final GetElements getEdgesWithGlobalProperty = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(viewWithGlobalProperty)
                .build();
        final CloseableIterable<? extends Element> globalPropertyResults = graph.execute(getEdgesWithGlobalProperty, user);
        // ---------------------------------------------------------
        log("\nAll edges containing the vertex 10 with the property `count`, using global properties\n");
        for (final Element e : globalPropertyResults) {
            log("GET_ELEMENTS_WITH_GLOBAL_PROPERTY", e.toString());
        }

        // [get global and group specific property] run query with a global property, overridden by group specific property to return the doesNotExist property
        // ---------------------------------------------------------
        final View viewWithGlobalAndGroupSpecificProperty = new View.Builder()
                .globalEdges(new GlobalViewElementDefinition.Builder()
                        .properties("count")
                        .build())
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .properties("doesNotExist")
                        .build())
                .build();
        final GetElements getEdgesWithGlobalAndGroupSpecificProperty = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(viewWithGlobalAndGroupSpecificProperty)
                .build();
        final CloseableIterable<? extends Element> globalAndGroupSpecificPropertyResults = graph.execute(getEdgesWithGlobalAndGroupSpecificProperty, user);
        // ---------------------------------------------------------
        log("\nAll edges containing the vertex 10 with the property `doesNotExist`, using global property `count` overridden by group specific property `doesNotExist`\n");
        for (final Element e : globalAndGroupSpecificPropertyResults) {
            log("GET_ELEMENTS_WITH_GLOBAL_AND_GROUP_SPECIFIC_PROPERTY", e.toString());
        }

        // [get group specific exclude property] run query with a group specific exclude property to not return the count property
        // ---------------------------------------------------------
        final View viewWithGroupSpecificExcludeProperty = new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .excludeProperties("count")
                        .build())
                .build();
        final GetElements getEdgesWithGroupSpecificExcludeProperty = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(viewWithGroupSpecificExcludeProperty)
                .build();
        final CloseableIterable<? extends Element> groupSpecificExcludePropertyResults = graph.execute(getEdgesWithGroupSpecificExcludeProperty, user);
        // ---------------------------------------------------------
        log("\nAll edges containing the vertex 10, using group specific exclude property to exclude the property `count`\n");
        for (final Element e : groupSpecificExcludePropertyResults) {
            log("GET_ELEMENTS_WITH_GROUP_SPECIFIC_EXCLUDE_PROPERTY", e.toString());
        }

        // [get global exclude property] run query with a global exclude property to not return the count property
        // ---------------------------------------------------------
        final View viewWithGlobalExcludeProperty = new View.Builder()
                .globalEdges(new GlobalViewElementDefinition.Builder()
                        .excludeProperties("count")
                        .build())
                .build();
        final GetElements getEdgesWithGlobalExcludeProperty = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(viewWithGlobalExcludeProperty)
                .build();
        final CloseableIterable<? extends Element> globalExcludePropertyResults = graph.execute(getEdgesWithGlobalExcludeProperty, user);
        // ---------------------------------------------------------
        log("\nAll edges containing the vertex 10, using global exclude property to exclude the property `count`\n");
        for (final Element e : globalExcludePropertyResults) {
            log("GET_ELEMENTS_WITH_GLOBAL_EXCLUDE_PROPERTY", e.toString());
        }

        // [get global and group specific exclude property] run query with a global exclude property to not return the count property, overridden by the group specific filter to not return the doesNotExist property
        // ---------------------------------------------------------
        final View viewWithGlobalAndGroupSpecificExcludeProperty = new View.Builder()
                .globalEdges(new GlobalViewElementDefinition.Builder()
                        .excludeProperties("count")
                        .build())
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .excludeProperties("doesNotExist")
                        .build())
                .build();
        final GetElements getEdgesWithGlobalAndGroupSpecificExcludeProperty = new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(viewWithGlobalAndGroupSpecificExcludeProperty)
                .build();
        final CloseableIterable<? extends Element> globalAndGroupSpecificExcludePropertyResults = graph.execute(getEdgesWithGlobalAndGroupSpecificExcludeProperty, user);
        // ---------------------------------------------------------
        log("\nAll edges containing the vertex 10, using global exclude property to exclude the property `count`, overridden by the group specific exclude property `doesNotExist`\n");
        for (final Element e : globalAndGroupSpecificExcludePropertyResults) {
            log("GET_ELEMENTS_WITH_GLOBAL_AND_GROUP_SPECIFIC_EXCLUDE_PROPERTY", e.toString());
        }

        return globallyFilteredResults;
    }

    public static void main(final String[] args) throws OperationException, IOException {
        final Views walkthrough = new Views();
        walkthrough.run();
    }
}
