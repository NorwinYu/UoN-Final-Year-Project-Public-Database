package com.hasee.minibuslocalhost.transmit.Class;

import com.hasee.minibuslocalhost.activity.MainActivity;
import com.hasee.minibuslocalhost.bean.IntegerCommand;
import com.hasee.minibuslocalhost.util.ByteUtil;
import com.hasee.minibuslocalhost.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import static com.hasee.minibuslocalhost.util.ByteUtil.countBits;

public class EPS1 extends BaseClass {
    private static final String TAG = "EPS1";
    private HashMap<Integer, MyPair<Integer>> fields = new HashMap<Integer, MyPair<Integer>>() {{
        put(0, new MyPair<>(2, IntegerCommand.EPS_Dig_Alm_EPSWarning, MainActivity.SEND_TO_LOCALHOST)); // EPS警告灯
    }};
    private byte[] bytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};


    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void setBytes(byte[] bytes) {
        super.setBytes(bytes);
    }

    @Override
    public Object getValue(Map.Entry<Integer, MyPair<Integer>> entry, byte[] bytes) {
        int index = entry.getKey();
        switch (index) {
            case 0:
                return (int) countBits(bytes, 0, index, 2, ByteUtil.Motorola);
            default:
                LogUtil.d(TAG, "数据下标错误");
                break;
        }
        return null;
    }

    @Override
    public int getState() {
        return ByteUtil.Motorola;
    }

    @Override
    public HashMap<Integer, MyPair<Integer>> getFields() {
        return fields;
    }
}
