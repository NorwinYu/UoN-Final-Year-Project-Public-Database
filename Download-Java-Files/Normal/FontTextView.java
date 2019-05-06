package com.hasee.minibuslocalhost.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class FontTextView extends TextView {
 
    public FontTextView(Context context) {
        this(context, null);
    }
 
    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
 
 
    //使用自定义的字体库
    private void init(Context context){
        AssetManager assetManager=context.getAssets();
        Typeface typeface=Typeface.createFromAsset(assetManager, "msyh.ttf");
        setTypeface(typeface);
    }
}
