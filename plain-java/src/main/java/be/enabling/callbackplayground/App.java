package be.enabling.callbackplayground;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.enabling.callbackplayground.mockbin.MockbinFacade;
import be.enabling.callbackplayground.mockbin.MockbinPoller;
import be.enabling.callbackplayground.service.ActionTriggerer;
import be.enabling.callbackplayground.service.CallbackDataSaver;

/**
 * Plain Java application with a main method that starts a {@link MockbinPoller}
 * which polls for added callback events. The required mockbin id and the retry
 * period is be configured in src/main/resources/application.properties <br/>
 * New callbacks events are stored inside {@link CallbackDataSaver}. For each
 * new inserted event,
 * {@link ActionTriggerer#triggerAction(be.enabling.callbackplayground.dto.CallbackDataDTO)}
 * will be called and the action implemented in that method will be executed.
 */
public class App {

    public static void main(String[] args) {
        // When facade is created, the poller will start
        final MockbinFacade mockbinFacade = new MockbinFacade();
    }

    private static final Log LOG = LogFactory.getLog(App.class);
}
