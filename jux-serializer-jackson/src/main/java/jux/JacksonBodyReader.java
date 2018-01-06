package jux;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JacksonBodyReader implements BodyReader {

    private ObjectMapper mapper;

    public JacksonBodyReader() {
        this(new ObjectMapper());
    }

    public JacksonBodyReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> T read(InputStream is, Class<T> returnClass) {
        try {
            return this.mapper.readValue(is, returnClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO handle the error correctly!
        }
    }
}
