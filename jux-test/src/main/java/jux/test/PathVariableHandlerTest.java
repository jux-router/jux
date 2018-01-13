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
import jux.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.Optional;

import static jux.HttpMethod.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JuxTest
class PathVariableHandlerTest extends JuxTestBase {

    @Override
    void configureRequest(HttpRequestBase request, int port) {
        request.setURI(URI.create("http://localhost:" + port + "/foo/hello/1"));
    }

    @Override
    void validateResponse(HttpResponse response) throws Exception {
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("text/plain",
                response.getFirstHeader("Content-Type").getValue());
        String content = CharStreams.toString(
                new InputStreamReader(response.getEntity().getContent()));
        assertEquals("hello", content);
    }

    @Override
    @RouteProvider
    protected void configureRoutes(Router router) {
        router.handle("/foo/{param}/1", new ParamHandler())
                .methods(GET, POST, PUT, DELETE, PATCH);
    }

    static class ParamHandler implements Handler {

        private static final Logger LOG =
                LogManager.getLogger(ParamHandler.class);

        @Override
        public Response handle(Context ctx, Request req) {
            String param = req.getParam("param").orElse("");
            LOG.info("Param is: {}", param);
            return Response.ok(param).as("text/plain");
        }
    }
}
