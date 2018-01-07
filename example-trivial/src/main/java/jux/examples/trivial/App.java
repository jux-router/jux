package jux.examples.trivial;

import jux.HttpMethod;
import jux.Jux;
import jux.Response;
import jux.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private static final Logger LOG = LogManager.getLogger(App.class);

    public static void main(String... args) {
        LOG.info("Starting application");
        Router router = Jux.router();
        router.handle("/hello", ((ctx, req) -> Response.ok("hello").as("text/plain"))).methods(HttpMethod.GET);

        Jux.start(0, router);
    }

}
