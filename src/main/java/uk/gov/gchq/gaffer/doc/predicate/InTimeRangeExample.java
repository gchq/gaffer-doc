/*
 * Copyright 2016-2020 Crown Copyright
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


import uk.gov.gchq.koryphe.impl.predicate.range.InTimeRange;
import uk.gov.gchq.koryphe.util.DateUtil;
import uk.gov.gchq.koryphe.util.TimeUnit;

public class InTimeRangeExample extends PredicateExample {
    public static void main(final String[] args) {
        new InTimeRangeExample().runAndPrint();
    }

    public InTimeRangeExample() {
        super(InTimeRange.class,
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
        inTimeRangeWithDayPrecision();
        inTimeRangeWithSecondPrecision();
        inTimeRangeWithTimestamps();
        inTimeRangeExclusive();
        withinTheLastWeek();
        exactly7HoursAgo();
        inDateRangeWithTimeUnitMicroseconds();
    }

    public void inTimeRangeWithDayPrecision() {
        // ---------------------------------------------------------
        final InTimeRange function = new InTimeRange.Builder()
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

    public void inTimeRangeWithSecondPrecision() {
        // ---------------------------------------------------------
        final InTimeRange function = new InTimeRange.Builder()
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


    public void inTimeRangeWithTimestamps() {
        // ---------------------------------------------------------
        final InTimeRange function = new InTimeRange.Builder()
                .start("1483315200")
                .end("1485907200")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                1483315199L,
                1483315200L,
                1483316200L,
                1485907200L,
                1485907201L,
                null);
    }

    public void inTimeRangeExclusive() {
        // ---------------------------------------------------------
        final InTimeRange function = new InTimeRange.Builder()
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
        final InTimeRange function = new InTimeRange.Builder()
                .startOffset(-7L)
                        // end is not set - it is unbounded
                .offsetUnit(TimeUnit.DAY)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                "If the end of the range is not specified then the end of the range is unbounded.",
                System.currentTimeMillis() - DateUtil.DAYS_TO_MILLISECONDS * 8,
                System.currentTimeMillis() - DateUtil.DAYS_TO_MILLISECONDS * 6,
                System.currentTimeMillis() - DateUtil.DAYS_TO_MILLISECONDS,
                System.currentTimeMillis(),
                null);
    }

    public void exactly7HoursAgo() {
        // ---------------------------------------------------------
        final InTimeRange function = new InTimeRange.Builder()
                .startOffset(-7L)
                .endOffset(-6L)
                .endInclusive(false)
                .offsetUnit(TimeUnit.HOUR)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                System.currentTimeMillis() - DateUtil.HOURS_TO_MILLISECONDS * 8,
                System.currentTimeMillis() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L,
                System.currentTimeMillis() - DateUtil.HOURS_TO_MILLISECONDS * 6 - 10000L,
                System.currentTimeMillis() - DateUtil.HOURS_TO_MILLISECONDS * 6 + 10000L,
                System.currentTimeMillis(),
                null);
    }

    public void inDateRangeWithTimeUnitMicroseconds() {
        // ---------------------------------------------------------
        final InTimeRange function = new InTimeRange.Builder()
                .start("2017/01/01 01:30:10")
                .end("2017/01/01 01:30:50")
                .timeUnit(TimeUnit.MICROSECOND)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                getTimestamp("2017/01/01 01:30:09") * 1000,
                getTimestamp("2017/01/01 01:30:10") * 1000,
                getTimestamp("2017/01/01 01:30:20") * 1000,
                getTimestamp("2017/01/01 01:30:50") * 1000,
                getTimestamp("2017/01/01 01:30:51") * 1000,
                null);
    }

    private Long getTimestamp(final String dateStr) {
        return DateUtil.parseTime(dateStr);
    }
}
