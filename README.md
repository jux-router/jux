# jux
> A minimalist and opinionated Java web framework.

[![Travis](https://img.shields.io/travis/rust-lang/rust.svg?style=flat-square)](https://travis-ci.org/jux-router/jux)
[![Codecov](https://img.shields.io/codecov/c/github/codecov/example-java.svg?style=flat-square)](https://codecov.io/gh/jux-router/jux)

The `jux` project is born out of the idea that the Java community should also
have a simple library which provides a high-performance web framework, while
being extendable and very simple in the same moment. 

This project builds on several principles:

- _Use only what's needed_: the features one needs have to be explicitly
  included
- _What's there should be present_: the included features should be - as much as
  it is possible - automatically registered and configured, no extra things
  necessary
- _Be cloud native_: the application should be containerizable utilizing the
  full extent of the features supported there: e.g. it should react correctly to
  the `SIGTERM` and `SIGKILL` signals a container might receive
 
## Getting started

Get Jux via either Maven or Gradle:

**Maven**

```xml
<dependencies>
    <dependency>
        <groupId>jux</groupId>
        <artifactId>jux-undertow</artifactId>
        <version>${jux.version}</version>
    </dependency>
    <dependency>
        <groupId>jux</groupId>
        <artifactId>jux-bodyparser-plain</artifactId>
        <version>${jux.version}</version>
    </dependency>
</dependencies>
```

**Gradle**

```groovy
dependencies {
    implementation "jux:jux-undertow:$juxVersion"
    implementation "jux:jux-bodyparser-plain:$juxVersion"
}
```

And afterwards the simplest way to get a simple app up and running:

```java
class App {
    
    public static void main(String... args) {
        Router router = Jux.router()
            .handle("/foo", App::sayHello).methods(HttpMethod.GET);
        Jux.start(8080, router);
    }
    
    public static Response sayHello(Context ctx, Request req) {
        return Response.ok("hello").asPlainText();
    } 
    
}
```

Then just go to [http://localhost:8080/hello] and see the result.

## Developing

**Java 9** is a prerequisite for this project to be built, so make sure that it
is configured. Afterwards just check out the repository, and build the project
via `mvnw`. 

```shell
git clone https://github.com/jux-router/jux.git
cd jux/
./mvnw clean install
```

## Distributing

The distribution of this project is handled to Bintray via Travis-CI.

## Contributing

If you'd like to contribute, please fork the repository and use a feature
branch. Pull requests are warmly welcome. See the `CONTRIBUTING.md` for 
guidelines (yet to be written as this project is just getting born.)

## Licensing

This project is licensed under the Apache 2 license. See the accompanying 
`LICENSE` file.
