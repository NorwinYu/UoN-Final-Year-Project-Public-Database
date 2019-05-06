package com.msproject.myhome.mydays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyNotiControlActivity extends AppCompatActivity {
    TextView wiseTitle;
    TextView wiseWho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_noti_control);
//        String title = (String) getIntent().getStringExtra("title");
//        String who = getIntent().getStringExtra("who");
//
//        wiseTitle.setText(title);
//        wiseWho.setText(" - " + who);
        Intent intent = new Intent(MyNotiControlActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("restart", true);
//        MainActivity.restartNoti = true;
        startActivity(intent);
        finish();
    }
}
