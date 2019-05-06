package edu.gatech.cs2340.spacetraders.views;

//import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;

import edu.gatech.cs2340.spacetraders.R;

/**
 * pirate encounter class
 */
public class PirateEncounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pirate_encounter);
    }

    /**
     * run away from pirate
     * @param v view
     */
    public void runAwayButtonOnClick(View v) {
        onBackPressed();
    }

}
