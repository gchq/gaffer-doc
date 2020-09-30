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

import uk.gov.gchq.koryphe.impl.binaryoperator.Max;
import uk.gov.gchq.koryphe.impl.function.IterableFlatten;

public class IterableFlattenExample extends FunctionExample {

    public IterableFlattenExample() {
        super(IterableFlatten.class);
    }

    public static void main(final String[] args) {
        new IterableFlattenExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        withNoBinaryOperator();
        withABinaryOperator();
    }

    private void withNoBinaryOperator() {
        // ---------------------------------------------------------
        final IterableFlatten function = new IterableFlatten(null);
        // ---------------------------------------------------------

        runExample(function, null, Lists.newArrayList("a", "b", "c"));
    }

    private void withABinaryOperator() {
        // ---------------------------------------------------------
        final IterableFlatten function = new IterableFlatten<>(new Max());
        // ---------------------------------------------------------

        runExample(function, null,
                Lists.newArrayList(1, 2, 3, 1),
                Lists.newArrayList(1, null, 6),
                Lists.newArrayList(),
                null
        );
    }
}
