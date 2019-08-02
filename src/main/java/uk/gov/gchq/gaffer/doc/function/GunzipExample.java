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

import uk.gov.gchq.koryphe.impl.function.Gunzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GunzipExample extends FunctionExample {
    public static void main(final String[] args) {
        new GunzipExample().runAndPrint();
    }

    public GunzipExample() {
        super(uk.gov.gchq.koryphe.impl.function.Gunzip.class);
    }

    @Override
    protected void runExamples() {
        gunzip();
    }

    private void gunzip() {
        // ---------------------------------------------------------
        final Gunzip gunzip = new Gunzip();
        // ---------------------------------------------------------

        final byte[] input = "test string".getBytes();
        byte[] gzip = null;
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final GZIPOutputStream gzipOut = new GZIPOutputStream(out)) {
            gzipOut.write(input);
            gzipOut.flush();
            gzipOut.close();
            gzip = out.toByteArray();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        runExample(gunzip, "byte array of string", gzip);
    }


}
