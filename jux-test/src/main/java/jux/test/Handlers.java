package jux.test;

import jux.Handler;
import jux.Response;

/**
 * Collection of handlers for test cases.
 */
public class Handlers {

    public static Handler BASIC_HELLO = (ctx, req) -> Response.ok("hello").as("text/plain");

}
