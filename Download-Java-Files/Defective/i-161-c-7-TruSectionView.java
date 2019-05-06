package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.utils.TruUtils;
import com.trufla.androidtruforms.models.ObjectInstance;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.utils.ValueToSchemaMapper;

import org.json.JSONObject;

/**
 * Created by ohefny on 6/26/18.
 */

public class TruSectionView extends TruObjectView {

    boolean expanded = false;

    public TruSectionView(Context context, ObjectInstance instance) {
        super(context, instance);
    }

    @Override
    protected void buildSubview() {
        super.buildSubview();
        if (TruUtils.isEmpty(instance.getTitle())) {
            mView.findViewById(R.id.section_header).setVisibility(View.GONE);
        } else {
            handleExpandBehavior();
            mView.findViewById(R.id.container).setVisibility(View.GONE);
        }
        for (SchemaInstance child : instance.getProperties().getVals()) {
            addChildView(child);
        }
    }

    @Override
    protected ViewGroup getContainerView() {
        return ((ViewGroup) mView.findViewById(R.id.container));
    }

    @Override
    protected void onFieldNotValid(SchemaBaseView viewBuilder) {
        if(!expanded)
            mView.findViewById(R.id.section_header).performClick();
    }
    private void handleExpandBehavior() {
        mView.findViewById(R.id.section_header).setOnClickListener(view -> {
            if (expanded) {
                ((ImageView) (view.findViewById(R.id.expand_collapse_img))).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_more_white_24dp));
                mView.findViewById(R.id.container).setVisibility(View.GONE);

            } else {
                ((ImageView) (view.findViewById(R.id.expand_collapse_img))).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_less_white_24dp));
                mView.findViewById(R.id.container).setVisibility(View.VISIBLE);
            }
            expanded = !expanded;
        });
    }

    @Override
    protected void setInstanceData() {
        ((TextView) (mView.findViewById(R.id.input_data))).setText(instance.getPresentationTitle());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_section_view;
    }

    @Override
    protected boolean isFilled() {
        return false;
    }

    @Override
    protected void setNonEditableValues(Object constItem) {
        if (constItem instanceof String) {
            constItem = new JsonParser().parse(constItem.toString());
        }
        if (constItem instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) constItem;
            for (SchemaInstance child : instance.getProperties().getVals()) {
                JsonElement val = jsonObject.get(child.getKey());
                if (val.isJsonPrimitive()) {
                    child.setConstItem(ValueToSchemaMapper.jsonVal2Obj(val.getAsJsonPrimitive()));
                } else if (val.isJsonArray()) {
                    child.setConstItem(ValueToSchemaMapper.getArrayConst(val.getAsJsonArray()));
                } else {
                    child.setConstItem(val.getAsJsonObject());
                }

            }
        }
        mView.setEnabled(false);
    }
}
