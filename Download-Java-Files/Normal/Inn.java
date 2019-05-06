package com.musiccoder.clickergame2;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inn);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        final int innCost = 2;

        Intent intent = getIntent();
        String numOfCoins = intent.getStringExtra("numOfCoins");

        final TextView currentCoinsTextView = (TextView)findViewById(R.id.currentcoinsinn);
        TextView costTextView = (TextView)findViewById(R.id.healcost);

        currentCoinsTextView.setText("You currently have " + numOfCoins + " coins.");
        costTextView.setText("The cost is " + Integer.toString(innCost) + " coins.");

        Button paymentButton = (Button) findViewById(R.id.innButton);

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overnightInnStay(Player.getInstance(), innCost, currentCoinsTextView);
            }
        });
    }

    public void overnightInnStay(Player p, int innCost, TextView currentCoinsTextView) {
        if (p.getCurrentHealth() != p.getMaxHealth()) {
            p.setCoins(p.getCoins() - innCost);
            p.setCurrentHealth(p.getMaxHealth());
            currentCoinsTextView.setText("You currently have " + Integer.toString(p.getCoins()) + " coins.");

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Inn.this,MainActivity.class));
        super.onBackPressed();
    }
}
