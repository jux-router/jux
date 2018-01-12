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
package jux.undertow;

import com.google.common.io.CharStreams;
import jux.*;
import jux.test.JuxExtension;
import jux.test.RouteProvider;
import jux.test.TestServerPort;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the Undertow server.
 */
@ExtendWith(JuxExtension.class)
class UndertowServerTest {

    private CloseableHttpClient client = HttpClientBuilder.create().build();

    @AfterEach
    void cleanup() throws Exception {
        client.close();
    }

    @Test
    void test(@TestServerPort Integer port) throws Exception {
        HttpGet get = new HttpGet("http://localhost:" + port.intValue() + "/foo");

        CloseableHttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        String result = CharStreams.toString(new InputStreamReader(response.getEntity().getContent()));
        assertThat(result).isEqualTo("hello");
        assertThat(response.getFirstHeader("Content-Type").getValue()).isEqualTo("text/plain");
    }

    @RouteProvider
    void routes(Router router) {
        router.handle("/foo", (ctx, req) -> Response.ok("hello").as("text/plain")).methods(HttpMethod.GET);
    }

}
