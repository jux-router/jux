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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class Router {

    private LinkedList<Route> routes = new LinkedList<>();
    private LinkedList<Middleware> middlewares = new LinkedList<>();

    // Routes

    List<Route> getRoutes() {
        return routes;
    }

    public Router handle(String path, jux.Handler handler) {
        routes.add(new Route(handler, path));
        return this;
    }

    public Router methods(HttpMethod... methods) {
        this.routes.getLast().methods(methods);
        return this;
    }

    public Stream<Route> routes() {
        return routes.stream();
    }

    // Middleware

    /**
     * Configure the router to use the provided middleware.
     *
     * @param middleware the {@link Middleware} implementation to be used
     * @return the router itself
     */
    public Router use(Middleware middleware) {
        middlewares.add(middleware);
        return this;
    }

    /**
     * @return the stream of registered {@link Middleware} instances
     */
    public Stream<Middleware> middlewares() {
        return middlewares.stream();
    }

    public class Route {
        private jux.Handler handler;
        private String path;
        private Collection<HttpMethod> methods = new ArrayList<>();

        Route(jux.Handler handler, String path) {
            this.handler = handler;
            this.path = path;
        }

        public jux.Handler getHandler() {
            return handler;
        }

        public String getPath() {
            return path;
        }

        public Collection<HttpMethod> getMethods() {
            return methods;
        }

        void methods(HttpMethod... methods) {
            this.methods.addAll(asList(methods));
        }
    }
}
