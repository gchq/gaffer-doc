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

import com.google.common.collect.Lists;

import uk.gov.gchq.koryphe.impl.function.NthItem;

public class NthItemExample extends FunctionExample {
    public static void main(final String[] args) {
        new NthItemExample().runAndPrint();
    }

    public NthItemExample() {
        super(NthItem.class, "For a given Iterable, an NthItem will extract the item at the Nth index, where n is a user-provided selection. (Consider that this is array-backed, so a selection of \"1\" will extract the item at index 1, ie the 2nd item)\"");
    }

    @Override
    protected void runExamples() {
        extractNthItem();
    }

    public void extractNthItem() {
        // ---------------------------------------------------------
        final NthItem<Integer> function = new NthItem<>(2);
        // ---------------------------------------------------------

        runExample(function,
                null,
                Lists.newArrayList(3, 1, 4),
                Lists.newArrayList(1, 5, 9),
                Lists.newArrayList(2, 6, 5),
                Lists.newArrayList(2, null, 5),
                Lists.newArrayList(2, 6, null),
                null);
    }
}
