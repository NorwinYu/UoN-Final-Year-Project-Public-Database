package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.OneOfPropertyWrapper;
import com.trufla.androidtruforms.models.SchemaInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


/**
 * Created by ohefny on 7/2/18.
 */

public abstract class TruObjectView extends SchemaBaseView<ObjectInstance> implements TruEnumView.EnumValueChangedListener {
    protected ArrayList<SchemaBaseView> childs = new ArrayList<>();
    protected HashMap<String, SchemaBaseView> childsAsHash = new HashMap();

    public TruObjectView(Context context, ObjectInstance instance) {
        super(context, instance);
    }

    protected abstract ViewGroup getContainerView();

    @Override
    public String getInputtedData() {
        if (mView == null)
            return String.format(Locale.getDefault(), "\"%s\":{}", instance.getKey());

        if (!validate())
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (SchemaBaseView viewBuilder : childs) {
            if (!viewBuilder.getInputtedData().equals(""))
                stringBuilder.append(viewBuilder.getInputtedData()).append(",");
        }
        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ',')
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        else
            return "";
        return String.format(Locale.getDefault(), "\"%s\":{%s}", instance.getKey(), stringBuilder.toString());
    }

    protected void addChildView(SchemaInstance child) {
        SchemaBaseView childViewBuilder = child.getViewBuilder(mContext);
        View childView = childViewBuilder.build();
        initBooleanLogicForChild(childViewBuilder);
        childs.add(childViewBuilder);
        ((ViewGroup) mView.findViewById(R.id.container)).addView(childView);
        setLayoutParams(childView, childViewBuilder);
        childViewBuilder.setParentView(this);
    }

    private void initBooleanLogicForChild(SchemaBaseView childViewBuilder) {
        if (hasDependencies(childViewBuilder.instance.getKey()))
            childViewBuilder.build().setVisibility(View.GONE);
        if (isRequiredForOthers(childViewBuilder.instance.getKey()) && childViewBuilder instanceof TruEnumView)
            ((TruEnumView) childViewBuilder).setValueChangedListener(this);
    }

    protected void setLayoutParams(View childView, SchemaBaseView truView) {
        LinearLayout.LayoutParams layoutParams = truView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 8);
        childView.setLayoutParams(layoutParams);
    }

    @Override
    public boolean validate() {
        boolean isVaild = true;
        for (SchemaBaseView viewBuilder : childs) {
            if (!viewBuilder.validate()) {
                isVaild = false;
                onFieldNotValid(viewBuilder);
            }
        }
        return isVaild;
    }

    abstract protected void  onFieldNotValid(SchemaBaseView viewBuilder);

    public boolean isRequiredForOthers(String itemKey) {
        if (instance.getOneOf() != null) {
            for (OneOfPropertyWrapper oneOfPropertyWrapper : instance.getOneOf()) {
                if (oneOfPropertyWrapper.getProperty().getKey().equals(itemKey)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasDependencies(String itemKey) {
        if (instance.getOneOf() != null) {
            for (OneOfPropertyWrapper oneOfPropertyWrapper : instance.getOneOf()) {
                for (String dependOnItem : oneOfPropertyWrapper.getProperty().getRequired()) {
                    if (dependOnItem.equals(itemKey))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onEnumValueChanged(String itemKey, Object val) {
        for (OneOfPropertyWrapper oneOfProperty : instance.getOneOf()) {
            if (oneOfProperty.getProperty().getKey().equals(itemKey.toLowerCase())) {
                for (String required : oneOfProperty.getProperty().getRequired()) {
                    for (SchemaBaseView childView : childs) {
                        if (childView.instance.getKey().equals(required)) {
                            if (isValueRequireItem(val, oneOfProperty))
                                childView.build().setVisibility(View.VISIBLE);
                            else
                                childView.build().setVisibility(View.GONE);

                        }
                    }
                }
            }
        }
    }

    private boolean isValueRequireItem(Object val, OneOfPropertyWrapper oneOfProperty) {
        return oneOfProperty.getProperty().getEnums().contains(val.toString());
    }
}
