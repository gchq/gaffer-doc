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

    public static void main(final String[] args) {
        new ReduceRelatedElementsExample().runAndPrint();
    }

    public ReduceRelatedElementsExample() {
        super(ReduceRelatedElements.class);
        skipPython();
    }

    @Override
    protected void runExamples() {
        basicExample();
    }

    private void basicExample() {
        // ---------------------------------------------------------
        final List<Element> elements = Arrays.asList(
                new Edge.Builder()
                        .source(11)
                        .dest(2)
                        .directed(true)
                        .group("basicEdge")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source(1)
                        .dest(3)
                        .directed(true)
                        .group("basicEdge")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Entity.Builder()
                        .vertex(2)
                        .group("basicEntity")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source(11)
                        .dest(1)
                        .directed(true)
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source(2)
                        .dest(22)
                        .directed(true)
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source(3)
                        .dest(33)
                        .directed(true)
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

        runExample(function, null,  elements, null);
    }

//    private static final class Longest extends KorypheBinaryOperator<String> {
//        @Override
//        protected String _apply(final String s, final String t) {
//            if (s.length() > t.length()) {
//                return s;
//            }
//
//            return t;
//        }
//    }
}
