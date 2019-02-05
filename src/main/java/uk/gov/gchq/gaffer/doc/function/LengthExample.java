/*
 * Copyright 2017-2019 Crown Copyright
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

import com.google.common.collect.Lists;

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
        getLength();
    }

    public void getLength() {
        // ---------------------------------------------------------
        final Length function = new Length();
        // ---------------------------------------------------------

        runExample(function, null,
                Lists.newArrayList(new Entity.Builder()
                                .group("entity")
                                .vertex("1")
                                .build(),
                        new Entity.Builder()
                                .group("entity")
                                .vertex("2")
                                .build(),
                        new Entity.Builder()
                                .group("entity")
                                .vertex("3")
                                .build(),
                        new Entity.Builder()
                                .group("entity")
                                .vertex("4")
                                .build(),
                        new Entity.Builder()
                                .group("entity")
                                .vertex("5")
                                .build()),
                new GetAllElements.Builder()
                        .option("option1", "value1")
                        .option("option2", "value2")
                        .option("option3", "value3")
                        .build().getOptions(),
                new Walk.Builder()
                        .edge(new Edge.Builder()
                                .group("BasicEdge")
                                .source("A")
                                .dest("B")
                                .directed(true)
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
                        .edge(new Edge.Builder()
                                .group("BasicEdge")
                                .source("C")
                                .dest("A")
                                .directed(true)
                                .build())
                        .edge(new Edge.Builder()
                                .group("BasicEdge")
                                .source("A")
                                .dest("E")
                                .directed(true)
                                .build())
                        .build(),
                5,
                "some string",
                new String[]{"1", "2"},
                null);
    }
}
