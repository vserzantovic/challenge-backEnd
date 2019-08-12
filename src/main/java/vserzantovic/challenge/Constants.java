package vserzantovic.challenge;

public enum Constants {
    PROFILE_CREATE("profile_create"),
    PROFILE_UPDATE("profile_update"),
    REQUEST("request"),

    ALLOW("allow"),
    BLOCK("block"),
    QUARANTINE("quarantine");

    private String text;

    Constants(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
