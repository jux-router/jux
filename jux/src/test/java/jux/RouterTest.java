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

import org.junit.jupiter.api.Test;

import java.util.List;

import static jux.HttpMethod.*;
import static org.assertj.core.api.Assertions.assertThat;

class RouterTest {

    @Test
    void buildsBasicRoutes() {
        jux.Handler h1 = (ctx, req) -> null;
        jux.Handler h2 = (ctx, req) -> null;
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