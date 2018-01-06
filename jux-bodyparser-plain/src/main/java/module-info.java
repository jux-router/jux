import jux.bodyparser.plain.PlainTextBodyWriter;

module jux.bodyparser.plain {
    requires jux;

    provides jux.BodyWriter with PlainTextBodyWriter;
}