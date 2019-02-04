/*
 * Copyright 2016-2019 Crown Copyright
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

import org.apache.commons.lang.StringUtils;

import uk.gov.gchq.gaffer.commonutil.pair.Pair;
import uk.gov.gchq.gaffer.doc.util.Example;
import uk.gov.gchq.koryphe.signature.Signature;

import java.util.function.Predicate;


public abstract class PredicateExample extends Example {
    public PredicateExample(final Class<? extends Predicate> classForExample) {
        super(classForExample);
    }

    public PredicateExample(final Class<? extends Predicate> classForExample, final String description) {
        super(classForExample, description);
    }

    public void runExample(final Predicate predicate, final String description, final Object... inputs) {
        print("### " + getMethodNameAsSentence(1) + "\n");
        if (StringUtils.isNotBlank(description)) {
            print(description + "\n");
        }

        printJavaJsonPython(predicate, 3);

        print("Input type:");
        print("\n```");
        final StringBuilder inputClasses = new StringBuilder();
        for (final Class<?> item : Signature.getInputSignature(predicate).getClasses()) {
            inputClasses.append(item.getName());
            inputClasses.append(", ");
        }
        print(inputClasses.substring(0, inputClasses.length() - 2));
        print("```\n");

        print("Example inputs:");
        print("<table style=\"display: block;\">");
        print("<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>");
        for (final Object input : inputs) {
            final Pair<String, String> inputTypeValue = getTypeValue(input);

            Pair<String, String> resultTypeValue;
            try {
                resultTypeValue = getTypeValue(predicate.test(input));
            } catch (final Exception e) {
                resultTypeValue = new Pair<>("", e.getClass().getSimpleName() + ": " + e.getMessage());
            }

            print("<tr><td>" + inputTypeValue.getFirst() + "</td><td>" + inputTypeValue.getSecond() + "</td><td>" + resultTypeValue.getSecond() + "</td></tr>");
        }
        print("</table>\n");
        print(METHOD_DIVIDER);
    }
}
