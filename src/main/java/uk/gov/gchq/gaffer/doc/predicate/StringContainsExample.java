/*
 * Copyright 2017-2018 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.predicate.StringContains;

public class StringContainsExample extends PredicateExample {

    public static void main(final String[] args) {
        new StringContainsExample().runAndPrint();
    }

    public StringContainsExample() {
        super(StringContains.class);
    }

    @Override
    protected void runExamples() {
        stringContainsValue();
        stringContainsValueIgnoreCase();
    }

    public void stringContainsValue() {
        // ---------------------------------------------------------
        final StringContains function = new StringContains("test");
        // ---------------------------------------------------------

        runExample(function, "Note - the StringContains predicate is " +
                "case sensitive by default, hence only exact matches " +
                "are found.", "This is a Test", "Test", "test");

    }

    public void stringContainsValueIgnoreCase() {
        // ---------------------------------------------------------
        final StringContains function = new StringContains("test", true);
        // ---------------------------------------------------------

        runExample(function, "Here the optional flag is set to true - " +
                "this disables case sensitivity.", "This is a Test", "Test", "test");
    }

    public void stringContainsEmptyString() {
        // ---------------------------------------------------------
        final StringContains function = new StringContains("");
        // ---------------------------------------------------------

        runExample(function, "An empty string will simply return true" +
                " for any non-null input.", "Test", "test");
    }
}
