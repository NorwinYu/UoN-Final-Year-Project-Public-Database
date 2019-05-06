package com.hasee.minibuslocalhost.transmit;

import android.os.Message;
import android.util.Pair;

import com.alibaba.fastjson.JSONObject;
import com.hasee.minibuslocalhost.transmit.Class.AD1;
import com.hasee.minibuslocalhost.transmit.Class.AD4;
import com.hasee.minibuslocalhost.transmit.Class.AD_FeedBack;
import com.hasee.minibuslocalhost.transmit.Class.BCM1;
import com.hasee.minibuslocalhost.transmit.Class.BMS1;
import com.hasee.minibuslocalhost.transmit.Class.BMS7;
import com.hasee.minibuslocalhost.transmit.Class.BaseClass;
import com.hasee.minibuslocalhost.transmit.Class.EPB1;
import com.hasee.minibuslocalhost.transmit.Class.EPS1;
import com.hasee.minibuslocalhost.transmit.Class.ESC2;
import com.hasee.minibuslocalhost.transmit.Class.ESC3;
import com.hasee.minibuslocalhost.transmit.Class.HAD5;
import com.hasee.minibuslocalhost.transmit.Class.HAD6;
import com.hasee.minibuslocalhost.transmit.Class.HMI;
import com.hasee.minibuslocalhost.transmit.Class.MCU1;
import com.hasee.minibuslocalhost.transmit.Class.OBU2;
import com.hasee.minibuslocalhost.transmit.Class.OBU5;
import com.hasee.minibuslocalhost.transmit.Class.PCGL1;
import com.hasee.minibuslocalhost.transmit.Class.PCGR1;
import com.hasee.minibuslocalhost.transmit.Class.RCU1;
import com.hasee.minibuslocalhost.transmit.Class.RCU_FeedBack;
import com.hasee.minibuslocalhost.transmit.Class.VCU2;
import com.hasee.minibuslocalhost.transmit.Class.VCU3;
import com.hasee.minibuslocalhost.transmit.Class.VCU4;
import com.hasee.minibuslocalhost.util.LogUtil;
import com.hasee.minibuslocalhost.util.MyHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import static com.hasee.minibuslocalhost.util.ByteUtil.bytesToHex;
import static com.hasee.minibuslocalhost.util.ByteUtil.subBytes;

public class Transmit {
    private final static String TAG = "Transmit";
    private final static int MESSAGELENGTH = 14;
    private final int PORT = 4001;   // port号
    private final static String IP = "192.168.1.60"; // ip地址
    private final static Transmit instance = new Transmit();
    private MyHandler handler;
    private LinkedBlockingQueue<Pair<Pair<byte[],byte[]>, Long>> sendQueue = new LinkedBlockingQueue<>();
    private boolean threadFlag = true; // 接收线程是否关闭

    public static void main(String[] args) {
        new Transmit();
    }

    private Transmit() {
        init();
    }


    // 主机发送数据给CAN总线
    public void hostToCAN(String clazz, int field, Object o) {
        BaseClass baseClass = (BaseClass) NAME_AND_CLASS.get(clazz);
        if (baseClass == null) {
            LogUtil.d(TAG, "类转换错误");
            return;
        }
        if (baseClass instanceof HMI)
            ((HMI) baseClass).changeStatus(field, o);
        byte[] bytes_1 = baseClass.getBytes();
        LogUtil.d(TAG, "hostToCan" + bytesToHex(bytes_1));
        if (baseClass instanceof HMI)
            ((HMI) baseClass).changeStatus(0, o);
        byte[] bytes_2 = baseClass.getBytes();
        try {
            sendQueue.put(new Pair<>(new Pair<>(bytes_1, bytes_2), (long) 0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 发送队列线程
    private class StartSend implements Runnable{

        @Override
        public void run() {
            try {
                while (threadFlag) {
                    Pair<Pair<byte[], byte[]>, Long> tmp = sendQueue.take();
                    for (int i = 0; i < 5; i++) {
                        UDP_send(tmp.first.first);
                        Thread.sleep(tmp.second);
                    }
                    UDP_send(tmp.first.second);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LogUtil.d(TAG, "发送队列退出");
        }
    }

    // 车辆初始化
    public void  Can_init(Object obj){
        HMI HMI_Class = (HMI) NAME_AND_CLASS.get("HMI");
        Map<Integer,Integer> map = (Map<Integer, Integer>) obj;
        for (Map.Entry<Integer, Integer> it : map.entrySet()) {
            int field = it.getKey();
            Object o = it.getValue();
            HMI_Class.changeStatus(field, o);
        }
        byte[] bytes = HMI_Class.getBytes();
        LogUtil.d(TAG, bytesToHex(bytes));
        UDP_send(bytes);
    }

    public void setHandler(MyHandler handler) {
        this.handler = handler;
        LogUtil.d(TAG, "setHandler");
        new Thread(new StartSend()).start(); // 开启发送线程
        UDP_receive(); // 开启接收线程
    }

    public static Transmit getInstance() {
        return instance;
    }

    // 以下为私有方法，对外部是隐藏的
    // 回调
    // jsonObject:{'id':1, 'data':[1,1,...], 'target':1}
    public void callback(JSONObject jsonObject, int target) {
        //发给hmi
//        int id = jsonObject.getIntValue("id");
//        switch (id) {
//            case BCM_Flg_Stat_HighBeam:
//                ((HMI) NAME_AND_CLASS.get("HMI")).changeStatus(HMI_Dig_Ord_HighBeam, jsonObject.getInteger("data"));
//                break;
//            case BCM_Flg_Stat_LowBeam:
//                ((HMI) NAME_AND_CLASS.get("HMI")).changeStatus(HMI_Dig_Ord_LowBeam, jsonObject.getInteger("data"));
//                break;
//            case BCM_Flg_Stat_LeftTurningLamp:
//                ((HMI) NAME_AND_CLASS.get("HMI")).changeStatus(HMI_Dig_Ord_LeftTurningLamp, jsonObject.getInteger("data"));
//                break;
//            case BCM_Flg_Stat_RightTurningLamp:
//                ((HMI) NAME_AND_CLASS.get("HMI")).changeStatus(HMI_Dig_Ord_RightTurningLamp, jsonObject.getInteger("data"));
//                break;
//            case BCM_Flg_Stat_RearFogLamp:
//                ((HMI) NAME_AND_CLASS.get("HMI")).changeStatus(HMI_Dig_Ord_RearFogLamp, jsonObject.getInteger("data"));
//                break;
//            case BCM_Flg_Stat_DangerAlarmLamp:
//                ((HMI) NAME_AND_CLASS.get("HMI")).changeStatus(HMI_Dig_Ord_DangerAlarm, jsonObject.getInteger("data"));
//                break;
//            default:
//                break;
//        }
        //通过message 发给ui
        Message msg = handler.obtainMessage();
        msg.what = target;
        msg.obj = jsonObject;
        handler.sendMessage(msg);
    }

    // 接收CAN总线
    private void UDP_receive() {
        byte[] receMsgs = new byte[MESSAGELENGTH];
        DatagramSocket datagramSocket = null;
        DatagramPacket datagramPacket;
        try {
            datagramSocket = new DatagramSocket(PORT);
            while (true) {
                datagramPacket = new DatagramPacket(receMsgs, receMsgs.length);
                datagramSocket.receive(datagramPacket);
                LogUtil.d(TAG, bytesToHex(receMsgs));
                dispose(datagramPacket.getData());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭socket
            if (datagramSocket != null)
                datagramSocket.close();
            threadFlag = false;
            sendQueue = null;
        }
    }

    // 发到CAN总线
    private void UDP_send(final byte[] sendMsgs) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket datagramSocket = null;
                DatagramPacket datagramPacket;
                try {
                    datagramSocket = new DatagramSocket();
                    datagramPacket = new DatagramPacket(sendMsgs, sendMsgs.length, InetAddress.getByName(IP), PORT);
                    datagramSocket.send(datagramPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // 关闭socket
                    if (datagramSocket != null) {
                        datagramSocket.close();
                    }
                }
            }
        }).start();
    }

    // 消息标识符
    private ArrayList<Pair<String, ? extends BaseClass>> list = new ArrayList<>(Arrays.asList(
            new Pair<>("00000220", new VCU2()),
            new Pair<>("000003e1", new EPB1()),
            new Pair<>("00000300", new MCU1()),
            new Pair<>("00000373", new EPS1()),
            new Pair<>("00000361", new BCM1()),
            new Pair<>("00000219", new AD1()),
            new Pair<>("000004cf", new AD4()),
            new Pair<>("00000227", new ESC2()),
            new Pair<>("000004c0", new ESC3()),
            new Pair<>("00000331", new PCGL1()),
            new Pair<>("00000333", new PCGR1()),
            new Pair<>("00000383", new HMI()),
            new Pair<>("00000219", new RCU1()),
            new Pair<>("00000234", new OBU2()),
            new Pair<>("00000235", new OBU5()),
            new Pair<>("00000236", new HAD5()),
            new Pair<>("00000237", new HAD6()),
            new Pair<>("00000260", new BMS1()),
            new Pair<>("00000420", new VCU3()),
            new Pair<>("00000421", new VCU4()),
            new Pair<>("00000465", new BMS7()),
            new Pair<>("00000081", new AD_FeedBack()),
            new Pair<>("00000080", new RCU_FeedBack())
    ));
    // 消息标识符键值对，方便查找
    private Map<String, ? super BaseClass> FLAG_AND_CLASS = new HashMap<>();
    private Map<String, ? super BaseClass> NAME_AND_CLASS = new HashMap<>();

    // 初始化
    private void init() {
        for (Pair<String, ? extends BaseClass> pair : list) {
            FLAG_AND_CLASS.put(pair.first, pair.second);
            NAME_AND_CLASS.put(pair.second.getClass().getSimpleName(), pair.second);
        }
        ((HMI) NAME_AND_CLASS.get("HMI")).setNAME_AND_CLASS(NAME_AND_CLASS);
    }

    // 处理收到的byte数组
    private void dispose(byte[] receMsgs) {
        String key;
        String check;
        key = bytesToHex(subBytes(receMsgs, 10, 4));
        check = bytesToHex(subBytes(receMsgs, 0, 2));
        LogUtil.d(TAG, "dispose->bytes:" + bytesToHex(receMsgs));
        LogUtil.d(TAG, "key:" + key);
        LogUtil.d(TAG, "check:" + check);
        if(!check.equals("aabb")){
            return;
        }
        try {
            if (FLAG_AND_CLASS.containsKey(key))
                ((BaseClass) FLAG_AND_CLASS.get(key)).setBytes(subBytes(receMsgs, 2, 8));
            else
                LogUtil.d(TAG, "未找到消息表示符");
        }catch (NullPointerException e){
            LogUtil.d(TAG, "消息流方向错误");
        }
    }
}
