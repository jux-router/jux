package jux;

import com.google.common.annotations.VisibleForTesting;

import java.util.*;

import static java.util.Arrays.asList;

public class JuxRouteBuilder {

    private LinkedList<Route> routes = new LinkedList<>();

    @VisibleForTesting
    List<Route> getRoutes() {
        return routes;
    }

    public JuxRouteBuilder handle(String path, Object handler) {
        routes.add(new Route(handler, path));
        return this;
    }

    public JuxRouteBuilder methods(HttpMethod... methods) {
        this.routes.getLast().methods(methods);
        return this;
    }

    class Route {
        private Object handler;
        private String path;
        private Collection<HttpMethod> methods = new ArrayList<>();

        Route(Object handler, String path) {
            this.handler = handler;
            this.path = path;
        }

        public Object getHandler() {
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
