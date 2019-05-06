package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.Zayavka;

import java.util.ArrayList;

public class VygruzkaList extends AppCompatActivity{

    private ArrayList<Zayavka> mZayavkas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_list);

        ListView list = (ListView) findViewById(R.id.manager_list);

        Zayavka z1 = new Zayavka();
        mZayavkas = new ArrayList<>();

        VygruzkaAdapter adapter = new VygruzkaAdapter(this, R.layout.manager_list, mZayavkas);
        list.setAdapter(adapter);


    }
}
