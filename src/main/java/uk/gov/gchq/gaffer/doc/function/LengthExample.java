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
package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.graph.Walk;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements;
import uk.gov.gchq.koryphe.impl.function.Length;

public class LengthExample extends FunctionExample {

    public static void main(final String[] args) {
        new LengthExample().runAndPrint();
    }

    public LengthExample() {
        super(Length.class);
    }

    @Override
    protected void runExamples() {
        skipPython();
        countNumberOfConfiguredOptions();
        countNumberOfEdgesInAWalk();
    }

    public void countNumberOfConfiguredOptions() {
        // ---------------------------------------------------------
        final Length function = new Length();
        // ---------------------------------------------------------

        runExample(function,
                "This simple example returns the length of the Map, " +
                        "containing all configured options on the Operation.",
                new GetAllElements.Builder()
                        .option("option1", "value1")
                        .option("option2", "value2")
                        .option("option3", "value3")
                        .build().getOptions());
    }

    public void countNumberOfEdgesInAWalk() {
        // ---------------------------------------------------------
        final Length function = new Length();
        // ---------------------------------------------------------

        runExample(function,
                "This example will, provided a Walk object, return the Length of the Walk, " +
                        "ie the number of Edges from start to finish.",
                new Walk.Builder()
                        .entity(new Entity.Builder()
                                .group("BasicEntity")
                                .vertex("A")
                                .build())
                        .edge(new Edge.Builder()
                                .group("BasicEdge")
                                .source("A")
                                .dest("B")
                                .directed(true)
                                .build())
                        .entity(new Entity.Builder()
                                .group("BasicEntity")
                                .vertex("B")
                                .build())
                        .edges(new Edge.Builder()
                                        .group("BasicEdge")
                                        .source("B")
                                        .dest("C")
                                        .directed(true)
                                        .build(),
                                new Edge.Builder()
                                        .group("EnhancedEdge")
                                        .source("B")
                                        .dest("C")
                                        .directed(true)
                                        .build())
                        .entity(new Entity.Builder()
                                .group("BasicEntity")
                                .vertex("C")
                                .build())
                        .edge(new Edge.Builder()
                                .group("BasicEdge")
                                .source("C")
                                .dest("A")
                                .directed(true)
                                .build())
                        .entity(new Entity.Builder()
                                .group("BasicEntity")
                                .vertex("A")
                                .build())
                        .build());
    }
}
