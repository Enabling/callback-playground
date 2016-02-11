package be.enabling.callbackplayground.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.enabling.callbackplayground.mockbin.MockbinPoller;

/**
 * Object which exposes properties read in from the <i>application.properties</i> file on the classpath.
 */
public enum PropertiesHolder {

    INSTANCE;

    private final Log LOG = LogFactory.getLog(MockbinPoller.class);

    private String mockbinId;
    private Long pollPeriodInMs;

    private PropertiesHolder() {
        final Properties properties = new Properties();
        try {
            final InputStream stream = PropertiesHolder.class.getClassLoader().getResourceAsStream(
                    "application.properties");
            properties.load(stream);

            this.mockbinId = properties.getProperty("mockbin.id");
            this.pollPeriodInMs = Long.parseLong(properties.getProperty("poll.periodInMs"));
        } catch (IOException e) {
            LOG.error("Could not load properties frop application.properties from the classpath", e);
        }
    }

    public String getMockbinId() {
        return mockbinId;
    }

    public Long getPollPeriodInMs() {
        return pollPeriodInMs;
    }
}
