package com.hasee.minibuslocalhost.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 处理can总线的消息
 * Created by fangju on 2018/12/8
 */
public class MyHandler extends Handler {
    private Context mContext;

    public MyHandler(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void handleMessage(Message msg) {

    }


}
