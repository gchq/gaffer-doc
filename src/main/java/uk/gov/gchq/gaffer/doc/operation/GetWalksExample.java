/*
 * Copyright 2016-2018 Crown Copyright
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

import com.google.common.collect.Lists;

import uk.gov.gchq.gaffer.commonutil.CollectionUtil;
import uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator;
import uk.gov.gchq.gaffer.data.element.function.ElementFilter;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.data.graph.Walk;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.graph.SeededGraphFilters;
import uk.gov.gchq.gaffer.operation.impl.GetWalks;
import uk.gov.gchq.gaffer.operation.impl.compare.Sort;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan;
import uk.gov.gchq.koryphe.impl.predicate.IsEqual;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

public class GetWalksExample extends OperationExample {
    public GetWalksExample() {
        super(GetWalks.class, "Examples for the GetWalks operation. This example " +
                "uses a modified graph. This graph contains two different edge groups, " +
                "each with an modified count property. The count is set to the sum of the source " +
                "and destination vertices. Additionally the edge group is determined by " +
                "whether this count property is even (group edge) or odd (group edge1).", true);
    }

    public static void main(final String[] args) throws OperationException {
        new GetWalksExample().runAndPrint();
    }

    @Override
    public void runExamples() {
        getWalks();
        getWalksWithIncomingOutgoingFlags();
        getWalksWithFiltering();
        getWalksWithEntities();
        getWalksWithFilteringOnCardinalityEntities();
        getWalksWithMultipleGroups();
        getWalksWithLoops();
        getWalksWithSelfLoops();
        getWalksWithAdditionalOperations();
    }

    public Iterable<Walk> getWalks() {
        // ---------------------------------------------------------
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                .input(new EntitySeed(1))
                .build();
        // ---------------------------------------------------------

        return runExample(getWalks, "Gets all of the Walks of length 2 " +
                "which start from vertex 1, with the added restriction that all " +
                "edges must be traversed using the source as the matched vertex.");
    }

    public Iterable<Walk> getWalksWithFiltering() {
        // ---------------------------------------------------------
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new GetElements.Builder()
                                .view(new View.Builder().edge("edge", new ViewElementDefinition.Builder()
                                        .preAggregationFilter(new ElementFilter.Builder()
                                                .select("count")
                                                .execute(new IsMoreThan(3))
                                                .build())
                                        .build())
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .view(new View.Builder().edge("edge1", new ViewElementDefinition.Builder()
                                        .preAggregationFilter(new ElementFilter.Builder()
                                                .select("count")
                                                .execute(new IsMoreThan(8))
                                                .build())
                                        .build())
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build())
                .input(new EntitySeed(1))
                .build();
        // ---------------------------------------------------------

        return runExample(getWalks, "Gets all of the Walks of length 2 " +
                "which start from vertex 1. This example demonstrates the use of " +
                "pre-aggregation filters to select which edges to traverse based " +
                "on a property on the edge.");
    }

    public Iterable<Walk> getWalksWithIncomingOutgoingFlags() {
        // ---------------------------------------------------------
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build())
                .input(new EntitySeed(1))
                .build();
        // ---------------------------------------------------------

        return runExample(getWalks, "Gets all of the Walks of length 2 " +
                "which start from vertex 1. The IncludeIncomingOutgoingType flag " +
                "can be used to determine which edge direction the Walk follows " +
                "for each hop.");
    }

    public Iterable<Walk> getWalksWithMultipleGroups() {
        // ---------------------------------------------------------
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new GetElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge1")
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build())
                .input(new EntitySeed(1))
                .build();
        // ---------------------------------------------------------

        return runExample(getWalks, "Gets all of the Walks of length 2 " +
                "which start from vertex 1. The IncludeIncomingOutgoingType flag " +
                "can be used to determine which edge direction the Walk follows " +
                "for each hop. Additionally, the group set in the view is used to " +
                "only travel down certain edges in each hop.");
    }

    public Iterable<Walk> getWalksWithLoops() {
        // ---------------------------------------------------------
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                .input(new EntitySeed(1))
                .build();
        // ---------------------------------------------------------

        return runExample(getWalks, "Gets all of the Walks of length 6 " +
                "which start from vertex 1, with the added restriction that all " +
                "edges must be traversed using the source as the matched vertex. " +
                "This demonstrates the behaviour when previously traversed edges " +
                "are encountered again.");
    }

    public Iterable<Walk> getWalksWithSelfLoops() {
        // ---------------------------------------------------------
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                .input(new EntitySeed(8))
                .build();
        // ---------------------------------------------------------

        return runExample(getWalks, "Gets all of the Walks of length 3 " +
                "which start from vertex 8, with the added restriction that all " +
                "edges must be traversed using the source as the matched vertex. " +
                "This demonstrates the behaviour when self loops exist in the graph.");
    }

    public Iterable<Walk> getWalksWithEntities() {
        // ---------------------------------------------------------
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new GetElements.Builder()
                                .view(new View.Builder().edge("edge")
                                        .entities(Lists.newArrayList("entity", "entity1"))
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .view(new View.Builder().edge("edge1")
                                        .entity("entity1")
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build(),
                        new GetElements.Builder()
                                .view(new View.Builder()
                                        .entities(Lists.newArrayList("entity", "entity1"))
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build())
                .input(new EntitySeed(1))
                .build();
        // ---------------------------------------------------------

        return runExample(getWalks, "Gets all of the Walks of length 2 " +
                "which start from vertex 1, with all of the entities which are attached " +
                "to the vertices found along the way.");
    }

    public Iterable<Walk> getWalksWithAdditionalOperations() {
        // ---------------------------------------------------------
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new OperationChain(new GetElements.Builder()
                                .view(new View.Builder()
                                        .edges(Lists.newArrayList("edge", "edge1"))
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build(),
                                new Sort.Builder()
                                        .comparators(new ElementPropertyComparator.Builder()
                                                .property("count")
                                                .build())
                                        .build()),
                        new GetElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge1")
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build())
                .input(new EntitySeed(5))
                .build();
        // ---------------------------------------------------------

        return runExample(getWalks, "Gets all of the Walks of length 2 " +
                "which start from vertex 5, where an additional operation is inserted " +
                "between the GetElements operations used to retrieve elements.");
    }

    public Iterable<Walk> getWalksWithFilteringOnCardinalityEntities() {
        // ---------------------------------------------------------
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new OperationChain.Builder()
                                .first(new GetElements.Builder()
                                        .view(new View.Builder()
                                                .entity("cardinality", new ViewElementDefinition.Builder()
                                                        .preAggregationFilter(new ElementFilter.Builder()
                                                                .select("edgeGroup")
                                                                .execute(new IsEqual(CollectionUtil.treeSet("edge")))
                                                                .build())
                                                        .groupBy()
                                                        .postAggregationFilter(new ElementFilter.Builder()
                                                                .select("hllp")
                                                                .execute(new HyperLogLogPlusIsLessThan(2))
                                                                .build())
                                                        .build())
                                                .build())
                                        .build())
                                .then(new GetElements.Builder()
                                        .view(new View.Builder()
                                                .edges(Lists.newArrayList("edge", "edge1"))
                                                .entities(Lists.newArrayList("entity", "entity1"))
                                                .build())
                                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                        .build())
                                .build(),
                        new GetElements.Builder()
                                .view(new View.Builder()
                                        .edges(Lists.newArrayList("edge", "edge1"))
                                        .entities(Lists.newArrayList("entity", "entity1"))
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build(),
                        new GetElements.Builder()
                                .view(new View.Builder()
                                        .entities(Lists.newArrayList("entity", "entity1"))
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build())
                .input(new EntitySeed(5))
                .build();
        // ---------------------------------------------------------

        return runExample(getWalks, "Gets all of the Walks of length 2 " +
                "which start from vertex 5, where the results of the first hop are " +
                "filtered based on the cardinality entities in the graph. IMPORTANT NOTE - you cannot filter walks based on entity filters at the end of a walk.");
    }
}
