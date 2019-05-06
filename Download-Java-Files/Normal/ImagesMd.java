package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.usk.glotus_final.R;

import java.util.ArrayList;
import java.util.List;

public class ImagesMd extends Activity {
    ListView listView;
    private ArrayList<String> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_md);
        listView=findViewById(R.id.list);
        List<ImageView> ls = new ArrayList<>();


        Intent intent=getIntent();
        arr=new ArrayList<>();
        arr= (ArrayList<String>) intent.getExtras().getSerializable("imageData");
        System.out.println(arr.size());
        System.out.println(arr.get(0));
        ImageView imageView=new ImageView(this);
        //imageView.setImageBitmap(arr.get(0));
        ls.add(imageView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),R.layout.imageadap,ls);
        listView.setAdapter(arrayAdapter);


    }
}
/*byte[] decodedString = Base64.decode(s[i], Base64.DEFAULT);
                        System.out.println("==============");
                        System.out.println(decodedString);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,options);*/
