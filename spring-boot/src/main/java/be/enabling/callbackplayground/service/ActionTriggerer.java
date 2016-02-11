package be.enabling.callbackplayground.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.enabling.callbackplayground.App;
import be.enabling.callbackplayground.dto.CallbackDataDTO;

/**
 * Service which will trigger its action when it is called after a callback has been triggered.
 */
@Service
public class ActionTriggerer {

    @Autowired
    private CallbackDataSaver callbackSaver;

    public void triggerAction(CallbackDataDTO dto) {
        // TODO: update this block to do something useful with callback data
        LOG.info("New data inserted: " + dto);
        LOG.info("There are currently " + callbackSaver.getAllCallbackData().size() + " number of callback calls registered inside this application.");
    }

    private static final Log LOG = LogFactory.getLog(App.class);
}
