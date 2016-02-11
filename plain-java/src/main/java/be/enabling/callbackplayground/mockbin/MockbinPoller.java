package be.enabling.callbackplayground.mockbin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import be.enabling.callbackplayground.dto.CallbackDataDTO;
import be.enabling.callbackplayground.service.CallbackDataSaver;
import be.enabling.callbackplayground.service.PropertiesHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MockbinPoller {

    private static final String TIMER_NAME = "MOCKBIN_POLLER_TIMER";
    private static final Log LOG = LogFactory.getLog(MockbinPoller.class);
    private String mockbinId;
    private Timer timer;
    private boolean initialized = false;

    public MockbinPoller() {
        this.mockbinId = PropertiesHolder.INSTANCE.getMockbinId();

        // Starting timer as non-daemon keeps main thread from exiting
        final boolean runTimerAsDaemon = false;
        this.timer = new Timer(TIMER_NAME, runTimerAsDaemon);
    }

    synchronized void init() {
        if(initialized) {
            throw new IllegalStateException("Poller was already initialized");
        }

        if (mockbinId == null) {
            throw new NullPointerException("MockbinId cannot be null as it is used to retrieve callback data");
        }

        final long delay = 0L;
        final long periodInMs = PropertiesHolder.INSTANCE.getPollPeriodInMs();

        initializeRepo();
        timer.schedule(new PollTask(), delay, periodInMs);

        this.initialized = true;
    }

    private void initializeRepo() {
        final List<CallbackDataDTO> allCallbackData = getAllCallbackData();
        saver.initialize(allCallbackData);
    }

    protected void poll() {
        final List<CallbackDataDTO> allCallbackData = getAllCallbackData();
        saveData(allCallbackData);
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

    private void saveData(final List<CallbackDataDTO> allCallbackData) {
        saver.insertBulk(allCallbackData);
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
