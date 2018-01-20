/**
 * Copyright © 2018 Sandor Nemeth (sandor.nemeth.1986 at gmail dot com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package jux.undertow;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;
import jux.Middleware;
import jux.Router;
import jux.RouterConverter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Converts the routes registered in {@link jux.Router} into a
 * {@link RoutingHandler} used by the Undertow server.
 *
 * @author Sandor Nemeth
 */
class UndertowRoutingConverter implements RouterConverter<RoutingHandler> {

    @Override
    public RoutingHandler convert(Router router) {
        RoutingHandler handler = Handlers.routing();

        // get the middlewares
        List<Middleware> middlewares = router.middlewares().collect(toList());

        router.routes()
                .flatMap(route -> this.routes(route, middlewares))
                .forEach(r -> handler.add(r.method, r.path, new BlockingHandler(r.handler)));

        return handler;
    }

    private Stream<UndertowRoute> routes(Router.Route route, List<Middleware> middlewares) {
        return route.getMethods().stream().map(method -> new UndertowRoute(method.name(), route.getPath(), route.getHandler()));
    }

    private static class UndertowRoute {
        private String method;
        private String path;
        private HttpHandler handler;

        UndertowRoute(String method, String path, jux.Handler handler) {
            this.method = method;
            this.path = path;
            this.handler = new UndertowHttpExchangeHandler(handler);
        }
    }
}
