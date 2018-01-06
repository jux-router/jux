package jux;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonBodyWriter implements BodyWriter {

    private ObjectMapper mapper;

    public JacksonBodyWriter() {
        this(new ObjectMapper());
    }

    public JacksonBodyWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String write(Object o) {
        try {
            return this.mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            // TODO handle this exception properly.
            throw new RuntimeException(e);
        }
    }
}
