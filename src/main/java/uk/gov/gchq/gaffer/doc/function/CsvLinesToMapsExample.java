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

import uk.gov.gchq.koryphe.impl.function.CsvLinesToMaps;

import java.util.Arrays;
import java.util.List;

public class CsvLinesToMapsExample extends FunctionExample {
    public static void main(final String[] args) {
        new CsvLinesToMapsExample().runAndPrint();
    }

    public CsvLinesToMapsExample() {
        super(uk.gov.gchq.koryphe.impl.function.CsvLinesToMaps.class);
    }

    @Override
    protected void runExamples() {
        csvToMap();
        cstToMapDeliminator();
    }

    private void csvToMap() {
        // ---------------------------------------------------------
        final CsvLinesToMaps function = new CsvLinesToMaps()
                .header("header1", "header2", "header3")
                .firstRow(1);
        // ---------------------------------------------------------

        final List<String> input = Arrays.asList(
                "header1,header2,header3",
                "value1,value2,value3"
        );
        runExample(function, null, input);
    }

    private void cstToMapDeliminator() {
        // ---------------------------------------------------------
        final CsvLinesToMaps function = new CsvLinesToMaps()
                .header("header1", "header2", "header3")
                .firstRow(1)
                .delimiter('|');
        // ---------------------------------------------------------

        final List<String> input = Arrays.asList(
                "header1|header2|header3",
                "value1|value2|value3"
        );
        runExample(function, null, input);
    }
}
