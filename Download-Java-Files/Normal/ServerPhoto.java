package com.example.usk.glotus_final.System.connection;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerPhoto {
    public static String credential;
    public static int status;
    public static String url;
    public static String way;
    public static String body;
    public static String ans;

    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody data;

    public String res;
    public ServerPhoto(){
    }

    public ServerPhoto(String url){
        this.url=url;
    }
    public ServerPhoto(String url, String credential){
        this.url=url;
        this.credential=credential;
    }
    public ServerPhoto(String url, String credential, String body){
        this.url=url;
        this.credential=credential;
        this.body=body;
    }




    public String get() {

        if (credential==null){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();
        return connect(request);
        }
        else {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("authorization",credential)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .build();
            return connect(request);

        }
    }

    public String post() {
        data = RequestBody.create(mediaType, body);
        if (credential==null){
            Request request = new Request.Builder()
                    .url(url)
                    .post(data)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .build();
            return connect(request);
        }
        else {
            Request request = new Request.Builder()
                    .url(url)
                    .post(data)
                    .addHeader("authorization", credential)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .build();
            return connect(request);
        }

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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (response.isSuccessful()) {


                        res= response.body().string();


                    status=1;
                    countDownLatch.countDown();
                } else {
                    status=0;
                    res=response.body().string();
                    countDownLatch.countDown();
                }
            }});
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        return res;
    }
    public static String getBody() {
        return body;
    }
    public static String getCredential() {
        return credential;
    }
    public static int getStatus() {
        return status;
    }
    public static String getUrl() {
        return url;
    }
    public static String getWay() {
        return way;
    }
    public static void setBody(String body) {
        ServerPhoto.body = body;
    }
    public static void setCredential(String credential) {
        ServerPhoto.credential = credential;
    }
    public static void setStatus(int status) {
        ServerPhoto.status = status;
    }
    public static void setUrl(String url) {
        ServerPhoto.url = url;
    }
    public static void setWay(String way) {
        ServerPhoto.way = way;
    }
    public String getRes() {
        return res;
    }
    public static String getAns() {
        return ans;
    }


}
