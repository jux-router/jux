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

    private static Server instance;

    public static Router router() {
        return new Router();
    }

    public static void start(int port, jux.Router router) {
        if (instance != null) {
            throw new IllegalStateException("Jux already running!");
        }
        ServiceLoader<Server> loader = ServiceLoader.load(Server.class);
        Server server = loader.findFirst().orElseThrow(
                () -> new IllegalStateException(
                        "No jux.Server implementation present."));
        instance = server;
        LOG.debug("Using server {}", server.getClass());
        server.use(router)
                .listenOn(port)
                .start();
    }

    public static void stop() {
        if (null != instance) {
            instance.stop();
        }
    }
}
