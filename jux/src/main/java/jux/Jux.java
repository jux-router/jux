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
