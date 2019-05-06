package com.hasee.minibuslocalhost.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hasee.minibuslocalhost.R;

/**
 * Created by fangju on 2019/3/7
 */
public class LogFragment extends Fragment {
    public final int RECE = 1;
    public final int SEND = 2;
    private TextView receLogTextView;
    private TextView sendLogTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_fragment, container, false);
        receLogTextView = (TextView) view.findViewById(R.id.receLogTv);
        sendLogTextView = (TextView) view.findViewById(R.id.sendLogTv);
        return view;
    }

    /**
     * 拼接log
     *
     */
    public void appendLog(JSONObject object) {
        int flag = object.getIntValue("id");
        String log = object.getString("data");
        if(flag == RECE){
            receLogTextView.append(log + "\n");
        }else if(flag == SEND){
            sendLogTextView.setText(log);
        }
    }
}
