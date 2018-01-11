/**
 * Copyright 2018 Sandor Nemeth
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

import java.util.*;

import static java.util.Arrays.asList;

public class Router implements Iterable<Router.Route> {

    private LinkedList<Route> routes = new LinkedList<>();

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

    @Override
    public Iterator<Route> iterator() {
        return routes.iterator();
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
