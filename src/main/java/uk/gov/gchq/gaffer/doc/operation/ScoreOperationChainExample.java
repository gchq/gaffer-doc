/*
 * Copyright 2017 Crown Copyright
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
import uk.gov.gchq.gaffer.named.operation.AddNamedOperation;
import uk.gov.gchq.gaffer.named.operation.DeleteNamedOperation;
import uk.gov.gchq.gaffer.named.operation.NamedOperation;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.Limit;
import uk.gov.gchq.gaffer.operation.impl.ScoreOperationChain;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;

public class ScoreOperationChainExample extends OperationExample {
    public static void main(final String[] args) {
        new ScoreOperationChainExample().run();
    }

    public ScoreOperationChainExample() {
        super(ScoreOperationChain.class, "Note - ScoreOperationChain is not a core operation.");
    }

    @Override
    protected void runExamples() {
        final AddNamedOperation addNamedOp = new AddNamedOperation.Builder()
                .operationChain(new OperationChain.Builder()
                        .first(new GetElements())
                        .build())
                .name("1-hop")
                .description("a 1-hop operation")
                .overwrite()
                .score(3)
                .build();

        try {
            getGraph().execute(addNamedOp, createContext());
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }

        scoreOperationChain();
        scoreOperationChainWithCustomNamedScore();

        try {
            getGraph().execute(new DeleteNamedOperation.Builder()
                    .name("1-hop")
                    .build(), createContext());
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }
    }

    public void scoreOperationChain() {
        // ---------------------------------------------------------
        final ScoreOperationChain scoreOpChain = new ScoreOperationChain.Builder()
                .operationChain(new OperationChain.Builder()
                        .first(new GetElements())
                        .then(new NamedOperation.Builder<Element, Iterable<? extends Element>>()
                                .name("namedOp")
                                .build())
                        .then(new Limit<>(3))
                        .build())
                .build();
        // ---------------------------------------------------------
        runExample(scoreOpChain, "This demonstrates a simple example for constructing a " +
                "ScoreOperationChain, with Operations and a NamedOperation.");
    }

    public void scoreOperationChainWithCustomNamedScore() {
        // ---------------------------------------------------------
        final ScoreOperationChain scoreOperationChain = new ScoreOperationChain.Builder()
                .operationChain(new OperationChain.Builder()
                        .first(new NamedOperation.Builder<EntitySeed, Iterable<? extends Element>>()
                                .name("1-hop")
                                .input(new EntitySeed(1))
                                .build())
                        .then(new Limit<>(3))
                        .build())
                .build();
        // ---------------------------------------------------------
        runExample(scoreOperationChain, "Here we have added a NamedOperation to the NamedOperationCache, " +
                "with a custom score of 3. In our ScoreOperationChainDeclaration.json file, we have also " +
                "declared that this should be resolved with a NamedOperationScoreResolver. " +
                "With Limit declared as having a score of 2, the above chain correctly has a score of 5.");
    }
}
