package ru.virarnd.matdesigntrainingproject.ui.secondary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;


public class TestRectangle extends AppCompatImageView {

    private Paint paint;

    public TestRectangle(Context context) {
        super(context);
    }

    public TestRectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestRectangle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(20);
//        float leftx = 10;
//        float topy = 10;
//        float rightx = 290;
//        float bottomy = 290;
//        canvas.drawRect(leftx, topy, rightx, bottomy, paint);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        canvas.drawCircle(100, 100, 50, paint);

    }

    public void changeRectangleColor(int color) {
        paint.setColor(color);
    }
}
