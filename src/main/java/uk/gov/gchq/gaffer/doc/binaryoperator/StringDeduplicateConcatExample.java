/*
 * Copyright 2019-2020 Crown Copyright
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
package uk.gov.gchq.gaffer.doc.binaryoperator;

import uk.gov.gchq.gaffer.commonutil.pair.Pair;
import uk.gov.gchq.koryphe.impl.binaryoperator.StringDeduplicateConcat;

public class StringDeduplicateConcatExample extends BinaryOperatorExample {

    public StringDeduplicateConcatExample() {
        super(StringDeduplicateConcat.class, "Concatenates 2 strings and omits duplicates");
    }

    public static void main(final String[] args) {
        new StringConcatExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        stringDeduplicateConcatWithSeparator();
        stringDeduplicateConcatWithDefaultSeparator();
    }

    private void stringDeduplicateConcatWithSeparator() {
        // ---------------------------------------------------------
        final StringDeduplicateConcat stringDeduplicateConcat = new StringDeduplicateConcat();
        stringDeduplicateConcat.setSeparator(" ");
        // ---------------------------------------------------------

        runExample(stringDeduplicateConcat, null,
                new Pair<>("hello", "world"),
                new Pair<>("abc", null),
                new Pair<>(null, null),
                new Pair<>("abc,", "abc")
        );
    }

    private void stringDeduplicateConcatWithDefaultSeparator() {
        // ---------------------------------------------------------
        final StringDeduplicateConcat stringDeduplicateConcat = new StringDeduplicateConcat();
        // ---------------------------------------------------------

        runExample(stringDeduplicateConcat, null,
                new Pair<>("hello", "world"),
                new Pair<>("abc", null),
                new Pair<>(null, null),
                new Pair<>("abc,", "abc")
        );
    }
}
