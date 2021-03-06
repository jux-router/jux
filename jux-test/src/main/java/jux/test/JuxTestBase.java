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

import jux.Router;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Base test class for JUX-tests.
 */
@ExtendWith({JuxExtension.class, HttpClientParameterResolver.class})
public abstract class JuxTestBase {

    void execute(HttpRequestBase req, int port, HttpClient client)
            throws Exception {
        configureRequest(req, port);
        validateResponse(client.execute(req));
    }

    abstract void validateResponse(HttpResponse response) throws Exception;

    abstract void configureRequest(HttpRequestBase request, int port)
            throws Exception;

    @SuppressWarnings("unused")
    protected abstract void configureRoutes(Router router);

}
