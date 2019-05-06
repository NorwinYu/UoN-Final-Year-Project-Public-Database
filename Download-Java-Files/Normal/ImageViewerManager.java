package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.Image;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.ImageAdapter;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.ImageFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ImageViewerManager extends AppCompatActivity {
    private ArrayList<Bitmap> arr;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);
        viewPager=findViewById(R.id.viewPager);

        ArrayList<Fragment> fragments=new ArrayList<>();

        //принимает объекты с Reception
        Intent intent=getIntent();
        arr=new ArrayList<>();
        arr= (ArrayList<Bitmap>) intent.getExtras().getSerializable("imageData");

        for(Bitmap imgs:arr){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imgs.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Bundle b = new Bundle();
            b.putByteArray("image",byteArray);
            Fragment fragment=new Fragment();
            fragment.setArguments(b);

            // your fragment code
            fragment.setArguments(b);
            fragments.add(fragment);
        }

        ImageAdapter adapter=new ImageAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }
}
