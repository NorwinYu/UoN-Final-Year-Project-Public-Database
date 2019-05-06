package com.clocker.clocker.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerUpdateEndTimeRequest {
    @NotBlank
    @Size(min = 1, max = 40)
    private String end_time;

    @NotBlank
    @Size(min = 1, max = 40)
    private String start_time;

    public Date getEnd_time() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); // Quoted "Z" to indicate UTC, no timezone offset

        try {
            return df.parse(end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Date getStart_time() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); // Quoted "Z" to indicate UTC, no timezone offset

        try {
            return df.parse(start_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}