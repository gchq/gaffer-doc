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

import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseElementGenerator;
import uk.gov.gchq.gaffer.doc.user.generator.RoadUseCsvGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.graph.SeededGraphFilters.IncludeIncomingOutgoingType;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects;
import uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.user.User;

import java.io.IOException;

public class OperationChains extends UserWalkthrough {
    public OperationChains() {
        super("Operation Chains", "RoadAndRoadUse", RoadAndRoadUseElementGenerator.class);
    }

    @Override
    public Iterable<? extends String> run() throws OperationException, IOException {
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
                        .generator(new RoadAndRoadUseElementGenerator())
                        .input(IOUtils.readLines(StreamUtil.openStream(getClass(), dataPath)))
                        .build())
                .then(new AddElements())
                .build();

        graph.execute(addOpChain, user);
        // ---------------------------------------------------------
        print("The elements have been added.");


        // [get] Create and execute an operation chain consisting of 2 operations:
        // GetAdjacentIds - starting at vertex M5 get all adjacent vertices (vertices at other end of outbound edges)
        // GetElements - get outbound edges
        // ---------------------------------------------------------
        final OperationChain<? extends Iterable<? extends Element>> opChain =
                new OperationChain.Builder()
                        .first(new GetAdjacentIds.Builder()
                                .input(new EntitySeed("M5"))
                                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                        .then(new GetElements.Builder()
                                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                        .build();

        final Iterable<? extends Element> results = graph.execute(opChain, user);
        // ---------------------------------------------------------

        print("\n2nd hop edges\n");
        for (final Element result : results) {
            print("RESULT", result.toString());
        }

        // [get and convert] Create and execute an operation chain consisting of 3 operations:
        // GetAdjacentIds - starting at vertex 1 get all adjacent vertices (vertices at other end of outbound edges)
        // GetElements - get outbound edges
        // GenerateObjects - convert the edges into csv
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends String>> opChainWithCSV =
                new OperationChain.Builder()
                        .first(new GetAdjacentIds.Builder()
                                .input(new EntitySeed("M5"))
                                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                        .then(new GetElements.Builder()
                                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                        .then(new GenerateObjects.Builder<String>()
                                .generator(new RoadUseCsvGenerator())
                                .build())
                        .build();

        final Iterable<? extends String> csvResults = graph.execute(opChainWithCSV, user);
        // ---------------------------------------------------------

        print("\n2nd hop edges converted back into comma separated strings.\n");
        for (final String result : csvResults) {
            print("CSV_RESULT", result);
        }

        return csvResults;
    }

    public static void main(final String[] args) throws OperationException, IOException {
        final OperationChains walkthrough = new OperationChains();
        walkthrough.run();
    }
}
