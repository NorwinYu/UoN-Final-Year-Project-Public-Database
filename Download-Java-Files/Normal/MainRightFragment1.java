package com.hasee.minibuslocalhost.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hasee.minibuslocalhost.R;
import com.hasee.minibuslocalhost.activity.MainActivity;

import static com.hasee.minibuslocalhost.transmit.Class.HMI.DRIVE_MODEL_AUTO;
import static com.hasee.minibuslocalhost.transmit.Class.HMI.DRIVE_MODEL_REMOTE;


/**
 * 右边Fragment（按钮）
 */
public class MainRightFragment1 extends Fragment {
    private Context mContext;
    private MainActivity activity;
    private Button rightFragment1AutoDrive;//自动驾驶
    private Button rightFragment1LongDrive;//远程驾驶
    private Button rightFragment1Await;//待机

    public MainRightFragment1(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_right1, container, false);
        rightFragment1AutoDrive = (Button) view.findViewById(R.id.rightFragment1_autoDrive);
        rightFragment1AutoDrive.setOnClickListener(onClickListener);
        rightFragment1LongDrive = (Button) view.findViewById(R.id.rightFragment1_longDrive);
        rightFragment1LongDrive.setOnClickListener(onClickListener);
        rightFragment1Await = (Button) view.findViewById(R.id.rightFragment1_await);
        rightFragment1Await.setOnClickListener(onClickListener);
        return view;
    }

    /**
     * 事件点击监听器
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rightFragment1_autoDrive:{//自动驾驶
                    activity.handleFragmentMsg(DRIVE_MODEL_AUTO);//自动
                    break;
                }
                case R.id.rightFragment1_longDrive:{//远程驾驶
                    activity.handleFragmentMsg(DRIVE_MODEL_REMOTE);
                    break;
                }
                case R.id.rightFragment1_await:{//待机
                    break;
                }
            }
        }
    };

}
