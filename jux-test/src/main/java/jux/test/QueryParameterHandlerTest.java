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

import com.google.common.io.CharStreams;
import jux.Handler;
import jux.HttpMethod;
import jux.Response;
import jux.Router;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.InputStreamReader;
import java.net.URI;

import static jux.HttpMethod.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JuxTest
public class QueryParameterHandlerTest extends JuxTestBase {

    @Override
    void validateResponse(HttpResponse response) throws Exception {
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("text/plain",
                response.getFirstHeader("Content-Type").getValue());
        String content = CharStreams.toString(
                new InputStreamReader(response.getEntity().getContent()));
        assertEquals("test", content);
    }

    @Override
    void configureRequest(HttpRequestBase request, int port) throws Exception {
        String uri = "http://localhost:" + port + "/foo?aparam=test";
        request.setURI(URI.create(uri));
    }

    @Override @RouteProvider
    protected void configureRoutes(Router router) {
        Handler h = (ctx, req) -> Response.ok(req.getParam("aparam").orElse("")).asPlainText();
        router.handle("/foo", h).methods(GET, POST, PUT, PATCH, DELETE);
    }
}
