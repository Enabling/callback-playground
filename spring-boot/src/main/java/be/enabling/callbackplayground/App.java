package be.enabling.callbackplayground;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import be.enabling.callbackplayground.service.ActionTriggerer;

/**
 * Application that starts a Spring Application Context that will repeatedly
 * poll for any added callback attempts on Mockbin. The required mockbin id and
 * the retry period is be configured in
 * src/main/resources/application.properties
 *
 * If a new attempt is detected, an action in {@link ActionTriggerer} is
 * triggered. This action can be updated to do something else with the callback
 * data. <br/>
 *
 * <b>DISCLAIMER</b>: The current application can be run with maven using <i>mvn
 * spring-boot:run</i>. It cannot, however, be packaged into a war and deployed
 * (tested on Wildfly 8.1.0). Using the spring-boot:run command will also start
 * a server so that you can write new Rest controllers, so it is adviced to use
 * this approach.
 *
 * <br/>
 * More information about creating a deployable war from a Spring boot
 * application can be found <a href=
 * "https://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html#howto-create-a-deployable-war-file"
 * >here</a>
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.bannerMode(Banner.Mode.OFF).sources(App.class);
    }

    public static void main(String[] args) {
        new App().configure(new SpringApplicationBuilder()).run(args);
    }
}
