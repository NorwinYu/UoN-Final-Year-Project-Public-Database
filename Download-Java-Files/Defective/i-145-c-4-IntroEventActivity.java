package com.msproject.myhome.mydays;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class IntroEventActivity extends AppCompatActivity {
    LinearLayout firstContent;
    LinearLayout secondContent;
    LinearLayout thirdContent;
    LinearLayout fourthContent;
    ImageView imageFirst;
    ImageView imageSecond;
    ImageView imageThird;
    ListView eventListView;
    ArrayList<Event> events = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_event);

        firstContent = findViewById(R.id.first_content);
        secondContent = findViewById(R.id.second_content);
        thirdContent = findViewById(R.id.third_content);
        fourthContent = findViewById(R.id.fourth_content);
        imageFirst = findViewById(R.id.mouse_img);
        imageSecond = findViewById(R.id.mouse_img2);
        imageThird = findViewById(R.id.mouse_img3);
        eventListView = findViewById(R.id.event_listview);
        final Animation animationClick = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mouse_focus);
        imageFirst.startAnimation(animationClick);
        final FloatingActionButton fab = findViewById(R.id.fab);
        final int[] location = new int[2];
        fab.getLocationOnScreen(location);
        Log.d("fabLocation==", location[0] + "," + location[1]);
        for (int i = 0; i < 6; i++) {
            events.add(new Event( i, "", ""));
        }//listView 생성
        EventListAdapter eventListAdapter = new EventListAdapter(events, this);
        eventListView.setAdapter(eventListAdapter);

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            //상단 바 색상 변경
            getWindow().setStatusBarColor(getColor(R.color.colorTitleBar));
        }

        firstContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFirst.clearAnimation();
                firstContent.setVisibility(View.GONE);
                secondContent.setVisibility(View.VISIBLE);
                secondContent.bringToFront();
                secondContent.getLocationOnScreen(location);
                Log.d("content==", location[0] + "," + location[1]);
                fab.getLocationOnScreen(location);
                Log.d("fabLocationif==", location[0] + "," + location[1]);
                imageSecond.setImageBitmap(rotateImage(
                        BitmapFactory.decodeResource(getResources(),
                                R.drawable.one_finger_mouse), 180));
                imageSecond.startAnimation(animationClick);
            }
        });

        secondContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSecond.clearAnimation();
                secondContent.setVisibility(View.GONE);
                thirdContent.setVisibility(View.VISIBLE);
                imageThird.startAnimation(animationClick);
            }
        });

        thirdContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageThird.clearAnimation();
                thirdContent.setVisibility(View.GONE);
                fourthContent.setVisibility(View.VISIBLE);
                Animation animationMove = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mouseup_event_item);
                animationMove.setRepeatCount(3);
                fourthContent.startAnimation(animationMove);
            }
        });

        fourthContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourthContent.clearAnimation();
                finish();
            }
        });


    }

    public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

}
