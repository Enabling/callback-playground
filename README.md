# callback-playground

The callback-playground project serves as a playground for reacting to callback events triggered by the enabling company system.

It allows you to define a callback in the enabling system that, when triggered, makes a request to a [mockbin](http://mockbin.org/) of your choosing.

The playground detects new callbacks being fired (after some time) and will trigger a custom action for each callback.

## Flavors

The playground comes in the following flavours:

- [`plain-java`](plain-java): a plain Java application that runs as a simple JAR, and
- [`spring-boot`](spring-boot): a Spring Boot application that runs inside a TomCat container which allows you to use Spring's dependency injection and REST controllers to interact with the running application.