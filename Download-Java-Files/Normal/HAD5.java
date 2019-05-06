package com.hasee.minibuslocalhost.transmit.Class;

import com.hasee.minibuslocalhost.activity.MainActivity;
import com.hasee.minibuslocalhost.bean.IntegerCommand;
import com.hasee.minibuslocalhost.util.ByteUtil;
import com.hasee.minibuslocalhost.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import static com.hasee.minibuslocalhost.util.ByteUtil.*;

public class HAD5 extends BaseClass {
    private static final String TAG = "HAD5";
    private HashMap<Integer, MyPair<Integer>> fields = new HashMap<Integer, MyPair<Integer>>(){{
        put(0,new MyPair<>(8, IntegerCommand.HAD_CurrentDrivingRoadIDNum, MainActivity.SEND_TO_LOCALHOST)); // 档位位置;
        put(8,new MyPair<>(8, IntegerCommand.HAD_NextStationIDNumb, MainActivity.SEND_TO_LOCALHOST)); // 档位位置;
        put(24,new MyPair<>(16, IntegerCommand.HAD_DistanceForNextStation, MainActivity.SEND_TO_LOCALHOST)); // 档位位置;
        put(32,new MyPair<>(8, IntegerCommand.HAD_TimeForArrivingNextStation, MainActivity.SEND_TO_LOCALHOST)); // 档位位置;
        put(40,new MyPair<>(8, IntegerCommand.HAD_StartingSiteNum, MainActivity.SEND_TO_LOCALHOST)); // 档位位置;
        put(48,new MyPair<>(8, IntegerCommand.HAD_EndingSiteNum, MainActivity.SEND_TO_LOCALHOST)); // 档位位置;
        put(56,new MyPair<>(8, IntegerCommand.HAD_PassingBySiteNum, MainActivity.SEND_TO_LOCALHOST)); // 档位位置;
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
                return (int) countBits(bytes, 0, index, 8,ByteUtil.Motorola);
            case 8:
                return (int) countBits(bytes, 0, index, 8,ByteUtil.Motorola);
            case 24:
                return countBits(bytes, 0, index, 16,ByteUtil.Motorola) * 0.1;
            case 32:
            case 40:
            case 48:
            case 56:
                return (int) countBits(bytes, 0, index, 8,ByteUtil.Motorola);
            default:
                LogUtil.d(TAG, "数据下标错误");
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
