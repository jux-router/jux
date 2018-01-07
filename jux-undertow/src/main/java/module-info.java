module jux.undertow {
    exports jux.undertow to jux;

    requires jux;
    requires undertow.core;

    provides jux.Server with jux.undertow.UndertowServer;
}