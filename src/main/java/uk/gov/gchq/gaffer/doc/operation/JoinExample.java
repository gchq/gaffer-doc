/*
 * Copyright 2018 Crown Copyright
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

package uk.gov.gchq.gaffer.doc.operation;

import uk.gov.gchq.gaffer.operation.OperationException;

public class JoinExample extends OperationExample {

    public static void main(final String[] args) throws OperationException {
        new JoinExample().runAndPrint();
    }

    public JoinExample() {
        super(Join.class, "This Operation can be used to Join two iterables of Elements." +
                "A left side Iterable must be specified, along with a right side Operation that will produce the second iterable." +
                "A JoinType will be specified, which will describe the Join on the two iterables.  The possible types are FULL, FULL_INNER," +
                "FULL_OUTER, OUTER and INNER.  A FULL join will return all Elements from both sides, joining related rows where appropriate." +
                "A FULL_INNER will return only the related `middle` where both rows have a relationship.  A FULL_OUTER will return only Elements" +
                "where there is no relationship between the rows.  An OUTER will return just the side specified by the Key with no relations." +
                "An INNER will return only the related elements from the `middle` based on the key specified.  A MatchKey will also be " +
                "specified, which will describe the side in which the Key will be set, the options for this being LEFT or RIGHT.  The Matcher class" +
                "describes how the match on the two Elements will be done.  The Merge class will then describe how the Elements will be merged together" +
                "into a useful Iterable.  Within the Merge method it is possible to select the KEY_ONLY, RELATED_ONLY or BOTH results, and the " +
                "MergeType (NONE, AGAINST_KEY, BOTH).  The Element aggregation used will be the same as the ingest Aggregator specified in the Schema.");
    }

    @Override
    protected void runExamples() {

    }
}
