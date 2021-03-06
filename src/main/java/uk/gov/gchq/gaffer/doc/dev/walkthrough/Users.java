/*
 * Copyright 2017-2020 Crown Copyright
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
package uk.gov.gchq.gaffer.doc.dev.walkthrough;

import uk.gov.gchq.gaffer.commonutil.CommonConstants;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.store.schema.Schema;

import java.io.UnsupportedEncodingException;

public class Users extends DevWalkthrough {
    public Users() {
        super("Users", "SchemaExample");
    }

    @Override
    public Schema run() throws OperationException {
        /// [user] execute
        // ---------------------------------------------------------
        final Graph graph = new Graph.Builder()
                .config(getDefaultGraphConfig())
                .addSchemas(StreamUtil.openStreams(getClass(), schemaPath))
                .storeProperties(getDefaultStoreProperties())
                .build();
        // ---------------------------------------------------------

        final Schema schema = graph.getSchema();
        try {
            print("SCHEMA", new String(schema.toJson(true), CommonConstants.UTF_8));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return schema;
    }

    public static void main(final String[] args) throws OperationException {
        final Users walkthrough = new Users();
        walkthrough.run();
    }
}
