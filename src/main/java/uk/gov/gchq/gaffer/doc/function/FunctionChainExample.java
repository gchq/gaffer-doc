/*
 * Copyright 2019 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.FunctionChain;
import uk.gov.gchq.koryphe.impl.function.ToSet;
import uk.gov.gchq.koryphe.impl.function.ToUpperCase;
import uk.gov.gchq.koryphe.tuple.ArrayTuple;

public class FunctionChainExample extends FunctionExample {
    public static void main(final String[] args) {
        new FunctionChainExample().runAndPrint();
    }

    public FunctionChainExample() {
        super(FunctionChain.class);
    }

    @Override
    protected void runExamples() {
        functionChain();
    }

    private void functionChain() {
        // ---------------------------------------------------------
        final FunctionChain function = new FunctionChain.Builder<>()
                .execute(new Integer[]{0}, new ToUpperCase(), new Integer[]{1})
                .execute(new Integer[]{1}, new ToSet(), new Integer[]{2})
                .build();
        // ---------------------------------------------------------

        runExample(function, null
                , new ArrayTuple("someString", null, null)
                , new ArrayTuple("SOMESTRING", null, null)
                , new ArrayTuple("somestring", null, null)
                , new ArrayTuple("@£$%", null, null)
                , new ArrayTuple("1234", null, null)
                , new ArrayTuple("", null, null)
                , new ArrayTuple(null, null, null)
                , new ArrayTuple(1234, null, null)

        );
    }


}
