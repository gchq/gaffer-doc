/*
 * Copyright 2019-2020 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.CsvLinesToMaps;

import java.util.Arrays;

public class CsvLinesToMapsExample extends FunctionExample {
    public static void main(final String[] args) {
        new CsvLinesToMapsExample().runAndPrint();
    }

    public CsvLinesToMapsExample() {
        super(uk.gov.gchq.koryphe.impl.function.CsvLinesToMaps.class);
    }

    @Override
    protected void runExamples() {
        cstToMapDeliminator();
    }

    private void cstToMapDeliminator() {
        // ---------------------------------------------------------
        final CsvLinesToMaps function = new CsvLinesToMaps()
                .header("header1", "header2", "header3")
                .firstRow(1)
                .delimiter('|');
        // ---------------------------------------------------------

        runExample(function, null,
                Arrays.asList(
                        "header1|header2|header3",
                        "value1|value2|value3"),
                Arrays.asList(
                        "header1|header2|header3",
                        "value1||value3"),
                Arrays.asList(
                        "header1|header2|header3",
                        "value1|value2"),
                Arrays.asList(
                        "header1||header3",
                        "value1|value2|value3"),
                Arrays.asList(
                        "header1|header2",
                        "value1|value2|value3"),
                Arrays.asList(
                        "header1|header2|header3",
                        "value1|value2|value3",
                        "value4|value5|value6"),
                Arrays.asList(
                        "header1|header2|header3",
                        "",
                        "value4|value5|value6"),
                Arrays.asList(
                        "header1|header2|header3",
                        null,
                        "value4|value5|value6"),
                Arrays.asList(
                        "",
                        "value1|value2|value3",
                        "value4|value5|value6"),
                Arrays.asList(
                        null,
                        "value1|value2|value3",
                        "value4|value5|value6"));
    }
}
