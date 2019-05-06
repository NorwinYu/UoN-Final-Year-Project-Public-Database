package com.hasee.minibuslocalhost.fragment;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hasee.minibuslocalhost.R;
import com.hasee.minibuslocalhost.activity.App;
import com.hasee.minibuslocalhost.activity.MainActivity;
import com.hasee.minibuslocalhost.util.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

import static com.hasee.minibuslocalhost.bean.IntegerCommand.BCM_InsideTemp;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.BCM_OutsideTemp;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_TotalOdmeter;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.Wheel_Speed_ABS;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.can_RemainKm;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.can_num_PackAverageTemp;


/**
 * 右边Fragment（仪表盘）
 */
public class MainRightFragment2 extends Fragment {
    private static final String TAG = "MainRightFragment2";
    private MainActivity activity;
    private TextView rightFragment2BatteryTemperature;//温度
    private TextView rightFragment2BatteryTemperature_e;//温度
    private TextView rightFragment2Zonlic;//总里程
    private TextView rightFragment2Renwujd;//任务进度
    private TextView rightFragment2Pingjunss;//平均时速
    private TextView rightFragment2Speed;//速度
    private TextView rightFragment2DriveInfo;//行驶参数
    private double avgSpeed = 0;//平均速度
    private int speedCount = 0;//统计速度次数
    private double totalMile = 0;//总里程
    private double newSpeed = 0;
    private double lastSpeed = 0;//上一次的速度
    private ReadSpeedTimer readSpeedTimer = null;
    private double totalRemainKm = 0;//任务进度总里程
    private boolean totalRemainKmFlag = false;//判断是否是第一次接收到总里程


    public MainRightFragment2() {
        JSONObject carInfo = JSON.parseObject(App.getInstance().getPreferences("carInfo"));
        if (carInfo != null) {
            totalMile = carInfo.getDouble("totalMile");
        }
        Log.d(TAG, "MainRightFragment2: ");
    }

    public ReadSpeedTimer getReadSpeedTimer() {
        if (readSpeedTimer == null) {
            readSpeedTimer = new ReadSpeedTimer();
        }
        return readSpeedTimer;
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
        rightFragment2BatteryTemperature = (TextView) view.findViewById(R.id.rightFragment2_batteryTemperature);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "font1.ttf");
        rightFragment2BatteryTemperature.setTypeface(typeface);
        rightFragment2BatteryTemperature_e = (TextView) view.findViewById(R.id.rightFragment2_batteryTemperature_e);
        rightFragment2Speed = (TextView) view.findViewById(R.id.rightFragment2_speed);
        rightFragment2Zonlic = (TextView) view.findViewById(R.id.rightFragment2_zonlic);
        rightFragment2Renwujd = (TextView) view.findViewById(R.id.rightFragment2_renwujd);
        rightFragment2Pingjunss = (TextView) view.findViewById(R.id.rightFragment2_pingjunss);
        rightFragment2DriveInfo = (TextView) view.findViewById(R.id.driveInfo_tv);
        rightFragment2Zonlic.setText(String.valueOf((int) totalMile));
//        showDriveInfo("");
        return view;
    }


    /**
     * 更新UI
     *
     * @param object
     */
    public void refresh(JSONObject object) {
        int id = object.getIntValue("id");
        if (id == Wheel_Speed_ABS) {//车速
            newSpeed = (int) object.getDoubleValue("data");//新速度
        }
        if (id == BCM_InsideTemp) {//车内温度

        }
        if (id == BCM_OutsideTemp) {//车外温度

        }
        if (id == can_RemainKm) {//剩余里程数
            int data = (int) object.getDoubleValue("data");
            if(totalRemainKmFlag){
                totalRemainKm = data;
                totalRemainKmFlag = false;
            }
            int d = (int) (((totalRemainKm - data) / totalRemainKm) * 100);
            rightFragment2Renwujd.setText(String.valueOf(d));
        }
        if (id == can_num_PackAverageTemp) {//电池包平均温度
            int data = (int) object.getDoubleValue("data");
            if (data > 40) {
                rightFragment2BatteryTemperature.setTextColor(
                        getResources().getColor(R.color.main_battery_color));
                rightFragment2BatteryTemperature_e.setTextColor(
                        getResources().getColor(R.color.main_battery_color));
            } else {
                rightFragment2BatteryTemperature.setTextColor(
                        getResources().getColor(R.color.right_fragment1_text_color));
                rightFragment2BatteryTemperature_e.setTextColor(
                        getResources().getColor(R.color.right_fragment1_text_color));
            }
            rightFragment2BatteryTemperature.setText(String.valueOf(data));
        }
    }

    /**
     * 计算总里程
     *
     * @param newSpeed
     * @return
     */
    private double calculateTotalMile(double newSpeed) {
        double totalMile = (lastSpeed + newSpeed) / (7200.0);
//        Log.d(TAG, "上一次速度: "+lastSpeed);
//        Log.d(TAG, "新速度: "+newSpeed);
//        Log.d(TAG, "计算的里程: "+totalMile);
        return totalMile;
    }

    /**
     * 计算平均速度
     * @param newSpeed
     * @return
     */
    private double calculateAvgSpeed(double newSpeed){
        double avgSpeed = (lastSpeed + newSpeed) / 2.0;
        return avgSpeed;
    }

    /**
     * 显示行驶参数
     */
    private void showDriveInfo(String message) {
//        rightFragment2DriveInfo.append("</script><script>");
//        rightFragment2DriveInfo.append("\n");
//        rightFragment2DriveInfo.append("$.getJSON(\"//ajax.ibaotu.com/?");
//        rightFragment2DriveInfo.append("\n");
//        rightFragment2DriveInfo.append("m=wenjuan&a=statusjson&name");
//        rightFragment2DriveInfo.append("\n");
//        rightFragment2DriveInfo.append("=rjjc&callback=?\", function(e) {");
    }

    public class ReadSpeedTimer {
        private Timer timer = null;
        private TimerTask timerTask = null;
        private int delay = 0;
        private int period = 1000;

        public ReadSpeedTimer() {
        }

        public void startTimer() {
            if (timer == null) {
                timer = new Timer();
            }
            if (timerTask == null) {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        //获取车辆的速度和总里程
                        Log.d(TAG, "run: "+newSpeed);
                        if(newSpeed != 0){
                            totalMile += calculateTotalMile(newSpeed);
                            lastSpeed = newSpeed;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rightFragment2Speed.setText(String.format("%.1f",newSpeed));
                                    rightFragment2Zonlic.setText(String.format("%.2f",totalMile));
                                    rightFragment2Pingjunss.setText(String.valueOf((int)calculateAvgSpeed(newSpeed)));
                                }
                            });
                            activity.sendToCAN("HMI", HMI_Dig_Ord_TotalOdmeter, (int) totalMile);
                            JSONObject carInfo = new JSONObject();
                            carInfo.put("totalMile", totalMile);
                            App.getInstance().setPreferences("carInfo", carInfo.toJSONString());
                            LogUtil.d(TAG, "总里程:" + totalMile);
                        }else{
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rightFragment2Speed.setText(String.valueOf((int) newSpeed));
                                }
                            });
                        }
                    }
                };
            }
            if (timer != null && timerTask != null) {
                timer.schedule(timerTask, delay, period);
            }
        }

        /**
         * 关闭定时器
         */
        public void stopTimer() {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
//            LogUtil.d(TAG, "stopTimer");
        }
    }
}
