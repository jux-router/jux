package jux;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BodyWritersTest {
    @Test
    void getsSupportedBodyWriter() {
        BodyWriter reader = BodyWriters.forMediaType("text/plain");
        Assertions.assertThat(reader)
                .isNotNull()
                .isInstanceOf(MockBodyWriter.class);
    }

    @Test
    void failsOnNotSupportedMediaType() {
        Assertions.assertThatThrownBy(() -> BodyWriters.forMediaType("a/b"))
                .isInstanceOf(IllegalStateException.class);
    }
}
