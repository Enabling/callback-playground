package be.enabling.callbackplayground.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.enabling.callbackplayground.App;
import be.enabling.callbackplayground.dto.CallbackDataDTO;

/**
 * Service which will trigger its action when it is called after a callback has been triggered.
 */
public class ActionTriggerer {

    public void triggerAction(CallbackDataDTO dto) {
        // TODO: update this block to do something useful with callback data
        LOG.info("New data inserted: " + dto);
    }

    private static final Log LOG = LogFactory.getLog(App.class);
}
