package com.frictionhacks.tenderhaltinfo.DataWord;

public class NotificationWord {
private    String mTenderId;
private  String mNotificationStatus;

    public String getmTenderId() {
        return mTenderId;
    }

    public void setmTenderId(String mTenderId) {
        this.mTenderId = mTenderId;
    }

    public String getmNotificationStatus() {
        return mNotificationStatus;
    }

    public NotificationWord() {

    }

    public void setmNotificationStatus(String mNotificationStatus) {
        this.mNotificationStatus = mNotificationStatus;

    }

    public NotificationWord(String mTenderId, String mNotificationStatus) {
        this.mTenderId = mTenderId;
        this.mNotificationStatus = mNotificationStatus;
    }
}
