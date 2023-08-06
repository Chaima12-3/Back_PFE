package org.Twilio;

import jakarta.json.bind.annotation.JsonbProperty;

public class TwilioRequest {
    @JsonbProperty("To")
    private String to;

    @JsonbProperty("From")
    private String from;

    @JsonbProperty("Body")
    private String body;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public TwilioRequest(String to, String from, String body) {
        this.to = to;
        this.from = from;
        this.body = body;
    }

    public TwilioRequest() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
