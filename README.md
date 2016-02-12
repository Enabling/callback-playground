# callback-playground

The callback-playground project serves as a playground for reacting to callback events triggered by the enabling company system.

It allows you to define a callback in the enabling system that, when triggered, makes a request to a [mockbin](http://mockbin.org/) of your choosing.

The playground detects new callbacks being fired (after some time) and will trigger a custom action for each callback.

## Flavors

The playground comes in the following flavours:

- [`plain-java`](plain-java): a plain Java application that runs as a simple JAR, and
- [`spring-boot`](spring-boot): a Spring Boot application that runs inside a TomCat container which allows you to use Spring's dependency injection and REST controllers to interact with the running application.

## Additional documentation

See [enabling trigger documentation](http://docs.enabling.be/docs/trigger-api) for more information about trigger creation.

Example curl (replace where necessary):

```
curl -i -X POST -H "Authorization:Bearer xxxxx" -H "Accept:application/json" -H "Content-type:application/json" https://api.enabling.be/seaas/0.0.1/device/xxx/triggers/create/x -d '{"trigger":{"unit":"Celsius","conditionValueName":"temperature","conditionValueType":"int","condition":"temperature>23","callbackUrl":"http://mockbin.org/bin/xxxxx000000xxxxxxxx"}}'
```
