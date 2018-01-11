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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ServiceLoader;

/**
 * Jux is the main point of entry to the Jux library.
 *
 * @author sandornemeth
 */
public class Jux {

    private static final Logger LOG = LogManager.getLogger(Jux.class);

    public static Router router() {
        return new Router();
    }

    public static Server start(int port, jux.Router router) {
        ServiceLoader<Server> loader = ServiceLoader.load(Server.class);
        Server server = loader.findFirst().orElseThrow(
                () -> new IllegalStateException(
                        "No jux.Server implementation present."));
        LOG.debug("Using server {}", server.getClass());
        server.use(router)
                .listenOn(port)
                .start();
        LOG.info("Listening on {}", port);
        return server;
    }

}
