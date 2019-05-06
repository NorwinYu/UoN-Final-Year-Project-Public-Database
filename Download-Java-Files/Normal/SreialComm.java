package android_serialport_api;


import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hasee.minibuslocalhost.activity.App;
import com.hasee.minibuslocalhost.util.LogUtil;
import com.hasee.minibuslocalhost.util.MyHandler;

public class SreialComm {
    private static final String TAG = "SreialComm";
    public static final int AUDIO_VOLUME = 1;
    public static final int LIGHT_NUM = 2;
    private SerialPortUtil serialPortUtil = null;
    private int m = 0;
    private int ptr[] = new int[3];
    private byte bt[] = new byte[3];
    JSONObject object;//发送的数据
    private MyHandler handler;//主线程

    public SreialComm(MyHandler handler) {
        this.handler = handler;
        //实例化串口
        serialPortUtil = SerialPortUtil.getInstance();
        LogUtil.d(TAG,"485监听打开");
    }

    public void receive() {
        serialPortUtil.openSerialPort();
        serialPortUtil.setSCMDataReceiveListener(new SCMDataReceiveListener() {
            @Override
            public void dataRecevie(byte[] data, int size) {
                m++;
                StringBuffer tString = new StringBuffer();
                for (int i = 0; i < size; i++) {
                    String s = Integer.toBinaryString((data[i] & 0xFF) + 0x100).substring(1);
                    tString.append(s);
                }
                if (m == 1) {
                    object = new JSONObject();
                    if (data[0] == 1) {
                        object.put("id",AUDIO_VOLUME);
                        ptr[0] = 1;
                    } else if (data[0] == 2) {
                        object.put("id",LIGHT_NUM);
                        ptr[0] = 2;
                    } else {
                        m = 0;
                    }
                }
                if (m == 2) {
                    if (data[0] >= 0 && data[0] <= 26) {
                        object.put("data",data[0]);
                        ptr[1] = data[0];
                    } else {
                        m = 0;
                    }
                }
                if (m == 3) {
                    ptr[2] = data[0];
                    int c = Crc.xCal_crc(ptr, 2);
                    if (c == ptr[2]) {
                        LogUtil.d(TAG,"校验正确！");
                        m = 0;
                        handler.post(runnable);//发送给主线程

                        bt[0] = (byte)ptr[0];
                        bt[1]=(byte)ptr[1];
                        bt[2]=(byte)ptr[2];
                        serialPortUtil.sendDataToSerialPort(bt);
                    } else {
                        m = 0;
                        LogUtil.d(TAG,"校验错误！");
                    }
                }
            }
        });

    }

    public void close() {
        if(serialPortUtil != null){
            serialPortUtil.closeSerialPort();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            App.getInstance().setAudioVolume(object);
        }
    };

}
