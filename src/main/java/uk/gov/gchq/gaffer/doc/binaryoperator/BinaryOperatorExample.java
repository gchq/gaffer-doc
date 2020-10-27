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
package uk.gov.gchq.gaffer.doc.binaryoperator;

import org.apache.commons.lang.StringUtils;

import uk.gov.gchq.gaffer.commonutil.pair.Pair;
import uk.gov.gchq.gaffer.doc.util.Example;
import uk.gov.gchq.koryphe.signature.Signature;

import java.util.function.BinaryOperator;

public abstract class BinaryOperatorExample extends Example {
    public BinaryOperatorExample(final Class<? extends BinaryOperator> classForExample) {
        super(classForExample);
    }

    public BinaryOperatorExample(final Class<? extends BinaryOperator> classForExample, final String description) {
        super(classForExample, description);

        // TODO: This should be removed when the binary operators have been added to the python shell
        skipPython();
    }

    @SafeVarargs
    public final void runExample(final BinaryOperator function, final String description, final Pair<Object, Object>... inputs) {
        print("### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            print(description + "\n");
        }

        printJavaJsonPython(function, 3);

        print("Input type:");
        print("\n```");
        final StringBuilder inputClasses = new StringBuilder();
        for (final Class<?> item : Signature.getInputSignature(function).getClasses()) {
            inputClasses.append(item.getName());
            inputClasses.append(", ");
        }
        print(inputClasses.substring(0, inputClasses.length() - 2));
        print("```\n");

        print("Example inputs:");

        print("<table style=\"display: block;\">");
        print("<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>");
        for (final Pair<Object, Object> inputPair : inputs) {
            final Pair<String, String> inputTypeValue1 = getTypeValue(inputPair.getFirst());
            final Pair<String, String> inputTypeValue2 = getTypeValue(inputPair.getSecond());

            Pair<String, String> resultTypeValue;
            try {
                resultTypeValue = getTypeValue(function.apply(inputPair.getFirst(), inputPair.getSecond()));
            } catch (final Exception e) {
                resultTypeValue = new Pair<>("", e.getClass().getSimpleName() + ": " + e.getMessage());
            }

            print("<tr><td>" + inputTypeValue1.getFirst() + "</td><td>" + inputTypeValue1.getSecond() + " and " + inputTypeValue2.getSecond() + "</td><td>" + resultTypeValue.getFirst() + "</td><td>" + resultTypeValue.getSecond() + "</td></tr>");
        }
        print("</table>\n");
        print(METHOD_DIVIDER);
    }
}
