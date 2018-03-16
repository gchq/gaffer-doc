/*
 * Copyright 2016-2018 Crown Copyright
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


import uk.gov.gchq.koryphe.impl.predicate.range.InDateRangeDual;
import uk.gov.gchq.koryphe.tuple.n.Tuple2;
import uk.gov.gchq.koryphe.util.DateUtil;
import uk.gov.gchq.koryphe.util.TimeUnit;

import java.util.Date;

public class InDateRangeDualExample extends PredicateExample {
    public static void main(final String[] args) {
        new InDateRangeDualExample().runAndPrint();
    }

    public InDateRangeDualExample() {
        super(InDateRangeDual.class,
                "The predicate tests 2 date inputs (a start date and an end date) are within a defined range. " +
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
        final InDateRangeDual function = new InDateRangeDual.Builder()
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

    public void inDateRangeWithSecondPrecision() {
        // ---------------------------------------------------------
        final InDateRangeDual function = new InDateRangeDual.Builder()
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


    public void inDateRangeWithTimestamps() {
        // ---------------------------------------------------------
        final InDateRangeDual function = new InDateRangeDual.Builder()
                .start("1483315200")
                .end("1485907200")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(new Date(1483315198L), new Date(1483315199L)),
                new Tuple2<>(new Date(1483315198L), new Date(1483315200L)),
                new Tuple2<>(new Date(1483315200L), new Date(1483315201L)),
                new Tuple2<>(new Date(1483316200L), new Date(1485907200L)),
                new Tuple2<>(new Date(1483316200L), new Date(1485907201L)),
                new Tuple2<>(null, null));
    }

    public void inDateRangeExclusive() {
        // ---------------------------------------------------------
        final InDateRangeDual function = new InDateRangeDual.Builder()
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
        final InDateRangeDual function = new InDateRangeDual.Builder()
                .startOffset(-7L)
                        // end is not set - it is unbounded
                .offsetUnit(TimeUnit.DAY)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                "If the end of the range is not specified then the end of the range is unbounded.",
                new Tuple2<>(new Date(now() - DateUtil.DAYS_TO_MILLISECONDS * 9), new Date(now() - DateUtil.DAYS_TO_MILLISECONDS * 8)),
                new Tuple2<>(new Date(now() - DateUtil.DAYS_TO_MILLISECONDS * 6), new Date(now() - DateUtil.DAYS_TO_MILLISECONDS * 6 + 1)),
                new Tuple2<>(new Date(now() - DateUtil.DAYS_TO_MILLISECONDS * 6), new Date(now() - DateUtil.DAYS_TO_MILLISECONDS)),
                new Tuple2<>(new Date(now() - 10), new Date()),
                new Tuple2<>(null, null));
    }

    public void exactly7HoursAgo() {
        // ---------------------------------------------------------
        final InDateRangeDual function = new InDateRangeDual.Builder()
                .startOffset(-7L)
                .endOffset(-6L)
                .endInclusive(false)
                .offsetUnit(TimeUnit.HOUR)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(new Date(now() - DateUtil.HOURS_TO_MILLISECONDS * 8), new Date(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 - 10)),
                new Tuple2<>(new Date(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L), new Date(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 20000L)),
                new Tuple2<>(new Date(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L), new Date(now() - DateUtil.HOURS_TO_MILLISECONDS * 6 - 10000L)),
                new Tuple2<>(new Date(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L), new Date(now() - DateUtil.HOURS_TO_MILLISECONDS * 6 + 10000L)),
                new Tuple2<>(new Date(now() - DateUtil.HOURS_TO_MILLISECONDS * 7 + 10000L), new Date(now())),
                new Tuple2<>(null, null));
    }

    private Date getTimestamp(final String dateStr) {
        return DateUtil.parse(dateStr);
    }

    private long now() {
        return System.currentTimeMillis();
    }
}
