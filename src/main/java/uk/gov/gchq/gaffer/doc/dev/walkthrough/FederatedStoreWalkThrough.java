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
package uk.gov.gchq.gaffer.doc.dev.walkthrough;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;

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
import uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements.Builder;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.store.StoreProperties;
import uk.gov.gchq.gaffer.store.library.HashMapGraphLibrary;
import uk.gov.gchq.gaffer.store.schema.Schema;
import uk.gov.gchq.gaffer.user.User;

public class FederatedStoreWalkThrough extends DevWalkthrough {
    public FederatedStoreWalkThrough() {
        super("FederatedStore", "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore");
    }

    public static void main(final String[] args) throws Exception {
        final FederatedStoreWalkThrough walkthrough = new FederatedStoreWalkThrough();
        walkthrough.run();
    }

    public CloseableIterable<? extends Element> run() throws Exception {
        final FederatedStoreProperties federatedProperty = FederatedStoreProperties.loadStoreProperties(StreamUtil.openStream(getClass(), "federatedStore.properties"));


        final StoreProperties mapProps = StoreProperties.loadStoreProperties("mockmapstore.properties");
        final StoreProperties accumuloProps = StoreProperties.loadStoreProperties("mockaccumulostore.properties");

        final Schema schema = new Schema.Builder()
                .json(StreamUtil.openStreams(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema"))
                .build();

        final HashMapGraphLibrary library = new HashMapGraphLibrary();
        library.addProperties("mapStore", mapProps);
        library.addProperties("accumuloStore", accumuloProps);
        library.addSchema("roadTraffic", schema);

        // [creating a federatedstore] create a store that federates to a MapStore and AccumuloStore
        // ---------------------------------------------------------
        final Graph federatedGraph = new Graph.Builder()
                .config(new GraphConfig.Builder()
                        .graphId("federatedRoadUse")
                        .library(library)
                        .build())
                .storeProperties(federatedProperty)
                .build();
        // ---------------------------------------------------------

        // [federatedstore properties]
        // ---------------------------------------------------------
        final FederatedStoreProperties exampleFederatedProperty = new FederatedStoreProperties();
        exampleFederatedProperty.setCacheProperties(HashMapCacheService.class.getName());
        // ---------------------------------------------------------

        log("fedPropJson", new String(JSONSerialiser.serialise(federatedProperty.getProperties(), true)));


        final User user = new User("user01");

        final AddGraph addMapGraph = new AddGraph.Builder()
                .graphId("mapGraph")
                .schema(new Schema.Builder()
                        .json(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema/entities.json"))
                        .json(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema/types.json"))
                        .build())
                .storeProperties(mapProps)
                .isPublic(true)
                .build();
        federatedGraph.execute(addMapGraph, user);

        final AddGraph addAccumuloGraph = new AddGraph.Builder()
                .graphId("accumuloGraph")
                .schema(new Schema.Builder()
                        .json(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema/edges.json"))
                        .json(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema/types.json"))
                        .build())
                .storeProperties(accumuloProps)
                .isPublic(true)
                .build();
        federatedGraph.execute(addAccumuloGraph, user);

        // [add another graph] add a graph to the federated store.
        // ---------------------------------------------------------
        AddGraph addAnotherGraph = new AddGraph.Builder()
                .graphId("AnotherGraph")
                .schema(schema)
                .storeProperties(mapProps)
                .build();
        federatedGraph.execute(addAnotherGraph, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(addAnotherGraph);
        addAccumuloGraph.setGraphAuths(null);
        log("addGraphJson", new String(JSONSerialiser.serialise(addAnotherGraph, true)));

        // [remove graph] remove a graph from the federated store.
        // ---------------------------------------------------------
        RemoveGraph removeGraph = new RemoveGraph.Builder()
                .graphId("AnotherGraph")
                .build();
        federatedGraph.execute(removeGraph, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(removeGraph);
        log("removeGraphJson", new String(JSONSerialiser.serialise(removeGraph, true)));

        // [get all graph ids] Get a list of all the graphId within the FederatedStore.
        // ---------------------------------------------------------
        final GetAllGraphIds getAllGraphIDs = new GetAllGraphIds();
        Iterable<? extends String> graphIds = federatedGraph.execute(getAllGraphIDs, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(getAllGraphIDs);
        log("getAllGraphIdsJson", new String(JSONSerialiser.serialise(getAllGraphIDs, true)));

        log("graphIds", graphIds.toString());


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

        log("addElementsJson", new String(JSONSerialiser.serialise(addOpChain, true)));

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
            log("elements", element.toString());
        }

        log("getElementsJson", new String(JSONSerialiser.serialise(getOpChain, true)));


        // [get elements from accumulo graph]
        // ---------------------------------------------------------
        final OperationChain<CloseableIterable<? extends Element>> getOpChainOnAccumuloGraph = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed("10"))
                        .option(FederatedStoreConstants.KEY_OPERATION_OPTIONS_GRAPH_IDS, "accumuloGraph")
                        .build())
                .build();

        CloseableIterable<? extends Element> elementsFromAccumuloGraph = federatedGraph.execute(getOpChainOnAccumuloGraph, user);
        // ---------------------------------------------------------

        for (final Element element : elementsFromAccumuloGraph) {
            log("elementsFromAccumuloGraph", element.toString());
        }

        // [select graphs for operations]
        // ---------------------------------------------------------
        GetAllElements selectGraphsForOperations = new Builder()
                .option(FederatedStoreConstants.KEY_OPERATION_OPTIONS_GRAPH_IDS, "graphId1, graphId2")
                .build();
        // ---------------------------------------------------------

        log("selectGraphsForOperationsJson", new String(JSONSerialiser.serialise(selectGraphsForOperations, true)));

        // [do not skip failed execution]
        // ---------------------------------------------------------
        GetAllElements doNotSkipFailedExecution = new Builder()
                .option(FederatedStoreConstants.KEY_SKIP_FAILED_FEDERATED_STORE_EXECUTE, "false")
                .build();
        // ---------------------------------------------------------

        log("doNotSkipFailedExecutionJson", new String(JSONSerialiser.serialise(doNotSkipFailedExecution, true)));


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
        log("addPublicGraphJson", new String(JSONSerialiser.serialise(publicGraph, true)));

        // [add private graph] add a graph to the federated store.
        // ---------------------------------------------------------
        AddGraph privateGraph = new AddGraph.Builder()
                .graphId("privateGraph")
                .parentSchemaIds(Lists.newArrayList("roadTraffic"))
                .parentPropertiesId("mapStore")
                        //.isPublic(false) <-- not specifying also defaults to false.
                        //.graphAuths() <-- leave blank/null or do no specify otherwise private access is lost.
                .build();
        federatedGraph.execute(addAnotherGraph, user);
        // ---------------------------------------------------------

        improveReadabilityOfJson(privateGraph);
        log("addPrivateGraphJson", new String(JSONSerialiser.serialise(privateGraph, true)));


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
        log("addSecureGraphJson", new String(JSONSerialiser.serialise(addSecureGraph, true)));


        // [disallow public access]
        // ---------------------------------------------------------
        FederatedStoreProperties disallowPublicProperties = new FederatedStoreProperties();
        disallowPublicProperties.setGraphsCanHavePublicAccess(false);
        // ---------------------------------------------------------

        log("disallowPublicAccessJson", new String(JSONSerialiser.serialise(disallowPublicProperties.getProperties(), true)));


        // [limit custom properties]
        // ---------------------------------------------------------
        FederatedStoreProperties limitCustomProperties = new FederatedStoreProperties();
        limitCustomProperties.setCustomPropertyAuths("Auth1, Auth2, Auth3");
        // ---------------------------------------------------------

        log("limitCustomPropertiesJson", new String(JSONSerialiser.serialise(limitCustomProperties.getProperties(), true)));


        return elements;
    }

    private String improveReadabilityOfJson(final Operation op) {
        return op.getOptions().remove(FederatedStoreConstants.KEY_OPERATION_OPTIONS_GRAPH_IDS);
    }
}
