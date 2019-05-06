package com.musiccoder.clickergame2;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseALevelWild extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_a_level);

        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add("Level 1");
        arrayList.add("Level 2");
        arrayList.add("Level 3");
        arrayList.add("Level 4");
        arrayList.add("Level 5");
        arrayList.add("Level 6");
        arrayList.add("Level 7");
        arrayList.add("Level 8");
        arrayList.add("Level 9");
        arrayList.add("Level 10");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChooseALevelWild.this, Wild.class);
                intent.putExtra("position",Integer.toString(position));
                startActivity(intent);
            }
        });

    }
}
