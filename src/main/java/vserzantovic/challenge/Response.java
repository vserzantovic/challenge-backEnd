package vserzantovic.challenge;

public class Response {
    private String requestId;
    private String deviceId;
    private String action;

    public Response(String requestId, String deviceId, String action) {
        if (action.equals(Constants.QUARANTINE.getText())) {
            this.deviceId = deviceId;
        } else {
            this.requestId = requestId;
        }
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

    @Override
    public String toString(){
        if(action.equals(Constants.QUARANTINE.getText())) {
            return "device_id: "+deviceId+", action :" +action;
        } else {
            return "request_id: "+requestId+", action :" +action;
        }

    }
}
