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
package jux;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static jux.HttpMethod.*;
import static org.assertj.core.api.Assertions.assertThat;

class RouterTest {

    private Handler h1 = exchange -> {
    };
    private Handler h2 = exchange -> {
    };
    private final Router router = new Router();

    @Test
    void testBuildsBasicRoute() {
        router.handle("/foo", h1).methods(GET);

        List<Router.Route> routes = router.routes().collect(toList());
        assertThat(routes).hasSize(1);
        Router.Route route = routes.get(0);

        assertThat(route.getHandler()).isEqualTo(h1);
        assertThat(route.getPath()).isEqualTo("/foo");
        assertThat(route.getMethods()).containsExactlyInAnyOrder(GET);
    }

    @Test
    void testRegisterSameHandlerForMultipleMethods() {
        Router router = new Router();
        router.handle("/foo", h1).methods(GET, POST);
        List<Router.Route> routes = router.routes().collect(toList());
        assertThat(routes).hasSize(1);
        Router.Route route = routes.get(0);

        assertThat(route.getHandler()).isEqualTo(h1);
        assertThat(route.getPath()).isEqualTo("/foo");
        assertThat(route.getMethods()).containsExactlyInAnyOrder(GET, POST);
    }

    @Test
    void testRegisterSamePathToDifferentHandlersOnDifferentMethods() {
        router.handle("/foo", h1).methods(GET, POST).and()
                .handle("/foo", h2).methods(PUT, DELETE);
        List<Router.Route> routes = router.routes().collect(toList());
        assertThat(routes).hasSize(2);

        Router.Route route = routes.get(0);
        assertThat(route.getHandler()).isEqualTo(h1);
        assertThat(route.getPath()).isEqualTo("/foo");
        assertThat(route.getMethods()).containsExactlyInAnyOrder(GET, POST);

        route = routes.get(1);
        assertThat(route.getHandler()).isEqualTo(h2);
        assertThat(route.getPath()).isEqualTo("/foo");
        assertThat(route.getMethods()).containsExactlyInAnyOrder(PUT, DELETE);
    }

    @Test
    void testRegisterGeneralMiddlewares() {
        Middleware noopMiddleware = this::noopMiddleware;
        router.use(noopMiddleware);
        List<Middleware> middlewares = router.middlewares().collect(toList());

        assertThat(middlewares)
                .hasSize(1)
                .containsExactlyInAnyOrder(noopMiddleware);
    }

    @Test
    void testRegisterRouteSpecificMiddleware() {
        Middleware noopMiddleware = this::noopMiddleware;
        Router.Route route = router.handle("/foo", h1).methods(POST).using(noopMiddleware);

        assertThat(route.getMiddlewares())
                .hasSize(1)
                .containsExactlyInAnyOrder(noopMiddleware);
    }

    @Test
    void testNoRouteSpecificMiddlewareReturnsEmptyList() {
        Router.Route route = router.handle("/foo", h1).methods(GET);
        assertThat(route.getMiddlewares()).isEmpty();
    }

    @Test
    void testNotSettingMethodsResultsInAllMethodsConfigured() {
        Router.Route route = router.handle("/foo", h1);
        assertThat(route.getMethods()).containsExactlyInAnyOrder(HttpMethod.values());
    }

    @Test
    void testNoMiddlewareRegistered() {
        Router.Route route = router.handle("/foo", h1);
        assertThat(route.getHandler()).isEqualTo(h1);
    }

    // Example and discovery tests


    @Test
    void exampleMergingMiddlewares() {
        Middleware m1 = createMiddleware(LogManager.getLogger("m1"));
        Middleware m2 = createMiddleware(LogManager.getLogger("m2"));
        Middleware m3 = createMiddleware(LogManager.getLogger("m3"));
        Middleware m4 = createMiddleware(LogManager.getLogger("m4"));

        Logger log = LogManager.getLogger("handler");

        router.use(m1, m2);
        Router.Route route = router.handle("/foo", exchange -> log.info("handling")).using(m3, m4);
        route.getHandler().handle(new Exchange());
    }

    private Middleware createMiddleware(Logger log) {
        return next -> exchange -> {
            log.info("before");
            next.handle(exchange);
            log.info("after");
        };
    }

    private Handler noopMiddleware(Handler next) {
        return next;
    }

}