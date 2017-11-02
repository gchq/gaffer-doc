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
package uk.gov.gchq.gaffer.doc.operation.spark;

import org.graphframes.GraphFrame;

import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.spark.operation.graphframe.GetGraphFrameOfElements;
import uk.gov.gchq.gaffer.spark.operation.graphframe.PageRank;

public class PageRankExample extends SparkOperationExample {

    public static void main(final String[] args) {
        new PageRankExample().run();
    }

    @Override
    protected void _runExamples() throws Exception {
        getPageRankForGraph();
        endOfMethod();
    }

    public PageRankExample() {
        super(PageRank.class);
        skipEndOfMethodBreaks = true;
    }

    public void getPageRankForGraph() {
        // ---------------------------------------------------------

        final GetGraphFrameOfElements getGraphFrameOfElements = new GetGraphFrameOfElements.Builder()
                .view(new View.Builder()
                        .entity("entity")
                        .edge("edge")
                        .build())
                .build();

        final PageRank pageRank = new PageRank.Builder()
                .maxIterations(20)
                .build();

        final OperationChain<GraphFrame> opChain = new OperationChain.Builder()
                .first(getGraphFrameOfElements)
                .then(pageRank)
                .build();

        // ---------------------------------------------------------
        final GraphFrame graphFrame = runExample(opChain, null);

        log("```");
        log(graphFrame.vertices()
                .selectExpr("group", "id", "count", "format_number(pagerank, 2) as pagerank2")
                .showString(100, 20));
        log("```");

    }
}
