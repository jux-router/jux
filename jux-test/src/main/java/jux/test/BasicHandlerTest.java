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

import jux.Exchange;
import jux.Response;
import jux.Router;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;

import static jux.HttpMethod.*;

@JuxTest
class BasicHandlerTest extends ResponseExpectingTestBase {

    @SuppressWarnings("RedundantThrows")
    @Override
    void validateResponse(HttpResponse response) throws Exception {
        JuxAssertions.assertThat(response)
                .isOk()
                .hasContentType("text/plain")
                .hasStringContent("hello");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    void configureRequest(HttpRequestBase request, int port) throws Exception {
        request.setURI(URI.create("http://localhost:" + port + "/foo"));
    }

    @Override
    @RouteProvider
    protected void configureRoutes(Router router) {
        router.handle("/foo", this::sayHello).methods(GET, POST, PUT, PATCH, DELETE);
    }

    private void sayHello(Exchange exchange) {
        exchange.response(Response.ok("hello").asPlainText());
    }
}
