package org.Twilio;

import jakarta.json.bind.annotation.JsonbProperty;

public class TwilioResponse {
    public TwilioResponse() {

    }

    @JsonbProperty("sid")
    private String sid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
