/*
 * Copyright 2016 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.Identity;

public class IdentityExample extends FunctionExample {
    public static void main(final String[] args) {
        new IdentityExample().runAndPrint();
    }

    public IdentityExample() {
        super(Identity.class, "Just returns the input.");
    }

    @Override
    public void runExamples() {
        identity2();
    }

    public void identity2() {
        // ---------------------------------------------------------
        final Identity function = new Identity();
        // ---------------------------------------------------------

        runExample(function,
                null,
                6, 6.1, "input1", Lists.newArrayList(1, 2, 3), null
        );
    }
}
