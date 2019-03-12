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
import uk.gov.gchq.koryphe.tuple.MapTuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinExample extends OperationExample {

    public static void main(final String[] args) {
        new JoinExample().runAndPrint();
    }

    public JoinExample() {
        super(Join.class, "This operation joins two iterables together. There are three different types" +
                " of Join:\n\n" +
                "* FULL - returns all objects in the key, along with any matched objects from the other side\n" +
                "* INNER - returns all keys which matched with objects on the other side\n" +
                "* OUTER - returns all keys which didn't match with objects from the other side\n" +
                "\n" +
                "A Join operation can key by the left (input) or right hand side (output of the operation specified)" +
                " and outputs an iterable of MapTuples. These Tuples contain the left and right outputs.\n" +
                "\n" +
                "A join operation must be supplied with a match method. This tells the operation how to determine what is and what" +
                " isn't a match. There are two built in match methods:\n" +
                "\n" +
                "* ElementMatch - Matches elements of the same id(s), group and group by properties\n" +
                "* KeyFunctionMatch - Matches any objects based on two key functions. The first key function applies to whatever the" +
                " join type is (the object on the left hand side for Left keyed join and vice versa for the right).\n" +
                "\n" +
                "Once matched, the left and right sides are outputted as MapTuples keyed by \"LEFT\" and \"RIGHT\". The output is" +
                " flattened by default (one left value for each right value) but this can be turned off using the flatten flag." +
                " Setting the flatten flag to false will cause the non keyed side to be summarised in a list.");
    }

    @Override
    protected void runExamples() {
        leftKeyInnerJoin();
        flattenedLeftKeyInnerJoin();
        rightKeyInnerJoin();
        flattenedRightKeyInnerJoin();
        leftKeyFullJoin();
        flattenedLeftKeyFullJoin();
        rightKeyFullJoin();
        flattenedRightKeyFullJoin();
        leftKeyOuterJoin();
        flattenedLeftKeyOuterJoin();
        rightKeyOuterJoin();
        flattenedRightKeyOuterJoin();
    }

    public Iterable<? extends MapTuple> leftKeyInnerJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.LEFT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> flattenedLeftKeyInnerJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> rightKeyInnerJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.RIGHT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> flattenedRightKeyInnerJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .flatten(true)
                        .matchKey(MatchKey.RIGHT)
                        .joinType(JoinType.INNER)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> leftKeyFullJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.LEFT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> flattenedLeftKeyFullJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }


    public Iterable<? extends MapTuple> rightKeyFullJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.RIGHT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> flattenedRightKeyFullJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> leftKeyOuterJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.LEFT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> flattenedLeftKeyOuterJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> rightKeyOuterJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.RIGHT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }

    public Iterable<? extends MapTuple> flattenedRightKeyOuterJoin() {
        List<Element> inputElements = new ArrayList<>(Arrays.asList(getJoinEntity("entity", 1, 3), getJoinEntity("entity", 4, 1), getJoinEntity("entity", 5, 3)));
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, null);
    }




    private Entity getJoinEntity(final String group, final int vertex, final int propVal) {
        return new Entity.Builder().group(group).vertex(vertex).property("count", propVal).build();
    }
}
