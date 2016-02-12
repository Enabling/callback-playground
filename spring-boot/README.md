# callback-playground-spring-boot

The callback-playground project serves as a playground for reacting to callback events triggered by the enabling company system.

It allows you to define a callback in the enabling system that, when triggered, makes a request to a [mockbin](http://mockbin.org/) of your choosing.

The playground detects new callbacks being fired (after some time) and will trigger a custom action for each callback.

## Usage

The application contains a [Spring Boot](http://projects.spring.io/spring-boot/) application, which will start an embedded TomCat container in which the Spring application will run.

By default, the application is available on http://localhost:8080

Start the application with the following command:

```
  mvn clean spring-boot:run
```

The application will start and load in all existing callback attempts. When a new callback is noticed, the action in ```be.enabling.callbackplayground.service.ActionTriggerer``` is triggered.

By default, the action will log the callback to the console.

### Properties

The application can be configured using a properties file (src/main/resources/application.properties).

The properties file contains the following:
- `mockbin.id`: the mockbin id which will be used by the app to retrieve events from
- `poll.periodInMs`: the time in milliseconds that the poller will wait before polling again
- `server.port`: the port on which the application is accessible

## Dependencies

- Java JDK 7 (or higher)
- Maven (tested with v3.2.3)

## Known limitations

### Mockbin request limit

Mockbin currently has a **request limit of 100 request**. As a result, any subsequent requests are ignored and will not show up in the request log. This application needs this log to be able to pick up on new callbacks. If the request limit has been reached, no new actions will be triggered.

This can easily be fixed by:
- creating a new mockbin with a new id
- updating the trigger to call to the new mockbin