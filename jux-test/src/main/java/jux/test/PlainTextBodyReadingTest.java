/**
 * Copyright © 2018 Sandor Nemeth (sandor.nemeth.1986 at gmail dot com)
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
package jux.test;

import jux.Context;
import jux.Request;
import jux.Response;
import jux.Router;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;

import java.net.URI;

import static jux.HttpMethod.*;

/**
 * Test plain text body reading.
 */
@JuxTest
public class PlainTextBodyReadingTest extends RequestEnclosingTestBase {
    @SuppressWarnings("RedundantThrows")
    @Override
    void validateResponse(HttpResponse response) throws Exception {
        JuxAssertions.assertThat(response)
                .isOk()
                .hasContentType("text/plain")
                .hasStringContent("hello");
    }

    @Override
    void configureRequest(HttpEntityEnclosingRequestBase req, int port)
            throws Exception {
        req.setURI(URI.create("http://localhost:" + port + "/foo"));
        req.setEntity(new StringEntity("hello"));
    }

    @Override
    @RouteProvider
    protected void configureRoutes(Router router) {
        router.handle("/foo", PlainTextBodyReadingTest::handlePlainBody)
                .methods(GET, POST, PUT, DELETE, PATCH);
    }

    private static Response handlePlainBody(@SuppressWarnings("unused") Context ctx, Request req) {
        String body = req.getBody(String.class);
        return Response.ok(body).asPlainText();
    }
}
