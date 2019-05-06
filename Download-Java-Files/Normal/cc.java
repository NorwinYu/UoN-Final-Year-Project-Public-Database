package com.example.usk.glotus_final.System.loginFiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.ZayavkaListAdapter;
import com.example.usk.glotus_final.System.connection.ServerPhoto;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class cc {
    public static void main(String args[]){

        getting();

    }
    public static void getimages() {
        String body = "number="+"\"ALA004807\"";
        ServerPhoto sr= new ServerPhoto("http://185.209.23.53/upload/getimages.php",null,body);
        String rr=sr.post();
        Log.d("ada",rr);
        System.out.println(rr);

        System.out.println("7887");

    }
    public static void getting()
    {

        String url=("http://185.209.23.53/upload/getimages.php\"");
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "number="+"ALA004807");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                System.out.println(response.body().toString());
                if (response.isSuccessful()) {
                    final String s[] =response.body().string().split("-----------------------------------------------------------------------------------");
                    for (int i=0;i<s.length;i++)
                        System.out.println(s[i]);



                } else {

                }
            }});
      //  progressBar.setVisibility(View.INVISIBLE);
    }
    /*public void show(String imageString) {
        //  progressBar.setVisibility(View.INVISIBLE);
        ImageView image = new ImageView();

        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,options);
        image.setImageBitmap(decodedByte);
        image.setLayoutParams(new LinearLayout.LayoutParams(linearLayout.getWidth(), 561));
        linearLayout.addView(image);
    }*/

}
