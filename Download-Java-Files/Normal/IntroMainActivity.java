package com.msproject.myhome.mydays;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroMainActivity extends AppCompatActivity {
    int count;
    ImageView imageFirst;
    TextView textFirst;
    ImageView imageSecond;
    TextView textSecond;
    ImageView imageThird;
    TextView textThird;
    ImageView imageFourth;
    TextView textFourth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_main);

        imageFirst = findViewById(R.id.mouse_img);
        textFirst = findViewById(R.id.intro_text);
        imageSecond = findViewById(R.id.mouse_img2);
        textSecond = findViewById(R.id.intro_text2);
        imageThird = findViewById(R.id.mouse_img3);
        textThird = findViewById(R.id.intro_text3);
        imageFourth = findViewById(R.id.mouse_img4);
        textFourth = findViewById(R.id.intro_text4);
        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mouse_focus);
        imageFirst.startAnimation(animation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.colorTitleBar));
        }


        imageFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFirst.clearAnimation();
                imageFirst.setVisibility(View.GONE);
                textFirst.setVisibility(View.GONE);
                imageSecond.setVisibility(View.VISIBLE);
                textSecond.setVisibility(View.VISIBLE);
                imageSecond.startAnimation(animation);

            }
        });

        imageSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSecond.clearAnimation();
                imageSecond.setVisibility(View.GONE);
                textSecond.setVisibility(View.GONE);
                imageThird.setVisibility(View.VISIBLE);
                textThird.setVisibility(View.VISIBLE);
                imageThird.startAnimation(animation);
            }
        });

        imageThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageThird.clearAnimation();
                imageThird.setVisibility(View.GONE);
                textThird.setVisibility(View.GONE);
                imageFourth.setVisibility(View.VISIBLE);
                textFourth.setVisibility(View.VISIBLE);
                imageFourth.setImageBitmap(rotateImage(
                        BitmapFactory.decodeResource(getResources(),
                                R.drawable.one_finger_mouse), 180));
                imageFourth.startAnimation(animation);
            }
        });

        imageFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFourth.clearAnimation();
                finish();
            }
        });
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
