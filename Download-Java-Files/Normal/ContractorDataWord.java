package com.frictionhacks.tenderhaltinfo.DataWord;

public class ContractorDataWord {
    String mName;
    String mPhone;
    String mContractorID;

    public ContractorDataWord(String mName, String mPhone, String mContractorID) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.mContractorID = mContractorID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmContractorID() {
        return mContractorID;
    }

    public void setmContractorID(String mContractorID) {
        this.mContractorID = mContractorID;
    }
}
