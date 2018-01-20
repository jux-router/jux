package jux;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PortProviderTest {

    @Test
    void testReturnSetPort() {
        assertThat(new PortProvider(8080).get()).isEqualTo(8080);
    }

    @Test
    void testReturnRandomPort() {
        assertThat(new PortProvider(0).get()).isGreaterThan(0);
    }
}