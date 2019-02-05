/*
 * Copyright 2016-2019 Crown Copyright
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

import com.google.common.collect.Sets;

import uk.gov.gchq.koryphe.impl.function.CallMethod;

public class CallMethodExample extends FunctionExample {
    public static void main(final String[] args) {
        new CallMethodExample().runAndPrint();
    }

    public CallMethodExample() {
        super(CallMethod.class, "Allows you to call any public no-argument method on an object");
    }

    @Override
    public void runExamples() {
        callToString();
        callToLowerCase();
    }

    public void callToString() {
        // ---------------------------------------------------------
        final CallMethod function = new CallMethod("toString");
        // ---------------------------------------------------------

        runExample(function,
                null,
                "a string",
                1,
                Sets.newHashSet("item1", "item2"),
                null
        );
    }

    public void callToLowerCase() {
        // ---------------------------------------------------------
        final CallMethod function = new CallMethod("toLowerCase");
        // ---------------------------------------------------------

        runExample(function,
                null,
                "STRING1",
                "String2",
                10,
                null
        );
    }
}
