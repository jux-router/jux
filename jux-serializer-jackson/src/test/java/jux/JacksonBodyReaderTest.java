package jux;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class JacksonBodyReaderTest {

    private JacksonBodyReader bodyReader  = new JacksonBodyReader();

    @Test
    void readsString() {
        String input = "\"hello\"";
        String result = bodyReader.read(asStream(input), String.class);
        assertThat(result).isEqualTo("hello");
    }

    @Test
    void readsPojo() {
        String input = "{\"a\":\"hello\",\"b\":1}";
        TestPojo result = bodyReader.read(asStream(input), TestPojo.class);
        assertThat(result).hasFieldOrPropertyWithValue("a", "hello")
                .hasFieldOrPropertyWithValue("b", 1);
    }

    private InputStream asStream(String s) {
        return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
    }

    @SuppressWarnings("unused")
    static class TestPojo {
        private String a;
        private int b;

        TestPojo() {
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }
    }
}