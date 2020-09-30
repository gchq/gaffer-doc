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

import uk.gov.gchq.koryphe.impl.function.FirstItem;

public class FirstItemExample extends FunctionExample {
    public static void main(final String[] args) {
        new FirstItemExample().runAndPrint();
    }

    public FirstItemExample() {
        super(FirstItem.class, "For a given Iterable, a FirstItem will extract the first item.");
    }

    @Override
    protected void runExamples() {
        extractFirstItem();
    }

    public void extractFirstItem() {
        // ---------------------------------------------------------
        final FirstItem<Integer> function = new FirstItem<>();
        // ---------------------------------------------------------

        runExample(function,
                null,
                Lists.newArrayList(2, 3, 5),
                Lists.newArrayList(7, 11, 13),
                Lists.newArrayList(17, 19, null),
                Lists.newArrayList(null, 19, 27),
                null);
    }
}
