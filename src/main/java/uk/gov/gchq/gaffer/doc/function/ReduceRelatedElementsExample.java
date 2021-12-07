/*
 * Copyright 2021 Crown Copyright
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

import com.google.common.collect.Sets;

import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.element.function.ReduceRelatedElements;
import uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat;
import uk.gov.gchq.koryphe.impl.binaryoperator.Max;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReduceRelatedElementsExample extends FunctionExample {
    private static final String FUNCTION_DESCRIPTION = "This function takes an Iterable of Elements and combines all related elements using the provided aggregator and related group";

    public static void main(final String[] args) {
        new ReduceRelatedElementsExample().runAndPrint();
    }

    public ReduceRelatedElementsExample() {
        super(ReduceRelatedElements.class, FUNCTION_DESCRIPTION);
    }

    @Override
    protected void runExamples() {
        basicExample();
        complexExample();
    }

    private void basicExample() {
        // ---------------------------------------------------------
        final List<Element> elements = Arrays.asList(
                new Edge.Builder()
                        .source("1a")
                        .dest("2b")
                        .group("basicEdge")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("1a")
                        .dest("1b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("2a")
                        .dest("2b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("private"))
                        .build()
        );
        final ReduceRelatedElements function = new ReduceRelatedElements();
        function.setVisibilityProperty("visibility");
        function.setVisibilityAggregator(new CollectionConcat<>());
        function.setVertexAggregator(new Max());
        function.setRelatedVertexGroups(Collections.singleton("relatesTo"));
        // ---------------------------------------------------------

        String description = "In this small example, vertex 1a is related to vertex 1b, and vertex 2a is related to vertex 2b.  \n"
                                + "As well as this, vertex 1a is connected to vertex 2b with the basicEdge group.  \n"
                                + "We setup the function to do a few things.  \n"
                                + "Firstly, we set the visibility property name, then state we want to concatenate the visibility properties.  \n"
                                + "Next we set the vertex aggregator to the Max Binary Operator. This will be used to compare and reduce vertices with.  \n"
                                + "Finally, we assert the vertex groups that describe which vertices are related, in this case 'relatesTo'.  \n\n"
                                + "In our results we should expect to see that 1b and 2b are source and dest as they were aggregated with the Max operator.  \n"
                                + "The other properties should be listed in the related properties. As well as this, the visiblities should be concatenated together.  ";

        runExample(function, description,  elements, null);
    }

    private void complexExample() {
        // ---------------------------------------------------------
        final List<Element> elements = Arrays.asList(
                new Edge.Builder()
                        .source("1b")
                        .dest("2a")
                        .group("basicEdge")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("1a")
                        .dest("3a")
                        .group("basicEdge")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Entity.Builder()
                        .vertex("2a")
                        .group("basicEntity")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("1b")
                        .dest("1a")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("2a")
                        .dest("2b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("3a")
                        .dest("3b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("private"))
                        .build(),
                new Edge.Builder()
                        .source("2a")
                        .dest("3b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("private"))
                        .build(),
                new Edge.Builder()
                        .source("2b")
                        .dest("3a")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("3a")
                        .dest("4b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("private"))
                        .build(),
                new Edge.Builder()
                        .source("5b")
                        .dest("4a")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build()
        );
        final ReduceRelatedElements function = new ReduceRelatedElements();
        function.setVisibilityProperty("visibility");
        function.setVisibilityAggregator(new CollectionConcat<>());
        function.setVertexAggregator(new Max());
        function.setRelatedVertexGroups(Collections.singleton("relatesTo"));
        // ---------------------------------------------------------

        runExample(function, null,  elements);
    }
}
