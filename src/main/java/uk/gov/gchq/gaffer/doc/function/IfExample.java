/*
 * Copyright 2017-2019 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.If;
import uk.gov.gchq.koryphe.impl.function.ToLowerCase;
import uk.gov.gchq.koryphe.impl.function.ToUpperCase;
import uk.gov.gchq.koryphe.impl.predicate.StringContains;

public class IfExample extends FunctionExample {

    public static void main(final String[] args) {
        new IfExample().runAndPrint();
    }

    public IfExample() {
        super(If.class);
    }

    @Override
    protected void runExamples() {
        applyFunctionsToInput();
    }

    public void applyFunctionsToInput() {
        // ---------------------------------------------------------
        final If<String, String> predicate = new If<String, String>()
                .predicate(new StringContains("upper"))
                .then(new ToUpperCase())
                .otherwise(new ToLowerCase());
        // ---------------------------------------------------------

        runExample(predicate, "This example tests first whether the input contains 'upper'. " +
                        "If so, then it is converted to upper case. " +
                        "Otherwise, it is converted to lower case",
                null,
                "Convert me to upper case",
                "Convert me to lower case",
                "");
    }
}
