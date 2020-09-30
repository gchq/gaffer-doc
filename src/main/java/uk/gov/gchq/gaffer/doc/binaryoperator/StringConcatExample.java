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
import uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat;

public class StringConcatExample extends BinaryOperatorExample {

    public StringConcatExample() {
        super(StringConcat.class, "Concatenates 2 strings");
    }

    public static void main(final String[] args) {
        new StringConcatExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        stringConcatWithSeparator();
        stringConcatWithDefaultSeparator();
    }

    private void stringConcatWithSeparator() {
        // ---------------------------------------------------------
        final StringConcat stringConcat = new StringConcat();
        stringConcat.setSeparator(" ");
        // ---------------------------------------------------------

        runExample(stringConcat, null,
                new Pair<>("hello", "world"),
                new Pair<>("abc", null),
                new Pair<>(null, null)
        );
    }

    private void stringConcatWithDefaultSeparator() {
        // ---------------------------------------------------------
        final StringConcat stringConcat = new StringConcat();
        // ---------------------------------------------------------

        runExample(stringConcat, null,
                new Pair<>("hello", "world"),
                new Pair<>("abc", null),
                new Pair<>(null, null)
        );
    }
}
