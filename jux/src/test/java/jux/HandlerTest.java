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

class HandlerTest {

    private static final Logger LOG = LogManager.getLogger(HandlerTest.class);

    @Test
    void exampleHandleAndMiddleware() {
        Handler h1 = exchange -> LOG.info("hello from the handler");

        Middleware m1 = next -> (exchange) -> {
            LOG.info("m1 before");
            next.handle(exchange);
            LOG.info("m1 after");
        };

        Middleware m2 = next -> (exchange) -> {
            LOG.info("m2 before");
            next.handle(exchange);
            LOG.info("m2 after");
        };

        Handler handler = m2.around(m1.around(h1));
        Handler handler2 = m2.around(m1.around(exchange -> LOG.info("Second handler")));

        handler.handle(new Exchange());
        handler2.handle(new Exchange());
    }

}
