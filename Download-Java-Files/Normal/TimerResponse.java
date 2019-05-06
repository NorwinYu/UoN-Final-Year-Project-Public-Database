package com.clocker.clocker.payload;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimerResponse {
    private Long id;
    private Date start_at;
    private Date end_at;
    private String title;
    private String start_date;
    private String start_time;
    private String end_time;
    private String difference;

    public TimerResponse(Long id, Date start_at, Date end_at, String title) {
        this.id = id;
        this.start_at = start_at;
        this.end_at = end_at;
        this.title = title;

        // Start date format
        Calendar cal = Calendar.getInstance();
        cal.setTime(start_at);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);
        this.start_date = String.format("%02d.%02d.%04d", day, month + 1, year);
        this.start_time = String.format("%02d:%02d:%02d", hour, minute, seconds);

        // End date format
        if (end_at != null) {
            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(end_at);
            int hourEnd = calEnd.get(Calendar.HOUR_OF_DAY);
            int minuteEnd = calEnd.get(Calendar.MINUTE);
            int secondsEnd = calEnd.get(Calendar.SECOND);
            this.end_time = String.format("%02d:%02d:%02d", hourEnd, minuteEnd, secondsEnd);

            Duration dur = Duration.between(cal.toInstant(), calEnd.toInstant());
            long millis = dur.toMillis();
            this.difference = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        }
    }


    public Date getStart_at() {
        return start_at;
    }

    public void setStart_at(Date start_at) {
        this.start_at = start_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEnd_at() {
        return end_at;
    }

    public void setEnd_at(Date end_at) {
        this.end_at = end_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }
}