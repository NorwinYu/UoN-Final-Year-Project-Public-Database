package com.tribesbackend.tribes.services.timeservice;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class TimeService {

    public static long timeDifferenceInMin(Timestamp timestamp1, Timestamp timestamp2) {
        long milliseconds = timestamp2.getTime() - timestamp1.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(milliseconds);

    }
}
