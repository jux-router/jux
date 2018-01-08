package jux.undertow;

import jux.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Test the Undertow server.
 */
class UndertowServerTest {

    private Server server;

    @AfterEach
    void stopJux() {
        server.stop();
    }

    @Test
    void test() {
        Router router = Jux.router();
        router.handle("/foo", (ctx, req) -> Response.ok("hello").as("text/plain")).methods(HttpMethod.GET);
        server = Jux.start(0, router);
    }

}
