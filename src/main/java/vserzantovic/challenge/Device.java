package vserzantovic.challenge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Device {

    private String deviceId;
    private String requestId;
    private String type;
    private String model_name;
    private String defaultPolicy;
    private List<String> whiteList;
    private List<String> blackList;
    private String timeStamp;
    private String url;

    @JsonCreator
    public Device(
            @JsonProperty("device_id") String deviceId,
            @JsonProperty("request_id") String requestId,
            @JsonProperty("type") String type,
            @JsonProperty("model_name") String model_name,
            @JsonProperty("default") String defaultPolicy,
            @JsonProperty("whitelist") List<String> whiteList,
            @JsonProperty("blacklist") List<String> blackList,
            @JsonProperty("timestamp") String timeStamp) {
        this.deviceId = deviceId;
        this.requestId = requestId;
        this.type = type;
        this.model_name = model_name;
        this.defaultPolicy = defaultPolicy;
        this.whiteList = whiteList;
        this.blackList = blackList;
        this.timeStamp = timeStamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getDefaultPolicy() {
        return defaultPolicy;
    }


    public void setDefaultPolicy(String defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<String> blackList) {
        this.blackList = blackList;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
