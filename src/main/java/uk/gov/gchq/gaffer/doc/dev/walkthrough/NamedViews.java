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

package uk.gov.gchq.gaffer.doc.dev.walkthrough;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.elementdefinition.view.NamedView;
import uk.gov.gchq.gaffer.data.elementdefinition.view.NamedViewDetail;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewParameterDetail;
import uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.named.view.AddNamedView;
import uk.gov.gchq.gaffer.named.view.GetAllNamedViews;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.user.User;

import java.io.IOException;
import java.util.Map;

public class NamedViews extends DevWalkthrough {

    public NamedViews() {
        super("NamedViews", "RoadAndRoadUseWithTimesAndCardinalities");
    }

    @Override
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

        // [add named view] add a NamedView to be reused
        // ---------------------------------------------------------
        final AddNamedView addNamedView = new AddNamedView.Builder()
                .name("RoadUse edges")
                .description("NamedView to get only RoadUse edges")
                .view(new View.Builder()
                        .edge("RoadUse")
                        .build())
                .overwrite(false)
                .build();

        graph.execute(addNamedView, user);
        // ---------------------------------------------------------

        // [get all named views] Get all named views
        // ---------------------------------------------------------
        final CloseableIterable<NamedViewDetail> namedViewDetails = graph.execute(new GetAllNamedViews(), user);

        // ---------------------------------------------------------
        for (final NamedViewDetail detail : namedViewDetails) {
            print("ALL_NAMED_VIEWS", detail.toString());
        }

        // [create get elements operation] create the operation using the NamedView
        // ---------------------------------------------------------
        final GetElements operation = new GetElements.Builder()
                .view(new NamedView.Builder()
                        .name("RoadUse edges")
                        .build())
                .input(new EntitySeed("10"))
                .build();
        // ---------------------------------------------------------

        // [execute get elements operation] Get the results
        // ---------------------------------------------------------
        final CloseableIterable<? extends Element> namedViewResults = graph.execute(operation, user);

        // ---------------------------------------------------------
        for (final Object result : namedViewResults) {
            print("OPERATION_USING_NAMED_VIEW_RESULTS", result.toString());
        }

        // [add named view with parameters] create an operation chain to be executed as a named operation
        // with parameters
        // ---------------------------------------------------------
        String viewString = "{" +
                "      \"edges\" : {" +
                "        \"RoadUse\" : {  " +
                "           \"preAggregationFilterFunctions\" : [ {" +
                "               \"predicate\" : {" +
                "                   \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.IsMoreThan\"," +
                "                   \"orEqualTo\" : false," +
                "                     \"value\": \"${isMoreThanParam}\"" +
                "                 }," +
                "             \"selection\" : [ \"${selectionParam}\" ]" +
                "         } ] }" +
                "      }," +
                "      \"entities\" : { }" +
                "}";

        ViewParameterDetail param = new ViewParameterDetail.Builder()
                .description("Selection param")
                .valueClass(String.class)
                .required(true)
                .build();

        ViewParameterDetail param1 = new ViewParameterDetail.Builder()
                .description("isMoreThan param")
                .defaultValue(0L)
                .valueClass(Long.class)
                .build();

        Map<String, ViewParameterDetail> paramDetailMap = Maps.newHashMap();
        paramDetailMap.put("selectionParam", param);
        paramDetailMap.put("isMoreThanParam", param1);

        final AddNamedView addNamedViewWithParams = new AddNamedView.Builder()
                .name("customCountView")
                .description("named View with count param")
                .view(viewString)
                .parameters(paramDetailMap)
                .overwrite(false)
                .build();

        graph.execute(addNamedViewWithParams, user);

        // [create named operation with parameters] create the named operation with a parameter
        // ---------------------------------------------------------
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("selectionParam", "count");
        paramMap.put("isMoreThanParam", 1L);

        final GetElements operationUsingParams = new GetElements.Builder()
                .view(new NamedView.Builder()
                        .name("customCountView")
                        .parameters(paramMap)
                        .build())
                .input(new EntitySeed("10"))
                .build();
        // ---------------------------------------------------------

        // [execute get elements operation with parameters] Get the results
        // ---------------------------------------------------------

        final CloseableIterable<? extends Element> namedViewResultsWithParams = graph.execute(operationUsingParams, user);
        // ---------------------------------------------------------

        for (final Object result : namedViewResultsWithParams) {
            print("OPERATION_USING_NAMED_VIEW_WITH_PARAMETERS_RESULTS", result.toString());
        }

        return namedViewResultsWithParams;
    }

    public static void main(final String[] args) throws OperationException, IOException {
        final NamedViews walkthrough = new NamedViews();
        walkthrough.run();
    }
}
