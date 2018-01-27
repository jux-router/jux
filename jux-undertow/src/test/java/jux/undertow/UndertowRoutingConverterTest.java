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

import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;
import jux.Jux;
import jux.Router;
import org.junit.jupiter.api.Test;

import static jux.test.JuxAssertions.assertThat;

class UndertowRoutingConverterTest {

    private UndertowRoutingConverter routingConverter = new UndertowRoutingConverter();

    @Test
    void testUsesGracefulShutdownIfConfigured() {
        Router router = Jux.router();
        router.useGracefulShutdown();

        HttpHandler handler = routingConverter.convert(router);

        assertThat(handler).isInstanceOf(GracefulShutdownHandler.class);
    }

    @Test
    void testDoesntUseGracefulShutdownByDefault() {
        HttpHandler handler = routingConverter.convert(Jux.router());
        assertThat(handler).isInstanceOf(RoutingHandler.class);
    }
}