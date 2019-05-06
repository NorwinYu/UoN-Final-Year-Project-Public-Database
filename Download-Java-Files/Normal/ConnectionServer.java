package com.example.usk.glotus_final.System.connection;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectionServer {
    public static String credential;
    public static String url;
    public static int status;

    String json="";

    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body;

    public ConnectionServer(String url, String credential) {
        this.credential = credential;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCredential(String credential){
        this.credential=credential;
    }

    public String get(String cr) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("authorization", cr)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "911e2610-d801-59ed-a01d-694635f63d1c")
                .build();
        return connect(request);
    }

    public String post(String pjson, String cr) {
        body = RequestBody.create(mediaType, pjson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("authorization", cr)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "911e2610-d801-59ed-a01d-694635f63d1c")
                .build();
        return connect(request);
    }

    public String connect(Request request) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                status=-1;
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    json=response.body().string();
                    status=1;
                    countDownLatch.countDown();
                } else {
                    status=0;
                    json=response.body().string();
                    countDownLatch.countDown();
                }
            }});
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return json;
    }
}
