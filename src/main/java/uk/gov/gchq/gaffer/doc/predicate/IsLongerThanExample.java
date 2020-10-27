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
package uk.gov.gchq.gaffer.doc.predicate;

import uk.gov.gchq.koryphe.impl.predicate.IsLongerThan;

import java.util.Arrays;

public class IsLongerThanExample extends PredicateExample {

    public IsLongerThanExample() {
        super(IsLongerThan.class);
    }

    public static void main(final String[] args) {
        new IsLongerThanExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        testInputs();
        testInputsWithEqualToSet();
    }

    public void testInputs() {
        // ---------------------------------------------------------
        final IsLongerThan predicate = new IsLongerThan(5);
        // ---------------------------------------------------------

        runExample(predicate,
                "This will test whether each input has a size/length attribute greater than 5.",
                "testString",
                "aTest",
                new String[5],
                new String[10],
                Arrays.asList(0, 1, 2, 3, 4, 5));
    }

    public void testInputsWithEqualToSet() {
        // ---------------------------------------------------------
        final IsLongerThan predicate = new IsLongerThan(5, true);
        // ---------------------------------------------------------

        runExample(predicate,
                "This will test whether each input has a size/length attribute greater than, " +
                        "OR equal to 5.",
                "test",
                "testString",
                "aTest",
                new String[5],
                new String[10],
                Arrays.asList(0, 1, 2, 3, 4, 5));
    }
}
