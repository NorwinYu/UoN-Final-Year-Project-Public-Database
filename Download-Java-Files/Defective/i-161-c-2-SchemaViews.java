package com.trufla.androidtruforms;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.trufla.androidtruforms.exceptions.UnableToParseSchemaException;

public class SchemaViews {
    public static TruFormFragment newSchemaFragmentInstance(String schemaString, Context context) throws UnableToParseSchemaException {
        TruFormFragment formFragment = TruFormFragment.newInstance(schemaString.toString());
        return formFragment;
    }

    public static TruFormFragment newSchemaFragmentWithConstValuesInstance(String schemaString, String jsonValue, Context context) throws UnableToParseSchemaException {
        TruFormFragment formFragment = TruFormFragment.newInstanceWithConstJson(schemaString.toString(), jsonValue);
        return formFragment;
    }

    public static <T extends Activity & TruFormFragment.OnFormActionsListener> void showFragment(String schemaString, T hostActivity, FragmentManager fragmentManager, @IdRes int containerViewId) throws UnableToParseSchemaException {
        TruFormFragment formFragment = newSchemaFragmentInstance(schemaString, hostActivity);
        fragmentManager.beginTransaction().replace(containerViewId, formFragment, TruFormFragment.FRAGMENT_TAG).commit();
    }

    public static <T extends Activity & TruFormFragment.OnFormActionsListener> void showFragmentWithConstValue(String schemaString, String jsonValues, T hostActivity, FragmentManager fragmentManager, @IdRes int containerViewId) throws UnableToParseSchemaException {
        TruFormFragment formFragment = newSchemaFragmentWithConstValuesInstance(schemaString, jsonValues, hostActivity);
        fragmentManager.beginTransaction().replace(containerViewId, formFragment, TruFormFragment.FRAGMENT_TAG).commit();
    }

    public static void startActivityForResult(Activity context, String schemaString) {
        startActivityForResult(context, schemaString);
    }

    public static void startActivityForResult(Fragment fragment, String schemaString) {
        startActivityForResult(fragment, schemaString);
    }

    public static void startActivityToRenderConstSchema(Activity activity, String schemaString, String constValue) {
        startActivityToRenderConstSchema(activity, schemaString, constValue);
    }

    public static void startActivityToRenderConstSchema(Fragment fragment, String schemaString, String constValue) {
        startActivityToRenderConstSchema(fragment, schemaString, constValue);
    }


}