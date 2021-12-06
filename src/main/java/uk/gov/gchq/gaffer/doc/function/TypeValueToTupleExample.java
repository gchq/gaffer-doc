/*
 * Copyright 2021 Crown Copyright
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

import uk.gov.gchq.gaffer.data.element.function.TypeValueToTuple;
import uk.gov.gchq.gaffer.types.TypeValue;

public class TypeValueToTupleExample extends FunctionExample {

    public static void main(final String[] args) {
        new TypeValueToTupleExample().runAndPrint();
    }

    public TypeValueToTupleExample() {
        super(TypeValueToTuple.class);
    }

    @Override
    protected void runExamples() {
        basicExample();
    }

    private void basicExample() {
        // ---------------------------------------------------------
        final TypeValueToTuple function = new TypeValueToTuple();
        // ---------------------------------------------------------
        TypeValue typeValue = new TypeValue("type", "value");
        runExample(function, null, typeValue, null);
    }
}
