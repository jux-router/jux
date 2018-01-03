package jux;

public interface Handler {

    Response handle(Context ctx, Request req);

}
