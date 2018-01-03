package jux.test;

import jux.Jux;
import jux.Router;
import org.junit.jupiter.api.Test;

import static jux.HttpMethod.GET;

/**
 * Test the usage of JUX.
 */
class JuxUsageTest {

    @Test
    void aTest() {
        jux.Router router = Jux.router()
                .handle("/info", "s1").methods(GET)
                .handle("/env", "s2").methods(GET);

        Jux.start(8080, router);
    }
}
