[![Build Status](https://travis-ci.org/sandor-nemeth/jux.svg?branch=master)](https://travis-ci.org/sandor-nemeth/jux)

# jux
> A minimalist Java web framework.

A brief description of your project, what it is used for and how does life get
awesome when someone starts to use it.

## Installing / Getting started

Get Jux via either Maven or Gradle:

**Maven**

```xml
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
```

**Gradle**

```groovy
dependencies {
    implementation "jux:jux-undertow:$juxVersion"
    implementation "jux:jux-bodyparser-plain:$juxVersion"
}
```

And afterwards to get started in your application:

```java
class App {
    
    public static void main(String... args) {
        Router router = Jux.router()
            .handle("/foo", (ctx, req) -> Response.ok("hello").as("text/plain"))
                .methods(HttpMethod.GET);
        Jux.start(8080, router);
    }
    
}
```

## Developing

If you want to build the project:

```shell
git clone https://github.com/sandor-nemeth/jux.git
cd jux/
./gradlew wrapper build
```

### Deploying / Publishing

## Features

## Configuration

## Contributing

If you'd like to contribute, please fork the repository and use a feature
branch. Pull requests are warmly welcome. See the `CONTRIBUTING.md` for 
guidelines (yet to be written as this project is just getting born.)

## Licensing

This project is licensed under the Apache 2 license. See the accompanying 
`LICENSE` file.