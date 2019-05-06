package com.trufla.androidtruforms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.trufla.androidtruforms.interfaces.FormContract;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.truviews.TruFormView;
import com.trufla.androidtruforms.utils.EnumDataFormatter;
import com.trufla.androidtruforms.utils.TruUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TruFormFragment.OnFormActionsListener} interface
 * to handle interaction events.
 * Use the {@link TruFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TruFormFragment extends Fragment implements FormContract {
    private OnFormActionsListener mListener;
    private static final String SCHEMA_KEY = "SCHEMA_KEY";
    private static final String JSON_KEY = "JSON_VALUE";
    private static final int IMAGE_PICKER_CODE = 505;
    private TruFormView truFormView;
    TruConsumer<String> mPickedImageListener;
    TruConsumer<ArrayList<Pair<Object, String>>> mDataFetchListener;
    ProgressDialog progressDialog;
    private String schemaString;
    public static final String FRAGMENT_TAG = "TRU_FORM_FRAGMENT";

    public TruFormFragment() {
        // Required empty public constructor
    }

    public static TruFormFragment newInstance(String schemaString) {
        TruFormFragment fragment = new TruFormFragment();
        Bundle args = new Bundle();
        args.putString(SCHEMA_KEY, schemaString);
        fragment.setArguments(args);
        return fragment;
    }

    public static TruFormFragment newInstanceWithConstJson(String schemaString, String jsonValue) {
        TruFormFragment fragment = new TruFormFragment();
        Bundle args = new Bundle();
        args.putString(SCHEMA_KEY, schemaString);
        args.putString(JSON_KEY, jsonValue);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            schemaString = getArguments().getString(SCHEMA_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tru_form, container, false);
        try {
            String jsonVal = getArguments().getString(JSON_KEY);
            if (TextUtils.isEmpty(jsonVal))
                truFormView = SchemaBuilder.getInstance().buildSchemaView(schemaString, getContext());
            else {
                truFormView = SchemaBuilder.getInstance().buildSchemaViewWithConstValues(schemaString, jsonVal, getContext());
                rootView.findViewById(R.id.submit_btn).setVisibility(View.GONE);
            }
            View formView = truFormView.build();
            ((LinearLayout) rootView.findViewById(R.id.form_container)).addView(formView);
            rootView.findViewById(R.id.submit_btn).setOnClickListener((v) -> onSubmitClicked());
        } catch (Exception ex) {
            mListener.onFormFailed();
            ex.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFormActionsListener) {
            mListener = (OnFormActionsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void openImagePicker(TruConsumer<String> pickedImageListener) {
        this.mPickedImageListener = pickedImageListener;
        ImagePicker.create(this).single() // single mode
                // Activity or Fragment
                .start(IMAGE_PICKER_CODE);
    }

    @Override
    public void onRequestData(TruConsumer<ArrayList<Pair<Object, String>>> listener, String selector, ArrayList<String> names, String url) {
        this.mDataFetchListener = listener;
        EnumDataFetcher fetcher = new EnumDataFetcher(mDataFetchListener, selector, names);
        fetcher.requestData(url, getHttpCallback(selector, names));
        showProgressDialog();
    }

    private void showProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            return;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();
    }

    private boolean isValidData() {
        return truFormView.getInputtedData() != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_CODE) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            // or get a single image only
            if (images == null || images.size() == 0)
                return;
            Image image = ImagePicker.getImages(data).get(0);
            String path = image.getPath();
            if (!TruUtils.isEmpty(path) && mPickedImageListener != null) {
                mPickedImageListener.accept(path);
            }

        }
    }


    @NonNull
    private Callback getHttpCallback(final String selector, final ArrayList<String> names) {
        return new TruCallback(getContext().getApplicationContext()) {
            @Override
            public void onUIFailure(String message) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(getContext(), "Can't Load your data " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUISuccess(String responseBody) {
                progressDialog.dismiss();
                mDataFetchListener.accept(EnumDataFormatter.getPairList(responseBody, selector, names));
            }
        };
    }


    public void onSubmitClicked() {
        if (!isValidData()) {
            Toast.makeText(getContext(), "Please correct the errors", Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(getContext(), "submitted", Toast.LENGTH_SHORT).show();
        String result = truFormView.getInputtedData();
        if (mListener != null) {
            mListener.onFormSubmitted(result);
        }
    }

    public interface OnFormActionsListener {
        void onFormSubmitted(String jsonReperesentation);

        void onFormFailed();
    }
}
