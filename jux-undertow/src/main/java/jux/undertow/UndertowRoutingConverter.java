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

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;
import jux.Router;
import jux.RouterConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Stream;

/**
 * Converts the routes registered in {@link jux.Router} into a {@link
 * RoutingHandler} used by the Undertow server.
 *
 * @author Sandor Nemeth
 */
class UndertowRoutingConverter implements RouterConverter<HttpHandler> {

    private static final Logger LOG =
            LogManager.getLogger(UndertowRoutingConverter.class);

    @Override
    public HttpHandler convert(Router router) {
        RoutingHandler handler = Handlers.routing();

        // get the middlewares

        router.routes()
                .flatMap(this::routes)
                .forEach(r -> handler
                        .add(r.method, r.path, new BlockingHandler(r.handler)));

        return router.isGracefulShutdown() ?
                new GracefulShutdownHandler(handler) : handler;
    }

    private Stream<UndertowRoute> routes(Router.Route route) {
        return route.getMethods().stream()
                .peek(method -> LOG.debug("Registering endpoint {} {}", method,
                        route.getPath()))
                .map(method -> new UndertowRoute(method.name(), route));
    }

    private static class UndertowRoute {
        private String method;
        private String path;
        private HttpHandler handler;

        UndertowRoute(String method, Router.Route route) {
            this.method = method;
            this.path = route.getPath();
            this.handler = new UndertowHttpExchangeHandler(route.getHandler());
        }
    }
}
