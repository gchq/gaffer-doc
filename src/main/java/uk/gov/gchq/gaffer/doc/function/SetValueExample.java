/*
 * Copyright 2018-2019 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.SetValue;

import java.util.Arrays;

public class SetValueExample extends FunctionExample {
    public static void main(final String[] args) {
        new SetValueExample().runAndPrint();
    }

    public SetValueExample() {
        super(SetValue.class, "Returns a set value from any input.");
    }

    @Override
    public void runExamples() {
        setValue();
    }

    public void setValue() {
        // ---------------------------------------------------------
        final SetValue function = new SetValue(5);
        // ---------------------------------------------------------

        runExample(function,
                null,
                4, 5, "aString", Arrays.asList(4, 5), null
        );
    }
}
