package com.msproject.myhome.mydays;

import android.graphics.Color;

import java.util.HashMap;

public class ColorMakeHelper {
    private static HashMap<String, Integer> colors = new HashMap();

    public static Integer getColor(String category){
        if(category == null || category.length() == 0){
//            return Color.argb(50,0xBC,0xA9,0xF5);
//            return Color.argb(50,0x58,0xAC,0xFA);
            return colors.get("Default");
        }
        return colors.get(category);
    }

    public static void setColor(String category, Integer color){
        colors.put(category, color);
    }
}
