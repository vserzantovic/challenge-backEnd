package vserzantovic.chalenge;

public class Response {
    private String requestId;
    private String deviceId;
    private String action;

    public Response(String requestId, String deviceId, String action) {
        this.requestId = requestId;
        this.deviceId = deviceId;
        this.action = action;
    }

    public Response(String deviceId, String action) {
        this.deviceId = deviceId;
        this.action = action;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
