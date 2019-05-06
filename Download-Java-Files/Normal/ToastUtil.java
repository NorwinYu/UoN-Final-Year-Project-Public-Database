package com.hasee.minibuslocalhost.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by fangju on 2018/11/23
 */
public class ToastUtil {
    private static ToastUtil mToastUtil;
    private Toast mToast;

    private ToastUtil(Context mContext) {
        if(mToast == null){
            mToast = Toast.makeText(mContext,"",Toast.LENGTH_SHORT);
        }
    }

    public static ToastUtil getInstance(Context mContext) {
        if(mToastUtil == null){
            mToastUtil = new ToastUtil(mContext.getApplicationContext());
        }
        return mToastUtil;
    }

    public void showShortToast(String message){
        if(mToast == null){
            return ;
        }
        mToast.setText(message);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showLengthToast(String message){
        if(mToast == null){
            return ;
        }
        mToast.setText(message);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }
}
