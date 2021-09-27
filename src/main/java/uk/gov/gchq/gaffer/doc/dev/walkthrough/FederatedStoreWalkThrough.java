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
package uk.gov.gchq.gaffer.doc.dev.walkthrough;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.access.predicate.AccessPredicate;
import uk.gov.gchq.gaffer.cache.impl.HashMapCacheService;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator;
import uk.gov.gchq.gaffer.federatedstore.FederatedStoreConstants;
import uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties;
import uk.gov.gchq.gaffer.federatedstore.operation.AddGraph;
import uk.gov.gchq.gaffer.federatedstore.operation.GetAllGraphIds;
import uk.gov.gchq.gaffer.federatedstore.operation.RemoveGraph;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.graph.GraphConfig;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements.Builder;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.store.library.HashMapGraphLibrary;
import uk.gov.gchq.gaffer.store.schema.Schema;
import uk.gov.gchq.gaffer.user.User;
import uk.gov.gchq.koryphe.impl.function.CallMethod;
import uk.gov.gchq.koryphe.impl.predicate.And;
import uk.gov.gchq.koryphe.impl.predicate.CollectionContains;
import uk.gov.gchq.koryphe.predicate.AdaptedPredicate;

public class FederatedStoreWalkThrough extends DevWalkthrough {
    public FederatedStoreWalkThrough() {
        super("FederatedStore", "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore");
    }

    public static void main(final String[] args) throws Exception {
        System.out.println(new FederatedStoreWalkThrough().walkthrough());
    }

    public CloseableIterable<? extends Element> run() throws Exception {

        final Schema schema = new Schema.Builder()
                .json(StreamUtil.openStreams(getClass(), schemaPath))
                .build();

        final HashMapGraphLibrary library = new HashMapGraphLibrary();
        library.addProperties("mapStore", getMapStoreProperties());
        library.addSchema("roadTraffic", schema);

        // [creating a federatedstore] create a store that federates to across two MapStores
        // ---------------------------------------------------------
        final Graph federatedGraph = new Graph.Builder()
                .config(new GraphConfig.Builder()
                        .graphId(getClass().getSimpleName())
                        .library(library)
                        .build())
                .storeProperties(getFederatedStoreProperties())
                .build();
        // ---------------------------------------------------------

        // [federatedstore properties]
        // ---------------------------------------------------------
        final FederatedStoreProperties exampleFederatedProperty = new FederatedStoreProperties();
        exampleFederatedProperty.setCacheProperties(HashMapCacheService.class.getName());
        // ---------------------------------------------------------

        printJson("FED_PROP", getFederatedStoreProperties());

        final User user = new User("user01");

        final AddGraph addFirstGraph = new AddGraph.Builder()
                .graphId("firstGraph")
                .schema(new Schema.Builder()
                        .json(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema/entities.json"))
                        .json(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema/types.json"))
                        .build())
                .storeProperties(getMapStoreProperties())
                .isPublic(true)
                .build();
        federatedGraph.execute(addFirstGraph, user);

        final AddGraph addSecondGraph = new AddGraph.Builder()
                .graphId("secondGraph")
                .schema(new Schema.Builder()
                        .json(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema/edges.json"))
                        .json(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema/types.json"))
                        .build())
                .storeProperties(getMapStoreProperties())
                .isPublic(true)
                .build();
        federatedGraph.execute(addSecondGraph, user);

        // [add another graph] add a graph to the federated store.
        // ---------------------------------------------------------
        AddGraph addAnotherGraph = new AddGraph.Builder()
                .graphId("AnotherGraph")
                .schema(schema)
                .storeProperties(getMapStoreProperties())
                .build();
        federatedGraph.execute(addAnotherGraph, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(addAnotherGraph);
        addAnotherGraph.setGraphAuths(null);
        printJson("ADD_GRAPH", addAnotherGraph);

        // [remove graph] remove a graph from the federated store.
        // ---------------------------------------------------------
        RemoveGraph removeGraph = new RemoveGraph.Builder()
                .graphId("AnotherGraph")
                .build();
        federatedGraph.execute(removeGraph, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(removeGraph);
        printJson("REMOVE_GRAPH", removeGraph);

        // [get all graph ids] Get a list of all the graphId within the FederatedStore.
        // ---------------------------------------------------------
        final GetAllGraphIds getAllGraphIDs = new GetAllGraphIds();
        Iterable<? extends String> graphIds = federatedGraph.execute(getAllGraphIDs, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(getAllGraphIDs);
        printJson("GET_ALL_GRAPH_IDS", getAllGraphIDs);
        print("GRAPH_IDS", graphIds.toString());


        // [add elements] Create a data generator and add the edges to the federated graphs using an operation chain consisting of:
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

        federatedGraph.execute(addOpChain, user);
        // ---------------------------------------------------------

        printJsonAndPython("ADD_ELEMENTS", addOpChain);

        // [get elements]
        // ---------------------------------------------------------
        final OperationChain<CloseableIterable<? extends Element>> getOpChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed("10"))
                        .build())
                .build();

        CloseableIterable<? extends Element> elements = federatedGraph.execute(getOpChain, user);
        // ---------------------------------------------------------

        for (final Element element : elements) {
            print("ELEMENTS", element.toString());
        }

        printJsonAndPython("GET_ELEMENTS", getOpChain);


        // [get elements from first graph]
        // ---------------------------------------------------------
        final OperationChain<CloseableIterable<? extends Element>> getOpChainOnFirstGraph = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed("10"))
                        .option(FederatedStoreConstants.KEY_OPERATION_OPTIONS_GRAPH_IDS, "firstGraph")
                        .build())
                .build();

        CloseableIterable<? extends Element> elementsFromFirstGraph = federatedGraph.execute(getOpChainOnFirstGraph, user);
        // ---------------------------------------------------------

        for (final Element element : elementsFromFirstGraph) {
            print("ELEMENTS_FROM_FIRST_GRAPH", element.toString());
        }

        // [select graphs for operations]
        // ---------------------------------------------------------
        GetAllElements selectGraphsForOperations = new Builder()
                .option(FederatedStoreConstants.KEY_OPERATION_OPTIONS_GRAPH_IDS, "firstGraph, secondGraph")
                .build();
        // ---------------------------------------------------------

        printJsonAndPython("SELECT_GRAPHS_FOR_OPERATIONS", selectGraphsForOperations);

        // [do not skip failed execution]
        // ---------------------------------------------------------
        GetAllElements doNotSkipFailedExecution = new Builder()
                .option(FederatedStoreConstants.KEY_SKIP_FAILED_FEDERATED_STORE_EXECUTE, "false")
                .build();
        // ---------------------------------------------------------

        printJsonAndPython("DO_NOT_SKIP_FAILED_EXECUTION", doNotSkipFailedExecution);


        // [add public graph] add a graph to the federated store.
        // ---------------------------------------------------------
        AddGraph publicGraph = new AddGraph.Builder()
                .graphId("publicGraph")
                .parentSchemaIds(Lists.newArrayList("roadTraffic"))
                .parentPropertiesId("mapStore")
                .isPublic(true) //<-- public access
                .graphAuths("Auth1") //<-- used but irrelevant as graph has public access
                .build();
        federatedGraph.execute(addAnotherGraph, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(publicGraph);
        printJson("ADD_PUBLIC_GRAPH", publicGraph);

        // [add private graph] add a graph to the federated store.
        // ---------------------------------------------------------
        AddGraph privateGraph = new AddGraph.Builder()
                .graphId("privateGraph")
                .parentSchemaIds(Lists.newArrayList("roadTraffic"))
                .parentPropertiesId("mapStore")
                //.isPublic(false) <-- not specifying also defaults to false.
                //.graphAuths() <-- leave blank/null or do no specify otherwise private access is lost.
                .build();
        federatedGraph.execute(privateGraph, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(privateGraph);
        printJson("ADD_PRIVATE_GRAPH", privateGraph);


        // [add secure graph] add a graph to the federated store.
        // ---------------------------------------------------------
        AddGraph addSecureGraph = new AddGraph.Builder()
                .graphId("SecureGraph")
                .parentSchemaIds(Lists.newArrayList("roadTraffic"))
                .parentPropertiesId("mapStore")
                .graphAuths("Auth1", "Auth2", "Auth3")
                //.isPublic(false) <-- not specifying also defaults to false.
                .build();
        federatedGraph.execute(addSecureGraph, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(addSecureGraph);
        printJson("ADD_SECURE_GRAPH", addSecureGraph);


        // [add access controlled resource secure graph] add a graph to the federated store.
        // ---------------------------------------------------------
        AddGraph addAccessControlledResourceSecureGraph = new AddGraph.Builder()
                .graphId("AccessControlledResourceSecureGraph")
                .parentSchemaIds(Lists.newArrayList("roadTraffic"))
                .parentPropertiesId("mapStore")
                .readAccessPredicate(
                        new AccessPredicate(
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
        federatedGraph.execute(addAccessControlledResourceSecureGraph, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(addAccessControlledResourceSecureGraph);
        printJson("ADD_ACCESS_CONTROLLED_RESOURCE_SECURE_GRAPH", addAccessControlledResourceSecureGraph);


        // [disallow public access]
        // ---------------------------------------------------------
        FederatedStoreProperties disallowPublicProperties = new FederatedStoreProperties();
        disallowPublicProperties.setGraphsCanHavePublicAccess(false);
        // ---------------------------------------------------------

        printJson("DISALLOW_PUBLIC_ACCESS", disallowPublicProperties.getProperties());


        // [limit custom properties]
        // ---------------------------------------------------------
        FederatedStoreProperties limitCustomProperties = new FederatedStoreProperties();
        limitCustomProperties.setCustomPropertyAuths("Auth1, Auth2, Auth3");
        // ---------------------------------------------------------

        printJson("LIMIT_CUSTOM_PROPERTIES", limitCustomProperties.getProperties());


        return elements;
    }

    private String improveReadabilityOfJson(final Operation op) {
        return op.getOptions().remove(FederatedStoreConstants.KEY_OPERATION_OPTIONS_GRAPH_IDS);
    }
}
