/*
 * Copyright 2020 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.FirstValid;
import uk.gov.gchq.koryphe.impl.predicate.StringContains;

public class FirstValidExample extends FunctionExample {

    public FirstValidExample() {
        super(FirstValid.class);
    }

    public static void main(final String[] args) {
        new FirstValidExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        withAPredicate();
        withNoPredicate();
    }

    private void withNoPredicate() {
        // ---------------------------------------------------------
        final FirstValid function = new FirstValid(null);
        // ---------------------------------------------------------


        runExample(function, "FirstValid always returns null if no predicate is specified",
                Lists.newArrayList("a", "b", "c"),
                Lists.newArrayList(1, 2, 3),
                Lists.newArrayList());
    }

    private void withAPredicate() {
        // ---------------------------------------------------------
        final FirstValid function = new FirstValid(new StringContains("my"));
        // ---------------------------------------------------------

        runExample(function, null,
                Lists.newArrayList(1, 2, 3),
                Lists.newArrayList("Hello", "my", "value"),
                Lists.newArrayList("MY", "tummy", "my", "My"),
                null
                );
    }
}
