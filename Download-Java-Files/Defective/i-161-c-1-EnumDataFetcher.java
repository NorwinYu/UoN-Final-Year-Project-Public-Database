package com.trufla.androidtruforms;

import android.app.ProgressDialog;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trufla.androidtruforms.interfaces.TruConsumer;
import com.trufla.androidtruforms.models.SchemaInstance;

import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class EnumDataFetcher {
    TruConsumer<ArrayList<Pair<Object, String>>> mListener;
    String mSelector;
    ArrayList<String> mNames;

    public EnumDataFetcher(TruConsumer<ArrayList<Pair<Object, String>>> listener, String selector, ArrayList<String> names) {
        this.mListener = listener;
        this.mNames = names;
        this.mSelector = selector;
    }

    public void requestData(String url, Callback callback) {
        SchemaBuilder.getInstance().getOkHttpClient().newCall(getFullRequest(url)).enqueue(callback);
    }


    private Request getFullRequest(String absoluteUrl) {
        ArrayList<String> urlIncludes = new ArrayList<>();
        String fullURL = absoluteUrl;
        for (String mName : mNames) {
            if (mName.contains(".")) {
                String[] parts = mName.split("\\.");
                urlIncludes.add(parts[0]);
            }
        }
        if (urlIncludes.size() != 0) {
            fullURL = fullURL + "?includes=";
            for (int i = 0; i < urlIncludes.size(); i++)
                fullURL = fullURL + urlIncludes.get(i) + ",";
        }

        Request request = SchemaBuilder.getInstance().getRequestBuilder().build();
        return request.newBuilder().url(request.url() + fullURL).build();
    }


}