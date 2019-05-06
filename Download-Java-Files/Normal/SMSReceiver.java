package com.stevensiahaan.smsretreiversample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    private static CommonListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle data = intent.getExtras();
        Object[] pdus = new Object[0];
        if (data != null) {
            pdus = (Object[]) data.get("pdus");
        }

        if (pdus != null) {
            for (Object pdu : pdus) { // loop through and pick up the SMS of interest
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                String messageBody = smsMessage.getMessageBody();

                if (mListener != null)
                    mListener.onOTPReceived(messageBody);
                break;
            }
        }
    }

    public static void bindListener(CommonListener listener) {
        mListener = listener;
    }

    public static void unbindListener() {
        mListener = null;
    }
}
