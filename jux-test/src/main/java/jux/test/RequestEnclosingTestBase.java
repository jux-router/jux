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

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

abstract class RequestEnclosingTestBase extends JuxTestBase {

    private static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of(new HttpPost()),
                Arguments.of(new HttpPut()),
                Arguments.of(new HttpPatch())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void executeTest(HttpEntityEnclosingRequestBase request,
                            @TestServerPort int port,
                            HttpClient client) throws Exception {
        execute(request, port, client);
    }

    @Override
    void configureRequest(HttpRequestBase request, int port) throws Exception {
        configureRequest((HttpEntityEnclosingRequestBase) request, port);
    }

    abstract void configureRequest(HttpEntityEnclosingRequestBase req,
                                   int port) throws Exception;
}
