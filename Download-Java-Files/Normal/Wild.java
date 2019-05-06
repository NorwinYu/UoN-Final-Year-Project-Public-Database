package com.musiccoder.clickergame2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Wild extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        final AllWildInfo allWildInfo = new AllWildInfo();

        Intent intent = getIntent();
        final int wildLevel = Integer.parseInt(intent.getStringExtra("position")) + 1;
        final Enemy enemy = new Enemy(allWildInfo.getWildInfo(wildLevel).getMaxHealth(), allWildInfo.getWildInfo(wildLevel).getAttackValue(),
                allWildInfo.getWildInfo(wildLevel).getLootAmount());

        final TextView healthTextView = (TextView) findViewById(R.id.hptext2);

        healthTextView.setText(Integer.toString(allWildInfo.getWildInfo(wildLevel).getCurrentHealth()) + "/" +
                Integer.toString(allWildInfo.getWildInfo(wildLevel).getMaxHealth()));

        Button attackButton = (Button) findViewById(R.id.attackbutton);

        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Encounter.exchangeBlows(Player.getInstance(), enemy);
                healthTextView.setText(Integer.toString(enemy.getCurrentHealth()) + "/" + Integer.toString(enemy.getMaxHealth()));
                if (!Player.getInstance().isAlive() || !enemy.isAlive()) {
                    startActivity(new Intent(Wild.this, MainActivity.class));
                }
            }
        });

    }
}