package jux.test;

import com.google.common.io.CharStreams;
import jux.Handler;
import jux.HttpMethod;
import jux.Response;
import jux.Router;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.InputStreamReader;
import java.net.URI;

import static jux.HttpMethod.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JuxTest
public class QueryParameterHandlerTest extends JuxTestBase {

    @Override
    void validateResponse(HttpResponse response) throws Exception {
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("text/plain",
                response.getFirstHeader("Content-Type").getValue());
        String content = CharStreams.toString(
                new InputStreamReader(response.getEntity().getContent()));
        assertEquals("test", content);
    }

    @Override
    void configureRequest(HttpRequestBase request, int port) throws Exception {
        String uri = "http://localhost:" + port + "/foo?aparam=test";
        request.setURI(URI.create(uri));
    }

    @Override @RouteProvider
    protected void configureRoutes(Router router) {
        Handler h = (ctx, req) -> Response.ok(req.getParam("aparam").orElse(""))
                .as("text/plain");
        router.handle("/foo", h).methods(GET, POST, PUT, PATCH, DELETE);
    }
}
