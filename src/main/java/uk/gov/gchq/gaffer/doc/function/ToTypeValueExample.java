/*
 * Copyright 2018 Crown Copyright
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

import uk.gov.gchq.gaffer.types.TypeValue;
import uk.gov.gchq.gaffer.types.function.ToTypeValue;

import java.util.function.Function;

public class ToTypeValueExample extends FunctionExample {

    public ToTypeValueExample() {
        super(ToTypeValue.class, "Converts a value into a TypeValue");
    }

    public static void main(final String[] args) {
        new ToTypeValueExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        toTypeValue();
    }

    private void toTypeValue() {
        // ---------------------------------------------------------
        Function toTypeValue = new ToTypeValue();
        // ---------------------------------------------------------

        super.runExample(
                toTypeValue,
                null,
                "aString", 100L, 25, new TypeValue("type1", "value1"), null
        );
    }
}
