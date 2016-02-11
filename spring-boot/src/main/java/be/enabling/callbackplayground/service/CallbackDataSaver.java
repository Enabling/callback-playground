package be.enabling.callbackplayground.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.enabling.callbackplayground.dto.CallbackDataDTO;
import be.enabling.callbackplayground.service.MockbinPoller;

/**
 * Component that saves all callback data from Mockbin and calls
 * {@link ActionTriggerer} to trigger an action after each new callback that has
 * been added
 */
@Component
public class CallbackDataSaver {

    private static final Log LOG = LogFactory.getLog(MockbinPoller.class);

    private final LinkedHashSet<CallbackDataDTO> callbackData = new LinkedHashSet<CallbackDataDTO>();

    @Autowired
    private ActionTriggerer actionTrigerer;

    /**
     * Returns a list of all callback data in the order as it inserted into the
     * repo.
     *
     * @return
     */
    public List<CallbackDataDTO> getAllCallbackData() {
        return new ArrayList<CallbackDataDTO>(callbackData);
    }

    /**
     * Initializes the repo with the items in the list in the order as they
     * appear in the list.
     *
     * Note that this removes any existing data from the repo!
     *
     * @param data
     */
    public void initialize(List<CallbackDataDTO> data) {
        LOG.info("Initializing repo with data, clearing all existing data");
        callbackData.clear();
        callbackData.addAll(data);
    }

    /**
     * Inserts the given data in the repo in the order as they appear in the
     * list.
     *
     * If the data is not yet present in the repo, it will be added. A trigger
     * is called after each insertion.
     *
     * If the data was already put in the repo, it will be ignored: it will not
     * be added to the repo and no trigger will be called.
     *
     * Note that the trigger runs inside the current thread.
     *
     * @param data
     */
    public void insertBulk(List<CallbackDataDTO> data) {
        // Correct implementation of hashcode and equals is required for the DTO
        for (CallbackDataDTO callbackDataDTO : data) {
            if (callbackData.contains(callbackDataDTO)) {
                continue;
            }

            LOG.debug("New CallbackData detected: " + data);
            callbackData.add(callbackDataDTO);

            trigger(callbackDataDTO);
        }
    }

    private void trigger(CallbackDataDTO callbackDataDTO) {
        LOG.debug("Triggering action for " + callbackDataDTO);
        this.actionTrigerer.triggerAction(callbackDataDTO);
    }
}
