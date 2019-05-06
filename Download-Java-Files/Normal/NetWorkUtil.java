package com.hasee.minibuslocalhost.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by fangju on 2018/12/23
 */
public class NetWorkUtil {
    private Context mContext;//上下文
    private static NetWorkUtil instance = null;
    private ConnectivityManager connectivityManager;//用于判断是否有网络
    private NetworkInfo info;//网络信息

    private NetWorkUtil(Context mContext){
        //私有构造函数
        connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetWorkUtil getInstance(Context mContext) {
        if(instance == null){
            instance = new NetWorkUtil(mContext);
        }
        return instance;
    }

    /**
     * 判断网络是否可用
     * @return
     */
    public boolean isAvailable(){
        info = connectivityManager.getActiveNetworkInfo();//网络的连接信息
        if(info == null){//网络没有连接
            return false;
        }
        return true;
    }
}
