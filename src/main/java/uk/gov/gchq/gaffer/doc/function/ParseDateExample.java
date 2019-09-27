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

import uk.gov.gchq.koryphe.impl.function.ParseDate;

public class ParseDateExample extends FunctionExample {
    public static void main(final String[] args) {
        new ParseDateExample().runAndPrint();
    }

    public ParseDateExample() {
        super(ParseDate.class);
    }

    @Override
    protected void runExamples() {
        parseDateGreenwichMeanTimePlus4Hours();
        parseDateGreenwichMeanTimePlus0Hours();
    }

    private void parseDateGreenwichMeanTimePlus4Hours() {
        // ---------------------------------------------------------
        final ParseDate parseDate = new ParseDate();
        parseDate.setFormat("yyyy-MM-dd HH:mm:ss.SSS");
        parseDate.setTimeZone("Etc/GMT+4");
        // ---------------------------------------------------------

        runExample(parseDate, "date string",
                "2015-10-21 16:29:00.000",
                "1985-10-26 09:00:00.000",
                "1885-01-01 12:00:00.000"
        );
    }

    private void parseDateGreenwichMeanTimePlus0Hours() {
        // ---------------------------------------------------------
        final ParseDate parseDate = new ParseDate();
        parseDate.setFormat("yyyy-MM-dd HH:mm");
        parseDate.setTimeZone("Etc/GMT+0");
        // ---------------------------------------------------------

        runExample(parseDate, "date string",
                "2015-10-21 16:29",
                "1985-10-26 09:00",
                "1885-01-01 12:00"
        );
    }


}
