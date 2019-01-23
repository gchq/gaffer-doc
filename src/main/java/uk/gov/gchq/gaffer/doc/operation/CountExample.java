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

package uk.gov.gchq.gaffer.doc.operation;

import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.impl.Count;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements;
import uk.gov.gchq.gaffer.operation.serialisation.TypeReferenceImpl.String;

public class CountExample extends OperationExample {

    public static void main(final String[] args) {
        new CountExample().runAndPrint();
    }

    public CountExample() {
        super(Count.class, "Counts the number of items in an iterable");
    }

    @Override
    protected void runExamples() {
        countAllElements();
    }

    public Long countAllElements() {
        // ---------------------------------------------------------
        OperationChain<Long> countAllElements = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new Count<>())
                .build();
        // ---------------------------------------------------------
        return runExample(countAllElements, null);
    }
}
