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

import uk.gov.gchq.koryphe.impl.function.ExtractValue;

import java.util.HashMap;
import java.util.Map;

public class ExtractValueExample extends FunctionExample {

    public static void main(final String[] args) {
        new ExtractValueExample().runAndPrint();
    }

    public ExtractValueExample() {
        super(ExtractValue.class, "An ExtractValue will return the value associated with the pre-configured key, from a supplied Java Map.");
    }

    @Override
    protected void runExamples() {
        extractValueFromMap();
    }

    public void extractValueFromMap() {
        // ---------------------------------------------------------
        final ExtractValue<String, Integer> function = new ExtractValue<>("blueKey");
        // ---------------------------------------------------------

        final Map<String, Integer> input = new HashMap<>();
        input.put("redKey", 5);
        input.put("blueKey", 25);
        input.put("greenKey", 9);
        input.put("yellowKey", 4);

        runExample(function,
                null,
                input);
    }
}
