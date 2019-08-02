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

import uk.gov.gchq.koryphe.impl.function.ParseTime;

public class ParseTimeExample extends FunctionExample {
    public static void main(final String[] args) {
        new ParseTimeExample().runAndPrint();
    }

    public ParseTimeExample() {
        super(uk.gov.gchq.koryphe.impl.function.ParseTime.class);
    }

    @Override
    protected void runExamples() {
        parseTime();
        parseFormattedTime();
        parseFormattedGMT();
    }

    private void parseTime() {
        // ---------------------------------------------------------
        final ParseTime parseTime = new ParseTime();
        // ---------------------------------------------------------
        runExample(parseTime, null, "2000-01-02 03:04:05.006");
    }

    private void parseFormattedTime() {
        // ---------------------------------------------------------
        final ParseTime parseTime = new ParseTime().format("yyyy-MM hh:mm");
        // ---------------------------------------------------------
        runExample(parseTime, null, "2000-01-02 03:04");
    }

    private void parseFormattedGMT() {
        // ---------------------------------------------------------
        final ParseTime parseTime = new ParseTime()
                .format("yyyy-MM-dd")
                .timeUnit("SECOND")
                .timeZone("GMT");
        // ---------------------------------------------------------
        runExample(parseTime, null, "2000-01-02 03:04:05.006");
    }


}
