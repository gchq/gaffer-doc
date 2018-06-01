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

package uk.gov.gchq.gaffer.doc.user.walkthrough;

import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.id.DirectedType;
import uk.gov.gchq.gaffer.data.graph.Walk;
import uk.gov.gchq.gaffer.doc.operation.generator.ElementWithVaryingGroupsGenerator;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseElementGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.graph.GraphConfig;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EdgeSeed;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.graph.SeededGraphFilters;
import uk.gov.gchq.gaffer.operation.impl.GetWalks;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.operation.impl.output.ToEntitySeeds;
import uk.gov.gchq.gaffer.operation.impl.output.ToVertices;
import uk.gov.gchq.gaffer.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Traversing extends UserWalkthrough {

    public Traversing() {
        super("Traversing", "ComplexExample", RoadAndRoadUseElementGenerator.class);
    }

    @Override
    public CloseableIterable<? extends Element> run() throws OperationException {
        // [generate] Create some edges from the complex data file
        // ---------------------------------------------------------
        final ElementWithVaryingGroupsGenerator dataGenerator = new ElementWithVaryingGroupsGenerator();

        // Load data into memory
        final List<String> data;
        try {
            data = IOUtils.readLines(StreamUtil.openStream(getClass(), "operations/complexData.txt"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        // ---------------------------------------------------------

        // [user] Create a user
        // ---------------------------------------------------------
        final User user = new User("user01");
        // ---------------------------------------------------------


        // [graph] Create a graph using schema and store properties
        // ---------------------------------------------------------
        final Graph graph = new Graph.Builder()
                .config(new GraphConfig.Builder()
                        .json(StreamUtil.graphConfig(getClass()))
                        .graphId(getClass().getSimpleName())
                        .build())
                .addSchemas(StreamUtil.openStreams(getClass(), "operations/schema"))
                .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
                .build();
        // ---------------------------------------------------------

        //[add] the elements to the graph using an operation chain consisting of:
        //generateElements - generating edges from the data (note these are directed edges)
        //addElements - add the edges to the graph
        // ---------------------------------------------------------
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
        // ---------------------------------------------------------

        // [get walks simple] simple getWalks operation example
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

        final Iterable<Walk> getWalksResults = graph.execute(getWalks, user);

        printJsonAndPython("GET_WALKS_SIMPLE", getWalks);

        for (final String walk : makeWalksPrintable(getWalksResults)) {
            print("GET_WALKS_SIMPLE_RESULT", walk);
        }
        // ---------------------------------------------------------

        // [get adjacent ids simple] simple getAdjacentIds operation example
        // ---------------------------------------------------------
        final OperationChain<CloseableIterable<? extends Element>> getAdjacentIdsOpChain = new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed(2))
                        .build())
                .then(new GetElements())
                .build();

        final CloseableIterable<? extends Element> getAdjacentIdsResults = graph.execute(getAdjacentIdsOpChain, user);

        printJsonAndPython("GET_ADJACENT_IDS_SIMPLE", getAdjacentIdsOpChain);

        for (final Element result : getAdjacentIdsResults) {
            print("GET_ADJACENT_IDS_SIMPLE_RESULT", result.toString());
        }
        // ---------------------------------------------------------

        // [to vertices simple] simple toVertices operation chain example
        // ---------------------------------------------------------
        final OperationChain<CloseableIterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EdgeSeed(2, 3, DirectedType.EITHER), new EdgeSeed(2, 5, DirectedType.EITHER))
                        .build())
                .then(new ToVertices.Builder()
                        .useMatchedVertex(ToVertices.UseMatchedVertex.OPPOSITE)
                        .edgeVertices(ToVertices.EdgeVertices.NONE)
                        .build())
                .then(new ToEntitySeeds())
                .then(new GetElements())
                .build();

        final CloseableIterable<? extends Element> toVerticesResults = graph.execute(opChain, user);

        printJsonAndPython("TO_VERTICES_SIMPLE", opChain);

        for (final Element result : toVerticesResults) {
            print("TO_VERTICES_SIMPLE_RESULT", result.toString());
        }
        // ---------------------------------------------------------

        return toVerticesResults;
    }

    private List<String> makeWalksPrintable(final Iterable<Walk> walks) {
        final List<String> printableWalks = new ArrayList<>();
        for (final Walk walk : walks) {
            printableWalks.add(Walk.class.getName() + walk.getVerticesOrdered()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(" --> ", "[ ", " ]")));
        }
        return printableWalks;
    }
}
