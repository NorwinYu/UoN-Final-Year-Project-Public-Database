package com.hasee.minibuslocalhost.transmit.Class;

import com.hasee.minibuslocalhost.activity.MainActivity;
import com.hasee.minibuslocalhost.bean.IntegerCommand;
import com.hasee.minibuslocalhost.util.ByteUtil;
import com.hasee.minibuslocalhost.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import static com.hasee.minibuslocalhost.util.ByteUtil.countBits;

public class BMS7 extends BaseClass {
    private static final String TAG = "BMS7";
    private HashMap<Integer, MyPair<Integer>> fields = new HashMap<Integer, MyPair<Integer>>() {{
        put(0, new MyPair<>(16, IntegerCommand.can_num_HVMaxTemp, MainActivity.SEND_TO_LOCALHOST));
        put(16, new MyPair<>(16, IntegerCommand.can_num_HVMinTemp, MainActivity.SEND_TO_LOCALHOST));
        put(48, new MyPair<>(16, IntegerCommand.can_num_PackAverageTemp, MainActivity.SEND_TO_LOCALHOST));
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
            case 16:
            case 48:
                return countBits(bytes, 0, index, 16, ByteUtil.Intel) * 0.1 - 40;
            default:
                LogUtil.d(TAG, "数据下标错误");
        }
        return null;
    }

    @Override
    public int getState() {
        return ByteUtil.Intel;
    }

    @Override
    public HashMap<Integer, MyPair<Integer>> getFields() {
        return fields;
    }
}
