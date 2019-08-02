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

package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.koryphe.impl.binaryoperator.Max;
import uk.gov.gchq.koryphe.impl.binaryoperator.Min;
import uk.gov.gchq.koryphe.impl.binaryoperator.Sum;
import uk.gov.gchq.koryphe.impl.function.ApplyBiFunction;
import uk.gov.gchq.koryphe.tuple.n.Tuple2;

public class ApplyBiFunctionExample extends FunctionExample {

    public static void main(final String[] args) {
        new ApplyBiFunctionExample().runAndPrint();
    }

    public ApplyBiFunctionExample() {
        super(uk.gov.gchq.koryphe.impl.function.ApplyBiFunction.class);
    }

    @Override
    protected void runExamples() {
        applyBiFunctionSum();
        applyBiFunctionMax();
        applyBiFunctionMin();
    }

    private void applyBiFunctionSum() {
        // ---------------------------------------------------------
        final ApplyBiFunction<Number, Number, Number> function = new ApplyBiFunction<>(new Sum());
        // ---------------------------------------------------------
        final Tuple2<Number, Number> input = new Tuple2<>(1, 2);
        runExample(function, null, input);
    }

    private void applyBiFunctionMax() {
        // ---------------------------------------------------------
        final ApplyBiFunction<Comparable, Comparable, Comparable> function = new ApplyBiFunction<>(new Max());
        // ---------------------------------------------------------
        final Tuple2<Number, Number> input = new Tuple2<>(1, 2);
        runExample(function, null, input);
    }

    private void applyBiFunctionMin() {
        // ---------------------------------------------------------
        final ApplyBiFunction<Comparable, Comparable, Comparable> function = new ApplyBiFunction<>(new Min());
        // ---------------------------------------------------------
        final Tuple2<Number, Number> input = new Tuple2<>(1, 2);
        runExample(function, null, input);
    }
}
