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

import com.google.common.collect.Lists;

import uk.gov.gchq.gaffer.commonutil.CommonTimeUtil;
import uk.gov.gchq.gaffer.time.RBMBackedTimestampSet;
import uk.gov.gchq.gaffer.time.function.MaskTimestampSetByTimeRange;
import uk.gov.gchq.koryphe.util.TimeUnit;

import java.time.Instant;
import java.util.ArrayList;

public class MaskTimestampSetByTimeRangeExample extends FunctionExample {

    private RBMBackedTimestampSet timestampSet;

    public MaskTimestampSetByTimeRangeExample() {
        super(MaskTimestampSetByTimeRange.class, "Applies a mask to a timestamp set based on a start and end date");
    }

    public static void main(final String[] args) {
        new MaskTimestampSetByTimeRangeExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        setupTimestampSet();
        maskWithStartDate();
        maskWithEndDate();
        maskWithStartAndEndDate();
        maskWithNoStartOrEndDates();
        maskWithTimeUnit();
    }

    private void setupTimestampSet() {

        final ArrayList<Instant> instants = Lists.newArrayList(Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(10),
                Instant.ofEpochSecond(20),
                Instant.ofEpochSecond(30));


        timestampSet = new RBMBackedTimestampSet.Builder().timestamps(instants).timeBucket(CommonTimeUtil.TimeBucket.SECOND).build();
    }

    public void maskWithStartAndEndDate() {
        // ---------------------------------------------------------
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(10000L, 20000L);
        // ---------------------------------------------------------

        runExample(function, null, timestampSet);
    }

    public void maskWithEndDate() {
        // ---------------------------------------------------------
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(null, 20000L);
        // ---------------------------------------------------------

        runExample(function, null, timestampSet);
    }

    public void maskWithStartDate() {
        // ---------------------------------------------------------
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(10000L, null);
        // ---------------------------------------------------------

        runExample(function, null, timestampSet);
    }

    public void maskWithNoStartOrEndDates() {
        // ---------------------------------------------------------
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange();
        // ---------------------------------------------------------

        runExample(function, null, timestampSet);
    }

    public void maskWithTimeUnit() {
        // ---------------------------------------------------------
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(10L, 25L, TimeUnit.SECOND);
        // ---------------------------------------------------------

        runExample(function, null, timestampSet);
    }
}
