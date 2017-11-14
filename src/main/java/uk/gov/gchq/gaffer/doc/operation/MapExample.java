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
package uk.gov.gchq.gaffer.doc.operation;

import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.impl.Map;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements;
import uk.gov.gchq.koryphe.impl.function.FirstItem;

public class MapExample extends OperationExample {

    public static void main(final String[] args) {
        new MapExample().run();
    }

    public MapExample() {
        super(Map.class);
    }

    @Override
    protected void runExamples() {
        extractItemFromIterable();
    }

    public void extractItemFromIterable() {
        // ---------------------------------------------------------
        final OperationChain<Element> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new Map.Builder<Element, Element>()
                        .function(new FirstItem<>())
                        .build())
                .build();
        // ---------------------------------------------------------

        runExample(opChain, "Here, the Map operation is being used to map an " +
                "Iterable of Elements to a single Element.");
    }
}
