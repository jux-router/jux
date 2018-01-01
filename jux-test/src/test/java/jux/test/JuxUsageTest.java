package jux.test;

import jux.Jux;
import org.junit.jupiter.api.Test;

/**
 * Test the usage of JUX.
 */
class JuxUsageTest {

    @Test
    void aTest() {
        Jux.builder()
                .handle("/info", "s1");
    }
}
