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
package uk.gov.gchq.gaffer.doc.dev.walkthrough;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.access.predicate.AccessPredicate;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.id.EntityId;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.named.operation.AddNamedOperation;
import uk.gov.gchq.gaffer.named.operation.GetAllNamedOperations;
import uk.gov.gchq.gaffer.named.operation.NamedOperation;
import uk.gov.gchq.gaffer.named.operation.NamedOperationDetail;
import uk.gov.gchq.gaffer.named.operation.ParameterDetail;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.Limit;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.user.User;
import uk.gov.gchq.koryphe.impl.function.CallMethod;
import uk.gov.gchq.koryphe.impl.predicate.And;
import uk.gov.gchq.koryphe.impl.predicate.CollectionContains;
import uk.gov.gchq.koryphe.predicate.AdaptedPredicate;

import java.io.IOException;
import java.util.Map;

public class NamedOperations extends DevWalkthrough {
    public NamedOperations() {
        super("NamedOperations", "RoadAndRoadUseWithTimesAndCardinalities");
    }

    public CloseableIterable<? extends Element> run() throws OperationException, IOException {
        /// [graph] create a graph using our schema and store properties
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

        // [add named operation] create an operation chain to be executed as a named operation
        // ---------------------------------------------------------
        final AddNamedOperation addOperation = new AddNamedOperation.Builder()
                .operationChain(new OperationChain.Builder()
                        .first(new GetElements.Builder()
                                .view(new View.Builder()
                                        .edge("RoadUse")
                                        .build())
                                .build())
                        .then(new Limit.Builder<>().resultLimit(10).build())
                        .build())
                .description("named operation limit query")
                .name("2-limit")
                .readAccessRoles("read-user")
                .writeAccessRoles("write-user")
                .score(2)
                .overwrite()
                .build();

        graph.execute(addOperation, user);
        // ---------------------------------------------------------

        // [get all named operations] Get all named operations
        // ---------------------------------------------------------
        final CloseableIterable<NamedOperationDetail> details = graph.execute(new GetAllNamedOperations(), user);

        // ---------------------------------------------------------
        for (final NamedOperationDetail detail : details) {
            print("ALL_NAMED_OPERATIONS", detail.toString());
        }

        // [create named operation] create the named operation
        // ---------------------------------------------------------
        final NamedOperation<EntityId, CloseableIterable<? extends Element>> operation =
                new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                        .name("2-limit")
                        .input(new EntitySeed("10"))
                        .build();
        // ---------------------------------------------------------

        // [execute named operation] Get the results
        // ---------------------------------------------------------
        final CloseableIterable<? extends Element> results = graph.execute(operation, user);

        // ---------------------------------------------------------
        for (final Object result : results) {
            print("NAMED_OPERATION_RESULTS", result.toString());
        }

        // [add named operation with parameters] create an operation chain to be executed as a named operation
        // with parameters
        // ---------------------------------------------------------
        String opChainString = "{" +
                "  \"operations\" : [ {" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\"," +
                "    \"view\" : {" +
                "      \"edges\" : {" +
                "        \"RoadUse\" : { }" +
                "      }," +
                "      \"entities\" : { }" +
                "    }" +
                "  }, {" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.Limit\"," +
                "    \"resultLimit\" : \"${limitParam}\"" +
                "  } ]" +
                "}";

        ParameterDetail param = new ParameterDetail.Builder()
                .defaultValue(1L)
                .description("Limit param")
                .valueClass(Long.class)
                .build();
        Map<String, ParameterDetail> paramDetailMap = Maps.newHashMap();
        paramDetailMap.put("limitParam", param);

        final AddNamedOperation addOperationWithParams = new AddNamedOperation.Builder()
                .operationChain(opChainString)
                .description("named operation limit query")
                .name("custom-limit")
                .readAccessRoles("read-user")
                .writeAccessRoles("write-user")
                .parameters(paramDetailMap)
                .overwrite()
                .build();

        graph.execute(addOperationWithParams, user);
        // ---------------------------------------------------------

        // [create named operation with parameters] create the named operation with a parameter
        // ---------------------------------------------------------
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("limitParam", 3L);

        final NamedOperation<EntityId, CloseableIterable<? extends Element>> operationWithParams =
                new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                        .name("custom-limit")
                        .input(new EntitySeed("10"))
                        .parameters(paramMap)
                        .build();
        // ---------------------------------------------------------

        // [execute named operation with parameters] Get the results
        // ---------------------------------------------------------

        final CloseableIterable<? extends Element> namedOperationResults = graph.execute(operationWithParams, user);
        // ---------------------------------------------------------

        for (final Object result : namedOperationResults) {
            print("NAMED_OPERATION_WITH_PARAMETER_RESULTS", result.toString());
        }

        // [add named operation access controlled resource] create a named operation using access controlled resource predicates
        // ---------------------------------------------------------
        final AddNamedOperation addNamedOperationAccessControlledResource = new AddNamedOperation.Builder()
                .operationChain(new OperationChain.Builder()
                        .first(new GetElements.Builder()
                                .view(new View.Builder()
                                        .edge("RoadUse")
                                        .build())
                                .build())
                        .then(new Limit.Builder<>().resultLimit(10).build())
                        .build())
                .description("named operation limit query")
                .name("access-controlled-2-limit")
                .score(2)
                .overwrite()
                .readAccessPredicate(new AccessPredicate(
                        new AdaptedPredicate(
                                new CallMethod("getOpAuths"),
                                new And(
                                        new CollectionContains("read-access-auth-1"),
                                        new CollectionContains("read-access-auth-2")))))

                .writeAccessPredicate(
                        new AccessPredicate(
                                new AdaptedPredicate(
                                        new CallMethod("getOpAuths"),
                                        new And(
                                                new CollectionContains("write-access-auth-1"),
                                                new CollectionContains("write-access-auth-2")))))

                .build();

        graph.execute(addNamedOperationAccessControlledResource, user);
        // ---------------------------------------------------------

        // [add full example named operation] Add the full example as a named operation, with fullExampleParams to configure the vehicle type and result limit
        // ---------------------------------------------------------
        final String fullExampleOpChain = "{\n" +
                "  \"operations\" : [ {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\n" +
                "    \"view\" : {\n" +
                "      \"edges\" : {\n" +
                "        \"RegionContainsLocation\" : { }\n" +
                "      }\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\n" +
                "    \"view\" : {\n" +
                "      \"edges\" : {\n" +
                "        \"LocationContainsRoad\" : { }\n" +
                "      }\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.output.ToSet\"\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\n" +
                "    \"view\" : {\n" +
                "      \"edges\" : {\n" +
                "        \"RoadHasJunction\" : { }\n" +
                "      }\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\",\n" +
                "    \"view\" : {\n" +
                "      \"entities\" : {\n" +
                "        \"JunctionUse\" : {\n" +
                "          \"properties\" : [\"${vehicle}\"],\n" +
                "          \"preAggregationFilterFunctions\" : [ {\n" +
                "            \"selection\" : [ \"startDate\", \"endDate\" ],\n" +
                "            \"predicate\" : {\n" +
                "              \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.range.InDateRangeDual\",\n" +
                "              \"start\" : \"2000/01/01\",\n" +
                "              \"end\" : \"2001/01/01\"\n" +
                "            }\n" +
                "          } ],\n" +
                "          \"transientProperties\" : {\n" +
                "            \"${vehicle}\" : \"Long\"\n" +
                "          },\n" +
                "          \"transformFunctions\" : [ {\n" +
                "            \"selection\" : [ \"countByVehicleType\" ],\n" +
                "            \"function\" : {\n" +
                "              \"class\" : \"uk.gov.gchq.gaffer.types.function.FreqMapExtractor\",\n" +
                "              \"key\" : \"${vehicle}\"\n" +
                "            },\n" +
                "            \"projection\" : [ \"${vehicle}\" ]\n" +
                "          } ]\n" +
                "        }\n" +
                "      },\n" +
                "      \"globalElements\" : [ {\n" +
                "        \"groupBy\" : [ ]\n" +
                "      } ]\n" +
                "    },\n" +
                "    \"includeIncomingOutGoing\" : \"OUTGOING\"\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.compare.Sort\",\n" +
                "    \"comparators\" : [ {\n" +
                "      \"class\" : \"uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator\",\n" +
                "      \"property\" : \"${vehicle}\",\n" +
                "      \"groups\" : [ \"JunctionUse\" ],\n" +
                "      \"reversed\" : true\n" +
                "    } ],\n" +
                "    \"deduplicate\" : true,\n" +
                "    \"resultLimit\" : \"${result-limit}\"\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.If\",\n" +
                "    \"condition\" : \"${to-csv}\",\n" +
                "    \"then\" : {\n" +
                "        \"class\" : \"uk.gov.gchq.gaffer.operation.impl.output.ToCsv\",\n" +
                "        \"elementGenerator\" : {\n" +
                "          \"class\" : \"uk.gov.gchq.gaffer.data.generator.CsvGenerator\",\n" +
                "          \"fields\" : {\n" +
                "            \"VERTEX\" : \"Junction\",\n" +
                "            \"${vehicle}\" : \"${vehicle}\"\n" +
                "          },\n" +
                "          \"constants\" : { },\n" +
                "          \"quoted\" : false,\n" +
                "          \"commaReplacement\" : \" \"\n" +
                "        },\n" +
                "        \"includeHeader\" : true\n" +
                "    }\n" +
                "  } ]\n" +
                "}";
        final Map<String, ParameterDetail> fullExampleParams = Maps.newHashMap();
        fullExampleParams.put("vehicle", new ParameterDetail.Builder()
                .defaultValue("BUS")
                .description("The type of vehicle: HGVR3, BUS, HGVR4, AMV, HGVR2, HGVA3, PC, HGVA3, PC, HGCA5, HGVA6, CAR, HGV, WM2, LGV")
                .valueClass(String.class)
                .required(false)
                .build());
        fullExampleParams.put("result-limit", new ParameterDetail.Builder()
                .defaultValue(2)
                .description("The maximum number of junctions to return")
                .valueClass(Integer.class)
                .required(false)
                .build());
        fullExampleParams.put("to-csv", new ParameterDetail.Builder()
                .defaultValue(false)
                .description("Enable this parameter to convert the results to a simple CSV in the format: Junction, Count")
                .valueClass(Boolean.class)
                .required(false)
                .build());
        final AddNamedOperation addFullExampleNamedOperation = new AddNamedOperation.Builder()
                .name("frequent-vehicles-in-region")
                .description("Finds the junctions in a region with the most of an individual vehicle (e.g BUS, CAR) in the year 2000. The input is the region.")
                .overwrite(true)
                .parameters(fullExampleParams)
                .operationChain(fullExampleOpChain)
                .build();

        // ---------------------------------------------------------

        printJsonAndPython("ADD_FULL_EXAMPLE_NAMED_OPERATION", addFullExampleNamedOperation);

        return namedOperationResults;
    }

    public static void main(final String[] args) throws OperationException, IOException {
        System.out.println(new NamedOperations().walkthrough());

    }
}
