/*
 * Copyright 2016-2019 Crown Copyright
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


import uk.gov.gchq.koryphe.impl.predicate.range.InDateRange;
import uk.gov.gchq.koryphe.util.DateUtil;
import uk.gov.gchq.koryphe.util.TimeUnit;

import java.util.Date;

public class InDateRangeExample extends PredicateExample {
    public static void main(final String[] args) {
        new InDateRangeExample().runAndPrint();
    }

    public InDateRangeExample() {
        super(InDateRange.class,
                "You can configure the start and end time strings using the following formats:\n" +
                        "* timestamp in milliseconds\n" +
                        "* yyyy/MM\n" +
                        "* yyyy/MM/dd\n" +
                        "* yyyy/MM/dd HH\n" +
                        "* yyyy/MM/dd HH:mm\n" +
                        "* yyyy/MM/dd HH:mm:ss\n\n" +
                        "You can use a space, '-', '/', '_', ':', '|', or '.' to separate the parts.");
    }

    @Override
    public void runExamples() {
        inDateRangeWithDayPrecision();
        inDateRangeWithSecondPrecision();
        inDateRangeWithTimestamps();
        inDateRangeExclusive();
        withinTheLastWeek();
        exactly7HoursAgo();
    }

    public void inDateRangeWithDayPrecision() {
        // ---------------------------------------------------------
        final InDateRange function = new InDateRange.Builder()
                .start("2017/01/01")
                .end("2017/02/01")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                getTimestamp("2016/01/01 00:00"),
                getTimestamp("2017/01/01 00:00"),
                getTimestamp("2017/01/01 01:00"),
                getTimestamp("2017/01/01 23:59:59"),
                getTimestamp("2017/02/01 00:00:00"),
                getTimestamp("2017/02/01 00:00:01"),
                null);
    }

    public void inDateRangeWithSecondPrecision() {
        // ---------------------------------------------------------
        final InDateRange function = new InDateRange.Builder()
                .start("2017/01/01 01:30:10")
                .end("2017/01/01 01:30:50")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                getTimestamp("2017/01/01 01:30:09"),
                getTimestamp("2017/01/01 01:30:10"),
                getTimestamp("2017/01/01 01:30:20"),
                getTimestamp("2017/01/01 01:30:50"),
                getTimestamp("2017/01/01 01:30:51"),
                null);
    }


    public void inDateRangeWithTimestamps() {
        // ---------------------------------------------------------
        final InDateRange function = new InDateRange.Builder()
                .start("1483315200")
                .end("1485907200")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Date(1483315199L),
                new Date(1483315200L),
                new Date(1483316200L),
                new Date(1485907200L),
                new Date(1485907201L),
                null);
    }

    public void inDateRangeExclusive() {
        // ---------------------------------------------------------
        final InDateRange function = new InDateRange.Builder()
                .start("2017/01/01")
                .end("2017/02/01")
                .startInclusive(false)
                .endInclusive(false)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                getTimestamp("2016/01/01 00:00"),
                getTimestamp("2017/01/01 00:00"),
                getTimestamp("2017/01/01 01:00"),
                getTimestamp("2017/01/01 23:59:59"),
                getTimestamp("2017/02/01 00:00:00"),
                getTimestamp("2017/02/01 00:00:01"),
                null);
    }

    public void withinTheLastWeek() {
        // ---------------------------------------------------------
        final InDateRange function = new InDateRange.Builder()
                .startOffset(-7L)
                        // end is not set - it is unbounded
                .offsetUnit(TimeUnit.DAY)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                "If the end of the range is not specified then the end of the range is unbounded.",
                new Date(System.currentTimeMillis() - DateUtil.DAYS_TO_MILLISECONDS * 8),
                new Date(System.currentTimeMillis() - DateUtil.DAYS_TO_MILLISECONDS * 6),
                new Date(System.currentTimeMillis() - DateUtil.DAYS_TO_MILLISECONDS),
                new Date(),
                null);
    }

    public void exactly7HoursAgo() {
        // ---------------------------------------------------------
        final InDateRange function = new InDateRange.Builder()
                .startOffset(-7L)
                .endOffset(-6L)
                .endInclusive(false)
                .offsetUnit(TimeUnit.HOUR)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Date(System.currentTimeMillis() - DateUtil.HOURS_TO_MILLISECONDS * 8),
                new Date(System.currentTimeMillis() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L),
                new Date(System.currentTimeMillis() - DateUtil.HOURS_TO_MILLISECONDS * 6 - 10000L),
                new Date(System.currentTimeMillis() - DateUtil.HOURS_TO_MILLISECONDS * 6 + 10000L),
                new Date(),
                null);
    }

    private Date getTimestamp(final String dateStr) {
        return DateUtil.parse(dateStr);
    }
}
