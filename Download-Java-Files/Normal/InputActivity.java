package com.msproject.myhome.mydays;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {
    EditText start, end, category;
    Button btnConfirm;
    int[] color = {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN};
    static int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        start = findViewById(R.id.start_hour);
        end = findViewById(R.id.end_hour);
        category = findViewById(R.id.category);


        btnConfirm = findViewById(R.id.confirm);
        btnConfirm.setText(getIntent().getStringExtra("Hour"));

        Toast.makeText(this.getApplicationContext(), getIntent().getStringExtra("Date"), Toast.LENGTH_SHORT).show();
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startHour = Integer.parseInt(start.getText().toString());
                int endHour = Integer.parseInt(end.getText().toString());
                String categoryName = category.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("start",startHour);
                returnIntent.putExtra("end",endHour);
                returnIntent.putExtra("category",categoryName);
                returnIntent.putExtra("color",color[index%4]);
                index++;
                setResult(0,returnIntent);
                finish();
            }
        });
    }
}
