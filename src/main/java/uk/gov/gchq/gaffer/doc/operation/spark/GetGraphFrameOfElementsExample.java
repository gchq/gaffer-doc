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
import uk.gov.gchq.gaffer.spark.operation.graphframe.GetGraphFrameOfElements;

public class GetGraphFrameOfElementsExample extends SparkOperationExample {

    public static void main(final String[] args) {
        new GetGraphFrameOfElementsExample().run();
    }

    @Override
    protected void _runExamples() throws Exception {
        getGraphFrameOfElements();
        endOfMethod();
    }

    public GetGraphFrameOfElementsExample() {
        super(GetGraphFrameOfElements.class);
        skipEndOfMethodBreaks = true;
    }

    public void getGraphFrameOfElements() {
        // ---------------------------------------------------------

        final GetGraphFrameOfElements operation = new GetGraphFrameOfElements.Builder()
                .view(new View.Builder()
                        .entity("entity")
                        .edge("edge")
                        .build())
                .build();
        // ---------------------------------------------------------
        final GraphFrame graphFrame = runExample(operation, null);

        printJava("graphFrame.vertices().filter(\"vertex = 1 OR vertex = 2\").show()");
        log("The results are: ");
        log("```");
        log(graphFrame.vertices()
                .filter("vertex = 1 OR vertex = 2")
                .showString(100, 20));
        log("```");

        printJava("graphFrame.edges().filter(\"count > 1\").show()");
        log("The results are: ");
        log("```");
        log(graphFrame.edges()
                .filter("count > 1")
                .showString(100, 20));
        log("```");
    }
}
