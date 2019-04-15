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
package uk.gov.gchq.gaffer.doc.binaryoperator;

import uk.gov.gchq.gaffer.commonutil.pair.Pair;
import uk.gov.gchq.koryphe.impl.binaryoperator.Product;

public class ProductExample extends BinaryOperatorExample {

    public ProductExample() {
        super(Product.class, "Calculates the product of 2 numbers");
    }

    public static void main(final String[] args) {
        new ProductExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        product();
    }

    private void product() {
        // ---------------------------------------------------------
        final Product product = new Product();
        // ---------------------------------------------------------

        runExample(product, null,
                new Pair<>(20L, 3L),
                new Pair<>(300, 400),
                new Pair<>(0.0, 3.0),
                new Pair<>(new Short("50"), new Short("50")),
                new Pair<>(new Short("500"), new Short("500")),
                new Pair<>(-5, 5),
                new Pair<>(20L, null)
        );

    }


}
