/*
 * Copyright 2016-2020 Crown Copyright
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

import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.element.function.UnwrapEntityId;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;

public class UnwrapEntityIdExample extends FunctionExample {
    public static void main(final String[] args) {
        new UnwrapEntityIdExample().runAndPrint();
    }

    public UnwrapEntityIdExample() {
        super(UnwrapEntityId.class, "If the object is an EntityId, the vertex value will be unwrapped and returned, otherwise the original object will be returned.");
    }

    @Override
    public void runExamples() {
        unwrapEntityIds();
    }

    public void unwrapEntityIds() {
        // ---------------------------------------------------------
        final UnwrapEntityId function = new UnwrapEntityId();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new EntitySeed("vertex1"),
                new EntitySeed("vertex2"),
                new Entity("group", "vertex2"),
                "a string",
                10,
                null
        );
    }
}
