package com.hasee.minibuslocalhost.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hasee.minibuslocalhost.R;
import com.hasee.minibuslocalhost.util.MyHandler;
import com.hasee.minibuslocalhost.util.SendToScreenThread;
import com.hasee.minibuslocalhost.util.ToastUtil;


/**
 * Created by fangju on 2018/12/15
 */
public class LoginActivity extends BaseActivity {
    private Context mContext;//上下文
    private int focuState = -1;//判断输入框焦点在哪个上面
    private EditText userNameEt;//用户名输入框
    private EditText passWordEt;//密码输入框
    private RelativeLayout number1Button;//数字1
    private RelativeLayout number2Button;//数字2
    private RelativeLayout number3Button;//数字3
    private RelativeLayout number4Button;//数字4
    private RelativeLayout number5Button;//数字5
    private RelativeLayout number6Button;//数字6
    private RelativeLayout number7Button;//数字7
    private RelativeLayout number8Button;//数字8
    private RelativeLayout number9Button;//数字9
    private RelativeLayout number0Button;//数字0
    private RelativeLayout backSpaceButton;//删除按钮
    private RelativeLayout submitButton;//确认按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        mContext = LoginActivity.this;
        //界面控件初始化
        viewInit();
    }

    /**
     * 界面初始化
     */
    @SuppressLint("WrongViewCast")
    private void viewInit() {
        userNameEt = (EditText)findViewById(R.id.username_et);
        userNameEt.setOnFocusChangeListener(onFocusChangeListener);
        passWordEt = (EditText)findViewById(R.id.password_et);
        //隐藏软键盘
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            userNameEt.setShowSoftInputOnFocus(false);
            passWordEt.setShowSoftInputOnFocus(false);
        }
        //取出本地密码
        String userInfo = App.getInstance().getPreferences();
        if(!TextUtils.isEmpty(userInfo)){
            JSONObject object = JSON.parseObject(userInfo);
            String userName = object.getString("userName");
            String passWord = object.getString("passWord");
            userNameEt.setText(userName);
            passWordEt.setText(passWord);
        }
        passWordEt.setOnFocusChangeListener(onFocusChangeListener);
        number0Button = (RelativeLayout)findViewById(R.id.imageButton_0);
        number0Button.setOnClickListener(onClickListener);
        number1Button = (RelativeLayout)findViewById(R.id.imageButton_1);
        number1Button.setOnClickListener(onClickListener);
        number2Button = (RelativeLayout)findViewById(R.id.imageButton_2);
        number2Button.setOnClickListener(onClickListener);
        number3Button = (RelativeLayout)findViewById(R.id.imageButton_3);
        number3Button.setOnClickListener(onClickListener);
        number4Button = (RelativeLayout)findViewById(R.id.imageButton_4);
        number4Button.setOnClickListener(onClickListener);
        number5Button = (RelativeLayout)findViewById(R.id.imageButton_5);
        number5Button.setOnClickListener(onClickListener);
        number6Button = (RelativeLayout)findViewById(R.id.imageButton_6);
        number6Button.setOnClickListener(onClickListener);
        number7Button = (RelativeLayout)findViewById(R.id.imageButton_7);
        number7Button.setOnClickListener(onClickListener);
        number8Button = (RelativeLayout)findViewById(R.id.imageButton_8);
        number8Button.setOnClickListener(onClickListener);
        number9Button = (RelativeLayout)findViewById(R.id.imageButton_9);
        number9Button.setOnClickListener(onClickListener);
        backSpaceButton = (RelativeLayout)findViewById(R.id.imageButton_backSpace);
        backSpaceButton.setOnClickListener(onClickListener);
        submitButton = (RelativeLayout)findViewById(R.id.imageButton_submit);
        submitButton.setOnClickListener(onClickListener);
    }

    /**
     * 点击事件监听器
     */
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageButton_0:{
                    appendText("0");
                    break;
                }
                case R.id.imageButton_1:{
                    appendText("1");
                    break;
                }
                case R.id.imageButton_2:{
                    appendText("2");
                    break;
                }
                case R.id.imageButton_3:{
                    appendText("3");
                    break;
                }
                case R.id.imageButton_4:{
                    appendText("4");
                    break;
                }
                case R.id.imageButton_5:{
                    appendText("5");
                    break;
                }
                case R.id.imageButton_6:{
                    appendText("6");
                    break;
                }
                case R.id.imageButton_7:{
                    appendText("7");
                    break;
                }
                case R.id.imageButton_8:{
                    appendText("8");
                    break;
                }
                case R.id.imageButton_9:{
                    appendText("9");
                    break;
                }
                case R.id.imageButton_backSpace:{
                    if(focuState == R.id.username_et){
                        userNameEt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DEL));
                    }else if(focuState == R.id.password_et){
                        passWordEt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DEL));
                    }
                    break;
                }
                case R.id.imageButton_submit:{
                    if(etIsEmpty()){
                        ToastUtil.getInstance(mContext).showShortToast(
                                getResources().getString(R.string.et_empty));
                    }else{//登陆
                        //保存密码至本地
                        JSONObject object = new JSONObject();
                        object.put("userName",userNameEt.getText().toString().trim());
                        object.put("passWord",passWordEt.getText().toString().trim());
                        App.getInstance().setPreferences(object.toJSONString());
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    break;
                }
            }
        }
    };

    /**
     * 输入框焦点监听
     */
    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(v.getId() == R.id.username_et){
                if(hasFocus){//判断焦点是否在用户名输入框
                    focuState = R.id.username_et;
                }else{
                    focuState = -1;
                }
            }else if(v.getId() == R.id.password_et){
                if(hasFocus){//判断焦点是否在密码输入框
                    focuState = R.id.password_et;
                }else{
                    focuState = -1;
                }
            }
        }
    };

    /**
     * 处理登陆信息
     */
    private MyHandler handler = new MyHandler(mContext){
        @Override
        public void handleMessage(Message msg) {
//            switch (){
//
//            }
        }
    };

    /**
     * 拼接文本
     * @param text
     */
    public void appendText(String text){
        if(focuState == R.id.username_et){
            userNameEt.append(text);
        }else if(focuState == R.id.password_et){
            passWordEt.append(text);
        }
    }

    /**
     * 判断输入框是否为空
     * @return
     */
    public boolean etIsEmpty(){
        if(TextUtils.isEmpty(userNameEt.getText().toString().trim())||
                TextUtils.isEmpty(passWordEt.getText().toString().trim())){
            return true;
        }
        return false;
    }
}
