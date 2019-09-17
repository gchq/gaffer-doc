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

import uk.gov.gchq.koryphe.impl.function.Base64Decode;

public class Base64DecodeExample extends FunctionExample {
    public static void main(final String[] args) {
        new Base64DecodeExample().runAndPrint();
    }

    public Base64DecodeExample() {
        super(Base64Decode.class);
    }

    @Override
    protected void runExamples() {
        decodeBase64();
    }

    private void decodeBase64() {
        // ---------------------------------------------------------
        final Base64Decode function = new Base64Decode();
        // ---------------------------------------------------------
        byte[] input = "test string".getBytes();

        runExample(function, null, input);
    }

}
