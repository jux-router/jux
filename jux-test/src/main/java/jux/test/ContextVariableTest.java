package jux.test;

import jux.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;

import static jux.HttpMethod.*;

/**
 * Test if context variables get set.
 */
public class ContextVariableTest extends ResponseExpectingTestBase {

    @Override
    void validateResponse(HttpResponse response) throws Exception {
        JuxAssertions.assertThat(response)
                .isOk()
                .hasContentType(ContentType.PLAIN_TEXT.getContentType())
                .hasStringContent("hello");
    }

    @Override
    void configureRequest(HttpRequestBase request, int port) throws Exception {
        request.setURI(URI.create("http://localhost:" + port + "/foo"));
    }

    @Override
    protected void configureRoutes(Router router) {
        router.handle("/foo", this::handler).methods(GET, POST, PUT, DELETE, PATCH);
    }

    Response handler(Context ctx, Request req) {
        return Response.ok(ctx.get("test", String.class).orElse("failed"));
    }
}
