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
        super(Join.class, "This operation Joins two iterables together. There are three different types" +
                " of Join:\n" +
                "1) FULL - returns all objects in the key, along with any matched objects from the other side\n" +
                "2) INNER - returns all matching keys with objects on the other side\n" +
                "3) OUTER - returns all non matching keys\n" +
                "\n" +
                "A Join operation can key by the left (input) or right hand side (output of the operation specified)" +
                " and outputs an iterable of MapTuples. These Tuples contain the left and right outputs and can be iterated over" +
                " using the Map operation.\n\n" +
                "A join operation must be supplied with a match method. This tells the operation how to determine what is and what" +
                " isn't a match. There are two built in match methods:\n" +
                "1) ElementMatch - Matches elements of the same id(s), group and group by properties\n" +
                "2) KeyFunctionMatch - Matches any objects based on two key functions. The first key function applies to whatever the" +
                " join type is (the object on the left hand side for Left keyed join and vice versa for the right)\n\n" +
                "Once matched, the left and right sides are outputted as MapTuples keyed by \"LEFT\" and \"RIGHT\". The output is" +
                "flattened by default (one left value for each right value) but this can be turned off using the flatten flag." +
                " Setting the flatten flag to false will cause the non keyed side to be summarised in a list.");
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
