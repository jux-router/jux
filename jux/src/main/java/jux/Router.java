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

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Stream;

/**
 * The router is the central piece of JUX, being responsible for building up the application that is to be used.
 *
 * Usage (example):
 *
 * <pre>{@code
 * Router router = Jux.router();
 *
 * router.use(new Middleware1());
 * router.use(new Middleware2());
 *
 * router.handle("/foo", new FooHandler()).methods(GET).and()
 *     .handle("/foo", new FooPostHandler()).methods(POST).and()
 *     .handle("/bar", new BarHandler()).methods(POST).using(new Middleware3());
 * }</pre>
 *
 * <strong>Registering middlewares:</strong> The router will assemble the final {@link Handler handlers} using the
 * following order: <ol> <li>Middlewares registered with {@link Router#use(Middleware...)} in the order of
 * registration</li> <li>Middlewares registered with {@link Route#using(Middleware...)} in the order of
 * registration</li> <li>the registered {@link Handler} for the route</li> </ol> Currently there is <strong>no
 * possibility</strong> to change this ordering.
 *
 * @author Sandor Nemeth
 */
public class Router {

    private LinkedList<Route> routes = new LinkedList<>();
    private LinkedList<Middleware> middlewares = new LinkedList<>();
    private boolean gracefulShutdown;

    // Routes
    public Route handle(String path, jux.Handler handler) {
        Route route = new Route(handler, path, this);
        routes.add(route);
        return route;
    }

    public Stream<Route> routes() {
        return routes.stream();
    }

    // Middlewares

    public void use(Middleware... middlewares) {
        this.middlewares.addAll(Arrays.asList(middlewares));
    }

    public Stream<Middleware> middlewares() {
        return this.middlewares.stream();
    }

    // other configuration

    public boolean isGracefulShutdown() {
        return gracefulShutdown;
    }

    public void useGracefulShutdown() {
        this.gracefulShutdown = true;
    }

    public class Route {

        private jux.Handler handler;
        private String path;
        private Router router;
        private Set<HttpMethod> methods = Sets.newHashSet(HttpMethod.values());
        private LinkedList<Middleware> middlewares = new LinkedList<>();

        Route(jux.Handler handler, String path, Router router) {
            this.handler = handler;
            this.path = path;
            this.router = router;
        }

        public Router and() {
            return router;
        }

        public Route methods(HttpMethod... methods) {
            this.methods = Sets.intersection(this.methods, Sets.newHashSet(methods));
            return this;
        }

        public Route using(Middleware... middlewares) {
            this.middlewares.addAll(Arrays.asList(middlewares));
            return this;
        }

        public jux.Handler getHandler() {
            return useMiddlewares(this.router.middlewares, useMiddlewares(middlewares, this.handler));
        }

        private Handler useMiddlewares(LinkedList<Middleware> middlewares, Handler handler) {
            Handler result = handler;
            Iterator<Middleware> iter = middlewares.descendingIterator();
            while (iter.hasNext()) {
                result = iter.next().around(result);
            }
            return result;
        }

        public String getPath() {
            return path;
        }

        public Collection<HttpMethod> getMethods() {
            return methods;
        }

        public List<Middleware> getMiddlewares() {
            return middlewares;
        }
    }
}
