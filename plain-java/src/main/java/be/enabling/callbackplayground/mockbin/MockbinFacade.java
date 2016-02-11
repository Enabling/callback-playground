package be.enabling.callbackplayground.mockbin;

import java.util.List;

import be.enabling.callbackplayground.dto.CallbackDataDTO;
import be.enabling.callbackplayground.service.ActionTriggerer;
import be.enabling.callbackplayground.service.CallbackDataSaver;

/**
 * Facade for using mockbin and registering an observer which is called when a new callback has occurred.
 */
public class MockbinFacade {

    private MockbinPoller poller;
    private CallbackDataSaver saver;
    private ActionTriggerer actionTriggerer;

    public MockbinFacade() {
        this.actionTriggerer = new ActionTriggerer();

        this.saver = new CallbackDataSaver();
        this.saver.setActionTriggerer(this.actionTriggerer);

        this.poller = new MockbinPoller();
        this.poller.setDataSaver(saver);

        init();
    }

    private void init() {
        poller.init();
    }

    /**
     * Return a the ActionTriggerer used when using this facade.
     *
     * @return
     */
    public ActionTriggerer getActionTriggerer() {
        return actionTriggerer;
    }

    /**
     * Return a list of all {@link CallbackDataDTO} results that were registered
     * in Mockbin and were registered in {@link CallbackDataSaver}
     *
     * @return
     */
    public List<CallbackDataDTO> getAllCallbackData() {
        return this.saver.getAllCallbackData();
    }

    /**
     * @return the {@link MockbinPoller} used by the facade to interact with it directly
     */
    public MockbinPoller getPoller() {
        return poller;
    }

    /**
     * @return the {@link CallbackDataSaver} used by the facade to interact with it directly
     */
    public CallbackDataSaver getSaver() {
        return saver;
    }

}
