package jux;

/**
 * Jux is the main point of entry to the Jux library.
 *
 * @author sandornemeth
 */
public class Jux {

    public static Router router() {
        return new Router();
    }

    public static void start(int port, jux.Router router) {
        new UndertowServer().listenOn(port).use(router).start();
    }
}
