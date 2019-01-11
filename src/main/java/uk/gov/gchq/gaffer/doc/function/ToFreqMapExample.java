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
import uk.gov.gchq.gaffer.types.function.ToFreqMap;

import java.util.function.Function;

public class ToFreqMapExample extends FunctionExample {

    public ToFreqMapExample() {
        super(ToFreqMap.class, "Creates a new FreqMap and upserts a given value");
    }

    public static void main(final String[] args) {
        ToFreqMapExample example = new ToFreqMapExample();
        example.skipPython();
        example.runAndPrint();
    }


    @Override
    protected void runExamples() {
        toFreqMap();
    }

    private void toFreqMap() {
        // ---------------------------------------------------------
        Function toFreqMap = new ToFreqMap();
        // ---------------------------------------------------------

        runExample(
                toFreqMap,
                null,
                "aString", 100L, 20, new TypeValue("type1", "value1"), null
        );
    }
}
