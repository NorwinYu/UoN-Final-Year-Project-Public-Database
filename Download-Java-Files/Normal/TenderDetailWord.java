package com.frictionhacks.tenderhaltinfo.DataWord;

import com.google.type.LatLng;

public class TenderDetailWord {
    private String mtenderId;
    private double mLong;
    private double mLat;
    private String mDateStart;
    private String mDataEnd;
    private int mStatus;
    private String location;
    

    public TenderDetailWord() {
        super();
    }

    public TenderDetailWord(String mtenderId, double mLong, double mLat, String mDateStart, String mDataEnd, int mStatus, String location) {
        this.mtenderId = mtenderId;
        this.mLong = mLong;
        this.mLat = mLat;
        this.mDateStart = mDateStart;
        this.mDataEnd = mDataEnd;
        this.mStatus = mStatus;
        this.location = location;
    }

    public String getMtenderId() {
        return mtenderId;
    }

    public void setMtenderId(String mtenderId) {
        this.mtenderId = mtenderId;
    }

    public double getmLong() {
        return mLong;
    }

    public void setmLong(double mLong) {
        this.mLong = mLong;
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public String getmDateStart() {
        return mDateStart;
    }

    public void setmDateStart(String mDateStart) {
        this.mDateStart = mDateStart;
    }

    public String getmDataEnd() {
        return mDataEnd;
    }

    public void setmDataEnd(String mDataEnd) {
        this.mDataEnd = mDataEnd;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }
}
