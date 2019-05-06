package ru.virarnd.matdesigntrainingproject;

import androidx.appcompat.app.AppCompatActivity;
import ru.virarnd.matdesigntrainingproject.ui.secondary.TestRectangle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Objects;

public class ColorThemeActivity extends AppCompatActivity {

    private ImageView rectangle1;
    private ImageView rectangle2;

    private Button btnTheme1;
    private Button btnTheme2;

    private MyClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_theme);

        rectangle1 = findViewById(R.id.iv1);
        rectangle2 = findViewById(R.id.iv2);
        btnTheme1 = findViewById(R.id.buttonStyle1);
        btnTheme2 = findViewById(R.id.buttonStyle2);


        Bitmap bitMap1 = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        bitMap1 = bitMap1.copy(bitMap1.getConfig(), true);
        Canvas canvas = new Canvas(bitMap1);
        Paint paint1 = new Paint();
        paint1.setColor(getResources().getColor(R.color.colorAccent1));
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);
        rectangle1.setImageBitmap(bitMap1);
        rectangle1.setBackgroundResource(R.drawable.rounded_btn);
        canvas.drawCircle(50, 50, 50, paint1);
        rectangle1.invalidate();

        Bitmap bitMap2 = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        bitMap2 = bitMap2.copy(bitMap2.getConfig(), true);
        canvas = new Canvas(bitMap2);
        Paint paint2 = new Paint();
        paint2.setColor(getResources().getColor(R.color.colorAccent2));
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setAntiAlias(true);
        rectangle2.setImageBitmap(bitMap2);
        rectangle2.setBackgroundResource(R.drawable.rounded_btn);
        canvas.drawCircle(50, 50, 50, paint2);
        rectangle2.invalidate();



//        int border = 10;
//        rectangle1.setPadding(2 * border, 2 * border, 0, 0);
//        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(100, 100);
//        params.leftMargin = 10 + border;
//        params.topMargin = 10 + border;
//        rectangle1.setBackgroundDrawable();
//        rectangle1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark1));
//        rectangle1.refreshDrawableState();
//        addView(imageView, params);
//        GradientDrawable rect1Shape = (GradientDrawable) rectangle1.getBackground();
//        rect1Shape.setColor(this.getResources().getColor(R.color.colorPrimary1));
//        rect1Shape.setStroke(10, getResources().getColor(R.color.colorPrimaryDark1));



        listener = new MyClickListener();
        btnTheme1.setOnClickListener(listener);
        btnTheme2.setOnClickListener(listener);


    }

    private class MyClickListener implements View.OnClickListener {
        int answer;
        @Override
        public void onClick(View v) {
            switch ((v.getId())) {
                case R.id.buttonStyle1:
                    answer = 1;
                     break;
                case R.id.buttonStyle2:
                    answer = 2;
                    break;
            }
            Intent intent = new Intent();
            intent.putExtra("theme_number", answer);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
