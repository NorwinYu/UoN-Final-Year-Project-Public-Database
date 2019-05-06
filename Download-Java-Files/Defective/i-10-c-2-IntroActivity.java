package com.frictionhacks.tenderhaltinfo.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.frictionhacks.tenderhaltinfo.R;
import com.frictionhacks.tenderhaltinfo.Util.CubeOutDepthTransformation;
import com.frictionhacks.tenderhaltinfo.Util.Preferences;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SliderPage sp1 = new SliderPage();
        sp1.setTitle("MINISTRY OF ROAD & TRANSPORT");
        sp1.setDescription("Simply serves the practical solution to the on-field issues");
        sp1.setImageDrawable(R.drawable.logo);
        sp1.setBgColor(Color.parseColor("#034C69"));

        SliderPage sp2 = new SliderPage();
        sp2.setTitle("The 3'S");
        sp2.setDescription("----Simple, Swift & Secure--- ");
        sp2.setBgColor(Color.parseColor("#3265F5"));

        SliderPage sp3 = new SliderPage();
        sp3.setTitle("Real Time Location Access");
        sp3.setDescription("It requires real time location permission for the exact location of the concerned project  ");
        sp3.setBgColor(Color.parseColor("#2F8B57"));


        addSlide(AppIntroFragment.newInstance(sp1));
        addSlide(AppIntroFragment.newInstance(sp2));
        addSlide(AppIntroFragment.newInstance(sp3));

        setCustomTransformer(new CubeOutDepthTransformation());

    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Preferences.setFirstRun(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Preferences.setFirstRun(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
