package vserzantovic.chalenge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class Device {

    private String type;
    private String model_name;

    //    @JsonProperty("default")
    private String defaultPolicy;
    private List<String> whiteList;
    private List<String> blackList;
    private Number timeStamp;
    private String url;

    @JsonCreator
    public Device(@JsonProperty("type") String type, @JsonProperty("model_name") String model_name, @JsonProperty("default") String defaultPolicy, @JsonProperty("whitelist") List<String> whiteList) {
        this.type = type;
        this.model_name = model_name;
        this.defaultPolicy = defaultPolicy;
        this.whiteList = whiteList;
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

    public Number getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Number timeStamp) {
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
