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
