package jux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class JacksonBodyReader implements BodyReader {

    private static final Logger LOG =
            LoggerFactory.getLogger(JacksonBodyReader.class);

    private ObjectMapper mapper;

    public JacksonBodyReader() {
        this(new ObjectMapper());
        LOG.debug("Created JacksonBodyReader with default configuration");
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
