package com.hasee.minibuslocalhost.transmit.Class;

import com.hasee.minibuslocalhost.util.ByteUtil;
import com.hasee.minibuslocalhost.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_Alam;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_DangerAlarm;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_Demister_Control;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_DoorLock;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_Driver_model;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_FANPWM_Control;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_HighBeam;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_LeftTurningLamp;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_LowBeam;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_RearFogLamp;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_RightTurningLamp;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_SystemRuningStatus;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_TotalOdmeter;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_air_grade;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_air_model;
import static com.hasee.minibuslocalhost.bean.IntegerCommand.HMI_Dig_Ord_eBooster_Warning;
import static com.hasee.minibuslocalhost.util.ByteUtil.setBits;

public class HMI extends BaseClass {
    private final static String TAG = "HMI";
    private final static int offset = 4;

    // status
    // 灯光和门
    public static final int POINTLESS = 0; // 无意义
    public static final int OFF = 1; // 关
    public static final int ON = 2; // 开
    // 驾驶控制
    public static final int DRIVE_MODEL_AUTO = 0; // 自动
    public static final int DRIVE_MODEL_REMOTE = 1; // 远程
    public static final int DRIVE_MODEL_AUTO_AWAIT = 2; // 待定
    // 低速报警
    public static final int Ord_Alam_POINTLESS = 0; // 无输入
    public static final int Ord_Alam_OFF = 1; // 不报警
    public static final int Ord_Alam_ON = 2; // 报警
    // 空调
    public static final int AIR_MODEL_COOL = 0; // 制冷
    public static final int AIR_MODEL_HEAT = 1; // 制热
    public static final int AIR_MODEL_DEMIST = 2; // 除雾
    public static final int AIR_MODEL_AWAIT = 2; // 待定
    public static final int AIR_GRADE_OFF = 0; // 关闭
    public static final int AIR_GRADE_FIRST_GEAR = 1; // 1挡
    public static final int AIR_GRADE_SECOND_GEAR = 2; // 2挡
    public static final int AIR_GRADE_THIRD_GEAR = 3; // 3挡
    public static final int AIR_GRADE_FOURTH_GEAR = 4; // 4挡
    public static final int AIR_GRADE_FIVE_GEAR = 5; // 5挡
    public static final int AIR_GRADE_SIX_GEAR = 6; // 无输入
    // 制动液面报警
    public static final int eBooster_Warning_ON = 1; // 报警
    public static final int eBooster_Warning_OFF = 0; // 正常
    // HMI控制器运行状态
    public static final int Ord_SystemRuningStatus_ONINPUT = 0; // 无输入
    public static final int Ord_SystemRuningStatus_NORMAL = 1; // 正常
    public static final int Ord_SystemRuningStatus_ERROR = 2; // 故障
    public static final int Ord_SystemRuningStatus_AWAIT = 3; // 预留

    //
    private Map<String, ? super BaseClass> NAME_AND_CLASS;


    public HMI() {
    }

    public void setNAME_AND_CLASS(Map<String, ? super BaseClass> NAME_AND_CLASS) {
        this.NAME_AND_CLASS = NAME_AND_CLASS;
    }

    private byte[] bytes = {(byte) 0xFF, (byte) 0xAA, 0x03, (byte) 0x83, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x02};

    public void changeStatus(int command, Object status) {
        switch (command) {
            case HMI_Dig_Ord_HighBeam:
                setBits(bytes, (int) status, offset, 0, 2, ByteUtil.Motorola);
                if ((int) status != POINTLESS)
                    setBits(bytes, OFF, offset, 2, 2, ByteUtil.Motorola);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 4, (int) status == ON);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 5,  false);
                break;
            case HMI_Dig_Ord_LowBeam:
                setBits(bytes, (int) status, offset, 2, 2, ByteUtil.Motorola);
                if ((int) status != POINTLESS)
                    setBits(bytes, OFF, offset, 0, 2, ByteUtil.Motorola);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 5, (int) status == ON);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 4, false);
                break;
            case HMI_Dig_Ord_LeftTurningLamp:
                setBits(bytes, (int) status, offset, 4, 2, ByteUtil.Motorola);
                if ((int) status != POINTLESS)
                    setBits(bytes, OFF, offset, 6, 2, ByteUtil.Motorola);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 1, (int) status == ON);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 2, false);
                break;
            case HMI_Dig_Ord_RightTurningLamp:
                setBits(bytes, (int) status, offset, 6, 2, ByteUtil.Motorola);
                if ((int) status != POINTLESS)
                    setBits(bytes, OFF, offset, 4, 2, ByteUtil.Motorola);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 2, (int) status == ON);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 1, false);
                break;
            case HMI_Dig_Ord_RearFogLamp:
                setBits(bytes, (int) status, offset, 8, 2, ByteUtil.Motorola);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 6, (int) status == ON);
                break;
            case HMI_Dig_Ord_DoorLock:
                setBits(bytes, (int) status, offset, 10, 2, ByteUtil.Motorola);
                break;
            case HMI_Dig_Ord_Alam:
                setBits(bytes, (int) status, offset, 12, 2, ByteUtil.Motorola);
                break;
            case HMI_Dig_Ord_Driver_model:
                setBits(bytes, (int) status, offset, 14, 2, ByteUtil.Motorola);
                break;
            case HMI_Dig_Ord_air_model:
                setBits(bytes, (int) status, offset, 16, 2, ByteUtil.Motorola);
                break;
            case HMI_Dig_Ord_air_grade:
                setBits(bytes, (int) status, offset, 18, 3, ByteUtil.Motorola);
                break;
            case HMI_Dig_Ord_eBooster_Warning:
                setBits(bytes, (int) status, offset, 21, 1, ByteUtil.Motorola);
                break;
            case HMI_Dig_Ord_DangerAlarm:
                setBits(bytes, (int) status, offset, 22, 2, ByteUtil.Motorola);
//                ((BCM1) NAME_AND_CLASS.get("BCM1")).setBytes(0, 7, (int) status == ON);
                break;
            case HMI_Dig_Ord_FANPWM_Control:
                setBits(bytes, (int) status, offset, 24, 8, ByteUtil.Motorola);
                break;
            case HMI_Dig_Ord_Demister_Control:
                setBits(bytes, (int) status, offset, 38, 2, ByteUtil.Motorola);
                break;
            case HMI_Dig_Ord_TotalOdmeter:
                setBits(bytes, (int) status, offset, 48, 20, ByteUtil.Motorola);
                break;
            case HMI_Dig_Ord_SystemRuningStatus:
                setBits(bytes, (int) status, offset, 36, 2, ByteUtil.Motorola);
                break;
            default:
                LogUtil.d(TAG, "消息转换错误");
                break;
        }
    }

    @Override
    public int getState() {
        return ByteUtil.Motorola;
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public Object getValue(Map.Entry<Integer, MyPair<Integer>> entry, byte[] bytes) {
        return null;
    }

    @Override
    public HashMap<Integer, MyPair<Integer>> getFields() {
        return null;
    }
}
