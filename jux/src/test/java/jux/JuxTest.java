package jux;

import org.junit.jupiter.api.Test;

class JuxTest {

    /**
     * Test if a simple Jux application starts or not. Uses {@link MockServer},
     * so the test will fail if it doesn't work, but otherwise it passes,
     * therefore no expectations.
     */
    @Test
    void simpleJuxApplicationStarts() {
        Router router = Jux.router();
        Jux.start(0, router);
    }
}
