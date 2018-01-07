package jux.undertow;

import jux.HttpMethod;
import jux.Jux;
import jux.Response;
import jux.Router;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Test the Undertow server.
 */
class UndertowServerTest {

    @AfterEach
    void stopJux() {
        Jux.stop();
    }

    @Test @Disabled
    void test() {
        Router router = Jux.router();
        router.handle("/foo", (ctx, req) -> Response.ok("hello").as("text/plain")).methods(HttpMethod.GET);
        Jux.start(0, router);
    }

}
