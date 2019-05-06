package com.trufla.androidtruforms.truviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trufla.androidtruforms.R;
import com.trufla.androidtruforms.models.SchemaDocument;
import com.trufla.androidtruforms.models.SchemaInstance;
import com.trufla.androidtruforms.utils.TruUtils;

/**
 * Created by ohefny on 7/2/18.
 */

public class TruFormView extends TruObjectView {

    public TruFormView(Context context, SchemaDocument instance) {
        super(context, instance);
    }

    @Override
    protected void setInstanceData() {

    }

    @Override
    protected void buildSubview() {
        super.buildSubview();
        for (SchemaInstance child : instance.getProperties().getVals()) {
            addChildView(child);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tru_form_view;
    }

    @Override
    protected boolean isFilled() {
        return false;
    }

    @Override
    protected ViewGroup getContainerView() {
        return ((ViewGroup) mView.findViewById(R.id.container));
    }

    @Override
    public String getInputtedData() {
        String json = super.getInputtedData();
        if(json==null)
            return null;
        String subString = json.substring(json.indexOf(':') + 1);
        return subString;
    }

    protected void setLayoutParams(View childView, SchemaBaseView truView) {
        if (truView instanceof TruSectionView)
            super.setLayoutParams(childView, truView);
        else {
            LinearLayout.LayoutParams layoutParams = truView.getLayoutParams();
            layoutParams.setMargins((int) TruUtils.convertDpToPixel(16,mContext), 0, (int) TruUtils.convertDpToPixel(16,mContext), 0);
            childView.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onFieldNotValid(SchemaBaseView viewBuilder) {

    }
}
