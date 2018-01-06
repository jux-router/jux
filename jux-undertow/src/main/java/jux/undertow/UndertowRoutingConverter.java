package jux.undertow;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import jux.Router;
import jux.RouterConverter;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Converts the routes registered in {@link jux.Router} into a
 * {@link RoutingHandler} used by the Undertow server.
 */
class UndertowRoutingConverter implements RouterConverter<RoutingHandler> {

    @Override
    public RoutingHandler convert(Router router) {
        RoutingHandler handler = Handlers.routing();

        StreamSupport.stream(router.spliterator(), false)
                .flatMap(this::routes)
                .forEach(r -> handler.add(r.method, r.path, r.handler));

        return handler;
    }

    private Stream<UndertowRoute> routes(Router.Route route) {
        return route.getMethods().stream()
                .map(method -> new UndertowRoute(method.name(), route.getPath(),
                        route.getHandler()));
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