package jux;

/**
 * Enumeration of http methods.
 * <p>
 * <tt>CONNECT</tt> and <tt>TRACE</tt> are intentionally ignored at the moment.
 */
public enum HttpMethod {
    GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD
}
