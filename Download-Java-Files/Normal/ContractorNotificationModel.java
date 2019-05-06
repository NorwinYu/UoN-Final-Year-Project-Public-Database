package com.frictionhacks.tenderhaltinfo.DataModel;

import com.frictionhacks.tenderhaltinfo.DataWord.NotificationWord;
import com.frictionhacks.tenderhaltinfo.DataWord.TenderDetailWord;

import java.util.ArrayList;

public class ContractorNotificationModel {


    public static ArrayList<NotificationWord> mData;

    public ContractorNotificationModel() {
        mData=new ArrayList<>();


    }

    static public void init(){
        mData=new ArrayList<>();

    }
}
