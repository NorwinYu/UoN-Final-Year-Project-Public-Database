package com.frictionhacks.tenderhaltinfo.DataModel;


import com.frictionhacks.tenderhaltinfo.DataWord.TenderDetailWord;

import java.util.ArrayList;

public class ContractorTenderDetailsDashboardModel {

    public static ArrayList<TenderDetailWord> mData;

    public ContractorTenderDetailsDashboardModel() {
        mData=new ArrayList<>();

    }

    static public void init(){
    mData=new ArrayList<>();


    }


}
