/**
 * Copyright © 2018 Sandor Nemeth (sandor.nemeth.1986 at gmail dot com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jux.test;

import com.google.common.io.CharStreams;
import jux.Response;
import jux.Router;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.InputStreamReader;
import java.net.URI;

import static jux.HttpMethod.*;
import static org.assertj.core.api.Assertions.assertThat;

@JuxTest
class BasicHandlerTest extends JuxTestBase {

    @Override
    void validateResponse(HttpResponse response) throws Exception {
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        String result = CharStreams.toString(
                new InputStreamReader(response.getEntity().getContent()));
        assertThat(result).isEqualTo("hello");
        assertThat(response.getFirstHeader("Content-Type").getValue())
                .isEqualTo("text/plain");
    }

    @Override
    void configureRequest(HttpRequestBase request, int port) throws Exception {
        request.setURI(URI.create("http://localhost:" + port + "/foo"));
    }

    @Override
    @RouteProvider
    protected void configureRoutes(Router router) {
        router.handle("/foo",
                (ctx, req) -> Response.ok("hello").as("text/plain"))
                .methods(GET, POST, PUT, PATCH, DELETE);
    }
}
