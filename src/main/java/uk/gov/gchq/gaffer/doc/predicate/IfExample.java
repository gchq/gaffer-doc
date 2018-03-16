/*
 * Copyright 2017-2018 Crown Copyright
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
package uk.gov.gchq.gaffer.doc.predicate;

import uk.gov.gchq.koryphe.impl.predicate.If;
import uk.gov.gchq.koryphe.impl.predicate.IsA;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;
import uk.gov.gchq.koryphe.impl.predicate.Not;

import java.util.ArrayList;
import java.util.HashMap;

public class IfExample extends PredicateExample {

    public static void main(final String[] args) {
        new IfExample().runAndPrint();
    }

    public IfExample() {
        super(If.class);
    }

    @Override
    protected void runExamples() {
        applyPredicatesToInput();
    }

    public void applyPredicatesToInput() {
        // ---------------------------------------------------------
        final If<Comparable> predicate = new If<>(new IsA(Integer.class), new IsMoreThan(3), new Not<>(new IsA(String.class)));
        // ---------------------------------------------------------

        runExample(predicate, "This example tests first whether the input is an Integer. " +
                        "If so, it is then tested to see if the value is greater than 3. " +
                        "Otherwise, since it is not an Integer, we then test to see if it is NOT a String.",
                2,
                3,
                5,
                "test",
                new HashMap<>(),
                new ArrayList<>());
    }
}
