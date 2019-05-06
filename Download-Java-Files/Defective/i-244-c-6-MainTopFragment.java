package com.hasee.minibuslocalhost.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hasee.minibuslocalhost.R;
import com.hasee.minibuslocalhost.activity.MainActivity;
import com.hasee.minibuslocalhost.util.TimeThread;
import com.hasee.minibuslocalhost.view.BatteryView;

import static com.hasee.minibuslocalhost.bean.IntegerCommand.BMS_SOC;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HAD_GPSPositioningStatus;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.OBU_LocalTime;


/**
 * 上部分Fragment
 */
public class MainTopFragment extends Fragment {
    private MainActivity activity;
    private TextView topFragmentTime;//时间
    private TextView topFragmentBatteryTv;//电池文字信息
    private BatteryView topFragmentBatteryImg;//
    private TimeThread timeThread;//时间线程

    public MainTopFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_top,container,false);
        topFragmentTime = (TextView)view.findViewById(R.id.topFragment_time);
        topFragmentBatteryTv = (TextView)view.findViewById(R.id.topFragment_battery_tv);
        topFragmentBatteryImg = (BatteryView)view.findViewById(R.id.topFragment_battery_img);
        topFragmentBatteryImg.setPower(100);//默认
//        timeThread = new TimeThread(activity,topFragmentTime);
//        timeThread.start();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        timeThread.setFlag(false);
    }

    /**
     * 更新界面
     */
    public void refresh(JSONObject object){
        int id = object.getIntValue("id");
        if(id == OBU_LocalTime){//本地时间
            JSONObject data = object.getJSONObject("data");
            topFragmentTime.setText(getTime(data));
        }
        if(id == BMS_SOC){//动力电池剩余电量SOC
            //电量显示
            int battryNum = (int) object.getDoubleValue("data");
            topFragmentBatteryTv.setText(String.valueOf(battryNum)+"%");
            topFragmentBatteryImg.setPower(battryNum);
        }
        if(id == HAD_GPSPositioningStatus){//GPS状态

        }
    }

    /**
     * int转换成时间
     */
    private String getTime(JSONObject object){
        StringBuffer stringBuffer = new StringBuffer();
        int hour = object.getIntValue("hour");
        int minute = object.getIntValue("minute");
        int second = object.getIntValue("second");
        stringBuffer.append(hour);
        stringBuffer.append(":");
        stringBuffer.append(minute);
        stringBuffer.append(":");
        stringBuffer.append(second);
        return stringBuffer.toString();
    }
}
