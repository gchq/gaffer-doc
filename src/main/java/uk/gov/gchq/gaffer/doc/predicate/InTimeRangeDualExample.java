/*
 * Copyright 2016 Crown Copyright
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


import uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual;
import uk.gov.gchq.koryphe.tuple.n.Tuple2;
import uk.gov.gchq.koryphe.util.DateUtil;
import uk.gov.gchq.koryphe.util.TimeUnit;

public class InTimeRangeDualExample extends PredicateExample {
    public static void main(final String[] args) {
        new InTimeRangeDualExample().runAndPrint();
    }

    public InTimeRangeDualExample() {
        super(InTimeRangeDual.class,
                "The predicate tests 2 timestamp (long) inputs (a start timestamp and an end timestamp) are within a defined range. " +
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
        final InTimeRangeDual function = new InTimeRangeDual.Builder()
                .start("2017/01/01")
                .end("2017/02/01")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(getTimestamp("2016/01/01 00:00"), getTimestamp("2016/02/01 00:00")),
                new Tuple2<>(getTimestamp("2017/01/01 00:00"), getTimestamp("2017/01/01 00:00")),
                new Tuple2<>(getTimestamp("2017/01/01 01:00"), getTimestamp("2017/01/01 01:00")),
                new Tuple2<>(getTimestamp("2017/01/01 01:00"), getTimestamp("2017/01/01 23:59:59")),
                new Tuple2<>(getTimestamp("2017/01/01 01:00"), getTimestamp("2017/02/01 00:00:00")),
                new Tuple2<>(getTimestamp("2017/01/01 01:00"), getTimestamp("2017/02/01 00:00:01")),
                new Tuple2<>(null, null));
    }

    public void inTimeRangeWithSecondPrecision() {
        // ---------------------------------------------------------
        final InTimeRangeDual function = new InTimeRangeDual.Builder()
                .start("2017/01/01 01:30:10")
                .end("2017/01/01 01:30:50")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(getTimestamp("2017/01/01 01:30:08"), getTimestamp("2017/01/01 01:30:09")),
                new Tuple2<>(getTimestamp("2017/01/01 01:30:10"), getTimestamp("2017/01/01 01:30:10")),
                new Tuple2<>(getTimestamp("2017/01/01 01:30:10"), getTimestamp("2017/01/01 01:30:20")),
                new Tuple2<>(getTimestamp("2017/01/01 01:30:10"), getTimestamp("2017/01/01 01:30:50")),
                new Tuple2<>(getTimestamp("2017/01/01 01:30:10"), getTimestamp("2017/01/01 01:30:51")),
                new Tuple2<>(null, null));
    }


    public void inTimeRangeWithTimestamps() {
        // ---------------------------------------------------------
        final InTimeRangeDual function = new InTimeRangeDual.Builder()
                .start("1483315200")
                .end("1485907200")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(1483315198L, 1483315199L),
                new Tuple2<>(1483315198L, 1483315200L),
                new Tuple2<>(1483315200L, 1483315201L),
                new Tuple2<>(1483316200L, 1485907200L),
                new Tuple2<>(1483316200L, 1485907201L),
                new Tuple2<>(null, null));
    }

    public void inTimeRangeExclusive() {
        // ---------------------------------------------------------
        final InTimeRangeDual function = new InTimeRangeDual.Builder()
                .start("2017/01/01")
                .end("2017/02/01")
                .startInclusive(false)
                .endInclusive(false)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(getTimestamp("2016/01/01 00:00"), getTimestamp("2016/02/01 00:00")),
                new Tuple2<>(getTimestamp("2017/01/01 00:00"), getTimestamp("2017/01/01 00:00")),
                new Tuple2<>(getTimestamp("2017/01/01 01:00"), getTimestamp("2017/01/01 01:00")),
                new Tuple2<>(getTimestamp("2017/01/01 01:00"), getTimestamp("2017/01/01 23:59:59")),
                new Tuple2<>(getTimestamp("2017/01/01 01:00"), getTimestamp("2017/02/01 00:00:00")),
                new Tuple2<>(getTimestamp("2017/01/01 01:00"), getTimestamp("2017/02/01 00:00:01")),
                new Tuple2<>(null, null));
    }

    public void withinTheLastWeek() {
        // ---------------------------------------------------------
        final InTimeRangeDual function = new InTimeRangeDual.Builder()
                .startOffset(-7L)
                        // end is not set - it is unbounded
                .offsetUnit(TimeUnit.DAY)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                "If the end of the range is not specified then the end of the range is unbounded.",
                new Tuple2<>(now() - DateUtil.DAYS_TO_MILLISECONDS * 9, now() - DateUtil.DAYS_TO_MILLISECONDS * 8),
                new Tuple2<>(now() - DateUtil.DAYS_TO_MILLISECONDS * 6, now() - DateUtil.DAYS_TO_MILLISECONDS * 6 + 1),
                new Tuple2<>(now() - DateUtil.DAYS_TO_MILLISECONDS * 6, now() - DateUtil.DAYS_TO_MILLISECONDS),
                new Tuple2<>(now() - 10, now()),
                new Tuple2<>(null, null));
    }

    public void exactly7HoursAgo() {
        // ---------------------------------------------------------
        final InTimeRangeDual function = new InTimeRangeDual.Builder()
                .startOffset(-7L)
                .endOffset(-6L)
                .endInclusive(false)
                .offsetUnit(TimeUnit.HOUR)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(now() - DateUtil.HOURS_TO_MILLISECONDS * 8, now() - DateUtil.HOURS_TO_MILLISECONDS * 7 - 10),
                new Tuple2<>(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L, now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 20000L),
                new Tuple2<>(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L, now() - DateUtil.HOURS_TO_MILLISECONDS * 6 - 10000L),
                new Tuple2<>(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L, now() - DateUtil.HOURS_TO_MILLISECONDS * 6 + 10000L),
                new Tuple2<>(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L, now()),
                new Tuple2<>(null, null));
    }

    public void inDateRangeWithTimeUnitMicroseconds() {
        // ---------------------------------------------------------
        final InTimeRangeDual function = new InTimeRangeDual.Builder()
                .start("2017/01/01")
                .end("2017/02/01")
                .timeUnit(TimeUnit.MICROSECOND)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(getTimestamp("2016/01/01 00:00") * 1000, getTimestamp("2016/02/01 00:00") * 1000),
                new Tuple2<>(getTimestamp("2017/01/01 00:00") * 1000, getTimestamp("2017/01/01 00:00") * 1000),
                new Tuple2<>(getTimestamp("2017/01/01 01:00") * 1000, getTimestamp("2017/01/01 01:00") * 1000),
                new Tuple2<>(getTimestamp("2017/01/01 01:00") * 1000, getTimestamp("2017/01/01 23:59:59") * 1000),
                new Tuple2<>(getTimestamp("2017/01/01 01:00") * 1000, getTimestamp("2017/02/01 00:00:00") * 1000),
                new Tuple2<>(getTimestamp("2017/01/01 01:00") * 1000, getTimestamp("2017/02/01 00:00:01") * 1000),
                new Tuple2<>(null, null));
    }

    private Long getTimestamp(final String dateStr) {
        return DateUtil.parseTime(dateStr);
    }

    private long now() {
        return System.currentTimeMillis();
    }
}
