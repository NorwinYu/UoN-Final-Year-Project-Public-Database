package com.hasee.minibuslocalhost.test;

import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hasee.minibuslocalhost.util.MyHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.hasee.minibuslocalhost.activity.MainActivity.SEND_TO_LOCALHOST;

/**
 * Created by fangju on 2019/1/25
 * 定时发送模拟数据（只模拟）
 */
public class TimerManager {
    private static final String TAG = "TimerManager";
    private MyHandler handler = null;
    private Timer timer = null;
    private ReschedulableTimerTask timerTask = null;
    private boolean isPause = false;//默认开启
    private boolean isStop = false;//默认开启
    private int delay = 0;
    private int period = 3000;
    private TouchTimer touchTimer = null;//触摸检测
    private List<JSONObject> jsons = new ArrayList<>();
    private int index = 0;

    public TimerManager(MyHandler handler) {
        this.handler = handler;
        initMsg();
    }

    private void initMsg() {
        String[] wangLeft = new String[]{
                "{\"id\":63,\"data\":0}", // 当前路线ID 1
//                1-0
                "{\"id\":64,\"data\":0}", // 下一站点ID 0
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
//                1-1
                "{\"id\":64,\"data\":1}", // 下一站点ID 1
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号
                // 1-2
                "{\"id\":64,\"data\":2}", // 下一站点ID 2
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号
                // 1-3
                "{\"id\":64,\"data\":3}", // 下一站点ID 3
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号
                // 1-4
                "{\"id\":64,\"data\":4}", // 下一站点ID 4
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号
                // 1-5
                "{\"id\":64,\"data\":5}", // 下一站点ID 5

                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号
                // 1-6
                "{\"id\":64,\"data\":6}", // 下一站点ID 6
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号

                // 2-5
                "{\"id\":64,\"data\":10}", // 下一站点ID 5
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号


                "{\"id\":63,\"data\":1}", // 当前路线ID 2
                // 2-0
                "{\"id\":64,\"data\":0}", // 下一站点ID 5
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                // 2-1
                "{\"id\":64,\"data\":1}", // 下一站点ID 1
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号
                // 2-2
                "{\"id\":64,\"data\":2}", // 下一站点ID 2
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号
                // 2-3
                "{\"id\":64,\"data\":3}", // 下一站点ID 3
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号
                // 2-4
                "{\"id\":64,\"data\":4}", // 下一站点ID 4
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                "{\"id\":76,\"data\":3}", // 起步信号
                // 2-5
                "{\"id\":64,\"data\":5}", // 下一站点ID 5
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
                // 2-5
                "{\"id\":64,\"data\":10}", // 下一站点ID 5
                "{\"id\":76,\"data\":2}", // 车到站信号
                "{\"id\":43,\"data\":3}", // 开车门信号
                "{\"id\":43,\"data\":0}", // 关车门信号
        };
        String[] wangRight = new String[]{
                "{\"id\":47,\"data\":3}", // 开门
                "{\"id\":100,\"data\":3}",
                "{\"id\":47,\"data\":0}", // 关门
                "{\"id\":200,\"data\":3}",
                "{\"id\":76,\"data\":3}", // 广告
                "{\"id\":300,\"data\":3}"
        };
        String[] qinFront = new String[]{
                //第一条路线
                "{\"id\":58,\"data\":0}", //当前行驶线路ID信号
                "{\"id\":68,\"data\":2}", //起始站出发提醒信号
                "{\"id\":66,\"data\":2}", //行人避让提醒信号
                "{\"id\":66,\"data\":3}", //行人避让结束
                "{\"id\":69,\"data\":2}", //到站提醒信号
                "{\"id\":69,\"data\":3}", //车辆出发信号
                "{\"id\":67,\"data\":2}", //紧急停车提醒信号
                "{\"id\":67,\"data\":3}", //紧急停车结束信号
                "{\"id\":69,\"data\":2}", //到达终点站
                //第二条路线
                "{\"id\":58,\"data\":1}", //当前行驶线路ID信号
                "{\"id\":68,\"data\":2}", //起始站出发提醒信号
                "{\"id\":66,\"data\":2}", //行人避让提醒信号
                "{\"id\":66,\"data\":3}", //行人避让结束
                "{\"id\":69,\"data\":2}", //到站提醒信号
                "{\"id\":69,\"data\":3}", //车辆出发信号
                "{\"id\":67,\"data\":2}", //紧急停车提醒信号
                "{\"id\":67,\"data\":3}", //紧急停车结束信号
                "{\"id\":69,\"data\":2}" //到达终点站
        };
        String[] me = new String[]{
            "{\"id\":77,\"data\":10}",//车速信号
            "{\"id\":77,\"data\":14}",//车速信号
            "{\"id\":85,\"data\":0}",//AD主控请求状态反馈
                "{\"id\":85,\"data\":1}",//AD主控请求状态反馈
                "{\"id\":85,\"data\":2}",//AD主控请求状态反馈

                "{\"id\":86,\"data\":0}",//RCU主控请求状态反馈
                "{\"id\":86,\"data\":1}",//RCU主控请求状态反馈
                "{\"id\":86,\"data\":2}",//RCU主控请求状态反馈
                "{\"id\":86,\"data\":3}",//RCU主控请求状态反馈


            "{\"id\":39,\"data\":20}",////剩余里程数
            "{\"id\":77,\"data\":16}",//车速信号
            "{\"id\":42,\"data\":50.0}",//电池包平均温度
            "{\"id\":77,\"data\":9}",//车速信号
            "{\"id\":39,\"data\":2}",//剩余里程数
            "{\"id\":77,\"data\":17}",//车速信号
            "{\"id\":34,\"data\":10}",//动力电池剩余电量SOC
            "{\"id\":77,\"data\":20}",//车速信号
            "{\"id\":34,\"data\":50}",//动力电池剩余电量SOC
             "{\"id\":42,\"data\":20.0}"//电池包平均温度

        };

        addMsg(me);
    }

    private void addMsg(String[] msgs) {
        for (int i = 0; i < msgs.length; i++) {
            JSONObject object = new JSONObject();
            object.put("target", SEND_TO_LOCALHOST);//发给谁
            object.put("delay", 3000);//延时
            JSONObject data = JSON.parseObject(msgs[i]);//发送的数据
            object.put("data", data);
            jsons.add(object);
        }
    }

    public void setPause(boolean pause) {
        isPause = pause;
        Log.d(TAG, "setPause: ");
    }

    public boolean isPause() {
        return isPause;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    /**
     * 定时任务开启
     */
    public void startTimer() {
        //
        touchTimer = new TouchTimer(this);
        touchTimer.startTimer();
        //
        isPause = true;
        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new ReschedulableTimerTask() {
                @Override
                public void run() {
                    if (index >= jsons.size()) {//索引值超过总大小
                        index = 0;
                    }
                    if (isPause) {//发送模拟数据
                        JSONObject object = jsons.get(index);
                        period = object.getIntValue("delay");//延时多长时间
                        int target = object.getIntValue("target");//发送给哪个;
                        JSONObject data = object.getJSONObject("data");//发送的数据
                        sendVirtualData(target, data);
                        timerTask.setPeriod(period);
                        Log.d(TAG, "发送命令后延时：" + period);
                        index++;
                    } else {//检测30s
//                        isPause = true;
//                        if(touchTimer.isStop()){
//                            touchTimer.startTimer();
//                            Log.d(TAG, "run: "+"jiance");
//                        }
                    }
                }
            };
        }
        if (timer != null && timerTask != null) {
            Log.d(TAG, "startTimer: ");
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
        if (touchTimer != null) {
            touchTimer.stopTimer();
        }
    }

    /**
     * 发送虚拟数据
     */
    private void sendVirtualData(int id, JSONObject data) {
//        Log.d(TAG, "sendVirtualData: ");
        //发送数据
        Message msg = handler.obtainMessage();
        msg.what = id;//发送给哪个
        msg.obj = data;//发送的数据
        handler.sendMessage(msg);

    }

    public abstract class ReschedulableTimerTask extends TimerTask {
        public void setPeriod(long period) {
            //缩短周期，执行频率就提高
            setDeclaredField(TimerTask.class, this, "period", period);
        }

        //通过反射修改字段的值
        boolean setDeclaredField(Class<?> clazz, Object obj,
                                 String name, Object value) {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                field.set(obj, value);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

}
