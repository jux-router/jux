package jux;

// TODO use a builder
public class Response<T> {

    public static <T> Response<T> ok(T body) {
        Response<T> response = new Response<>();
        response.status = 200;
        response.body = body;
        return response;
    }

    private T body;
    private String mediaType;
    private int status;

    public T getBody() {
        return body;
    }

    public String getMediaType() {
        return mediaType;
    }

    public int getStatus() {
        return status;
    }

    public Response<T> as(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }
}
