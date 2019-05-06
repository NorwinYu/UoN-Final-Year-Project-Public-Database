package com.clocker.clocker.payload;

import com.clocker.clocker.model.Timer;

import java.util.*;
import java.util.stream.Collectors;

public class UserTimers {
    private Set<Timer> timers;

    public UserTimers(Set<Timer> timers) {
        this.timers = timers;
    }

    public List<TimerResponse> getTimers() {
        List<Timer> sortedList = timers.stream().sorted(Comparator.comparing(Timer::getId).reversed()).collect(Collectors.toList());
        List<TimerResponse> responseTimers = new ArrayList<TimerResponse>();
        for (Timer timer:
                sortedList) {
            responseTimers.add(new TimerResponse(timer.getId(), timer.getStart_at(), timer.getEnd_at(), timer.getTitle()));
        }
        return responseTimers;
    }

    public void setTimers(Set<Timer> timers) {
        this.timers = timers;
    }
}