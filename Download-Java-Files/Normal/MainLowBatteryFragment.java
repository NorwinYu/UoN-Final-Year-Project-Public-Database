package com.hasee.minibuslocalhost.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasee.minibuslocalhost.R;


/**
 * 低电量报警
 */
public class MainLowBatteryFragment extends Fragment {


    public MainLowBatteryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_low_battery,container,false);
        return view;
    }

}
