package be.enabling.callbackplayground.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import be.enabling.callbackplayground.dto.CallbackDataDTO;
import be.enabling.callbackplayground.service.CallbackDataSaver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class MockbinPoller {

    private static final String TIMER_NAME = "MOCKBIN_POLLER_TIMER";
    private static final Log LOG = LogFactory.getLog(MockbinPoller.class);
    private String mockbinId;
    private Timer timer;
    private boolean initialized = false;
    private long periodInMs;

    @Autowired
    public MockbinPoller(
            @Value("${mockbin.id}")
            String mockbinId,
            CallbackDataSaver callbackDataSaver,
            @Value("${poll.periodInMs}")
            long periodInMs
    ) {
        this.mockbinId = mockbinId;
        this.saver = callbackDataSaver;

        this.periodInMs = periodInMs;

        // Starting timer as non-daemon keeps main thread from exiting
        final boolean runTimerAsDaemon = false;
        this.timer = new Timer(TIMER_NAME, runTimerAsDaemon);
    }

    @PostConstruct
    public synchronized void init() {
        if(initialized) {
            throw new IllegalStateException("Poller was already initialized");
        }

        if (mockbinId == null) {
            throw new NullPointerException("MockbinId cannot be null as it is used to retrieve callback data");
        }

        final long delay = 0L;

        initializeRepo();
        timer.schedule(new PollTask(), delay, periodInMs);

        this.initialized = true;
    }

    private void initializeRepo() {
        final List<CallbackDataDTO> allCallbackData = getAllCallbackData();
        this.saver.initialize(allCallbackData);
    }

    /**
     * Polls with Mockbin every X milliseconds, and tries to save all logged
     * callback attempts from Mockbin into {@link CallbackDataSaver}. The saver
     * {@link CallbackDataSaver} itself is responsible for determining which
     * callback attempt has already been saved, and which new attempts must yet
     * be saved.
     */
    private void poll() {
        final List<CallbackDataDTO> allCallbackData = getAllCallbackData();
        this.saver.insertBulk(allCallbackData);
    }

    private List<CallbackDataDTO> getAllCallbackData() {
        final List<CallbackDataDTO> deserializedObjects = new ArrayList<CallbackDataDTO>();

        try {
            final HttpResponse<JsonNode> reponse = Unirest.get("http://mockbin.org/bin/{id}/log")
                    .routeParam("id", mockbinId).asJson();

            final JsonNode json = reponse.getBody();
            final JSONArray entriesAsJson = json.getObject().getJSONObject("log").getJSONArray("entries");

            for (int i = 0; i < entriesAsJson.length(); i++) {
                try {
                    final JSONObject entryJson = entriesAsJson.getJSONObject(i);
                    final boolean isDeserializableRequest = "POST".equals(entryJson.getJSONObject("request")
                            .optString("method").toUpperCase());

                    if (!isDeserializableRequest) {
                        continue;
                    }

                    // Callback data as a String is stored under
                    // /log//entries/request/postData/text
                    final String callbackDataAsString = entryJson.getJSONObject("request").getJSONObject("postData")
                            .optString("text");
                    ObjectMapper mapper = new ObjectMapper();
                    final CallbackDataDTO mappedCallbackResult = mapper.readValue(callbackDataAsString,
                            CallbackDataDTO.class);

                    deserializedObjects.add(mappedCallbackResult);
                } catch (Exception ex) {
                    LOG.error("Something went wrong when deserializing current entry at index " + i, ex);
                }
            }
        } catch (UnirestException e) {
            LOG.error("Error occurred during polling", e);
        }

        return deserializedObjects;
    }

    private class PollTask extends TimerTask {

        @Override
        public void run() {
            poll();
        }
    }

    public void setDataSaver(CallbackDataSaver saver) {
        this.saver = saver;
    }

    private CallbackDataSaver saver;
}
