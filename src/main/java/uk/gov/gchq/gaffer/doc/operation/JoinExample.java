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

import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements;
import uk.gov.gchq.gaffer.operation.impl.join.Join;
import uk.gov.gchq.gaffer.operation.impl.join.match.MatchKey;
import uk.gov.gchq.gaffer.operation.impl.join.methods.JoinType;
import uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch;
import uk.gov.gchq.gaffer.store.operation.handler.join.merge.ElementMerge;
import uk.gov.gchq.gaffer.store.operation.handler.join.merge.MergeType;
import uk.gov.gchq.gaffer.store.operation.handler.join.merge.ResultsWanted;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinExample extends OperationExample {

    public static void main(final String[] args) throws OperationException {
        new JoinExample().runAndPrint();
    }

    public JoinExample() {
        super(Join.class, "This Operation can be used to Join two iterables of Elements." +
                "A left side Iterable must be specified, along with a right side Operation that will produce the second iterable." +
                "A JoinType will be specified, which will describe the Join on the two iterables.  The possible types are FULL, FULL_INNER," +
                "FULL_OUTER, OUTER and INNER.  A FULL join will return all Elements from both sides, joining related rows where appropriate." +
                "A FULL_INNER will return only the related `middle` where " +
                "both rows have a relationship based on the ElementMatch.  A " +
                "FULL_OUTER will return only Elements" +
                "where there is no relationship between the rows.  An OUTER will return just the side specified by the Key with no relations." +
                "An INNER will return only the related elements from the `middle` based on the key specified.  A MatchKey will also be " +
                "specified, which will describe the side in which the Key will be set, the options for this being LEFT or RIGHT.  The Matcher class" +
                "describes how the match on the two Elements will be done.  The Merge class will then describe how the Elements will be merged together" +
                "into a useful Iterable.  Within the Merge method it is possible to select the KEY_ONLY, RELATED_ONLY or BOTH results, and the " +
                "MergeType (NONE, AGAINST_KEY, BOTH).  The Element aggregation used will be the same as the ingest Aggregator specified in the Schema.");
    }

    @Override
    protected void runExamples() {
        leftKeyFullInnerJoin();
        rightKeyFullInnerJoin();
        leftKeyFullJoin();
        rightKeyFullJoin();
        fullOuterJoin();
        leftKeyOuterJoin();
        rightKeyOuterJoin();
        leftKeyInnerJoin();
        rightKeyInnerJoin();
    }

    public Iterable<? extends Element> leftKeyFullInnerJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<Element, Element>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL_INNER)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new ElementMatch("count"))
                        .mergeMethod(new ElementMerge(ResultsWanted.KEY_ONLY, MergeType.NONE))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends Element> rightKeyFullInnerJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<Element, Element>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL_INNER)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new ElementMatch("count"))
                        .mergeMethod(new ElementMerge(ResultsWanted.KEY_ONLY, MergeType.NONE))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends Element> leftKeyFullJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<Element, Element>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new ElementMatch("count"))
                        .mergeMethod(new ElementMerge(ResultsWanted.KEY_ONLY, MergeType.NONE))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends Element> rightKeyFullJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<Element, Element>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new ElementMatch("count"))
                        .mergeMethod(new ElementMerge(ResultsWanted.KEY_ONLY, MergeType.NONE))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends Element> fullOuterJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<Element, Element>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL_OUTER)
                        .matchMethod(new ElementMatch("count"))
                        .mergeMethod(new ElementMerge(ResultsWanted.KEY_ONLY, MergeType.NONE))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends Element> leftKeyOuterJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<Element, Element>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new ElementMatch("count"))
                        .mergeMethod(new ElementMerge(ResultsWanted.KEY_ONLY, MergeType.NONE))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends Element> rightKeyOuterJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<Element, Element>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new ElementMatch("count"))
                        .mergeMethod(new ElementMerge(ResultsWanted.KEY_ONLY, MergeType.NONE))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends Element> leftKeyInnerJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<Element, Element>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new ElementMatch("count"))
                        .mergeMethod(new ElementMerge(ResultsWanted.KEY_ONLY, MergeType.NONE))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends Element> rightKeyInnerJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<Element, Element>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new ElementMatch("count"))
                        .mergeMethod(new ElementMerge(ResultsWanted.KEY_ONLY, MergeType.NONE))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }


    private Entity getJoinEntity(final String group, final int vertex, final int propVal) {
        return new Entity.Builder().group(group).vertex(vertex).property("count", propVal).build();
    }
}
