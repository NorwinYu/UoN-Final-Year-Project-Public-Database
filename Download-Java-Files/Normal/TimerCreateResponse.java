package com.clocker.clocker.payload;

import java.util.Date;

public class TimerCreateResponse {
    private Boolean success;
    private String message;
    private Date start_at;

    public TimerCreateResponse(Boolean success, String message, Date start_at) {
        this.success = success;
        this.message = message;
        this.start_at = start_at;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getStart_at() {
        return start_at;
    }

    public void setStart_at(Date start_at) {
        this.start_at = start_at;
    }
}