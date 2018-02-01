/*
 * Copyright 2017 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.ExtractValues;

import java.util.HashMap;
import java.util.Map;

public class ExtractValuesExample extends FunctionExample {

    public static void main(final String[] args) {
        new ExtractValueExample().runAndPrint();
    }

    public ExtractValuesExample() {
        super(ExtractValues.class, "An ExtractValues will return a Collection of the values from a provided Java Map.");
    }

    @Override
    protected void runExamples() {
        extractValuesFromMap();
    }

    public void extractValuesFromMap() {
        // ---------------------------------------------------------
        final ExtractValues<String, Integer> function = new ExtractValues<>();
        // ---------------------------------------------------------

        final Map<String, Integer> input = new HashMap<>();
        input.put("theKey", 1);
        input.put("theWholeKey", 2);
        input.put("nothingButTheKey", 3);

        runExample(function,
                null,
                input);
    }
}
