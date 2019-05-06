package com.example.usk.glotus_final.SuperviserApp.ReceptionFiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.usk.glotus_final.R;

import java.util.ArrayList;

public class ImageViewer extends AppCompatActivity {
    private ArrayList<Image> arr;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);
        viewPager=findViewById(R.id.viewPager);

        ArrayList<Fragment> fragments=new ArrayList<>();

        //принимает объекты с Reception
        Intent intent=getIntent();
        arr=new ArrayList<>();
        arr= (ArrayList<Image>) intent.getExtras().getSerializable("imageData");

        for(Image imgs:arr){
            fragments.add(ImageFragment.newInstance(imgs.getImageName(),imgs.getImagePath()));
        }

        ImageAdapter adapter=new ImageAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }
}
