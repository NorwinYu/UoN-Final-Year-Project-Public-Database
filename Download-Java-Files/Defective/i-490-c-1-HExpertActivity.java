package com.sabrcare.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sabrcare.app.HomeActivity;
import com.sabrcare.app.symptomtracker.ModelSymptom;
import com.sabrcare.app.R;
import com.sabrcare.app.adapters.HExpertAdapter;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class HExpertActivity extends AppCompatActivity {

    private Button hexBtn;
    public static ArrayList<ModelSymptom> hExpert = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hexpert);

        hexBtn = findViewById(R.id.hex_btn);
        RecyclerView hexRv = findViewById(R.id.hex_rv);

        ModelSymptom model = new ModelSymptom("albert hawking", 0, "none");
        hExpert.add(model);
        ModelSymptom model1 = new ModelSymptom("robert hawking", 0, "none");
        hExpert.add(model1);

        hexRv.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        HExpertAdapter symptomsAdapter = new HExpertAdapter();
        hexRv.setAdapter(symptomsAdapter);

        hexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HExpertActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });



    }
}
