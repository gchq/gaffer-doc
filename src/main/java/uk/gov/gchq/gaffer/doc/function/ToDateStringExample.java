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

import uk.gov.gchq.koryphe.impl.function.ToDateString;

import java.util.Date;

public class ToDateStringExample extends FunctionExample {
    public static void main(final String[] args) {
        new ToDateStringExample().runAndPrint();
    }

    public ToDateStringExample() {
        super(ToDateString.class);
    }

    @Override
    protected void runExamples() {
        toDateStringMicroFormatted();
        toDateStringShortFormattedToMin();
        toDateStringShortFormattedToDay();
    }

    private void toDateStringMicroFormatted() {
        // ---------------------------------------------------------
        final ToDateString function = new ToDateString("yyyy-MM-dd HH:mm:ss.SSS");
        // ---------------------------------------------------------

        runExample(function, null, new Date(499165200L),
                new Date(0L),
                System.currentTimeMillis(),
                new Date(-499165200L)
        );
    }

    private void toDateStringShortFormattedToMin() {
        // ---------------------------------------------------------
        final ToDateString function = new ToDateString("yy-MM-dd HH:mm");
        // ---------------------------------------------------------

        runExample(function, null, new Date(499165200L),
                new Date(0L),
                System.currentTimeMillis(),
                new Date(-499165200L),
                null
        );
    }

    private void toDateStringShortFormattedToDay() {
        // ---------------------------------------------------------
        final ToDateString function = new ToDateString("yy-MM-dd");
        // ---------------------------------------------------------

        runExample(function, null, new Date(499165200L),
                new Date(0L),
                System.currentTimeMillis(),
                new Date(-499165200L),
                null
        );
    }
}
