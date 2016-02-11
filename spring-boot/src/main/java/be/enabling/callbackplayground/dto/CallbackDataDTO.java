package be.enabling.callbackplayground.dto;

import java.util.Date;

public class CallbackDataDTO {

    private String callbackUrl;
    private String condition;
    private String container;
    private String device;

    private CallbackSensorDataDTO sensorData;

    private Date timestamp;
    private String trigger;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getCondition() {
        return condition;
    }

    public String getContainer() {
        return container;
    }

    public String getDevice() {
        return device;
    }

    public CallbackSensorDataDTO getSensorData() {
        return sensorData;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setSensorData(CallbackSensorDataDTO sensorData) {
        this.sensorData = sensorData;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callbackUrl == null) ? 0 : callbackUrl.hashCode());
        result = prime * result + ((condition == null) ? 0 : condition.hashCode());
        result = prime * result + ((container == null) ? 0 : container.hashCode());
        result = prime * result + ((device == null) ? 0 : device.hashCode());
        result = prime * result + ((sensorData == null) ? 0 : sensorData.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((trigger == null) ? 0 : trigger.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CallbackDataDTO other = (CallbackDataDTO) obj;
        if (callbackUrl == null) {
            if (other.callbackUrl != null)
                return false;
        } else if (!callbackUrl.equals(other.callbackUrl))
            return false;
        if (condition == null) {
            if (other.condition != null)
                return false;
        } else if (!condition.equals(other.condition))
            return false;
        if (container == null) {
            if (other.container != null)
                return false;
        } else if (!container.equals(other.container))
            return false;
        if (device == null) {
            if (other.device != null)
                return false;
        } else if (!device.equals(other.device))
            return false;
        if (sensorData == null) {
            if (other.sensorData != null)
                return false;
        } else if (!sensorData.equals(other.sensorData))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        if (trigger == null) {
            if (other.trigger != null)
                return false;
        } else if (!trigger.equals(other.trigger))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CallbackDataDTO [callbackUrl=" + callbackUrl + ", condition=" + condition + ", container=" + container
                + ", device=" + device + ", sensorData=" + sensorData + ", timestamp=" + timestamp + ", trigger="
                + trigger + "]";
    }

}
