package jux;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BodyReadersTest {
    @Test
    void getsSupportedBodyReader() {
        BodyReader reader = BodyReaders.forMediaType("text/plain");
        Assertions.assertThat(reader)
                .isNotNull()
                .isInstanceOf(MockBodyReader.class);
    }

    @Test
    void failsOnNotSupportedMediaType() {
        Assertions.assertThatThrownBy(() -> BodyReaders.forMediaType("a/b"))
                .isInstanceOf(IllegalStateException.class);
    }
}