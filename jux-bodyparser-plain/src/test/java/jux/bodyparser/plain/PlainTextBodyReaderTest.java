package jux.bodyparser.plain;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

class PlainTextBodyReaderTest {

    private PlainTextBodyReader bodyReader = new PlainTextBodyReader();

    @Test
    void testSupportsPlainText() {
        Collection<String> supported = bodyReader.supportedMediaTypes();
        assertAll(
                () -> assertEquals(1, supported.size()),
                () -> assertTrue(supported.contains("text/plain"))
        );
    }

    @Test
    void testReadNormalBody() throws Exception {
        byte[] body = "hello".getBytes(UTF_8);
        try (ByteArrayInputStream in = new ByteArrayInputStream(body)) {
            assertEquals("hello", bodyReader.read(in, String.class));
        }
    }

    @Test
    void testCannotHaveNullInput() {
        assertThrows(NullPointerException.class,
                () -> bodyReader.read(null, String.class));
    }

    @Test
    void testMustHaveReturnTypeSet() throws IOException {
        byte[] body = "hello".getBytes(UTF_8);
        try (ByteArrayInputStream in = new ByteArrayInputStream(body)) {
            assertThrows(NullPointerException.class,
                    () -> bodyReader.read(in, null));
        }
    }

    @Test
    void testMustHaveStringAsReturnClass() throws IOException {
        byte[] body = "hello".getBytes(UTF_8);
        try (ByteArrayInputStream in = new ByteArrayInputStream(body)) {
            assertThrows(IllegalArgumentException.class,
                    () -> bodyReader.read(in, Integer.class));
        }
    }
}