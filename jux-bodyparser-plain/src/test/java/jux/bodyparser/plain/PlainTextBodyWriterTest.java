package jux.bodyparser.plain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlainTextBodyWriterTest {

    private PlainTextBodyWriter writer = new PlainTextBodyWriter();

    @Test
    void supportsOnlyPlainText() {
        assertThat(writer.supportedMediaTypes())
                .hasSize(1)
                .containsExactlyInAnyOrder("text/plain");
    }

    @Test
    void writesStringCorrectly() {
        String result = writer.write("hello");
        assertThat(result).isEqualTo("hello");
    }

    @Test
    void writesEmptyStringCorrectly() {
        assertThat(writer.write("")).isEqualTo("");
    }

    @Test
    void writesNullAsEmptyString() {
        assertThat(writer.write(null)).isEqualTo("");
    }
}