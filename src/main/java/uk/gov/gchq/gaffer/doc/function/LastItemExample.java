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

import uk.gov.gchq.koryphe.impl.function.LastItem;

import java.util.Arrays;

public class LastItemExample extends FunctionExample {
    public static void main(final String[] args) {
        new LastItemExample().runAndPrint();
    }

    public LastItemExample() {
        super(LastItem.class, "For a given Iterable, a LastItem will extract the last item.");
    }

    @Override
    protected void runExamples() {
        extractLastItem();

    }

    public void extractLastItem() {
        // ---------------------------------------------------------
        final LastItem<Integer> function = new LastItem<>();
        // ---------------------------------------------------------

        runExample(function,
                null,
                Arrays.asList(1, 2, 3),
                Arrays.asList(5, 8, 13),
                Arrays.asList(21, 34, 55));
    }
}
