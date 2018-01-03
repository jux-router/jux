package jux;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jux.HttpMethod.*;
import static org.assertj.core.api.Assertions.assertThat;

class RouterTest {

    @Test
    void buildsBasicRoutes() {
        jux.Handler h1 = (ctx, req, resp) -> {};
        jux.Handler h2 = (ctx, req, resp) -> {};
        Router routeBuilder = new Router()
                .handle("/head/get", h1).methods(HEAD, GET)
                .handle("/2ndhandler", h2).methods(POST);

        List<Router.Route> routes = routeBuilder.getRoutes();
        assertThat(routes).hasSize(2);

        Router.Route route = routes.get(0);
        assertThat(route.getHandler()).isEqualTo(h1);
        assertThat(route.getPath()).isEqualTo("/head/get");
        assertThat(route.getMethods()).containsExactlyInAnyOrder(HEAD, GET);

        route = routes.get(1);
        assertThat(route.getHandler()).isEqualTo(h2);
        assertThat(route.getPath()).isEqualTo("/2ndhandler");
        assertThat(route.getMethods()).containsExactlyInAnyOrder(POST);
    }
}