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
package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.element.IdentifierType;
import uk.gov.gchq.gaffer.data.element.function.ExtractId;

public class ExtractIdExample extends FunctionExample {

    public static void main(final String[] args) {
        new ExtractIdExample().runAndPrint();
    }

    public ExtractIdExample() {
        super(ExtractId.class);
    }

    @Override
    protected void runExamples() {
        extractSourceFromEdge();
        extractVertexFromEntity();
    }

    public void extractSourceFromEdge() {
        // ---------------------------------------------------------
        final ExtractId function = new ExtractId(IdentifierType.SOURCE);
        // ---------------------------------------------------------

        runExample(function,
                "The function will simply extract the value of the provided Id, for a given Element. " +
                        "This Id can either be an IdentifierType, or a String representation, eg \"SOURCE\".",
                new Edge.Builder()
                        .source("src")
                        .dest("dest")
                        .group("edge")
                        .directed(true)
                        .build(),
                new Edge.Builder()
                        .source(13.200)
                        .dest(15.642)
                        .group("otherEdge")
                        .directed(true)
                        .build());
    }

    public void extractVertexFromEntity() {
        // ---------------------------------------------------------
        final ExtractId function = new ExtractId(IdentifierType.VERTEX);
        // ---------------------------------------------------------

        runExample(function,
                "This example simply demonstrates the same functionality but on an Entity.",
                new Entity.Builder()
                        .group("entity")
                        .vertex("v1")
                        .build());
    }
}
