package com.hasee.minibuslocalhost.fragment;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hasee.minibuslocalhost.R;
import com.hasee.minibuslocalhost.activity.MainActivity;
import com.hasee.minibuslocalhost.util.LogUtil;

import static com.hasee.minibuslocalhost.bean.IntegerCommand.BCM_InsideTemp;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.BCM_OutsideTemp;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.Wheel_Speed_ABS;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.can_RemainKm;


/**
 * 右边Fragment（仪表盘）
 */
public class MainRightFragment2 extends Fragment {
    private static final String TAG = "MainRightFragment2";
    private MainActivity activity;
    private TextView rightFragment2BatteryTemperature;//温度
    private TextView rightFragment2Zonlic;//总里程
    private TextView rightFragment2Renwujd;//任务进度
    private TextView rightFragment2Pingjunss;//平均时速
    private TextView rightFragment2Speed;//速度
    private TextView driveInfo_tv;
    private double avgSpeed = 0;//平均速度
    private int speedCount = 0;//统计速度次数
    public MainRightFragment2(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_right2, container, false);
        rightFragment2BatteryTemperature = (TextView)view.findViewById(R.id.rightFragment2_batteryTemperature);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(),"font1.ttf");
        rightFragment2BatteryTemperature.setTypeface(typeface);
        rightFragment2Speed = (TextView)view.findViewById(R.id.rightFragment2_speed);
        rightFragment2Zonlic = (TextView)view.findViewById(R.id.rightFragment2_zonlic);
        rightFragment2Renwujd = (TextView)view.findViewById(R.id.rightFragment2_renwujd);
        rightFragment2Pingjunss = (TextView)view.findViewById(R.id.rightFragment2_pingjunss);
        driveInfo_tv = (TextView)view.findViewById(R.id.driveInfo_tv);
        driveInfo_tv.append("</script><script>");
        driveInfo_tv.append("\n");
        driveInfo_tv.append("$.getJSON(\"//ajax.ibaotu.com/?");
        driveInfo_tv.append("\n");
        driveInfo_tv.append("m=wenjuan&a=statusjson&name");
        driveInfo_tv.append("\n");
        driveInfo_tv.append("=rjjc&callback=?\", function(e) {");
        return view;
    }

    /**
     * 更新UI
     * @param object
     */
    public void refresh(JSONObject object){
        int id = object.getIntValue("id");
        if(id == Wheel_Speed_ABS){//车速
            int speed = (int) object.getDoubleValue("data");
            speedCount++;
//            avgSpeed = (int)calculate(speed,speedCount);
            rightFragment2Speed.setText(String.valueOf(speed));
//            rightFragment2Pingjunss.setText(String.valueOf(avgSpeed));
        }
        if(id == BCM_InsideTemp){//车内温度
            int data = object.getIntValue("data");
            rightFragment2BatteryTemperature.setText(data);
        }
        if(id == BCM_OutsideTemp){//车外温度

        }
        if(id == can_RemainKm){//剩余里程数
//            int data = (int) object.getDoubleValue("data");

        }
    }


    /**
     * 计算平均速度
     * @param speed
     * @param count
     * @return
     */
    private double calculate(int speed,int count){
        double newAvgSpeed = (avgSpeed*(count-1)+speed)/count;
//        LogUtil.d(TAG,String.valueOf(newAvgSpeed));
        return newAvgSpeed;
    }
}
