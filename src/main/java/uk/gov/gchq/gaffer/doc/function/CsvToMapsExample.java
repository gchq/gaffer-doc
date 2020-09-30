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

import uk.gov.gchq.koryphe.impl.function.CsvToMaps;

public class CsvToMapsExample extends FunctionExample {
    public static void main(final String[] args) {
        new CsvToMapsExample().runAndPrint();
    }

    public CsvToMapsExample() {
        super(uk.gov.gchq.koryphe.impl.function.CsvToMaps.class);
    }

    @Override
    protected void runExamples() {
        csvToMap();
    }

    private void csvToMap() {
        // ---------------------------------------------------------
        final CsvToMaps function = new CsvToMaps().header("header1", "header2", "header3").firstRow(1);
        // ---------------------------------------------------------

        runExample(function, null, "header1,header2,header3\nvalue1,value2,value3",
                "header1,header2,header3\nvalue1,value2,value3\nvalue4,value5,value6\"",
                "header1,header2,header3\n,,value3\nvalue4,value5,value6\"",
                "header1,header2,header3,header4\nvalue1,value2,value3,value4\nvalue5,value6,value7,value8\"",
                null,
                "");
    }
}
