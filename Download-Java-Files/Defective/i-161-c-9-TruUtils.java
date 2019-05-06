package com.trufla.androidtruforms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ohefny on 6/27/18.
 */

public class TruUtils {

    /**
     * Checks to see if EditText contains whitespace or no text
     *
     * @param et the EditText
     * @return true if EditText contains whitespace or no text otherwise false
     */
    public static boolean isEmpty(EditText et) {
        return TextUtils.isEmpty(et.getText().toString().trim());
    }

    /**
     * Checks to see if text is null or contains whitespace or no content
     *
     * @param charSequence
     * @return true if text contains whitespace or no content otherwise false
     */
    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.equals("");
    }

    /**
     * Get the EditText text trimmed
     *
     * @param et
     * @return the EditText text trimmed
     */
    public static String getText(EditText et) {
        return et.getText().toString().trim();
    }

    public static String getText(EditText et, String defaultText) {
        return isEmpty(et.getText()) ? defaultText : et.getText().toString();
    }

    public static String getText(String val, String defaultText) {
        if (val == null || val.equals(""))
            return defaultText;

        return val;
    }

    public static String removeUnderscoresAndCapitalize(String value) {
        if (!isEmpty(value)) {
            return value.replaceAll("_", " ");
        }
        return value;
    }

    public static String convertToData(long timeInMillis, String dateFormat) {
        Date date = new Date(timeInMillis);
        Format format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return format.format(date);
    }

    public static Activity getHostActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static String numberToString(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static boolean isNullOrEmpty(ArrayList list) {
        return list == null || list.isEmpty();
    }
    @NonNull
    public static String removeLastColon(String name) {
        if (name.length() > 0 && name.charAt(name.length() - 1) == ',') {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }
}
