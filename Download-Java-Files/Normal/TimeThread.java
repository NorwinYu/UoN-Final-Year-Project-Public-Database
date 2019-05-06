package com.hasee.minibuslocalhost.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fangju on 2018/12/23
 * 时间线程
 */
public class TimeThread extends Thread {
    private static final String TAG = "TimeThread";
    private final int TIME_FLAG_LOCAL = 1;//本地时间
    private final int TIME_FLAG_NET = 2;//网络时间
    private boolean flag = true;//默认开启线程
    private Context mContext;//上下文
    private TextView timeTextView;//时间文本

    public TimeThread(Context mContext,TextView textView){
        this.mContext = mContext;
        this.timeTextView = textView;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    //更新时间
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TIME_FLAG_NET:{//更新UI
                    timeTextView.setText(getNetTime());
                    break;
                }
                case TIME_FLAG_LOCAL:{//更新UI
                    timeTextView.setText(getLocalTime());
                    break;
                }
            }
        }
    };

    @Override
    public void run() {
        while (flag){
            try {
//                if(NetWorkUtil.getInstance(mContext).isAvailable()){//获取网络时间
//                    sendText(TIME_FLAG_NET);
//                }else{
//                    sendText(TIME_FLAG_LOCAL);
//                }
                sendText(TIME_FLAG_LOCAL);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送更新时间命令给UI线程
     */
    private void sendText(int what){
        Message msg = handler.obtainMessage();
        msg.what = what;
        handler.sendMessage(msg);
    }

    /**
     * 获取本地时间
     * @return
     */
    private String getLocalTime(){
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获取网络时间
     * @return
     */
    private String getNetTime(){
        URL url = null;
        try {
            url = new URL("http://www.bjtime.cn");
            URLConnection connection = url.openConnection();
            connection.connect();//建立连接
            long time = connection.getDate();//取得网站日期时间
            Date date = new Date(time);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//            LogUtil.d(TAG,format.format(date));
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
